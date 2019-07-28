/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 13:28
  Info:
*/

package top.justdj.job.service.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.JobInfo;
import top.justdj.common.entity.JobTypeInfo;
import top.justdj.common.entity.dto.ClickHistoryInfo;
import top.justdj.job.service.ClickHistoryInfoService;
import top.justdj.job.service.JobInfoService;
import top.justdj.job.service.JobTypeInfoService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 13:28
 */
@Slf4j
@Service
public class Consumer {
	
	@Autowired
	private ClickHistoryInfoService clickHistoryInfoService;
	
	@Autowired
	private JobInfoService jobInfoService;
	
	@Autowired
	private JobTypeInfoService jobTypeInfoService;
	
	@JmsListener(destination = "clickHistory")
	public void receiveQueue(String message) {
		log.info("消费消息 {} ",message);
		ClickHistoryInfo clickHistoryInfo = null;
		try {
			clickHistoryInfo = JSON.parseObject(message,ClickHistoryInfo.class);
		}catch (Exception e){
			log.error("消息格式错误");
		}
		if (clickHistoryInfo != null){
			if (!(StringUtils.isEmpty(clickHistoryInfo.getJobId()) || StringUtils.isEmpty(clickHistoryInfo.getUserId
					()))){
				JobInfo jobInfo = jobInfoService.getById(clickHistoryInfo.getJobId());
				if (jobInfo != null){
					jobInfo.setPopularScore(jobInfo.getPopularScore() + 1);
					jobInfoService.saveOrUpdate(jobInfo);
					List<String> ids = jobInfo.getJobType();
					if (!CollectionUtils.isEmpty(ids)){
						//更新兼职所属分类热度
						JobTypeInfo jobTypeInfo = jobTypeInfoService.getById(ids.get(ids.size() - 1));
						Long heat = jobTypeInfo.getHeat();
						if (heat == null){
							heat =0L;
						}
						jobTypeInfo.setHeat(heat + 1);
						jobTypeInfoService.saveOrUpdate(jobTypeInfo);
					}
				}
				clickHistoryInfoService.save(clickHistoryInfo);
			}
		}
		
	}
	
}
