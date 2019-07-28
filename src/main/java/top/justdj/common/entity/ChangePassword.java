/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.24
  Time: 21:29
  Info:
*/

package top.justdj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.24
 * Time: 21:29
 */
@Data
@ApiModel
public class ChangePassword {
	
	
	@ApiModelProperty(value = "旧密码",required = true,example = "")
	@NotEmpty(message = "旧密码不能为空")
	private String oldPassword;
	
	@ApiModelProperty(value = "新密码",required = true,example = "")
	@NotEmpty(message = "新密码不能为空")
	private String newPassword;
	
}
