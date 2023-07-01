package ca.ehealthsask.rmsdemo.errorHandling;

import java.time.Instant;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.Getter;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .stream().forEach(fieldError -> {
                    fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
                });
        return new ErrorResponse(fieldErrors, "form data contains errors");
    }
}

@Getter
class ErrorResponse {
    private Map<String, String> fieldErrors;
    private String message;

    public ErrorResponse(Map<String, String> errors, String message) {
        this.fieldErrors = errors;
        this.message = message;
    }

}