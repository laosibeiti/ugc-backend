/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.17
  Time: 18:03
  Info:
*/

package top.justdj.ugc.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import top.justdj.ugc.config.shiro.redis.MyRedisSessionDao;
import top.justdj.ugc.config.shiro.redis.ShiroRedisCacheManager;

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
	
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	

	
	
	/**
	 * 复制
	 * @param redisTemplate
	 * @return
	 */
	@Bean(name = "myShiroRealm")
	@DependsOn(value = {"lifecycleBeanPostProcessor", "ShiroRedisCacheManager"})
	public CustomerRealm myShiroRealm(RedisTemplate redisTemplate) {
		CustomerRealm shiroRealm = new CustomerRealm();
		//设置缓存管理器
		shiroRealm.setCacheManager(redisCacheManager(redisTemplate));
		shiroRealm.setCachingEnabled(true);
		//设置认证密码算法及迭代复杂度
		shiroRealm.setCredentialsMatcher(credentialsMatcher());
		//认证
		shiroRealm.setAuthenticationCachingEnabled(false);
		//授权
		shiroRealm.setAuthorizationCachingEnabled(false);
		return shiroRealm;
	}
	
	
	/**
	 * 复制
	 * 缓存管理器的配置
	 * @param redisTemplate
	 * @return]
	 */
	@Bean(name = "ShiroRedisCacheManager")
	public ShiroRedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
		ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager(redisTemplate);
		//name是key的前缀，可以设置任何值，无影响，可以设置带项目特色的值
		redisCacheManager.createCache("shiro_redis");
		return redisCacheManager;
	}
	
	
	/**
	 * 复制
	 *  配置sessionmanager，由redis存储数据
	 */
	@Bean(name = "sessionManager")
	@DependsOn(value = "lifecycleBeanPostProcessor")
	public DefaultWebSessionManager sessionManager(RedisTemplate redisTemplate) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		
		MyRedisSessionDao redisSessionDao = new MyRedisSessionDao(redisTemplate);

		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		sessionManager.setSessionDAO(redisSessionDao);
		sessionManager.setSessionIdCookieEnabled(true);
		
		//全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
		sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
		//是否开启删除无效的session对象  默认为true
		sessionManager.setDeleteInvalidSessions(true);
		//是否开启定时调度器进行检测过期session 默认为true
		sessionManager.setSessionValidationSchedulerEnabled(true);
		//设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
		//设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
		//暂时设置为 5秒 用来测试
		sessionManager.setSessionValidationInterval(3600000);
		//取消url 后面的 JSESSIONID
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		return sessionManager;
	}
	
	
	
	/**
	 * 配置会话ID生成器
	 * @return
	 */
	@Bean
	public SessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}
	
	
	/**
	 * 复制
	 * 配置保存sessionId的cookie
	 * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
	 * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
	 * @return
	 */
	@Bean("sessionIdCookie")
	public SimpleCookie sessionIdCookie(){
		//这个参数是cookie的名称
		SimpleCookie simpleCookie = new SimpleCookie("mySessionId");
		//setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
		//setcookie()的第七个参数
		//设为true后，只能通过http访问，javascript无法访问
		//防止xss读取cookie
		simpleCookie.setHttpOnly(true);
		simpleCookie.setPath("/");
		//maxAge=-1表示浏览器关闭时失效此Cookie
		simpleCookie.setMaxAge(-1);
		return simpleCookie;
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 复制
	 * 安全管理模块，所有的manager在此配置
	 * @return
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//自定义realm
		securityManager.setRealm(myShiroRealm(redisTemplate));
		//配置redis缓存
		securityManager.setCacheManager(redisCacheManager(redisTemplate));
		//自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager(redisTemplate));
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
//		filterMap.put("/api/**","cors");
		filterMap.put("/api/universal/**","anon");
		filterMap.put("/api/login/**", "anon");
		filterMap.put("/api/drop/one/**", "anon");
		filterMap.put("/**","authc");
		filterMap.put("/**", "jwt");
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		// 判断token是否存在
//		filters.put("cors",new CorsFilter());
		filters.put("jwt", new JwtFilter());
		shiroFilter.setFilters(filters);
		return shiroFilter;
	}
	
	
	/**
	 * 复制
	 * shiro管理生命周期的东西
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	
	/**
	 * relam 认证算法
	 * @return
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
		//2次迭代
		credentialsMatcher.setHashIterations(10);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
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