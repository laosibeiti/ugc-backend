/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.7
  Time: 10:29
  Info:
*/

package top.justdj.ugc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.justdj.ugc.common.util.Result;
import top.justdj.ugc.service.PositionInfoService;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.7
 * Time: 10:29
 */
@Slf4j
@RestController
@Api(value = "地区 定位相关接口", tags = "地区 定位相关接口")
@RequestMapping("/api/region")
public class RegionController {
	
	@Autowired
	private PositionInfoService positionInfoService;
	
	@PostMapping("/")
	@ApiOperation("获取地区fullName")
	Result getRegion(@RequestBody List<String> regionList){
		if (!CollectionUtils.isEmpty(regionList)){
			String temp = regionList.get(regionList.size() - 1);
			return Result.ok(positionInfoService.getByCode(temp));
		}else {
			return Result.ok();
		}
	}
	
	
}
