package com.example.chatroom.pojo;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private Integer code;
}
