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

	private List<Profile> candidates = new ArrayList<>();

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

	public List<Profile> getCandidates() {
		return this.candidates;
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

	public boolean addCandidate(Profile candidate) throws InvalidParameterException {

		checkNotRepeatedCandidate(candidate);

		this.candidates.add(candidate);

		return candidate.hasLiked(this);
	}

	private void checkNotRepeatedCandidate(Profile candidate) throws InvalidParameterException {
		if (hasLiked(candidate)) {
			throw new InvalidParameterException();
		}
	}

	private boolean hasLiked(Profile candidate) {
		return this.candidates.contains(candidate);
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
