/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.14
  Time: 16:23
  Info:
*/

package top.justdj.ugc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import top.justdj.common.entity.AppealInfo;
import top.justdj.common.entity.CompanyInfo;
import top.justdj.common.entity.JobInfo;
import top.justdj.common.entity.UserInfo;
import top.justdj.common.entity.dto.ClickHistoryInfo;
import top.justdj.common.entity.pagefilter.BasePageFilter;
import top.justdj.common.entity.vo.AppealInfoVO;
import top.justdj.common.util.Result;
import top.justdj.ugc.service.AppealInfoService;
import top.justdj.ugc.service.CompanyInfoService;
import top.justdj.ugc.service.JobInfoService;
import top.justdj.ugc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@Api(value = "申诉信息相关接口",tags = "申诉信息相关接口")
public class AppealInfoController extends BaseController{


	@Autowired
	private AppealInfoService appealInfoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyInfoService companyInfoService;
	
	@Autowired
	private JobInfoService jobInfoService;
	
	@ApiOperation("新增申诉信息")
	@PostMapping("/tApi/appealInfo/")
	@HystrixCommand(fallbackMethod = "addFallback")
	public Result add(@RequestBody AppealInfo appealInfo,
	                  HttpServletRequest request) {
		appealInfoService.saveOrUpdate(appealInfo);
		return Result.ok();
	}
	public Result addFallback(@RequestBody AppealInfo appealInfo,
	                  HttpServletRequest request){
		return Result.busy("新增申诉");
	}
	
	
	@ApiOperation("获取申诉信息")
	@GetMapping("/tApi/appealInfo/{jobId}")
	@HystrixCommand(fallbackMethod = "getByIdFallback")
	public Result getById(@PathVariable String jobId,
	                      HttpServletRequest request){
		UserInfo userInfo = getUserInfo(request);
		AppealInfo appealInfo = appealInfoService.get(userInfo.getId(),jobId);
		return Result.ok(appealInfo);
	}
	public Result getByIdFallback(@PathVariable String jobId,
	                      HttpServletRequest request){
		return Result.busy("获取申诉");
	}
	
	
	@ApiOperation("获取申诉信息")
	@PostMapping("/api/appealInfo/detail")
	@HystrixCommand(fallbackMethod = "getByIdFallback")
	public Result getById(@RequestBody ClickHistoryInfo info){
		return Result.ok(appealInfoService.get(info.getUserId(),info.getJobId()));
	}
	public Result getByIdFallback(@RequestBody ClickHistoryInfo info){
		return Result.busy("获取申诉");
	}
	
	
	@ApiOperation("简单无条件分页查询申诉相关信息")
	@PostMapping("/tApi/appealInfo/simplePageFind")
	@HystrixCommand(fallbackMethod = "simplePageFindFallback")
	public Result simplePageFind(@RequestBody BasePageFilter pageRequest) {
		log.info("AppealInfoController 简单无条件分页查询 page:{},size:{}",pageRequest.getPageNum(),pageRequest.getPageSize());
		Page page = appealInfoService.pageFind(pageRequest);
		List<AppealInfo> list = page.getContent();
		List<AppealInfoVO> result = new ArrayList <>();
		if (!CollectionUtils.isEmpty(list)){
			list.forEach(a ->{
				AppealInfoVO appealInfoVO = JSON.parseObject(JSON.toJSONString(a),AppealInfoVO.class);
				UserInfo userInfo  = userService.getById(appealInfoVO.getUserId());
				appealInfoVO.setUserInfo(userInfo);
				CompanyInfo companyInfo = companyInfoService.getById(appealInfoVO.getCompanyId());
				appealInfoVO.setCompanyInfo(companyInfo);
				JobInfo jobInfo = jobInfoService.getById(appealInfoVO.getJobId());
				appealInfoVO.setJobInfo(jobInfo);
				result.add(appealInfoVO);
			});
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("content",result);
		jsonObject.put("totalElements",page.getTotalElements());
		return Result.ok(jsonObject);
	}
	public Result simplePageFindFallback(@RequestBody BasePageFilter pageRequest){
		return Result.busy("分页查询申诉");
	}

}
