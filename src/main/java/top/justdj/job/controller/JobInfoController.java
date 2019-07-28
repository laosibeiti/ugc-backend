/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 21:51
  Info:
*/

package top.justdj.job.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import top.justdj.common.constant.UserRoleConstant;
import top.justdj.common.entity.JobInfo;
import top.justdj.common.entity.UserInfo;
import top.justdj.common.entity.dto.CompanySelectUserDTO;
import top.justdj.common.entity.pagefilter.BasePageFilter;
import top.justdj.common.entity.pagefilter.CompanyJobPageFilter;
import top.justdj.common.entity.pagefilter.JobPageFilter;
import top.justdj.common.util.Result;
import top.justdj.job.service.JobInfoService;
import top.justdj.job.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 21:51
 */
@Slf4j
@RestController
@Api(value = "兼职相关信息相关接口",tags = "兼职相关信息相关接口" )
@RequestMapping("")
public  class JobInfoController extends BaseController {
	
	@Autowired
	private JobInfoService jobInfoService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation("新增兼职相关信息")
	@PostMapping("/tApi/jobInfo/")
	@HystrixCommand(fallbackMethod = "addFallback")
	@RequiresRoles(value = {"company","admin"},logical = Logical.OR)
	public Result add(@RequestBody JobInfo jobInfo,
	                  HttpServletRequest request) {
		UserInfo userInfo = getUserInfo(request);
		jobInfo.setCompanyId(userInfo.getCompanyId());
		log.info("JobInfoController 新增 jobInfo:{}",jobInfo);
		if (StringUtils.isEmpty(jobInfo.getId())){
			jobInfo.setCreateUserId(userInfo.getId());
		}else {
			jobInfo.setUpdateUserId(userInfo.getId());
		}
		jobInfo = jobInfoService.save(jobInfo);
		return Result.ok();
	}
	
	public Result addFallback( JobInfo jobInfo, HttpServletRequest request) {
		return Result.busy("新增兼职");
	}
	
	
	@ApiOperation("更新兼职相关信息")
	@PatchMapping("/tApi/jobInfo/")
	@RequiresRoles({"company"})
	@HystrixCommand(fallbackMethod = "updateFallback")
	public Result update(@RequestBody JobInfo jobInfo) {
		log.info("JobInfoController 更新 jobInfo:{}",jobInfo);
		 jobInfoService.saveOrUpdate(jobInfo);
		return Result.ok();
	}
	public Result updateFallback(@RequestBody JobInfo jobInfo) {
		return Result.busy("更新兼职");
	}
	
	@ApiOperation("删除兼职相关信息")
	@DeleteMapping("/tApi/jobInfo/{id}")
	@RequiresRoles(value = {"company","admin"},logical = Logical.OR)
	public Result delete(@PathVariable String id){
		log.info("JobInfoController 删除 id:{}",id);
		jobInfoService.deleteById(id);
		return Result.ok();
	}
	
	@ApiOperation("查询兼职相关信息")
	@GetMapping("/api/jobInfo/{id}")
	@HystrixCommand(fallbackMethod = "findFallback")
	public Result<JobInfo> find(@PathVariable String id){
		log.info("JobInfoController 单个查询 id:{}",id);
		JobInfo jobInfo = jobInfoService.getById(id);
		return Result.ok(jobInfo);
	}
	public Result<JobInfo> findFallback( String id){
		return Result.busy("查询兼职");
	}
	
	
	@ApiOperation("用户收藏或取消收藏兼职")
	@GetMapping("/tApi/jobInfo/collection/{id}")
	@HystrixCommand(fallbackMethod = "findCollectionFallback")
	public Result<JobInfo> find(@PathVariable String id,
	HttpServletRequest request ){
		log.info("JobInfoController 用户收藏或取消收藏兼职 id:{}",id);
		UserInfo userInfo = getLatestUserInfo(request);
		List<String> list = userInfo.getJobId();
		if (CollectionUtils.isEmpty(list)){
			list = new ArrayList <>();
		}
		if (list.contains(id)){
			list.remove(id);
		}else {
			list.add(id);
		}
		userInfo.setJobId(list);
		userService.saveOrUpdate(userInfo);
		log.info("JobInfoController 用户收藏或取消收藏兼职 id:{}",id);
		return Result.ok();
	}
	public Result<JobInfo> findCollectionFallback(@PathVariable String id,
	                            HttpServletRequest request ){
		return Result.busy("用户收藏");
	}
	
	
	@ApiOperation("用户报名")
	@GetMapping("/tApi/jobInfo/signUp/{jobId}")
	@HystrixCommand(fallbackMethod = "signUpJobFallback")
	public Result<JobInfo> signUpJob(@PathVariable String jobId,
	                            HttpServletRequest request ){
		log.info("JobInfoController 用户报名 id:{}",jobId);
		UserInfo userInfo = getUserInfo(request);
		JobInfo jobInfo = jobInfoService.getById(jobId);
		if (jobInfo != null){
			List<String> list = jobInfo.getAppliedUserId();
			if (CollectionUtils.isEmpty(list)){
				list = new ArrayList <>();
			}
			if (!list.contains(userInfo.getId())) {
				list.add(userInfo.getId());
				jobInfo.setAppliedUserId(list);
				jobInfoService.saveOrUpdate(jobInfo);
			}
			return Result.ok("报名成功");
		}else {
			return Result.error("兼职不存在");
		}
	}
	public Result<JobInfo> signUpJobFallback( String jobId,
	                                 HttpServletRequest request ){
		return Result.busy("用户报名");
	}
	
	
	@ApiOperation("简单无条件分页查询兼职相关信息")
	@PostMapping("/tApi/jobInfo/simplePageFind")
	public Result<Page<JobInfo>> simplePageFind(@RequestBody BasePageFilter pageRequest) {
		
		log.info("JobInfoController 简单无条件分页查询 page:{},size:{}",pageRequest.getPageNum(),pageRequest.getPageSize());
		return Result.ok(jobInfoService.pageFind(pageRequest));
	}
	
	
	
