package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.domain.Like;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TinderController {
	private ProfileDAO profileDAO;

	public TinderController(ProfileDAO profileDAO) {
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

	public List<Profile> getCandidates(String email) {
		Profile user = this.getProfileLazy(email);
		return this.getProfilesLazy().stream().filter(user::isCompatible).collect(Collectors.toList());
	}

	public String addProfile(Profile profile) {
		return profileDAO.addProfile(profile);
	}

	public int newLikes(String originMail, List<String> targetMails) {
		Profile origin = profileDAO.getProfile(originMail); //check it exists in BBDD

		List<Like> likes =
		targetMails.stream().map(profileDAO::getProfile) 	//check it exists in BBDD
				.filter(origin::isCompatible) 				 //make sure it is compatible
				.map(t -> createAndMatchProposal(origin, t)).collect(Collectors.toList());

		origin.addLikes(likes);
		profileDAO.saveLikes(origin.getEmail(), likes);
		return targetMails.size();
	}

	//Should this function be separated in three smaller ones? such as
	// 1.- Create proposal
	// 2.- Set proposal to match if it does
	// 3.- Update the target like to match in the DDBB
	private Like createAndMatchProposal(Profile origin, Profile target) {
		Like like = new Like(target.getEmail());
		if (target.likes(origin)) {
			like.setMatched(true);  	//origin set to match
			target.setMatch(origin);		//target set to match
			profileDAO.updateLikeToMatch(target.getEmail(), origin.getEmail());
		}
		return like;
	}
}
