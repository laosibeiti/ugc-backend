/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:42
  Info:
*/

package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.common.entity.DropList;
import top.justdj.ugc.dao.DropListDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:42
 */
@Service
public class DropListDAOImpl extends AbstractCommonDAOImpl<DropList>
implements DropListDAO{
	
	public DropListDAOImpl() {
		super.setCOLLECTION();
	}
	
}
