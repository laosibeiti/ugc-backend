/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 20:13
  Info:
*/

package top.justdj.ugc.common.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 20:13
 */
@Data
@ApiModel("在线简历")
@Document(collection = "online_resume_info")
public class OnlineResumeInfo extends BaseInfo {
	
	
	@ApiModelProperty("求职状态 0积极找工作 1随便看看 2暂时不找工作")
	private Integer jobStatus;
	
	
//	求职意向
	@ApiModelProperty("求职类型 数组类型 为空表示不限类型 直接选取就好了")
	private List<String> jobType = new ArrayList <>();
	
	@ApiModelProperty("偏好区域 多选级联")
	private List<String> preferredArea = new ArrayList <>();
	
	@ApiModelProperty("空闲时间 ")
	private String  idleTime = "";
	
	@ApiModelProperty("期望月薪 上限")
	private Double expectingSalaryDown = 0D;
	
	@ApiModelProperty("期望月薪 下限")
	private Double expectingSalaryUp = 0D;
	

	@ApiModelProperty("工作经历")
	private JSONObject workExperience;
	
	@ApiModelProperty("自我描述")
	private String selfDesc;
	

	
	@ApiModelProperty("学历")
	private String academic;
	
}
