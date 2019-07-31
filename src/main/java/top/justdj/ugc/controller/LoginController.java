package top.justdj.ugc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.justdj.ugc.common.entity.*;
import top.justdj.ugc.common.entity.dto.CodeIdentify;
import top.justdj.ugc.common.entity.dto.CompanySignUpDTO;
import top.justdj.ugc.common.entity.dto.PersonSignUpDTO;
import top.justdj.ugc.config.shiro.JwtHelper;
import top.justdj.ugc.common.constant.ErrorConstant;
import top.justdj.ugc.common.exception.AccountDeleteException;
import top.justdj.ugc.common.exception.ForbidLoginException;
import top.justdj.ugc.common.exception.AccountExpireException;
import top.justdj.ugc.service.*;
import top.justdj.ugc.util.Md5Utils;
import top.justdj.ugc.common.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "登录注册管理",tags = "用户登录注册等 API")
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private MailService mailService;
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private CompanyInfoService companyInfoService;
    
//    @Autowired
//    private MailService mailService;

    @ApiOperation("用户登录")
    @PostMapping("/api/login/in")
    @HystrixCommand(fallbackMethod = "loginFallback")
    public Result login(@Valid Authentication authentication) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(authentication.getEmail(), authentication.getPassword());
        try {
            
            subject.login(token);
            //创建token
            UserInfo userInfo = userService.selectByEmail(authentication.getEmail());
            userInfo.setPassword(authentication.getPassword());
            Object jwt = JwtHelper.createJWT(userInfo);
            JSONObject result = new JSONObject();
            result.put("t",jwt);
            userInfo.setPassword("");
            userInfo.setSalt("");
            userInfo.setCreditRating(null);
            result.put("u",userInfo);
            return Result.ok(result);
        } catch (UnknownAccountException e) {
            //账户不存在
            return Result.error(ErrorConstant.USER_NOT_EXIST.getCode(),ErrorConstant.USER_NOT_EXIST.getMsg());
        }catch (ForbidLoginException e){
            return Result.error(ErrorConstant.ACCOUNT_STOP.getCode(),ErrorConstant.ACCOUNT_STOP.getMsg());
        }
        catch (AccountExpireException e){
            return Result.error(ErrorConstant.ACCOUNT_EXPIRE.getCode(),ErrorConstant.ACCOUNT_EXPIRE.getMsg());
        }
        catch (AccountDeleteException e){
            return Result.error(ErrorConstant.ACCOUNT_DELETE.getCode(),ErrorConstant.ACCOUNT_DELETE.getMsg());
        }
        catch (IncorrectCredentialsException e) {
            return Result.error(ErrorConstant.PASSWORD_ERROR.getCode(),ErrorConstant.PASSWORD_ERROR.getMsg());
        }
    }
    
    public Result loginFallback( Authentication authentication) {
        return Result.error(2,"登录服务忙 请稍后重试");
    }
    
    
    @ApiOperation("用户 账户是否存在 true 存在  false 不存在")
    @PostMapping("/api/login/isExist")
    @ResponseBody
    public Result<Boolean> isAccountExist(@ApiParam(required = true,example = "2269090020@qq.com",name = "用户邮箱") @RequestBody String email){
        UserInfo userInfo = userService.selectByEmail(email);
        return Result.ok(ObjectUtils.allNotNull(userInfo));
    }
    
    
    @ApiOperation("用户注销")
    @PostMapping("/api/login/out")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.ok("注销成功");
    }
    
    
    @ApiOperation("个人用户注册")
    @PostMapping("/api/login/register/person")
    public Result personRegister(@RequestBody @Valid PersonSignUpDTO signUpDTO){
        // TODO: 19.3.3 如果真实使用的话 我感觉这里应该再次验证一下验证码
        log.info("注册数据 {}", JSON.toJSONString(signUpDTO));
        if (ObjectUtils.allNotNull(userService.selectByEmail(signUpDTO.getEmail()))){
            return Result.error(ErrorConstant.ACCOUNT_ALREADY_EXIT.getCode(),ErrorConstant.ACCOUNT_ALREADY_EXIT
                    .getMsg());
        }else {
            userService.saveUser(signUpDTO);
            Authentication authentication = new Authentication();
            authentication.setEmail(signUpDTO.getEmail());
            authentication.setPassword(signUpDTO.getPassword());
            return login(authentication);
        }
    }
    
    
    @ApiOperation("公司注册")
    @PostMapping("/api/login/register/company")
    public Result companyRegister(@RequestBody @Valid CompanySignUpDTO signUpDTO){
        // TODO: 19.3.3 如果真实使用的话 我感觉这里应该再次验证一下验证码
        log.info("注册数据 {}", JSON.toJSONString(signUpDTO));
        if (ObjectUtils.allNotNull(userService.selectByEmail(signUpDTO.getEmail()))){
            return Result.error(ErrorConstant.ACCOUNT_ALREADY_EXIT.getCode(),ErrorConstant.ACCOUNT_ALREADY_EXIT.getMsg());
        }else {
            userService.saveUser(signUpDTO,   companyInfoService.saveCompany(signUpDTO).getId());
            Authentication authentication = new Authentication();
            authentication.setEmail(signUpDTO.getEmail());
            authentication.setPassword(signUpDTO.getPassword());
            return login(authentication);
        }
    }
    
    
