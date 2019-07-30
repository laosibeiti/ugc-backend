/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.17
  Time: 21:39
  Info:
*/

package top.justdj.ugc.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.17
 * Time: 21:39
 */
@Data
@ApiModel("用户登录实体")
public class Authentication {
	
	@ApiModelProperty(value = "邮箱",example = "2269090020@qq.com",required = true)
	@NotBlank(message = "邮箱不能为空")
	private String email;
	
	@ApiModelProperty(value = "密码",example = "123456",required = true)
	@NotBlank(message = "密码不能为空")
	private String password;
	
}

