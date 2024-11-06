package burundi.ilucky.exception;

import burundi.ilucky.constants.GlobalConstant;
import burundi.ilucky.model.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        String errorMsg = validationErrors.entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.joining(", "));
        ErrorResponse errorResponse = new ErrorResponse(request.getDescription(false), GlobalConstant.STATUS_400, errorMsg, new Date());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(request.getDescription(false), GlobalConstant.STATUS_500, exception.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                         WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(request.getDescription(false), GlobalConstant.STATUS_404, exception.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleObjectAlreadyExistsException(ObjectAlreadyExistsException exception,
                                                                            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(request.getDescription(false), GlobalConstant.STATUS_404, exception.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(LogicalException.class)
    public ResponseEntity<ErrorResponse> handleLogicalException(LogicalException exception,
                                                                WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(request.getDescription(false), GlobalConstant.STATUS_409, exception.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationServiceException(AuthenticationServiceException exception,
                                                                WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(request.getDescription(false), GlobalConstant.STATUS_403, exception.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
