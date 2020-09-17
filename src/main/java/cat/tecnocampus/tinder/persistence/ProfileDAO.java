package cat.tecnocampus.tinder.persistence;

import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import cat.tecnocampus.tinder.application.exception.ProfileNotFound;
import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.domain.Like;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
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
			return jdbcTemplate.queryForObject(queryProfileLazy, new Object[]{id}, profileRowMapperLazy);
		} catch (EmptyResultDataAccessException e) {
			throw new ProfileNotFound(id);
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
				"p.target as likes_target, p.creation_date as likes_creationDate, p.matched as likes_matched, p.match_date as likes_matchDate from tinder_user u left join tinder_like p on u.id = p.origin where u.id = ?";
		ProfileDTO result;
		try {
			result = jdbcTemplate.queryForObject(queryProfile, new Object[]{id}, profileRowMapper);
			cleanEmptyLikes(result);
			return result;
		} catch (EmptyResultDataAccessException e) {
			throw new ProfileNotFound(id);
		}
	}

	@Override
	public List<ProfileDTO> getProfiles() {
		final String queryProfiles = "select u.id as id, u.email as email, u.nickname as nickname, u.gender as gender, u.attraction as attraction, u.passion as passion, " +
				"p.target as likes_target, p.creation_date as likes_creationDate, p.matched as likes_matched, p.match_date as likes_matchDate from tinder_user u left join tinder_like p on u.id = p.origin";
		List<ProfileDTO> result;
		result = jdbcTemplate.query(queryProfiles, profilesRowMapper);
		result.stream().forEach(this::cleanEmptyLikes);
		return result;
	}

	//Avoid list of candidates with an invalid like when the profile hasn't any
	private void cleanEmptyLikes(ProfileDTO profile) {
		boolean hasNullCandidates = profile.getLikes().stream().anyMatch(c -> c.getTarget() == null);
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
		final String insertLike = "INSERT INTO tinder_like (origin, target, matched, creation_date) VALUES (?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(insertLike, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
				Like like = likes.get(i);
				preparedStatement.setString(1, origin);
				preparedStatement.setString(2, like.getTarget());
				preparedStatement.setBoolean(3, like.isMatched());
				preparedStatement.setDate(4, Date.valueOf(like.getCreationDate()));
			}

			@Override
			public int getBatchSize() {
				return likes.size();
			}
		});
	}

	@Override
	public void updateLikeToMatch(String id, String id1) {
		final String updateLike = "UPDATE tinder_like SET matched = true, match_date = ? where origin = ? AND target = ?";
		jdbcTemplate.update(updateLike, Date.valueOf(LocalDate.now()), id, id1);
	}
}
