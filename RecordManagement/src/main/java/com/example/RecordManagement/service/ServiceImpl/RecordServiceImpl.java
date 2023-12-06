package com.example.RecordManagement.service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Model.entity.Record;
import com.example.RecordManagement.mapper.RecordMapper;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService{

}
