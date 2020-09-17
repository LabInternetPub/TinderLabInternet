package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.domain.Like;

import java.util.List;

public interface ProfileDAO {

    ProfileDTO getProfileLazy(String id);
    List<ProfileDTO> getProfilesLazy();

    ProfileDTO getProfile(String id);
    List<ProfileDTO> getProfiles();

    ProfileDTO addProfile(ProfileDTO profile);

    void saveLikes(String origin, List<Like> likes);

    void updateLikeToMatch(String id, String id1);
}
