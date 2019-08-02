package top.justdj.ugc.common.constant;

import lombok.Getter;

/**
 * 错误返回常量类
 *
 * @author ling
 * @date 2018/6/26
 */
@Getter
public enum  ErrorConstant {
    
    
    /**
     * 停用
     */
    SUCCESS(200, "请求成功"),
    /**
     * 账户相关
     */
    USER_NOT_EXIST(-1, "用户不存在"),
    
    ACCOUNT_EXPIRE(-1,"账号过期"),
    
    ACCOUNT_STOP(-1,"账号停用"),
    
    ACCOUNT_DELETE(-1,"账号删除"),
    
    PASSWORD_ERROR(-1,"密码错误"),
    
    ACCOUNT_ALREADY_EXIT(-1,"账户已存在"),
    
    /**
     * 伞相关的常量
     */
    NO_UMBRELLA(-1,"伞不存在"),
    
    NO_VOLUNTEER(-1,"志愿者不存在"),
    
    NOT_NORMAL_STATUS(-1,"伞 非正常状态"),
    /**
     * 接口参数
     */
    PARAM_WRONG(400, "请求参数错误"),
    
    /**
     * token相关
     */
    NO_TOKEN(2, "token校验失败"),
    
    TOKEN_EXPIRE(2, "token过期"),
    
    UNKNOWN_ERROR(500, "未知异常 请联系dj"),
    
    NO_PERMISSION(2,"没有该接口的访问权限"),
    
    UMBRELLA_NOT_RETURN(-1,"当前用户有伞未归还");
    

    
    
    private int code;
    private String msg;
    
    ErrorConstant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    
    
    public static final Integer ERROR_WRONG_ACCOUNT = 203;
    public static final String ERROR_WRONG_ACCOUNT_MSG = "账号密码错误";
    public static final Integer ERROR_NOT_LOGIN = 204;
    public static final String ERROR_NOT_LOGIN_MSG = "尚未登录，请登录!";
    public static final Integer ERROR_COMMON = 205;
    public static final String ERROR_COMMON_MSG = "错误";
    public static final Integer ERROR_WRONG_ACCOUNT_FORBIDDEN = 207;
    public static final String ERROR_WRONG_ACCOUNT_FORBIDDEN_MSG = "该账号已被禁用,请联系管理员";
    public static final Integer FORBIDDEN = 401;
    public static final String FORBIDDEN_MSG = "forbidden!";
    public static final Integer ERROR_PAGE_NOT_FOUND = 404;
    public static final String ERROR_PAGE_NOT_FOUND_MSG = "接口不存在";
    public static final Integer ERROR_EXCEPTION = 500;
    public static final String ERROR_EXCEPTION_MSG = "未知异常，请联系管理员";

    /**
     * 登陆相关
     */
    public static final Integer ERROR_PERMISSION_DENIED = 1000;
    public static final String ERROR_PERMISSION_DENIED_MSG = "没有权限，请联系管理员授权";

    public static final Integer ERROR_SYS_USER_AUTHENTICATION_OVERDUE = 1001;
    public static final String ERROR_SYS_USER_AUTHENTICATION_OVERDUE_MSG = "登陆认证过期";

    public static final Integer ERROR_SYS_USER_NICKNAME = 1002;
    public static final String ERROR_SYS_USER_NICKNAME_MSG = "账号名称重复，请重新填写";

    /**
     * 角色相关
     */
    public static final Integer ERROR_SYS_ROLE_DATA_EMPTY = 1601;
    public static final String ERROR_SYS_ROLE_DATA_EMPTY_MSG = "该查询条件下无数据";
    public static final Integer ERROR_SYS_ROLE_INSERT_FAIL = 1602;
    public static final String ERROR_SYS_ROLE_INSERT_FAIL_MSG = "角色名重复";
    public static final Integer ERROR_SYS_ROLE_UPDATE_FAIL = 1603;
    public static final String ERROR_SYS_ROLE_UPDATE_FAIL_MSG = "修改失败";
    public static final Integer ERROR_SYS_ROLE_DELETE_FAIL = 1604;
    public static final String ERROR_SYS_ROLE_DELETE_FAIL_MSG = "删除失败,该角色下仍有用户";
    public static final Integer ERROR_SYS_ROLE_LEAST_ONE_PERMISSION_REQUIRED = 1605;
    public static final String ERROR_SYS_ROLE_LEAST_ONE_PERMISSION_REQUIRED_MSG = "至少选择一项权限";

    public static final Integer DATABASE_OPER_ERROR = 5500;

    /**
     * 请求参数相关
     */
    public static final Integer ERROR_REQUIRED_PARAM_NONSTANDARD = 5100;
    public static final String ERROR_REQUIRED_PARAM_NONSTANDARD_MSG = "请求参数不符合规范";
    public static final Integer PARAM_NULL_CODE = 5101;
    public static final String PARAM_NULL_CODE_MSG = "参数不能为空";

    /**
     * 日期相关
     */
    public static final Integer ERROR_DATE_FORMAT = 5200;
    public static final String ERROR_DATE_FORMAT_MSG = "日期参数格式不正确";

    public static final Integer DATA_NOT_EXIST = 5400;
    public static final String DATA_NOT_EXIST_MSG = "数据不存在";

    /**
     * 数据库相关
     */
    public static final Integer ERROR_DATABASE_DUPLICATE_KEY = 6000;
    public static final String ERROR_DATABASE_DUPLICATE_KEY_MSG = "数据库中已存在该记录";


    public static final Integer ERROR_UPLOAD_SIZE_EXCEEDED = 7000;
    public static final String ERROR_UPLOAD_SIZE_EXCEEDED_MSG = "上传文件过大";


}
