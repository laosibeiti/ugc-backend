package top.justdj.job.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.common.entity.dto.ClickHistoryInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 13:35
 */
@Repository
public interface ClickHistoryInfoRepository extends MongoRepository<ClickHistoryInfo,String> {
}
