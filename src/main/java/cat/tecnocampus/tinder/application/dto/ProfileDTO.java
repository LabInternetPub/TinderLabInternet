package cat.tecnocampus.tinder.application.dto;

import cat.tecnocampus.tinder.domain.Like;
import cat.tecnocampus.tinder.domain.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileDTO {
    private String id;
    private String email;
    private String nickname;
    private Profile.Gender gender;
    private Profile.Gender attraction;
    private Profile.Passion passion;

    private List<Like> likes = new ArrayList<>();

    public ProfileDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Profile.Gender getGender() {
        return gender;
    }

    public void setGender(Profile.Gender gender) {
        this.gender = gender;
    }

    public Profile.Gender getAttraction() {
        return attraction;
    }

    public void setAttraction(Profile.Gender attraction) {
        this.attraction = attraction;
    }

    public Profile.Passion getPassion() {
        return passion;
    }

    public void setPassion(Profile.Passion passion) {
        this.passion = passion;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}
