package top.justdj.common.constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.18
 * Time: 22:13
 */
@Getter
public enum UserSourceConstant {
	
	//用户来源 0数据库手动添加 1微信扫码 2批量导入
	
	MANUAL_ADD(0,"数据库手动添加"),
	
	WEI_XIN(1,"微信扫码"),
	
	BATCH_IMPORT(2,"批量导入"),
	
	SIGNUP(3,"注册");
	
	
	private Integer code;
	private String msg;
	
	UserSourceConstant(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
