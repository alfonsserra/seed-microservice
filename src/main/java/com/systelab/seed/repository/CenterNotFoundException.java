package com.systelab.seed.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CenterNotFoundException extends RuntimeException {

    private final String id;

    public CenterNotFoundException(String id) {
        super("center-not-found-" + id.toString());
        this.id = id.toString();
    }

    public String getCenterId() {
        return id;
    }
}

