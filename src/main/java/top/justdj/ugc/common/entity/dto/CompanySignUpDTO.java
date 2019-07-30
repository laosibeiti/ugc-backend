/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.3
  Time: 18:39
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
 * Time: 18:39
 */
@ApiModel("公司注册")
@Data
public class CompanySignUpDTO {
	
	
	@ApiModelProperty(value = "邮箱",required = true)
	@NotBlank(message = "不能为空")
	private String email;
	
	@ApiModelProperty(value = "验证码",required = true)
	@NotBlank(message = "不能为空")
	private String code;
	
	@ApiModelProperty(value = "联系人",required = true)
	@NotBlank(message = "不能为空")
	private String contract;
	
	@ApiModelProperty(value = "手机",required = true)
	@NotBlank(message = "不能为空")
	private String phone;
	
	@ApiModelProperty(value = "密码",required = true)
	@NotBlank(message = "不能为空")
	private String password;
	
	@ApiModelProperty("公司名")
	private String companyName;
	
	@ApiModelProperty("类型;")
	private String companyType;
	
}
