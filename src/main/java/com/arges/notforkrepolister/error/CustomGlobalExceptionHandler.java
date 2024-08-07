package com.arges.notforkrepolister.error;

import com.arges.notforkrepolister.web.GithubUserNotFoundException;
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
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", ex.getStatus().value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, ex.getStatus());
    }
}
