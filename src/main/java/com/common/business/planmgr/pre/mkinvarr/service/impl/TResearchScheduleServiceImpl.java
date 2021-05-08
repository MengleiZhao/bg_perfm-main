package com.common.business.planmgr.pre.mkinvarr.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.library.experts.entity.TLibraryPerformanceExpert;
import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.mapper.RelationProResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarr.mapper.TResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarr.service.TResearchScheduleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mkinvarr.web.ResearchScheduleVo;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarrcheck.mapper.BussinessFlowProResearchScheduleMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.SchemaOutputResolver;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 * TResearchScheduleServiceImpl [拟定调研安排表 服务实现层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
@Service
@Transactional
public class TResearchScheduleServiceImpl extends ServiceImpl<TResearchScheduleMapper, TResearchSchedule> implements TResearchScheduleService {

    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//项目主子信息 mapper
    @Autowired
    private TResearchScheduleMapper researchScheduleMapper;//拟定调研安排 mapper
    @Autowired
    private RelationProResearchScheduleMapper relationProResearchScheduleMapper;//拟定调研安排关系表 mapper
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;//工作组 mapper
    @Autowired
    private BussinessFlowProResearchScheduleMapper bussinessFlowProResearchScheduleMapper;// 调研安排审批业务表 mapper
    @Autowired
    private RcUserMapper userMapper;//用户表 mapper


    /**
     * 1.TResearchScheduleServiceImpl [拟定调研安排表 服务实现层]
     * 主页面显示 :约束条件：当前登录人是项目主管/或者组员、明确工作任务时有“拟定调研安排” 且已经勾选、已经立项、已经创建工作组、拟定关系表中存在
     * 整体思路：
     *      1）分页拦截器
     *      2）从后台拿到需要拟定调研安排的项目
     * @param current                  开始查询的页码数 默认为第1页
     * @param size                     每页的大小  默认每页显示10条数据
     * @param proPerformanceInfo       精确查询封装的对象
     * @param search                   综合查询的字段
     * @param userId                   当前登录用户的id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 10:30
     * @updateTime 2021/4/19 10:03
     */
    @Override
    public PageInfo<TProPerformanceInfo> queryForPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId,Integer preOrScheme) {
        //1.分页拦截器
        PageHelper.startPage(current,size);
        //2.从后台拿数据
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.pageForResearchSchedule(proPerformanceInfo,search,userId,preOrScheme);
        if(proPerformanceInfos!=null && proPerformanceInfos.size()>0){
            return new PageInfo<>(proPerformanceInfos);
        }else{
            return null;//没有需要当前登录人 拟定调研安排
        }

    }

    /**
     * 2.分页显示 选择要拟定调研安排的项目
     * 约束条件：
     *      1-当前登录人是项目主管/或者组员、明确工作任务时有“拟定调研安排” 且已经勾选、已经立项、已经创建工作组
     *      2-在1的基础上判断，现存的拟定单中，是否有2-已完成的，如果有已通过的，可以再次发起第二、三 …… 等等版本
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/15 14:44
     * @updateTime 2021/4/19 10:03
     */
    @Override
    public List<TProPerformanceInfo> chooseRecarchProject(Integer current, Integer size, String userId,TProPerformanceInfo proPerformanceInfo,String search,Integer preOrScheme) {
        PageHelper.startPage(current,size);
        //最终要返回的项目集合
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.chooseRecarchProject(userId,proPerformanceInfo,search,preOrScheme,1);

        return proPerformanceInfos;
    }

    /**
     * 3.TResearchScheduleServiceImpl [拟定调研安排表 服务实现层]
     * 拟定调研安排的 组长、组员的选择
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 14:43
     * @updateTime 2021/4/19 10:10
     */
    @Override
    public PageInfo<TPerformanceWorkingGroup> chooseResearchPeople(Integer current, Integer size, TPerformanceWorkingGroup performanceWorkingGroup, String search, ResearchScheduleVo researchVo) {
        //项目主键idA
        Integer idA=researchVo.getIdA();
        PageHelper.startPage(current,size);
        //调用 员工 mapper接口中的方法分页查询所有符合条件的员工信息 得到的是集合
        List<TPerformanceWorkingGroup> performanceWorkingGroups= performanceWorkingGroupMapper.queryLiveMemeber(performanceWorkingGroup,search,idA);
        return new PageInfo<>(performanceWorkingGroups);


    }

    /**
     * 4.选择组员信息，跟老数据匹配返还给前端
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 9:55
     * @updateTime 2021/4/16 9:55
     */
    @Override
    public TResearchSchedule goBackMembersGroups(ResearchScheduleVo researchVo) {
        //最终要返回的安排表对象(先将 前端填写好的 调研表对象进行赋值)
        TResearchSchedule lastResearchSchedule=researchVo.getResearchSchedule();

        //拿到前端选择的组员集合
        List<TPerformanceWorkingGroup> chooseMembers=researchVo.getChooseMembers();

        //判断是 组长还是组员
        Integer who=researchVo.getWho();
        if(who!=null && who==0){
            if(chooseMembers.size()==1){
                lastResearchSchedule.setGroupLeaderId(chooseMembers.get(0).getGroupMemberId());
                lastResearchSchedule.setGroupLeader(chooseMembers.get(0).getGroupMemberName());
            }

        }else if(who!=null && who==0){

            //将组员id和姓名进行拼接反传过去
            String memberNames=null;//员工姓名，中间用|分割
            List<String> memeberIds=new ArrayList<>();//员工id集合
            for(TPerformanceWorkingGroup memeber:chooseMembers){
                memberNames+=memeber.getGroupMemberName()+"|";
                memeberIds.add(memeber.getGroupMemberId());
            }

            //设置对象
            lastResearchSchedule.setMemberNames(memberNames);
            lastResearchSchedule.setMemeberIds(memeberIds);
        }

        return lastResearchSchedule;
    }


    /**
     * 5.TResearchScheduleServiceImpl [拟定调研安排表 服务实现层]
     * 拟定调研安排 提交/暂存  “暂存”为0  “提交”就是“审批中”为1
     * 整体思路：
     *      1）准备工作：
     *          idA:主子项目主键
     *          idR：关系表主键
     *          opreation：操作 0-暂存 1-提交
     *          workCharge：项目的外勤主管
     *      2）判断前端传递过来的拟定调研安排关系表的主键idR是否为空
     *      3）为空==》新增
     *          3-1.调用Tool:1.(installProScheduleObject)设置调研安排关系表值审批对象值
     *          3-2.新增拟定调研安排关系表,并返回主键idR值
     *      4）不为空==》修改(删除之后再新增)
     *          4-1.根据idR拿到原来的关系表数据
     *          4-2.原来关系表的操作 oldOpreation (-1退回)
     *          4-3.修改拟定调研安排关系表数据:调用Tool:1.(installProScheduleObject)设置调研安排关系表值审批对象值
     *          4-4.判断原来拟定关系表中的数据状态（ -1退回 ）
     *          4-4.1. "-1退回": 删除业务流数据
     *          4-5.删除拟定调研安排表
     *      5) 无论是 “0-暂存” 还是 “1-提交”：新增拟定调研安排表
     *      6) “1-提交”时，新增业务流数据
     * @param proResearchSchedule 调研安排关系表对象，每一个关系表对应多个调研安排表（集合）
     * 业务逻辑：点击“拟定调研安排”时点击了“暂存”，那么下次进来时，该项目显示的是“修改”
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 16:30
     * @updateTime 2021/4/16 11:06
     */
    @Override
    @Transactional
    public void tempAndSubmit(RelationProResearchSchedule proResearchSchedule,ShiroUser user) {
        //主子项目的idA
        Integer idA=proResearchSchedule.getIdA();
        //判断 拟定  新增和修改,idR为空或者是空字符串，则是新增，否则是修改
        Integer idR=proResearchSchedule.getIdR();
        //操作:0-暂存  1-提交（审批中）
        String opreation=proResearchSchedule.getCreateStauts();
        //根据idA查询外勤主管
        TPerformanceWorkingGroup workCharge=performanceWorkingGroupMapper.queryWorkCharge(idA);

        //1）判断前端传递过来的拟定调研安排关系表的主键idR是否为空
        //2）为空==》新增
        if(null==idR || "".equals(idR)){
            //新增拟定调研安排关系表,并返回主键idR值
            //调用Tool:1.(installProScheduleObject)设置调研安排关系表值审批对象值
            RelationProResearchSchedule proSchedule=installProScheduleObject(proResearchSchedule,user,workCharge);
            relationProResearchScheduleMapper.insertBackKey(proSchedule);
            idR=proSchedule.getIdR();
        }
        //3）不为空==》修改
        else{
            //根据idR拿到原来的关系表数据
            RelationProResearchSchedule oldProSchedule=relationProResearchScheduleMapper.selectById(idR);
            String oldOperation=oldProSchedule.getCreateStauts();
            //修改拟定调研安排关系表数据:调用Tool:1.(installProScheduleObject)设置调研安排关系表值审批对象值
            RelationProResearchSchedule updateProSchedule=installProScheduleObject(oldProSchedule,user,workCharge);
            relationProResearchScheduleMapper.updateAllColumnById(updateProSchedule);

            //判断原来拟定关系表中的数据状态（ -1退回 ）
            // -1退回: 删除业务流数据
            EntityWrapper deleteByIdR= new EntityWrapper();
            deleteByIdR.eq("ID_R",idR);
            if("-1".equals(oldOperation)){
                bussinessFlowProResearchScheduleMapper.delete(deleteByIdR);
            }
            //删除拟定调研安排表
            researchScheduleMapper.delete(deleteByIdR);

        }

        //无论是 “0-暂存” 还是 “1-提交”：新增拟定调研安排表
        //调用 Tool:3.(installScheduleObject)设置拟定调研安排对象数据
        List<TResearchSchedule> researchSchedules=installScheduleObject(proResearchSchedule);
        researchScheduleMapper.insertBatches(researchSchedules);

        //“1-提交”时，新增业务流数据
        if(opreation!=null && "1".equals(opreation)){
            //项目经理id值
            String managerId=proPerformanceInfoMapper.queryManagerId(idA);
            Integer[] checkers={user.getId(),Integer.parseInt(workCharge.getGroupMemberId()),Integer.parseInt(managerId)};
            //Tool:2.(insertChecker)新增审批流业务表数据：发起人、一级审批人、二级审批人
            insertChecker(checkers,idR,user.getName());
        }


    }

    /**
     * 6.显示该idR中的调研安排数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 14:10
     * @updateTime 2021/4/19 14:10
     */
    @Override
    public List<TResearchSchedule> showResearchs(Integer idR) {
        EntityWrapper idRwrapper=new EntityWrapper();
        idRwrapper.eq("ID_R",idR);
        List<TResearchSchedule> researchSchedules=researchScheduleMapper.selectList(idRwrapper);
        if(researchSchedules!=null && researchSchedules.size()>0){
            for(TResearchSchedule researchSchedule:researchSchedules){
                //将组员字段拆分成一个字符串和集合
                String groupMembers=researchSchedule.getGroupMembers();
                //拿到的值是：（1,张三） 集合
                String[] groupMemberList= groupMembers.split(Pattern.quote("|"));
                String memberNames=null;//姓名（张三|李四）
                List<String> memeberIds=new ArrayList<>();//员工的id集合
                for(String member:groupMemberList){
                    List<String> memberInfo=Arrays.asList(member.split(Pattern.quote(",")));
                    memeberIds.add(memberInfo.get(0));
                    memberNames+=memberInfo+"|";
                }
                researchSchedule.setMemeberIds(memeberIds);
                researchSchedule.setMemberNames(memberNames);
            }

        }
        return researchSchedules;
    }


    /**
     * Tool:1.(installProScheduleObject)设置调研安排关系表值审批对象值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 14:17
     * @updateTime 2021/4/16 14:17
     */
    public RelationProResearchSchedule installProScheduleObject(RelationProResearchSchedule proResearchSchedule,ShiroUser user,TPerformanceWorkingGroup workCharge){
        //最终要返回的调研安排关系表对象
        RelationProResearchSchedule proSchedule=new RelationProResearchSchedule();
        //idA主子项目id值
        Integer idA=proResearchSchedule.getIdA();
        proSchedule.setIdA(idA);
        //调研安排关系表的主键idR
        Integer idR=proResearchSchedule.getIdR();
        proSchedule.setIdR(idR);

        //调研安排（申请）时间
        if(null==proResearchSchedule.getCreateTime()){
            proSchedule.setCreateTime(new Date());
        }
        //申请人姓名
        if(null==proResearchSchedule.getCreateUaseName()){
            proSchedule.setCreateUaseName(user.getName());
        }

        //如果不是 0-暂存 操作，则设置审核人
        if(!"0".equals(proResearchSchedule.getCreateStauts())){
            //当前审批人ID=外勤主管ID ; 当前审批人姓名=外勤主管姓名
            if(workCharge!=null){
                proSchedule.setCurrCheckId(workCharge.getGroupMemberId());
                proSchedule.setCurrCheckName(workCharge.getGroupMemberName());
            }
        }


        //如果idA为空，则设置版本号
        if(idR==null){
            //查询该idA下的最大版本号
            String maxVersionNO=relationProResearchScheduleMapper.queryMaxVersionNO(idA);
            maxVersionNO=maxVersionNO.replaceAll("V","");
            String newVersionNO="V"+(Integer.parseInt(maxVersionNO)+1);
            proSchedule.setVersionNo(newVersionNO);
        }

        return proSchedule;
    }


    /**
     * Tool:2.(insertChecker)新增审批流业务表数据：发起人、一级审批人、二级审批人
     * @param checkers 审批人id数组
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 17:36
     * @updateTime 2021/4/16 17:36
     */
    public void insertChecker(Integer[] checkers,Integer idR,String name){
        for(int i=0;i<checkers.length;i++){
            //根据id从用户表中拿数据
            RcUser user=userMapper.selectById(checkers[i]);
            //设置对象
            BussinessFlowProResearchSchedule flow=new BussinessFlowProResearchSchedule();
            flow.setIdR(idR);
            flow.setCheckUserId(String.valueOf(user.getId()));//审批人id
            flow.setCheckUser(user.getName());//审批人姓名
            flow.setCheckUserDeptId(user.getDeptId());//审批人部门ID
            flow.setCheckUserDeptName(user.getDeptName());//审批人部门名称
            flow.setOrderOfCurrentNode(i+1);//当前节点审批顺序
            //是否活跃
            if(i==1){
                flow.setNodeIsActive("1");//刚开始时，发起人是不活跃的，下级审批人是活跃的，其他人都是不活跃的
            }else{
                flow.setNodeIsActive("0");
            }

            flow.setCreateor(name);//创建人
            if(null==flow.getCreateTime()){
                flow.setCreateTime(new Date());//创建时间
            }
            flow.setUpdateor(name);//修改人
            if(null==flow.getUpdateTime()){
                flow.setUpdateTime(new Date());//修改时间
            }
            flow.setCheckUserJobNumber(user.getGroupMemberCode());//审批人工号==groupMemberCode员工编号
            if(i==0){
                flow.setCurrentNodeStatus("1");//刚开始时，发起人是1-已完成的，其他都是0-未完成
            }else{
                flow.setCurrentNodeStatus("0");
            }

            bussinessFlowProResearchScheduleMapper.insert(flow);

        }

    }


    /**
     * Tool:3.(installScheduleObject)设置拟定调研安排对象数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 15:08
     * @updateTime 2021/4/19 15:08
     */
    public List<TResearchSchedule> installScheduleObject(RelationProResearchSchedule proResearchSchedule){
        List<TResearchSchedule> researchSchedules=proResearchSchedule.getResearchSchedules();
        //设置组员数据和idR
        for(TResearchSchedule schedule:researchSchedules){
            //idR
            schedule.setIdR(proResearchSchedule.getIdR());

            //组员
            //前端传递过来的员工id集合
            List<String> memeberIdList=schedule.getMemeberIds();
            //前端传递过来的员工姓名
            String memberNames=schedule.getMemberNames();
            //将前端传递过来的员工姓名字符串转换成集合
            List<String> memeberNameList=Arrays.asList(memberNames.split(Pattern.quote("|")));
            String groupMembers=null;
            for(int i=0;i<memeberIdList.size();i++){
                groupMembers+=memeberIdList.get(i)+","+memeberNameList.get(i)+"|";
            }

        }
        return researchSchedules;
    }





}
