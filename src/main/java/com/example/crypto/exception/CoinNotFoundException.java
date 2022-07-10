package com.example.crypto.exception;

public class CoinNotFoundException extends RuntimeException{

    public CoinNotFoundException(String message) {
        super(message);
    }
}
