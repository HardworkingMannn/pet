package com.example.Model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pet")
public class Pet {
    @TableId
    private Integer id;
    private Integer userId;
    private String name;
    private String type;
    private String sex;
    private String born;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date arrive;
}
