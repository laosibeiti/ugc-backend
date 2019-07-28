/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 11:35
  Info:
*/

package top.justdj.ugc.service;

import top.justdj.common.entity.JobTypeInfo;
import top.justdj.common.entity.dto.DbSearchResultListId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 11:35
 */
public interface JobTypeInfoService  extends CommonService<JobTypeInfo>{

	
	List<JobTypeInfo> getByParentId(String parentId);
	
	/**
	 * 兼职类别下属兼职数量统计
	 * @return
	 */
	List<DbSearchResultListId> analyseJobTypeNum();
	
	
	/**
	 * 兼职类别热度统计
	 * @return
	 */
	List<JobTypeInfo> analyseJobTypeHotNum();
	
}
