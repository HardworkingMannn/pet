package com.example.chatroom.pojo;

import lombok.Data;

@Data
public class ModifyPasswordDTO {  //修改密码
    private String username;
    private String oldPassword;
    private String newPassword;
}
