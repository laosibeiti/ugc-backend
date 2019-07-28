/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.26
  Time: 18:37
  Info:
*/

package top.justdj.job.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.UserInfo;
import top.justdj.job.dao.UserInfoDAO;
import top.justdj.job.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.26
 * Time: 18:37
 */
@Slf4j
@Service
public class UserInfoDAOImpl extends AbstractCommonDAOImpl<UserInfo>
		implements UserInfoDAO {
	
	public UserInfoDAOImpl(){
		super.setCOLLECTION();
	}
	
}
