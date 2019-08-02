package top.justdj.ugc.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.2
 * Time: 10:47
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */
@Data
@ApiModel("模块实体类")
@Document(collection = "module_info")
public class ModuleInfo extends BaseInfo {
	
	@ApiModelProperty("模块名")
	private String name;
	
	@ApiModelProperty("模块路径")
	private String url;
	
	@ApiModelProperty("模块组件名")
	private String vueComponent;
	
	@ApiModelProperty("模块等级")
	private Integer level;
	
	@ApiModelProperty("模块父级id")
	private String fatherId;
	
	@ApiModelProperty("模块图标")
	private String ico;
	
	@ApiModelProperty("模块排序规则")
	private Integer sort;
	
	@ApiModelProperty("模块备注")
	private String remark;
	
	@ApiModelProperty("模块状态 1 启用 0禁用")
	private Integer status;
	
}
