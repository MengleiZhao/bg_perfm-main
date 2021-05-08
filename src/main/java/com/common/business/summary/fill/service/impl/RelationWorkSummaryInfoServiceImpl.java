package com.common.business.summary.fill.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.summary.fill.entity.RelationWorkSummaryInfo;
import com.common.business.summary.fill.entity.TWorkSummaryInfo;
import com.common.business.summary.fill.mapper.RelationWorkSummaryInfoMapper;
import com.common.business.summary.fill.mapper.TWorkSummaryInfoMapper;
import com.common.business.summary.fill.service.RelationWorkSummaryInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.summary.fillcheck.entity.BussinessFlowWorkSummaryInfo;
import com.common.business.summary.fillcheck.mapper.BussinessFlowWorkSummaryInfoMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 项目编写工作总结关系表 服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
@Service
public class RelationWorkSummaryInfoServiceImpl extends ServiceImpl<RelationWorkSummaryInfoMapper, RelationWorkSummaryInfo> implements RelationWorkSummaryInfoService {

    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//主子项目 mapper
    @Autowired
    private RcUserMapper rcUserMapper;//用户表 mapper
    @Autowired
    private RelationWorkSummaryInfoMapper relationWorkSummaryInfoMapper;//工作总结关系表 mapper
    @Autowired
    private TWorkSummaryInfoMapper workSummaryInfoMapper;//工作总结表 mapper
    @Autowired
    private BussinessFlowWorkSummaryInfoMapper bussinessFlowWorkSummaryInfoMapper;//工作总结业务流数据 mapper

