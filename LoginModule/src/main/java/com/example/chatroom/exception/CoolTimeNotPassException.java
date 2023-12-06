package com.example.chatroom.exception;

public class CoolTimeNotPassException extends RuntimeException{//发送验证冷却时间未到
    public CoolTimeNotPassException() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
