package top.justdj.ugc.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author shan
 */
@Data
@ApiModel("基础实体类")
public class BaseInfo implements Serializable,Cloneable{

    
    @Id
    @ApiModelProperty("id")
    @JsonIgnore
    private String id;
    
    @ApiModelProperty("数据插入时间")
    @JsonIgnore
    private Long createTime = System.currentTimeMillis();
    
    @ApiModelProperty("创建人id")
    private String createUserId;
    
    @ApiModelProperty(value = "数据修改时间",required = false)
    @JsonIgnore
    private Long updateTime;
    
    @ApiModelProperty(value = "修改人id",required = false)
    private String updateUserId;
    
}
