package top.justdj.ugc.service;

import org.springframework.data.domain.Page;
import top.justdj.common.entity.pagefilter.BasePageFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 9:37
 */
public interface CommonService<T> {
	
	/**
	 * 插入
	 * @param t
	 * @return
	 */
	T save(T t);
	
	
	/**
	 * 批量插入
	 * @param list
	 */
	void saveBatch(List<T> list);
	
	
	/**
	 * 插入或更新
	 * @param t
	 * @return
	 */
	T saveOrUpdate (T t);
	
	
	/**
	 * 删除
	 * @param id  id
	 */
	void deleteById(String id);
	
	
	/**
	 * 批量删除
	 * @param idList
	 */
	void deleteByIdIn(List<String>idList);
	
	
	/**
	 * 通过id 获取
	 * @return
	 */
	T getById(String id);
	
	
	/**
	 * 查找
	 * @param idList
	 * @return
	 */
	List<T> getByIndIn(List<String> idList);
	
	
	/**
	 * 获取全部
	 * @return
	 */
	List<T> getAll();
	
	
	/**
	 * 简单分页
	 * @param pageRequest
	 * @return
	 */
	Page<T> pageFind(BasePageFilter pageRequest);
	
	
}
