/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.23
  Time: 10:20
  Info:
*/

package top.justdj.common.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.23
 * Time: 10:20
 */
@Data
@ApiModel
public class UserDTO {
	
	@ApiModelProperty(value = "姓名",required = true,example = "justdj")
	@NotBlank(message = "用户姓名不能为空! ")
	private String name;
	
	@ApiModelProperty(value = "学号/工号",required = true,example = "2015210405049")
	@NotBlank(message = "学号/工号 不能为空! " )
	private String code;
	
	@ApiModelProperty(value = "班级编号",required = true,example = "1")
	@NotNull(message = "班级编号不能为空")
	private Integer classId;
	
	@ApiModelProperty(value = "联系方式",required = true,example = "13588222812")
	@NotBlank(message = "联系方式不能为空")
	private String tel;
	
}
