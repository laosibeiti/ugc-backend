/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 11:20
  Info:
*/

package top.justdj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 11:20
 */
@Data
@ApiModel("职位")
@Document(collection = "job_type_info")
public class JobTypeInfo extends BaseInfo {
	
	@ApiModelProperty("职位名称")
	private String name;
	
	@ApiModelProperty("热度")
	private Long heat;
	
	@ApiModelProperty("父级id")
	private String parentId;
	
	@ApiModelProperty("第几级 从1开始")
	private Integer level;
	
}