//    @ApiOperation("忘记密码")
//    @PostMapping("/forget")
//    public Result forgerPassword(@RequestBody Email e) {
//        String email=e.getEmail();
//        if (email==null || email.equals("")) {
//            return Result.error("邮箱不能为空");
//        }
//        User user = userService.getUserByEmail(email);
//        if (user==null) {
//            return Result.error("用户不存在");
//        }
//        Mail mail=new Mail();
//        Integer code = CacheUtils.generaterCode();
//        CacheUtils.saveCode(user.getUsername(),code);
//        mail.setSubject("验证码");
//        mail.setText("欢迎使用新大新ERP系统，您的验证码是:"+String.valueOf(code));
//        mail.setTo(user.getEmail());
//        mailService.sendSimpleMail(mail);
//        return Result.ok(new SimpleUser(user.getId(),user.getUsername(),user.getEmail()));
//    }
//    @ApiOperation("/验证码验证")
//    @PostMapping("/code")
//    public Result checkCode(@RequestBody CheckCode checkCode) {
//        if (CacheUtils.verify(checkCode.getUsername(),checkCode.getCode())){
//            return Result.ok("验证成功");
//        }
//        CacheUtils.remove(checkCode.getUsername());
//        return Result.ok("验证失败");
//    }
//
    @ApiOperation("/修改密码")
    @PatchMapping("/tApi/login/changePassword")
    public Result resetPassword(@RequestBody ChangePassword changePassword,
                                HttpServletRequest request) {
        UserInfo userInfo = getLatestUserInfo(request);
       if (Md5Utils.encryption(changePassword.getOldPassword(),userInfo.getSalt()).equals(userInfo.getPassword())){
           userInfo.setPassword(Md5Utils.encryption(changePassword.getNewPassword(),userInfo.getSalt()));
           userService.saveOrUpdate(userInfo);
           return Result.ok("修改成功");
       }else {
           return Result.error("原密码错误");
       }
      
    }
    
    @ApiOperation(value = "发送验证码",notes = "发送验证码")
    @PostMapping("/api/login//code/send")
    public Result getCode(@ApiParam(required = true,example = "977878634@qq.com") @RequestBody String email,
            HttpServletRequest request){
        log.info("即将发送邮件 邮箱 {}",email);
        String sessionId = request.getSession().getId();
       String result =  redisService.get(email+"/code/email");
       if (StringUtils.isEmpty(result)){
           final Long time = 5 * 60L;
           String code = org.apache.commons.lang3.RandomStringUtils.randomNumeric(6);
           redisService.setAndExpire(email +"_signUpCode",code,time);
           redisService.setAndExpire(email+"/code/email",System.currentTimeMillis()+"",60L);
           mailService.sendSimpleMail(email,"校园兼职注册验证码(测试)",code + " 以上为您的注册验证码,五分钟内有效。您正在尝试注册校园兼职系统,如非本人请忽略。");
           return Result.ok();
       }else {
           return Result.error("发送频率过高!");
       }
    }

    
    @PostMapping("/api/login//code/check/")
    @ApiOperation(value = "检查验证码是否正确 true正确",notes ="检查验证码是否正确" )
    public Result checkCode(@RequestBody @Valid CodeIdentify code,
                          HttpServletRequest request){
        String sessionId = request.getSession().getId();
        String result = redisService.get(code.getEmail() +"_signUpCode");
        return Result.ok(code.getCode().equals(result));
    }
}
