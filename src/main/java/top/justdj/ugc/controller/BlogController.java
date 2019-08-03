package top.justdj.ugc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.justdj.ugc.common.entity.BlogInfo;
import top.justdj.ugc.common.entity.UserInfo;
import top.justdj.ugc.common.util.Result;
import top.justdj.ugc.service.BlogInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.3
 * Time: 9:22
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */
@Slf4j
@RestController
@RequestMapping("/api/blog")
@Api(value = "博客相关的接口",tags = "博客相关的接口")
public class BlogController extends BaseController {


	@Autowired
	private BlogInfoService blogInfoService;


	@ApiOperation("新建博客")
	@PostMapping("/")
	public Result add(@Valid  @RequestBody BlogInfo blogInfo,
	                  HttpServletRequest request){
		UserInfo userInfo = getUserInfo(request);
		blogInfo.setAuthorId(userInfo.getId());
		blogInfo.setAuthorName(userInfo.getName());
		blogInfoService.save(blogInfo);
		return Result.ok("新增成功");
	}
	
	


}
