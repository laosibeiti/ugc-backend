/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.17
  Time: 18:07
  Info:
*/

package top.justdj.job.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import  top.justdj.common.constant.UserStatusConstant;
import top.justdj.common.exception.AccountDeleteException;
import top.justdj.common.exception.AccountExpireException;
import top.justdj.common.exception.ForbidLoginException;
import top.justdj.common.entity.UserInfo;
import top.justdj.job.service.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.17
 * Time: 18:07
 */
@Slf4j
public abstract class AbstractUserRealm extends AuthorizingRealm {
	
	
	@Autowired
	private UserService userService;
	
	//获取用户组的权限信息
	public abstract UserRolesAndPermissions doGetGroupAuthorizationInfo(UserInfo userInfo);
	
	//获取用户角色的权限信息
	public abstract UserRolesAndPermissions doGetRoleAuthorizationInfo(UserInfo userInfo);
	
	/**"
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String email  = (String) principals.getPrimaryPrincipal();
		Set <String> userRoles = new HashSet <>();
		Set <String> userPermissions = new HashSet <>();
		//从数据库中获取当前登录用户的详细信息
		UserInfo userInfo = userService.selectByEmail(email);
		if (null != userInfo) {
			UserRolesAndPermissions groupContainer = doGetGroupAuthorizationInfo(userInfo);
			UserRolesAndPermissions roleContainer = doGetGroupAuthorizationInfo(userInfo);
			userRoles.addAll(groupContainer.getUserRoles());
			userRoles.addAll(roleContainer.getUserRoles());
			userPermissions.addAll(groupContainer.getUserPermissions());
			userPermissions.addAll(roleContainer.getUserPermissions());
		} else {
			throw new AuthorizationException();
		}
		//为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRoles(userRoles);
		authorizationInfo.addStringPermissions(userPermissions);
		log.info("###【获取角色成功】[SessionId] => {}", SecurityUtils.getSubject().getSession().getId());
		return authorizationInfo;
	}
	
	/**
	 * 登录认证 登陆时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		//UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		//查出是否有此用户
		
		UserInfo user = userService.selectByEmail(token.getUsername());
		if (user != null) {
			if (UserStatusConstant.STOP.getCode().equals(user.getUserStatus())){
				throw new ForbidLoginException(UserStatusConstant.STOP.getMsg());
			}else if (UserStatusConstant.EXPIRE.getCode().equals(user.getUserStatus())){
				throw new AccountExpireException(UserStatusConstant.EXPIRE.getMsg());
			}else if (UserStatusConstant.DELETE.getCode().equals(user.getUserStatus())){
				throw new AccountDeleteException(UserStatusConstant.DELETE.getMsg());
			}
			else if (UserStatusConstant.NORMAL.getCode().equals(user.getUserStatus())
					|| UserStatusConstant.NOT_ACTIVE.getCode().equals(user.getUserStatus())){
				// 账号正常将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
				ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());//这里的参数要给个唯一的;
				return new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(), credentialsSalt,getName());
			}else {
				log.info("账号没有状态位");
				throw new AccountException("账号没有状态位");
			}
		}else {
			throw new UnknownAccountException();
		}
	}
	
	protected class UserRolesAndPermissions {
		Set <String> userRoles;
		Set <String> userPermissions;
		
		public UserRolesAndPermissions(Set <String> userRoles, Set <String> userPermissions) {
			this.userRoles = userRoles;
			this.userPermissions = userPermissions;
		}
		
		public Set <String> getUserRoles() {
			return userRoles;
		}
		
		public Set <String> getUserPermissions() {
			return userPermissions;
		}
	}
	
}