package com.common.business.summary.fillcheck.service.impl;

import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.common.business.planmgr.pre.mkoutlinecheck.entity.BussinessFlowProResearchOutline;
import com.common.business.planmgr.pre.mkoutlinecheck.entity.DesigneeRecProResearchOutline;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.summary.fill.entity.RelationWorkSummaryInfo;
import com.common.business.summary.fill.mapper.RelationWorkSummaryInfoMapper;
import com.common.business.summary.fillcheck.entity.BussinessFlowWorkSummaryInfo;
import com.common.business.summary.fillcheck.entity.DesigneeRecWorkSummaryInfo;
import com.common.business.summary.fillcheck.entity.TWorkSummaryCheckRec;
import com.common.business.summary.fillcheck.mapper.BussinessFlowWorkSummaryInfoMapper;
import com.common.business.summary.fillcheck.mapper.DesigneeRecWorkSummaryInfoMapper;
import com.common.business.summary.fillcheck.mapper.TWorkSummaryCheckRecMapper;
import com.common.business.summary.fillcheck.service.TWorkSummaryCheckRecService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 工作总结审批记录表 服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
@Service
public class TWorkSummaryCheckRecServiceImpl extends ServiceImpl<TWorkSummaryCheckRecMapper, TWorkSummaryCheckRec> implements TWorkSummaryCheckRecService {

    @Autowired
    private TWorkSummaryCheckRecMapper workSummaryCheckRecMapper;//工作总结审核 mapper
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//主子项目 mapper
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;//组员 mapper
    @Autowired
    private BussinessFlowWorkSummaryInfoMapper bussinessFlowWorkSummaryInfoMapper;//业务流 mapper
    @Autowired
    private DesigneeRecWorkSummaryInfoMapper designeeRecWorkSummaryInfoMapper;//指派表 mapper
    @Autowired
    private RelationWorkSummaryInfoMapper relationWorkSummaryInfoMapper;//关系表 mapper
    @Autowired
    private RcUserMapper userMapper;//用户表 mapper

