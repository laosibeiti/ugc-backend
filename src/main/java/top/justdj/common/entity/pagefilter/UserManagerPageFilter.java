/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.23
  Time: 22:14
  Info:
*/

package top.justdj.common.entity.pagefilter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.23
 * Time: 22:14
 */
@Data
@ApiModel
public class UserManagerPageFilter extends BasePageFilter {
	
	@ApiModelProperty(value = "用户名",example ="佩奇")
	private String name;
	
	@ApiModelProperty(value = "邮箱",example = "软工")
	private String email;
	
	@ApiModelProperty(value = "用户类型 0用户 1企业 2学校",example = "1358822")
	private Integer userType = -1;
	
	@ApiModelProperty(value = "用户状态 0账号停用 1账号过期 2账号正常 3账号删除",example = "1358822")
	private Integer userStatus = -1;
}
