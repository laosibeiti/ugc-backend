package top.justdj.ugc.service;

import top.justdj.common.entity.PositionInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:05
 */
public interface PositionInfoService extends CommonService<PositionInfo> {
	
	

	PositionInfo getByCode(String code);
	
}
