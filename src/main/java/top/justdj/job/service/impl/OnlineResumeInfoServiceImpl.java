/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 9:27
  Info:
*/

package top.justdj.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.OnlineResumeInfo;
import top.justdj.job.config.repository.OnlineResumeInfoRepository;
import top.justdj.job.dao.OnlineResumeInfoDAO;
import top.justdj.job.service.OnlineResumeInfoService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 9:27
 */
@Service
public class OnlineResumeInfoServiceImpl extends CommonServiceImpl<OnlineResumeInfoRepository,OnlineResumeInfo>
		implements OnlineResumeInfoService {
	
	
	@Autowired
	private OnlineResumeInfoRepository onlineResumeInfoRepository;
	
	@Autowired
	private OnlineResumeInfoDAO onlineResumeInfoDAO;
	
	
	
}
