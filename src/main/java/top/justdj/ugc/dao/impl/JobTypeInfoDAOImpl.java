/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 11:30
  Info:
*/

package top.justdj.ugc.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.JobTypeInfo;
import top.justdj.ugc.dao.JobTypeInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 11:30
 */
@Slf4j
@Service
public class JobTypeInfoDAOImpl extends AbstractCommonDAOImpl<JobTypeInfo>
implements JobTypeInfoDAO {

	JobTypeInfoDAOImpl(){
		super.setCOLLECTION();
	}
	
}
