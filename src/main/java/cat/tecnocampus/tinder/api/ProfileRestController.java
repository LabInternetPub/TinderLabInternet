package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.application.TinderController;
import cat.tecnocampus.tinder.domain.Profile;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProfileRestController {

	private TinderController tinderController;

	public ProfileRestController(TinderController tinderController) {
		this.tinderController = tinderController;
	}

	@GetMapping("/profiles/{email}")
	public Profile getProfile(@PathVariable String email) throws Exception {
		Profile user = tinderController.getProfileLazy(email);
		return user;
	}

	@GetMapping("/profiles")
	public List<Profile> getProfiles() {
		return tinderController.getProfilesLazy();
	}

	@GetMapping("/fullProfiles/{email}")
	public Profile getFullProfile(@PathVariable String email) throws Exception {
		Profile user = tinderController.getFullProfile(email);
		return user;
	}

	@GetMapping("/fullProfiles")
	public List<Profile> getfullProfiles() {
		return tinderController.getFullProfiles();
	}

	@GetMapping("/{email}/candidates")
	public List<Profile> getCandidates(@PathVariable String email) {
		return tinderController.getCandidates(email);
	}

	@PostMapping("/profiles")
	public String addProfile(@RequestBody Profile profile) {
		return tinderController.addProfile(profile);
	}

	@PostMapping("/profilesString")
	public String addProfile(@RequestBody String profile) {
		Gson gson = new Gson();

		Profile user=gson.fromJson(profile, Profile.class);
		tinderController.addProfile(user);;
		return gson.toJson(user);
	}

	@PostMapping("/likes")
	public String addLikes(@RequestBody Like likes) {
		int created = tinderController.newLikes(likes.getOrigin(), likes.getTargets());
		return created + " likes created";
	}


	private class Like {
		private String origin;
		private List<String> targets;

		public Like() {
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public List<String> getTargets() {
			return targets;
		}

		public void setTargets(List<String> targets) {
			this.targets = targets;
		}
	}
}
