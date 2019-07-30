/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:09
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
 * Time: 16:09
 */
@Data
@ApiModel("申诉信息")
@Document(collection = "appeal_info")
public class AppealInfo extends BaseInfo {
	
	
	private String userId;
	
	private String jobId;
	
	private String companyId;
	
	@ApiModelProperty("备注")
	private String desc;
	
}
