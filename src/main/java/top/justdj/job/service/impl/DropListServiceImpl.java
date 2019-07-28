/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:31
  Info:
*/

package top.justdj.job.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.DropList;
import top.justdj.common.entity.dto.DropListItemDTO;
import top.justdj.job.config.repository.DropListRepository;
import top.justdj.job.dao.DropListDAO;
import top.justdj.job.service.DropListService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:31
 */
@Slf4j
@Service
public class DropListServiceImpl implements DropListService {
	
	@Autowired
	private DropListRepository dropListRepository;
	
	@Autowired
	private DropListDAO dropListDAO;
	
	
	@Override
	public boolean addList(DropList dropList) {
		return dropListDAO.add(dropList);
	}
	
	@Override
	public boolean addItem(DropListItemDTO itemDTO) {
		Query query = new Query();
		query.addCriteria(Criteria.where("key").is(itemDTO.getKey()));
		DropList obj = dropListDAO.findOne(query);
		if (obj != null) {
			JSONObject item = new JSONObject();
			try {
				item.put("value", Integer.parseInt(itemDTO.getValue()));
			} catch (NumberFormatException e) {
				item.put("value", itemDTO.getValue());
			}
			item.put("label", itemDTO.getLabel());
			List<JSONObject> list = obj.getValueLabel();
			if (list == null) {
				list = new ArrayList<>();
			}
			list.add(item);
			obj.setValueLabel(list);
		} else {
			return false;
		}
		return dropListDAO.add(obj);
	}
	
	@Override
	public List<DropList> getAllDropListInfo() {
		return dropListDAO.query(new Query());
	}
	
	@Override
	public DropList getObjectByKey(String key) {
		Query query = new Query();
		query.addCriteria(Criteria.where("key").is(key));
		return dropListDAO.findOne(query);
	}
	
	@Override
	public boolean deleteItem(String key, String value) {
		Query query = new Query();
		query.addCriteria(Criteria.where("key").is(key));
		DropList obj = dropListDAO.findOne(query);
		if (obj != null) {
			List<JSONObject> map = obj.getValueLabel();
			for (int i = map.size() - 1; i >= 0; i--) {
				if (StringUtils.equals(map.get(i).getString("value"), value)) {
					map.remove(i);
				}
			}
			obj.setValueLabel(map);
		} else {
			return false;
		}
		return dropListDAO.add(obj);
	}
	
	@Override
	public boolean updateItem(DropListItemDTO dropListItemDTO) {
		if (deleteItem(dropListItemDTO.getKey(), dropListItemDTO.getValue())) {
			return addItem(dropListItemDTO);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean delList(String id) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(id));
			return dropListDAO.delete(query);
		} catch (Exception e) {
			log.info("入参:  id:"+id+",  msg: "+e);
			return false;
		}
	}
	
}
