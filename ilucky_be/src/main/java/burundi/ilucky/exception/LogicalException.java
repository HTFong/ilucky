package burundi.ilucky.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LogicalException extends RuntimeException {

    public LogicalException(String resourceName, String methodName, String message) {
        super(String.format("[%s] cause logical in [%s]: %s", resourceName, methodName, message));
    }

}
