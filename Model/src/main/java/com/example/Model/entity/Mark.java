package com.example.Model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Mark")
public class Mark {
    private Integer shared_id;
    @TableId
    private Integer id;
    private String mark;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date time;
}
