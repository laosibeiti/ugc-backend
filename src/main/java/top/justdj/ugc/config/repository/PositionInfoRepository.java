/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:01
  Info:
*/

package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.ugc.common.entity.PositionInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:01
 */
@Repository("positionInfoRepository")
public interface PositionInfoRepository extends MongoRepository<PositionInfo,String>{
	
	
	PositionInfo getByValue(String value);
	
}
