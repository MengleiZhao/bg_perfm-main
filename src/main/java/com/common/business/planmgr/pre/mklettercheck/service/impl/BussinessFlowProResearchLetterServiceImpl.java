package com.common.business.planmgr.pre.mklettercheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.meanslistcheck.entity.BussinessFlowProList;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.business.planmgr.pre.mklettercheck.mapper.BussinessFlowProResearchLetterMapper;
import com.common.business.planmgr.pre.mklettercheck.mapper.TResearchLetterCheckRecMapper;
import com.common.business.planmgr.pre.mklettercheck.service.BussinessFlowProResearchLetterService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mklettercheck.service.TResearchLetterCheckRecService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 业务流程表（拟定调研函审批） 服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
@Service
@Transactional
public class BussinessFlowProResearchLetterServiceImpl extends ServiceImpl<BussinessFlowProResearchLetterMapper, BussinessFlowProResearchLetter> implements BussinessFlowProResearchLetterService {

    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;



    @Override
    public List<BussinessFlowProResearchLetter> getWorkFlow(RelationProResearchLetter relationProResearchLetter, ShiroUser user) {
        List<BussinessFlowProResearchLetter> list = new ArrayList<BussinessFlowProResearchLetter>();
        //获取项目
        TProPerformanceInfo pro = tProPerformanceInfoMapper.selectById(relationProResearchLetter.getIdR());

        //创建发起人
        BussinessFlowProResearchLetter organizerFlow = new BussinessFlowProResearchLetter();
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
        organizerFlow.setIdR(relationProResearchLetter.getIdR());


        //创建外勤主管
        EntityWrapper fieldSupervisorEntityWrapper = new EntityWrapper();
        fieldSupervisorEntityWrapper.eq("ID_A",pro.getIdA());
        fieldSupervisorEntityWrapper.eq("IS_WORK_CHARGE","Y");
        List<TPerformanceWorkingGroup> fieldSupervisorList = performanceWorkingGroupMapper.selectList(fieldSupervisorEntityWrapper);
        TPerformanceWorkingGroup fieldSupervisor = fieldSupervisorList.get(0);
        //查询项目外勤主管
        RcUser fieldSupervisorUser = rcUserMapper.selectById(fieldSupervisor.getGroupMemberId());
        BussinessFlowProResearchLetter fieldSupervisorFlow = new BussinessFlowProResearchLetter();

        fieldSupervisorFlow.setCheckUserId(fieldSupervisorUser.getId().toString());
        fieldSupervisorFlow.setCheckUser(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setCheckUserDeptId(fieldSupervisorUser.getDeptId().toString());
        fieldSupervisorFlow.setCheckUserDeptName(fieldSupervisorUser.getDeptName());
        fieldSupervisorFlow.setNodeIsActive("0");
        fieldSupervisorFlow.setCreateor(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setCreateTime(new Date());
        fieldSupervisorFlow.setUpdateor(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setUpdateTime(new Date());
        fieldSupervisorFlow.setIdR(relationProResearchLetter.getIdR());
        fieldSupervisorFlow.setOrderOfCurrentNode(2);
        
        //获得项目经理id
        RcUser proManagerUser = rcUserMapper.selectById(pro.getProManagerId());
        BussinessFlowProResearchLetter proManagerFlow = new BussinessFlowProResearchLetter(null,
                relationProResearchLetter.getIdR(),proManagerUser.getId().toString(), proManagerUser.getName(),proManagerUser.getGroupMemberCode(),
                proManagerUser.getDeptId(),proManagerUser.getDeptName(),null,null,
                null,null,null,3,"0","0",
                relationProResearchLetter.getCreateUaseName(),new Date(),
                relationProResearchLetter.getCreateUaseId(),new Date(),null,null,null,null);

        //判断发起人是不是外勤主管
        if (user.getId().equals(fieldSupervisorUser.getId())){
            //是外勤主管发起
            fieldSupervisorFlow.setOrderOfCurrentNode(1);
            proManagerFlow.setOrderOfCurrentNode(2);
            //根据是否送审设置活跃节点
            if ("1".equals(relationProResearchLetter.getCreateStauts())){
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
            if ("1".equals(relationProResearchLetter.getCreateStauts())){
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

    @Override
    public List<BussinessFlowProResearchLetter> assemblyBussinessFlow(List<BussinessFlowProResearchLetter> bussinessFlowProResearchLetterList, List<TResearchLetterCheckRec> checkHistoryList) {
        
        //根据节点编号倒序排列
        bussinessFlowProResearchLetterList.sort(Comparator.comparing(BussinessFlowProResearchLetter::getOrderOfCurrentNode));
        checkHistoryList.sort(Comparator.comparing(TResearchLetterCheckRec::getOrderOfCurrentNode));
        for (int j = 0; j < checkHistoryList.size(); j++) {
            TResearchLetterCheckRec checkHistory = checkHistoryList.get(j);

            for (int i = 0; i < bussinessFlowProResearchLetterList.size(); i++) {
                BussinessFlowProResearchLetter flowProResearchLetter = bussinessFlowProResearchLetterList.get(i);
                //需要添加审批记录字段
                //节点序号相等
                if ((flowProResearchLetter.getOrderOfCurrentNode()).equals(checkHistory.getOrderOfCurrentNode())){
                    bussinessFlowProResearchLetterList.get(i).setCheckTime(checkHistory.getCheckTime());
                    bussinessFlowProResearchLetterList.get(i).setCheckResult(checkHistory.getCheckResult());
                    bussinessFlowProResearchLetterList.get(i).setCheckOpinion(checkHistory.getCheckOpinion());
                    bussinessFlowProResearchLetterList.get(i).setRemark(checkHistory.getRemark());
                }
            }
        }
        return bussinessFlowProResearchLetterList;
    }
}
