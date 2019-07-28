/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 9:23
  Info:
*/

package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.common.entity.OnlineResumeInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 9:23
 */

@Repository
public interface OnlineResumeInfoRepository extends MongoRepository<OnlineResumeInfo,String> {





}
