package com.example.shared.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Model.entity.Mark;
import com.example.shared.mapper.MarkMapper;
import org.springframework.stereotype.Service;

@Service
public class MarkServiceImple extends ServiceImpl<MarkMapper, Mark> implements MarkService {
}
