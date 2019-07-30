package top.justdj.ugc.common.exception;

import org.apache.shiro.authc.AccountException;

public class ForbidLoginException extends AccountException {
    private String msg;
    public ForbidLoginException(String msg){
        this.msg=msg;
    }
}
