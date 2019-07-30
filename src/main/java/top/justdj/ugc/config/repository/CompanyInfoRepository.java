/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 11:35
  Info:
*/

package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.ugc.common.entity.CompanyInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 11:35
 */
@Repository
public interface CompanyInfoRepository extends MongoRepository<CompanyInfo,String> {





}
