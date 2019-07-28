/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.4
  Time: 11:28
  Info:
*/

package top.justdj.common.constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.4
 * Time: 11:28
 */
@Getter
public enum  UserTypeConstant {
	
	
	//	0账号停用 1账号过期 2账号正常
	
	NORMAL(0,"普通用户"),
	
	COMPANY(1,"企业用户");
	
	
	
	private Integer code;
	private String msg;
	
	UserTypeConstant(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
