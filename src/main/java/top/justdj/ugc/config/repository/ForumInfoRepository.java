package top.justdj.ugc.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.justdj.ugc.common.entity.ForumInfo;

@Repository("forumInfoRepository")
public interface ForumInfoRepository extends MongoRepository<ForumInfo, String> {
}
