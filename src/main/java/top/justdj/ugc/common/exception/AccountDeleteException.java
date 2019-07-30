package top.justdj.ugc.common.exception;

import org.apache.shiro.authc.AccountException;

public class AccountDeleteException extends AccountException {
    private String msg;
    public AccountDeleteException(String msg){
        this.msg=msg;
    }
}
