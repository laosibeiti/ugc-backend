/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 9:29
  Info:
*/

package top.justdj.ugc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.justdj.common.entity.OnlineResumeInfo;
import top.justdj.common.entity.UserInfo;
import top.justdj.common.util.Result;
import top.justdj.ugc.service.OnlineResumeInfoService;
import top.justdj.ugc.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 9:29
 */
@Slf4j
@RestController
@Api(value = "在线简历相关接口",tags = "在线简历相关接口" )
public final class OnlineResumeInfoController extends BaseController {
	
	@Autowired
	private OnlineResumeInfoService onlineResumeInfoService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation("新增在线简历")
	@PostMapping("/tApi/onlineResumeInfo")
	public Result add(@RequestBody @ApiParam(required = true,value = "json字符串") OnlineResumeInfo onlineResumeInfo,
	                  HttpServletRequest request) {
		log.info("OnlineResumeInfoController 新增 onlineResumeInfo:{}",onlineResumeInfo);
		onlineResumeInfo = onlineResumeInfoService.saveOrUpdate(onlineResumeInfo);
		UserInfo userInfo = getLatestUserInfo(request);
		if (StringUtils.isEmpty(userInfo.getOnlineResumeInfoId())){
			userInfo.setOnlineResumeInfoId(onlineResumeInfo.getId());
			userService.saveOrUpdate(userInfo);
		}
		return Result.ok();
	}
	
	
	@ApiOperation("更新在线简历")
	@PatchMapping("/tApi/onlineResumeInfo")
	public Result update(@RequestBody @ApiParam(required = true,value = "json字符串") OnlineResumeInfo onlineResumeInfo,
	                     HttpServletRequest request) {
		log.info("OnlineResumeInfoController 更新 onlineResumeInfo:{}",onlineResumeInfo);
		UserInfo userInfo = getLatestUserInfo(request);
		onlineResumeInfo = onlineResumeInfoService.saveOrUpdate(onlineResumeInfo);
		if (StringUtils.isEmpty(userInfo.getOnlineResumeInfoId())){
			userInfo.setOnlineResumeInfoId(onlineResumeInfo.getId());
			userService.saveOrUpdate(userInfo);
		}
		return Result.ok();
	}
	
	@ApiOperation("删除在线简历")
	@DeleteMapping("/api/onlineResumeInfo/{id}")
	public Result delete(@PathVariable @ApiParam(required = true)String id){
		log.info("OnlineResumeInfoController 删除 id:{}",id);
		onlineResumeInfoService.deleteById(id);
		return Result.ok();
	}
	
	@ApiOperation("根据用户id查询在线简历")
	@GetMapping("/api/onlineResumeInfo/{userId}")
	public Result find(@PathVariable @ApiParam(required = true,value = "id")String userId){
		log.info("OnlineResumeInfoController 单个查询 id:{}",userId);
		UserInfo userInfo = userService.getById(userId);
		if (!StringUtils.isEmpty(userInfo.getOnlineResumeInfoId())){
			return Result.ok(onlineResumeInfoService.getById(userInfo.getOnlineResumeInfoId()));
		}else {
			return Result.error(-1,"用户没有在线简历");
		}
	}
	
	@ApiOperation("获取自己的在线简历")
	@GetMapping("/tApi/onlineResumeInfo")
	public Result findSelf(HttpServletRequest request){
		UserInfo userInfo = getUserInfo(request);
		if (StringUtils.isEmpty(userInfo.getOnlineResumeInfoId())){
			//还没有在线简历
			return Result.ok();
		}else {
			return Result.ok(onlineResumeInfoService.getById(userInfo.getOnlineResumeInfoId()));
		}
		
	}
	
}
