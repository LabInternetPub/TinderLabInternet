package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.application.ProfileController;
import cat.tecnocampus.tinder.domain.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProfileRestController {

	private ProfileController profileController;

	public ProfileRestController(ProfileController profileController) {
		this.profileController = profileController;
	}

	@GetMapping("/profiles/{email}")
	public Profile getProfile(@PathVariable String email) throws Exception {
		Profile user = profileController.getProfileLazy(email);
		return user;
	}

	@GetMapping("/profiles")
	public List<Profile> getProfiles() {
		return profileController.getProfilesLazy();
	}

	@GetMapping("/fullProfiles/{email}")
	public Profile getFullProfile(@PathVariable String email) throws Exception {
		Profile user = profileController.getFullProfile(email);
		return user;
	}

	@GetMapping("/fullProfiles")
	public List<Profile> getfullProfiles() {
		return profileController.getFullProfiles();
	}
}
