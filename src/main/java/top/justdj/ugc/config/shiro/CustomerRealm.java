/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.17
  Time: 18:12
  Info:
*/

package top.justdj.ugc.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.justdj.ugc.common.entity.RoleInfo;
import top.justdj.ugc.common.entity.UserInfo;
import top.justdj.ugc.service.RoleInfoService;
import top.justdj.ugc.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.17
 * Time: 18:12
 */
@Slf4j
public class CustomerRealm extends AbstractUserRealm {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleInfoService roleInfoService;
	
	@Override
	public UserRolesAndPermissions doGetGroupAuthorizationInfo(UserInfo userInfo) {
		log.info("角色认证 {}" ,userInfo);
		Set<String> userRoles = new HashSet<>();
		Set<String> userPermissions = new HashSet<>();
		//TODO 获取当前用户下拥有的所有角色列表,及权限
		if (ObjectUtils.allNotNull(userInfo)){
			//获取角色列表
			List<RoleInfo> roleInfoList = roleInfoService.selectByRoleIdIn(userInfo.getRoleId());
			if (!CollectionUtils.isEmpty(roleInfoList)){
				//角色不为空的时候
				userRoles.addAll(roleInfoList.stream().map(RoleInfo::getCode).collect(Collectors.toList()));
				//权限
				for (RoleInfo roleInfo: roleInfoList){
					userPermissions.addAll(roleInfo.getModulePermission());
				}
			}
		}
		return new UserRolesAndPermissions(userRoles, userPermissions);
	}
	
	@Override
	public UserRolesAndPermissions doGetRoleAuthorizationInfo(UserInfo userInfo) {
		log.info("用户组认证 {}" ,userInfo);
		Set<String> userRoles = new HashSet<>();
		Set<String> userPermissions = new HashSet<>();
		//TODO 获取当前用户下拥有的所有角色列表,及权限
		return new UserRolesAndPermissions(userRoles, userPermissions);
	}
}
