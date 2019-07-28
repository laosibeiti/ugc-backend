/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.3
  Time: 16:58
  Info:
*/

package top.justdj.common.entity.dto;

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
 * Time: 16:58
 */
@Data
@ApiModel
public class CodeIdentify {
	
	@ApiModelProperty(value = "邮箱",required = true,example = "977878634@qq.com")
	@NotBlank(message = "不能为空")
	private String email;
	
	@ApiModelProperty(value = "验证码",required = true,example = "123456")
	@NotBlank(message = "不能为空")
	private String code;
	
}
