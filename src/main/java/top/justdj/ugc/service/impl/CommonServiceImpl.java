/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 9:59
  Info:
*/

package top.justdj.ugc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.justdj.common.entity.BaseInfo;
import top.justdj.common.entity.pagefilter.BasePageFilter;
import top.justdj.ugc.service.CommonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 9:59
 */
@Slf4j
public class CommonServiceImpl<Repository extends MongoRepository<T,String>,T extends BaseInfo> implements
		CommonService<T> {
	
	@Autowired
	private Repository repository;
	
	
	@Override
	public T save(T t) {
		return repository.save(t);
	}
	
	@Override
	public T saveOrUpdate(T t) {
		if (!StringUtils.isEmpty(t.getId())){
			t.setUpdateTime(System.currentTimeMillis());
		}
		return repository.save(t);
	}
	
	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}
	
	@Override
	public T getById(String id) {
		Optional<T> optional = repository.findById(id);
		return optional.orElse(null);
	}
	
	@Override
	public List <T> getAll() {
		return repository.findAll();
	}
	
	@Override
	public Page <T> pageFind(BasePageFilter pageRequest) {
		Pageable pageable =  PageRequest.of(pageRequest.getPageNum(),pageRequest.getPageSize(), Sort.by(Sort.Order
				.asc("createTime")));
		return repository.findAll(pageable);
	}
	
	@Override
	public void saveBatch(List <T> list) {
		repository.saveAll(list);
	}
	
	@Override
	public void deleteByIdIn(List <String> idList) {
		for (String temp : idList){
			repository.deleteById(temp);
		}
	}
	
	@Override
	public List <T> getByIndIn(List <String> idList) {
		Iterable<T> temp = repository.findAllById(idList);
		List<T> result = new ArrayList <T>();
		if (ObjectUtils.allNotNull(temp)){
			temp.forEach(a -> {
				result.add(a);
			});
		}
		return result;
	}
}
