package top.justdj.ugc.dao.common;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * 数据库基本操作
 * Created by superren on 2017/8/19.
 */
public interface CommonDAO<T> {
    
    
    /**
     * 添加
     * @param systemInfo
     * @return
     */
    public boolean add(T systemInfo);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 查询
     * @param query
     * @return
     */
    public List<T> query(Query query);


    /**
     * 查询一个
     * @param query
     * @return
     */
    public T findOne(Query query);


    /**
     * 修改
     * @param update
     * @param query
     * @return
     */
    public boolean update(Update update, Query query);

    /**
     * 批量修改
     * @param update
     * @param query
     * @return
     */
    public long multiUpdate(Update update, Query query);


    /**
     * 统计
     * @param query
     * @return
     */
    public long count(Query query);


}
