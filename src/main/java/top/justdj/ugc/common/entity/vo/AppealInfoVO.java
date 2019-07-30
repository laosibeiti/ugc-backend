/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.15
  Time: 22:02
  Info:
*/

package top.justdj.ugc.common.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import top.justdj.ugc.common.entity.AppealInfo;
import top.justdj.ugc.common.entity.CompanyInfo;
import top.justdj.ugc.common.entity.JobInfo;
import top.justdj.ugc.common.entity.UserInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.15
 * Time: 22:02
 */
@Data
@ApiModel
public class AppealInfoVO extends AppealInfo {

	private UserInfo userInfo;
	
	private CompanyInfo companyInfo;
	
	private JobInfo jobInfo;
	
}
