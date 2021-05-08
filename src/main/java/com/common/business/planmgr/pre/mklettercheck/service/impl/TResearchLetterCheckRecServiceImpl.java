package com.common.business.planmgr.pre.mklettercheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mkletter.mapper.RelationProResearchLetterMapper;
import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.DesigneeRecProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.business.planmgr.pre.mklettercheck.mapper.BussinessFlowProResearchLetterMapper;
import com.common.business.planmgr.pre.mklettercheck.mapper.DesigneeRecProResearchLetterMapper;
import com.common.business.planmgr.pre.mklettercheck.mapper.TResearchLetterCheckRecMapper;
import com.common.business.planmgr.pre.mklettercheck.service.DesigneeRecProResearchLetterService;
import com.common.business.planmgr.pre.mklettercheck.service.TResearchLetterCheckRecService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mkquestioncheck.entity.DesigneeRecProQuestionnaire;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 调研函审批记录表 服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
@Service
@Transactional
public class TResearchLetterCheckRecServiceImpl extends ServiceImpl<TResearchLetterCheckRecMapper, TResearchLetterCheckRec> implements TResearchLetterCheckRecService {

    @Autowired
    private BussinessFlowProResearchLetterMapper bussinessFlowProResearchLetterMapper;
    @Autowired
    private RelationProResearchLetterMapper relationProResearchLetterMapper;
    @Autowired
    private RcUserMapper userMapper;
    @Autowired
    private DesigneeRecProResearchLetterMapper designeeRecProResearchLetterMapper;
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;


