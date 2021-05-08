package com.common.business.planmgr.pre.mkquestioncheck.service;

import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.common.business.planmgr.pre.mkquestioncheck.entity.BussinessFlowProQuestionnaire;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.pre.mkquestioncheck.entity.TQuestionnaireCheckRec;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;

import java.util.List;

/**
 * <p>
 * 业务流程表（拟定调查问卷审批 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
public interface BussinessFlowProQuestionnaireService extends IService<BussinessFlowProQuestionnaire> {


    /**
     * @Title:
     * @Description: 获取新的工作流
     * @author: 陈睿超
     * @createDate: 2021/4/2 10:11
     * @updater: 陈睿超
     * @updateDate: 2021/4/2 10:11
     * @param relationProQuestionnaire : 项目拟定调查问卷关系数据
     * @param user : 当前登录人
     * @return
     **/
    List<BussinessFlowProQuestionnaire> getWorkFlow(RelationProQuestionnaire relationProQuestionnaire, ShiroUser user);
    
    /**
     * @Title: 
     * @Description:  
     * @author: 陈睿超
     * @createDate: 2021/4/2 14:08
     * @updater: 陈睿超
     * @updateDate: 2021/4/2 14:08
     * @param bussinessFlowProQuestionnaireList : 当前工作流
     * @param user : 当前登录人
     * @return 
     **/
//    RcUser getNextUser(List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList, ShiroUser user);

    /**
     * 组装审批记录和工作流
     * @param bussinessFlowProQuestionnaireList 流程list
     * @param questionnaireCheckRecList 这次的审批记录list
     * @return
     */
    List<BussinessFlowProQuestionnaire> assemblyBussinessFlow(List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList,
                                                               List<TQuestionnaireCheckRec> questionnaireCheckRecList);

    /**
     * @Title: 
     * @Description: 获取下一个流程节点
     * @author: 陈睿超
     * @createDate: 2021/4/6 17:19
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 17:19
     * @param bussinessFlowProQuestionnaireList 流程list
     * @return 
     **/
    BussinessFlowProQuestionnaire getNextBussinessFlow(List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList);
    
    /**
     * @Title: 
     * @Description: 获取当前流程节点
     * @author: 陈睿超
     * @createDate: 2021/4/6 17:45
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 17:45
     * @param bussinessFlowProQuestionnaireList 流程list
     * @return 
     **/
    BussinessFlowProQuestionnaire getCurrentBussinessFlow(List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList);
    
}
