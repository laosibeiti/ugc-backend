/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:13
  Info:
*/

package top.justdj.ugc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.justdj.common.constant.UserRoleConstant;
import top.justdj.common.constant.UserSourceConstant;
import top.justdj.common.constant.UserStatusConstant;
import top.justdj.common.constant.UserTypeConstant;
import top.justdj.common.entity.dto.CompanySignUpDTO;
import top.justdj.common.entity.dto.PersonSignUpDTO;
import top.justdj.common.entity.pagefilter.UserManagerPageFilter;
import top.justdj.ugc.config.repository.UserInfoRepository;
import top.justdj.common.entity.UserInfo;
import top.justdj.ugc.dao.UserInfoDAO;
import top.justdj.ugc.service.UserService;
import top.justdj.ugc.util.Md5Utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl extends CommonServiceImpl<UserInfoRepository,UserInfo>
		implements UserService {
	
	@Autowired
	private UserInfoRepository  userInfoRepository;
	
	@Autowired
	private UserInfoDAO userInfoDao;
	
	@Override
	public UserInfo selectByEmail(String phone) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(phone));
		UserInfo userInfo = userInfoDao.findOne(query);
		return userInfo ;
	}
	
	
	@Override
	public UserInfo saveUser(PersonSignUpDTO signUpDTO) {
		UserInfo userInfo =  new UserInfo();
		userInfo.setEmail(signUpDTO.getEmail());
		userInfo.setName(signUpDTO.getName());
		userInfo.setSex(signUpDTO.getSex());
		String salt = Md5Utils.generateSalt();
		userInfo.setSalt(salt);
		userInfo.setPassword(Md5Utils.encryption(signUpDTO.getPassword(),salt));
		List<Integer> roleList  = Arrays.asList(UserRoleConstant.NORMAL.getCode());
		userInfo.setRoleId(roleList);
		userInfo.setCompanyId("");
		userInfo.setType(UserTypeConstant.NORMAL.getCode());
		userInfo.setCreditRating(UserStatusConstant.MAX_CREDIT_RATING.getCode());
		userInfo.setUserStatus(UserStatusConstant.NORMAL.getCode());
		userInfo.setSource(UserSourceConstant.SIGNUP.getCode());
		return userInfoRepository.save(userInfo);
	}
	
	@Override
	public UserInfo saveUser(CompanySignUpDTO signUpDTO, String companyId) {
		UserInfo userInfo =  new UserInfo();
		userInfo.setEmail(signUpDTO.getEmail());
		userInfo.setName(signUpDTO.getContract());
		userInfo.setPhone(signUpDTO.getPhone());
		String salt = Md5Utils.generateSalt();
		userInfo.setSalt(salt);
		userInfo.setPassword(Md5Utils.encryption(signUpDTO.getPassword(),salt));
		List<Integer> roleList  = Arrays.asList(UserRoleConstant.NORMAL.getCode(),UserRoleConstant.COMPANY
				.getCode());
		userInfo.setRoleId(roleList);
		userInfo.setCompanyId(companyId);
		userInfo.setType(UserTypeConstant.COMPANY.getCode());
		userInfo.setCreditRating(UserStatusConstant.MAX_CREDIT_RATING.getCode());
		userInfo.setUserStatus(UserStatusConstant.NOT_ACTIVE.getCode());
		userInfo.setSource(UserSourceConstant.SIGNUP.getCode());
		return userInfoRepository.save(userInfo);
	}
	
	@Override
	public Page<UserInfo> pageFind(UserManagerPageFilter filter) {
		Query query =  new Query();
		PageRequest pageRequest = PageRequest.of(filter.getPageNum() ,filter.getPageSize(), Sort.by(Sort.Order
				.asc("createTime")));
		query.with(pageRequest);
		query.addCriteria(Criteria.where("id").ne("5c7550f59d471e5ed4211fd4"));
		if (!StringUtils.isEmpty(filter.getName())){
			Pattern name = Pattern.compile("^.*"+filter.getName() +".*$", Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("name").regex(name));
		}
		if (!StringUtils.isEmpty(filter.getEmail())){
			Pattern email = Pattern.compile("^.*"+filter.getEmail()+".*$", Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("email").regex(email));
		}
		if (filter.getUserType() != null && filter.getUserType() > -1){
			query.addCriteria(Criteria.where("type").is(filter.getUserType()));
		}
		if (filter.getUserStatus() != null && filter.getUserStatus() > -1){
			query.addCriteria(Criteria.where("userStatus").is(filter.getUserStatus()));
		}else {
			query.addCriteria(Criteria.where("userStatus").ne(UserStatusConstant.DELETE.getCode()));
		}
		return new PageImpl <UserInfo>(userInfoDao.query(query),pageRequest,userInfoDao.count(query));
	}
}
