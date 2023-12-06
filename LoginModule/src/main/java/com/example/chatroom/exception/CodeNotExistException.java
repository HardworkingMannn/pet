package com.example.chatroom.exception;

public class CodeNotExistException extends RuntimeException{
    public CodeNotExistException(String message) {
        super(message);
    }

    public CodeNotExistException() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
