/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.13
  Time: 15:08
  Info:
*/

package top.justdj.ugc.common.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.13
 * Time: 15:08
 */
@Data
@ApiModel
public class CompanySelectUserDTO {
	
	private String jobId;
	
	private List<String> userIdList;
	
}
