package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.application.dto.ProfileDTO;
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

	public ProfileDTO getProfileLazy(String id) {
		return profileDAO.getProfileLazy(id);
	}

	public List<ProfileDTO> getProfilesLazy() {
		return profileDAO.getProfilesLazy();
	}

	public ProfileDTO getFullProfile(String id) {
		return profileDAO.getProfile(id);
	}

	public List<ProfileDTO> getFullProfiles() {
		return profileDAO.getProfiles();
	}

	public List<ProfileDTO> getCandidates(String id) {
		ProfileDTO userDTO = this.getProfileLazy(id);
		Profile user = profileDTOtoProfile(userDTO);
		return this.getProfilesLazy().stream()
				.map(this::profileDTOtoProfile)
				.filter(user::isCompatible)
				.map(this::profileToProfileDTO)
				.collect(Collectors.toList());
	}

	public ProfileDTO addProfile(ProfileDTO profile) {
		profile.setId(UUID.randomUUID().toString());
		return profileDAO.addProfile(profile);
	}

	public int newLikes(String originId, List<String> targetId) {
		ProfileDTO originDTO = profileDAO.getProfile(originId); //check it exists in BBDD
		Profile origin = profileDTOtoProfile(originDTO);

		List<Like> likes =
		targetId.stream().map(profileDAO::getProfile) 	//check it exists in BBDD
				.map(this::profileDTOtoProfile)			//convert to domain profile
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

	private Profile profileDTOtoProfile(ProfileDTO profileDTO) {
		Profile result = new Profile();
		result.setId(profileDTO.getId());
		result.setEmail(profileDTO.getEmail());
		result.setPassion(profileDTO.getPassion());
		result.setNickname(profileDTO.getNickname());
		result.setAttraction(profileDTO.getAttraction());
		result.setGender(profileDTO.getGender());
		result.setLikes(profileDTO.getLikes());

		return result;
	}
	private ProfileDTO profileToProfileDTO(Profile profile) {
		ProfileDTO result = new ProfileDTO();
		result.setId(profile.getId());
		result.setEmail(profile.getEmail());
		result.setPassion(profile.getPassion());
		result.setNickname(profile.getNickname());
		result.setAttraction(profile.getAttraction());
		result.setGender(profile.getGender());
		result.setLikes(profile.getLikes());

		return result;
	}
}
