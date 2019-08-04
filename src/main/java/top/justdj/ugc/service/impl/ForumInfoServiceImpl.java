package top.justdj.ugc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.ForumInfo;
import top.justdj.ugc.config.repository.ForumInfoRepository;
import top.justdj.ugc.dao.ForumInfoDAO;
import top.justdj.ugc.service.ForumInfoService;
import org.springframework.data.mongodb.core.query.Query;

//import javax.management.Query;

@Service
public class ForumInfoServiceImpl extends CommonServiceImpl<ForumInfoRepository, ForumInfo> implements ForumInfoService {

    @Autowired
    private ForumInfoDAO forumInfoDAO;

    @Override
    public ForumInfo selectByTitle(String title){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        return forumInfoDAO.findOne(query);

    }
}
