package top.justdj.job.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.common.entity.JobTypeInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 11:36
 */
@Repository("jobTypeInfoRepository")
public interface JobTypeInfoRepository extends MongoRepository<JobTypeInfo,String> {


	
}
