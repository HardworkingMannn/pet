package com.example.chatroom.exception;

public class RegexException extends RuntimeException{//邮箱或手机验证错误时
    public RegexException() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
