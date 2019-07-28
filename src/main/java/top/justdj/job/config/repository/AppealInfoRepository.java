/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:12
  Info:
*/

package top.justdj.job.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.common.entity.AppealInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:12
 */
@Repository
public interface AppealInfoRepository extends MongoRepository<AppealInfo,String>{
}
