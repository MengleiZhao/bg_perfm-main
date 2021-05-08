package com.common.business.planmgr.schemecheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.mapper.RelationProPreparEvalWorkPlanMapper;
import com.common.business.planmgr.scheme.mkscheme.web.RelationProPreparEvalWorkPlanVo;
import com.common.business.planmgr.schemecheck.entity.BussinessFlowPreparEvalWorkPlan;
import com.common.business.planmgr.schemecheck.entity.DesigneeRecPreparEvalWorkPlan;
import com.common.business.planmgr.schemecheck.entity.TPreparEvalWorkPlanCheckRec;
import com.common.business.planmgr.schemecheck.mapper.BussinessFlowPreparEvalWorkPlanMapper;
import com.common.business.planmgr.schemecheck.mapper.DesigneeRecPreparEvalWorkPlanMapper;
import com.common.business.planmgr.schemecheck.mapper.TPreparEvalWorkPlanCheckRecMapper;
import com.common.business.planmgr.schemecheck.service.BussinessFlowPreparEvalWorkPlanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.system.exception.ServiceException;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  业务流程表（编制评价工作方案审批）
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@Service
public class BussinessFlowPreparEvalWorkPlanServiceImpl extends ServiceImpl<BussinessFlowPreparEvalWorkPlanMapper, BussinessFlowPreparEvalWorkPlan> implements BussinessFlowPreparEvalWorkPlanService {

    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;
    @Autowired
    private DesigneeRecPreparEvalWorkPlanMapper designeeRecPreparEvalWorkPlanMapper;
    @Autowired
    private RelationProPreparEvalWorkPlanMapper relationProPreparEvalWorkPlanMapper;
    @Autowired
    private TPreparEvalWorkPlanCheckRecMapper preparEvalWorkPlanCheckRecMapper;



