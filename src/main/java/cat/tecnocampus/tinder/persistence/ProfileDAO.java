package cat.tecnocampus.tinder.persistence;

import cat.tecnocampus.tinder.application.exception.ProfileNotFound;
import cat.tecnocampus.tinder.domain.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileDAO implements cat.tecnocampus.tinder.application.ProfileDAO {

	private JdbcTemplate jdbcTemplate;

	private final String queryProfileLazy = "select email, nickname, gender, attraction, passion from tinder_user where email = ?";
	private final String queryProfilesLazy = "select email, nickname, gender, attraction, passion from tinder_user";

	private RowMapper<Profile> profileRowMapperLazy = (resultSet, i) -> {
		Profile profile = new Profile();

		profile.setEmail(resultSet.getString("email"));
		profile.setNickname(resultSet.getString("nickname"));
		profile.setGender(Profile.Gender.valueOf(resultSet.getString("gender")));
		profile.setAttraction(Profile.Gender.valueOf(resultSet.getString("attraction")));
		profile.setPassion(Profile.Passion.valueOf(resultSet.getString("passion")));

		return profile;
	};


	public ProfileDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Profile getProfileLazy(String email) {
		try {
			return jdbcTemplate.queryForObject(queryProfileLazy, new Object[]{email}, profileRowMapperLazy);
		} catch (EmptyResultDataAccessException e) {
			throw new ProfileNotFound(email);
		}
	}

	public List<Profile> getProfilesLazy() {
		return jdbcTemplate.query(queryProfilesLazy, profileRowMapperLazy);
	}

}
