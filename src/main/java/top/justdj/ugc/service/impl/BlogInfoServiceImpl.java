package top.justdj.ugc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.BlogInfo;
import top.justdj.ugc.config.repository.BlogInfoRepository;
import top.justdj.ugc.dao.BlogInfoDAO;
import top.justdj.ugc.service.BlogInfoService;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.3
 * Time: 9:54
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */
@Service
public class BlogInfoServiceImpl  extends CommonServiceImpl<BlogInfoRepository, BlogInfo>
implements BlogInfoService {
	
	@Autowired
	private BlogInfoDAO blogInfoDAO;
	
	
	@Override
	public BlogInfo selectByTitle(String title) {
		Query query = new Query();
		query.addCriteria(Criteria.where("title").is(title));
		return blogInfoDAO.findOne(query);
	}
}
