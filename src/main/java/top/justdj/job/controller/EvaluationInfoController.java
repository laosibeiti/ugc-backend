/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:23
  Info:
*/

package top.justdj.job.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import top.justdj.common.entity.CompanyInfo;
import top.justdj.common.entity.EvaluationInfo;
import top.justdj.common.entity.UserInfo;
import top.justdj.common.entity.dto.ClickHistoryInfo;
import top.justdj.common.entity.pagefilter.BasePageFilter;
import top.justdj.common.util.Result;
import top.justdj.job.service.CompanyInfoService;
import top.justdj.job.service.EvaluationInfoService;
import top.justdj.job.service.impl.EvaluationInfoServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.14
 * Time: 16:23
 */
@Slf4j
@RestController
@RequestMapping("")
@Api(value = "评价相关的接口",tags = "评价相关的接口")
public class EvaluationInfoController extends BaseController{


	@Autowired
	private EvaluationInfoService evaluationInfoService;
	
	@Autowired
	private CompanyInfoService companyInfoService;
	
	@ApiOperation("新增评价信息")
	@PostMapping("/tApi/evaluationInfo/")
	@HystrixCommand(fallbackMethod = "addFallback")
	public Result add(@RequestBody EvaluationInfo evaluationInfo,
	                  HttpServletRequest request) {
		UserInfo userInfo = getUserInfo(request);
		evaluationInfo.setUserId(userInfo.getId());
		evaluationInfoService.saveOrUpdate(evaluationInfo);
		// 重新计算公司评分
		List<EvaluationInfoServiceImpl.ScoreResult> list = evaluationInfoService.getAvgScore(evaluationInfo.getCompanyId
				());
		if (!CollectionUtils.isEmpty(list)){
			CompanyInfo companyInfo = companyInfoService.getById(list.get(0).getId());
			companyInfo.setScore(list.get(0).getScore());
			companyInfoService.saveOrUpdate(companyInfo);
		}
		return Result.ok();
	}
	public Result addFallback(@RequestBody EvaluationInfo evaluationInfo,
	                  HttpServletRequest request){
		return Result.busy("新增评价");
	}
	
	
	@ApiOperation("获取评价信息")
	@GetMapping("/tApi/evaluationInfo/{jobId}")
	@HystrixCommand(fallbackMethod = "getByIdFallback")
	public Result getById(@PathVariable String jobId,
	                      HttpServletRequest request){
		UserInfo userInfo = getUserInfo(request);
		EvaluationInfo evaluationInfo = evaluationInfoService.get(userInfo.getId(),jobId);
		return Result.ok(evaluationInfo);
	}
	public Result getByIdFallback(@PathVariable String jobId,
	                      HttpServletRequest request){
		return Result.busy("获取评价");
	}
	
	
	@ApiOperation("获取评价信息")
	@PostMapping("/api/evaluationInfo/detail")
	@HystrixCommand(fallbackMethod = "getByIdFallback")
	public Result getById(@RequestBody ClickHistoryInfo info){
		return Result.ok(evaluationInfoService.get(info.getUserId(),info.getJobId()));
	}
	public Result getByIdFallback(@RequestBody ClickHistoryInfo info){
		return Result.busy("获取评价");
	}
	
	
	@ApiOperation("简单无条件分页查询评价相关信息")
	@PostMapping("/tApi/evaluationInfo/simplePageFind")
	public Result<Page<EvaluationInfo>> simplePageFind(@RequestBody BasePageFilter pageRequest) {
		log.info("EvaluationInfoController 简单无条件分页查询 page:{},size:{}",pageRequest.getPageNum(),pageRequest.getPageSize());
		return Result.ok(evaluationInfoService.pageFind(pageRequest));
	}
	

}
