package telran.java57.forum.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserAccountNotFoundException extends RuntimeException{
    public UserAccountNotFoundException(String message) {
        super("Post with id "+message+" not found");
    }
}
