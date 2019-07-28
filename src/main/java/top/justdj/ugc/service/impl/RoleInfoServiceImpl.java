/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 15:10
  Info:
*/

package top.justdj.ugc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.RoleInfo;
import top.justdj.ugc.config.repository.RoleInfoRepository;
import top.justdj.ugc.dao.RoleInfoDAO;
import top.justdj.ugc.service.RoleInfoService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 15:10
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

	@Autowired
	private RoleInfoRepository roleInfoRepository;
	
	@Autowired
	private RoleInfoDAO roleInfoDAO;
	
	
	@Override
	public List<RoleInfo> selectByRoleIdIn(List <Integer> roleIdList) {
		return roleInfoRepository.getByRoleIdIn(roleIdList);
	}
}
