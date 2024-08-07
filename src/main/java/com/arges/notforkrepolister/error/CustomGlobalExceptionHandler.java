package com.arges.notforkrepolister.error;

import com.arges.notforkrepolister.service.GithubUserNotFoundException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GithubUserNotFoundException.class)
    protected ResponseEntity<Object> handeGithubUserNotFound(GithubUserNotFoundException ex) {
        return handleException(ex, ex.getStatus());
    }

    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<Object> handleJsonParseException(JsonParseException ex, HttpStatus status) {
        return handleException(ex, status);
    }

    private ResponseEntity<Object> handleException(Exception ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
