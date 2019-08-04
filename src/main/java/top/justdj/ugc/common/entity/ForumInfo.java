package top.justdj.ugc.common.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("论坛")
@Document(collection = "forum_info")

public class ForumInfo extends  UserInfo {

    @ApiModelProperty(value = "论坛帖子标题", required = true, example = "如何评价程序员")
    @NotBlank(message = "论坛帖子标题不能为空")
    private String postTitle;

    @ApiModelProperty(value = "论坛帖子内容", required = true, example = "程序员是什么")
    @NotBlank
    private String postContent;

    @ApiModelProperty(value = "帖子作者", required = true, example = "张三")
    @NotBlank
    private String postAuthor;

    @ApiModelProperty(value = "发帖日期")
    private Long postDate;

    @ApiModelProperty(value = "留言内容", example = "程序员是写代码的")
    @NotBlank
    private String message;

    @ApiModelProperty(value = "留言者")
    private String messageName;

    @ApiModelProperty(value = "留言日期")
    private Long messageDate;

    @ApiModelProperty(value = "帖子留言数")
    private Long messageCount;

    @ApiModelProperty(value = "帖子点赞数")
    private Long postLike;

}
