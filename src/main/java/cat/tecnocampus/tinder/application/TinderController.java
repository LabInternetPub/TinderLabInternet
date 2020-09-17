package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.domain.Like;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TinderController {
	private ProfileDAO profileDAO;

	public TinderController(ProfileDAO profileDAO) {
		this.profileDAO = profileDAO;
	}

	public Profile getProfileLazy(String id) {
		return profileDAO.getProfileLazy(id);
	}

	public List<Profile> getProfilesLazy() {
		return profileDAO.getProfilesLazy();
	}

	public Profile getFullProfile(String id) {
		return profileDAO.getProfile(id);
	}

	public List<Profile> getFullProfiles() {
		return profileDAO.getProfiles();
	}

	public List<Profile> getCandidates(String id) {
		Profile user = this.getProfileLazy(id);
		return this.getProfilesLazy().stream().filter(user::isCompatible).collect(Collectors.toList());
	}

	public Profile addProfile(Profile profile) {
		profile.setId(UUID.randomUUID().toString());
		return profileDAO.addProfile(profile);
	}

	public int newLikes(String originId, List<String> targetId) {
		Profile origin = profileDAO.getProfile(originId); //check it exists in BBDD

		List<Like> likes =
		targetId.stream().map(profileDAO::getProfile) 	//check it exists in BBDD
				.filter(origin::isCompatible) 				 //make sure it is compatible
				.map(t -> createAndMatchProposal(origin, t)).collect(Collectors.toList());

		origin.addLikes(likes);
		profileDAO.saveLikes(origin.getId(), likes);
		return targetId.size();
	}

	//Should this function be separated in three smaller ones? such as
	// 1.- Create proposal
	// 2.- Set proposal to match if it does
	// 3.- Update the target like to match in the DDBB
	private Like createAndMatchProposal(Profile origin, Profile target) {
		Like like = new Like(target.getId());
		if (target.likes(origin)) {
			like.setMatched(true);  	//origin set to match
			target.setMatch(origin);		//target set to match
			profileDAO.updateLikeToMatch(target.getId(), origin.getId());
		}
		return like;
	}
}
