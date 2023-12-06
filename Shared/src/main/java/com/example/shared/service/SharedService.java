package com.example.shared.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Model.entity.Share;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SharedService extends IService<Share> {
    String saveImg(MultipartFile file) throws IOException;
}
