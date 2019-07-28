/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:30
  Info:
*/

package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.common.entity.DropList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:30
 */
@Repository("dropListRepository")
public interface DropListRepository extends MongoRepository<DropList,String>{



}
