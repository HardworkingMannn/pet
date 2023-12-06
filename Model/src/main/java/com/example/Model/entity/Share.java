package com.example.Model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("share")
public class Share {
    @TableId
    private Integer id;
    private String activityName;
    private String content;
    private String location;
    private String image;
    private Integer likes;
    private Integer marks;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date time;
}
