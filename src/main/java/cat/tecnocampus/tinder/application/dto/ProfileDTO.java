package cat.tecnocampus.tinder.application.dto;

import cat.tecnocampus.tinder.domain.Profile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ProfileDTO {
    private String id;

    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b",
            message = "Email must look like an email")
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min=5, max=10)
    private String nickname;
    private Profile.Gender gender;
    private Profile.Gender attraction;
    private Profile.Passion passion;

    private List<LikeDTO> likes = new ArrayList<>();

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

    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }
}
