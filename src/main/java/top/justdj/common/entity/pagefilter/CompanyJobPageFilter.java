/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.5
  Time: 18:17
  Info:
*/

package top.justdj.common.entity.pagefilter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.5
 * Time: 18:17
 */
@Data
@ApiModel
public class CompanyJobPageFilter extends BasePageFilter {
	
	
	@ApiModelProperty(value = "兼职名称")
	private String jobName;
	
	@ApiModelProperty(value = "联系人 ")
	private String contactPerson;
	
	@ApiModelProperty(value = "所属分类")
	private List<String> jobType;
	
	@ApiModelProperty(value = "结算方式 0不限 日结 周结 月结 完工结 按件结")
	private String payMethod;
	
	@ApiModelProperty("公司id")
	private String companyId;
	
	@JsonIgnore
	private Boolean isAdmin;
}
