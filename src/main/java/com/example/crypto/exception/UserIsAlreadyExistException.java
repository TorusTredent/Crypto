package com.example.crypto.exception;

public class UserIsAlreadyExistException  extends RuntimeException {

    public UserIsAlreadyExistException(String message) {
        super(message);
    }
}
