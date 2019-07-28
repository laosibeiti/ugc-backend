package top.justdj.ugc.service;

import top.justdj.common.entity.EvaluationInfo;
import top.justdj.ugc.service.impl.EvaluationInfoServiceImpl;

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
