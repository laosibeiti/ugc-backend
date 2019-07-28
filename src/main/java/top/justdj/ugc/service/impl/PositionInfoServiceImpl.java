/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:05
  Info:
*/

package top.justdj.ugc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.PositionInfo;
import top.justdj.ugc.config.repository.PositionInfoRepository;
import top.justdj.ugc.dao.PositionInfoDAO;
import top.justdj.ugc.service.PositionInfoService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:05
 */
@Service
public class PositionInfoServiceImpl extends CommonServiceImpl<PositionInfoRepository,PositionInfo>
		implements PositionInfoService {
	
	@Autowired
	private PositionInfoDAO positionInfoDAO;
	
	@Autowired
	private PositionInfoRepository positionInfoRepository;
	
	
	@Override
	public PositionInfo getByCode(String code) {
		return positionInfoRepository.getByValue(code);
	}
}
