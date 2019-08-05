/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 15:58
  Info:
*/

package top.justdj.ugc.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shan
 */
@Data
@ApiModel("用户")
@Document(collection = "user_info")
public class UserInfo extends BaseInfo {
	
	@ApiModelProperty(value = "工号",required = true,example = "2015210405049")
	private String code;
	
	@ApiModelProperty(value = "姓名",required = true,example = "justdj")
	private String name;
	
	@ApiModelProperty(value = "手机号",required = true,example = "135882228")
	@NotBlank(message = "手机号不能为空")
	private String phone;
	
	private String headImg;
	
	@ApiModelProperty("邮箱")
	@NotBlank(message = "邮箱不能为空")
	private String email;
	
	@ApiModelProperty("紧急联系方式")
	private String urgentContract;
	
	@ApiModelProperty("性别 0 男 1女")
	private String sex;
	
	@ApiModelProperty("出生年份")
	private Long birthday;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty("加密密码的盐")
	private String salt;
	
	@ApiModelProperty(value = "角色id",required = true,example = "1")
	private List<String> roleId;
	
//	@ApiModelProperty("用户类型 0用户 1企业 2学校")
//	private Integer type;
	
	@ApiModelProperty("信用等级 ; 总分100分 扣一分")
	private Integer creditRating = 100;
	
	@ApiModelProperty("黑名单")
	private List<String> blackList;
	
	
	@ApiModelProperty("公司id")
	private String companyId;
	
	@ApiModelProperty("用户状态 0账号停用 1账号过期 2账号正常 3账号删除 4未认证企业账户")
	private Integer userStatus = 2;
	
	
	@ApiModelProperty("上次登录时间")
	private Long lastLoginTime;

    public void setHeadImg(String url) {
    }
}

//String,openId,微信ID;
//String,code,学号/工号;
//Integer,roleId,角色（0借伞人 1志愿者 2管理员）;
//String,name,姓名;
//String,password,密码;
//String,tel,手机号;
//Integer,classId,班级编号;
//Integer,creditRating,信用等级;
//Integer,volunteerId,志愿者编号;
