/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 11:34
  Info:
*/

package top.justdj.ugc.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 11:34
 */
@ApiModel("公司详细信息")
@Document(collection = "company_info")
@Data
public class CompanyInfo extends BaseInfo {
	
	
	@ApiModelProperty("公司类型 1合资、独资、国有、私营、全民所有制、集体所有制、股份制、8有限责任 ")
	private String companyType;
	
	@ApiModelProperty("公司名称")
	private String companyName;
	
	
	@ApiModelProperty("公司成立日期")
	private Long companyCreateTime;
	
	@ApiModelProperty("注册资本")
	private Double  registeredCapital;
	
	
	@ApiModelProperty("经营范围")
	private String businessScope;
	
	@ApiModelProperty("联系人")
	private String contact;
	
	@ApiModelProperty("联系方式")
	private String contactPhone;
	
	@ApiModelProperty("公司地址")
	private String address;
	
	@ApiModelProperty("公司介绍")
	private String companyIntroduce;
	
		@ApiModelProperty("消息回复率")
		@JsonIgnore
		private Double responseRate;
		
		@ApiModelProperty("报名处理速度;")
		@JsonIgnore
		private Double processingSpeed;
	
	
	@ApiModelProperty("公司平均评分")
	private Double score;
}
