/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.24
  Time: 11:03
  Info:
*/

package top.justdj.common.constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.24
 * Time: 11:03
 */
@Getter
public enum  OtherConstant {
	
	//
	DEFAULT_PASSWORD ("123456","默认密码"),
	
	
	CREDIT_MAX (10,"默认信誉分") ;
	
	
	private Object value;
	private String desc;
	
	
	OtherConstant(Object value, String desc) {
		this.value = value;
		this.desc = desc;
	}
}
