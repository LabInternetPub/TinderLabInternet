package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.domain.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileController {
	private ProfileDAO profileDAO;

	public ProfileController(ProfileDAO profileDAO) {
		this.profileDAO = profileDAO;
	}

	public Profile getProfileLazy(String email) {
		return profileDAO.getProfileLazy(email);
	}

	public List<Profile> getProfilesLazy() {
		return profileDAO.getProfilesLazy();
	}

	public Profile getFullProfile(String email) {
		return profileDAO.getProfile(email);
	}

	public List<Profile> getFullProfiles() {
		return profileDAO.getProfiles();
	}

	public String addProfile(Profile profile) {
		return profileDAO.addProfile(profile);
	}
}
