package com.example.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Model.entity.Pet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PetService extends IService<Pet> {
    String saveImg(MultipartFile file) throws IOException;
}
