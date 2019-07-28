/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 11:37
  Info:
*/

package top.justdj.job.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.common.entity.CompanyInfo;
import top.justdj.job.dao.CompanyInfoDAO;
import top.justdj.job.dao.common.AbstractCommonDAOImpl;
import top.justdj.job.dao.common.CommonDAO;

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