	@ApiOperation("公司分页查询兼职相关信息")
	@PostMapping("/tApi/jobInfo/company/pageFind")
	@RequiresRoles(value = {"company","admin"},logical = Logical.OR)
//	@HystrixCommand(fallbackMethod = "companyPageFindFallback")
	public Result<Page<JobInfo>> companyPageFind(@RequestBody CompanyJobPageFilter filter,
	                                             HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		log.info("JobInfoController 公司兼职分页查询 page:{}", JSON.toJSONString(filter));
		if (subject.hasRole(UserRoleConstant.ADMIN.getMsg())){
			//不处理
			filter.setIsAdmin(true);
		}else {
			filter.setCompanyId(getLatestUserInfo(request).getCompanyId());
			filter.setIsAdmin(false);
		}
		return Result.ok(jobInfoService.pageFind(filter));
	}
//	public Result<Page<JobInfo>> companyPageFindFallback(@RequestBody CompanyJobPageFilter filter,
//	                                             HttpServletRequest request){
//		return Result.busy("公司分页查询兼职");
//	}
	
	
	@ApiOperation("用户分页查询兼职相关信息")
	@PostMapping("/api/jobInfo/normal/pageFind")
//	@HystrixCommand(fallbackMethod = "normalPageFindFindFallback")
	public Result<Page<JobInfo>> normalPageFind(@RequestBody JobPageFilter filter,
	                                             HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		log.info("JobInfoController 用户分页查询兼职相关信息 page:{}", JSON.toJSONString(filter));
		return Result.ok(jobInfoService.pageFind(filter));
	}
	public Result<Page<JobInfo>> normalPageFindFindFallback(@RequestBody JobPageFilter filter,
	                                            HttpServletRequest request){
		return Result.busy("用户分页查询兼职");
	}
	
	
	@PostMapping("/tApi/jobInfo/collection")
	@ApiOperation("获取用户收藏的所有兼职")
	@HystrixCommand(fallbackMethod = "getAllCollectionJobFindFindFallback")
	public Result<List<JobInfo>> getAllCollectionJob(HttpServletRequest request){
		UserInfo userInfo = getLatestUserInfo(request);
		List<JobInfo> jobInfoList = new ArrayList <>();
		if (!CollectionUtils.isEmpty(userInfo.getJobId())){
			jobInfoList  = jobInfoService.getByIndIn(userInfo.getJobId());
		}
		return Result.ok(jobInfoList);
	}
	public Result<List<JobInfo>> getAllCollectionJobFindFindFallback(HttpServletRequest request){
		return Result.busy("获取用户收藏的所有兼职");
	}
	
	
	@PostMapping("/tApi/jobInfo/signUp")
	@ApiOperation("获取用户报名的所有兼职")
	@HystrixCommand(fallbackMethod = "getAllSignUpJobFallback")
	public Result<List<JobInfo>> getAllSignUpJob(HttpServletRequest request){
		UserInfo userInfo = getLatestUserInfo(request);
		return Result.ok(jobInfoService.findAllSignUpJob(userInfo.getId()));
	}
	public Result<List<JobInfo>> getAllSignUpJobFallback(HttpServletRequest request){
		return Result.busy("获取用户报名的所有兼职");
	}
	
	
	@PostMapping("/tApi/jobInfo/select")
	@ApiOperation("公司选取报名用户")
	@RequiresRoles({"company"})
	@HystrixCommand(fallbackMethod = "selectUserFallback")
	public Result selectUser(@RequestBody CompanySelectUserDTO select){
		log.info("公司选取报名用户 {}",JSON.toJSONString(select));
		JobInfo jobInfo = jobInfoService.getById(select.getJobId());
		if (jobInfo != null){
			// TODO: 19.3.13 这里可能会出现同步问题
			jobInfo.setSelectedUserId(select.getUserIdList());
			jobInfoService.saveOrUpdate(jobInfo);
		}
		return Result.ok();
	}
	public Result selectUserFallback(@RequestBody CompanySelectUserDTO select){
		return Result.busy("公司选取报名用户");
	}
	
}
