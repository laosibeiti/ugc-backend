/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 11:35
  Info:
*/

package top.justdj.ugc.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.JobTypeInfo;
import top.justdj.common.entity.dto.DbSearchResultListId;
import top.justdj.ugc.config.repository.JobTypeInfoRepository;
import top.justdj.ugc.dao.JobTypeInfoDAO;
import top.justdj.ugc.service.JobTypeInfoService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 11:35
 */
@Slf4j
@Service
public class JobTypeInfoServiceImpl extends CommonServiceImpl<JobTypeInfoRepository,JobTypeInfo>
		implements JobTypeInfoService {
	
	@Autowired
	private JobTypeInfoDAO jobTypeInfoDAO;
	
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final String JOB_INFO="job_info";
	private final String JOB_TYPE_INFO="job_type_info";
	
	@Override
	public List<JobTypeInfo> getByParentId(String parentId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("parentId").is(parentId));
		return jobTypeInfoDAO.query(query);
		
	}
	
	@Override
	public List <DbSearchResultListId> analyseJobTypeNum() {
			Aggregation aggregation  = Aggregation.newAggregation(
					Aggregation.group("jobType").count().as("num"),
					Aggregation.project("jobType","num"));
			AggregationResults<DbSearchResultListId> infoAggregationResults = mongoTemplate.aggregate(aggregation,
					JOB_INFO, DbSearchResultListId.class);
			List<DbSearchResultListId> result = infoAggregationResults.getMappedResults();
			log.info("数据分析 聚合结果 {}", JSON.toJSONString(result));
			return result;
	}
	
	
	@Override
	public List <JobTypeInfo> analyseJobTypeHotNum() {
		PageRequest pageRequest = PageRequest.of(0,10);
		Query query = new Query();
		query.with(Sort.by(Sort.Order.desc("heat")));
		query.with(pageRequest);
		return jobTypeInfoDAO.query(query);
	}
}
