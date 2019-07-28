/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.1.3
  Time: 16:21
  Info:
*/

package top.justdj.common.util;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.justdj.common.constant.ErrorConstant;

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
	public static top.justdj.common.util.Result ok() {
		return new top.justdj.common.util.Result();
	}
	
	/**
	 * 成功提示
	 *
	 * @param msg 提示信息
	 * @return
	 */
	public static <T> top.justdj.common.util.Result <T> ok(String msg) {
		top.justdj.common.util.Result <T> r = new top.justdj.common.util.Result <>();
		r.setMsg(msg);
		log.info("操作成功 {}", JSON.toJSONString(r));
		return r;
	}
	
	public static<T> top.justdj.common.util.Result loginOk(T t) {
		top.justdj.common.util.Result <T> r = new top.justdj.common.util.Result <>();
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
	public static <T> top.justdj.common.util.Result <T> ok(T obj) {
		top.justdj.common.util.Result <T> r = new top.justdj.common.util.Result <>();
		r.setData(obj);
		log.info("数据请求成功 {}", JSON.toJSONString(r));
		return r;
	}
	
	
	/**
	 * 错误提示
	 *
	 * @return
	 */
	public static <T> top.justdj.common.util.Result <T> error() {
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
	public static <T> top.justdj.common.util.Result <T> error(String msg) {
		return error(-1, msg);
	}
	
	
	/**
	 * 错误提示
	 *
	 * @return
	 */
	public static <T> top.justdj.common.util.Result <T> noToken() {
		return error(ErrorConstant.NO_TOKEN.getCode(), ErrorConstant.NO_TOKEN.getMsg());
	}
	
	
	/**
	 * 错误提示
	 *
	 * @return
	 */
	public static <T> top.justdj.common.util.Result <T> tokenExpire() {
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
	public static <T> top.justdj.common.util.Result <T> error(int code, String msg) {
		top.justdj.common.util.Result <T> r = new top.justdj.common.util.Result <>();
		r.setMsg(msg);
		r.setCode(code);
		r.setData(null);
		log.info("发生错误 {}", JSON.toJSONString(r));
		return r;
	}
	
	
	
	public static <T> top.justdj.common.util.Result <T> busy(String item) {
		top.justdj.common.util.Result <T> r = new top.justdj.common.util.Result <>();
		r.setMsg(item + "服务忙,请稍后重试.");
		r.setCode(1);
		r.setData(null);
		log.info("发生错误 服务忙{}", JSON.toJSONString(r));
		return r;
	}
	
	
}
