package com.common.business.planmgr.indexcheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.indexcheck.entity.BussinessFlowProIndex;
import com.common.business.planmgr.indexcheck.entity.DesigneeRecProIndex;
import com.common.business.planmgr.indexcheck.entity.RelationProIndex;
import com.common.business.planmgr.indexcheck.entity.TIndexSystemDseignCheckRec;
import com.common.business.planmgr.indexcheck.mapper.BussinessFlowProIndexMapper;
import com.common.business.planmgr.indexcheck.mapper.DesigneeRecProIndexMapper;
import com.common.business.planmgr.indexcheck.mapper.RelationProIndexMapper;
import com.common.business.planmgr.indexcheck.mapper.TIndexSystemDseignCheckRecMapper;
import com.common.business.planmgr.indexcheck.service.RelationProIndexService;
import com.common.business.planmgr.indexdesign.entity.*;
import com.common.business.planmgr.indexdesign.mapper.*;
import com.common.business.planmgr.indexdesign.service.TIndexSystemDseignService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TEvalUnitInfoMapper;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 项目指标体系关系表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
@Service
@Transactional
public class RelationProIndexServiceImpl extends ServiceImpl<RelationProIndexMapper, RelationProIndex> implements RelationProIndexService {
    @Autowired
    RelationProIndexMapper relationProIndexMapper;
    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TPerformanceWorkingGroupService tPerformanceWorkingGroupService;
    @Autowired
    private BussinessFlowProIndexMapper bussinessFlowProIndexMapper;
    @Autowired
    private TIndexSystemDseignCheckRecMapper tIndexSystemDseignCheckRecMapper;
    @Autowired
    private DesigneeRecProIndexMapper designeeRecProIndexMapper;
    @Autowired
    private TIndexSystemDseignMapper tIndexSystemDseignMapper;
    @Autowired
    private TAssessmentObjectByIndexMapper tAssessmentObjectByIndexMapper;
    @Autowired
    private TEvalUnitInfoMapper tEvalUnitInfoMapper;
    @Autowired
    private TAssessmentObjectByPointsMapper tAssessmentObjectByPointsMapper;
    @Autowired
    private TScoringStandardsAndRuilesDetailMapper tScoringStandardsAndRuilesDetailMapper;
    @Autowired
    private TEvidencePoolsMapper tEvidencePoolsMapper;
    @Autowired
    private TIndexScoringPointsMapper tIndexScoringPointsMapper;

