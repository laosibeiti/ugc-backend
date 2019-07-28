/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:19
  Info:
*/

package top.justdj.job.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.common.entity.AppealInfo;
import top.justdj.job.dao.AppealInfoDAO;
import top.justdj.job.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:19
 */
@Service
public class AppealInfoDAOImpl extends AbstractCommonDAOImpl<AppealInfo>
implements AppealInfoDAO{
	
	public AppealInfoDAOImpl() {
		super.setCOLLECTION();
	}
}
