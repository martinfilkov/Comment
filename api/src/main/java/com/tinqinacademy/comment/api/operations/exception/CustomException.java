package com.tinqinacademy.comment.api.operations.exception;

public abstract class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
