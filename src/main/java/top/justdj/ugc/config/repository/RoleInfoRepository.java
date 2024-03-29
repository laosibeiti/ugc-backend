package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.ugc.common.entity.RoleInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 15:09
 */
@Repository("roleInfoRepository")
public interface RoleInfoRepository extends MongoRepository<RoleInfo,String> {
	
	
	List<RoleInfo> getByIdIn(List<String> roleIdList);
	
}
