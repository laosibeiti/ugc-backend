/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:49
  Info:
*/

package top.justdj.ugc.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import top.justdj.ugc.common.entity.CompanyInfo;
import top.justdj.ugc.config.shiro.JwtHelper;
import top.justdj.ugc.common.entity.UserInfo;
import top.justdj.ugc.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	@Autowired
	private UserService userService;
	

	
	protected UserInfo getUserInfo(HttpServletRequest request) {
		String token = request.getHeader("token");
		String user = (String) JwtHelper.parseJWT(token).get("user");
		UserInfo userInfo =  JSON.parseObject(user,UserInfo.class);
		return userService.selectByEmail(userInfo.getEmail());
	}
	
	protected UserInfo getLatestUserInfo(HttpServletRequest request) {
		return getUserInfo(request);
	}
	
	
}