/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.17
  Time: 18:03
  Info:
*/

package top.justdj.job.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.17
 * Time: 18:03
 */
@Slf4j
@Configuration
public class ShiroConfig {
	
	
	
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		hashedCredentialsMatcher.setHashIterations(10);
		return hashedCredentialsMatcher;
	}
	
	@Bean
	public CustomerRealm customerRealm() {
		CustomerRealm customerRealm = new CustomerRealm();
		customerRealm.setCacheManager(new MemoryConstrainedCacheManager());
		customerRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return customerRealm;
	}
	
	
	@Bean("securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(customerRealm());
		//securityManager.setSessionManager(sessionManager());
		return securityManager;
	}
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		Map<String, Filter> filters = new HashMap<>();
		Map<String, String> filterMap = new LinkedHashMap<>();
		//swagger有关
		filterMap.put("/swagger-ui.html/**","anon");
		filterMap.put("/swagger-resources/**", "anon");
		filterMap.put("/v2/api-docs", "anon");
		filterMap.put("/csrf", "anon");
		filterMap.put("/", "anon");
		filterMap.put("/webjars/**", "anon");
		//静态资源
		filterMap.put("/**/*.html", "anon");
		filterMap.put("/**/*.js", "anon");
		filterMap.put("/**/*.css", "anon");
		filterMap.put("/img/**", "anon");
		//其他
		filterMap.put("/api/**","cors");
		filterMap.put("/api/universal/**","anon");
		filterMap.put("/api/login/**", "anon");
		filterMap.put("/tApi/**", "jwt");
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		// 判断token是否存在
		filters.put("cors",new CorsFilter());
		filters.put("jwt", new JwtFilter());
		shiroFilter.setFilters(filters);
		return shiroFilter;
	}
	
	
	
	
	/**
	 * 启用shrio授权注解拦截方式，AOP式方法级权限检查
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	
}