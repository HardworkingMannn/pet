package com.example.RecordManagement.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.Model.constant.TypeConstant;
import com.example.Model.entity.Record;
import com.example.Model.pojo.Result;
import com.example.RecordManagement.service.ServiceImpl.RecordService;
import com.example.utils.JedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("/record")
@Tag(name="备忘录相关接口")
public class RecordController {
    @Autowired
    private RecordService recordService;
    private final static Jedis jedis= JedisUtil.getJedis();
    @GetMapping("/findNotification")
    @Operation(summary = "查找所有提醒")
    public Result<List<Record>> findNotification(@RequestHeader("token") String token){
        Record record = new Record();
        Integer userId = Integer.parseInt(jedis.get(token));
        record.setUserId(userId);
        List<Record> list = recordService.list(Wrappers.<Record>lambdaQuery().eq(Record::getUserId, record.getUserId()).eq(Record::getType, TypeConstant.NOTIFY_TYPE));
        return Result.success(list);
    }
    @GetMapping("/findRecord")
    @Operation(summary = "查找所有记录")
    public Result<List<Record>> findRecord(){
        Record record = new Record();
        record.setUserId(1);
        List<Record> list = recordService.list(Wrappers.<Record>lambdaQuery().eq(Record::getUserId, record.getUserId()).eq(Record::getType, TypeConstant.RECORD_TYPE));
        return Result.success(list);
    }
    @PostMapping("/insertRecord")
    @Operation(summary = "插入记录或者提醒")
    public Result insertRecord(@RequestBody Record record,@RequestHeader("token") String token){
        Integer userId = Integer.parseInt(jedis.get(token));
        record.setUserId(userId);
        boolean save = recordService.save(record);
        return save?Result.success():Result.fail("保存失败");
    }
    @GetMapping("/deleteRecord")
    @Operation(summary = "删除")
    public Result deleteRecord(Integer id){
        boolean b = recordService.removeById(id);
        return b?Result.success():Result.fail("删除失败");
    }

}
