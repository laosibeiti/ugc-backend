/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.17
  Time: 20:51
  Info:
*/

package top.justdj.job.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import top.justdj.common.util.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: justdj
 * @Email : top90982@gmail.com
 * Date: 19.2.17
 * Time: 20:51
 */
public class ResponseUtil {
	
	public static void responseReturn(HttpServletResponse response, Integer status, Result result) throws IOException {
		if (status == null) {
			status = HttpServletResponse.SC_OK;
		}
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(status);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
		out.flush();
		out.close();
	}
}