/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.17
  Time: 13:24
  Info:
*/

package top.justdj.job.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.justdj.common.entity.JobTypeInfo;
import top.justdj.common.entity.dto.DbSearchResultListId;
import top.justdj.common.util.Result;
import top.justdj.job.service.JobInfoService;
import top.justdj.job.service.JobTypeInfoService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.17
 * Time: 13:24
 */

@Api(value = "数据统计分析相关的接口",tags = "数据统计分析相关的接口")
@Slf4j
@RestController
@RequestMapping("/tApi/dataAnalyse")
@RequiresRoles({"admin"})
public class DataAnalyseController {
	
	
	@Autowired
	private JobTypeInfoService jobTypeInfoService;
	
	@Autowired
	private JobInfoService jobInfoService;
	
	
	@GetMapping("/jobType")
	@HystrixCommand(fallbackMethod = "analyseJobTypeFallback")
	Result analyseJobType(){
		List<DbSearchResultListId> result = jobTypeInfoService.analyseJobTypeNum();
		Set<String> set = new HashSet <>();
		result.forEach(a->{
			set.addAll(a.getId());
		});
		List<JobTypeInfo> jobTypeInfoList = jobTypeInfoService.getByIndIn(new ArrayList <>(set));
		result.forEach(a -> {
			JobTypeInfo jobTypeInfo = jobTypeInfoList.stream().filter( b-> b.getId().equals(a.getId().get(a.getId()
					.size() - 1))).findAny().orElse(new JobTypeInfo());
			a.setName(jobTypeInfo.getName());
		});
		return Result.ok(result);
	}
	Result analyseJobTypeFallback(){
		return Result.busy("兼职类别数据分析");
	}
	
	
	@GetMapping("/jobTypeHot")
	@HystrixCommand(fallbackMethod = "analyseJobTypeHotFallback")
	Result analyseJobTypeHot(){
		return Result.ok(jobTypeInfoService.analyseJobTypeHotNum());
	}
	Result analyseJobTypeHotFallback(){
		return Result.busy("兼职热度数据分析");
	}
}
