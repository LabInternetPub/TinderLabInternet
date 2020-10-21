package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.application.dto.LikeDTO;
import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.domain.Like;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service //same as @Component
public class TinderController {
	private ProfileDAO profileDAO;

	public TinderController(ProfileDAO profileDAO) {
		this.profileDAO = profileDAO;
	}

	public ProfileDTO getProfileLazy(String id) {
		return profileDAO.getProfileLazy(id);
	}

	private ProfileDTO getProfileByNameLazy(String name) {
		return profileDAO.getProfileByNameLazy(name);
	}

	public List<ProfileDTO> getProfilesLazy() {
		return profileDAO.getProfilesLazy();
	}

	public ProfileDTO getProfileEager(String id) {
		return profileDAO.getProfile(id);
	}

	public ProfileDTO getProfileByNameEager(String name) {
		return profileDAO.getProfileByName(name);
	}

	public List<ProfileDTO> getProfilesEager() {
		return profileDAO.getProfiles();
	}

	public List<ProfileDTO> getCandidates(String id) {
		ProfileDTO userDTO = this.getProfileLazy(id);
		return getProfileDTOS(userDTO);
	}

	private List<ProfileDTO> getProfileDTOS(ProfileDTO userDTO) {
		Profile user = profileDTOtoProfile(userDTO);
		return this.getProfilesLazy().stream()
				.map(this::profileDTOtoProfile)
				.filter(user::isCompatible)
				.map(this::profileToProfileDTO)
				.collect(Collectors.toList());
	}

	public List<ProfileDTO> getCandidatesByName(String name) {
		ProfileDTO userDTO = this.getProfileByNameLazy(name);
		return getProfileDTOS(userDTO);
	}

	public ProfileDTO addProfile(ProfileDTO profile) {
		profile.setId(UUID.randomUUID().toString());
		return profileDAO.addProfile(profile);
	}

	public int newLikes(String originId, List<String> targetId) {
		ProfileDTO originDTO = profileDAO.getProfile(originId); //check it exists in DDBB
		Profile origin = profileDTOtoProfile(originDTO);

		List<Like> likes =
		targetId.stream().map(profileDAO::getProfile) 	//check it exists in DDBB
				.map(this::profileDTOtoProfile)			//convert to domain profile
				.filter(origin::isCompatible) 			//make sure it is compatible
				.map(origin::createAndMatchLike)		//create likes
				.collect(Collectors.toList());

		updateLikesPersistence(likes, originId);
		return likes.size();
	}

	private void updateLikesPersistence(List<Like> likes, String originId) {
		//origin likes
		profileDAO.saveLikes(originId, likes);

		//target matched likes
		likes.stream().filter(l -> l.isMatched()).forEach(l -> profileDAO.updateLikeToMatch(l.getTarget().getId(), originId, l.getMatchDate()));
	}

	private Profile profileDTOtoProfile(ProfileDTO profileDTO) {
		Profile result = new Profile();
		result.setId(profileDTO.getId());
		result.setEmail(profileDTO.getEmail());
		result.setPassion(profileDTO.getPassion());
		result.setNickname(profileDTO.getNickname());
		result.setAttraction(profileDTO.getAttraction());
		result.setGender(profileDTO.getGender());
		result.setLikes(profileDTO.getLikes().stream().map(this::LikeDTOtoLike).collect(Collectors.toList()));

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
		result.setLikes(profile.getLikes().stream().map(this::LikeToLikeDTO).collect(Collectors.toList()));

		return result;
	}

	private Like LikeDTOtoLike(LikeDTO likeDTO) {
		Like like = new Like();
		like.setTarget(profileDTOtoProfile(likeDTO.getTarget()));
		like.setCreationDate(likeDTO.getCreationDate());
		like.setMatchDate(likeDTO.getMatchDate());
		like.setMatched(likeDTO.isMatched());

		return like;
	}

	private LikeDTO LikeToLikeDTO(Like like) {
		LikeDTO likeDTO = new LikeDTO();
		likeDTO.setTarget(profileToProfileDTO(like.getTarget()));
		likeDTO.setCreationDate(like.getCreationDate());
		likeDTO.setMatchDate(like.getMatchDate());
		likeDTO.setMatched(like.isMatched());

		return likeDTO;
	}
}
