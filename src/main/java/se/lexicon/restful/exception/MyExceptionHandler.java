package se.lexicon.restful.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice // Handle all global exceptions
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @Override // From ResponseEntityExceptionHandler. @@ExceptionHandler is not used here because it is not custom exception as in the next exception.
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, "Malformed JSON request!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(DataNotFoundException.class) // Used to handle custom exceptions.
    public ResponseEntity<Object> dataNotFound(DataNotFoundException exception){
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError); //HttpStatus.BAD_REQUEST = 400
    }

    @ExceptionHandler(DataDuplicateException.class) // Used to handle custom exceptions.
    public ResponseEntity<Object> dataDuplicate(DataDuplicateException exception){
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError); //HttpStatus.BAD_REQUEST = 400
    }

    @ExceptionHandler(IllegalArgumentException.class) // Used to handle custom exceptions.
    public ResponseEntity<Object> illegalArgument(IllegalArgumentException exception){
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError); //HttpStatus.BAD_REQUEST = 400
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolation(ConstraintViolationException exception){
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalException(Exception exception){
        String errorCode = UUID.randomUUID().toString();
        APIError apiError = new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "internal error! code: (" + errorCode + ")" + " Call support team!");
        System.out.println("INTERNAL_ERROR" + "," + errorCode + "," + exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
