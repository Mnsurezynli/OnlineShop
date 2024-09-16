package application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public ResponseEntity<String>handleInvalidInputException(InvalidInputException ex){
    return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

     @ExceptionHandler(ResourceAlreadyExistsException.class)
     @ResponseBody
     public ResponseEntity<String>handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
    return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

}
