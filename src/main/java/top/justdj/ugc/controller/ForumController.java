package top.justdj.ugc.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;
import top.justdj.ugc.common.entity.ForumInfo;
import top.justdj.ugc.common.entity.UserInfo;
import top.justdj.ugc.common.util.Result;
import top.justdj.ugc.service.ForumInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/forum")
@Api(value = "论坛相关的接口",tags = "论坛相关的接口")
public class ForumController extends BaseController {

    @Autowired
    private ForumInfoService forumInfoService;

    @ApiOperation("新建帖子")
    @PostMapping("/")
    public Result add(@Valid @RequestBody ForumInfo forumInfo, HttpServletRequest request){
        UserInfo userInfo = getUserInfo(request);
        forumInfo.setPostAuthor(userInfo.getName());
        forumInfoService.save(forumInfo);
        return Result.ok("发帖成功");
    }


}
