package io.github.iamzaidsheikh.sprint.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ResponseEntity<Error> exception(ResourceNotFoundException e) {
    var error = new Error(HttpStatus.NOT_FOUND, e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity<Error> exception(BadRequestException e) {
    var error = new Error(HttpStatus.BAD_REQUEST, e.getMessage());
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(value = UsernameAlreadyExistsException.class)
  public ResponseEntity<Error> exception(UsernameAlreadyExistsException e) {
    var error = new Error(HttpStatus.CONFLICT, e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    String errorMessage = fieldErrors.get(0).getDefaultMessage();
    return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, errorMessage));
  }
}
