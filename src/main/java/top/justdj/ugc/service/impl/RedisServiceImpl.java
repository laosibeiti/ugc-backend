package top.justdj.ugc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import top.justdj.ugc.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @Author  123
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    
    @Override
    public boolean set(final String key, final String value) {
        return redisTemplate.execute((RedisCallback <Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
    }
    
    @Override
    public boolean setAndExpire(final String key, final String value, final long expire) {
        return set(key, value) && expire(key, expire);
    }
    
    
    @Override
    public String get(final String key) {
        return redisTemplate.execute((RedisCallback <String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
    }
    
    @Override
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
    
    @Override
    public <T> boolean setList(String key, List<T> list) {
        String value = JSON.toJSONString(list);
        return set(key, value);
    }
    
    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            return JSONArray.parseArray(json, clz);
        }
        return null;
    }
    
    
    @Override
    public List<String> keys(String key) {
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
    
    @Override
    public boolean delete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            log.error("data:{"+key+"},msg:"+e);
            return false;
        }
    }

}
