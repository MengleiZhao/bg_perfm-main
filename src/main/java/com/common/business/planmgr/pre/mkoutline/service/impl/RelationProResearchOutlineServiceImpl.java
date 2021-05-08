package com.common.business.planmgr.pre.mkoutline.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.mapper.RelationProResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarr.mapper.TResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarrcheck.mapper.BussinessFlowProResearchScheduleMapper;
import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.common.business.planmgr.pre.mkoutline.entity.TResearchOutline;
import com.common.business.planmgr.pre.mkoutline.mapper.RelationProResearchOutlineMapper;
import com.common.business.planmgr.pre.mkoutline.mapper.TResearchOutlineMapper;
import com.common.business.planmgr.pre.mkoutline.service.RelationProResearchOutlineService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mkoutlinecheck.entity.BussinessFlowProResearchOutline;
import com.common.business.planmgr.pre.mkoutlinecheck.mapper.BussinessFlowProResearchOutlineMapper;
import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.common.business.planmgr.scheme.mkoutline.mapper.TResearchOutlineTempMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 项目调研提纲关系表  服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
@Service
@Transactional
public class RelationProResearchOutlineServiceImpl extends ServiceImpl<RelationProResearchOutlineMapper, RelationProResearchOutline> implements RelationProResearchOutlineService {

    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//项目主子信息 mapper
    @Autowired
    private TResearchOutlineMapper researchOutlineMapper;//拟定调研提纲安排 mapper
    @Autowired
    private RelationProResearchOutlineMapper relationProResearchOutlineMapper;//拟定调研提纲关系表 mapper
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;//工作组 mapper
    @Autowired
    private BussinessFlowProResearchOutlineMapper bussinessFlowProResearchOutlineMapper;// 调研安排审批业务表 mapper
    @Autowired
    private RcUserMapper userMapper;//用户表 mapper
    @Autowired
    private TResearchOutlineTempMapper tResearchOutlineTempMapper;//拟定调研提纲初始化数据 mapper
    /**
     * 1.拟定调研提纲主页面显示
     * 约束条件：当前登录人是项目主管/或者组员、明确工作任务时有“拟定调研提纲” 且已经勾选、已经立项、已经创建工作组、跟拟定关系表相关联
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 21:07
     * @updateTime 2021/4/21 21:07
     */
    @Override
    public PageInfo<TProPerformanceInfo> queryForPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId, Integer preOrScheme) throws Exception{
        //1.分页拦截器
        PageHelper.startPage(current,size);
        //2.从后台拿数据
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.pageForOutlineSchedule(proPerformanceInfo,search,userId,preOrScheme);
        if(proPerformanceInfos!=null && proPerformanceInfos.size()>0){
            return new PageInfo<>(proPerformanceInfos);
        }else{
            return null;//没有需要当前登录人 拟定调研安排
        }
    }

    /**
     * 2.分页显示 选择要拟定调研提纲的项目
     * 约束条件：
     *      1-当前登录人是项目主管/或者组员、明确工作任务时有“拟定调研提纲” 且已经勾选、已经立项、已经创建工作组
     *      2-在1的基础上判断，现存的拟定单中，是否有2-已完成的，如果有已通过的，可以再次发起第二、三 …… 等等版本
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 9:13
     * @updateTime 2021/4/22 9:13
     */
    @Override
    public List<TProPerformanceInfo> chooseOutlineProject(Integer current, Integer size, String userId, TProPerformanceInfo proPerformanceInfo, String search, Integer preOrScheme) throws Exception{
        PageHelper.startPage(current,size);
        //最终要返回的项目集合
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.chooseRecarchProject(userId,proPerformanceInfo,search,preOrScheme,2);
        return proPerformanceInfos;

    }

    /**
     * 3.显示该idR下的原来的拟定表数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 9:52
     * @updateTime 2021/4/22 9:52
     */
    @Override
    public List<TResearchOutline> showOutlines(Integer idR)throws Exception {
        EntityWrapper idRwapper=new EntityWrapper();
        idRwapper.eq("ID_R",idR);
        List<TResearchOutline> researchOutlines=researchOutlineMapper.selectList(idRwapper);
        return researchOutlines;
    }

    /**
     * 4-显示初始调研提纲数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 10:16
     * @updateTime 2021/4/22 10:16
     */
    @Override
    public List<TResearchOutlineTemp> initialOutlines() throws Exception{
        List<TResearchOutlineTemp> showDatas = tResearchOutlineTempMapper.queryForPage(null);//接收的是TResearchOutlineTemp类型的列表
        return showDatas;
    }

    /**
     * 5-暂存/提交
     * 整体思路：
     *整体思路：
     *      1）准备工作：
     *          idA:主子项目主键
     *          idR：关系表主键
     *          opreation：操作 0-暂存 1-提交
     *          workCharge：项目的外勤主管
     *      2）判断前端传递过来的调研提纲关系表的主键idR是否为空
     *      3）为空==》新增
     *          3-1.调用Tool:1.(installProScheduleObject)设置调研提纲关系表值审批对象值
     *          3-2.新增拟定调研提纲关系表,并返回主键idR值
     *      4）不为空==》修改(删除之后再新增)
     *          4-1.根据idR拿到原来的关系表数据
     *              4-1.1原来关系表的操作如果是：-1退回 则删除业务流数据
     *          4-2.删除拟定调研提纲表
     *          4-3.修改拟定调研提纲关系表数据:调用Tool:1.(installProScheduleObject)设置调研提纲关系表值审批对象值
     *      5) 无论是 “0-暂存” 还是 “1-提交”：新增拟定调研提纲表
     *      6) “1-提交”时，新增业务流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 10:51
     * @updateTime 2021/4/22 10:51
     */
    @Override
    @Transactional
    public void tempAndSubmit(RelationProResearchOutline proResearchOutline, ShiroUser user) throws Exception{
        //1）准备工作：
        //主子项目的idA
        Integer idA=proResearchOutline.getIdA();
        //判断 拟定  新增和修改,idR为空或者是空字符串，则是新增，否则是修改
        Integer idR=proResearchOutline.getIdR();
        //操作:0-暂存  1-提交（审批中）
        String opreation=proResearchOutline.getCreateStauts();
        //根据idA查询外勤主管
        TPerformanceWorkingGroup workCharge=performanceWorkingGroupMapper.queryWorkCharge(idA);

        //2）判断前端传递过来的调研提纲关系表的主键idR是否为空
        //3）为空==》新增
        if(idR==null){
            //3-1.调用 Tool:1.(installProOutlineObject)设置调研提纲关系表值审批对象值
            RelationProResearchOutline installOutline=installProOutlineObject(proResearchOutline,user,workCharge);
            //3-2.新增拟定调研提纲关系表,并返回主键idR值
            relationProResearchOutlineMapper.insertBackKey(installOutline);
            proResearchOutline.setIdR(installOutline.getIdR());
        }
        //4）不为空==》修改(删除之后再新增)
        else{

            EntityWrapper deleteByIdR= new EntityWrapper();
            deleteByIdR.eq("ID_R",idR);

            //4-1.根据idR拿到原来的关系表数据
            RelationProResearchOutline oldOutline=relationProResearchOutlineMapper.selectById(idR);
            //4-1.1.原来关系表的操作如果是：-1退回 则删除业务流数据
            if("-1".equals(oldOutline.getCreateStauts())){
                bussinessFlowProResearchOutlineMapper.delete(deleteByIdR);
            }

            //4-2.删除拟定调研提纲表
            researchOutlineMapper.delete(deleteByIdR);

            //4-3.修改拟定调研提纲关系表数据:调用Tool:1.(installProOutlineObject)设置调研提纲关系表值审批对象值
            RelationProResearchOutline updateOutline=installProOutlineObject(proResearchOutline,user,workCharge);
            relationProResearchOutlineMapper.updateAllColumnById(updateOutline);

        }

        //5) 无论是 “0-暂存” 还是 “1-提交”：新增拟定调研提纲表
        //设置拟定调研提纲的idR值为关系表的idR值
        for(TResearchOutline outline:proResearchOutline.getResearchOutlines()){
            outline.setIdR(proResearchOutline.getIdR());
        }
        researchOutlineMapper.insertBatches(proResearchOutline.getResearchOutlines());

        //6) “1-提交”时，新增业务流数据
        if(opreation!=null && "1".equals(opreation)){
            //项目经理id值
            String managerId=proPerformanceInfoMapper.queryManagerId(idA);
            Integer[] checkers={user.getId(),Integer.parseInt(workCharge.getGroupMemberId()),Integer.parseInt(managerId)};
            //Tool:2.(insertChecker)新增审批流业务表数据：发起人、一级审批人、二级审批人
            insertChecker(checkers,idR,user.getName());
        }


    }



    /**
     * Tool:1.(installProOutlineObject)设置调研提纲关系表值审批对象值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 11:00
     * @updateTime 2021/4/22 11:00
     */
    public RelationProResearchOutline installProOutlineObject(RelationProResearchOutline proResearchOutline,ShiroUser user,TPerformanceWorkingGroup workCharge)throws Exception{
        //版本号 如果idR为空，则说明是新增，需要设置版本号
        if(proResearchOutline.getIdR()==null){
            //根据idA查询该项目前最大的版本号，如果有则在最大的版本号的基础上加 1，如果没有则 设置为 V1
            String maxVersionNO=relationProResearchOutlineMapper.queryMaxVersionNO(proResearchOutline.getIdA());
            if(StringUtils.isNotEmpty(maxVersionNO)){
                maxVersionNO=maxVersionNO.replaceAll("V","");
                String newMaxVersionNO="V"+(Integer.parseInt(maxVersionNO)+1);
                proResearchOutline.setVersionNo(newMaxVersionNO);
            }else{
                proResearchOutline.setVersionNo("V1");
            }
        }

        //调研提纲（申请）时间
        if(proResearchOutline.getCreateStauts()==null){
            proResearchOutline.setCreateTime(new Date());
        }

        //申请人
        if(proResearchOutline.getCreateUaseName()==null || "".equals(proResearchOutline.getCreateUaseName())){
            proResearchOutline.setCreateUaseName(user.getName());
        }

        //如果是 1 提交，则设置一级审批人为外勤主管
        if("1".equals(proResearchOutline.getCreateStauts())){
            proResearchOutline.setCurrCheckId(workCharge.getGroupMemberId());
            proResearchOutline.setCurrCheckName(workCharge.getGroupMemberName());
        }

        return proResearchOutline;
    }



    /**
     * Tool:2.(insertChecker)新增审批流业务表数据：发起人、一级审批人、二级审批人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 13:50
     * @updateTime 2021/4/22 13:50
     */
    @Transactional
    public void insertChecker(Integer[] checkers,Integer idR,String name)throws Exception{
        //最终要批量插入的业务数据
        List<BussinessFlowProResearchOutline> flowsList=new ArrayList<>();

        //根据传递过来的审核人id值（可能有重复的id值）组成id集合，根据id查询用户数据 Map集合
        List<Integer> userIds=new ArrayList<>();
        for(int j=0;j<checkers.length;j++){
            userIds.add(checkers[j]);
        }
        HashMap<Integer,RcUser> checkUsersMap=userMapper.queryByIds(userIds);

        for(int i=0;i<checkers.length;i++){
            //从用户表中拿到相关用户数据
            RcUser user=checkUsersMap.get(checkers[i]);
            //将用户数据转化为业务数据
            BussinessFlowProResearchOutline flow=new BussinessFlowProResearchOutline();
            flow.setIdR(idR);
            flow.setCheckUserId(String.valueOf(user.getId()));
            flow.setCheckUser(user.getName());
            flow.setCheckUserDeptId(user.getDeptId());
            flow.setCheckUserDeptName(user.getDeptName());
            flow.setOrderOfCurrentNode(i+1);
            //刚开始得时候，一级审批人是活跃的，发起人和二级审批人不活跃
            if(1==i){
                flow.setNodeIsActive("1");
            }else{
                flow.setNodeIsActive("0");
            }
            flow.setCreateor(name);
            flow.setCreateTime(new Date());
            flow.setCheckUserJobNumber(user.getGroupMemberCode());
            //刚开始时，发起人是完成的，其他人都是未完成的
            if(0==i){
                flow.setCurrentNodeStatus("1");
            }else{
                flow.setCurrentNodeStatus("0");
            }
            flowsList.add(flow);

        }
        //批量插入业务数据
        bussinessFlowProResearchOutlineMapper.insertBatches(flowsList);
    }





}
