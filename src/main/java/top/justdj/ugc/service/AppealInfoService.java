/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:19
  Info:
*/

package top.justdj.ugc.service;

import top.justdj.common.entity.AppealInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:19
 */
public interface AppealInfoService  extends CommonService<AppealInfo>{
	
	
	AppealInfo get(String userId,String jobId);
	
}
