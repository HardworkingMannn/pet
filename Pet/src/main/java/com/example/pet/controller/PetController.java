package com.example.pet.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.Model.entity.Pet;
import com.example.Model.pojo.Result;
import com.example.pet.service.PetService;
import com.example.utils.JedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name="和宠物相关的接口")
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    private static final Jedis jedis = JedisUtil.getJedis();
    @PostMapping("/create")
    @Operation(summary = "创建宠物，不要填写id")
    public Result create(@RequestBody Pet pet,@RequestHeader("token") String token){
        Integer userId = Integer.parseInt(jedis.get(token));
        if(pet.getId()!=null){
            pet.setId(null);
        }
        pet.setUserId(userId);
        petService.save(pet);
        return Result.success();
    }
    @PostMapping("/postImg")
    @Operation(summary = "上传图片，返回图片链接")
    public Result<String> postImg(MultipartFile file) throws IOException {
        String s = petService.saveImg(file);
        return Result.success(s);
    }
    @GetMapping("/getPet")
    @Operation(summary = "获取所有宠物")
    public Result<List<Pet>> getPet(@RequestHeader("token") String token){
        Integer userId = Integer.parseInt(jedis.get(token));
        List<Pet> list = petService.list(Wrappers.<Pet>lambdaQuery().eq(Pet::getUserId, userId));
        return Result.success(list);
    }
}
