package top.justdj.common.exception;

import org.apache.shiro.authc.AccountException;

public class AccountExpireException extends AccountException {
    private String msg;
    public AccountExpireException(String msg){
        this.msg=msg;
    }
}
