/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 21:46
  Info:
*/

package top.justdj.job.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.common.entity.JobInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 21:46
 */
@Repository
public interface JobInfoRepository extends MongoRepository<JobInfo,String> {





}