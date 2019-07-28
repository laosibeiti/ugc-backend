/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:12
  Info:
*/

package top.justdj.job.service;


import org.springframework.data.domain.Page;
import top.justdj.common.entity.UserInfo;
import top.justdj.common.entity.dto.CompanySignUpDTO;
import top.justdj.common.entity.dto.PersonSignUpDTO;
import top.justdj.common.entity.pagefilter.UserManagerPageFilter;

public interface UserService extends CommonService<UserInfo> {

	
	UserInfo selectByEmail(String phone);
	
	
	UserInfo saveUser(PersonSignUpDTO signUpDTO);
	
	
	UserInfo saveUser(CompanySignUpDTO signUpDTO, String companyId);
	
	/**
	 * 用户管理分页查询
	 * @param userManagerPageFilter
	 * @return
	 */
	Page<UserInfo> pageFind(UserManagerPageFilter userManagerPageFilter);
}
