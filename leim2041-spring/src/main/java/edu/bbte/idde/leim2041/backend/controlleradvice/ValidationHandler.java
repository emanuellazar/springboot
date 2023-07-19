package edu.bbte.idde.leim2041.backend.controlleradvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Stream;

@Slf4j
@ControllerAdvice
public class ValidationHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Stream<String> handleMethodeArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("Not valid", methodArgumentNotValidException);
        return methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(fieldError ->
                fieldError.getField() + " " + fieldError.getDefaultMessage());
    }
}

