package top.justdj.ugc.dao.impl;

import org.springframework.stereotype.Service;
import top.justdj.ugc.common.entity.ForumInfo;
import top.justdj.ugc.dao.ForumInfoDAO;
import top.justdj.ugc.dao.common.AbstractCommonDAOImpl;

@Service
public class ForumInfoDAOImpl extends AbstractCommonDAOImpl<ForumInfo>
        implements ForumInfoDAO {

    public ForumInfoDAOImpl(){
        super.setCOLLECTION();
    }

}
