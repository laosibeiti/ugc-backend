package top.justdj.ugc.service;

import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import top.justdj.ugc.common.entity.ForumInfo;

public interface ForumInfoService extends CommonService<ForumInfo> {

    ForumInfo selectByTitle(String title);
}
