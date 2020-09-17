package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.api.frontendException.IncorrectRESTParameter;
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

	@GetMapping("/profiles/{id}")
	public Profile getProfile(@PathVariable String id, @RequestParam(defaultValue = "lazy") String mode) throws Exception {
		Profile user;
		if (mode.equalsIgnoreCase("lazy"))
			user = tinderController.getProfileLazy(id);
		else {
			if (mode.equalsIgnoreCase("eager"))
				user = tinderController.getFullProfile(id);
			else throw new IncorrectRESTParameter("mode", mode);
		}
		return user;
	}

	@GetMapping("/profiles")
	public List<Profile> getProfiles(@RequestParam(defaultValue = "lazy") String mode) {
		if (mode.equalsIgnoreCase("lazy"))
			return tinderController.getProfilesLazy();
		else {
			if (mode.equalsIgnoreCase("eager"))
				return tinderController.getFullProfiles();
			else throw new IncorrectRESTParameter("mode", mode);
		}
	}

	//Returns profiles that match the user (id) preferences
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
