/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.27
  Time: 10:29
  Info:
*/

package top.justdj.ugc.common.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.27
 * Time: 10:29
 */
@Data
@ApiModel("下拉列表")
@Document(collection = "drop_list")
public class DropList {
	
	
	@ApiModelProperty(value = "下拉列表的key",example = "deletable_list",required = true)
	@NotBlank(message = "key 不能为空")
	private String key;
	
	@ApiModelProperty(value = "名称",example = "可以删除的下拉列表",required = true)
	@NotBlank(message = "下拉列表名称 不能为空")
	private String name;
	
	@ApiModelProperty("下拉值")
	@JsonIgnore
	private List<JSONObject> valueLabel;
	
	@ApiModelProperty(value = "描诉",example = "一段描诉")
	private String des;
}
