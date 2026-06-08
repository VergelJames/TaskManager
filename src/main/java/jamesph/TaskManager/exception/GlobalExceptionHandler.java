package jamesph.TaskManager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
    ErrorResponse body = new ErrorResponse(UUID.randomUUID().toString(),
        ex.getErrorCode(),
        ex.getMessage(),
        ex.getStatus().value(),
        ex.getStatus().getReasonPhrase(),
        ex.getTimestamp(),
        ex.getDetails(),
        request.getRequestURI());
    return ResponseEntity.status(ex.getStatus()).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    BindingResult br = ex.getBindingResult();

    Map<String, String> fieldErrors = new HashMap<>();
    for (FieldError fe : br.getFieldErrors()) {
      fieldErrors.put(fe.getField(), fe.getDefaultMessage());
    }

    ErrorResponse body = new ErrorResponse(UUID.randomUUID().toString(),
        "VALIDATION ERROR", "Validation failed", HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(), Instant.now(), Map.of("fieldErrors", fieldErrors),
        request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  // @ExceptionHandler(Exception.class)
  // public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
  //   ErrorResponse body = new ErrorResponse(UUID.randomUUID().toString(),
  //       "INTERNAL ERROR",
  //       ex.getMessage() == null ? "Unexpected ERROR" : ex.getMessage(),
  //       HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
  //       Instant.now(),
  //       Map.of("exception",
  //           ex.getClass().getName()),
  //       request.getRequestURI());

  //   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  // }
}
