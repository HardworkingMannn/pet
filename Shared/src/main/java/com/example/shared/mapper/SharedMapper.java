package com.example.shared.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Model.entity.Share;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SharedMapper extends BaseMapper<Share> {
}
