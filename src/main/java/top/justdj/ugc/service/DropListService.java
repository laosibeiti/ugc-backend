/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:31
  Info:
*/

package top.justdj.ugc.service;

import top.justdj.ugc.common.entity.DropList;
import top.justdj.ugc.common.entity.dto.DropListItemDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:31
 */
public interface DropListService {
	
	
	/**
	 * 添加下拉列表
	 * @param dropList
	 * @return
	 */
	boolean addList(DropList dropList);
	
	
	/**
	 * 添加item
	 * @param dropListItemDTO
	 * @return
	 */
	public boolean addItem(DropListItemDTO dropListItemDTO);
	
	/**
	 * 查询全部droplist
	 * @return
	 */
	public List<DropList> getAllDropListInfo();
	
	
	/**
	 * 根据key查询具体信息
	 * @param key
	 * @return
	 */
	public DropList getObjectByKey(String key);
	
	/**
	 * 删除item
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean deleteItem(String key,String value);
	
	
	/**
	 * 修改
	 * @param dropListItemDTO
	 * @return
	 */
	public boolean updateItem(DropListItemDTO dropListItemDTO);
	
	/**
	 *
	 * @param id
	 * @return
	 */
	boolean delList(String id);
	
}
