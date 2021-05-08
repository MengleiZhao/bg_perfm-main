package com.common.business.planmgr.schemecheck.service;

import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.common.business.planmgr.pre.mkquestioncheck.entity.BussinessFlowProQuestionnaire;
import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.web.RelationProPreparEvalWorkPlanVo;
import com.common.business.planmgr.schemecheck.entity.BussinessFlowPreparEvalWorkPlan;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.schemecheck.entity.TPreparEvalWorkPlanCheckRec;
import com.common.system.shiro.ShiroUser;

import java.util.List;

/**
 * <p>
 *  业务流程表（编制评价工作方案审批）
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
public interface BussinessFlowPreparEvalWorkPlanService extends IService<BussinessFlowPreparEvalWorkPlan> {


    /**
     * 获取新的工作流
     * @param relationProPreparEvalWorkPlan
     * @param user
     * @return
     */
    List<BussinessFlowPreparEvalWorkPlan> getWorkFlow(RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan, ShiroUser user);

    /**
     * 组装审批记录和工作流
     * @param bussinessFlowProResearchLetterList 流程list
     * @param checkHistoryList 这次的审批记录list
     * @return
     */
    List<BussinessFlowPreparEvalWorkPlan> assemblyBussinessFlow(List<BussinessFlowPreparEvalWorkPlan> bussinessFlowProResearchLetterList,
                                                               List<TPreparEvalWorkPlanCheckRec> checkHistoryList);

    /**
     * 审批或转派
     * @author: 陈睿超
     * @createDate: 2021/4/16 16:16
     * @updater: 陈睿超
     * @updateDate: 2021/4/16 16:16
     * @param relationProPreparEvalWorkPlanVo : 接收前天传过来的参数
     * @param user : 当前登录人
     * @return 
     **/
    Boolean check(RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo, ShiroUser user);

    /**
     * 获取下一个流程节点
     * @author: 陈睿超
     * @createDate: 2021/4/19 10:53
     * @updater: 陈睿超
     * @updateDate: 2021/4/19 10:53
     * @param bussinessFlowPreparEvalWorkPlansList 流程list
     * @return
     **/
    BussinessFlowPreparEvalWorkPlan getNextBussinessFlow(List<BussinessFlowPreparEvalWorkPlan> bussinessFlowPreparEvalWorkPlansList);

    /**
     *  获取当前流程节点
     * @author: 陈睿超
     * @createDate: 2021/4/19 10:53
     * @updater: 陈睿超
     * @updateDate: 2021/4/19 10:53
     * @param bussinessFlowPreparEvalWorkPlansList 流程list
     * @return
     **/
    BussinessFlowPreparEvalWorkPlan getCurrentBussinessFlow(List<BussinessFlowPreparEvalWorkPlan> bussinessFlowPreparEvalWorkPlansList);


}
