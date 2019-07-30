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
	
	@ApiModelProperty("角色id")
	private Integer roleId;
	
	@ApiModelProperty("角色名")
	private String name;
	
	@ApiModelProperty("描诉")
	private String desc;
}
