/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:49
  Info:
*/

package top.justdj.job.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import top.justdj.common.entity.CompanyInfo;
import top.justdj.job.config.shiro.JwtHelper;
import top.justdj.common.entity.UserInfo;
import top.justdj.job.service.CompanyInfoService;
import top.justdj.job.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyInfoService companyInfoService;
	
	protected UserInfo getUserInfo(HttpServletRequest request) {
		String token = request.getHeader("token");
		String user = (String) JwtHelper.parseJWT(token).get("user");
		UserInfo userInfo =  JSON.parseObject(user,UserInfo.class);
		return userService.selectByEmail(userInfo.getEmail());
	}
	
	protected UserInfo getLatestUserInfo(HttpServletRequest request) {
		return getUserInfo(request);
	}
	
	protected CompanyInfo getUserCompany(String companyId){
		CompanyInfo companyInfo = companyInfoService.getById(companyId);
		if (null == companyInfo){
			return new CompanyInfo();
		}else {
			return companyInfo;
		}
	}
	
}