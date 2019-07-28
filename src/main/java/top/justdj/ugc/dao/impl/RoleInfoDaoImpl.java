/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 14:54
  Info:
*/

package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.common.entity.RoleInfo;
import top.justdj.ugc.dao.RoleInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 14:54
 */
@Service
public class RoleInfoDaoImpl extends AbstractCommonDAOImpl<RoleInfo>
implements RoleInfoDAO{
	
	public RoleInfoDaoImpl() {
		super.setCOLLECTION();
	}
}
