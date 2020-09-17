package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.domain.Like;

import java.util.List;

public interface ProfileDAO {

    Profile getProfileLazy(String id);
    List<Profile> getProfilesLazy();

    Profile getProfile(String id);
    List<Profile> getProfiles();

    Profile addProfile(Profile profile);

    void saveLikes(String origin, List<Like> likes);

    void updateLikeToMatch(String id, String id1);
}
