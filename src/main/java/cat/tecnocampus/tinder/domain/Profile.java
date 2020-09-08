package cat.tecnocampus.tinder.domain;


import cat.tecnocampus.tinder.utilities.InvalidParameterException;

import java.util.ArrayList;
import java.util.List;

public class Profile {

	public enum Gender {Man, Woman, Indefinite, Bisexual}
	public enum Passion {Sport, Music, Walk, Dance}

	private String email;
	private String nickname;
	private String password;
	private Gender gender;
	private Gender attraction;
	private Passion passion;

	private List<Proposal> candidates = new ArrayList<>();

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

	public List<Proposal> getCandidates() {
		return candidates;
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

	public void setCandidates(List<Proposal> proposals) {
		this.candidates = proposals;
	}

	public boolean getCompatibility(Profile user) {
		if (this.isAttracted(user)) {
			if (user.getPassion() == this.passion)
				return true;
		}
		return false;

	}

	private boolean isAttracted(Profile user) {
		if (user.getGender() == this.attraction)
			return true;
		return this.attraction == Gender.Bisexual;
	}

}
