/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.24
  Time: 10:47
  Info:
*/

package top.justdj.ugc.common.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.24
 * Time: 10:47
 */
@Data
@ApiModel
public class UserPCDTO {
	
	@ApiModelProperty(value = "用户id",required = true,example = "3")
	@NotNull(message = "id 不能为空")
	private Integer id;
	
	@ApiModelProperty(value = "学号/工号",required = true,example = "2015210405049")
	@NotBlank(message = "学号/工号 不能为空! " )
	private String code;
	
	@ApiModelProperty(value = "姓名",required = true,example = "justdj")
	@NotBlank(message = "用户姓名不能为空! ")
	private String name;
	
	
	@ApiModelProperty(value = "联系方式",required = true,example = "135884228")
	@NotBlank(message = "phone 不能为空")
	private String phone;
	
	@ApiModelProperty(value = "班级编号",required = true,example = "1")
	@NotNull(message = "班级编号 不能为空")
	private Integer classId;
	
	
	@ApiModelProperty(value = "角色（0借伞人 1志愿者 2管理员）",required = true,example = "1,2")
	@NotNull(message = "角色id不能为空! ")
	private List<Integer> roleId;
	

}
