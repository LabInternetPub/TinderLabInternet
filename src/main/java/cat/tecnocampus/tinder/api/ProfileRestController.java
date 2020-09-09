package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.application.ProfileController;
import cat.tecnocampus.tinder.domain.Profile;
import com.google.gson.Gson;
import org.apache.naming.factory.SendMailFactory;
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

	@GetMapping("/{email}/candidates")
	public List<Profile> getCandidates(@PathVariable String email) {
		return profileController.getCandidates(email);
	}

	@PostMapping("/profiles")
	public String addProfile(@RequestBody Profile profile) {
		return profileController.addProfile(profile);
	}

	@PostMapping("/profilesString")
	public String addProfile(@RequestBody String profile) {
		Gson gson = new Gson();

		Profile user=gson.fromJson(profile, Profile.class);
		profileController.addProfile(user);;
		return gson.toJson(user);
	}
}
