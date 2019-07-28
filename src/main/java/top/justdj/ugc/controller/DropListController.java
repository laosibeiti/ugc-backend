/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 10:49
  Info:
*/

package top.justdj.ugc.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.justdj.common.entity.DropList;
import top.justdj.common.entity.dto.DropListItemDTO;
import top.justdj.common.util.Result;
import top.justdj.ugc.service.DropListService;
import top.justdj.ugc.service.RedisService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 10:49
 */
@Slf4j
@RestController
@RequestMapping("")
@Api(value = "下拉列表管理相关的接口",tags = "下拉列表管理相关的接口")
public class DropListController {
	
	@Autowired
	private DropListService dropListService;
	
	@Autowired
	private RedisService redisService;
	
	@ApiOperation(value = "查询全部dropList信息",notes = "查询全部dropList信息")
	@GetMapping("/api/drop/all")
	public Result<List<DropList>> all(){
		List<DropList> list = dropListService.getAllDropListInfo();
		if(list == null){
			return Result.error();
		}else{
			return Result.ok(list);
		}
	}
	
	
	@ApiOperation(value = "根据key查询下拉列表",notes = "根据key查询下拉列表")
	@GetMapping(value = "/api/drop/one/{key}")
	@HystrixCommand(fallbackMethod = "getByKeyFallback")
	public Result getByKey(@PathVariable String key){
		
		String result = redisService.get(key);
		if (StringUtils.isEmpty(result)){
			log.info("数据库读取");
			DropList obj = dropListService.getObjectByKey(key);
			if(obj != null){
				redisService.setAndExpire(key,JSON.toJSONString(obj.getValueLabel()),24 * 60 * 60L);
				return Result.ok(obj.getValueLabel());
			}else {
				return Result.error("下拉列表 " + key + " 不存在");
			}
		}else {
			log.info("缓存读取");
			return Result.ok(JSON.parse(result));
		}
	}
	public Result getByKeyFallback(String key){
		return Result.error(1,"下拉列表服务忙,请稍后重试");
	}
	
	
	@ApiOperation(value = "新增下拉列表",notes = "新增下拉列表")
	@PostMapping("/api/drop/dropList")
	public Result addList(@Valid @RequestBody DropList dropList){
		if (dropListService.addList(dropList)) {
			return Result.ok();
		} else {
			return Result.error();
		}
	}
	
	
	@ApiOperation(value = "删除下拉列表",notes = "删除下拉列表")
	@DeleteMapping("/api/drop/dropList/{id}")
	public Result delList(@PathVariable String id){
		if (dropListService.delList(id)) {
			return Result.ok();
		} else {
			return Result.error();
		}
	}
	
	
	@ApiOperation(value = "添加item",notes = "添加item")
	@PostMapping("/api/drop/")
	@ResponseBody
	public Result addItem(@Valid @RequestBody DropListItemDTO dropListItemDTO){
		if(dropListService.addItem(dropListItemDTO)){
			return Result.ok();
		}else {
			return Result.error();
		}
	}
	
	
	@ApiOperation(value = "修改item",notes = "修改item")
	@PatchMapping(value = "/api/drop/")
	@ResponseBody
	public Result updateItem(@Valid @RequestBody DropListItemDTO dropListItemDTO){
		if(dropListService.updateItem(dropListItemDTO)){
			return Result.ok();
		}else {
			return Result.error();
		}
	}
	

	@ApiOperation(value = "删除item",notes = "删除item")
	@DeleteMapping(value = "/api/drop/")
	public Result updateItem(@RequestParam String key,
	                         @RequestParam String value){
		if(dropListService.deleteItem(key,value)){
			return Result.ok();
		}else {
			return Result.error();
		}
	}
	
	
	
}
