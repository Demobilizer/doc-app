package com.example.docapp.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}