/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:14
  Info:
*/

package top.justdj.job.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.AppealInfo;
import top.justdj.common.entity.EvaluationInfo;
import top.justdj.job.config.repository.EvaluationInfoRepository;
import top.justdj.job.dao.EvaluationInfoDAO;
import top.justdj.job.service.EvaluationInfoService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:14
 */
@Slf4j
@Service
public class EvaluationInfoServiceImpl extends CommonServiceImpl<EvaluationInfoRepository,EvaluationInfo>
implements EvaluationInfoService{

	@Autowired
	private EvaluationInfoRepository evaluationInfoRepository;
	
	@Autowired
	private EvaluationInfoDAO evaluationInfoDAO;
	
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	private final String COLLECTION="evaluation_info";
	
	@Override
	public EvaluationInfo get(String userId, String jobId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("jobId").is(jobId));
		return evaluationInfoDAO.findOne(query);
	}
	
	@Override
	public List<ScoreResult>  getAvgScore(String companyId) {
		Aggregation aggregation  = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("companyId").is(companyId)),
				Aggregation.group("companyId").avg("score").as("score"),
				Aggregation.project("score"));
		AggregationResults<ScoreResult> infoAggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION,
				ScoreResult.class);
		List<ScoreResult> list = infoAggregationResults.getMappedResults();
		log.info("聚合结果 {}", JSON.toJSONString(list));
		return list;
	}
	
	public class ScoreResult{
		
		private String id;
		
		private Double score;
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public Double getScore() {
			return score;
		}
		
		public void setScore(Double score) {
			this.score = score;
		}
	}
}
