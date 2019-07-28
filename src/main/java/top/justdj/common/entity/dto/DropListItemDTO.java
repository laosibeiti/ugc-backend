/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.2.28
  Time: 11:16
  Info:
*/

package top.justdj.common.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.2.28
 * Time: 11:16
 */
@Data
@ApiModel("下拉列表条目")
public class DropListItemDTO {
	
	@ApiModelProperty(value = "",required = true,example = "mes_product_type")
	@NotBlank(message = "key 不能为空")
	private String key;
	
	@ApiModelProperty(value = "",required = true,example = "value")
	@NotBlank(message = "value 不能为空")
	private String value;
	
	@ApiModelProperty(value = "",required = true,example = "label")
	@NotBlank(message = "label 不能为空")
	private String label;
	
	
	
}
