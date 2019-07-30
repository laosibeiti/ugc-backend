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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.justdj.ugc.common.constant.PositionConstant;
import top.justdj.ugc.common.entity.JobTypeInfo;
import top.justdj.ugc.common.entity.PositionInfo;
import top.justdj.ugc.service.FileManagerService;
import top.justdj.ugc.common.util.Result;
import top.justdj.ugc.service.JobTypeInfoService;
import top.justdj.ugc.service.PositionInfoService;

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
public class HelperController {

	
	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private JobTypeInfoService jobTypeInfoService;
	
	@Autowired
	private PositionInfoService positionInfoService;
	
	
	@GetMapping("/test")
	@ApiOperation(value = "测试用的接口",notes = "测试用的接口")
	Result testMethod () throws Exception{
		InetAddress address = InetAddress.getLocalHost();
		return Result.ok(address.getHostAddress()); //返回IP地址
		
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
	

	@PostMapping("/testJobType")
	@ApiOperation("测试用的接口 插入工作类型数据")
	void test(){
	
		String temp = "[{\"name\":\"热门\",\"child\":[\"全部\",\"潮兼职\",\"传单派发\",\"服务员\",\"促销导购\",\"打包分拣\",\"礼仪模特\",\"话务客服\",\"家教助教\",\"充场\",\"问卷调查\",\"展会协助\"]},{\"name\":\"在家赚钱\",\"child\":[\"APP试玩\",\"优惠券推广\",\"在线问卷\",\"微商\",\"注册任务\",\"游戏推广\",\"POS机代理\",\"其他兼职\"]},{\"name\":\"简单易做\",\"child\":[\"传单派发\",\"地推拉访\",\"打包分拣\",\"充场\",\"问卷调查\",\"审核录入\",\"保洁\"]},{\"name\":\"跑腿办事\",\"child\":[\"挂号排队\",\"送餐员\",\"宠物寄养\",\"车票代购\"]},{\"name\":\"室内推荐\",\"child\":[\"客服经理\",\"在线客服\",\"前台\",\"客房服务员\",\"酒店经理\",\"招聘专员\",\"人事专员\",\"前台/接待\",\"行政\",\"收银员\",\"理货员\",\"营业员\",\"房产内勤\",\"话务客服\",\"促销导购\",\"文员\",\"展会协助\"]},{\"name\":\"体力达人\",\"child\":[\"搬运工\",\"仓库管理员\",\"洗碗工\",\"普工\",\"餐饮工\",\"服务员\",\"安保\",\"会展执行\",\"快递配送\"]},{\"name\":\"专业技能\",\"child\":[\"销售支持\",\"销售经理\",\"网络销售\",\"电话销售\",\"保险内勤\",\"英语翻译\",\"小语种翻译\",\"招生顾问\",\"客运司机\",\"驾校教练\",\"货运司机\",\"出租车司机\",\"咖啡师\",\"后厨\",\"传菜员\",\"厨师\",\"店长经理\",\"保姆\",\"美容助理\",\"美容店长\",\"美发助理\",\"化妆师\",\"发型师\",\"瑜伽教练\",\"游泳教练\",\"舞蹈老师\",\"台球教练\",\"房产店长\",\"产品经理\",\"测试工程师\",\"司机代驾\",\"技师技工\",\"美容美发\",\"月嫂阿姨\",\"销售\",\"房产经纪人\",\"保险经纪人\",\"在线老师\"]},{\"name\":\"演艺达人\",\"child\":[\"礼仪模特\",\"群众演员\",\"演出\",\"主播\"]},{\"name\":\"特色职位\",\"child\":[\"校园代理\",\"家教助教\",\"健身教练\",\"翻译\",\"婚礼主持\",\"摄影摄像\",\"旅行导游\",\"美工设计\",\"创意策划\",\"财务会计\",\"项目外包\",\"软件开发\"]},{\"name\":\"优秀青年\",\"child\":[\"志愿者\",\"救生员\",\"实习生\",\"社会义工\"]},{\"name\":\"其他\",\"child\":[\"临时工\",\"其他\"]}]";
		
		JSONArray jsonArray = JSONArray.parseArray(temp);
		log.info("长度 {}",jsonArray.size());
		jsonArray.forEach(item -> {
			JSONObject jsonObject = (JSONObject)item;
			String parent = jsonObject.get("name").toString();
			JobTypeInfo jobTypeInfo = new JobTypeInfo();
			jobTypeInfo.setName(parent);
			jobTypeInfo.setParentId("");
			jobTypeInfo.setHeat(0L);
			jobTypeInfo.setLevel(1);
			jobTypeInfoService.save(jobTypeInfo);
			List<String> child  = JSON.parseArray(jsonObject.get("child").toString(),String.class);
			child.forEach(a ->{
				JobTypeInfo job = new JobTypeInfo();
				job.setParentId(jobTypeInfo.getId());
				job.setName(a);
				job.setHeat(0L);
				job.setLevel(2);
				jobTypeInfoService.save(job);
			});
			log.info("parent {}  child {}",parent,child);
		});
	
	
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