    /**
     * 指标体系设计列表——所有版本
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> allRelationProIndexPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        List<TProPerformanceInfo> list=tProPerformanceInfoMapper.allRelationProIndexPage(bean,search,user.getId()+"");
        if(list !=null && list.size()>0){
            //创建项目id集合
            List<Integer> idAList=new ArrayList<>();
            for(TProPerformanceInfo info:list){
                idAList.add(info.getIdA());
            }
            EntityWrapper groupEntity=new EntityWrapper();
            groupEntity.in("ID_A",idAList);
            groupEntity.eq("IS_WORK_CHARGE","Y");
            //获取工作组集合
            List<TPerformanceWorkingGroup> groupList=tPerformanceWorkingGroupService.selectList(groupEntity);
            Map<Integer, TPerformanceWorkingGroup> groupMap=getgroupMapByList(groupList);

            for(TProPerformanceInfo info:list){
                TPerformanceWorkingGroup group=groupMap.get(info.getIdA());
                //项目外勤主管组员id
                info.setGroupMemberId(group.getGroupMemberId());
                //项目外勤主管编号
                info.setGroupMemberCode(group.getGroupMemberCode());
                //项目外勤主管姓名
                info.setGroupMemberName(group.getGroupMemberName());
            }
        }
        return new PageInfo<TProPerformanceInfo>(list);
    }

    /**
     * 指标体系设计列表(待拟定的项目)
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> relationProIndexPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        List<TProPerformanceInfo> list=tProPerformanceInfoMapper.relationProIndexPage(bean,search,user.getId()+"");
        if(list !=null && list.size()>0){
            //创建项目id集合
            List<Integer> idAList=new ArrayList<>();
            for(TProPerformanceInfo info:list){
                idAList.add(info.getIdA());
            }
            EntityWrapper groupEntity=new EntityWrapper();
            groupEntity.in("ID_A",idAList);
            groupEntity.eq("IS_WORK_CHARGE","Y");
            //获取工作组集合
            List<TPerformanceWorkingGroup> groupList=tPerformanceWorkingGroupService.selectList(groupEntity);
            Map<Integer, TPerformanceWorkingGroup> groupMap=getgroupMapByList(groupList);

            for(TProPerformanceInfo info:list){
                TPerformanceWorkingGroup group=groupMap.get(info.getIdA());
                //项目外勤主管组员id
                info.setGroupMemberId(group.getGroupMemberId());
                //项目外勤主管编号
                info.setGroupMemberCode(group.getGroupMemberCode());
                //项目外勤主管姓名
                info.setGroupMemberName(group.getGroupMemberName());
            }
        }
        return new PageInfo<TProPerformanceInfo>(list);
    }
    /**
     * 指标体系审核列表(设计了，轮到我审批的项目)
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> relationProIndexCheckPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //指标体系审核列表(设计了，轮到我审批的项目)
        List<TProPerformanceInfo> list= tProPerformanceInfoMapper.relationProIndexCheckPage(bean,search,user.getId()+"");

        if(list !=null && list.size()>0){
            //创建项目id集合
            List<Integer> idAList=new ArrayList<>();
            for(TProPerformanceInfo info:list){
                idAList.add(info.getIdA());
            }
            EntityWrapper groupEntity=new EntityWrapper();
            groupEntity.in("ID_A",idAList);
            groupEntity.eq("IS_WORK_CHARGE","Y");
            //获取工作组集合
            List<TPerformanceWorkingGroup> groupList=tPerformanceWorkingGroupService.selectList(groupEntity);
            Map<Integer, TPerformanceWorkingGroup> groupMap=getgroupMapByList(groupList);

            for(TProPerformanceInfo info:list){
                TPerformanceWorkingGroup group=groupMap.get(info.getIdA());
                //项目外勤主管组员id
                info.setGroupMemberId(group.getGroupMemberId());
                //项目外勤主管编号
                info.setGroupMemberCode(group.getGroupMemberCode());
                //项目外勤主管姓名
                info.setGroupMemberName(group.getGroupMemberName());
            }
        }
        return new PageInfo<TProPerformanceInfo>(list);
    }

    /**
     * 根据集合获取 idR-创建时间 groupmap
     * @param groupList
     * @return
     */
    private Map<Integer, TPerformanceWorkingGroup> getgroupMapByList(List<TPerformanceWorkingGroup> groupList){
        Map<Integer, TPerformanceWorkingGroup> map=new HashMap<>();
        for(TPerformanceWorkingGroup group:groupList){
            map.put(group.getIdA(),group);
        }
        return map;
    }


    /**
     * 根据项目idA项目信息
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @return
     * @throws Exception
     */
    @Override
    public TProPerformanceInfo getTProPerformanceInfoByIdA(Integer idA) throws Exception {
        TProPerformanceInfo proPerformanceInfo= tProPerformanceInfoMapper.selectById(idA);
        //根据 idA 查询外勤主管工作组
        TPerformanceWorkingGroup group=tPerformanceWorkingGroupService.getFieldSupervisorByIdA(idA);
        //项目外勤主管姓名
        proPerformanceInfo.setGroupMemberName(group.getGroupMemberName());
        return proPerformanceInfo;
    }

    /**
     * @Description: 设置审批流
     * @Author: 安达
     * @Date: 2021/3/29 20:57
     * @Param:
     * @Return:
     */
    @Override
    public void saveCheckFlowUser(Integer idA,Integer idR,ShiroUser user) throws Exception {
        List<BussinessFlowProIndex> bussinessFlowProIndexList=new ArrayList<>();
        //发起人
        saveCheckUser(idR,user,user.getId().toString(),1,"0");
        //项目外勤主管
        String fieldSupervisorId=tPerformanceWorkingGroupService.getFieldSupervisorIdByIdA(idA);
        saveCheckUser(idR,user,fieldSupervisorId,2,"1");
        //获取关联项目
        TProPerformanceInfo pro = tProPerformanceInfoMapper.selectById(idA);
        //项目经理
        String proManagerId=pro.getProManagerId();
        saveCheckUser(idR,user,fieldSupervisorId,3,"0");
    }

