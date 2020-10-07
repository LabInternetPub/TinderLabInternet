package cat.tecnocampus.tinder.application.exception;

public class ProfileNotFoundException extends RuntimeException{
    public ProfileNotFoundException(String email)  {
        super("User " + email + " does not exist");
    }
}
