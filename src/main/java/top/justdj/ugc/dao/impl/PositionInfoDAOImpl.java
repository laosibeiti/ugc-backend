/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:00
  Info:
*/

package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.PositionInfo;
import top.justdj.ugc.dao.PositionInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

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
