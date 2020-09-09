package cat.tecnocampus.tinder.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO : Move responseStatus from here (create another exception in the api layer)
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not found")
public class ProfileNotFound extends RuntimeException{
    public ProfileNotFound(String email)  {
        super("User " + email + " does not exist");
    }
}
