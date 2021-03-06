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
 * ?????????????????????????????????????????????????????? ???????????????
 * </p>
 *
 * @author ??????
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
     * ???????????????????????????
     * @author:??????
     * @date:2021???3???9??? 9???51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> manuscriptDesignCheckPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception{
        //????????????
        PageHelper.startPage(pageNum, pageSize);
        //???????????????????????????
        List<TProPerformanceInfo> list= tProPerformanceInfoMapper.manuscriptDesignCheckPage(bean,search,user.getId()+"");
        return new PageInfo<TProPerformanceInfo>(list);
    }

    /**
     * @Description: ???????????????
     * @Author: ??????
     * @Date: 2021/3/29 20:57
     * @Param:
     * @Return:
     */
    @Override
    public void saveCheckFlowUser(Integer idA, Integer idR, ShiroUser user) throws Exception {
        List<BussinessFlowProIndex> bussinessFlowProIndexList=new ArrayList<>();
        //?????????
        saveCheckUser(idR,user,user.getId().toString(),1,"1");
        //??????????????????
        String fieldSupervisorId=tPerformanceWorkingGroupService.getFieldSupervisorIdByIdA(idA);
        saveCheckUser(idR,user,fieldSupervisorId,2,"0");
        //??????????????????
        TProPerformanceInfo pro = tProPerformanceInfoMapper.selectById(idA);
        //????????????
        String proManagerId=pro.getProManagerId();
        saveCheckUser(idR,user,fieldSupervisorId,3,"0");
    }

    /**
     * @Description: ?????????????????????
     * @Author: ??????
     * @Date: 2021/3/29 20:57
     * @Param: nowUser  ?????????
     * @Param: checkUserId ?????????id
     * @Param: orderOfCurrentNode ????????????????????????
     * @Param: nodeIsActive ???????????? 0:?????????   1?????????
     * @Return:
     */
    public void saveCheckUser(Integer idR,ShiroUser nowUser,String checkUserId,Integer orderOfCurrentNode,String nodeIsActive)throws Exception {
        //?????????????????????
        RcUser user=rcUserMapper.selectById(checkUserId);
        BussinessFlowIndexWorkingManuscriptDesign bussinessFlowProIndex = new BussinessFlowIndexWorkingManuscriptDesign();
        bussinessFlowProIndex.setIdR(idR);
        //?????????ID
        bussinessFlowProIndex.setCheckUserId(user.getId().toString());
        //???????????????
        bussinessFlowProIndex.setCheckUser(user.getName());
        //???????????????ID
        bussinessFlowProIndex.setCheckUserDeptId(user.getDeptId().toString());
        //?????????????????????
        bussinessFlowProIndex.setCheckUserDeptName(user.getDeptName());
        //????????????????????????
        bussinessFlowProIndex.setOrderOfCurrentNode(orderOfCurrentNode);
        //???????????? 0:?????????   1?????????
        bussinessFlowProIndex.setNodeIsActive(nodeIsActive);
        //?????????
        bussinessFlowProIndex.setCreateor(nowUser.getName());
        //????????????
        bussinessFlowProIndex.setCreateTime(new Date());
        //?????????
        bussinessFlowProIndex.setUpdateor(nowUser.getName());
        //????????????
        bussinessFlowProIndex.setUpdateTime(new Date());
        bussinessFlowIndexWorkingManuscriptDesignMapper.insert(bussinessFlowProIndex);
    }

    /**
     * @Description: ??????idR??????????????????????????????
     * @Author: ??????
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
     * @Description: ??????idR?????????????????????????????????
     * @Author: ??????
     * @Date: 2021/3/30 14:37
     * @Param:
     * @Return:
     */
    private  BussinessFlowIndexWorkingManuscriptDesign  getWorkFlowByIdrAndNo(Integer idR,Integer orderOfCurrentNode){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        //????????????
        entityWrapper.eq("ORDER_OF_CURRENT_NODE",orderOfCurrentNode);
        List<BussinessFlowIndexWorkingManuscriptDesign> workFlowList = bussinessFlowIndexWorkingManuscriptDesignMapper.selectList(entityWrapper);
        if(workFlowList !=null && workFlowList.size()>0){
            return workFlowList.get(0);
        }
        return null;
    }
    /**
     * @Description: ??????idR????????????????????????
     * @Author: ??????
     * @Date: 2021/3/30 14:37
     * @Param:
     * @Return:
     */
    private  BussinessFlowIndexWorkingManuscriptDesign  getActiveWorkFlowByIdR(Integer idR){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        //????????????
        entityWrapper.eq("NODE_IS_ACTIVE","1");
        List<BussinessFlowIndexWorkingManuscriptDesign> workFlowList = bussinessFlowIndexWorkingManuscriptDesignMapper.selectList(entityWrapper);
        if(workFlowList !=null && workFlowList.size()>0){
            return workFlowList.get(0);
        }
        return null;
    }
    /**
     * @Description: ??????
     * @Author: ??????
     * @Date: 2021/3/31 10:58
     * @Param:
     * @Return:
     */
    @Override
    public Boolean checkBussinessFlow(TIndexWorkingManuscriptDesignCheckRec checkRec, ShiroUser user) throws Exception {
        //???????????????????????????????????????
        RelationIndexWorkingManuscriptDesign relationProIndex = relationIndexWorkingManuscriptDesignMapper.selectById(checkRec.getIdR());
        //????????????????????????
        BussinessFlowIndexWorkingManuscriptDesign activeWorkFlow = getActiveWorkFlowByIdR(checkRec.getIdR());
        //???????????? ??????????????????????????????
        activeWorkFlow.setNodeIsActive("0");
        bussinessFlowIndexWorkingManuscriptDesignMapper.updateById(activeWorkFlow);
        BussinessFlowIndexWorkingManuscriptDesign nextFlowProIndex =null;
        //????????????
        if ("1".equals(checkRec.getCheckResult())) {
            //????????????????????????
            nextFlowProIndex = getWorkFlowByIdrAndNo(checkRec.getIdR(), checkRec.getOrderOfCurrentNode() + 1);
        }else{
            //???????????????
            //???????????????????????????
            nextFlowProIndex = getWorkFlowByIdrAndNo(checkRec.getIdR(),  1);
            //????????????:  -1-??????  0-??????  1-?????????  2-?????????
            relationProIndex.setCreateStauts("-1");
        }
        //???????????????????????????????????????????????????
        if (nextFlowProIndex != null) {
            //???????????? ?????????????????????????????????
            nextFlowProIndex.setNodeIsActive("1");
            bussinessFlowIndexWorkingManuscriptDesignMapper.updateById(nextFlowProIndex);
            /*
               ??????????????????????????????
             */
            //?????????ID
            String currCheckId = nextFlowProIndex.getCheckUserId();
            //???????????????
            String currCheckName = nextFlowProIndex.getCheckUser();
            //????????????ID
            String designeeId = nextFlowProIndex.getDesigneeId();
            //??????????????????
            String designeeName = nextFlowProIndex.getDesigneeName();
            //?????? ????????????ID ???????????????????????????????????????
            if (designeeId != null) {
                currCheckId = designeeId;
                currCheckName = designeeName;
            }
            //???????????????ID
            relationProIndex.setCurrCheckId(currCheckId);
            //?????????????????????
            relationProIndex.setCurrCheckName(currCheckName);
            relationIndexWorkingManuscriptDesignMapper.updateById(relationProIndex);
             /*
               ??????????????????????????????
             */
        } else {
            //?????????????????????????????????
            //???????????????ID
            relationProIndex.setCurrCheckId(null);
            //?????????????????????
            relationProIndex.setCurrCheckName(null);
            //????????????:  -1-??????  0-??????  1-?????????  2-?????????
            relationProIndex.setCreateStauts("2");
            relationIndexWorkingManuscriptDesignMapper.updateById(relationProIndex);
        }
         /*
            ????????????????????????
         */
        //???????????????????????????????????????????????????????????????????????????
        if (activeWorkFlow.getDesigneeId() != null && activeWorkFlow.getDesigneeId().equals(user.getId())) {
            //???????????????????????????
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(new Date());
            checkRec.setRemark(str + "?????? ??? " + activeWorkFlow.getCheckUser() + " ??? " +
                    user.getName() + " ?????????");
            checkRec.setOrderOfCurrentNode(activeWorkFlow.getOrderOfCurrentNode());
        }
        //?????????ID
        checkRec.setCheckUserId(user.getId().toString());
        //?????????
        checkRec.setCheckUser(user.getName());
        //????????????
        checkRec.setCheckTime(new Date());
        //??????????????????
        checkRec.setCheckDataStatus("1");
        //????????????????????????
        checkRec.setOrderOfCurrentNode(activeWorkFlow.getOrderOfCurrentNode());
        tIndexWorkingManuscriptDesignCheckRecMapper.insert(checkRec);
        /*
            ????????????????????????
         */
        return true;
    }

    /**
     * @Description: ??????????????????????????????
     * @Author: ??????
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    @Override
    public boolean designeeCheckUser(Integer idB,String designeeId)throws Exception {
        //???????????????????????????????????????
        RelationIndexWorkingManuscriptDesign relationProIndex = relationIndexWorkingManuscriptDesignMapper.selectById(idB);
        //????????????????????????
        BussinessFlowIndexWorkingManuscriptDesign activeWorkFlow = getActiveWorkFlowByIdR(relationProIndex.getIdR());
        //???????????????????????????
        RcUser user=rcUserMapper.selectById(designeeId);
        //????????????ID
        activeWorkFlow.setDesigneeId(designeeId);
        //??????????????????
        activeWorkFlow.setDesigneeName(user.getName());
        bussinessFlowIndexWorkingManuscriptDesignMapper.updateById(activeWorkFlow);
        //???????????????ID
        relationProIndex.setCurrCheckId(designeeId);
        //?????????????????????
        relationProIndex.setCurrCheckName(user.getName());
        relationIndexWorkingManuscriptDesignMapper.updateById(relationProIndex);
        //??????????????????
        DesigneeRecIndexWorkingManuscriptDesign designeeHistory = new DesigneeRecIndexWorkingManuscriptDesign();
        designeeHistory.setIdR(idB);
        //????????????
        designeeHistory.setDesignee(new Date());
        //????????????ID
        designeeHistory.setCheckUserId(activeWorkFlow.getCheckUserId());
        //??????????????????
        designeeHistory.setCheckUser(activeWorkFlow.getCheckUser());
        //????????????ID
        designeeHistory.setDesigneeId(activeWorkFlow.getDesigneeId());
        //??????????????????
        designeeHistory.setDesigneeName(activeWorkFlow.getDesigneeName());
        //????????????????????????
        designeeHistory.setOrderOfCurrentNode(activeWorkFlow.getOrderOfCurrentNode());
        designeeRecIndexWorkingManuscriptDesignMapper.insert(designeeHistory);
        return true;
    }

    /**
     * @Description: ??????????????????
     * @Author: ??????
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    @Override
    public List<TIndexWorkingManuscriptDesignCheckRec>  findCheckRecServiceList(Integer idR)throws Exception{
        List<TIndexWorkingManuscriptDesignCheckRec> list = findCheckRecServiceList(idR,null);
        return list;
    }
    //??????????????????????????????
    public List<TIndexWorkingManuscriptDesignCheckRec>  findCheckRecServiceList(Integer idR,String checkDataStatus)throws Exception{
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",idR);
        if(StringUtils.isNotEmpty(checkDataStatus)){
            //??????????????????  0-????????????  1-????????????
            entityWrapper.eq("CHECK_DATA_STATUS",checkDataStatus);
        }
        List<TIndexWorkingManuscriptDesignCheckRec> list = tIndexWorkingManuscriptDesignCheckRecMapper.selectList(entityWrapper);
        return list;
    }
    //?????????????????????map<??????--bean >
    public Map<Integer,TIndexWorkingManuscriptDesignCheckRec> findCheckRecServiceMap(Integer idR)throws Exception{
        Map<Integer,TIndexWorkingManuscriptDesignCheckRec> map=new HashMap<>();
        List<TIndexWorkingManuscriptDesignCheckRec> list = findCheckRecServiceList(idR,"1");
        for(TIndexWorkingManuscriptDesignCheckRec checkRec:list){
            map.put(checkRec.getOrderOfCurrentNode(),checkRec);
        }
        return map;
    }
    /**
     * @Description: ????????????????????????
     * @Author: ??????
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
