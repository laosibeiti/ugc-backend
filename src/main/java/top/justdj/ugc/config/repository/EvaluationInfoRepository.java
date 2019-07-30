package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.ugc.common.entity.EvaluationInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:12
 */
@Repository
public interface EvaluationInfoRepository extends MongoRepository<EvaluationInfo,String>{
}
