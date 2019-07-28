/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:21
  Info:
*/

package top.justdj.ugc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.AppealInfo;
import top.justdj.ugc.config.repository.AppealInfoRepository;
import top.justdj.ugc.dao.AppealInfoDAO;
import top.justdj.ugc.service.AppealInfoService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:21
 */
@Service
public class AppealInfoServiceImpl extends CommonServiceImpl<AppealInfoRepository,AppealInfo>
implements AppealInfoService{
	
	
	@Autowired
	private AppealInfoRepository appealInfoRepository;
	
	@Autowired
	private AppealInfoDAO appealInfoDAO;
	
	
	@Override
	public AppealInfo get(String userId, String jobId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("jobId").is(jobId));
		return appealInfoDAO.findOne(query);
	}
}
