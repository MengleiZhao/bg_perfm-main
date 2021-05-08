package com.common.business.planmgr.draftcheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.draftcheck.entity.BussinessFlowIndexWorkingManuscriptDesign;
import com.common.business.planmgr.draftcheck.entity.DesigneeRecIndexWorkingManuscriptDesign;
import com.common.business.planmgr.draftcheck.entity.TIndexWorkingManuscriptDesignCheckRec;
import com.common.business.planmgr.draftcheck.mapper.BussinessFlowIndexWorkingManuscriptDesignMapper;
import com.common.business.planmgr.draftcheck.mapper.DesigneeRecIndexWorkingManuscriptDesignMapper;
import com.common.business.planmgr.draftcheck.mapper.TIndexWorkingManuscriptDesignCheckRecMapper;
import com.common.business.planmgr.draftcheck.service.BussinessFlowIndexWorkingManuscriptDesignService;
import com.common.business.planmgr.indexcheck.entity.BussinessFlowProIndex;
import com.common.business.planmgr.indexcheck.entity.DesigneeRecProIndex;
import com.common.business.planmgr.indexcheck.entity.RelationProIndex;
import com.common.business.planmgr.indexcheck.entity.TIndexSystemDseignCheckRec;
import com.common.business.planmgr.indexdraftdesign.entity.RelationIndexWorkingManuscriptDesign;
import com.common.business.planmgr.indexdraftdesign.mapper.RelationIndexWorkingManuscriptDesignMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 业务流程表（三级指标与指标底稿审批） 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-30
 */
@Service
@Transactional
public class BussinessFlowIndexWorkingManuscriptDesignServiceImpl extends ServiceImpl<BussinessFlowIndexWorkingManuscriptDesignMapper, BussinessFlowIndexWorkingManuscriptDesign> implements BussinessFlowIndexWorkingManuscriptDesignService {
    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TPerformanceWorkingGroupService tPerformanceWorkingGroupService;
    @Autowired
    private BussinessFlowIndexWorkingManuscriptDesignMapper bussinessFlowIndexWorkingManuscriptDesignMapper;
    @Autowired
    private TIndexWorkingManuscriptDesignCheckRecMapper tIndexWorkingManuscriptDesignCheckRecMapper;
    @Autowired
    private RelationIndexWorkingManuscriptDesignMapper relationIndexWorkingManuscriptDesignMapper;
    @Autowired
    DesigneeRecIndexWorkingManuscriptDesignMapper designeeRecIndexWorkingManuscriptDesignMapper;

    /**
     * 底稿设计审核列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> manuscriptDesignCheckPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception{
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //查询已经立项的项目
        List<TProPerformanceInfo> list= tProPerformanceInfoMapper.manuscriptDesignCheckPage(bean,search,user.getId()+"");
        return new PageInfo<TProPerformanceInfo>(list);
    }

    /**
     * @Description: 设置审批流
     * @Author: 安达
     * @Date: 2021/3/29 20:57
     * @Param:
     * @Return:
     */
    @Override
    public void saveCheckFlowUser(Integer idA, Integer idR, ShiroUser user) throws Exception {
        List<BussinessFlowProIndex> bussinessFlowProIndexList=new ArrayList<>();
        //发起人
        saveCheckUser(idR,user,user.getId().toString(),1,"1");
        //项目外勤主管
        String fieldSupervisorId=tPerformanceWorkingGroupService.getFieldSupervisorIdByIdA(idA);
        saveCheckUser(idR,user,fieldSupervisorId,2,"0");
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
        BussinessFlowIndexWorkingManuscriptDesign bussinessFlowProIndex = new BussinessFlowIndexWorkingManuscriptDesign();
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
        bussinessFlowIndexWorkingManuscriptDesignMapper.insert(bussinessFlowProIndex);
    }

