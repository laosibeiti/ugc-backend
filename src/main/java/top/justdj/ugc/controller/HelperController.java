/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.25
  Time: 10:50
  Info:
*/

package top.justdj.ugc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.justdj.ugc.common.constant.PositionConstant;
import top.justdj.ugc.common.entity.PositionInfo;
import top.justdj.ugc.common.entity.UserInfo;
import top.justdj.ugc.config.shiro.ShiroConfig;
import top.justdj.ugc.service.*;
import top.justdj.ugc.common.util.Result;
import top.justdj.ugc.util.Md5Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.25
 * Time: 10:50
 */
@Slf4j
@RestController
@Api(value = "一些通用的接口",tags = "一些接口")
@RequestMapping("/api/universal")
public class HelperController extends BaseController{


	
	
	@Autowired
	private FileManagerService fileManagerService;
	
	
	@Autowired
	private PositionInfoService positionInfoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisService redisService;
	
	
	@GetMapping("/insertDataToRedis")
	@ApiOperation("插入数据到redis")
	@RequiresRoles("index")
	Result inserDataToRedis(){
		
		redisService.setAndExpire("test","qewqeq",60 * 1000);
		return Result.ok();
	}
	
	
	@GetMapping("/test")
	@ApiOperation(value = "测试用的接口",notes = "测试用的接口")
	Result testMethod (HttpServletRequest request) throws Exception{
		JSONObject jsonObject = new JSONObject();
		InetAddress address = InetAddress.getLocalHost();
		jsonObject.put("服务器地址 ",address.getHostAddress());
		Subject subject = SecurityUtils.getSubject();
		if (subject == null){
			jsonObject.put("用户状态 ","没有获取到session");
		}else {
			jsonObject.put("sessionId",subject.getSession());
			jsonObject.put("用户状态 ","获取到session");
			jsonObject.put("user",getUserInfo(request));
		}
		
		return Result.ok(jsonObject); //返回IP地址
		
	}
	
	@GetMapping("/insertUser")
	@ApiOperation(value = "插入新的用户",notes = "插入新的用户")
	Result insertUser() throws Exception{
		UserInfo userInfo = new UserInfo();
		userInfo.setName("万凯");
		userInfo.setSalt(Md5Utils.generateSalt());
		userInfo.setPassword(Md5Utils.encryption("123456",userInfo.getSalt()));
		userInfo.setEmail("977878634@qq.com");
		userService.save(userInfo);
		return Result.ok(userInfo);
		
	}
	
	
	
	@PostMapping("/upload")
	@ApiOperation(value = "上传文件用的接口",notes = "上传文件用的接口")
	Result uploadFile(@ApiParam(required = true)@RequestParam MultipartFile file) throws Exception{
		return Result.ok(fileManagerService.bomUploadFile(file.getOriginalFilename(),file.getInputStream()));
	}
	
	
	@GetMapping("/file/template")
	@ApiOperation(value = "获取志愿者导入模板",notes = "获取志愿者导入模板")
	void getTemplate(HttpServletResponse response){
		String fileName = "志愿者导入模板.xlsx";
		InputStream inputStream = fileManagerService.bomDownloadFile(fileName);
		log.info("文件 url {}",fileManagerService.getUrl(fileName));
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			bis = new BufferedInputStream(inputStream);
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, i);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			log.error("IO异常", e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					log.error("IO异常", e);
				}
			}
		}
	
	}
	
	
	
	@PostMapping("/testPosition")
	@ApiOperation("测试用的接口 插入地址数据")
	void testPosition(){
		
		JSONArray jsonArray = JSONArray.parseArray(PositionConstant.sb.toString());
		log.info("长度 {}",jsonArray.size());
		jsonArray.forEach(item -> {
			JSONObject jsonObject = (JSONObject)item;
			String label = jsonObject.get("label").toString();
			String value = jsonObject.get("value").toString();
			Object object = jsonObject.get("children");
			String child = "";
			if (ObjectUtils.allNotNull(object)){
				child = object.toString();
			}
			PositionInfo positionInfo1 = new PositionInfo();
			positionInfo1.setFullName(label);
			positionInfo1.setLabel(label);
			positionInfo1.setLevel(1);
			positionInfo1.setParentValue("");
			positionInfo1.setValue(value);
			positionInfoService.save(positionInfo1);
			log.info("level one {} " ,label);
			if(!StringUtils.isEmpty(child)){
				JSONArray temp2  = JSON.parseArray(child);
				temp2.forEach(second -> {
					JSONObject jsonObject2 = (JSONObject)second;
					String label2 = jsonObject2.get("label").toString();
					String value2 = jsonObject2.get("value").toString();
					Object object2 = jsonObject2.get("children");
					String child2 = "";
					if (ObjectUtils.allNotNull(object2)){
						child2 = object2.toString();
					}
					log.info("level two {} " ,(label+label2));
					PositionInfo positionInfo2 = new PositionInfo();
					positionInfo2.setFullName(label+label2);
					positionInfo2.setLabel(label2);
					positionInfo2.setLevel(2);
					positionInfo2.setParentValue(positionInfo1.getValue());
					positionInfo2.setValue(value2);
					positionInfoService.save(positionInfo2);
					if(!StringUtils.isEmpty(child2)){
						JSONArray temp3  = JSON.parseArray(child2);
						temp3.forEach(third -> {
							JSONObject jsonObject3 = (JSONObject)third;
							String label3 = jsonObject3.get("label").toString();
							String value3 = jsonObject3.get("value").toString();
//							String child3 = jsonObject3.get("children").toString();
							log.info("level three {}",(label + label2 + label3));
							PositionInfo positionInfo3 = new PositionInfo();
							positionInfo3.setFullName(label + label2 + label3);
							positionInfo3.setLabel(label3);
							positionInfo3.setLevel(3);
							positionInfo3.setParentValue(positionInfo2.getValue());
							positionInfo3.setValue(value3);
							positionInfoService.save(positionInfo3);
						});
					}
				});
			}
		});
		
		
	}
	

}
