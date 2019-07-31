package top.justdj.ugc.config.shiro.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import top.justdj.ugc.util.SerializeUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.7.31
 * Time: 8:58
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc 缓存相关
 */
@Slf4j
public class ShiroRedisCache<K,V> implements Cache <K,V> {
	
	private String cacheKey;
	private RedisTemplate<K,V> redisTemplate;
	//过期时间
	private long GLOB_EXPIRE = 30;
	private final static String USER_SHIRO_PREFLX = "user:shiro:preflx:";
	
	
			private String prefix = "shiro_redis:";
			
	
	@SuppressWarnings("rawtypes")
	public ShiroRedisCache(String name, RedisTemplate client) {
		this.cacheKey = String.format("%s%s:", USER_SHIRO_PREFLX,name);
		this.redisTemplate = client;
	}
	
	
	@Override
	public V get(K key) throws CacheException {
		log.debug("shiro Cache get 调用 {}  ",key);
		if(key == null){
			return null;
		}
		redisTemplate.boundValueOps(getCacheKey(key)).expire(GLOB_EXPIRE, TimeUnit.MINUTES);
		return redisTemplate.boundValueOps(getCacheKey(key)).get();
	}
	
	@Override
	public V put(K key, V value) throws CacheException {
		log.debug("sessionRedisCache set 调用 {}   {} ",key,value);
		if(key == null){
			return null;
		}
		V old = get(key);
		redisTemplate.boundValueOps(getCacheKey(key)).set(value);
		return old;
	}
	
	@Override
	public V remove(K key) throws CacheException {
		log.debug("sessionRedisCache remove 调用 {}",key);
		if(key == null){
			return null;
		}
		V old = get(key);
		redisTemplate.delete(getCacheKey(key));
		return old;
	}
	
	@Override
	public void clear() throws CacheException {
		log.debug("sessionRedisCache clear 调用");
		redisTemplate.delete(keys());
	}
	
	@Override
	public int size() {
		return keys().size();
	}
	
	@Override
	public Set <K> keys() {
		return redisTemplate.keys(getCacheKey("*"));
	}
	
	@Override
	public Collection <V> values() {
		Set<K> set = keys();
		List<V> list = new ArrayList<>();
		for (K s : set) {
			list.add(get(s));
		}
		return list;
	}
	
	private K getCacheKey(Object k) {
		return (K) (this.cacheKey + k);
	}
	
}
