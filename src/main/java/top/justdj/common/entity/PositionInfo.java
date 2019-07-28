/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 22:50
  Info:
*/

package top.justdj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 22:50
 */
@Data
@ApiModel("地区信息")
@Document(collection = "position_info")
public class PositionInfo extends BaseInfo {
	
	@ApiModelProperty("区号")
	@Field("code")
	private String value;
	
	@ApiModelProperty("名称")
	private String label;
	
	@ApiModelProperty("父级区号")
	@Field("parentCode")
	private String parentValue;
	
	@ApiModelProperty("第几级")
	private Integer level;
	
	@ApiModelProperty("地址全拼")
	private String fullName;
}
