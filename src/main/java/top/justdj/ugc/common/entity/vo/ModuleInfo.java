package top.justdj.ugc.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import top.justdj.ugc.common.entity.BaseInfo;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.1
 * Time: 15:56
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
	
	@ApiModelProperty("vue组件名")
	private String vueComponent;
	
	@ApiModelProperty("当前模块等级")
	private int level;
	
	@ApiModelProperty("父级模块Id")
	private String fatherId;
	
	@ApiModelProperty("模块图表")
	private String ico;
	
	@ApiModelProperty("排序级别")
	private int sort;
	
	@ApiModelProperty("备注")
	private String remark;
	
	
	
	
	
}
