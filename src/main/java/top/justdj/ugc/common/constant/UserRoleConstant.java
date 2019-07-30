package top.justdj.ugc.common.constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.21
 * Time: 18:39
 */
@Getter
public enum UserRoleConstant {
	
	//
	
	ADMIN(3,"admin"),
	
	COMPANY(2,"company"),
	
	NORMAL(1,"normal");
	
	
	private Integer code;
	private String msg;
	
	UserRoleConstant(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
