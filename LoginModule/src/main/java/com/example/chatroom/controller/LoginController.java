package com.example.chatroom.controller;

import com.example.chatroom.entity.User;
import com.example.chatroom.exception.*;
import com.example.chatroom.pojo.LoginDTO;
import com.example.chatroom.pojo.ModifyPasswordDTO;
import com.example.chatroom.pojo.RegisterDTO;
import com.example.chatroom.pojo.Result;
import com.example.chatroom.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@Tag(name="登录接口")
@Slf4j
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/sendCode")
    @Operation(summary = "获取验证码")
    public Result sendCode(String account){//可以为手机号也可以是邮箱，注册时使用，或者找回密码时使用
        log.info("{}获取验证码",account);
        try {
            loginService.sendCode(account);
        }catch(RegexException e){
            return Result.fail("邮箱或手机号没有通过验证");
        }catch(CoolTimeNotPassException e){
            return Result.fail("距离上次发送验证码还没过60s");
        }catch(MessagingException e){
            return Result.fail("邮箱发送失败");
        }
        return Result.success();
    }
    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public Result register(@RequestBody RegisterDTO registerDTO){//注册
        try {
            loginService.register(registerDTO);
        }catch(CodeNotExistException e){
            return Result.fail("验证码不存在");
        }catch(CodeNotPassException e){
            return Result.fail("验证码不通过");
        }catch(MultipleRegistrationException e){
            return Result.fail("重复注册");
        }
        return Result.success();
    }
    @PostMapping("/modifyPassword")
    @Operation(summary = "修改密码")
    public Result modifyPassword(@RequestBody ModifyPasswordDTO modifyPasswordDTO){
        try {
            loginService.modifyPassword(modifyPasswordDTO);
        }catch(PasswordNotRightException e){
            return Result.fail("密码错误");
        }
        return Result.success();
    }
    @GetMapping("/findPassword")
    @Operation(summary = "找回密码")
    public Result findPassword(String username,Integer code){//找回密码
        try{
            String password = loginService.findPassword(username, code);
            return Result.success(password);
        }catch(CodeNotExistException e){
            return Result.fail("验证码不存在");
        }catch(CodeNotPassException e){
            return Result.fail("验证码不通过");
        }catch(WrongUsernameException e){
            return Result.fail("邮箱或手机号错误");
        }
    }
    @PostMapping
    @Operation(summary = "登录")
    public Result login(@RequestBody LoginDTO loginDTO){
        try {
            User user = loginService.checkLogin(loginDTO);
            String token = loginService.generateToken(user.getId());
            return Result.success(token);
        }catch(WrongUsernameException e){
            return Result.fail("邮箱或手机号错误");
        }catch(PasswordNotRightException e){
            return Result.fail("密码错误");
        }
    }


}
