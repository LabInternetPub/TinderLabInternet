package cat.tecnocampus.tinder.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE, reason="Invalid parameters")
public class InvalidParameterException extends Exception {

	public InvalidParameterException() {
		
	}
}