    /**
     * 1-审核工作总结 主页面显示
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 11:22
     * @updateTime 2021/4/25 11:22
     */
    @Override
    public PageInfo<TProPerformanceInfo> summaryCheckPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId) throws Exception {
        PageHelper.startPage(current, size);
        List<TProPerformanceInfo> proPerformanceInfos = proPerformanceInfoMapper.summaryCheckPage(proPerformanceInfo, search, userId);
        return new PageInfo<>(proPerformanceInfos);
    }

    /**
     * 2-审批流数据
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 11:59
     * @updateTime 2021/4/25 11:59
     */
    @Override
    public RelationWorkSummaryInfo queryFlowCheck(Integer idR) throws Exception {
        RelationWorkSummaryInfo lastWorkSummaryInfo = new RelationWorkSummaryInfo();
        //业务流数据
        List<BussinessFlowWorkSummaryInfo> flowWorkSummaryInfos = bussinessFlowWorkSummaryInfoMapper.queryByIdR(idR);
        lastWorkSummaryInfo.setBussinessFlowWorkSummaryInfos(flowWorkSummaryInfos);
        //审批记录数据
        List<TWorkSummaryCheckRec> workSummaryCheckRecs = workSummaryCheckRecMapper.queryByIdR(idR);
        lastWorkSummaryInfo.setWorkSummaryCheckRecs(workSummaryCheckRecs);

        return lastWorkSummaryInfo;
    }

    /**
     * 3-审批
     * 整体思路：
     *      1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序
     *      2）根据idR拿到业务流数据,并封装成Map
     *      3）插入数据至审批记录表 调用工具方法 Tool:1.(insertSummaryCheck)插入数据至审批记录表
     *      4）修改审批业务表  调用工具方法 Tool:2.(updateFlow)修改审批业务表
     *      5）修改关系表  调用工具方法 Tool:3.(updateNextChecker)根据审批结果 修改关系表 状态/审批人
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 13:59
     * @updateTime 2021/4/25 13:59
     */
    @Override
    @Transactional
    public void checkSummary(TWorkSummaryCheckRec workSummaryCheckRec, ShiroUser user) throws Exception {
        //1）根据idR拿到业务流数据Map
        HashMap<Integer, BussinessFlowWorkSummaryInfo> flowsMap = bussinessFlowWorkSummaryInfoMapper.queryMapByIdR(workSummaryCheckRec.getIdR());
        //1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序
        Integer orderOfNode=bussinessFlowWorkSummaryInfoMapper.queryOrderOfNode(user.getId(),workSummaryCheckRec.getIdR());

        //3）插入数据至审批记录表 调用工具方法 Tool:1.(insertSummaryCheck)插入数据至审批记录表
        insertSummaryCheck(workSummaryCheckRec, user, orderOfNode);

        //4）修改审批业务表  调用工具方法 Tool:2.(updateFlow)修改审批业务表
        updateFlow(workSummaryCheckRec.getIdR(),orderOfNode,flowsMap,workSummaryCheckRec.getCheckResult(),user);

        //5）修改关系表  调用工具方法 Tool:3.(updateNextChecker)根据审批结果 修改关系表 状态/审批人

    }

    /**
     * 4-选择被转派人(分页显示)除去当前登录人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:04
     * @updateTime 2021/4/25 15:04
     */
    @Override
    public List<TPerformanceWorkingGroup> chooseTransPeople(Integer current, Integer size, TPerformanceWorkingGroup workingGroup, String search, Integer idA, ShiroUser user) throws Exception {
        PageHelper.startPage(current,size);
        List<TPerformanceWorkingGroup> workingGroups=performanceWorkingGroupMapper.queryLiveMemeber(workingGroup,search,idA);
        //遍历，除去当前登录人
        for(TPerformanceWorkingGroup workMemeber:workingGroups){
            if(workMemeber.getGroupMemberId()==String.valueOf(user.getId())){
                workingGroups.remove(workMemeber);
            }
        }
        return workingGroups;
    }

    /**
     * 5-转派
     * 整体思路：
     *      1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序数据对象
     *      2）调用 Tool:4.(transInsertDesignee)插入数据至指派记录表
     *      3）调用 Tool:5.(transUpdateFlow)修改业务表数据
     *      4）修改关系表数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:06
     * @updateTime 2021/4/25 15:06
     */
    @Override
    @Transactional
    public void transfer(Integer idR, TPerformanceWorkingGroup workingGroup, ShiroUser user) throws Exception {
        //1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序数据对象
        BussinessFlowWorkSummaryInfo orderOfNodeValue=bussinessFlowWorkSummaryInfoMapper.queryOrderOfNodeValue(user.getId(),idR);
        Integer orderOfNode=orderOfNodeValue.getOrderOfCurrentNode();

        //2）调用 Tool:4.(transInsertDesignee)插入数据至指派记录表
        transInsertDesignee(idR,workingGroup,user,orderOfNode);

        //3）调用 Tool:5.(transUpdateFlow)修改业务表数据
        transUpdateFlow(orderOfNodeValue,workingGroup,user);

        //4）修改关系表数据
        relationWorkSummaryInfoMapper.updateNextChecker(workingGroup.getGroupMemberId(),workingGroup.getGroupMemberName(),idR);
    }




    /**
     * Tool:1.(insertSummaryCheck)插入数据至审批记录表
     * 整体思路：
     *      1）根据idR判断原来这个人是否有审批数据，如果有，则将原来的 审批数据状态改为 0-历史记录
     *      2）设置审批数据对象
     *      3）插入数据至审批表
     *
     * @param workSummaryCheckRec 审批数据
     * @param user                当前登录用户（当前审批用户）
     * @param orderOfNode         当前登录用户的审批顺序
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:17
     * @updateTime 2021/4/25 14:17
     */
    @Transactional
    public void insertSummaryCheck(TWorkSummaryCheckRec workSummaryCheckRec, ShiroUser user, Integer orderOfNode) throws Exception {
        //1）根据idR判断原来这个人是否有审批数据，如果有，则将原来的 审批数据状态改为 0-历史记录
        workSummaryCheckRecMapper.updateIfOld(user.getId(), workSummaryCheckRec.getIdR());
        //2）设置审批数据对象
        //审批人id
        if (StringUtils.isNotEmpty(workSummaryCheckRec.getCheckUserId())) {
            workSummaryCheckRec.setCheckUserId(String.valueOf(user.getId()));
        }
        //审批人
        if (StringUtils.isNotEmpty(workSummaryCheckRec.getCheckUser())) {
            workSummaryCheckRec.setCheckUser(user.getName());
        }
        //审批时间
        if (null == workSummaryCheckRec.getCheckTime()) {
            workSummaryCheckRec.setCheckTime(new Date());
        }
        //审批数据状态：1-本次数据
        workSummaryCheckRec.setCheckDataStatus("1");
        //当前审批节点顺序
        workSummaryCheckRec.setOrderOfCurrentNode(orderOfNode);

        //3）插入数据至审批表
        workSummaryCheckRecMapper.insert(workSummaryCheckRec);
    }


    /**
     * Tool:2.(updateFlow)修改审批业务表
     * 整体思路：
     *      1）得到下级审批节点
     *      2)有没有通过都得 修改当前节点：0-不活跃,1-已完成,修改人和修改时间为当前登录人
     *      3）通过
     *          3-1.有下级审批节点：修改下级节点：1-活跃,0-未完成,修改人和修改时间为当前登录人
     *      4)不通过
     *          4-1.修改业务表 的发起人 1-活跃,0-未完成,修改人和修改时间为当前登录人
     *
     * @param idR         关系表主键
     * @param orderOfNode 当前审批顺序
     * @param flowsMap    业务流数据
     * @param result      审批结果
     * @param user        当前登录用户
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:33
     * @updateTime 2021/4/25 14:33
     */
    @Transactional
    public void updateFlow(Integer idR, Integer orderOfNode, HashMap<Integer, BussinessFlowWorkSummaryInfo> flowsMap, String result, ShiroUser user) throws Exception {
        //1）得到下级审批节点
        BussinessFlowWorkSummaryInfo nextFlow=flowsMap.get(orderOfNode+1);
        //2)有没有通过都得 修改当前节点：0-不活跃,1-已完成,修改人和修改时间为当前登录人
        BussinessFlowWorkSummaryInfo updateNowflow=new BussinessFlowWorkSummaryInfo();
        updateNowflow.setIdR(idR);
        //修改的节点为 当前节点
        updateNowflow.setOrderOfCurrentNode(orderOfNode);
        //是否活跃 0-不活跃
        updateNowflow.setCurrentNodeStatus("1");
        //节点状态  1-已完成
        updateNowflow.setNodeIsActive("1");
        //修改人
        updateNowflow.setUpdateor(user.getName());
        //修改时间
        updateNowflow.setUpdateTime(new Date());
        bussinessFlowWorkSummaryInfoMapper.updateFlowStatus(updateNowflow);

        if(StringUtils.isNotEmpty(result)){
            //3）通过
            if("1".equals(result)){
                //3-1.有下级审批节点：修改下级节点：1-活跃,0-未完成,修改人和修改时间为当前登录人
                if(nextFlow!=null){
                    BussinessFlowWorkSummaryInfo updateNextflow=new BussinessFlowWorkSummaryInfo();
                    updateNextflow.setIdR(idR);
                    //修改的节点为 当前节点+1
                    updateNextflow.setOrderOfCurrentNode(orderOfNode+1);
                    //是否活跃 1-活跃
                    updateNextflow.setCurrentNodeStatus("1");
                    //节点状态  0-已完成
                    updateNextflow.setNodeIsActive("1");
                    //修改人
                    updateNextflow.setUpdateor(user.getName());
                    //修改时间
                    updateNextflow.setUpdateTime(new Date());
                    bussinessFlowWorkSummaryInfoMapper.updateFlowStatus(updateNextflow);
                }
            }
            //4)不通过
            else if("0".equals(result)){
                //4-1.修改业务表 的发起人 1-活跃,0-未完成,修改人和修改时间为当前登录人
                BussinessFlowWorkSummaryInfo updateFirstflow=new BussinessFlowWorkSummaryInfo();
                updateFirstflow.setIdR(idR);
                //修改的节点为 1 （发起人）
                updateFirstflow.setOrderOfCurrentNode(1);
                //是否活跃 1-活跃
                updateFirstflow.setCurrentNodeStatus("1");
                //节点状态  0-未完成
                updateFirstflow.setNodeIsActive("0");
                //修改人
                updateFirstflow.setUpdateor(user.getName());
                //修改时间
                updateFirstflow.setUpdateTime(new Date());
                bussinessFlowWorkSummaryInfoMapper.updateFlowStatus(updateFirstflow);
            }

        }

    }

    /**
     * Tool:3.(updateNextChecker)根据审批结果 修改关系表 状态/审批人
     * 整体思路：
     *      1）得到下级审批节点
     *      2）1-通过
     *          2-1.当前审批人是最后一个人
     *              2-1.1.修改关系表的状态为 2-已完成；审批人为空
     *          2-2.当前审批人不是最后一个人
     *              2-2.1.修改关系表的审批人为下级审批人
     *      3）0-不通过：修改关系的状态为 -1-退回；审批人为空
     * @param idR          关系表主键
     * @param orderOfNode  当前审批顺序
     * @param flowsMap     业务流数据
     * @param result       审批结果
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:52
     * @updateTime 2021/4/25 14:52
     */
    public void updateNextChecker(Integer idR,Integer orderOfNode,HashMap<Integer,BussinessFlowWorkSummaryInfo> flowsMap,String result){
        //1）得到下级审批节点
        BussinessFlowWorkSummaryInfo nextFlow=flowsMap.get(orderOfNode+1);

        if(StringUtils.isNotEmpty(result)){
            //2）1-通过
            if("1".equals(result)){
                //2-1.当前审批人是最后一个人
                if(nextFlow==null){
                    //2-1.1.修改关系表的状态为 2-已完成；审批人为空
                    relationWorkSummaryInfoMapper.updateStatus("2",idR);
                }
                //2-2.当前审批人不是最后一个人
                else if(nextFlow!=null){
                    //2-2.1.修改关系表的审批人为下级审批人
                    relationWorkSummaryInfoMapper.updateNextChecker(nextFlow.getCheckUserId(),nextFlow.getCheckUser(),idR);
                }
            }
            //3）0-不通过：修改关系的状态为 -1-退回；审批人为空
            else if("0".equals(result)){
                relationWorkSummaryInfoMapper.updateStatus("-1",idR);
            }
        }
    }



    /**
     * 调用 Tool:4.(transInsertDesignee)插入数据至指派记录表
     * @param idR 关系表主键
     * @param workingGroup 前端选择的指派人员
     * @param user 当前登录者 （当前审批人 即:原审批人）
     * @param orderOfNode 当前审批顺序
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:08
     * @updateTime 2021/4/25 15:08
     */
    private void transInsertDesignee(Integer idR, TPerformanceWorkingGroup workingGroup, ShiroUser user, Integer orderOfNode) {
        //最终要插入的指派记录对象
        DesigneeRecWorkSummaryInfo designee=new DesigneeRecWorkSummaryInfo();

        //1）设置指派记录对象值
        //哪一个拟定调研安排关系表
        designee.setIdR(idR);
        //指派时间
        designee.setDesignee(new Date());
        //原审批人ID
        designee.setCheckUserId(String.valueOf(user.getId()));
        //原审批人
        designee.setCheckUser(user.getName());
        //被指派人ID
        designee.setDesigneeId(workingGroup.getGroupMemberId());
        //被指派人姓名
        designee.setDesigneeName(workingGroup.getGroupMemberName());
        //当前节点审批顺序
        designee.setOrderOfCurrentNode(orderOfNode);

        //2）插入数据
        //向指派记录表中 插入数据
        designeeRecWorkSummaryInfoMapper.insert(designee);
    }


    /**
     * Tool:5.(transUpdateFlow)修改业务表数据
     * @param orderOfNodeValue 当前审批顺序对象
     * @param workingGroup    前端选择的指派人员
     * @param nowUser 当前登录者（当前审批人 即:原审批人）
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:08
     * @updateTime 2021/4/25 15:08
     */
    private void transUpdateFlow(BussinessFlowWorkSummaryInfo orderOfNodeValue , TPerformanceWorkingGroup workingGroup, ShiroUser nowUser) {
        //1）设置业务数据对象
        //被指派人ID
        orderOfNodeValue.setDesigneeId(workingGroup.getGroupMemberId());
        //被指派人姓名
        orderOfNodeValue.setDesigneeName(workingGroup.getGroupMemberName());
        //被指派人部门ID
        orderOfNodeValue.setDesigneeDeptId(workingGroup.getDeptId());
        //被指派人部门名称
        orderOfNodeValue.setDesigneeDeptName(workingGroup.getDeptName());
        //修改人
        orderOfNodeValue.setUpdateor(nowUser.getName());
        //修改时间
        orderOfNodeValue.setUpdateTime(new Date());
        //审批人工号
        RcUser user=userMapper.selectById(workingGroup.getDeptId());
        orderOfNodeValue.setCheckUserJobNumber(user.getGroupMemberCode());

        //2）修改部分数据
        bussinessFlowWorkSummaryInfoMapper.updateTransfer(orderOfNodeValue);


    }



}
