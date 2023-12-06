package com.example.chatroom.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    void insertInfo(Integer userId,String userName,String userImage);
}
