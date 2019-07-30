/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.17
  Time: 15:17
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
 * Date: 19.3.17
 * Time: 15:17
 */
@Data
@ApiModel
public class DbSearchResultListId {
	
	
	private List<String> id;
	
	private Long num;
	
	
	private String name;
	
}
