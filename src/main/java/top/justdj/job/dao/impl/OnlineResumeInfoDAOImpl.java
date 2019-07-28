/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 9:24
  Info:
*/

package top.justdj.job.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.common.entity.OnlineResumeInfo;
import top.justdj.job.dao.OnlineResumeInfoDAO;
import top.justdj.job.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 9:24
 */
@Service
public class OnlineResumeInfoDAOImpl extends AbstractCommonDAOImpl<OnlineResumeInfo>
implements OnlineResumeInfoDAO{
	
	public OnlineResumeInfoDAOImpl() {
		super.setCOLLECTION();
	}
}
