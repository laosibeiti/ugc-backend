/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:17
  Info:
*/

package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.EvaluationInfo;
import top.justdj.ugc.dao.EvaluationInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:17
 */
@Service
public class EvaluationInfoDAOImpl extends AbstractCommonDAOImpl<EvaluationInfo>
		implements EvaluationInfoDAO {
	
	public EvaluationInfoDAOImpl() {
		super.setCOLLECTION();
	}
}
