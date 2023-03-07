package com.example.docapp.exception;

public class InvalidDateFormatException extends RuntimeException {

    public InvalidDateFormatException(String msg) {
        super(msg);
    }
}