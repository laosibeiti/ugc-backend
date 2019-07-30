/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 21:43
  Info:
*/

package top.justdj.ugc.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 21:43
 */
@ApiModel("兼职相关信息")
@Document(collection = "job_info")
@Data
public class JobInfo extends BaseInfo {
	
	
	@ApiModelProperty("公司id")
	private String companyId;
	
	@ApiModelProperty(value = "兼职名称",required = true)
	@NotBlank(message = "不能为空")
	private String jobName;
	
	@ApiModelProperty(value = "所属分类",required = true)
	@NotBlank(message = "不能为空")
	private List<String> jobType;
	
	
	@ApiModelProperty(value = "联系人 ",required = true)
	@NotBlank(message = "不能为空")
	private String contactPerson;
	
	@ApiModelProperty(value = "联系方式 ;",required = true)
	@NotBlank(message = "不能为空")
	private String phone;
	
	@ApiModelProperty(value = "招聘人数",required = true)
	@NotNull(message = "不能为空")
	private Integer requireNum;
	
	
	@ApiModelProperty("学历要求  0 不限 1专科 2 本科 3研究生 4 硕士")
	private String academicRequirements;
	
	@ApiModelProperty(value = "薪资",required = true)
	@NotNull(message = "不能为空")
	private Double salary;
	
	@ApiModelProperty(value = "类型 0 线下 1线上",required = true)
	@NotNull(message = "不能为空")
	private Integer type;
	
	@ApiModelProperty(value = "地区")
	private List<String> region;
	
	@ApiModelProperty(value = "详细地址")
	private String workLocation;
	
	@ApiModelProperty(value = "结算方式 0不限 日结 周结 月结 完工结 按件结",required = true)
	@NotEmpty(message = "不能为空")
	private String payMethod;
	
	@ApiModelProperty(value = "福利 无 0 包工作餐 交通补助 有提成 带薪年假 五险")
	private List<String> salaryTreatment;
	
	@ApiModelProperty(value = "性别要求 -1 无要求 0 男 1女",required = true)
	@NotNull(message = "不能为空")
	private Integer gender;
	
	@ApiModelProperty(value = "是否需要简历 false不需要 true需要",required = true)
	@NotNull(message = "不能为空")
	private Boolean needResume;
	
	
		@ApiModelProperty(value = "兼职详情",required = true)
		@NotEmpty(message = "不能为空")
		private String jobDetails;
	
		@ApiModelProperty("是否热门  分类 地区排名前十 浏览人数大于100")
		private Long popularScore;
		
		@ApiModelProperty("启用状态 1 关闭 2 正常  3暂存 4结束")
		private String enableStatus;
	
		
		@ApiModelProperty("报名用户id")
		List<String> appliedUserId;
	
		@ApiModelProperty("报名通过用户id")
		List<String> selectedUserId;
}

