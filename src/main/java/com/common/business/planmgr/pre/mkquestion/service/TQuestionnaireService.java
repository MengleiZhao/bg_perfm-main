package com.common.business.planmgr.pre.mkquestion.service;

import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.common.business.planmgr.pre.mkquestion.entity.TQuestionnaire;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 拟定调查问卷 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
public interface TQuestionnaireService extends IService<TQuestionnaire> {
    
    
    /**
     * 发布调查问卷
     * @author: 陈睿超
     * @createDate: 2021/4/20 16:33
     * @updater: 陈睿超
     * @updateDate: 2021/4/20 16:33
     * @param id:TQuestionnaire的主键id
     * @return 
     **/
    Boolean releaseQuestionnaire(Integer id);

    /**
     * 关闭调查问卷
     * @author: 陈睿超
     * @createDate: 2021/4/20 16:33
     * @updater: 陈睿超
     * @updateDate: 2021/4/20 16:33
     * @param id:TQuestionnaire的主键id
     * @return
     **/
    Boolean closeQuestionnaire(Integer id);


}
