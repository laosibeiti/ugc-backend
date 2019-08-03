package top.justdj.ugc.service;

import top.justdj.ugc.common.entity.BlogInfo;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.3
 * Time: 9:53
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */
public interface BlogInfoService extends CommonService<BlogInfo> {
	
	
	BlogInfo selectByTitle(String title);

}
