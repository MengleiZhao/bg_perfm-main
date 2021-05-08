package com.common.business.planmgr.pre.mkinvarrcheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.mapper.RelationProResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.DesigneeRecProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.TResearchScheduleCheckRec;
import com.common.business.planmgr.pre.mkinvarrcheck.mapper.BussinessFlowProResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarrcheck.mapper.DesigneeRecProResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarrcheck.mapper.TResearchScheduleCheckRecMapper;
import com.common.business.planmgr.pre.mkinvarrcheck.service.TResearchScheduleCheckRecService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Spliterator;

/**
 * <p>
 * 调研安排审批记录表 服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
@Service
@Transactional
public class TResearchScheduleCheckRecServiceImpl extends ServiceImpl<TResearchScheduleCheckRecMapper, TResearchScheduleCheckRec> implements TResearchScheduleCheckRecService {

    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//主子项目mapper
    @Autowired
    private BussinessFlowProResearchScheduleMapper bussinessFlowProResearchScheduleMapper;//审批业务流数据 mapper
    @Autowired
    private TResearchScheduleCheckRecMapper researchScheduleCheckRecMapper;//审批记录数据 mapper
    @Autowired
    private RelationProResearchScheduleMapper relationProResearchScheduleMapper;//关系表 mapper
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;//组员 mapper
    @Autowired
    private DesigneeRecProResearchScheduleMapper designeeRecProResearchScheduleMapper;//指派记录 mapper
    @Autowired
    private RcUserMapper userMapper;//用户 mapper


    /**
     * 1.调研安排审批界面 主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 20:57
     * @updateTime 2021/4/19 20:57
     */
    @Override
    public PageInfo<TProPerformanceInfo> checkPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, Integer preOrScheme, ShiroUser user) throws  Exception{
        PageHelper.startPage(current,size);
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.scheduleOutlinePage(proPerformanceInfo,search,preOrScheme,user.getId(),1);
        return new PageInfo<>(proPerformanceInfos);
    }

    /**
     * 2.审批流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 21:29
     * @updateTime 2021/4/19 21:29
     */
    @Override
    public RelationProResearchSchedule queryFlowCheck(Integer idR) throws  Exception{
        RelationProResearchSchedule lastResearchSchedule=new RelationProResearchSchedule();
        lastResearchSchedule.setIdR(idR);
        if(idR!=null){
            //审批业务流数据
            List<BussinessFlowProResearchSchedule> flows=bussinessFlowProResearchScheduleMapper.queryByIdR(idR);
            lastResearchSchedule.setBussinessFlowProResearchSchedules(flows);

            //审批记录数据
            List<TResearchScheduleCheckRec> checks=researchScheduleCheckRecMapper.queryByIdR(idR);
            lastResearchSchedule.setResearchScheduleCheckRecs(checks);

        }
        return lastResearchSchedule;
    }

    /**
     * 3.审批
     * 整体思路：
     *      1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序
     *      2）根据idR拿到业务流数据,并封装成Map
     *      3）插入数据至审批记录表 调用工具方法 Tool:1.(insertResearchCheck)插入数据至审批记录表
     *      4）修改审批业务表  调用工具方法 Tool:2.(updateFlow)修改审批业务表
     *      5）修改关系表  调用工具方法 Tool:3.(updateNextChecker)根据审批结果 修改关系表 状态/审批人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/20 10:01
     * @updateTime 2021/4/20 10:01
     */
    @Override
    @Transactional
    public void checkResearch(TResearchScheduleCheckRec researchScheduleCheck, ShiroUser user) throws  Exception{

        //1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序
        Integer orderOfNode=bussinessFlowProResearchScheduleMapper.queryOrderOfNode(user.getId(),researchScheduleCheck.getIdR());

        //2）根据idR拿到业务流数据,并封装成Map
        EntityWrapper idRwapper=new EntityWrapper();
        idRwapper.eq("ID_R",researchScheduleCheck.getIdR());
        List<BussinessFlowProResearchSchedule> flows=bussinessFlowProResearchScheduleMapper.selectList(idRwapper);
        HashMap<Integer,BussinessFlowProResearchSchedule> flowsMap=new HashMap<>();
        if(flows!=null && flows.size()>0){
            for(BussinessFlowProResearchSchedule flow:flows){
                flowsMap.put(flow.getOrderOfCurrentNode(),flow);
            }
        }

        //3）插入数据至审批记录表 调用工具方法 Tool:1.(insertResearchCheck)插入数据至审批记录表
        insertResearchCheck(researchScheduleCheck,user,orderOfNode);

        //4）修改审批业务表  调用工具方法 Tool:2.(updateFlow)修改审批业务表
        updateFlow(researchScheduleCheck.getIdR(),orderOfNode,flowsMap,researchScheduleCheck.getCheckResult(),user);

        //5）修改关系表  调用工具方法 Tool:3.(updateNextChecker)根据审批结果 修改关系表 状态/审批人
        updateNextChecker(researchScheduleCheck.getIdR(),orderOfNode,flowsMap,researchScheduleCheck.getCheckResult());


    }

    /**
     * 4.选择被转派人 除去当前登录人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 15:30
     * @updateTime 2021/4/21 15:30
     */
    @Override
    public List<TPerformanceWorkingGroup> chooseTransPeople(Integer current, Integer size, TPerformanceWorkingGroup workingGroup, String search, Integer idA,ShiroUser user) throws  Exception{
        PageHelper.startPage(current,size);
        List<TPerformanceWorkingGroup> workingGroups=performanceWorkingGroupMapper.queryLiveMemeber(workingGroup,search,idA);
        //遍历，如果除去当前登录人
        for(TPerformanceWorkingGroup workMemeber:workingGroups){
            if(workMemeber.getGroupMemberId()==String.valueOf(user.getId())){
                workingGroups.remove(workMemeber);
            }
        }
        return workingGroups;
    }

    /**
     * 5.转派
     * 整体思路：
     *      1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序数据对象
     *      2）调用 Tool:4.(transInsertDesignee)插入数据至指派记录表
     *      3）调用 Tool:5.(transUpdateFlow)修改业务表数据
     *      4）修改关系表数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 15:50
     * @updateTime 2021/4/21 15:50
     */
    @Override
    @Transactional
    public void transfer(Integer idR, TPerformanceWorkingGroup workingGroup,ShiroUser user) throws  Exception{
        //1）根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序数据对象
        BussinessFlowProResearchSchedule orderOfNodeVlue=bussinessFlowProResearchScheduleMapper.queryOrderOfNodeValue(user.getId(),idR);
        Integer orderOfNode=orderOfNodeVlue.getOrderOfCurrentNode();

        //2）调用 Tool:4.(transInsertDesignee)插入数据至指派记录表
        transInsertDesignee(idR,workingGroup,user,orderOfNode);

        //3）调用 Tool:5.(transUpdateFlow)修改业务表数据
        transUpdateFlow(orderOfNodeVlue,workingGroup,user);

        //4）修改关系表数据
        relationProResearchScheduleMapper.updateNextChecker(workingGroup.getGroupMemberId(),workingGroup.getGroupMemberName(),idR);



    }


    /**
     * Tool:1.(insertResearchCheck)插入数据至审批记录表
     * 整体思路：
     *      1）根据idR判断原来这个人是否有审批数据，如果有，则将原来的 审批数据状态改为 0-历史记录
     *      2）设置审批数据对象
     *      3）插入数据至审批表
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/20 10:10
     * @updateTime 2021/4/20 10:10
     */
    @Transactional
    public void insertResearchCheck(TResearchScheduleCheckRec researchScheduleCheck, ShiroUser user,Integer orderOfNode) throws Exception{
        //1）根据idR判断原来这个人是否有审批数据，如果有，则将原来的 审批数据状态改为 0-历史记录
        researchScheduleCheckRecMapper.updateIfOld(user.getId(),researchScheduleCheck.getIdR());

        //2）设置审批数据对象
        //2-1.审批人ID
        if(null==researchScheduleCheck.getCheckUserId()||"".equals(researchScheduleCheck.getCheckUserId())){
            researchScheduleCheck.setCheckUserId(String.valueOf(user.getId()));
        }
        //2-2.审批人姓名
        if(null==researchScheduleCheck.getCheckUser()||"".equals(researchScheduleCheck.getCheckUser())){
            researchScheduleCheck.setCheckUser(user.getName());
        }
        //2-3.审批时间
        if(null==researchScheduleCheck.getCheckTime()){
            researchScheduleCheck.setCheckTime(new Date());
        }

        //2-4.审批数据状态 0-历史数据  1-本次数据
        researchScheduleCheck.setCheckDataStatus("1");
        //2-5.当前节点审批顺序
        researchScheduleCheck.setOrderOfCurrentNode(orderOfNode);

        //3）插入数据至审批表
        researchScheduleCheckRecMapper.insert(researchScheduleCheck);

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
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 9:42
     * @updateTime 2021/4/21 9:42
     */
    @Transactional
    public void updateFlow(Integer idR,Integer orderOfNode,HashMap<Integer,BussinessFlowProResearchSchedule> flowsMap,String result,ShiroUser user) throws Exception{
        //1）下级审批节点
        BussinessFlowProResearchSchedule nextFlow=flowsMap.get(orderOfNode+1);

        //2）有没有通过都得 修改当前节点：0-不活跃,1-已完成
        BussinessFlowProResearchSchedule updateNowflow=new BussinessFlowProResearchSchedule();
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
        bussinessFlowProResearchScheduleMapper.updateFlowStatus(updateNowflow);

        if(StringUtils.isNotEmpty(result)){
            //3）通过
            if("1".equals(result)){
                // 3-1.有下级审批节点：修改下级节点：1-活跃,0-未完成
                if(nextFlow!=null){
                    BussinessFlowProResearchSchedule updateNextflow=new BussinessFlowProResearchSchedule();
                    updateNextflow.setIdR(idR);
                    //修改的节点为 当前节点+1
                    updateNextflow.setOrderOfCurrentNode(orderOfNode+1);
                    //是否活跃 1-活跃
                    updateNextflow.setCurrentNodeStatus("1");
                    //节点状态  0-未完成
                    updateNextflow.setNodeIsActive("0");
                    //修改人
                    updateNextflow.setUpdateor(user.getName());
                    //修改时间
                    updateNextflow.setUpdateTime(new Date());
                    bussinessFlowProResearchScheduleMapper.updateFlowStatus(updateNextflow);
                }

            }
            //4)不通过
            else if("0".equals(result)){
                //4-1.修改业务表 的发起人 1-活跃,0-未完成,修改人和修改时间为当前登录人
                BussinessFlowProResearchSchedule updateFirstflow=new BussinessFlowProResearchSchedule();
                updateFirstflow.setIdR(idR);
                //修改的节点为 1 （发起人）
                updateFirstflow.setOrderOfCurrentNode(1);
                //是否活跃 1-活跃
                updateFirstflow.setCurrentNodeStatus("1");
                //节点状态  0-已完成
                updateFirstflow.setNodeIsActive("1");
                //修改人
                updateFirstflow.setUpdateor(user.getName());
                //修改时间
                updateFirstflow.setUpdateTime(new Date());
                bussinessFlowProResearchScheduleMapper.updateFlowStatus(updateFirstflow);
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
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 9:59
     * @updateTime 2021/4/21 9:59
     */
    public void updateNextChecker(Integer idR,Integer orderOfNode,HashMap<Integer,BussinessFlowProResearchSchedule> flowsMap,String result) throws Exception{

        //1）得到下级审批节点
        BussinessFlowProResearchSchedule nextFlow=flowsMap.get(orderOfNode+1);

        if(StringUtils.isNotEmpty(result)){
            //2）1-通过
            if("1".equals(result)){
                //2-1.当前审批人是最后一个人
                if(nextFlow==null){
                    //2-1.1.修改关系表的状态为 2-已完成；审批人为空
                    relationProResearchScheduleMapper.updateStatus("2",idR);
                }
                //2-2.当前审批人不是最后一个人
                else if(nextFlow!=null){
                    //2-2.1.修改关系表的审批人为下级审批人
                    relationProResearchScheduleMapper.updateNextChecker(nextFlow.getCheckUserId(),nextFlow.getCheckUser(),idR);
                }
            }
            //3）0-不通过：修改关系的状态为 -1-退回；审批人为空
            else if("0".equals(result)){
                relationProResearchScheduleMapper.updateStatus("-1",idR);
            }
        }


    }


    /**
     * Tool:4.(transInsertDesignee)插入数据至指派记录表
     * 整体思路：
     *      1）设置指派记录对象值
     *      2）插入数据
     * @param idR           拟定调研安排关系表
     * @param workingGroup  被指派人
     * @param user          审批人
     * @param orderOfNode   当前审批节点顺序值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 17:12
     * @updateTime 2021/4/21 17:12
     */
    @Transactional
    public void transInsertDesignee(Integer idR, TPerformanceWorkingGroup workingGroup,ShiroUser user,Integer orderOfNode)throws Exception{
        //最终要插入的指派记录对象
        DesigneeRecProResearchSchedule designee=new DesigneeRecProResearchSchedule();

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
        designeeRecProResearchScheduleMapper.insert(designee);


    }

    /**
     * Tool:5.(transUpdateFlow)修改业务表数据
     * 整体思路：
     *      1）设置业务数据对象
     *      2）修改部分数据
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 17:27
     * @updateTime 2021/4/21 17:27
     */
    @Transactional
    public void transUpdateFlow(BussinessFlowProResearchSchedule orderOfNodeVlue, TPerformanceWorkingGroup workingGroup,ShiroUser nowUser)throws  Exception{
        //1）设置业务数据对象
        //被指派人ID
        orderOfNodeVlue.setDesigneeId(workingGroup.getGroupMemberId());
        //被指派人姓名
        orderOfNodeVlue.setDesigneeName(workingGroup.getGroupMemberName());
        //被指派人部门ID
        orderOfNodeVlue.setDesigneeDeptId(workingGroup.getDeptId());
        //被指派人部门名称
        orderOfNodeVlue.setDesigneeDeptName(workingGroup.getDeptName());
        //修改人
        orderOfNodeVlue.setUpdateor(nowUser.getName());
        //修改时间
        orderOfNodeVlue.setUpdateTime(new Date());
        //审批人工号
        RcUser user=userMapper.selectById(workingGroup.getDeptId());
        orderOfNodeVlue.setCheckUserJobNumber(user.getGroupMemberCode());

        //2）修改部分数据
        bussinessFlowProResearchScheduleMapper.updateTransfer(orderOfNodeVlue);

    }





}
