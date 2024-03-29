/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.17
  Time: 15:19
  Info:
*/

package top.justdj.ugc.common.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.17
 * Time: 15:19
 */
@Data
@ApiModel("角色实体类")
@Document(collection = "role_info")
public class RoleInfo extends BaseInfo {
	
	@ApiModelProperty("角色名")
	private String name;
	
	@ApiModelProperty("角色编码")
	private String code;
	
	@ApiModelProperty("角色描诉")
	private String desc;
	
	@ApiModelProperty("模组列表")
	private List<String> moduleIdList;
	
	@ApiModelProperty("具体模组操作权限")
	private List<String> modulePermission;
	
	@ApiModelProperty("是否可编辑")
	private Boolean editAble;
}
