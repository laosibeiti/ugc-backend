/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:00
  Info:
*/

package top.justdj.job.dao.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.PositionInfo;
import top.justdj.job.dao.PositionInfoDAO;
import top.justdj.job.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:00
 */
@Service
public class PositionInfoDAOImpl extends AbstractCommonDAOImpl<PositionInfo>
implements PositionInfoDAO{
	
	public PositionInfoDAOImpl() {
		super.setCOLLECTION();
	}
}
