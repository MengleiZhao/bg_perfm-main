package com.common.business.collection.meanslist.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.github.pagehelper.PageHelper;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.collection.meanslist.mapper.RelationProListMapper;
import com.common.business.collection.meanslist.mapper.TDevelopmentInformationListMapper;
import com.common.business.collection.meanslist.service.RelationProListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.common.business.collection.meanslistcheck.entity.BussinessFlowProList;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.common.business.collection.meanslistcheck.mapper.BussinessFlowProListMapper;
import com.common.business.collection.meanslistcheck.mapper.TDevelopmentInfoListCheckRecMapper;

import com.common.business.collection.meanslistcheck.service.BussinessFlowProListService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;

import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;

import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Service
@Transactional
public class RelationProListServiceImpl extends ServiceImpl<RelationProListMapper, RelationProList> implements RelationProListService {


    @Autowired
    private TDevelopmentInformationListMapper developmentInformationListMapper;
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;
    @Autowired
    private BussinessFlowProListMapper bussinessFlowProListMapper;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TDevelopmentInfoListCheckRecMapper developmentInfoListCheckRecMapper;
    @Lazy
    @Autowired
    private BussinessFlowProListService bussinessFlowProListService;
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;


    /**
     * 项目资料清单关系表,list页面查询项目
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @author: 陈睿超
     * @createDate: 2021/3/18 16:01
     * @updater: 陈睿超
     * @updateDate: 2021/3/18 16:01
     * @return
     */
    @Override
    public PageInfo<RelationProList> pagelist(Integer pageNum, Integer pageSize, String search, RelationProList bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProList> pagebean = baseMapper.findPageList(pageNum, pageSize, search, bean);
        PageInfo<RelationProList> pageInfo = new PageInfo<>(pagebean);

        return pageInfo;
    }

    /**
     * @Title:
     * @Description: 审批查询
     * @author: 陈睿超
     * @createDate: 2021/3/23 15:53
     * @updater: 陈睿超
     * @updateDate: 2021/3/23 15:53
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     **/
    @Override
    public PageInfo<RelationProList> checkPagelist(Integer pageNum, Integer pageSize, String search, RelationProList bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProList> pagebean = baseMapper.findCheckPageList(pageNum, pageSize, search, bean);
        PageInfo<RelationProList> pageInfo = new PageInfo<>(pagebean);

        return pageInfo;
    }

    /**
     * 查询台账页面
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    @Override
    public PageInfo<RelationProList> ledgerPagelist(Integer pageNum, Integer pageSize, String search, RelationProList bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProList> pagebean = baseMapper.findLedgerPageList(pageNum, pageSize, search, bean);
        PageInfo<RelationProList> pageInfo = new PageInfo<>(pagebean);

        return pageInfo;
    }

    @Override
    public Workbook expertledgerPagelist(Integer pageNum, Integer pageSize, String search, RelationProList bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProList> list = baseMapper.findLedgerPageList(pageNum, pageSize, search, bean);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getProPerformanceInfo().setNum(i + 1);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("资料台账", "资料台账"),
                RelationProList.class, list);

        return workbook;
    }

    /**
     * 项目资料清单关系表,点击新增查询项目
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @author: 陈睿超
     * @createDate: 2021/3/18 16:01
     * @updater: 陈睿超
     * @updateDate: 2021/3/18 16:01
     * @return
     */
    @Override
    public PageInfo<TProPerformanceInfo> selectProPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<TProPerformanceInfo> pagebean = proPerformanceInfoMapper.findRelationProPage(pageNum, pageSize, bean, search);
        PageInfo<TProPerformanceInfo> pageInfo = new PageInfo<>(pagebean);

