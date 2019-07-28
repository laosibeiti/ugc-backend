/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 13:36
  Info:
*/

package top.justdj.job.service.impl;

import org.springframework.stereotype.Service;
import top.justdj.common.entity.dto.ClickHistoryInfo;
import top.justdj.job.config.repository.ClickHistoryInfoRepository;
import top.justdj.job.service.ClickHistoryInfoService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 13:36
 */
@Service
public class ClickHistoryInfoServiceImpl extends CommonServiceImpl<ClickHistoryInfoRepository,ClickHistoryInfo>
implements ClickHistoryInfoService{
}
