package com.example.Model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    public static Result success(){
        return new Result(1,"success",null);
    }
    public static <T> Result success(T t){
        return new Result(1,"success",t);
    }
    public static Result fail(String message){
        return new Result(0,message,null);
    }

}
