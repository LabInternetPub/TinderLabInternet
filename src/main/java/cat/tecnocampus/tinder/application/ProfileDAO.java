package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.domain.Profile;

import java.util.List;

public interface ProfileDAO {

    Profile getProfileLazy(String email);
    List<Profile> getProfilesLazy();

    Profile getProfile(String email);
    List<Profile> getProfiles();

}
