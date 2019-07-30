/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.3
  Time: 18:34
  Info:
*/

package top.justdj.ugc.common.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.3
 * Time: 18:34
 */
@ApiModel("个人用户注册")
@Data
public class PersonSignUpDTO  {
	
	
	@ApiModelProperty(value = "邮箱",required = true)
	@NotBlank(message = "不能为空")
	private String email;
	
	@ApiModelProperty(value = "验证码",required = true)
	@NotBlank(message = "不能为空")
	private String code;
	
	@ApiModelProperty(value = "姓名",required = true)
	@NotBlank(message = "不能为空")
	private String name;
	
	@ApiModelProperty(value = "性别",required = true)
	@NotBlank(message = "不能为空")
	private String sex;
	
	@ApiModelProperty(value = "密码;",required = true)
	@NotBlank(message = "不能为空")
	private String password;
	
}