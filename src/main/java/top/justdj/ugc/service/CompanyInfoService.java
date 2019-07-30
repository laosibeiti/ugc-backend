package top.justdj.ugc.service;

import top.justdj.ugc.common.entity.CompanyInfo;
import top.justdj.ugc.common.entity.dto.CompanySignUpDTO;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 11:39
 */
public interface CompanyInfoService extends CommonService<CompanyInfo> {
	
	CompanyInfo saveCompany(CompanySignUpDTO signUpDTO);
}
