package top.justdj.ugc.common.exception;

import org.apache.shiro.authc.AccountException;

public class NormalException extends AccountException {
    private String msg;
    public NormalException(String msg){
        this.msg=msg;
    }
}
