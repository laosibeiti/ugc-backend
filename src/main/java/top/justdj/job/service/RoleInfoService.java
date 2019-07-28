/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 15:10
  Info:
*/

package top.justdj.job.service;

import top.justdj.common.entity.RoleInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 15:10
 */
public interface RoleInfoService {
	
	List<RoleInfo> selectByRoleIdIn(List<Integer> roleIdList);
	
}
