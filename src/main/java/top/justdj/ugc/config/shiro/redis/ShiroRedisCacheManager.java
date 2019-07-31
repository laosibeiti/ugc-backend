package top.justdj.ugc.config.shiro.redis;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.7.31
 * Time: 8:46
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc 使用redis实现shiro缓存管理
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {
	
	@Autowired
	private RedisTemplate  redisTemplate;
	
	
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new ShiroRedisCache<K, V>(name, redisTemplate);
	}
	
	
	public RedisTemplate<String, Serializable> getRedisTemplate() {
		return redisTemplate;
	}
	
	public void setRedisTemplate(RedisTemplate<String, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	
	//为了个性化配置redis存储时的key，我们选择了加前缀的方式，所以写了一个带名字及redis操作的构造函数的Cache类
	@Override
	public Cache createCache(String name) throws CacheException {
		return new ShiroRedisCache(name,redisTemplate);
	}
	
	
}