        return pageInfo;
    }

    /**
     * 保存
     * @param bean : 接收对象
     * @param user : 当前登录用户
     * @author: 陈睿超
     * @createDate: 2021/3/18 16:01
     * @updater: 陈睿超
     * @updateDate: 2021/3/18 16:01
     * @return
     */
    @Override
    public Boolean save(RelationProList bean, ShiroUser user) {
        RelationProList oldbean = new RelationProList();
        String addOrUpdate = null;
        //判断新增还是修改
        if(null==bean.getIdR()){
            //新增
            addOrUpdate = "add";
            //查询同个项目下有多少个版本
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_A",bean.getIdA());
            entityWrapper.eq("RELATION_STATUS","1");
            entityWrapper.orderBy("CREATE_TIME");
            List<RelationProList> versionNolist = selectList(entityWrapper);

            if (0 == versionNolist.size()){
                //版本号
                bean.setVersionNo("V1");
            }else {
//            versionNolist.sort(Comparator.comparing(RelationProList::getCreateTime));
                String versionNo = versionNolist.get(versionNolist.size()-1).getVersionNo();
                String num = versionNo.substring(1, versionNo.length());

                //版本号
                bean.setVersionNo("V"+(Integer.parseInt(num)+1));
            }
            //拟定（申请）时间
            bean.setCreateTime(new Date());
            //拟定人
            bean.setCreateUaseName(user.getName());
            //拟定人id
            bean.setCreateUaseId(user.getId().toString());


        }else{
            //修改
            addOrUpdate = "update";
            //查询旧数据
            oldbean = selectById(bean.getIdR());
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_R",bean.getIdR());
//            developmentInformationListMapper.selectList(entityWrapper);
            //删除旧的资料数据
            List<TDevelopmentInformationList> oldlist = developmentInformationListMapper.selectList(entityWrapper);
            for (int i = oldlist.size() - 1; i >= 0; i--) {
                TDevelopmentInformationList olddbean = oldlist.get(i);
                developmentInformationListMapper.deleteById(olddbean.getIdB());
            }
//            bean = oldbean;
            if("1".equals(bean.getCreateStauts())){
                //更新当前节点
                EntityWrapper entityWrapper1 = new EntityWrapper();
                entityWrapper1.eq("ID_R",bean.getIdR());
                entityWrapper1.eq("ORDER_OF_CURRENT_NODE",1);
                BussinessFlowProList workflow = bussinessFlowProListService.selectOne(entityWrapper1);
                if (workflow != null){
                    workflow.setCurrentNodeStatus("1");
                    workflow.setNodeIsActive("0");
                    bussinessFlowProListService.updateById(workflow);
                }
                EntityWrapper nextEntityWrapper = new EntityWrapper();
                nextEntityWrapper.eq("ID_R",bean.getIdR());
                nextEntityWrapper.eq("ORDER_OF_CURRENT_NODE",2);
                BussinessFlowProList nextWorkflow = bussinessFlowProListService.selectOne(nextEntityWrapper);
                if (nextWorkflow != null){
                    nextWorkflow.setCurrentNodeStatus("0");
                    nextWorkflow.setNodeIsActive("1");
                    bussinessFlowProListService.updateById(nextWorkflow);
                }
                
            }
        }

        //获取关联项目
        TProPerformanceInfo pro = proPerformanceInfoMapper.selectById(bean.getIdA());
        bean.setRelationStatus("1");
        //判断是送审还是暂存
        if("1".equals(bean.getCreateStauts())){
            //送审
            //设置下个审批人
            bean.setNextCheckId(pro.getProManagerId());
            bean.setNextCheckName(pro.getProManagerName());
            //设置以前的审批记录为历史数据
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_R",bean.getIdR());
            entityWrapper.eq("CHECK_DATA_STATUS","1");
            List<TDevelopmentInfoListCheckRec> checkList = developmentInfoListCheckRecMapper.selectList(entityWrapper);
            for (int i = 0; i < checkList.size(); i++) {
                TDevelopmentInfoListCheckRec checkHistory = checkList.get(i);
                checkHistory.setCheckDataStatus("0");
                developmentInfoListCheckRecMapper.updateById(checkHistory);
            }
        }

        if(null==bean.getIdR()){
            //保存关系表
            insertOrUpdate(bean);
        }else{
            //拟定（申请）时间
            oldbean.setCreateTime(new Date());
            oldbean.setCreateStauts(bean.getCreateStauts());
            oldbean.setNextCheckId(bean.getNextCheckId());
            oldbean.setNextCheckName(bean.getNextCheckName());
            updateById(oldbean);
            oldbean.setDevelopmentInformationLists(bean.getDevelopmentInformationLists());
            bean = oldbean;
        }
        
        //保存新资料的数据
        List<TDevelopmentInformationList> newlist = bean.getDevelopmentInformationLists();
        for (int i = newlist.size() - 1; i >= 0; i--) {
            TDevelopmentInformationList newbean = newlist.get(i);
            newbean.setIdB(null);
            //设置外键
            newbean.setIdR(bean.getIdR());
            developmentInformationListMapper.insert(newbean);
        }

        //新增
        if("add".equals(addOrUpdate)){
            //创建集合保存工作流
            List<BussinessFlowProList> bussinessFlowProList = new ArrayList<BussinessFlowProList>();
            //创建工作流数据
            //获取发起人
            BussinessFlowProList applicantBussinessFlowPro = bussinessFlowProListService.getapplicantUser(user);
            applicantBussinessFlowPro.setIdR(bean.getIdR());
            applicantBussinessFlowPro.setCheckUserJobNumber(user.getGroupMemberCode());
            applicantBussinessFlowPro.setCurrentNodeStatus("1");
            //获取项目经理
            RcUser rcUser = rcUserMapper.selectById(pro.getProManagerId());
            BussinessFlowProList bussinessFlowPro = new BussinessFlowProList(null,bean.getIdR(),
                    pro.getProManagerId(),pro.getProManagerName(),rcUser.getGroupMemberCode(),rcUser.getDeptId(),rcUser.getDeptName(),
                    null,null,null,null,null,2,
                    "0","0",user.getName(),new Date(),user.getName(),new Date(),null,null,null,null);
            bussinessFlowPro.setCheckUserId(pro.getProManagerId());
            bussinessFlowPro.setCheckUser(pro.getProManagerName());

            //送审的情况下下一个审批人是活跃状态，如果暂存的话，发起人才是活跃节点
            if("1".equals(bean.getCreateStauts())){
                applicantBussinessFlowPro.setNodeIsActive("0");
                bussinessFlowPro.setNodeIsActive("1");
            }
            bussinessFlowProList.add(applicantBussinessFlowPro);
            bussinessFlowProList.add(bussinessFlowPro);
            //保存流程
            for (int i = 0; i < bussinessFlowProList.size(); i++) {
                bussinessFlowProListMapper.insert(bussinessFlowProList.get(i));
            }
        }
        return true;
    }


    @Override
    public Boolean deleteRelationPro(RelationProList bean) {
        bean.setRelationStatus("9");
        baseMapper.updateById(bean);

        /*EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",bean.getIdR());
        List<TDevelopmentInfoListCheckRec> checkList = developmentInfoListCheckRecMapper.selectList(entityWrapper);
        for (int i = 0; i < checkList.size(); i++) {
            TDevelopmentInfoListCheckRec checkHistory = checkList.get(i);
            //设置为历史审批记录
            checkHistory.setCheckDataStatus("0");
            developmentInfoListCheckRecMapper.updateById(checkHistory);
        }*/

        return true;
    }

}
