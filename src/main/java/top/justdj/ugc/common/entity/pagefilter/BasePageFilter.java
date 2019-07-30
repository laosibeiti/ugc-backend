/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 9:56
  Info:
*/

package top.justdj.ugc.common.entity.pagefilter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 9:56
 */
@Data
@ApiModel("基础分页请求")
public class BasePageFilter {
	
	@ApiModelProperty(value = "第几页 从0开始 Mybatis plus从1开始",required = true,example = "0")
	@NotNull(message = "pageNum 不能为空")
	private Integer pageNum;
	
	@ApiModelProperty(value = "每页多少条",required = true,example = "10")
	@NotNull(message = "pageSize 不能为空")
	private Integer pageSize;
	

}
