/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.22
  Time: 14:15
  Info:
*/

package top.justdj.ugc.common.constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.22
 * Time: 14:15
 */
@Getter
public enum  UmbrellaStatesConstant {
	
//	伞状态 0停用 1正常 2已借出 3正在维修
	
	STOP(0,"停用"),
	
	NORMAL(1,"正常"),
	
	BORROWED(2,"已借出"),
	
	REPAIRING(3,"正在维修");
	
	private Integer code;
	private String msg;
	
	UmbrellaStatesConstant(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
