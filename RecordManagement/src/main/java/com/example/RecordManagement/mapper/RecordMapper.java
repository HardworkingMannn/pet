package com.example.RecordManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Model.entity.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
