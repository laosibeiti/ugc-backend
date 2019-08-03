package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.ugc.common.entity.BlogInfo;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.3
 * Time: 9:55
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */
@Repository("blogInfoRepository")
public interface BlogInfoRepository extends MongoRepository<BlogInfo,String> {

}
