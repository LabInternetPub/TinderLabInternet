package cat.tecnocampus.tinder.api.frontendException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectRESTParameter extends RuntimeException {
    public IncorrectRESTParameter(String parameter, String value) {
        super("The combination of parameter \"" + parameter + "\" and value \"" + value + "\" is incorrect");
    }
}
