package com.example.pet.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Model.constant.FileConst;
import com.example.Model.entity.Pet;
import com.example.pet.mapper.PetMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

    @Override
    public String saveImg(MultipartFile file) throws IOException {
            String filename = file.getOriginalFilename();
            String ext = filename.substring(filename.indexOf(".") + 1);
            String name= UUID.randomUUID()+ext;
            String localName= FileConst.FILE_STORE_PREFIX+name;
            File file1 = new File(localName);
            if(!file1.exists()){
                file1.createNewFile();
            }
            file.transferTo(file1);
            String remoteName=FileConst.FILE_ACCESS_PREFIX+name;
            return remoteName;
    }
}
