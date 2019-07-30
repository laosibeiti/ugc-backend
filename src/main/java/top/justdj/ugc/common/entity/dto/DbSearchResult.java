/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.10
  Time: 19:16
  Info:
*/

package top.justdj.ugc.common.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.10
 * Time: 19:16
 */
@Data
@ApiModel
public class DbSearchResult {
	
	private String id;
	
	private Long num;
	
}
