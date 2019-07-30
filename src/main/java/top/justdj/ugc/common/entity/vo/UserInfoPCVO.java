/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.24
  Time: 9:36
  Info:
*/

package top.justdj.ugc.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.24
 * Time: 9:36
 */
@Data
@ApiModel
public class UserInfoPCVO {
	
	private Integer id;
	
	@ApiModelProperty(value = "学号/工号",required = true,example = "2015210405049")
	private String code;
	
	@ApiModelProperty(value = "姓名",required = true,example = "justdj")
	private String name;
	
	@ApiModelProperty(value = "班级名称")
	private String className;
	
	@ApiModelProperty(value = "联系方式",required = true,example = "135882228")
	private String phone;
	

	@ApiModelProperty("信用等级 ; 总分10分 超期扣一分")
	private Integer creditRating = 10;
	
	@ApiModelProperty("角色")
	private List<Integer> roleList;
	
	
	@ApiModelProperty("志愿者编号")
	private String volunteerId;
	
	
}
