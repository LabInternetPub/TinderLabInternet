package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.api.frontendException.IncorrectRESTParameter;
import cat.tecnocampus.tinder.application.TinderController;
import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
public class ProfileRestController {

	private TinderController tinderController;


	public ProfileRestController(TinderController tinderController) {
		this.tinderController = tinderController;
	}

	@GetMapping("/profiles/{id}")
	public ProfileDTO getProfile(@PathVariable String id, @RequestParam(defaultValue = "lazy") String mode) throws Exception {
		ProfileDTO user;
		if (mode.equalsIgnoreCase("lazy"))
			user = tinderController.getProfileLazy(id);
		else {
			if (mode.equalsIgnoreCase("eager"))
				user = tinderController.getProfileEager(id);
			else throw new IncorrectRESTParameter("mode", mode);
		}
		return user;
	}

	@GetMapping("/profiles")
	public List<ProfileDTO> getProfiles(@RequestParam(defaultValue = "lazy") String mode) {
		if (mode.equalsIgnoreCase("lazy"))
			return tinderController.getProfilesLazy();
		else {
			if (mode.equalsIgnoreCase("eager"))
				return tinderController.getProfilesEager();
			else throw new IncorrectRESTParameter("mode", mode);
		}
	}

	@GetMapping("profiles/me")
	public ProfileDTO getMyProfile(Principal principal) {
		return tinderController.getProfileByNameEager(principal.getName());
	}

	//Returns profiles that match the user (id) preferences
	@GetMapping("profiles/{id}/candidates")
	public List<ProfileDTO> getCandidates(@PathVariable(name="id") String whatever) {
		return tinderController.getCandidates(whatever);
	}

	@GetMapping("profiles/me/candidates")
	public List<ProfileDTO> getMeCandidates(Principal principal) {
		return tinderController.getCandidatesByName(principal.getName());
	}

	@PostMapping("/profiles")
	public ProfileDTO addProfile(@RequestBody @Valid ProfileDTO profile) {
		return tinderController.addProfile(profile);
	}

	@PostMapping("/likes")
	public void addLikes(@RequestBody Like likes) {
		tinderController.newLikes(likes.getOrigin(), likes.getTargets());
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