    /**
     * @Description: 设置审批人流程
     * @Author: 安达
     * @Date: 2021/3/29 20:57
     * @Param: nowUser  创建人
     * @Param: checkUserId 审批人id
     * @Param: orderOfCurrentNode 当前节点审批顺序
     * @Param: nodeIsActive 是否活跃 0:不活跃   1：活跃
     * @Return:
     */
    public void saveCheckUser(Integer idR,ShiroUser nowUser,String checkUserId,Integer orderOfCurrentNode,String nodeIsActive)throws Exception {
        //获取审批人信息
        RcUser user=rcUserMapper.selectById(checkUserId);
        BussinessFlowProIndex bussinessFlowProIndex = new BussinessFlowProIndex();
        bussinessFlowProIndex.setIdR(idR);
        //审批人ID
        bussinessFlowProIndex.setCheckUserId(user.getId().toString());
        //审批人姓名
        bussinessFlowProIndex.setCheckUser(user.getName());
        //审批人部门ID
        bussinessFlowProIndex.setCheckUserDeptId(user.getDeptId().toString());
        //审批人部门名称
        bussinessFlowProIndex.setCheckUserDeptName(user.getDeptName());
        //当前节点审批顺序
        bussinessFlowProIndex.setOrderOfCurrentNode(orderOfCurrentNode);
        //是否活跃 0:不活跃   1：活跃
        bussinessFlowProIndex.setNodeIsActive(nodeIsActive);
        //创建人
        bussinessFlowProIndex.setCreateor(nowUser.getName());
        //创建时间
        bussinessFlowProIndex.setCreateTime(new Date());
        //修改人
        bussinessFlowProIndex.setUpdateor(nowUser.getName());
        //修改时间
        bussinessFlowProIndex.setUpdateTime(new Date());
        bussinessFlowProIndexMapper.insert(bussinessFlowProIndex);
    }
    /** 
     * @Description: 根据idR查询对应的工作流集合
     * @Author: 安达
     * @Date: 2021/3/30 14:37
     * @Param: 
     * @Return: 
     */
    private List<BussinessFlowProIndex> findWorkFlowListByIdr(Integer idR){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        List<BussinessFlowProIndex> workFlowList = bussinessFlowProIndexMapper.selectList(entityWrapper);
        return workFlowList;
    }
    /**
     * @Description: 根据idR和编号查询获取当前节点
     * @Author: 安达
     * @Date: 2021/3/30 14:37
     * @Param:
     * @Return:
     */
    private  BussinessFlowProIndex  getWorkFlowByIdrAndNo(Integer idR,Integer orderOfCurrentNode){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        //审批顺序
        entityWrapper.eq("ORDER_OF_CURRENT_NODE",orderOfCurrentNode);
        List<BussinessFlowProIndex> workFlowList = bussinessFlowProIndexMapper.selectList(entityWrapper);
        if(workFlowList !=null && workFlowList.size()>0){
            return workFlowList.get(0);
        }
        return null;
    }
    /**
     * @Description: 根据idR查询当前活跃节点
     * @Author: 安达
     * @Date: 2021/3/30 14:37
     * @Param:
     * @Return:
     */
    private  BussinessFlowProIndex  getActiveWorkFlowByIdr(Integer idR){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        //审批顺序
        entityWrapper.eq("NODE_IS_ACTIVE","1");
        List<BussinessFlowProIndex> workFlowList = bussinessFlowProIndexMapper.selectList(entityWrapper);
        if(workFlowList !=null && workFlowList.size()>0){
            return workFlowList.get(0);
        }
        return null;
    }
    /**
     * @Description: 审批 
     * @Author: 安达
     * @Date: 2021/3/31 10:58
     * @Param: 
     * @Return: 
     */
   @Override
    public Boolean checkRelationProIndex(TIndexSystemDseignCheckRec indexSystemDseignCheckRec, ShiroUser user) throws Exception {
        //项目指标体系关系对象
        RelationProIndex relationProIndex = relationProIndexMapper.selectById(indexSystemDseignCheckRec.getIdR());
        //获取当前活跃节点
        BussinessFlowProIndex nowFlowProIndex = getActiveWorkFlowByIdr(indexSystemDseignCheckRec.getIdR());
        //是否活跃 当前节点设置为不活跃
        nowFlowProIndex.setNodeIsActive("0");
        bussinessFlowProIndexMapper.updateById(nowFlowProIndex);
        BussinessFlowProIndex nextFlowProIndex =null;
        //审批通过
        if ("1".equals(indexSystemDseignCheckRec.getCheckResult())) {
            //获取下级审批节点
            nextFlowProIndex = getWorkFlowByIdrAndNo(indexSystemDseignCheckRec.getIdR(), indexSystemDseignCheckRec.getOrderOfCurrentNode() + 1);
        }else{
            //审批不通过
            //获取第一个审批节点
            nextFlowProIndex = getWorkFlowByIdrAndNo(indexSystemDseignCheckRec.getIdR(),  1);
            //版本状态:  -1-退回  0-暂存  1-审批中  2-已完成
            relationProIndex.setCreateStauts("-1");
        }
       //如果不为空，说明还不是最后一个节点
        if (nextFlowProIndex != null) {
            //是否活跃 下级审批节点设置为活跃
            nextFlowProIndex.setNodeIsActive("1");
            bussinessFlowProIndexMapper.updateById(nextFlowProIndex);
            /*
               设置体系关系对象开始
             */
            //审批人ID
            String currCheckId = nextFlowProIndex.getCheckUserId();
            //审批人姓名
            String currCheckName = nextFlowProIndex.getCheckUser();
            //被转派人ID
            String designeeId = nextFlowProIndex.getDesigneeId();
            //被转派人姓名
            String designeeName = nextFlowProIndex.getDesigneeName();
            //如果 被转派人ID 不为空，则审批人是被转派人
            if (designeeId != null) {
                currCheckId = designeeId;
                currCheckName = designeeName;
            }
            //当前审批人ID
            relationProIndex.setCurrCheckId(currCheckId);
            //当前审批人姓名
            relationProIndex.setCurrCheckName(currCheckName);
            relationProIndexMapper.updateById(relationProIndex);
             /*
               设置体系关系对象结束
             */
        } else {
            //如果是最后一级审批节点
            //当前审批人ID
            relationProIndex.setCurrCheckId(null);
            //当前审批人姓名
            relationProIndex.setCurrCheckName(null);
            //版本状态:  -1-退回  0-暂存  1-审批中  2-已完成
            relationProIndex.setCreateStauts("2");
            relationProIndexMapper.updateById(relationProIndex);
            //获得子项目
            TProPerformanceInfo proPerformanceInfo= tProPerformanceInfoMapper.selectById( relationProIndex.getIdA());
            //指标体系设计审核状态
            proPerformanceInfo.setIndexDseignStatus("1");
            tProPerformanceInfoMapper.updateById(proPerformanceInfo);
        }
         /*
            添加审批记录开始
         */
        //判断是不是被转派人审批，如果是需要在备注里添加信息
        if (nowFlowProIndex.getDesigneeId() != null && nowFlowProIndex.getDesigneeId().equals(user.getId())) {
            //设置审批记录的备注
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(new Date());
            indexSystemDseignCheckRec.setRemark(str + "时间 由 " + nowFlowProIndex.getCheckUser() + " 转 " +
                    user.getName() + " 承办”");
            indexSystemDseignCheckRec.setOrderOfCurrentNode(nowFlowProIndex.getOrderOfCurrentNode());
        }
        //审批人ID
        indexSystemDseignCheckRec.setCheckUserId(user.getId().toString());
        //审批人
        indexSystemDseignCheckRec.setCheckUser(user.getName());
        //审批时间
        indexSystemDseignCheckRec.setCheckTime(new Date());
        //审批数据状态
        indexSystemDseignCheckRec.setCheckDataStatus("1");
        //当前节点审批顺序
        indexSystemDseignCheckRec.setOrderOfCurrentNode(nowFlowProIndex.getOrderOfCurrentNode());
        tIndexSystemDseignCheckRecMapper.insert(indexSystemDseignCheckRec);
        /*
            添加审批记录结束
         */
        return true;
    }

