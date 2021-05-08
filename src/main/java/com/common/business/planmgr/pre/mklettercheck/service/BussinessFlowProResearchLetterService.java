package com.common.business.planmgr.pre.mklettercheck.service;

import com.common.business.collection.meanslistcheck.entity.BussinessFlowProList;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.system.shiro.ShiroUser;

import java.util.List;

/**
 * <p>
 * 业务流程表（拟定调研函审批） 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
public interface BussinessFlowProResearchLetterService extends IService<BussinessFlowProResearchLetter> {

   /**
    * @Title: 
    * @Description:  获取拟定调研函工作流
    * @author: 陈睿超
    * @createDate: 2021/3/25 11:37
    * @updater: 陈睿超
    * @updateDate: 2021/3/25 11:37
    * @param id : 项目主键id
    * @param user : 当前登录人
    * @return 
    **/
    List<BussinessFlowProResearchLetter> getWorkFlow(RelationProResearchLetter relationProResearchLetter,ShiroUser user);


    /**
     * 组装审批记录和工作流
     * @param bussinessFlowProResearchLetterList 流程list
     * @param checkHistoryList 这次的审批记录list
     * @return
     */
    List<BussinessFlowProResearchLetter> assemblyBussinessFlow(List<BussinessFlowProResearchLetter> bussinessFlowProResearchLetterList,
                                                     List<TResearchLetterCheckRec> checkHistoryList);



}
