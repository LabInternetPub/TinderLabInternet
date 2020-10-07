package cat.tecnocampus.tinder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Profile {
	public enum Gender {Man, Woman, Indefinite, Bisexual}
	public enum Passion {Sport, Music, Walk, Dance}

	private String id;
	private String email;
	private String nickname;
	private Gender gender;
	private Gender attraction;
	private Passion passion;

	private List<Like> likes = new ArrayList<>();

	public Profile() {

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

	public String getNickname() {
		return nickname;
	}

	public Gender getGender() {
		return gender;
	}

	public Gender getAttraction() {
		return attraction;
	}

	public Passion getPassion() {
		return passion;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setAttraction(Gender attraction) {
		this.attraction = attraction;
	}

	public void setPassion(Passion passion) {
		this.passion = passion;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public boolean isCompatible(Profile user) {
		if (user.getId().equals(this.id)) //to avoid narcicists
			return false;
		return (user.getGender() == this.getAttraction() || this.attraction == Gender.Bisexual) && user.getPassion() == this.passion;
	}

	public boolean likes(Profile target) {
		return this.getLikes().stream().anyMatch(l -> l.getTarget().getId().equals(target.getId()));
	}

	public void setMatch(Profile target) {
		Optional<Like> like = this.getLikes().stream().filter(l -> l.getTarget().getId().equals(target.getId())).findFirst();
		like.ifPresent(value -> value.setMatched(true));
	}

	//Target must be compatible
	// 1.- Create like
	// 2.- Set like to match if it does
	public Like createAndMatchLike(Profile target) {
		Like like = new Like(target);
		if (target.likes(this)) {
			like.setMatched(true);  	//origin set to match
			target.setMatch(this);		//target set to match
		}
		this.likes.add(like);
		return like;
	}


}