    @Override
    public Boolean check(TResearchLetterCheckRec researchLetterCheckRec, ShiroUser user) {
        RelationProResearchLetter relationProResearchLetter = relationProResearchLetterMapper.selectById(researchLetterCheckRec.getIdR());
       
        //获取当前工作流
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R", researchLetterCheckRec.getIdR());
        entityWrapper.orderBy("ID_CHE");
        List<BussinessFlowProResearchLetter> workFlowList = bussinessFlowProResearchLetterMapper.selectList(entityWrapper);
        //获取当前节点
        Integer orderOfCurrentNode = researchLetterCheckRec.getOrderOfCurrentNode();
       
        //获取当前活跃节点
        EntityWrapper nowNodeEntityWrapper = new EntityWrapper();
        nowNodeEntityWrapper.eq("ID_R", researchLetterCheckRec.getIdR());
        nowNodeEntityWrapper.eq("NODE_IS_ACTIVE", "1");
        BussinessFlowProResearchLetter nowNodeWorkFlow = (BussinessFlowProResearchLetter) bussinessFlowProResearchLetterMapper.selectList(nowNodeEntityWrapper).get(0);
       
        //获取下一节点
        BussinessFlowProResearchLetter nextWorkFlow = new BussinessFlowProResearchLetter();
        //判断是不是最后一个节点
        if (nowNodeWorkFlow.getOrderOfCurrentNode() < workFlowList.size()){
            //获取下一个节点
            EntityWrapper nextEntityWrapper = new EntityWrapper();
            nextEntityWrapper.eq("ID_R", researchLetterCheckRec.getIdR());
            nextEntityWrapper.eq("ORDER_OF_CURRENT_NODE", nowNodeWorkFlow.getOrderOfCurrentNode()+1);
            nextWorkFlow = (BussinessFlowProResearchLetter) bussinessFlowProResearchLetterMapper.selectList(nextEntityWrapper).get(0);
        }
        
        //获取上一节点
        BussinessFlowProResearchLetter upWorkFlow = new BussinessFlowProResearchLetter();
        if (!nowNodeWorkFlow.getOrderOfCurrentNode().equals(workFlowList.get(0).getOrderOfCurrentNode())){
            //获取下一个节点
            EntityWrapper upEntityWrapper = new EntityWrapper();
            upEntityWrapper.eq("ID_R", researchLetterCheckRec.getIdR());
            upEntityWrapper.eq("ORDER_OF_CURRENT_NODE", nowNodeWorkFlow.getOrderOfCurrentNode()-1);
            upWorkFlow = (BussinessFlowProResearchLetter) bussinessFlowProResearchLetterMapper.selectList(upEntityWrapper).get(0);
        }

        if (StringUtils.isEmpty(researchLetterCheckRec.getDesigneeRecProResearchLetter().getDesigneeId())) {
            //不转派
            if ("1".equals(researchLetterCheckRec.getCheckResult())) {
                //通过
                if (null != nextWorkFlow){
                    //过程节点
                    relationProResearchLetter.setCurrCheckId(nextWorkFlow.getCheckUserId());
                    relationProResearchLetter.setCurrCheckName(nextWorkFlow.getCheckUser());
                    nextWorkFlow.setNodeIsActive("1");
                    nextWorkFlow.setUpdateor(user.getName());
                    nextWorkFlow.setUpdateTime(new Date());
                    bussinessFlowProResearchLetterMapper.updateById(nextWorkFlow);
                }else {
                    //最后一个节点
                    relationProResearchLetter.setCurrCheckId(null);
                    relationProResearchLetter.setCurrCheckName(null);
                    relationProResearchLetter.setCreateStauts("2");
                    //改变项目研函审核状态
                    TProPerformanceInfo pro = proPerformanceInfoMapper.selectById(relationProResearchLetter.getIdA());
                    if (1 == relationProResearchLetter.getPreOrScheme()){
                        pro.setResearchLetterStatusC("1");
                    }else {
                        pro.setResearchLetterStatusY("1");
                    }
                    proPerformanceInfoMapper.updateById(pro);
                }
                nowNodeWorkFlow.setCurrentNodeStatus("1");
                nowNodeWorkFlow.setNodeIsActive("0");
                nowNodeWorkFlow.setUpdateor(user.getName());
                nowNodeWorkFlow.setUpdateTime(new Date());
                bussinessFlowProResearchLetterMapper.updateById(nowNodeWorkFlow);
            }else if ("0".equals(researchLetterCheckRec.getCheckResult())) {
                //不通过
                //审批节点全部变成未完成
                for (int i = 0; i < workFlowList.size(); i++) {
                    BussinessFlowProResearchLetter workFlow = workFlowList.get(i);
                    workFlow.setCurrentNodeStatus("0");
                    workFlow.setUpdateor(user.getName());
                    workFlow.setUpdateTime(new Date());
                    workFlow.setDesigneeDeptId(null);
                    workFlow.setDesigneeDeptName(null);
                    workFlow.setDesigneeId(null);
                    workFlow.setDesigneeName(null);
                    workFlow.setDesigneeJobNumber(null);
                    if (i == 0){
                        workFlow.setNodeIsActive("1");
                    }
                    bussinessFlowProResearchLetterMapper.updateAllColumnById(workFlow);
                }
            }
            //添加审批记录
            researchLetterCheckRec.setCheckUserId(user.getId().toString());
            researchLetterCheckRec.setCheckUser(user.getName());
            researchLetterCheckRec.setCheckTime(new Date());
            researchLetterCheckRec.setCheckDataStatus("1");
            researchLetterCheckRec.setOrderOfCurrentNode(nowNodeWorkFlow.getOrderOfCurrentNode());
            //判断是不是被转派人审批
            if (!StringUtils.isEmpty(nowNodeWorkFlow.getDesigneeId()) && user.getId().equals(nowNodeWorkFlow.getDesigneeId())){
                //被转派人进来审批需要添加备注
                //设置审批记录的备注
                EntityWrapper entityWrapper1 = new EntityWrapper();
                entityWrapper1.eq("ID_R",researchLetterCheckRec.getIdR());
                entityWrapper1.eq("CHECK_USER_ID",nowNodeWorkFlow.getCheckUserId());
                entityWrapper1.eq("ORDER_OF_CURRENT_NODE",nowNodeWorkFlow.getOrderOfCurrentNode());
                entityWrapper1.orderBy("DESIGNEE",true);
                List<DesigneeRecProResearchLetter> currentDesigneeRecProResearchLetter = designeeRecProResearchLetterMapper.selectList(entityWrapper1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str = sdf.format(currentDesigneeRecProResearchLetter.get(currentDesigneeRecProResearchLetter.size()-1).getDesignee());
                researchLetterCheckRec.setRemark(str+"时间 由 "+nowNodeWorkFlow.getCheckUser()+" 转 "+
                        user.getName()+" 承办”");
            }
            baseMapper.insert(researchLetterCheckRec);  
        }else {
            //转派任务
            //获取被转派人信息
            RcUser rcuser = userMapper.selectById(researchLetterCheckRec.getDesigneeRecProResearchLetter().getDesigneeId());
            
            nowNodeWorkFlow.setDesigneeId(rcuser.getId().toString());
            nowNodeWorkFlow.setDesigneeName(rcuser.getName());
            nowNodeWorkFlow.setDesigneeDeptId(rcuser.getDeptId());
            nowNodeWorkFlow.setDesigneeDeptName(rcuser.getDeptName());
            nowNodeWorkFlow.setUpdateor(user.getName());
            nowNodeWorkFlow.setUpdateTime(new Date());
            
            //更新工作流
            bussinessFlowProResearchLetterMapper.updateById(nowNodeWorkFlow);
            //添加转派记录
            DesigneeRecProResearchLetter designeeRecProResearchLetter = new DesigneeRecProResearchLetter(
                    null, relationProResearchLetter.getIdR(), new Date(), nowNodeWorkFlow.getCheckUserId(),
                    nowNodeWorkFlow.getCheckUser(), rcuser.getId().toString(), rcuser.getName(), orderOfCurrentNode);
            designeeRecProResearchLetterMapper.insert(designeeRecProResearchLetter);
            //更新关系表下级审批人数据
            relationProResearchLetter.setCurrCheckId(rcuser.getId().toString());
            relationProResearchLetter.setCurrCheckName(rcuser.getName());
            
        }
        relationProResearchLetterMapper.updateById(relationProResearchLetter);
        
        return true;
        /*if (StringUtils.isEmpty(researchLetterCheckRec.getDesigneeRecProResearchLetter().getDesigneeId())) {
            //不转派
            for (int i = 0; i < workFlowList.size(); i++) {
                BussinessFlowProResearchLetter workFlow = workFlowList.get(i);
                if (workFlow.getCheckUserId().equals(user.getId())) {
                
                    if ("1".equals(researchLetterCheckRec.getCheckResult())) {
                        //通过
                        //当前审批人和当前工作流节点匹配
                        if (workFlowList.size() == orderOfCurrentNode) {
                            //该节点序号和流程长度相同说明是最后一个节点
                            relationProResearchLetter.setCurrCheckId(null);
                            relationProResearchLetter.setCurrCheckName(null);
                            relationProResearchLetter.setCreateStauts("2");
                            workFlow.setNodeIsActive("0");
                            workFlow.setCurrentNodeStatus("1");
                            bussinessFlowProResearchLetterMapper.updateById(workFlow);
                        } else {
                            //中间审批节点
                            relationProResearchLetter.setCurrCheckId(user.getId().toString());
                            relationProResearchLetter.setCurrCheckName(user.getName());
                            workFlow.setNodeIsActive("0");
                            workFlow.setCurrentNodeStatus("1");
                            //下一级审批人
                            BussinessFlowProResearchLetter nextworkFlow = workFlowList.get(i + 1);
                            //下级审批节点变成活跃
                            nextworkFlow.setNodeIsActive("1");
                            bussinessFlowProResearchLetterMapper.updateById(workFlow);
                            bussinessFlowProResearchLetterMapper.updateById(nextworkFlow);
                        }
                    } else if ("0".equals(researchLetterCheckRec.getCheckResult())) {
                        //不通过
                        relationProResearchLetter.setCreateStauts("-1");
                        workFlow.setNodeIsActive("0");

                        relationProResearchLetter.setCurrCheckId(null);
                        relationProResearchLetter.setCurrCheckName(null);
                    }
                    //判断是不是被转派人审批，如果是需要在备注里添加信息
                    if (workFlow.getDesigneeId().equals(user.getId())) {
                        //设置审批记录的备注
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String str = sdf.format(new Date());
                        researchLetterCheckRec.setRemark(str + "时间 由 " + workFlow.getCheckUser() + " 转 " +
                                user.getName() + " 承办”");
                        researchLetterCheckRec.setOrderOfCurrentNode(workFlow.getOrderOfCurrentNode());
                    }
                    //添加审批记录
                    researchLetterCheckRec.setCheckUserId(user.getId().toString());
                    researchLetterCheckRec.setCheckUser(user.getName());
                    researchLetterCheckRec.setCheckTime(new Date());
                    researchLetterCheckRec.setCheckDataStatus("1");
                    researchLetterCheckRec.setOrderOfCurrentNode(workFlow.getOrderOfCurrentNode());
                    baseMapper.insert(researchLetterCheckRec);
//                    relationProResearchLetterMapper.updateById(relationProResearchLetter);
                }
                if ("0".equals(researchLetterCheckRec.getCheckResult())) {
                    //不通过
                    //所有的节点状态改成未完成
                    workFlow.setCurrentNodeStatus("0");
                    bussinessFlowProResearchLetterMapper.updateById(workFlow);
                }
            }
            relationProResearchLetterMapper.updateById(relationProResearchLetter);
        } else {
            //转派任务
            //获取被转派人信息
            RcUser rcuser = userMapper.selectById(researchLetterCheckRec.getDesigneeRecProResearchLetter().getDesigneeId());

            for (int i = 0; i < workFlowList.size(); i++) {
                BussinessFlowProResearchLetter workFlow = workFlowList.get(i);
                if (workFlow.getOrderOfCurrentNode().equals(orderOfCurrentNode)) {
                    //匹配到需要替换节点
                    workFlow.setDesigneeId(rcuser.getId().toString());
                    workFlow.setDesigneeName(rcuser.getName());
                    workFlow.setDesigneeDeptId(rcuser.getDeptId());
                    workFlow.setDesigneeDeptName(rcuser.getDeptName());
                    workFlow.setUpdateor(user.getName());
                    workFlow.setUpdateTime(new Date());
                    //更新工作流
                    bussinessFlowProResearchLetterMapper.updateById(workFlow);
                    //添加转派记录
                    DesigneeRecProResearchLetter designeeRecProResearchLetter = new DesigneeRecProResearchLetter(
                            null, relationProResearchLetter.getIdR(), new Date(), workFlow.getCheckUserId(),
                            workFlow.getCheckUser(), rcuser.getId().toString(), rcuser.getName(), orderOfCurrentNode);
                    designeeRecProResearchLetterService.insert(designeeRecProResearchLetter);
                }
            }
            //更新关系表下级审批人数据
            relationProResearchLetter.setCurrCheckId(rcuser.getId().toString());
            relationProResearchLetter.setCurrCheckName(rcuser.getName());
            relationProResearchLetterMapper.updateById(relationProResearchLetter);
        }
        return null;*/
    }

    

}
