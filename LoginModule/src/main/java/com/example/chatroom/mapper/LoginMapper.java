package com.example.chatroom.mapper;

import com.example.chatroom.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {//用于注册登录之类的功能
    void register(User user);
    User userExist(String username);
    User checkPassword(String username,String password);  //检验密码是否正确
    void setPassword(String username,String newPassword);
    String findPassword(String username);

}
