package com.example.exception;

public class UserExistedException extends RuntimeException {
    public UserExistedException(String message) { 
        super(message);
    }
}
