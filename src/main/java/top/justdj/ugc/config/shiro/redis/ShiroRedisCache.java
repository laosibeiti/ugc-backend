package top.justdj.ugc.config.shiro.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.justdj.ugc.service.RedisService;
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
	private RedisTemplate<String,?> redisTemplate;
	
	//过期时间
	private long GLOB_EXPIRE = 30;
	private final static String USER_SHIRO_PREFLX = "shiro_cache:";
	
			
	
	@SuppressWarnings("rawtypes")
	public ShiroRedisCache(String name, RedisTemplate redisTemplate) {
		this.cacheKey = String.format("%s%s:", USER_SHIRO_PREFLX,name);
		this.redisTemplate = redisTemplate;
	}
	
	
	@Override
	public V get(K key) throws CacheException {
		log.info("shiro Cache get 调用 {}  ",key);
		if(key == null){
			return null;
		}
		try {
			V temp = (V)SerializeUtils.deSerialization(getData(getCacheKey(key)), new SimpleSession());
			if (temp != null){
				log.error("shiro Cache get 调用后缀 {}", JSON.toJSONString(((SimpleSession)temp).getAttributes()));
			}
			log.info("shiro Cache get 调用结果 {}       {}",temp.toString(),SerializeUtils.serialize(temp));
			return temp ;
		}catch (Exception e){
			log.info("shiro Cache get 调用结果为空");
			return null;
		}
	
	}
	
	@Override
	public V put(K key, V value) throws CacheException {
		log.info("sessionRedisCache set 调用 {}   {} ",key,value);
		if(key == null){
			return null;
		}
		V old = get(key);
		setAndExpire(getCacheKey(key),SerializeUtils.serialize(value),GLOB_EXPIRE);
		return old;
	}
	
	@Override
	public V remove(K key) throws CacheException {
		log.info("sessionRedisCache remove 调用 {}",key);
		if(key == null){
			return null;
		}
		V old = get(key);
		deleteData(getCacheKey(key));
		return old;
	}
	
	@Override
	public void clear() throws CacheException {
		log.info("sessionRedisCache clear 调用");
		clearAll("*");
	}
	
	@Override
	public int size() {
		return keys().size();
	}
	
	@Override
	public Set <K> keys() {
		return new HashSet <K>((List<K>)getKeys("*"));
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
	
	private String getCacheKey(Object k) {
		return (this.cacheKey + k).toString();
	}
	
	
	
	
	
	private boolean setData(final String key, final String value) {
		redisTemplate.execute((RedisCallback <Boolean>) connection -> {
			RedisSerializer <String> serializer = redisTemplate.getStringSerializer();
			connection.set(serializer.serialize(key), serializer.serialize(value));
			return true;
		});
		return true;
	}
	
	private boolean deleteData(String key) {
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			log.error("data:{"+key+"},msg:"+e);
			return false;
		}
	}
	
	private void clearAll(String pattern) {
		redisTemplate.keys(pattern);
	}
	
	private String getData(final String key) {
		return redisTemplate.execute((RedisCallback <String>) connection -> {
			RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
			byte[] value = connection.get(serializer.serialize(key));
			return serializer.deserialize(value);
		});
	}
	
	private List<String> getKeys(String key) {
		List<String> keys = redisTemplate.execute((RedisCallback<List<String>>) connection -> {
			RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
			Set<byte[]> keys1 = connection.keys(serializer.serialize(key));
			List<String> list = new ArrayList<>();
			for (byte[] key1 : keys1) {
				byte[] value = connection.get(key1);
				list.add(serializer.deserialize(value));
			}
			return list;
		});
		return keys;
	}
	
	private boolean expire(final String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}
	
	private boolean setAndExpire(final String key, final String value, final long expire) {
		if (!hasKey(key)){
			setData(key, value);
			expire(key, expire);
		}
		return  true;
	}
	
	private Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

}
