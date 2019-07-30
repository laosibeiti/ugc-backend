/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.18
  Time: 19:37
  Info:
*/

package top.justdj.ugc.common.constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.18
 * Time: 19:37
 */
@Getter
public enum UserStatusConstant {

//	0账号停用 1账号过期 2账号正常
	
	STOP(0,"账号停用"),
	
	EXPIRE(1,"账号过期"),
	
	NORMAL(2,"账号正常"),
	
	DELETE(3,"账号删除"),
	
	NOT_ACTIVE(4,"未激活"),
	
	MAX_CREDIT_RATING(100,"最大信用积分");
	
	
	
	private Integer code;
	private String msg;
	
	UserStatusConstant(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
