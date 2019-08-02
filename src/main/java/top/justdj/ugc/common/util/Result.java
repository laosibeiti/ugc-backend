/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:21
  Info:
*/

package top.justdj.ugc.common.util;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.justdj.ugc.common.constant.ErrorConstant;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 单德金
 * @Email : shandejin@zhehekeji.com
 * Date: 19.1.3
 * Time: 16:21
 */
@Slf4j
@Data
@ApiModel("Response通用Bean")
public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("版本")
	private String version = "1.0";
	@ApiModelProperty("结果状态码")
	private Integer code;
	@ApiModelProperty("返回信息")
	private String msg;
	@ApiModelProperty("数据")
	private T data;
	
	public Result() {
		this.setCode(200);
		this.setMsg("请求成功");
		this.setVersion("1.0");
	}
	
	/**
	 * 基础成功提示
	 * @return
	 */
	public static  Result ok() {
		return new  Result();
	}
	
	/**
	 * 成功提示
	 *
	 * @param msg 提示信息
	 * @return
	 */
	public static <T>  Result <T> ok(String msg) {
		 Result <T> r = new  Result <>();
		r.setMsg(msg);
		log.info("操作成功 {}", JSON.toJSONString(r));
		return r;
	}
	
	public static<T>  Result loginOk(T t) {
		 Result <T> r = new  Result <>();
		r.setData(t);
		log.info("登录成功 {}", JSON.toJSONString(r));
		return r;
	}
	
	/**
	 * 成功提示
	 *
	 * @param obj data
	 * @return
	 */
	public static <T>  Result <T> ok(T obj) {
		 Result <T> r = new  Result <>();
		r.setData(obj);
		log.info("数据请求成功 {}", JSON.toJSONString(r));
		return r;
	}
	
	
	/**
	 * 错误提示
	 *
	 * @return
	 */
	public static <T>  Result <T> error() {
		log.info("未知错误 ");
		return error(ErrorConstant.UNKNOWN_ERROR.getCode(), ErrorConstant
				.UNKNOWN_ERROR.getMsg());
	}
	
	/**
	 * 错误提示
	 *
	 * @param msg 提示信息
	 * @return
	 */
	public static <T>  Result <T> error(String msg) {
		return error(-1, msg);
	}
	
	
	/**
	 * 没有token
	 *
	 * @return
	 */
	public static <T>  Result <T> noToken() {
		return error(ErrorConstant.NO_TOKEN.getCode(), ErrorConstant.NO_TOKEN.getMsg());
	}
	
	
	/**
	 * 没有权限
	 *
	 * @return
	 */
	public static <T>  Result <T> noPermission() {
		return error(ErrorConstant.NO_PERMISSION.getCode(), ErrorConstant.NO_PERMISSION.getMsg());
	}
	
	
	
	/**
	 * 错误提示
	 *
	 * @return
	 */
	public static <T>  Result <T> tokenExpire() {
		return error(ErrorConstant.TOKEN_EXPIRE.getCode(), ErrorConstant
				.TOKEN_EXPIRE.getMsg());
	}
	
	/**
	 * 错误提示
	 *
	 * @param code 返回码
	 * @param msg  提示信息
	 * @return
	 */
	public static <T>  Result <T> error(int code, String msg) {
		 Result <T> r = new  Result <>();
		r.setMsg(msg);
		r.setCode(code);
		r.setData(null);
		log.info("发生错误 {}", JSON.toJSONString(r));
		return r;
	}
	
	
	
	public static <T>  Result <T> busy(String item) {
		 Result <T> r = new  Result <>();
		r.setMsg(item + "服务忙,请稍后重试.");
		r.setCode(1);
		r.setData(null);
		log.info("发生错误 服务忙{}", JSON.toJSONString(r));
		return r;
	}
	
	
}
