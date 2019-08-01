/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:14
  Info:
*/

package top.justdj.ugc.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import top.justdj.ugc.common.entity.UserInfo;
import top.justdj.ugc.common.entity.pagefilter.UserManagerPageFilter;
import top.justdj.ugc.service.UserService;
import top.justdj.ugc.common.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("")
@Api(value = "用户相关的接口",tags = "用户相关的接口")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	

	@PatchMapping("/tApi/user/headImg")
	@ApiOperation("用户修改头像")
	public Result updateHeadImg(@RequestBody String url,
	                            HttpServletRequest request){
		UserInfo userInfo = getLatestUserInfo(request);
		userInfo.setHeadImg(url);
		userService.saveOrUpdate(userInfo);
		return Result.ok();
	}
	
	
	@PatchMapping("/tApi/user/resumeUrl")
	@ApiOperation("用户修改简历url")
	@HystrixCommand(fallbackMethod = "updateResumeUrlFallback")
	public Result updateResumeUrl(@RequestBody String url,
	                            HttpServletRequest request){
		UserInfo userInfo = getLatestUserInfo(request);
		userInfo.setResumeUrl(url);
		userService.saveOrUpdate(userInfo);
		return Result.ok();
	}
	public Result updateResumeUrlFallback(@RequestBody String url,
	                              HttpServletRequest request){
		return Result.busy("用户修改简历");
	}
	
	
	
	@PostMapping("/tApi/user/pageFind")
	@ApiOperation("用户管理分页查询接口")
	@RequiresRoles({"admin"})
	@HystrixCommand(fallbackMethod = "pageFindFallback")
	public Result<Page<UserInfo>> pageFind(@Valid @RequestBody UserManagerPageFilter filter){
		return Result.ok(userService.pageFind(filter));
	}
	public Result<Page<UserInfo>> pageFindFallback(@Valid @RequestBody UserManagerPageFilter filter){
		return Result.busy("用户管理分页查询");
	}
	
	
	@DeleteMapping("/tApi/user/{id}")
	@ApiOperation("删除用户")
	@RequiresRoles({"admin"})
	public Result deleteUser(@Valid @PathVariable String id){
		userService.deleteById(id);
		return Result.ok();
	}
	
	@GetMapping("api/user/{id}")
	@ApiOperation(value = "获取单个用户信息",notes = "获取单个用户信息")
	@HystrixCommand(fallbackMethod = "getUserFallback")
	Result<UserInfo> getUser(@PathVariable String id){
		UserInfo userInfo = userService.getById(id);
		return Result.ok(userInfo);
	}
	Result<UserInfo> getUserFallback(@PathVariable String id){
		return Result.busy("获取单个用户");
	}
	
	
	@PostMapping("/api/user/group")
	@ApiOperation("获取一组用户的信息")
	@HystrixCommand(fallbackMethod = "getUerGroupFallback")
	Result<List<UserInfo>> getUerGroup(@RequestBody List<String> idsList){
		List<UserInfo> list = userService.getByIndIn(idsList);
		return Result.ok(list);
	}
	Result<List<UserInfo>> getUerGroupFallback(@RequestBody List<String> idsList){
		return Result.busy("获取用户");
	}
	
	@PatchMapping("/tApi/user/")
	@ApiOperation(value = "用户修改个人信息",notes = "用户修改个人信息")
	Result updateUseInfo(@Valid @RequestBody UserInfo userInfo,
	                     HttpServletRequest request){
		UserInfo old = userService.getById(userInfo.getId());
		old.setUserStatus(userInfo.getUserStatus());
		userService.saveOrUpdate(old);
		return Result.ok();
	}
	
	
	
}
