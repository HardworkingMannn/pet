package com.example.chatroom.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;  //状态码，代表是否成功,0代表未成功，1代表成功
    private String message;    //返回信息
    private Object result;           //结果对象
    public static Result  success(Object o){
        return new Result(1,"success",o);
    }
    public static Result fail(String message){  //失败时不返回对象，返回错误的状态码
        return new Result(0,message,null);
    }
    public static Result success(){
        return new Result(1,"success",null);
    }
}
