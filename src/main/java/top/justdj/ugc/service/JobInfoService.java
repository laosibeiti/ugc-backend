/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 21:49
  Info:
*/

package top.justdj.ugc.service;

import org.springframework.data.domain.Page;
import top.justdj.common.entity.JobInfo;
import top.justdj.common.entity.pagefilter.CompanyJobPageFilter;
import top.justdj.common.entity.pagefilter.JobPageFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 21:49
 */
public interface JobInfoService extends CommonService<JobInfo> {
	
	
	Page<JobInfo> pageFind(CompanyJobPageFilter filter);
	
	
	Page<JobInfo> pageFind(JobPageFilter pageRequest);
	
	
	/**
	 * 获取用户投递的所有兼职
	 * @param userId
	 * @return
	 */
	List<JobInfo> findAllSignUpJob(String userId);
}
