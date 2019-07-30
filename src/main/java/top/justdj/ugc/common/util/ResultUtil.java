/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:17
  Info:
*/

package top.justdj.ugc.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultUtil {
	
	public ResultUtil() {
	}
	
	public static String result(int code, String msg, Object data) {
		ResultData obj = new ResultData();
		obj.setCode(code);
		obj.setData(data);
		obj.setMsg(msg);
		log.info(JSONObject.toJSONString(obj));
		return JSONObject.toJSONString(obj, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
	}
	
	public static JSONObject resultJSON(int code, String msg, Object data) {
		JSONObject obj = new JSONObject();
		obj.put("code", code);
		obj.put("msg", msg);
		obj.put("data", data);
		log.info(obj.toJSONString());
		return obj;
	}
	
	public static JSONObject mobileResult(int code, String message, Object data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Code", code);
		jsonObject.put("MessageDTO", message);
		jsonObject.put("Data", data);
		return jsonObject;
	}
}