    /**
     * 1-编写工作总结 主页面显示（分页显示）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 15:55
     * @updateTime 2021/4/23 15:55
     */
    @Override
    public PageInfo<TProPerformanceInfo> summaryPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId) throws Exception{
        PageHelper.startPage(current,size);
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.summaryPage(proPerformanceInfo,search,userId);
        return new PageInfo<>(proPerformanceInfos);
    }

    /**
     * 2-选择需要编写工作总结的项目(分页显示)
     * 约束条件：编写评价报告 审核通过的情况下才可以编写工作总结、当前登录人是外勤主管
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 17:30
     * @updateTime 2021/4/23 17:30
     */
    @Override
    public PageInfo<TProPerformanceInfo> chooseSummaryProject(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId) throws Exception{
        PageHelper.startPage(current,size);
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.chooseSummaryProject(proPerformanceInfo,search,userId);
        return new PageInfo<>(proPerformanceInfos);
    }

    /**
     * 3-暂存/提交
     * 整体思路：
     *      1）准备工作：
     *          idA:主子项目主键
     *          idR：关系表主键
     *          opreation：操作 0-暂存 1-提交
     *      2）判断前端传递过来的关系表的主键idR是否为空
     *      3）为空==》新增（关系表）
     *          3-1.调用Tool:1.(installRelSummaryObject)设置工作总结关系表对象值
     *          3-2.新增工作总结关系表,并返回主键idR值
     *      4）不为空==》修改(删除（删除 业务流数据、工作总结表）之后再修改（修改工作总结关系表）)
     *          4-1.根据idR拿到原来的关系表数据
     *              4-1.1原来关系表的操作如果是：-1退回 则删除业务流数据
     *          4-2.删除工作总结表
     *          4-3.修改工作总结关系表:调用Tool:1.(installRelSummaryObject)设置工作总结关系表对象值
     *      5) 无论是 “0-暂存” 还是 “1-提交”：新增工作总结表
     *      6) “1-提交”时，新增业务流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 18:03
     * @updateTime 2021/4/23 18:03
     */
    @Override
    public void tempAndSubmit(RelationWorkSummaryInfo relationWorkSummaryInfo, ShiroUser user) throws Exception {
        //1）准备工作：
        //idA:主子项目主键
        Integer idA=relationWorkSummaryInfo.getIdA();
        //idR：关系表主键
        Integer idR=relationWorkSummaryInfo.getIdR();
        //opreation：操作 0-暂存 1-提交
        String opreation=relationWorkSummaryInfo.getCreateStauts();
        //项目经理信息
        RcUser managerUser=rcUserMapper.managerUser(idA,user.getId());

        //2）判断前端传递过来的关系表的主键idR是否为空
        //3）为空==》新增（关系表）
        if(idR==null){
            //3-1.调用Tool:1.(installRelSummaryObject)设置工作总结关系表对象值
            RelationWorkSummaryInfo workSummaryInfo =installRelSummaryObject(relationWorkSummaryInfo,user,managerUser);
            //3-2.新增工作总结关系表,并返回主键idR值
            relationWorkSummaryInfoMapper.insertBackKey(workSummaryInfo);
            relationWorkSummaryInfo.setIdR(workSummaryInfo.getIdR());
        }
        //4）不为空==》修改(删除（删除 业务流数据、工作总结表）之后再修改（修改工作总结关系表）)
        else if(idR!=null){

            EntityWrapper deleteByIdR= new EntityWrapper();
            deleteByIdR.eq("ID_R",idR);

            //4-1.根据idR拿到原来的关系表数据
            RelationWorkSummaryInfo oldWorkSummaryInfo=relationWorkSummaryInfoMapper.selectById(idR);
            //4-1.1原来关系表的操作如果是：-1退回 则删除业务流数据
            if("-1".equals(oldWorkSummaryInfo.getCreateStauts())){
                bussinessFlowWorkSummaryInfoMapper.delete(deleteByIdR);
            }
            //4-2.删除工作总结表
            workSummaryInfoMapper.delete(deleteByIdR);

            //4-3.修改工作总结关系表:调用Tool:1.(installRelSummaryObject)设置工作总结关系表对象值
            RelationWorkSummaryInfo updateSummaryInfo=installRelSummaryObject(relationWorkSummaryInfo,user,managerUser);
            relationWorkSummaryInfoMapper.updateAllColumnById(updateSummaryInfo);

        }
        //5) 无论是 “0-暂存” 还是 “1-提交”：新增工作总结表
        //设置工作总结表的的idR值为关系表的idR值
        relationWorkSummaryInfo.getWorkSummaryInfo().setIdR(relationWorkSummaryInfo.getIdR());
        workSummaryInfoMapper.insert(relationWorkSummaryInfo.getWorkSummaryInfo());
        //6) “1-提交”时，新增业务流数据
        if("1".equals(relationWorkSummaryInfo.getCreateStauts())){
            //Tool:2.(insertChecker)提交时，新增业务流数据（发起人：外勤主管；审批人：项目经理）
            insertChecker(managerUser,relationWorkSummaryInfo.getIdR(),user);
        }

    }

    /**
     * Tool:1.(installRelSummaryObject)设置工作总结关系表对象值
     * @param user 当前登录人（就是外勤主管）
     * @param relationWorkSummaryInfo 要暂存或提交的工作总结关系表和工作总结表对象
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 18:14
     * @updateTime 2021/4/23 18:14
     */
    public RelationWorkSummaryInfo installRelSummaryObject(RelationWorkSummaryInfo relationWorkSummaryInfo, ShiroUser user, RcUser managerUser){
        //版本号:如果idR为空，则说明是新增，需要设置版本号
        if(null==relationWorkSummaryInfo.getIdR()){
            //拿到该idA下的最大的版本号，如果有则在最大的版本号的基础上加 1，如果没有则 设置为 V1
            String maxVersionNO=relationWorkSummaryInfoMapper.queryMaxVersionNO(relationWorkSummaryInfo.getIdA());
            if(StringUtils.isNotEmpty(maxVersionNO)){
                maxVersionNO=maxVersionNO.replaceAll("V","");
                String newMaxVersionNO="V"+(Integer.parseInt(maxVersionNO)+1);
                relationWorkSummaryInfo.setVersionNo(newMaxVersionNO);
            }else{
                relationWorkSummaryInfo.setVersionNo("V1");
            }
        }
        //工作总结申请时间
        if(relationWorkSummaryInfo.getCreateTime()==null){
            relationWorkSummaryInfo.setCreateTime(new Date());
        }
        //版本状态 1-提交，设置审核人为项目经理
        if("1".equals(relationWorkSummaryInfo.getCreateStauts())){
            relationWorkSummaryInfo.setCurrCheckId(String.valueOf(managerUser.getId()));
            relationWorkSummaryInfo.setCurrCheckName(managerUser.getName());
        }
        //申请人==外勤主管（即：当前登录人）
        if(null==relationWorkSummaryInfo.getCreateUaseName() || "".equals(relationWorkSummaryInfo.getCreateUaseName())){
            relationWorkSummaryInfo.setCreateUaseName(user.getName());
        }

        return relationWorkSummaryInfo;
    }

    /**
     * Tool:2.(insertChecker)提交时，新增业务流数据（发起人：外勤主管；审批人：项目经理）
     * @param user 外勤主管
     * @param managerUser 项目经理
     * @param idR 关系表主键值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 10:12
     * @updateTime 2021/4/25 10:12
     */
    @Transactional
    public void insertChecker(RcUser managerUser,Integer idR, ShiroUser user) throws Exception{
        List<BussinessFlowWorkSummaryInfo> flows=new ArrayList<>();
        for(int i=0;i<2;i++){
            //设置发起人（项目外勤主管 即：当前登录人）
            if(i==0){
                BussinessFlowWorkSummaryInfo flowOne =new BussinessFlowWorkSummaryInfo();
                //idR
                flowOne.setIdR(idR);
                //审批人ID
                flowOne.setCheckUserId(String.valueOf(user.getId()));
                //审批人姓名
                flowOne.setCheckUser(user.getName());
                //审批人部门ID
                flowOne.setCheckUserDeptId(String.valueOf(user.getDeptId()));
                //审批人部门名称
                flowOne.setCheckUserDeptName(user.getDeptName());
                //当前节点审批顺序
                flowOne.setOrderOfCurrentNode(1);
                //是否活跃 0-不活跃
                flowOne.setNodeIsActive("0");
                //创建人
                flowOne.setCreateor(user.getName());
                //创建时间
                flowOne.setCreateTime(new Date());
                //审批人工号==员工编号
                flowOne.setCheckUserJobNumber(user.getGroupMemberCode());
                //当前节点状态 1-已完成
                flowOne.setCurrentNodeStatus("1");
                flows.add(flowOne);

            }else{
                BussinessFlowWorkSummaryInfo flowTwo =new BussinessFlowWorkSummaryInfo();
                //idR
                flowTwo.setIdR(idR);
                //审批人ID
                flowTwo.setCheckUserId(String.valueOf(managerUser.getId()));
                //审批人姓名
                flowTwo.setCheckUser(managerUser.getName());
                //审批人部门ID
                flowTwo.setCheckUserDeptId(String.valueOf(managerUser.getDeptId()));
                //审批人部门名称
                flowTwo.setCheckUserDeptName(managerUser.getDeptName());
                //当前节点审批顺序
                flowTwo.setOrderOfCurrentNode(2);
                //是否活跃 1-活跃
                flowTwo.setNodeIsActive("1");
                //创建人
                flowTwo.setCreateor(user.getName());
                //创建时间
                flowTwo.setCreateTime(new Date());
                //审批人工号==员工编号
                flowTwo.setCheckUserJobNumber(managerUser.getGroupMemberCode());
                //当前节点状态 0-未完成
                flowTwo.setCurrentNodeStatus("0");
                flows.add(flowTwo);
            }

        }

        //批量插入业务表
        bussinessFlowWorkSummaryInfoMapper.insertBatches(flows);

    }

}
