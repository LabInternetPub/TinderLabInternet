package cat.tecnocampus.tinder.persistence;

import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import cat.tecnocampus.tinder.application.exception.ProfileNotFoundException;
import cat.tecnocampus.tinder.domain.Like;
import cat.tecnocampus.tinder.domain.Profile;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository //@Component
public class ProfileDAO implements cat.tecnocampus.tinder.application.ProfileDAO {

	private JdbcTemplate jdbcTemplate;

	private final RowMapper<ProfileDTO> profileRowMapperLazy = (resultSet, i) -> {
		ProfileDTO profile = new ProfileDTO();

		profile.setId(resultSet.getString("id"));
		profile.setEmail(resultSet.getString("email"));
		profile.setNickname(resultSet.getString("nickname"));
		profile.setGender(Profile.Gender.valueOf(resultSet.getString("gender")));
		profile.setAttraction(Profile.Gender.valueOf(resultSet.getString("attraction")));
		profile.setPassion(Profile.Passion.valueOf(resultSet.getString("passion")));

		return profile;
	};

	ResultSetExtractorImpl<ProfileDTO> profilesRowMapper =
			JdbcTemplateMapperFactory
					.newInstance()
					.addKeys("id")
					.newResultSetExtractor(ProfileDTO.class);

	RowMapperImpl<ProfileDTO> profileRowMapper =
			JdbcTemplateMapperFactory
					.newInstance()
					.addKeys("id")
					.newRowMapper(ProfileDTO.class);

	public ProfileDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public ProfileDTO getProfileLazy(String id) {
		final String queryProfileLazy = "select id, email, nickname, gender, attraction, passion from tinder_user where id = ?";
		try {
			return jdbcTemplate.queryForObject(queryProfileLazy, profileRowMapperLazy, id);
		} catch (EmptyResultDataAccessException e) {
			throw new ProfileNotFoundException(id);
		}
	}

	@Override
	public List<ProfileDTO> getProfilesLazy() {
		final String queryProfilesLazy = "select id, email, nickname, gender, attraction, passion from tinder_user";
		return jdbcTemplate.query(queryProfilesLazy, profileRowMapperLazy);
	}

	@Override
	public ProfileDTO getProfile(String id) {
		final String queryProfile = "select u.id as id, u.email as email, u.nickname as nickname, u.gender as gender, u.attraction as attraction, u.passion as passion, " +
				"l.creation_date as likes_creationDate, l.matched as likes_matched, l.match_date as likes_matchDate, " +
				"tu.id as likes_target_id, tu.email as likes_target_email, tu.nickname as likes_target_nickname, tu.gender as likes_target_gender, tu.attraction as likes_target_attraction, tu.passion as likes_target_passion " +
				"from tinder_user u " +
				"left join tinder_like l on u.id = l.origin " +
				"left join tinder_user tu on l.origin = u.id and l.target = tu.id " +
				"where u.id = ?";
		List<ProfileDTO> result;
		try {
			result = jdbcTemplate.query(queryProfile, profilesRowMapper, id);
			cleanEmptyLikes(result.get(0));
			return result.get(0);
		} catch (EmptyResultDataAccessException e) {
			throw new ProfileNotFoundException(id);
		}
	}

	@Override
	public List<ProfileDTO> getProfiles() {
		final String queryProfiles = "select u.id as id, u.email as email, u.nickname as nickname, u.gender as gender, u.attraction as attraction, u.passion as passion, " +
				"l.creation_date as likes_creationDate, l.matched as likes_matched, l.match_date as likes_matchDate, " +
				"tu.id as likes_target_id, tu.email as likes_target_email, tu.nickname as likes_target_nickname, tu.gender as likes_target_gender, tu.attraction as likes_target_attraction, tu.passion as likes_target_passion " +
				"from tinder_user u " +
				"left join tinder_like l on u.id = l.origin " +
				"left join tinder_user tu on l.origin = u.id and l.target = tu.id";

		List<ProfileDTO> result = jdbcTemplate.query(queryProfiles, profilesRowMapper);
		result.stream().forEach(this::cleanEmptyLikes);
		return result;
	}

	//Avoid list of candidates with an invalid like when the profile hasn't any
	private void cleanEmptyLikes(ProfileDTO profile) {
		boolean hasNullCandidates = profile.getLikes().stream().anyMatch(l -> l.getCreationDate() == null);
		if (hasNullCandidates) {
			profile.setLikes(new ArrayList<>());
		}
	}

	@Override
	public ProfileDTO addProfile(ProfileDTO profile) {
		final String insertProfile = "INSERT INTO tinder_user (id, email, nickname, gender, attraction, passion) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertProfile, profile.getId(), profile.getEmail(), profile.getNickname(), profile.getGender().toString(),
				profile.getAttraction().toString(), profile.getPassion().toString());

		return this.getProfile(profile.getId());
	}

	@Override
	public void saveLikes(String origin, List<Like> likes) {
		final String insertLike = "INSERT INTO tinder_like (origin, target, matched, creation_date, match_date) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(insertLike, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
				Like like = likes.get(i);
				preparedStatement.setString(1, origin);
				preparedStatement.setString(2, like.getTarget().getId());
				preparedStatement.setBoolean(3, like.isMatched());
				preparedStatement.setDate(4, Date.valueOf(like.getCreationDate()));
				preparedStatement.setDate(5, like.getMatchDate()==null? null : Date.valueOf(like.getMatchDate()));
			}

			@Override
			public int getBatchSize() {
				return likes.size();
			}
		});
	}

	@Override
	public void updateLikeToMatch(String id, String id1, LocalDate matchDate) {
		final String updateLike = "UPDATE tinder_like SET matched = 1, match_date = ? where origin = ? AND target = ?";
		jdbcTemplate.update(updateLike, Date.valueOf(matchDate), id, id1);
	}
}
