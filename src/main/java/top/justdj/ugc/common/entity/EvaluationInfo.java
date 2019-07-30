/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:06
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
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:06
 */
@Data
@ApiModel("评价信息")
@Document(collection = "evaluation_info")
public class EvaluationInfo extends BaseInfo {
	
	private String userId;
	
	private String jobId;
	
	private String companyId;
	
	
	@ApiModelProperty("评分")
	private Double score;
	
	@ApiModelProperty("备注")
	private String desc;
	
}
