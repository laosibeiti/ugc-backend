package top.justdj.ugc.service;

import java.util.List;

/**
 * @author
 */
public interface RedisService {
     
     public boolean set(String key, String value);
     
     public String get(String key);
     
     public boolean setAndExpire(final String key, final String value, final long expire);
     
     public boolean expire(String key, long expire);
     
     public <T> boolean setList(String key, List <T> list);
     
     public <T> List<T> getList(String key, Class <T> clz);
     
     public List<String> keys(String key);
     
     boolean delete(String key);
}