    /**
     * @Description: 根据idR查询对应的工作流集合
     * @Author: 安达
     * @Date: 2021/3/30 14:37
     * @Param:
     * @Return:
     */
    private List<BussinessFlowIndexWorkingManuscriptDesign> findWorkFlowListByIdr(Integer idR){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        List<BussinessFlowIndexWorkingManuscriptDesign> workFlowList = bussinessFlowIndexWorkingManuscriptDesignMapper.selectList(entityWrapper);
        return workFlowList;
    }
    /**
     * @Description: 根据idR和编号查询获取当前节点
     * @Author: 安达
     * @Date: 2021/3/30 14:37
     * @Param:
     * @Return:
     */
    private  BussinessFlowIndexWorkingManuscriptDesign  getWorkFlowByIdrAndNo(Integer idR,Integer orderOfCurrentNode){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        //审批顺序
        entityWrapper.eq("ORDER_OF_CURRENT_NODE",orderOfCurrentNode);
        List<BussinessFlowIndexWorkingManuscriptDesign> workFlowList = bussinessFlowIndexWorkingManuscriptDesignMapper.selectList(entityWrapper);
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
    private  BussinessFlowIndexWorkingManuscriptDesign  getActiveWorkFlowByIdR(Integer idR){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        //审批顺序
        entityWrapper.eq("NODE_IS_ACTIVE","1");
        List<BussinessFlowIndexWorkingManuscriptDesign> workFlowList = bussinessFlowIndexWorkingManuscriptDesignMapper.selectList(entityWrapper);
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
    public Boolean checkBussinessFlow(TIndexWorkingManuscriptDesignCheckRec checkRec, ShiroUser user) throws Exception {
        //三级指标与指标底稿关系对象
        RelationIndexWorkingManuscriptDesign relationProIndex = relationIndexWorkingManuscriptDesignMapper.selectById(checkRec.getIdR());
        //获取当前活跃节点
        BussinessFlowIndexWorkingManuscriptDesign activeWorkFlow = getActiveWorkFlowByIdR(checkRec.getIdR());
        //是否活跃 当前节点设置为不活跃
        activeWorkFlow.setNodeIsActive("0");
        bussinessFlowIndexWorkingManuscriptDesignMapper.updateById(activeWorkFlow);
        BussinessFlowIndexWorkingManuscriptDesign nextFlowProIndex =null;
        //审批通过
        if ("1".equals(checkRec.getCheckResult())) {
            //获取下级审批节点
            nextFlowProIndex = getWorkFlowByIdrAndNo(checkRec.getIdR(), checkRec.getOrderOfCurrentNode() + 1);
        }else{
            //审批不通过
            //获取第一个审批节点
            nextFlowProIndex = getWorkFlowByIdrAndNo(checkRec.getIdR(),  1);
            //版本状态:  -1-退回  0-暂存  1-审批中  2-已完成
            relationProIndex.setCreateStauts("-1");
        }
        //如果不为空，说明还不是最后一个节点
        if (nextFlowProIndex != null) {
            //是否活跃 下级审批节点设置为活跃
            nextFlowProIndex.setNodeIsActive("1");
            bussinessFlowIndexWorkingManuscriptDesignMapper.updateById(nextFlowProIndex);
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
            relationIndexWorkingManuscriptDesignMapper.updateById(relationProIndex);
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
            relationIndexWorkingManuscriptDesignMapper.updateById(relationProIndex);
        }
         /*
            添加审批记录开始
         */
        //判断是不是被转派人审批，如果是需要在备注里添加信息
        if (activeWorkFlow.getDesigneeId() != null && activeWorkFlow.getDesigneeId().equals(user.getId())) {
            //设置审批记录的备注
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(new Date());
            checkRec.setRemark(str + "时间 由 " + activeWorkFlow.getCheckUser() + " 转 " +
                    user.getName() + " 承办”");
            checkRec.setOrderOfCurrentNode(activeWorkFlow.getOrderOfCurrentNode());
        }
        //审批人ID
        checkRec.setCheckUserId(user.getId().toString());
        //审批人
        checkRec.setCheckUser(user.getName());
        //审批时间
        checkRec.setCheckTime(new Date());
        //审批数据状态
        checkRec.setCheckDataStatus("1");
        //当前节点审批顺序
        checkRec.setOrderOfCurrentNode(activeWorkFlow.getOrderOfCurrentNode());
        tIndexWorkingManuscriptDesignCheckRecMapper.insert(checkRec);
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
    public boolean designeeCheckUser(Integer idB,String designeeId)throws Exception {
        //三级指标与指标底稿关系对象
        RelationIndexWorkingManuscriptDesign relationProIndex = relationIndexWorkingManuscriptDesignMapper.selectById(idB);
        //获取当前活跃节点
        BussinessFlowIndexWorkingManuscriptDesign activeWorkFlow = getActiveWorkFlowByIdR(relationProIndex.getIdR());
        //获取指派审批人信息
        RcUser user=rcUserMapper.selectById(designeeId);
        //被转派人ID
        activeWorkFlow.setDesigneeId(designeeId);
        //被转派人姓名
        activeWorkFlow.setDesigneeName(user.getName());
        bussinessFlowIndexWorkingManuscriptDesignMapper.updateById(activeWorkFlow);
        //当前审批人ID
        relationProIndex.setCurrCheckId(designeeId);
        //当前审批人姓名
        relationProIndex.setCurrCheckName(user.getName());
        relationIndexWorkingManuscriptDesignMapper.updateById(relationProIndex);
        //添加转派记录
        DesigneeRecIndexWorkingManuscriptDesign designeeHistory = new DesigneeRecIndexWorkingManuscriptDesign();
        designeeHistory.setIdR(idB);
        //指派时间
        designeeHistory.setDesignee(new Date());
        //原审批人ID
        designeeHistory.setCheckUserId(activeWorkFlow.getCheckUserId());
        //原审批人姓名
        designeeHistory.setCheckUser(activeWorkFlow.getCheckUser());
        //被转派人ID
        designeeHistory.setDesigneeId(activeWorkFlow.getDesigneeId());
        //被转派人姓名
        designeeHistory.setDesigneeName(activeWorkFlow.getDesigneeName());
        //当前节点审批顺序
        designeeHistory.setOrderOfCurrentNode(activeWorkFlow.getOrderOfCurrentNode());
        designeeRecIndexWorkingManuscriptDesignMapper.insert(designeeHistory);
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
    public List<TIndexWorkingManuscriptDesignCheckRec>  findCheckRecServiceList(Integer idR)throws Exception{
        List<TIndexWorkingManuscriptDesignCheckRec> list = findCheckRecServiceList(idR,null);
        return list;
    }
    //根据条件查询审批记录
    public List<TIndexWorkingManuscriptDesignCheckRec>  findCheckRecServiceList(Integer idR,String checkDataStatus)throws Exception{
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        if(StringUtils.isNotEmpty(checkDataStatus)){
            //审批数据状态  0-历史数据  1-本次数据
            entityWrapper.eq("CHECK_DATA_STATUS",checkDataStatus);
        }
        List<TIndexWorkingManuscriptDesignCheckRec> list = tIndexWorkingManuscriptDesignCheckRecMapper.selectList(entityWrapper);
        return list;
    }
    //查询本次数据，map<顺序--bean >
    public Map<Integer,TIndexWorkingManuscriptDesignCheckRec> findCheckRecServiceMap(Integer idR)throws Exception{
        Map<Integer,TIndexWorkingManuscriptDesignCheckRec> map=new HashMap<>();
        List<TIndexWorkingManuscriptDesignCheckRec> list = findCheckRecServiceList(idR,"1");
        for(TIndexWorkingManuscriptDesignCheckRec checkRec:list){
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
    public List<BussinessFlowIndexWorkingManuscriptDesign>  findCheckNodes(Integer idR)throws Exception{
        Map<Integer,TIndexWorkingManuscriptDesignCheckRec>  checkRecServiceMap=findCheckRecServiceMap(idR);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        List<BussinessFlowIndexWorkingManuscriptDesign> list = bussinessFlowIndexWorkingManuscriptDesignMapper.selectList(entityWrapper);
        for(BussinessFlowIndexWorkingManuscriptDesign flow:list){
            TIndexWorkingManuscriptDesignCheckRec checkRec=checkRecServiceMap.get(flow.getOrderOfCurrentNode());
            flow.setIndexWorkingManuscriptDesignCheckRec(checkRec);
        }
        return list;
    }
}
