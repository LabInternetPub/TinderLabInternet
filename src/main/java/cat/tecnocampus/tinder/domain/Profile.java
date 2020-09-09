package cat.tecnocampus.tinder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Profile {
	public enum Gender {Man, Woman, Indefinite, Bisexual}
	public enum Passion {Sport, Music, Walk, Dance}

	private String email;
	private String nickname;
	private String password;
	private Gender gender;
	private Gender attraction;
	private Passion passion;

	private List<Proposal> likes = new ArrayList<>();

	public Profile() {

	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public Gender getGender() {
		return gender;
	}

	public Gender getAtraction() {
		return attraction;
	}

	public Passion getPassion() {
		return passion;
	}

	public List<Proposal> getLikes() {
		return likes;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setLikes(List<Proposal> proposals) {
		this.likes = proposals;
	}

	public void addLikes(List<Proposal> proposals) {
		this.likes.addAll(proposals);
	}

	public boolean isCompatible(Profile user) {
		if (user.getEmail().equals(this.email)) //to avoid narcicists
			return false;
		return (user.getGender() == this.getAtraction() || this.attraction == Gender.Bisexual) && user.getPassion() == this.passion;
	}

	public boolean likes(Profile target) {
		return this.getLikes().stream().anyMatch(l -> l.getTarget().equals(target.getEmail()));
	}

	public void setMatch(Profile target) {
		Optional<Proposal> proposal = this.getLikes().stream().filter(l -> l.getTarget().equals(target.getEmail())).findFirst();
		if (proposal.isPresent())
			proposal.get().setMatched(true);
	}


}
