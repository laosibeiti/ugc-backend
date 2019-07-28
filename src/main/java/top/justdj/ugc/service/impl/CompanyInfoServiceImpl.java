/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 11:39
  Info:
*/

package top.justdj.ugc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.CompanyInfo;
import top.justdj.common.entity.dto.CompanySignUpDTO;
import top.justdj.ugc.config.repository.CompanyInfoRepository;
import top.justdj.ugc.dao.CompanyInfoDAO;
import top.justdj.ugc.service.CompanyInfoService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 11:39
 */
@Service("companyInfoServiceImpl")
public class CompanyInfoServiceImpl extends CommonServiceImpl<CompanyInfoRepository,CompanyInfo>
implements CompanyInfoService{
	
	
	@Autowired
	private CompanyInfoRepository companyInfoRepository;
	
	@Autowired
	private CompanyInfoDAO companyInfoDAO;
	
	
	@Override
	public CompanyInfo saveCompany(CompanySignUpDTO signUpDTO) {
		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setContact(signUpDTO.getContract());
		companyInfo.setContactPhone(signUpDTO.getPhone());
		companyInfo.setCompanyName(signUpDTO.getCompanyName());
		companyInfo.setCompanyType(signUpDTO.getCompanyType());
		return companyInfoRepository.save(companyInfo);
	}
}
