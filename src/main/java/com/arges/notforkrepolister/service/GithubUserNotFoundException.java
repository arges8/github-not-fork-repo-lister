package com.arges.notforkrepolister.service;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

@Getter
public class GithubUserNotFoundException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public GithubUserNotFoundException(String userLogin) {
        this.status = HttpStatus.NOT_FOUND;
        this.message = MessageFormat.format("Github user {0} not found", userLogin);
    }
}
