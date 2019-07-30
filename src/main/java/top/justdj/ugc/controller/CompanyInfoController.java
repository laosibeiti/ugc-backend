/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 11:41
  Info:
*/

package top.justdj.ugc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.justdj.ugc.common.entity.CompanyInfo;
import top.justdj.ugc.common.util.Result;
import top.justdj.ugc.service.CompanyInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 11:41
 */
@Slf4j
@RestController
@Api(value = "公司详细信息相关接口",tags = "公司详细信息相关接口" )
@RequestMapping("/api/companyInfo")
public  class CompanyInfoController extends BaseController {
	
	@Autowired
	private CompanyInfoService companyInfoService;
	
	
	@ApiOperation("新增公司详细信息")
	@PostMapping("/")
	@HystrixCommand(fallbackMethod = "addFallback")
	public Result add(@RequestBody CompanyInfo companyInfo,
	                  HttpServletRequest request) {
		log.info("CompanyInfoController 新增 companyInfo:{}",companyInfo);
		return Result.ok(companyInfoService.saveOrUpdate(companyInfo));
	}
	public Result addFallback(CompanyInfo companyInfo,
	                  HttpServletRequest request) {
		return Result.busy("公司新增");
	}
	
	
	@ApiOperation("更新公司详细信息")
	@PatchMapping("/")
	public Result update(@RequestBody  CompanyInfo companyInfo,
	                     HttpServletRequest request) {
		log.info("CompanyInfoController 更新 companyInfo:{}",companyInfo);
		return Result.ok(companyInfoService.saveOrUpdate(companyInfo));
	}
	
	
	@ApiOperation("查询公司详细信息")
	@GetMapping("/{id}")
	@HystrixCommand(fallbackMethod = "findFallback")
	public Result<CompanyInfo> find(@PathVariable @ApiParam(required = true,value = "id")String id){
		log.info("CompanyInfoController 单个查询 id:{}",id);
		return Result.ok(companyInfoService.getById(id));
	}
	public Result<CompanyInfo> findFallback(String id){
		return Result.busy("公司查询");
	}
	
	
	@ApiOperation("获取全部公司列表")
	@GetMapping("/all")
	@HystrixCommand(fallbackMethod = "getCompanyListFallback")
	public Result getCompanyList(){
		List<CompanyInfo> list =  companyInfoService.getAll();
		JSONArray jsonArray = new JSONArray();
		list.forEach(a -> {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("label",a.getCompanyName());
			jsonObject.put("value",a.getId());
			jsonArray.add(jsonObject);
		});
		return Result.ok(jsonArray);
	}
	public Result getCompanyListFallback(){
		return Result.busy("公司列表查询");
	}
	
}