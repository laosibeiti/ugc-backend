package top.justdj.ugc.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.8.3
 * Time: 9:01
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */

@Data
@ApiModel("博客")
@Document(collection = "blog_info")
public class BlogInfo extends BaseInfo{
	
	
	@ApiModelProperty(value = "博客分类",required = true,example = "1")
	@NotBlank(message = "博客类型不能为空")
	private String kind;
	
	
	@ApiModelProperty(value = "博客title",required = true,example = "程序员养发秘籍")
	@NotBlank(message = "博客标题不能为空")
	private String title;
	
	
	@ApiModelProperty(value = "博客内容",required = true,example = "转行")
	@NotBlank(message = "博客内容不能为空")
	private String content;
	
	@ApiModelProperty("点赞数")
	@JsonIgnore
	private Long likeCount;
	
	
	@ApiModelProperty("阅读数")
	private Long readCount;
	
	
	@ApiModelProperty("发布时间")
	private Long presentTime;
	
	
	@ApiModelProperty("发布人ID")
	@JsonIgnore
	private String authorId;
	
	
	@ApiModelProperty("发布人名称")
	@JsonIgnore
	private String authorName;
	
	
	@ApiModelProperty("收藏者id")
	private List<String> collectorId;
	
}
