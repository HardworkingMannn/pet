package com.example.shared.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Model.constant.FileConst;
import com.example.Model.entity.Share;
import com.example.shared.mapper.SharedMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class SharedServiceImpl extends ServiceImpl<SharedMapper, Share> implements SharedService{

    @Override
    public String saveImg(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.indexOf(".") + 1);
        String name= UUID.randomUUID()+ext;
        String localName=FileConst.FILE_STORE_PREFIX+name;
        File file1 = new File(localName);
        if(!file1.exists()){
            file1.createNewFile();
        }
        file.transferTo(file1);
        String remoteName=FileConst.FILE_ACCESS_PREFIX+name;
        return remoteName;
    }
}
