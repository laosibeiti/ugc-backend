/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.12
  Time: 15:49
  Info:
*/

package top.justdj.common.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import top.justdj.common.entity.BaseInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.12
 * Time: 15:49
 */
@Data
@ApiModel("浏览历史")
@Document(collection = "click_history_info")
public class ClickHistoryInfo extends BaseInfo {
	
	
	private String userId;
	

	private String jobId;
	
	
}
