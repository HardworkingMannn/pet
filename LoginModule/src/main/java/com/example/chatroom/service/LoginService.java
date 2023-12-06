package com.example.chatroom.service;

import com.example.chatroom.constant.LoginConst;
import com.example.chatroom.constant.RabbitMQConst;
import com.example.chatroom.constant.UserInfoConstant;
import com.example.chatroom.entity.User;
import com.example.chatroom.exception.*;
import com.example.chatroom.mapper.LoginMapper;
import com.example.chatroom.mapper.UserInfoMapper;
import com.example.chatroom.pojo.EmailSendInfo;
import com.example.chatroom.pojo.LoginDTO;
import com.example.chatroom.pojo.ModifyPasswordDTO;
import com.example.chatroom.pojo.RegisterDTO;
import com.example.chatroom.utils.EmailSender;
import com.example.chatroom.utils.JwtUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.mail.MessagingException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class LoginService {
    @Autowired
    @Qualifier("emailPattern")
    private Pattern emailPattern;  //线程安全，可复用
    @Autowired
    private Random random;
    @Autowired
    private Jedis jedis;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpSession session;
    /*@Autowired
    private RabbitTemplate rabbitTemplate;*/
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private EmailSender emailSender;
    @Value("${jwt.existHour}")
    private Integer time;
    public void sendCode(String account) throws MessagingException {
        int code=generateCode();   //还得存储在redis中
        if(checkEmail(account)){
            String codeIdentifier= LoginConst.CODE_PREFIX+account;
            String validIdentifier=LoginConst.COOLING_OF_PREFIX+account;
            if(jedis.get(validIdentifier)==null) {
                jedis.setex(validIdentifier, LoginConst.SEND_AGAIN_TIME, "" + code);
            }else{
                log.error("{}距离上次发送验证码还没过60s",account);
                throw new CoolTimeNotPassException();
            }
            //TODO 验证码格式要完善
            EmailSendInfo emailSendInfo = new EmailSendInfo(account, code+"");
            /*rabbitTemplate.convertAndSend(RabbitMQConst.registerExchange,RabbitMQConst.emailQueueName,emailSendInfo);*/  //用消息队列发送，实现异步调用
            emailSender.sendEmail(emailSendInfo);
            log.info("{}发送验证码{},由交换机：{}，队列：{}完成",account,code,RabbitMQConst.registerExchange,RabbitMQConst.emailQueueName);
            jedis.setex(codeIdentifier,LoginConst.VALID_TIME,""+code);    //设置验证码有效期,只有通过前面冷却期的验证,邮件发送成功才能修改
        }else if(checkPhone(account)){
            //TODO 手机发送验证码
        }else{
            log.error("{}不通过手机和邮箱验证",account);
            throw new RegexException();
        }
    }
    public int generateCode(){//生成6位验证码
        return random.nextInt(900000)+100000;
    }
    public boolean checkEmail(String account){
        Matcher matcher = emailPattern.matcher(account);
        return matcher.matches();
    }
    public boolean checkPhone(String account){
        //TODO 验证手机号
        return false;
    }

    public void register(RegisterDTO registerDTO){
        String username = registerDTO.getUsername();
        boolean b = verifyCode(username, registerDTO.getCode());
        if(!b){
            log.info("{}的验证码{}校验不通过",username,registerDTO.getCode());
            throw new CodeNotPassException();
        }
        if(loginMapper.userExist(username)!=null){
            log.error("{}重复注册",username);
            throw new MultipleRegistrationException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(registerDTO.getPassword());
        loginMapper.register(user);
        log.info("{}注册成功", username);
        userInfoMapper.insertInfo(user.getId(), UserInfoConstant.NAME_PREFIX+ UUID.randomUUID().toString(),UserInfoConstant.IMAGE);
    }
    public boolean verifyCode(String username,Integer code){
        String codeIdentifier= LoginConst.CODE_PREFIX+username;
        String s = jedis.get(codeIdentifier);
        if(code==null){//可能没有传验证码
            log.error("{}传入的验证码不存在",username);
            throw new CodeNotExistException();
        }
        return code.toString().equals(s);
    }
    public void modifyPassword(ModifyPasswordDTO modifyPasswordDTO){//修改密码
        String username = modifyPasswordDTO.getUsername();
        if(loginMapper.checkPassword(username,modifyPasswordDTO.getOldPassword())==null){
            log.error("密码错误");
            throw new PasswordNotRightException();
        }
        log.info("{}修改密码",username);
        loginMapper.setPassword(username,modifyPasswordDTO.getNewPassword());
    }
    public String findPassword(String username,Integer code){
        boolean b = verifyCode(username, code);
        if(!b){
            log.info("{}的验证码{}校验不通过",username,code);
            throw new CodeNotPassException();
        }
        if(loginMapper.userExist(username)==null){
            log.error("{}错误邮箱或手机号",username);
            throw new WrongUsernameException();
        }
        return loginMapper.findPassword(username);
    }
    public User checkLogin(LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        if(loginMapper.userExist(username)==null){
            log.error("{}错误邮箱或手机号",username);
            throw new WrongUsernameException();
        }
        User user = loginMapper.checkPassword(username, loginDTO.getPassword());
        if(user ==null){
            throw new PasswordNotRightException();
        }
        return user;
    }
    public String generateToken(Integer id){
        session.setAttribute("userId",id);   //设置session
        String token = jwtUtil.generateToken(id);
        jedis.setex(token,time*60*60,""+id);   //设置在redis中，让其他共享
        return token;
    }
}
