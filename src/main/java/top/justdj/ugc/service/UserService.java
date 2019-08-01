/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:12
  Info:
*/

package top.justdj.ugc.service;


import org.springframework.data.domain.Page;
import top.justdj.ugc.common.entity.UserInfo;
import top.justdj.ugc.common.entity.dto.PersonSignUpDTO;
import top.justdj.ugc.common.entity.pagefilter.UserManagerPageFilter;

public interface UserService extends CommonService<UserInfo> {

	
	UserInfo selectByEmail(String phone);
	
	
	UserInfo saveUser(PersonSignUpDTO signUpDTO);
	
	

	
	/**
	 * 用户管理分页查询
	 * @param userManagerPageFilter
	 * @return
	 */
	Page<UserInfo> pageFind(UserManagerPageFilter userManagerPageFilter);
}
