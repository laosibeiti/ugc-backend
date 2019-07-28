/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.9
  Time: 16:36
  Info:
*/

package top.justdj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.9
 * Time: 16:36
 */
@Data
@ApiModel
@Document(collection = "message_info")
public class MessageInfo extends BaseInfo {
	
	
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
	
	@ApiModelProperty("是否已读")
	private Boolean read;
	
}
