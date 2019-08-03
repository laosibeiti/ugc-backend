package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.BlogInfo;
import top.justdj.ugc.dao.BlogInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.3
 * Time: 9:49
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */
@Service
public class BlogInfoDAOImpl extends AbstractCommonDAOImpl<BlogInfo>
implements BlogInfoDAO {
	
	public BlogInfoDAOImpl() {
		super.setCOLLECTION();
	}
}
