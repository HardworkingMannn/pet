package com.example.shared.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.Model.entity.Mark;
import com.example.Model.entity.Share;
import com.example.Model.pojo.Result;
import com.example.shared.service.MarkService;
import com.example.shared.service.SharedService;
import com.example.shared.service.SharedServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name="处理朋友圈相关的接口")
@RequestMapping("/share")
public class SharedController {
    @Autowired
    private SharedServiceImpl sharedService;
    @Autowired
    private MarkService markService;
    @GetMapping("/getAll")
    @Operation(summary = "获取所有")
    public Result<List<Share>> getAll(){
        return Result.success(sharedService.list());
    }
    @PostMapping("/postImg")
    @Operation(summary = "上传图片，返回图片链接")
    public Result<String> postImg(MultipartFile file) throws IOException {
        String s = sharedService.saveImg(file);
        return Result.success(s);
    }
    @PostMapping("/postShared")
    @Operation(summary = "上传朋友圈，注意id不要设置，只设置sharedid")
    public Result postShared(@RequestBody Share share){
        if(share.getId()!=null){
            share.setId(null);
        }
        sharedService.save(share);
        return Result.success();
    }
    @GetMapping("/like")
    @Operation(summary = "点赞")
    public Result like(Integer id){
        Share share = sharedService.getById(id);
        Integer likes = share.getLikes();
        if(likes==null){
            likes=0;
        }
        likes++;
        share.setLikes(likes);
        sharedService.updateById(share);
        return Result.success();
    }
    @GetMapping("/getMark")
    @Operation(summary = "获取所有评论")
    public Result<List<Mark>> getMark(Integer sharedId){
        List<Mark> marks = markService.list(Wrappers.<Mark>lambdaQuery().eq(Mark::getShared_id,sharedId));
        return Result.success(marks);
    }
    @PostMapping("/postMark")
    @Operation(summary = "上传评论，注意id不要设置，只设置sharedid")
    public Result postMark(@RequestBody Mark mark){
        if(mark.getId()!=null){
            mark.setId(null);
        }
        markService.save(mark);
        return Result.success();
    }
}
