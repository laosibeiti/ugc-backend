package top.justdj.ugc.config.shiro.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.justdj.ugc.util.SerializeUtils;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.7.31
 * Time: 9:01
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc 生成以及查询session的工具类
 * 都是先调用EnterpriseCacheSessionDAO的方法进行一个本地session的操作，然后再去redis中操作session，这样做就可以减少连接redis的次数，效率更高
 */
@Slf4j
public class MyRedisSessionDao extends EnterpriseCacheSessionDAO {
	private Long EXPIRE_TIME = 30 * 60L;
	public final static String USER_SHIRO_SESSION = "user_session:";
	
	
	private RedisTemplate<String,?> redisTemplate;
	
	
	public MyRedisSessionDao(RedisTemplate redisTemplate){
		this.redisTemplate = redisTemplate;
	}
	
	
	/**
	 * 创建session，保存到数据库
	 * @param session
	 * @return
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
		log.info("新建session {} ",session);
		setAndExpire(getKey(session),SerializeUtils.serialize(session),EXPIRE_TIME);
		return sessionId;
	}
	
	/**
	获取session
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		log.info("获取session:{}", sessionId);
		Session session = super.doReadSession(sessionId);
		if(session == null){
			if (redisTemplate == null){
				log.error("redisTemplate为空");
			}
			String value = getData(USER_SHIRO_SESSION + sessionId);
			if (!StringUtils.isEmpty(value)){
				log.info("当前sessionID 对应的数据 {}",value);
				try {
					session = SerializeUtils.deSerialization(value, new SimpleSession());
				}catch (Exception e){
					log.error("反序列化异常");
				}
				
			}else{
				log.error("当前sessionId 对应的session为空");
			}
		}
		return session;
	}
	
	/**
	 * 更新session的最后一次访问时间
	 * @param session
	 */
	@Override
	protected void doUpdate(Session session) {
		super.doUpdate(session);
		log.info("获取session:{}", session.getId());
		String key = getKey(session);
		if (!hasKey(key)) {
			setAndExpire(key,SerializeUtils.serialize(session),EXPIRE_TIME);
		}
	}
	
	/**
	 * 删除session
	 * @param session
	 */
	@Override
	protected void doDelete(Session session) {
		super.doDelete(session);
		log.info("删除session:{}", session.getId());
		deleteData(getKey(session));
	}
	
	
	String getKey(Session session){
		if (session == null){
			log.error("session 为null");
		}
		return  USER_SHIRO_SESSION + session.getId();
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
	
	private List <String> getKeys(String key) {
		List<String> keys = redisTemplate.execute((RedisCallback<List<String>>) connection -> {
			RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
			Set <byte[]> keys1 = connection.keys(serializer.serialize(key));
			List<String> list = new ArrayList <>();
			for (byte[] key1 : keys1) {
				byte[] value = connection.get(key1);
				list.add(serializer.deserialize(value));
			}
			return list;
		});
		return keys;
	}
	
	private boolean expire(final String key, long expire) {
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		return true;
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

