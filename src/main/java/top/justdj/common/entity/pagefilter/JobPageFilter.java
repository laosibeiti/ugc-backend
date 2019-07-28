/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.7
  Time: 20:30
  Info:
*/

package top.justdj.common.entity.pagefilter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.7
 * Time: 20:30
 */
@Data
@ApiModel
public class JobPageFilter extends BasePageFilter {
	
	@ApiModelProperty(value = "兼职名称",required = true)
	private String jobName;
	
	
	@ApiModelProperty(value = "所属分类",required = true)
	private List<String> jobType;
	
	@ApiModelProperty(value = "结算方式 0不限 日结 周结 月结 完工结 按件结",required = true)
	@NotEmpty(message = "不能为空")
	private String payMethod;
	
	
	@ApiModelProperty(value = "性别要求 -1 无要求 0 男 1女",required = true)
	@NotNull(message = "不能为空")
	private Integer gender;
	
	@ApiModelProperty("学历要求  0 不限 1专科 2 本科 3研究生 4 硕士")
	private String academicRequirements;
	
	@ApiModelProperty(value = "地区")
	private List<String> region;
	
	private String sort;
	
	
}
