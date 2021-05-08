package com.common.business.planmgr.pre.mkquestion.service;

import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.common.business.planmgr.pre.mkquestion.entity.TQuestionnaire;
import com.common.business.planmgr.pre.mkquestion.entity.TQuestionnaireAnswerSheet;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.pre.mkquestion.web.RelationProQuestionnaireVo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 答卷信息 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
public interface TQuestionnaireAnswerSheetService extends IService<TQuestionnaireAnswerSheet> {

    /**
     * 保存答卷信息
     * @author: 陈睿超
     * @createDate: 2021/4/20 09:55
     * @updater: 陈睿超
     * @updateDate: 2021/4/20 09:55
     * @param beanVo:前台接受数据
     * @param user:当前登录人
     * @return 保存成功-true，保存失败-false
     **/
    Boolean saveAnswerSheet(RelationProQuestionnaireVo beanVo, ShiroUser user);

    /**
     * Title: closeQuestionnaire
     * Description: 查看已答所有答卷列表
     * @author: 陈睿超
     * @createDate: 2021/4/21 09:48
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 09:48
     * @param questionnaireAnswerSheet：精确查询(外键idB不能为空)
     * @param user：当前登录人
     * @param search：模糊查询
     * @return
     **/
    PageInfo<TQuestionnaireAnswerSheet> closeQuestionnaire(Integer pageNum, Integer pageSize, String search, TQuestionnaireAnswerSheet questionnaireAnswerSheet, ShiroUser user);


}