    @Override
    public List<BussinessFlowPreparEvalWorkPlan> getWorkFlow(RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan, ShiroUser user) {
        List<BussinessFlowPreparEvalWorkPlan> list = new ArrayList<BussinessFlowPreparEvalWorkPlan>();
        //获取项目
        TProPerformanceInfo pro = tProPerformanceInfoMapper.selectById(relationProPreparEvalWorkPlan.getIdR());
        //创建发起人
        BussinessFlowPreparEvalWorkPlan organizerFlow = new BussinessFlowPreparEvalWorkPlan();
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
        organizerFlow.setIdR(relationProPreparEvalWorkPlan.getIdR());
        organizerFlow.setCheckUserJobNumber(user.getGroupMemberCode());
        //创建外勤主管
        EntityWrapper fieldSupervisorEntityWrapper = new EntityWrapper();
        fieldSupervisorEntityWrapper.eq("ID_A",pro.getIdA());
        fieldSupervisorEntityWrapper.eq("IS_WORK_CHARGE","Y");
        List<TPerformanceWorkingGroup> fieldSupervisorList = performanceWorkingGroupMapper.selectList(fieldSupervisorEntityWrapper);
        TPerformanceWorkingGroup fieldSupervisor = fieldSupervisorList.get(0);
        //查询项目外勤主管
        RcUser fieldSupervisorUser = rcUserMapper.selectById(fieldSupervisor.getGroupMemberId());
        BussinessFlowPreparEvalWorkPlan fieldSupervisorFlow = new BussinessFlowPreparEvalWorkPlan();
        fieldSupervisorFlow.setCheckUserId(fieldSupervisorUser.getId().toString());
        fieldSupervisorFlow.setCheckUser(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setCheckUserDeptId(fieldSupervisorUser.getDeptId().toString());
        fieldSupervisorFlow.setCheckUserDeptName(fieldSupervisorUser.getDeptName());
        fieldSupervisorFlow.setNodeIsActive("0");
        fieldSupervisorFlow.setCreateor(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setCreateTime(new Date());
        fieldSupervisorFlow.setUpdateor(fieldSupervisorUser.getName());
        fieldSupervisorFlow.setUpdateTime(new Date());
        fieldSupervisorFlow.setIdR(relationProPreparEvalWorkPlan.getIdR());
        fieldSupervisorFlow.setOrderOfCurrentNode(2);
        fieldSupervisorFlow.setCheckUserJobNumber(fieldSupervisorUser.getGroupMemberCode());
        //获得项目经理id
        RcUser proManagerUser = rcUserMapper.selectById(pro.getProManagerId());
        BussinessFlowPreparEvalWorkPlan proManagerFlow = new BussinessFlowPreparEvalWorkPlan();
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
        proManagerFlow.setIdR(relationProPreparEvalWorkPlan.getIdR());
        proManagerFlow.setOrderOfCurrentNode(3);
        //项目合伙人
        RcUser proPartenUser = rcUserMapper.selectById(pro.getProPartenId());
        BussinessFlowPreparEvalWorkPlan proPartenFlow = new BussinessFlowPreparEvalWorkPlan();
        proPartenFlow.setCheckUserJobNumber(proPartenUser.getGroupMemberCode());
        proPartenFlow.setCheckUserId(proPartenUser.getId().toString());
        proPartenFlow.setCheckUser(proPartenUser.getName());
        proPartenFlow.setCheckUserDeptId(proPartenUser.getDeptId().toString());
        proPartenFlow.setCheckUserDeptName(proPartenUser.getDeptName());
        proPartenFlow.setNodeIsActive("0");
        proPartenFlow.setCreateor(proPartenUser.getName());
        proPartenFlow.setCreateTime(new Date());
        proPartenFlow.setUpdateor(proPartenUser.getName());
        proPartenFlow.setUpdateTime(new Date());
        proPartenFlow.setIdR(relationProPreparEvalWorkPlan.getIdR());
        proPartenFlow.setOrderOfCurrentNode(4);
        //项目独立复核人
        RcUser proIndepReviewUser = rcUserMapper.selectById(pro.getProPartenId());
        BussinessFlowPreparEvalWorkPlan proIndepReviewFlow = new BussinessFlowPreparEvalWorkPlan();
        proIndepReviewFlow.setCheckUserJobNumber(proIndepReviewUser.getGroupMemberCode());
        proIndepReviewFlow.setCheckUserId(proIndepReviewUser.getId().toString());
        proIndepReviewFlow.setCheckUser(proIndepReviewUser.getName());
        proIndepReviewFlow.setCheckUserDeptId(proIndepReviewUser.getDeptId().toString());
        proIndepReviewFlow.setCheckUserDeptName(proIndepReviewUser.getDeptName());
        proIndepReviewFlow.setNodeIsActive("0");
        proIndepReviewFlow.setCreateor(proIndepReviewUser.getName());
        proIndepReviewFlow.setCreateTime(new Date());
        proIndepReviewFlow.setUpdateor(proIndepReviewUser.getName());
        proIndepReviewFlow.setUpdateTime(new Date());
        proIndepReviewFlow.setIdR(relationProPreparEvalWorkPlan.getIdR());
        proIndepReviewFlow.setOrderOfCurrentNode(5);
        //判断发起人是不是外勤主管
        if (user.getId().equals(fieldSupervisorUser.getId())){
            //是外勤主管发起
            fieldSupervisorFlow.setOrderOfCurrentNode(1);
            proManagerFlow.setOrderOfCurrentNode(2);
            //根据是否送审设置活跃节点
            if ("1".equals(relationProPreparEvalWorkPlan.getCreateStauts())){
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
            proPartenFlow.setOrderOfCurrentNode(3);
            list.add(proPartenFlow);
            //A级项目需要独立复核人    
            if ("A".equals(pro.getRiskLevel())){
                proIndepReviewFlow.setOrderOfCurrentNode(4);
                list.add(proIndepReviewFlow);
            }
        }else {
            //不是外勤主管发起
            //根据是否送审设置活跃节点
            if ("1".equals(relationProPreparEvalWorkPlan.getCreateStauts())){
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
            list.add(proPartenFlow);
            //A级项目需要独立复核人    
            if ("A".equals(pro.getRiskLevel())){
                list.add(proIndepReviewFlow);
            }
        }
        return list;
    }

    @Override
    public List<BussinessFlowPreparEvalWorkPlan> assemblyBussinessFlow(List<BussinessFlowPreparEvalWorkPlan> bussinessFlowProResearchLetterList, List<TPreparEvalWorkPlanCheckRec> checkHistoryList) {
        //根据节点编号倒序排列
        bussinessFlowProResearchLetterList.sort(Comparator.comparing(BussinessFlowPreparEvalWorkPlan::getOrderOfCurrentNode));
        checkHistoryList.sort(Comparator.comparing(TPreparEvalWorkPlanCheckRec::getOrderOfCurrentNode));
        for (int j = 0; j < checkHistoryList.size(); j++) {
            TPreparEvalWorkPlanCheckRec checkHistory = checkHistoryList.get(j);

            for (int i = 0; i < bussinessFlowProResearchLetterList.size(); i++) {
                BussinessFlowPreparEvalWorkPlan flowProResearchLetter = bussinessFlowProResearchLetterList.get(i);
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


    @Override
    public Boolean check(RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo, ShiroUser user) {
        //业务流程
        BussinessFlowPreparEvalWorkPlan bussinessFlowPreparEvalWorkPlan = relationProPreparEvalWorkPlanVo.getBussinessFlowPreparEvalWorkPlan();
        //指派记录
        DesigneeRecPreparEvalWorkPlan designeeRecPreparEvalWorkPlan = relationProPreparEvalWorkPlanVo.getDesigneeRecPreparEvalWorkPlan();
        //审批记录
        TPreparEvalWorkPlanCheckRec preparEvalWorkPlanCheckRec = relationProPreparEvalWorkPlanVo.getPreparEvalWorkPlanCheckRec();
        //项目编制评价工作方案关系
        RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan = relationProPreparEvalWorkPlanMapper.selectById(relationProPreparEvalWorkPlanVo.getRelationProPreparEvalWorkPlan().getIdR());
        //获取当前业务全部流程
        EntityWrapper workFlowEntityWrapper = new EntityWrapper();
        workFlowEntityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
        workFlowEntityWrapper.orderBy("ORDER_OF_CURRENT_NODE",true);
        List<BussinessFlowPreparEvalWorkPlan> workFlowList = baseMapper.selectList(workFlowEntityWrapper);
        //获取当前活动节点
        BussinessFlowPreparEvalWorkPlan currentBussinessFlow = getCurrentBussinessFlow(workFlowList);
        //获取下个审批节点
        BussinessFlowPreparEvalWorkPlan nextBussinessFlow = getNextBussinessFlow(workFlowList);
        //根据被转派人I判断是否转派任务
        if (StringUtils.isEmpty(designeeRecPreparEvalWorkPlan.getDesigneeId())){
            //判断通过与否
            if ("1".equals(preparEvalWorkPlanCheckRec.getCheckResult())){
                //通过
                //更新当前节点数据
                currentBussinessFlow.setNodeIsActive("0");
                currentBussinessFlow.setCurrentNodeStatus("1");
                currentBussinessFlow.setUpdateor(user.getName());
                currentBussinessFlow.setUpdateTime(new Date());
                baseMapper.updateById(currentBussinessFlow);
                //需要判断是否审批结束
                if (null == nextBussinessFlow){
                    //没有下级审批人,审批完成
                    relationProPreparEvalWorkPlan.setCreateStauts("2");
                    relationProPreparEvalWorkPlanMapper.updateById(relationProPreparEvalWorkPlan);
                    //改变项目评价工作方案审核状态
                    TProPerformanceInfo pro = tProPerformanceInfoMapper.selectById(relationProPreparEvalWorkPlan.getIdA());
                    pro.setPerparWorkplanStatus("1");
                    tProPerformanceInfoMapper.updateById(pro);
                    
                }else {
                    nextBussinessFlow.setCurrentNodeStatus("0");
                    nextBussinessFlow.setNodeIsActive("1");
                    nextBussinessFlow.setUpdateor(user.getName());
                    nextBussinessFlow.setUpdateTime(new Date());
                    baseMapper.updateById(nextBussinessFlow);
                }
            }else if ("0".equals(preparEvalWorkPlanCheckRec.getCheckResult())){
                //不通过
                relationProPreparEvalWorkPlan.setCreateStauts("-1");
                relationProPreparEvalWorkPlanMapper.updateById(relationProPreparEvalWorkPlan);
                //并重置流程
                for (int i = 0; i < workFlowList.size(); i++) {
                    BussinessFlowPreparEvalWorkPlan workFlow = workFlowList.get(i);
                    workFlow.setCurrentNodeStatus("0");
                    workFlow.setUpdateor(user.getName());
                    workFlow.setUpdateTime(new Date());
                    if (0 == i){
                        //第一个节点为活跃状态
                        workFlow.setNodeIsActive("1");
                    }else {
                        workFlow.setNodeIsActive("0");
                    }
                    baseMapper.updateById(workFlow);
                }
            }else {
                //其他情况
                throw new ServiceException("请填写审批结果");
            }
            //添加审批记录
            preparEvalWorkPlanCheckRec.setIdR(relationProPreparEvalWorkPlan.getIdR());
            preparEvalWorkPlanCheckRec.setCheckDataStatus("1");
            preparEvalWorkPlanCheckRec.setCheckTime(new Date());
            preparEvalWorkPlanCheckRec.setOrderOfCurrentNode(currentBussinessFlow.getOrderOfCurrentNode());
            //判断是否被转派人进来审批
            if (!StringUtils.isEmpty(currentBussinessFlow.getDesigneeId()) && user.getId().equals(currentBussinessFlow.getDesigneeId())){
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
                entityWrapper.eq("CHECK_USER_ID",currentBussinessFlow.getCheckUserId());
                entityWrapper.eq("ORDER_OF_CURRENT_NODE",currentBussinessFlow.getOrderOfCurrentNode());
                entityWrapper.orderBy("DESIGNEE",true);
                List<DesigneeRecPreparEvalWorkPlan> currentDesigneeRecPreparEvalWorkPlan = designeeRecPreparEvalWorkPlanMapper.selectList(entityWrapper);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str = sdf.format(currentDesigneeRecPreparEvalWorkPlan.get(currentDesigneeRecPreparEvalWorkPlan.size()-1).getDesignee());
                preparEvalWorkPlanCheckRec.setRemark(str+"时间 由 "+currentBussinessFlow.getCheckUser()+" 转 "+
                        user.getName()+" 承办”");
            }
            preparEvalWorkPlanCheckRecMapper.insert(preparEvalWorkPlanCheckRec);
        }else {
            //设置转派信息
            RcUser designeeUser = rcUserMapper.selectById(designeeRecPreparEvalWorkPlan.getDesigneeId());
            //更新当前节点信息
            currentBussinessFlow.setDesigneeDeptId(designeeUser.getDeptId());
            currentBussinessFlow.setDesigneeDeptName(designeeUser.getDeptName());
            currentBussinessFlow.setDesigneeId(designeeUser.getId().toString());
            currentBussinessFlow.setDesigneeName(designeeUser.getName());
            currentBussinessFlow.setDesigneeJobNumber(designeeUser.getGroupMemberCode());
            baseMapper.updateById(currentBussinessFlow);
            //添加转派信息
            designeeRecPreparEvalWorkPlan.setDesignee(new Date());
            designeeRecPreparEvalWorkPlan.setIdR(bussinessFlowPreparEvalWorkPlan.getIdR());
            designeeRecPreparEvalWorkPlan.setCheckUserId(currentBussinessFlow.getCheckUserId());
            designeeRecPreparEvalWorkPlan.setCheckUser(currentBussinessFlow.getCheckUser());
            designeeRecPreparEvalWorkPlan.setDesigneeId(designeeUser.getId().toString());
            designeeRecPreparEvalWorkPlan.setDesigneeName(designeeUser.getName());
            designeeRecPreparEvalWorkPlan.setOrderOfCurrentNode(currentBussinessFlow.getOrderOfCurrentNode());
            designeeRecPreparEvalWorkPlanMapper.insert(designeeRecPreparEvalWorkPlan);
        }
        return true;
    }


    @Override
    public BussinessFlowPreparEvalWorkPlan getNextBussinessFlow(List<BussinessFlowPreparEvalWorkPlan> bussinessFlowPreparEvalWorkPlansList) {
        BussinessFlowPreparEvalWorkPlan bussinessFlowPreparEvalWorkPlan = new BussinessFlowPreparEvalWorkPlan();
        for (int i = 0; i < bussinessFlowPreparEvalWorkPlansList.size(); i++) {
            //不是最后一个节点
            if (i != bussinessFlowPreparEvalWorkPlansList.size()-1 && "1".equals(bussinessFlowPreparEvalWorkPlansList.get(i).getNodeIsActive())){
                bussinessFlowPreparEvalWorkPlan = bussinessFlowPreparEvalWorkPlansList.get(i+1);
            }
        }
        return bussinessFlowPreparEvalWorkPlan;
    }


    @Override
    public BussinessFlowPreparEvalWorkPlan getCurrentBussinessFlow(List<BussinessFlowPreparEvalWorkPlan> bussinessFlowPreparEvalWorkPlansList) {
        BussinessFlowPreparEvalWorkPlan bussinessFlowPreparEvalWorkPlan = new BussinessFlowPreparEvalWorkPlan();
        for (int i = 0; i < bussinessFlowPreparEvalWorkPlansList.size(); i++) {
            //不是最后一个节点
            if (i != bussinessFlowPreparEvalWorkPlansList.size()-1 && "1".equals(bussinessFlowPreparEvalWorkPlansList.get(i).getNodeIsActive())){
                bussinessFlowPreparEvalWorkPlan = bussinessFlowPreparEvalWorkPlansList.get(i);
            }
        }
        return bussinessFlowPreparEvalWorkPlan;
    }
}
