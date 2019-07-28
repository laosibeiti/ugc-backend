/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.17
  Time: 11:59
  Info:
*/

package top.justdj.job.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.justdj.common.entity.JobTypeInfo;
import top.justdj.common.util.Result;
import top.justdj.job.service.JobTypeInfoService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.17
 * Time: 11:59
 */

@Slf4j
@RestController
@RequestMapping("/api/jobType")
@Api(value = "工作类型相关接口",tags = "工作类型相关接口")
public class JobTypeController {
	
	@Autowired
	private JobTypeInfoService jobTypeInfoService;
	
	@PostMapping("/")
	@ApiOperation("新增工作类型")
	@HystrixCommand(fallbackMethod = "addFallback")
	Result add(@RequestBody JobTypeInfo jobTypeInfo){
		JobTypeInfo old = jobTypeInfoService.getById(jobTypeInfo.getId());
		old.setId(null);
		old.setName(jobTypeInfo.getName());
		jobTypeInfoService.save(old);
		return Result.ok(old);
	}
	Result addFallback(@RequestBody JobTypeInfo jobTypeInfo){
		return Result.busy("新增工作类型");
	}
	
	
	@GetMapping("/")
	@ApiOperation("获取jobtype下拉列表")
	@HystrixCommand(fallbackMethod = "getJobTypeFallback",ignoreExceptions = NullPointerException.class)
	Result getJobType(){
		List<JobTypeInfo> all = jobTypeInfoService.getAll();
		List<JobTypeInfo> parent = all.stream().filter(a->a.getLevel() == 1).collect(Collectors.toList());
		List<JobTypeInfo> child = all.stream().filter(a->a.getLevel() == 2).collect(Collectors.toList());
		JSONArray jsonArray = new JSONArray();
		parent.forEach( a -> {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("label",a.getName());
			jsonObject.put("value",a.getId());
			JSONArray childArray = new JSONArray();
			List<JobTypeInfo> trueChild = child.stream().filter( b -> b.getParentId().equals(a.getId())).collect
					(Collectors.toList());
			trueChild.stream().forEach(c -> {
				JSONObject temp = new JSONObject();
				temp.put("label",c.getName());
				temp.put("value",c.getId());
				childArray.add(temp);
			});
			jsonObject.put("children",childArray);
			jsonArray.add(jsonObject);
		});
		return Result.ok(jsonArray);
	}
	Result getJobTypeFallback(){
		return Result.busy("获取jobType");
	}
	

	@DeleteMapping("/{id}")
	@ApiOperation("删除某个jobType")
	@HystrixCommand(fallbackMethod = "deleteFallback")
	Result delete(@PathVariable String id){
	
		JobTypeInfo jobTypeInfo = jobTypeInfoService.getById(id);
		if (null != jobTypeInfo){
			
			if (jobTypeInfo.getLevel() == 1){
				//删除子集
				List<JobTypeInfo> list = jobTypeInfoService.getByParentId(id);
				List<String> ids = list.stream().map(JobTypeInfo::getId).collect(Collectors.toList());
				if (!CollectionUtils.isEmpty(ids)){
					jobTypeInfoService.deleteByIdIn(ids);
				}
			}
			jobTypeInfoService.deleteById(id);
		}
		
		return Result.ok();
	}
	Result deleteFallback(@PathVariable String id){
		return Result.busy("删除jobType");
	}
	
	
	@PatchMapping("/")
	@ApiOperation("修改某个jobType")
	@HystrixCommand(fallbackMethod = "updateFallback")
	Result update(@RequestBody JobTypeInfo jobTypeInfo){
		JobTypeInfo old = jobTypeInfoService.getById(jobTypeInfo.getId());
		if (null != old){
			old.setName(jobTypeInfo.getName());
			jobTypeInfoService.save(old);
		}
		return Result.ok();
	}
	Result updateFallback(@RequestBody JobTypeInfo jobTypeInfo){
		return Result.busy("修改jobType");
	}
	
	
}
