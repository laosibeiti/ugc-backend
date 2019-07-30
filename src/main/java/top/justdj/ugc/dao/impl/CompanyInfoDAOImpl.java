/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 11:37
  Info:
*/

package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.CompanyInfo;
import top.justdj.ugc.dao.CompanyInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 11:37
 */
@Service
public class CompanyInfoDAOImpl extends AbstractCommonDAOImpl<CompanyInfo>
implements CompanyInfoDAO{
	
	public CompanyInfoDAOImpl() {
		super.setCOLLECTION();
	}
}
