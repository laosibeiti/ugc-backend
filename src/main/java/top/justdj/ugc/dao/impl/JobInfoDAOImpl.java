/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 21:48
  Info:
*/

package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.JobInfo;
import top.justdj.ugc.dao.JobInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 21:48
 */
@Service
public class JobInfoDAOImpl extends AbstractCommonDAOImpl<JobInfo>
implements JobInfoDAO{
	
	public JobInfoDAOImpl() {
		super.setCOLLECTION();
	}
}
