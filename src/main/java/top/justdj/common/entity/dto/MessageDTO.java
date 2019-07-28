/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.8
  Time: 21:53
  Info:
*/

package top.justdj.common.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.8
 * Time: 21:53
 */
@Data
@ApiModel("webSocket传递信息实体类")
public class MessageDTO {
	
	@ApiModelProperty("类型 0 群发 1单发")
	private Integer toUserType;
	
	@ApiModelProperty("类型 -2用户不在线 -1连接回复信息  0 系统通知 1 聊天信息 2 错误信息")
	private Integer messageType;
	
	@ApiModelProperty("发送者id")
	private String from;
	
	@ApiModelProperty("接收者id")
	private String to;
	
	@ApiModelProperty("发送者名称")
	private String fromUserName;
	
	@ApiModelProperty("接收者名称")
	private String toUserName;
	
	@ApiModelProperty("数据")
	private String data;
	
	public static top.justdj.common.entity.dto.MessageDTO linkSuccess(String toUser){
		top.justdj.common.entity.dto.MessageDTO message = new top.justdj.common.entity.dto.MessageDTO();
		message.setFrom("system");
		message.setTo(toUser);
		message.setMessageType(-1);
		message.setToUserType(1);
		message.setData("连接成功");
		return message;
	}
	
	public static top.justdj.common.entity.dto.MessageDTO linkError(String toUser){
		top.justdj.common.entity.dto.MessageDTO message = new top.justdj.common.entity.dto.MessageDTO();
		message.setFrom("system");
		message.setTo(toUser);
		message.setMessageType(1);
		message.setMessageType(0);
		message.setData("webSocket IO异常 连接失败");
		return message;
	}
	
}