    /**
     * @Description: 转派（指标体系审批）
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    @Override
    public boolean designeeCheckUser(Integer idR,String designeeId)throws Exception {
        //项目指标体系关系对象
        RelationProIndex relationProIndex = relationProIndexMapper.selectById(idR);
        //获取当前活跃节点
        BussinessFlowProIndex nowFlowProIndex = getActiveWorkFlowByIdr(idR);
        //获取指派审批人信息
        RcUser user=rcUserMapper.selectById(designeeId);
        //被转派人ID
        nowFlowProIndex.setDesigneeId(designeeId);
        //被转派人姓名
        nowFlowProIndex.setDesigneeName(user.getName());
        bussinessFlowProIndexMapper.updateById(nowFlowProIndex);
        //当前审批人ID
        relationProIndex.setCurrCheckId(designeeId);
        //当前审批人姓名
        relationProIndex.setCurrCheckName(user.getName());
        relationProIndexMapper.updateById(relationProIndex);
        //添加转派记录
        DesigneeRecProIndex designeeRecProIndex = new DesigneeRecProIndex();
        designeeRecProIndex.setIdR(idR);
        //指派时间
        designeeRecProIndex.setDesignee(new Date());
        //原审批人ID
        designeeRecProIndex.setCheckUserId(nowFlowProIndex.getCheckUserId());
        //原审批人姓名
        designeeRecProIndex.setCheckUser(nowFlowProIndex.getCheckUser());
        //被转派人ID
        designeeRecProIndex.setDesigneeId(nowFlowProIndex.getDesigneeId());
        //被转派人姓名
        designeeRecProIndex.setDesigneeName(nowFlowProIndex.getDesigneeName());
        //当前节点审批顺序
        designeeRecProIndex.setOrderOfCurrentNode(nowFlowProIndex.getOrderOfCurrentNode());
        designeeRecProIndexMapper.insert(designeeRecProIndex);
        return true;
    }

    /**
     * @Description: 审批记录信息
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    @Override
    public List<TIndexSystemDseignCheckRec>  findTIndexSystemDseignCheckRecServiceListByIdR(Integer idR)throws Exception{
        List<TIndexSystemDseignCheckRec> list = findTIndexSystemDseignCheckRecServiceList(idR,null);
        return list;
    }
    //根据条件查询审批记录
    public List<TIndexSystemDseignCheckRec>  findTIndexSystemDseignCheckRecServiceList(Integer idR,String checkDataStatus)throws Exception{
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        if(StringUtils.isNotEmpty(checkDataStatus)){
            //审批数据状态  0-历史数据  1-本次数据
            entityWrapper.eq("CHECK_DATA_STATUS",checkDataStatus);
        }
        List<TIndexSystemDseignCheckRec> list = tIndexSystemDseignCheckRecMapper.selectList(entityWrapper);
        return list;
    }
    //查询本次数据，map<顺序--bean >
    public Map<Integer,TIndexSystemDseignCheckRec>  findTIndexSystemDseignCheckRecServiceMapByIdR(Integer idR)throws Exception{
        Map<Integer,TIndexSystemDseignCheckRec> map=new HashMap<>();
        List<TIndexSystemDseignCheckRec> list = findTIndexSystemDseignCheckRecServiceList(idR,"1");
        for(TIndexSystemDseignCheckRec checkRec:list){
            map.put(checkRec.getOrderOfCurrentNode(),checkRec);
        }
        return map;
    }
    /**
     * @Description: 审批流程节点信息
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    @Override
    public List<BussinessFlowProIndex>  findTIndexSystemDseignCheckRecServiceNodesByIdR(Integer idR)throws Exception{
        Map<Integer,TIndexSystemDseignCheckRec>  indexSystemDseignCheckRecServiceMap=findTIndexSystemDseignCheckRecServiceMapByIdR(idR);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        //审批数据状态  0-历史数据  1-本次数据
        List<BussinessFlowProIndex> list = tIndexSystemDseignCheckRecMapper.selectList(entityWrapper);
        for(BussinessFlowProIndex flow:list){
            TIndexSystemDseignCheckRec indexSystemDseignCheckRec=indexSystemDseignCheckRecServiceMap.get(flow.getOrderOfCurrentNode());
            flow.setIndexSystemDseignCheckRec(indexSystemDseignCheckRec);
        }
        return list;
    }
    /**
     * @Description: 根据idA查找项目指标体系关系表 集合
     * @Author: 安达
     * @Date: 2021/3/26 10:37
     * @Param:
     * @Return:
     */
    public List<RelationProIndex> findRelationProIndexListByIdA(Integer idA) throws Exception {
        EntityWrapper<RelationProIndex> entity=new EntityWrapper<RelationProIndex>();
        entity.eq("ID_A",idA);
        entity.orderBy("CREATE_TIME",false);
        //根据idA查找项目指标体系关系表 集合
        List<RelationProIndex> list=relationProIndexMapper.selectList(entity);
        return list;

    }
    /**
     * @Description: 保存指标体系关系
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idA  子项目id
     * @Param stauts 版本状态
     * @param  indexSystemDseignList 指标对象集合
     * @Param user 当前登陆者
     * @Return:
     */
    @Override
    public void saveRelationProIndex(Integer idA, String stauts, List<TIndexSystemDseign> indexSystemDseignList, ShiroUser user) throws Exception {
        //根据idA查找项目指标体系关系表 集合
        List<RelationProIndex> rRelationProIndexList=findRelationProIndexListByIdA(idA);
        //定义版本号
        StringBuilder versionBuilder=new StringBuilder("V");
        int size;
        if(rRelationProIndexList !=null || rRelationProIndexList.size()>0) {
            String versionNo=rRelationProIndexList.get(0).getVersionNo();
            //获取最高的版本号
            size = Integer.parseInt(versionNo.substring(1))+1;
        }else {
            size = 1;
        }
        versionBuilder.append(size);
        for(TIndexSystemDseign systemDseign:indexSystemDseignList){
            //如果为空，就是新增
            if(systemDseign.getIdB()==null){
                //项目外勤主管
                String fieldSupervisorId=tPerformanceWorkingGroupService.getFieldSupervisorIdByIdA(idA);
                //获取外勤主管信息
                RcUser ieldSupervisorUser=rcUserMapper.selectById(fieldSupervisorId);
                //创建项目指标体系关系对象
                RelationProIndex insertBean=new RelationProIndex();
                insertBean.setIdA(idA);
                //版本号
                insertBean.setVersionNo(versionBuilder.toString());
                //指标底稿设计（申请）时间
                insertBean.setCreateTime(new Date());
                // 版本状态： -1：退货，0：暂存，1：审批中，2：已完成
                insertBean.setCreateStauts(stauts);
                //设计人
                insertBean.setCreateUaseName(systemDseign.getCreateUaseName());
                //设计人Id
                insertBean.setCreateUaseId(systemDseign.getCreateUaseId());
                //当前审批人ID
                insertBean.setCurrCheckId(ieldSupervisorUser.getId()+"");
                //当前审批人姓名
                insertBean.setCurrCheckName(ieldSupervisorUser.getName());
                //分值设置
                insertBean.setScoreType(systemDseign.getScoreType());
                //新增项目指标体系关系且返回主键
                relationProIndexMapper.insert(insertBean);
                //设置审批流
                saveCheckFlowUser(idA,insertBean.getIdR(),user);
                //设置外键
                systemDseign.setIdR(insertBean.getIdR());
                //新增指标体系设计且返回主键
                tIndexSystemDseignMapper.insert(systemDseign);
                int idB=systemDseign.getIdB();
                //把子表数据插入数据库
                insertSubTable(idB, systemDseign.getAssessmentObjectList(), systemDseign.getEvidencePoolsList(),systemDseign.getScoringPointsList());
            }else{
                //修改
                //修改指标体系设计
                tIndexSystemDseignMapper.updateById(systemDseign);
                int idB=systemDseign.getIdB();
                //把子表数据插入数据库
                insertSubTable(idB, systemDseign.getAssessmentObjectList(), systemDseign.getEvidencePoolsList(),systemDseign.getScoringPointsList());
            }
        }

    }
    /**
     * 把子表数据插入数据库
     * @param idB
     * @param assessmentObjectList
     * @param evidencePoolsList
     * @param scoringPointsList
     * @throws Exception
     */
    private void insertSubTable(Integer idB, List<TAssessmentObjectByIndex> assessmentObjectList, List<TEvidencePools> evidencePoolsList,
                                List<TIndexScoringPoints> scoringPointsList) throws Exception{
        //删除评价对象
        EntityWrapper tAssessmentObjectByIndexEntity=new EntityWrapper();
        tAssessmentObjectByIndexEntity.eq("ID_B",idB);
        tAssessmentObjectByIndexMapper.delete(tAssessmentObjectByIndexEntity);
        //保存评价对象
        for(TAssessmentObjectByIndex assessmentObject:assessmentObjectList){
            assessmentObject.setIdB(idB);
            assessmentObject.setIdC(null);
            tAssessmentObjectByIndexMapper.insert(assessmentObject);
        }
        //删除佐证材料
        EntityWrapper tEvidencePoolsEntity=new EntityWrapper();
        tEvidencePoolsEntity.eq("ID_B",idB);
        tEvidencePoolsMapper.delete(tEvidencePoolsEntity);
        //保存佐证材料
        for(TEvidencePools evidencePools:evidencePoolsList){
            evidencePools.setIdB(idB);
            evidencePools.setIdC(null);
            tEvidencePoolsMapper.insert(evidencePools);
        }
        for(TIndexScoringPoints scoringPoints:scoringPointsList){
            //删除评分标准及评分规则明细 集合
            EntityWrapper tScoringStandardsAndRuilesDetailEntity=new EntityWrapper();
            tScoringStandardsAndRuilesDetailEntity.eq("ID_C",scoringPoints.getIdC());
            tScoringStandardsAndRuilesDetailMapper.delete(tScoringStandardsAndRuilesDetailEntity);
            //删除评分要点考核对象 集合
            EntityWrapper tAssessmentObjectByPointsEntity=new EntityWrapper();
            tAssessmentObjectByPointsEntity.eq("ID_C",scoringPoints.getIdC());
            tAssessmentObjectByPointsMapper.delete(tAssessmentObjectByPointsEntity);
        }
        //删除评分要点
        EntityWrapper scoringPointsEntity=new EntityWrapper();
        scoringPointsEntity.eq("ID_B",idB);
        tIndexScoringPointsMapper.delete(scoringPointsEntity);
        //保存评分要点
        for(TIndexScoringPoints scoringPoints:scoringPointsList){
            scoringPoints.setIdB(idB);
            scoringPoints.setIdC(null);
            //新增评分要点且返回主键
            tIndexScoringPointsMapper.insert(scoringPoints);
            //评分标准及评分规则明细 集合
            List<TScoringStandardsAndRuilesDetail> scoringStandardsAndRuilesDetailList=scoringPoints.getScoringStandardsAndRuilesDetailList();
            for(TScoringStandardsAndRuilesDetail scoringStandardsAndRuilesDetail:scoringStandardsAndRuilesDetailList){
                scoringStandardsAndRuilesDetail.setIdC(scoringPoints.getIdC());
                scoringStandardsAndRuilesDetail.setIdE(null);
                //新增评分标准及评分规则明细
                tScoringStandardsAndRuilesDetailMapper.insert(scoringStandardsAndRuilesDetail);
            }
            //评分要点考核对象 集合
            List<TAssessmentObjectByPoints> assessmentObjectByPointsList=scoringPoints.getAssessmentObjectByPointsList();
            for(TAssessmentObjectByPoints assessmentObjectByPoints:assessmentObjectByPointsList){
                assessmentObjectByPoints.setIdC(scoringPoints.getIdC());
                assessmentObjectByPoints.setIdObj(null);
                //新增评分要点考核对象
                tAssessmentObjectByPointsMapper.insert(assessmentObjectByPoints);
            }
        }
    }
}
