package top.justdj.job.service;

import top.justdj.common.entity.AppealInfo;
import top.justdj.common.entity.EvaluationInfo;
import top.justdj.job.service.impl.EvaluationInfoServiceImpl;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:13
 */


public interface EvaluationInfoService extends CommonService<EvaluationInfo>{
	
	EvaluationInfo get(String userId, String jobId);
	
	List<EvaluationInfoServiceImpl.ScoreResult> getAvgScore(String companyId);
	
}
