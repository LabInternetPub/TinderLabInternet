package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.domain.Proposal;

import java.util.List;

public interface ProfileDAO {

    Profile getProfileLazy(String email);
    List<Profile> getProfilesLazy();

    Profile getProfile(String email);
    List<Profile> getProfiles();

    String addProfile(Profile profile);

    void saveLikes(String origin, List<Proposal> proposals);

    void updateLikeToMatch(String email, String email1);
}
