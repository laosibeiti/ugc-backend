package top.justdj.ugc.config;

import com.mongodb.MongoSocketReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.justdj.common.constant.ErrorConstant;
import top.justdj.common.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;



@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局捕获AuthorizationException异常，并进行相应处理
     */
    @ExceptionHandler({AuthorizationException.class})
    @ResponseBody
    public Result handleException(Exception e){
        return Result.error("您没有权限");
    }
    

    /**
     * 用于处理入参校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer errorMessage = new StringBuffer("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField() + " " + fieldError.getDefaultMessage()).append(", ");
        }
        return Result.error(ErrorConstant.PARAM_WRONG.getCode(), errorMessage.toString());
    }
    
    /**
     * 处理请求单个参数不满足校验规则的异常信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException exception) {
        
        log.info(exception.getMessage());
        return Result.error(exception.getMessage());
        
    }
    
    @ExceptionHandler(value = MongoSocketReadTimeoutException.class)
    public Result mongoDbError(HttpServletRequest request,Exception e){
        log.info(e.getMessage());
        return Result.error(-1,"数据库连接错误");
    }
    
    
    /**
     * 处理未定义的其他异常信息
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error(e.getMessage());
        
    }
    
}