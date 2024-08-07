package com.arges.notforkrepolister.error;

import com.arges.notforkrepolister.service.GithubUserNotFoundException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GithubUserNotFoundException.class)
    protected ResponseEntity<Object> handeGithubUserNotFound(GithubUserNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", ex.getStatus().value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, ex.getStatus());
    }

    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<Object> handleJsonParseException(JsonParseException exception,
                                                              HttpHeaders headers,
                                                              HttpStatus status,
                                                              WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("message", exception.getMessage());

        return new ResponseEntity<>(body, headers, status);
    }
}
