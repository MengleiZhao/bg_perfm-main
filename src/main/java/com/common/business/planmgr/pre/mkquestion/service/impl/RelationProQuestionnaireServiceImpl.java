package com.common.business.planmgr.pre.mkquestion.service.impl;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.planmgr.pre.mkquestion.entity.*;
import com.common.business.planmgr.pre.mkquestion.mapper.*;
import com.common.business.planmgr.pre.mkquestion.service.RelationProQuestionnaireService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mkquestion.service.TQuestionnaireService;
import com.common.business.planmgr.pre.mkquestion.service.TQuestionnaireSubjectsService;
import com.common.business.planmgr.pre.mkquestion.web.RelationProQuestionnaireVo;
import com.common.business.planmgr.pre.mkquestioncheck.entity.BussinessFlowProQuestionnaire;
import com.common.business.planmgr.pre.mkquestioncheck.entity.DesigneeRecProQuestionnaire;
import com.common.business.planmgr.pre.mkquestioncheck.entity.TQuestionnaireCheckRec;
import com.common.business.planmgr.pre.mkquestioncheck.mapper.DesigneeRecProQuestionnaireMapper;
import com.common.business.planmgr.pre.mkquestioncheck.mapper.TQuestionnaireCheckRecMapper;
import com.common.business.planmgr.pre.mkquestioncheck.service.BussinessFlowProQuestionnaireService;
import com.common.business.planmgr.pre.mkquestioncheck.service.DesigneeRecProQuestionnaireService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.exception.ServiceException;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  项目拟定调查问卷关系 
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RelationProQuestionnaireServiceImpl extends ServiceImpl<RelationProQuestionnaireMapper, RelationProQuestionnaire> implements RelationProQuestionnaireService {

    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;
    @Autowired
    private TQuestionnaireMapper questionnaireMapper;
    @Autowired
    private TQuestionnaireSubjectsMapper questionnaireSubjectsMapper;
    @Autowired
    private TQuestionnaireSubjectsOptionsMapper questionnaireSubjectsOptionsMapper;
    @Autowired
    private BussinessFlowProQuestionnaireService bussinessFlowProQuestionnaireService;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TQuestionnaireCheckRecMapper questionnaireCheckRecMapper;
    @Autowired
    private DesigneeRecProQuestionnaireMapper designeeRecProQuestionnaireMapper;
    @Autowired
    private TQuestionnaireAnswerSheetMapper questionnaireAnswerSheetMapper;



    /**
     * @Title: pagelist
     * @Description:  问卷拟定页面分页查询
     * @author: 陈睿超
     * @createDate: 2021/3/31 20:18
     * @updater: 陈睿超
     * @updateDate: 2021/3/31 20:18
     * @param pageNum
     * @param pageSize
     * @param search 模糊查询数据
     * @param bean 精确查询
     * @param user 当前登录人
     * @return
     **/
    @Override
    public PageInfo<RelationProQuestionnaire> pagelist(Integer pageNum, Integer pageSize, String search, RelationProQuestionnaire bean, ShiroUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProQuestionnaire> list = baseMapper.pageList(pageNum,pageSize,search,bean,user);
        return new PageInfo<RelationProQuestionnaire>(list);
    }

    /**
     * @Title:
     * @Description: 审批分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/7 11:07
     * @updater: 陈睿超
     * @updateDate: 2021/4/7 11:07
     * @param pageNum
     * @param pageSize
     * @param search 模糊查询数据
     * @param bean 精确查询
     * @param user 当前登录人
     * @return
     **/
    @Override
    public PageInfo<RelationProQuestionnaire> checkPage(Integer pageNum, Integer pageSize, String search, RelationProQuestionnaire bean, ShiroUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProQuestionnaire> list = baseMapper.checkPage(pageNum,pageSize,search,bean,user);
        return new PageInfo<RelationProQuestionnaire>(list);
    }

    /**
     * 调查问卷新增查询项目
     * @param pageNum
     * @param pageSize
     * @param pro
     * @param workingGroup
     * @param user
     * @return
     */
    @Override
    public PageInfo<TProPerformanceInfo> choosePro(Integer pageNum, Integer pageSize, Integer preOrScheme, TProPerformanceInfo pro, TPerformanceWorkingGroup workingGroup, ShiroUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<TProPerformanceInfo> list = proPerformanceInfoMapper.selectProByGroup(pageNum, pageSize, preOrScheme, pro, workingGroup, user);
        return new PageInfo<TProPerformanceInfo>(list);
    }

    /**
     *  保存
     * @author: 陈睿超
     * @createDate: 2021/4/1 15:43
     * @updater: 陈睿超
     * @updateDate: 2021/4/1 15:43
     * @param 	beanVo : 前台接收对象
     * @param 	user : 当前登录人
     * @return
     **/
    @Override
    public Boolean saveQuestionnaire(RelationProQuestionnaireVo beanVo,ShiroUser user) throws Exception {
        try {
            //项目拟定调查问卷关系数据
            RelationProQuestionnaire bean = beanVo.getBean();
            //拟定调查问卷
            TQuestionnaire questionnaire = beanVo.getQuestionnaire();
            String addOrUpdate = null;
            if (null == bean.getIdR()){
                //新增
                addOrUpdate = "add";
                bean.setCreateUaseId(user.getId().toString());
                bean.setCreateTime(new Date());
                bean.setCreateUaseName(user.getName());
                bean.setRelationStatus("1");
                //查询同个项目下有多少个版本
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_A",bean.getIdA());
                entityWrapper.eq("RELATION_STATUS","1");
                entityWrapper.orderBy("CREATE_TIME");
                List<RelationProList> versionNolist = selectList(entityWrapper);
                //版本号
                if (0 == versionNolist.size()) {
                    bean.setVersionNo("V1");
                }else {
                    String versionNo = versionNolist.get(versionNolist.size()-1).getVersionNo();
                    String num = versionNo.substring(1, versionNo.length());
                    bean.setVersionNo("V"+(Integer.parseInt(num)+1));
                }
                insert(bean);
                //保存拟定调查问卷
                questionnaire.setIdR(bean.getIdR());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

                String quesId = sdf.format(new Date());
                String r=String.valueOf((Math.random()*9+1)*1000);
                String r1=r.substring(0, 3);
                questionnaire.setQuesId(quesId+r1);
                questionnaire.setQuesStatus("0");
                questionnaire.setTotalAnswer(0);
                questionnaire.setQuesCreateTime(new Date());
                questionnaire.setQuesCreateor(user.getName());
                questionnaireMapper.insert(questionnaire);
                //保存问卷题目表
                List<TQuestionnaireSubjects> questionnaireSubjectsList = beanVo.getQuestionnaireSubjects();
                for (int i = 0; i < questionnaireSubjectsList.size(); i++) {
                    TQuestionnaireSubjects questionnaireSubjects = questionnaireSubjectsList.get(i);
                    questionnaireSubjects.setIdB(questionnaire.getIdB());
                    questionnaireSubjectsMapper.insert(questionnaireSubjects);
                    //保存问卷答项表
                    List<TQuestionnaireSubjectsOptions> questionnaireSubjectsOptionsList = questionnaireSubjects.getQuestionnaireSubjectsOptions();
                    for (int j = 0; j < questionnaireSubjectsOptionsList.size(); j++) {
                        TQuestionnaireSubjectsOptions questionnaireSubjectsOptions = questionnaireSubjectsOptionsList.get(j);
                        questionnaireSubjectsOptions.setIdC(questionnaireSubjects.getIdC());
                        questionnaireSubjectsOptionsMapper.insert(questionnaireSubjectsOptions);
                    }
                }

            }else {
                //修改
                addOrUpdate = "update";
                //更新关系表
                RelationProQuestionnaire oldbean = selectById(bean.getIdR());
                oldbean.setCreateUaseId(user.getId().toString());
                oldbean.setCreateTime(new Date());
                oldbean.setCreateUaseName(user.getName());
                oldbean.setCreateStauts(bean.getCreateStauts());
                oldbean.setQuesName(bean.getQuesName());
                updateById(oldbean);
                bean = oldbean;
                //更新调查问卷
                TQuestionnaire oldquestionnaire = questionnaireMapper.selectById(questionnaire.getIdB());
                questionnaire.setIdR(bean.getIdR());
                questionnaire.setQuesId(oldquestionnaire.getQuesId());
                questionnaire.setQuesStatus(oldquestionnaire.getQuesStatus());
                questionnaire.setTotalAnswer(oldquestionnaire.getTotalAnswer());
                questionnaire.setQuesCreateTime(oldquestionnaire.getQuesCreateTime());
                questionnaire.setQuesCreateor(oldquestionnaire.getQuesCreateor());
                questionnaireMapper.updateById(questionnaire);
                //更新问卷题目表
                //查询旧问卷题目，并删除
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_B",questionnaire.getIdB());
                List<TQuestionnaireSubjects> oldquestionnaireSubjectsList = questionnaireSubjectsMapper.selectList(entityWrapper);
                for (TQuestionnaireSubjects questionnaireSubjects : oldquestionnaireSubjectsList) {
                    questionnaireSubjectsMapper.deleteById(questionnaireSubjects.getIdC());
                }
                //保存新的问卷题目
                List<TQuestionnaireSubjects> questionnaireSubjectsList = beanVo.getQuestionnaireSubjects();
                for (int i = 0; i < questionnaireSubjectsList.size(); i++) {
                    TQuestionnaireSubjects questionnaireSubjects = questionnaireSubjectsList.get(i);
                    questionnaireSubjects.setIdB(questionnaire.getIdB());
                    questionnaireSubjectsMapper.insert(questionnaireSubjects);
                    //删除旧的问卷答项表
                    EntityWrapper entityWrapper1 = new EntityWrapper();
                    entityWrapper1.eq("ID_C",questionnaireSubjects.getIdC());
                    entityWrapper1.orderBy("ID_D",true);
                    List<TQuestionnaireSubjectsOptions> oldquestionnaireSubjectsOptionsList = questionnaireSubjectsOptionsMapper.selectList(entityWrapper1);
                    if (oldquestionnaireSubjectsOptionsList.size() > 0){
                        for (TQuestionnaireSubjectsOptions tQuestionnaireSubjectsOptions : oldquestionnaireSubjectsOptionsList) {
                            questionnaireSubjectsOptionsMapper.deleteById(tQuestionnaireSubjectsOptions.getIdD());
                        }
                    }
                    //添加修改后的问卷答项表
                    List<TQuestionnaireSubjectsOptions> questionnaireSubjectsOptionsList = questionnaireSubjects.getQuestionnaireSubjectsOptions();
                    for (int j = 0; j < questionnaireSubjectsOptionsList.size(); j++) {
                        TQuestionnaireSubjectsOptions questionnaireSubjectsOptions = questionnaireSubjectsOptionsList.get(j);
                        questionnaireSubjectsOptionsMapper.insert(questionnaireSubjectsOptions);
                    }
                }

            }

            if ("1".equals(bean.getCreateStauts())){
                //送审
                if ("add".equals(addOrUpdate)){
                    //获取的工作流
                    List<BussinessFlowProQuestionnaire> workFlowList = bussinessFlowProQuestionnaireService.getWorkFlow(bean, user);

                    RcUser nextUser = new RcUser();

                    //发起人不是最后一个人审批人
                    if (!user.getId().equals(workFlowList.get(workFlowList.size()-1).getCheckUserId())){
                        for (int i = 0; i < workFlowList.size(); i++) {
                            if ("1".equals(workFlowList.get(i).getNodeIsActive())){
                                //匹配到活跃节点
                                nextUser = rcUserMapper.selectById(workFlowList.get(i).getCheckUserId());

                            }
                            bussinessFlowProQuestionnaireService.insertOrUpdate(workFlowList.get(i));
                        }
                        //设置下级审批人
                        bean.setCurrCheckId(nextUser.getId().toString());
                        bean.setCurrCheckName(nextUser.getName());
                    }else {
                        //发起人是最后一个人审批人
                        System.out.print("发起人是最后一个人审批人");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        throw new Exception();
                    }
                    //更新下级审批人
                    updateById(bean);
                }else if ("update".equals(addOrUpdate)){
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("ID_R",bean.getIdR());
                    entityWrapper.orderBy("ORDER_OF_CURRENT_NODE",true);
                    //获取的工作流
                    List<BussinessFlowProQuestionnaire> workFlowList = bussinessFlowProQuestionnaireService.selectList(entityWrapper);
                    //当前节点
                    BussinessFlowProQuestionnaire currentWorkFlow = new BussinessFlowProQuestionnaire();
                    //下一个节点
                    BussinessFlowProQuestionnaire nextWorkFlow = new BussinessFlowProQuestionnaire();
                    //下一个审批人
                    RcUser nextUser = new RcUser();

                    //发起人不是最后一个人审批人
                    if (!user.getId().equals(workFlowList.get(workFlowList.size()-1).getCheckUserId())){
                        for (int i = 0; i < workFlowList.size(); i++) {
                            if ("1".equals(workFlowList.get(i).getOrderOfCurrentNode())){
                                //发节点
                                currentWorkFlow = workFlowList.get(i);
                                nextWorkFlow = workFlowList.get(i+1);
                            }
                        }
                        nextUser = rcUserMapper.selectById(nextWorkFlow.getCheckUserId());
                        //设置下级审批人
                        bean.setCurrCheckId(nextUser.getId().toString());
                        bean.setCurrCheckName(nextUser.getName());
                        nextWorkFlow.setNodeIsActive("1");
                        nextWorkFlow.setCurrentNodeStatus("0");
                        currentWorkFlow.setNodeIsActive("0");
                        currentWorkFlow.setCurrentNodeStatus("1");
                        bussinessFlowProQuestionnaireService.updateById(currentWorkFlow);
                        bussinessFlowProQuestionnaireService.updateById(nextWorkFlow);
                    }else {
                        //发起人是最后一个人审批人
                        System.out.print("发起人是最后一个人审批人");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        throw new ServiceException("发起人是最后一个人审批人");
                    }
                    //更新下级审批人
                    updateById(bean);
                }
                //重置审批记录
                EntityWrapper checkentityWrapper = new EntityWrapper();
                checkentityWrapper.eq("ID_R",bean.getIdR());
                checkentityWrapper.eq("CHECK_DATA_STATUS","1");
                List<TQuestionnaireCheckRec> list = questionnaireCheckRecMapper.selectList(checkentityWrapper);
                for (int i = 0; i < list.size(); i++) {
                    TQuestionnaireCheckRec questionnaireCheckRec = list.get(i);
                    questionnaireCheckRec.setCheckDataStatus("0");
                    questionnaireCheckRecMapper.updateById(questionnaireCheckRec);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception();
        }
    }

    /**
     * 逻辑删除数据
     * @author: 陈睿超
     * @createDate: 2021/4/6 16:42
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 16:42
     * @param idR : RelationProQuestionnaire的主键
     * @param user : 当前登录人
     * @return
     **/
    @Override
    public Boolean deleteByIdA(Integer idR, ShiroUser user) {
        RelationProQuestionnaire relationProQuestionnaire = baseMapper.selectById(idR);
        relationProQuestionnaire.setRelationStatus("9");
        baseMapper.updateById(relationProQuestionnaire);
        return true;
    }

    /**
     * 审批
     * @author: 陈睿超
     * @createDate: 2021/4/6 16:43
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 16:43
     * @param questionnaireCheckRec : 审批信息
     * @param user : 当前登录人
     * @return
     **/

    @Override
    public Boolean check(TQuestionnaireCheckRec questionnaireCheckRec, ShiroUser user) {
        //指派记录表
        DesigneeRecProQuestionnaire designeeRecProQuestionnaire = questionnaireCheckRec.getDesigneeRecProQuestionnaire();
        //项目拟定调查问卷关系
        RelationProQuestionnaire relationProQuestionnaire = baseMapper.selectById(questionnaireCheckRec.getIdR());
        //获取工作流
        EntityWrapper workFlowEntityWrapper = new EntityWrapper();
        workFlowEntityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
        workFlowEntityWrapper.orderBy("ORDER_OF_CURRENT_NODE",true);
        List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList = bussinessFlowProQuestionnaireService.selectList(workFlowEntityWrapper);
        //获取当前点
        BussinessFlowProQuestionnaire currentBussinessFlow = bussinessFlowProQuestionnaireService.getCurrentBussinessFlow(bussinessFlowProQuestionnaireList);
        //如果没有流程直接返回
        //判断是否转派任务
        if (StringUtils.isEmpty(designeeRecProQuestionnaire.getDesigneeId())){
            //不转派
            if (bussinessFlowProQuestionnaireList.size() <= 0){
                throw new ServiceException("查询不到该表单流程");
            }else {
                if ("0".equals(questionnaireCheckRec.getCheckResult())){
                    //不通过
                    relationProQuestionnaire.setCreateStauts("-1");
                    relationProQuestionnaire.setCurrCheckId(null);
                    relationProQuestionnaire.setCurrCheckName(null);
                    //工作流状态全部变成未完成
                    for (int i = 0; i < bussinessFlowProQuestionnaireList.size(); i++) {
                        BussinessFlowProQuestionnaire bussinessFlowProQuestionnaire = bussinessFlowProQuestionnaireList.get(i);
                        bussinessFlowProQuestionnaire.setCurrentNodeStatus("0");
                        if (i == 0){
                            bussinessFlowProQuestionnaire.setNodeIsActive("1");
                        }
                        bussinessFlowProQuestionnaireService.updateById(bussinessFlowProQuestionnaire);
                    }
                }else {
                    //通过
                    //获取下一节点
                    BussinessFlowProQuestionnaire nextBussinessFlow = bussinessFlowProQuestionnaireService.getNextBussinessFlow(bussinessFlowProQuestionnaireList);
                    RcUser nextUser = new RcUser();
                    if (null != nextBussinessFlow){
                        //获取下一审批人
                        nextUser = rcUserMapper.selectById(nextBussinessFlow.getCheckUserId());
                        relationProQuestionnaire.setCurrCheckId(nextUser.getId().toString());
                        relationProQuestionnaire.setCurrCheckName(nextUser.getName());
                        //修改活跃状态
                        currentBussinessFlow.setNodeIsActive("0");
                        currentBussinessFlow.setCurrentNodeStatus("1");
                        nextBussinessFlow.setNodeIsActive("1");
                        bussinessFlowProQuestionnaireService.updateById(currentBussinessFlow);
                        bussinessFlowProQuestionnaireService.updateById(nextBussinessFlow);
                    }else {
                        //是最后一个节点
                        relationProQuestionnaire.setCreateStauts("2");
                        relationProQuestionnaire.setCurrCheckId(null);
                        relationProQuestionnaire.setCurrCheckName(null);
                        //活跃状态改成不活跃
                        currentBussinessFlow.setNodeIsActive("0");
                        currentBussinessFlow.setCurrentNodeStatus("1");
                        bussinessFlowProQuestionnaireService.updateById(currentBussinessFlow);
                        //改变项目调查问卷审核状态
                        TProPerformanceInfo pro = proPerformanceInfoMapper.selectById(relationProQuestionnaire.getIdA());
                        if (1 == relationProQuestionnaire.getPreOrScheme()){
                            pro.setQuesCheckStatusC("1");
                        }else {
                            pro.setQuesCheckStatusY("1");
                        }
                        proPerformanceInfoMapper.updateById(pro);
                    }
                }

                //更新关系表数据状态
                baseMapper.updateById(relationProQuestionnaire);
                //添加审批记录
                questionnaireCheckRec.setCheckTime(new Date());
                questionnaireCheckRec.setCheckUserId(user.getId().toString());
                questionnaireCheckRec.setCheckUser(user.getName());
                questionnaireCheckRec.setCheckDataStatus("1");
                questionnaireCheckRec.setOrderOfCurrentNode(currentBussinessFlow.getOrderOfCurrentNode());
                //判断是不是被转派人审批
                if (!StringUtils.isEmpty(currentBussinessFlow.getDesigneeId()) && user.getId().equals(currentBussinessFlow.getDesigneeId())){
                    //被转派人进来审批需要添加备注
                    //设置审批记录的备注
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
                    entityWrapper.eq("CHECK_USER_ID",currentBussinessFlow.getCheckUserId());
                    entityWrapper.eq("ORDER_OF_CURRENT_NODE",currentBussinessFlow.getOrderOfCurrentNode());
                    entityWrapper.orderBy("DESIGNEE",true);
                    //查询以前具体主转派时间
                    List<DesigneeRecProQuestionnaire> currentDesigneeRecProQuestionnaire = designeeRecProQuestionnaireMapper.selectList(entityWrapper);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String str = sdf.format(currentDesigneeRecProQuestionnaire.get(currentDesigneeRecProQuestionnaire.size()-1).getDesignee());
                    questionnaireCheckRec.setRemark(str+"时间 由 "+currentBussinessFlow.getCheckUser()+" 转 "+
                            user.getName()+" 承办”");
                }
                questionnaireCheckRecMapper.insert(questionnaireCheckRec);
            }
        }else {
            //转派
            //添加转派记录
            designeeRecProQuestionnaire.setIdR(relationProQuestionnaire.getIdR());
            designeeRecProQuestionnaire.setDesignee(new Date());
            designeeRecProQuestionnaire.setCheckUserId(currentBussinessFlow.getCheckUserId());
            designeeRecProQuestionnaire.setCheckUser(currentBussinessFlow.getCheckUser());
            designeeRecProQuestionnaire.setOrderOfCurrentNode(currentBussinessFlow.getOrderOfCurrentNode());
            designeeRecProQuestionnaireMapper.insert(designeeRecProQuestionnaire);
            //更新当前节点信息
            RcUser designeeUser = rcUserMapper.selectById(designeeRecProQuestionnaire.getDesigneeId());
            currentBussinessFlow.setDesigneeId(designeeUser.getId().toString());
            currentBussinessFlow.setDesigneeName(designeeUser.getName());
            currentBussinessFlow.setDesigneeDeptId(designeeUser.getDeptId());
            currentBussinessFlow.setDesigneeDeptName(designeeUser.getDeptName());
            currentBussinessFlow.setDesigneeJobNumber(designeeUser.getGroupMemberCode());
            bussinessFlowProQuestionnaireService.updateById(currentBussinessFlow);
            //更新下级审批人数据
            relationProQuestionnaire.setCurrCheckId(designeeRecProQuestionnaire.getDesigneeId());
            relationProQuestionnaire.setCurrCheckName(designeeRecProQuestionnaire.getDesigneeName());
            //更新关系表数据状态
            baseMapper.updateById(relationProQuestionnaire);
        }
        return true;
    }


}
