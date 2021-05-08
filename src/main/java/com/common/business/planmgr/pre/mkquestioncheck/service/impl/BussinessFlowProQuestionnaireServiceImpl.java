package com.common.business.planmgr.pre.mkquestioncheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.common.business.planmgr.pre.mkquestioncheck.entity.BussinessFlowProQuestionnaire;
import com.common.business.planmgr.pre.mkquestioncheck.entity.TQuestionnaireCheckRec;
import com.common.business.planmgr.pre.mkquestioncheck.mapper.BussinessFlowProQuestionnaireMapper;
import com.common.business.planmgr.pre.mkquestioncheck.service.BussinessFlowProQuestionnaireService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Service
public class BussinessFlowProQuestionnaireServiceImpl extends ServiceImpl<BussinessFlowProQuestionnaireMapper, BussinessFlowProQuestionnaire> implements BussinessFlowProQuestionnaireService {

    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;
    
    /**
     * @Title:
     * @Description:
     * @author: 陈睿超
     * @createDate: 2021/4/2 10:11
     * @updater: 陈睿超
     * @updateDate: 2021/4/2 10:11
     * @param relationProQuestionnaire : 项目拟定调查问卷关系数据
     * @param user : 当前登录人
     * @return
     **/
    @Override
    public List<BussinessFlowProQuestionnaire> getWorkFlow(RelationProQuestionnaire relationProQuestionnaire, ShiroUser user) {
        List<BussinessFlowProQuestionnaire> list = new ArrayList<BussinessFlowProQuestionnaire>();
        //获取项目
        TProPerformanceInfo pro = tProPerformanceInfoMapper.selectById(relationProQuestionnaire.getIdR());

        //创建发起人
        BussinessFlowProQuestionnaire organizerFlow = new BussinessFlowProQuestionnaire();
        organizerFlow.setCheckUserId(user.getId().toString());
        organizerFlow.setCheckUser(user.getName());
        organizerFlow.setCheckUserDeptId(user.getDeptId().toString());
        organizerFlow.setCheckUserDeptName(user.getDeptName());
        organizerFlow.setOrderOfCurrentNode(1);
        organizerFlow.setNodeIsActive("0");
        organizerFlow.setCreateor(user.getName());
        organizerFlow.setCreateTime(new Date());
        organizerFlow.setUpdateor(user.getName());
        organizerFlow.setUpdateTime(new Date());
        organizerFlow.setIdR(relationProQuestionnaire.getIdR());
        organizerFlow.setCheckUserJobNumber(user.getGroupMemberCode());
        
        //创建外勤主管
        EntityWrapper fieldSupervisorEntityWrapper = new EntityWrapper();
        fieldSupervisorEntityWrapper.eq("ID_A",pro.getIdA());
        fieldSupervisorEntityWrapper.eq("IS_WORK_CHARGE","Y");
        List<TPerformanceWorkingGroup> fieldSupervisorList = performanceWorkingGroupMapper.selectList(fieldSupervisorEntityWrapper);
        TPerformanceWorkingGroup fieldSupervisor = fieldSupervisorList.get(0);
        //查询项目外勤主管
        RcUser fieldSupervisorUser = rcUserMapper.selectById(fieldSupervisor.getGroupMemberId());
        BussinessFlowProQuestionnaire fieldSupervisorFlow = new BussinessFlowProQuestionnaire();

        fieldSupervisorFlow.setCheckUserId(fieldSupervisorUser.getId().toString());
        fieldSupervisorFlow.setCheckUser(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setCheckUserDeptId(fieldSupervisorUser.getDeptId().toString());
        fieldSupervisorFlow.setCheckUserDeptName(fieldSupervisorUser.getDeptName());
        fieldSupervisorFlow.setNodeIsActive("0");
        fieldSupervisorFlow.setCreateor(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setCreateTime(new Date());
        fieldSupervisorFlow.setUpdateor(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setUpdateTime(new Date());
        fieldSupervisorFlow.setIdR(relationProQuestionnaire.getIdR());
        fieldSupervisorFlow.setOrderOfCurrentNode(2);
        fieldSupervisorFlow.setCheckUserJobNumber(fieldSupervisorUser.getGroupMemberCode());

        //获得项目经理id
        RcUser proManagerUser = rcUserMapper.selectById(pro.getProManagerId());
        BussinessFlowProQuestionnaire proManagerFlow = new BussinessFlowProQuestionnaire();
        proManagerFlow.setCheckUserJobNumber(proManagerUser.getGroupMemberCode());
        proManagerFlow.setCheckUserId(proManagerUser.getId().toString());
        proManagerFlow.setCheckUser(proManagerUser.getName());
        proManagerFlow.setCheckUserDeptId(proManagerUser.getDeptId().toString());
        proManagerFlow.setCheckUserDeptName(proManagerUser.getDeptName());
        proManagerFlow.setNodeIsActive("0");
        proManagerFlow.setCreateor(proManagerUser.getName());
        proManagerFlow.setCreateTime(new Date());
        proManagerFlow.setUpdateor(proManagerUser.getName());
        proManagerFlow.setUpdateTime(new Date());
        proManagerFlow.setIdR(relationProQuestionnaire.getIdR());
        proManagerFlow.setOrderOfCurrentNode(3);
        
        //判断发起人是不是外勤主管
        if (user.getId().equals(fieldSupervisorUser.getId())){
            //是外勤主管发起
            fieldSupervisorFlow.setOrderOfCurrentNode(1);
            proManagerFlow.setOrderOfCurrentNode(2);
            //根据是否送审设置活跃节点
            if ("1".equals(relationProQuestionnaire.getCreateStauts())){
                //送审
                proManagerFlow.setNodeIsActive("1");
                proManagerFlow.setCurrentNodeStatus("0");
                //发起人外勤主管已完成
                fieldSupervisorFlow.setCurrentNodeStatus("1");
            }else{
                fieldSupervisorFlow.setNodeIsActive("1");
                fieldSupervisorFlow.setCurrentNodeStatus("0");
            }
            list.add(fieldSupervisorFlow);
            list.add(proManagerFlow);
        }else {
            //不是外勤主管发起
            //根据是否送审设置活跃节点
            if ("1".equals(relationProQuestionnaire.getCreateStauts())){
                //送审
                fieldSupervisorFlow.setNodeIsActive("1");
                fieldSupervisorFlow.setCurrentNodeStatus("0");
                //发起人已完成
                organizerFlow.setCurrentNodeStatus("1");
            }else{
                organizerFlow.setNodeIsActive("1");
                organizerFlow.setCurrentNodeStatus("0");
            }
            list.add(organizerFlow);
            list.add(fieldSupervisorFlow);
            list.add(proManagerFlow);
        }
        return list;
    }

    /**
     * @Title: 
     * @Description:  
     * @author: 陈睿超
     * @createDate: 2021/4/6 15:11
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 15:11
     * @param bussinessFlowProQuestionnaireList 流程list
     * @param questionnaireCheckRecList 这次的审批记录list
     * @return 
     **/
    @Override
    public List<BussinessFlowProQuestionnaire> assemblyBussinessFlow(List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList, List<TQuestionnaireCheckRec> questionnaireCheckRecList) {

        //根据节点编号倒序排列
        bussinessFlowProQuestionnaireList.sort(Comparator.comparing(BussinessFlowProQuestionnaire::getNodeIsActive));
        questionnaireCheckRecList.sort(Comparator.comparing(TQuestionnaireCheckRec::getOrderOfCurrentNode));
        for (int j = 0; j < questionnaireCheckRecList.size(); j++) {
            TQuestionnaireCheckRec checkHistory = questionnaireCheckRecList.get(j);
            for (int i = 0; i < bussinessFlowProQuestionnaireList.size(); i++) {
                BussinessFlowProQuestionnaire flowProResearchLetter = bussinessFlowProQuestionnaireList.get(i);
                //需要添加审批记录字段
                //节点序号相等
                if ((flowProResearchLetter.getOrderOfCurrentNode()).equals(checkHistory.getOrderOfCurrentNode())){
                    bussinessFlowProQuestionnaireList.get(i).setCheckTime(checkHistory.getCheckTime());
                    bussinessFlowProQuestionnaireList.get(i).setCheckResult(checkHistory.getCheckResult());
                    bussinessFlowProQuestionnaireList.get(i).setCheckOpinion(checkHistory.getCheckOpinion());
                    bussinessFlowProQuestionnaireList.get(i).setRemark(checkHistory.getRemark());
//                    bussinessFlowProQuestionnaireList.get(i).setCheckResult(checkHistory.getCheckResult());
//                    bussinessFlowProQuestionnaireList.get(i).setCheckTime(checkHistory.getCheckTime());
//                    bussinessFlowProQuestionnaireList.get(i).setCheckOpinion(checkHistory.getCheckOpinion());
//                    bussinessFlowProQuestionnaireList.get(i).setRemark(checkHistory.getRemark());
                }
            }
        }
        return bussinessFlowProQuestionnaireList;

    }

    @Override
    public BussinessFlowProQuestionnaire getNextBussinessFlow(List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList) {
        BussinessFlowProQuestionnaire bussinessFlowProQuestionnaire = new BussinessFlowProQuestionnaire();
        for (int i = 0; i < bussinessFlowProQuestionnaireList.size(); i++) {
            //不是最后一个节点
            if (bussinessFlowProQuestionnaireList.size() != i && "1".equals(bussinessFlowProQuestionnaireList.get(i).getNodeIsActive())){
                bussinessFlowProQuestionnaire = bussinessFlowProQuestionnaireList.get(i+1);
            }
        }
        return bussinessFlowProQuestionnaire;
    }

    @Override
    public BussinessFlowProQuestionnaire getCurrentBussinessFlow(List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList) {
        BussinessFlowProQuestionnaire bussinessFlowProQuestionnaire = new BussinessFlowProQuestionnaire();
        for (int i = 0; i < bussinessFlowProQuestionnaireList.size(); i++) {
            //不是最后一个节点
            if ("1".equals(bussinessFlowProQuestionnaireList.get(i).getNodeIsActive())){
                bussinessFlowProQuestionnaire = bussinessFlowProQuestionnaireList.get(i);
            }
        }
        return bussinessFlowProQuestionnaire;
    }


}
