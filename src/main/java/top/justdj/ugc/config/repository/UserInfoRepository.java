/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.26
  Time: 18:23
  Info:
*/

package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.ugc.common.entity.UserInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.26
 * Time: 18:23
 */
@Repository("userInfoRepository")
public interface UserInfoRepository extends MongoRepository<UserInfo,String>{


	UserInfo findUserInfoByPhone(String phone);
}
