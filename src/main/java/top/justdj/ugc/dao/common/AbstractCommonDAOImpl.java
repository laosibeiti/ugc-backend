package top.justdj.ugc.dao.common;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author shan 
 * @param <T>
 */
@Slf4j
public abstract class AbstractCommonDAOImpl<T>{
    
    
    @Autowired
    public MongoTemplate mongoTemplate;
    
    private String COLLECTION = "";
    

    public  void setCOLLECTION()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.COLLECTION = tClass.getAnnotation(Document.class).collection();
        log.info("注入表名  {}" ,COLLECTION);
    }

    public boolean add(T obj) {
        try {
            mongoTemplate.save(obj,COLLECTION);
        }catch (Exception e){
            log.error("save  error:{}",e);
            return false;
        }
        return true;
    }


    public boolean delete(Query query) {
        try {
            mongoTemplate.remove(query,COLLECTION);
        }catch (Exception e){
            log.error("remove  error:{}",e);
            return false;
        }
        return true;
    }


    public List<T> query(Query query) {
        try {
            Class <T>  entityClass=(Class <T> ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ];
            return mongoTemplate.find(query,entityClass,COLLECTION);
        }catch (Exception e){
            log.error("find list error:{}",e);
            return null;
        }
    }


    public T findOne(Query query) {
        try {
            Class <T>  entityClass=(Class <T> ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ];
            return mongoTemplate.findOne(query,entityClass,COLLECTION);
        }catch (Exception e){
            log.error("find one error:{}",e);
            return null;
        }
    }


    public boolean update(Update update, Query query) {
        try {
            mongoTemplate.updateFirst(query,update,COLLECTION);

        }catch (Exception e){
            log.error("update  error:{}",e);
            return false;
        }
        return true;
    }
    
    
    //1.5版本返回值int
    public long multiUpdate(Update update, Query query) {
        UpdateResult result;
        try {
            result =  mongoTemplate.updateMulti(query,update,COLLECTION);

        }catch (Exception e){
            log.error("update  error:{}",e);
            return -1;
        }
        return result.getModifiedCount();
    }


    public long count(Query query) {
        try {
            return mongoTemplate.count(query,COLLECTION);
        }catch (Exception e){
            log.error("count  error:{}",e);
            return 0L;
        }
    }
}
