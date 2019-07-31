package top.justdj.ugc.config.shiro.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import top.justdj.ugc.util.SerializeUtils;


import javax.annotation.Resource;
import java.io.*;
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
	public final static String USER_SHIRO_SESSION = "user:shiro:session:";
	
	
	private RedisTemplate <byte[],byte[]> redisTemplate;
	
	
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
		log.debug("新建session {} ",session);
		redisTemplate.opsForValue().set((USER_SHIRO_SESSION + sessionId.toString()).getBytes(), sessionToByte(session));
//		redisTemplate.opsForValue().set(sessionId.toString().getBytes(),sessionToByte(session),EXPIRE_TIME, TimeUnit.SECONDS);
		return sessionId;
	}
	
	/**
	获取session
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		log.debug("获取session:{}", sessionId);
		Session session = super.doReadSession(sessionId);
		if(session == null){
			byte[] bytes = redisTemplate.opsForValue().get((USER_SHIRO_SESSION + sessionId.toString()).getBytes());
			if (bytes != null){
				session = (SimpleSession)byteToSession(bytes);
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
		log.debug("获取session:{}", session.getId());
		String key = USER_SHIRO_SESSION + session.getId().toString();
		if (!redisTemplate.hasKey(key.getBytes())) {
			redisTemplate.opsForValue().set(key.getBytes(), sessionToByte(session));
		}
		redisTemplate.expire(key.getBytes(), EXPIRE_TIME, TimeUnit.SECONDS);
	}
	
	/**
	 * 删除session
	 * @param session
	 */
	@Override
	protected void doDelete(Session session) {
		super.doDelete(session);
		log.debug("删除session:{}", session.getId());
		redisTemplate.delete((USER_SHIRO_SESSION + session.getId().toString()).getBytes());
	}
	
	private byte[] sessionToByte(Session session){
		log.debug("调用 session to byte");
		if (null == session){
			return null;
		}
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		byte[] bytes = null;
		ObjectOutputStream oo ;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(session);
			bytes = bo.toByteArray();
		}catch (Exception e){
			e.printStackTrace();
		}
		log.debug("调用结束 session to byte {}",bytes);
		return bytes;
		
	}
	
	
	private Session byteToSession(byte[] bytes){
		if(0==bytes.length){
			return null;
		}
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream in;
		SimpleSession session = null;
		try {
			in = new ObjectInputStream(bi);
			session = (SimpleSession) in.readObject();
		}catch (Exception e){
			e.printStackTrace();
		}
		return session;
	}
	
}

