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
 *  ?????????????????????????????? 
 *  ???????????????
 * </p>
 *
 * @author ?????????
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
     * @Description:  ??????????????????????????????
     * @author: ?????????
     * @createDate: 2021/3/31 20:18
     * @updater: ?????????
     * @updateDate: 2021/3/31 20:18
     * @param pageNum
     * @param pageSize
     * @param search ??????????????????
     * @param bean ????????????
     * @param user ???????????????
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
     * @Description: ??????????????????
     * @author: ?????????
     * @createDate: 2021/4/7 11:07
     * @updater: ?????????
     * @updateDate: 2021/4/7 11:07
     * @param pageNum
     * @param pageSize
     * @param search ??????????????????
     * @param bean ????????????
     * @param user ???????????????
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
     * ??????????????????????????????
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
     *  ??????
     * @author: ?????????
     * @createDate: 2021/4/1 15:43
     * @updater: ?????????
     * @updateDate: 2021/4/1 15:43
     * @param 	beanVo : ??????????????????
     * @param 	user : ???????????????
     * @return
     **/
    @Override
    public Boolean saveQuestionnaire(RelationProQuestionnaireVo beanVo,ShiroUser user) throws Exception {
        try {
            //????????????????????????????????????
            RelationProQuestionnaire bean = beanVo.getBean();
            //??????????????????
            TQuestionnaire questionnaire = beanVo.getQuestionnaire();
            String addOrUpdate = null;
            if (null == bean.getIdR()){
                //??????
                addOrUpdate = "add";
                bean.setCreateUaseId(user.getId().toString());
                bean.setCreateTime(new Date());
                bean.setCreateUaseName(user.getName());
                bean.setRelationStatus("1");
                //???????????????????????????????????????
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_A",bean.getIdA());
                entityWrapper.eq("RELATION_STATUS","1");
                entityWrapper.orderBy("CREATE_TIME");
                List<RelationProList> versionNolist = selectList(entityWrapper);
                //?????????
                if (0 == versionNolist.size()) {
                    bean.setVersionNo("V1");
                }else {
                    String versionNo = versionNolist.get(versionNolist.size()-1).getVersionNo();
                    String num = versionNo.substring(1, versionNo.length());
                    bean.setVersionNo("V"+(Integer.parseInt(num)+1));
                }
                insert(bean);
                //????????????????????????
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
                //?????????????????????
                List<TQuestionnaireSubjects> questionnaireSubjectsList = beanVo.getQuestionnaireSubjects();
                for (int i = 0; i < questionnaireSubjectsList.size(); i++) {
                    TQuestionnaireSubjects questionnaireSubjects = questionnaireSubjectsList.get(i);
                    questionnaireSubjects.setIdB(questionnaire.getIdB());
                    questionnaireSubjectsMapper.insert(questionnaireSubjects);
                    //?????????????????????
                    List<TQuestionnaireSubjectsOptions> questionnaireSubjectsOptionsList = questionnaireSubjects.getQuestionnaireSubjectsOptions();
                    for (int j = 0; j < questionnaireSubjectsOptionsList.size(); j++) {
                        TQuestionnaireSubjectsOptions questionnaireSubjectsOptions = questionnaireSubjectsOptionsList.get(j);
                        questionnaireSubjectsOptions.setIdC(questionnaireSubjects.getIdC());
                        questionnaireSubjectsOptionsMapper.insert(questionnaireSubjectsOptions);
                    }
                }

            }else {
                //??????
                addOrUpdate = "update";
                //???????????????
                RelationProQuestionnaire oldbean = selectById(bean.getIdR());
                oldbean.setCreateUaseId(user.getId().toString());
                oldbean.setCreateTime(new Date());
                oldbean.setCreateUaseName(user.getName());
                oldbean.setCreateStauts(bean.getCreateStauts());
                oldbean.setQuesName(bean.getQuesName());
                updateById(oldbean);
                bean = oldbean;
                //??????????????????
                TQuestionnaire oldquestionnaire = questionnaireMapper.selectById(questionnaire.getIdB());
                questionnaire.setIdR(bean.getIdR());
                questionnaire.setQuesId(oldquestionnaire.getQuesId());
                questionnaire.setQuesStatus(oldquestionnaire.getQuesStatus());
                questionnaire.setTotalAnswer(oldquestionnaire.getTotalAnswer());
                questionnaire.setQuesCreateTime(oldquestionnaire.getQuesCreateTime());
                questionnaire.setQuesCreateor(oldquestionnaire.getQuesCreateor());
                questionnaireMapper.updateById(questionnaire);
                //?????????????????????
                //?????????????????????????????????
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_B",questionnaire.getIdB());
                List<TQuestionnaireSubjects> oldquestionnaireSubjectsList = questionnaireSubjectsMapper.selectList(entityWrapper);
                for (TQuestionnaireSubjects questionnaireSubjects : oldquestionnaireSubjectsList) {
                    questionnaireSubjectsMapper.deleteById(questionnaireSubjects.getIdC());
                }
                //????????????????????????
                List<TQuestionnaireSubjects> questionnaireSubjectsList = beanVo.getQuestionnaireSubjects();
                for (int i = 0; i < questionnaireSubjectsList.size(); i++) {
                    TQuestionnaireSubjects questionnaireSubjects = questionnaireSubjectsList.get(i);
                    questionnaireSubjects.setIdB(questionnaire.getIdB());
                    questionnaireSubjectsMapper.insert(questionnaireSubjects);
                    //???????????????????????????
                    EntityWrapper entityWrapper1 = new EntityWrapper();
                    entityWrapper1.eq("ID_C",questionnaireSubjects.getIdC());
                    entityWrapper1.orderBy("ID_D",true);
                    List<TQuestionnaireSubjectsOptions> oldquestionnaireSubjectsOptionsList = questionnaireSubjectsOptionsMapper.selectList(entityWrapper1);
                    if (oldquestionnaireSubjectsOptionsList.size() > 0){
                        for (TQuestionnaireSubjectsOptions tQuestionnaireSubjectsOptions : oldquestionnaireSubjectsOptionsList) {
                            questionnaireSubjectsOptionsMapper.deleteById(tQuestionnaireSubjectsOptions.getIdD());
                        }
                    }
                    //?????????????????????????????????
                    List<TQuestionnaireSubjectsOptions> questionnaireSubjectsOptionsList = questionnaireSubjects.getQuestionnaireSubjectsOptions();
                    for (int j = 0; j < questionnaireSubjectsOptionsList.size(); j++) {
                        TQuestionnaireSubjectsOptions questionnaireSubjectsOptions = questionnaireSubjectsOptionsList.get(j);
                        questionnaireSubjectsOptionsMapper.insert(questionnaireSubjectsOptions);
                    }
                }

            }

            if ("1".equals(bean.getCreateStauts())){
                //??????
                if ("add".equals(addOrUpdate)){
                    //??????????????????
                    List<BussinessFlowProQuestionnaire> workFlowList = bussinessFlowProQuestionnaireService.getWorkFlow(bean, user);

                    RcUser nextUser = new RcUser();

                    //???????????????????????????????????????
                    if (!user.getId().equals(workFlowList.get(workFlowList.size()-1).getCheckUserId())){
                        for (int i = 0; i < workFlowList.size(); i++) {
                            if ("1".equals(workFlowList.get(i).getNodeIsActive())){
                                //?????????????????????
                                nextUser = rcUserMapper.selectById(workFlowList.get(i).getCheckUserId());

                            }
                            bussinessFlowProQuestionnaireService.insertOrUpdate(workFlowList.get(i));
                        }
                        //?????????????????????
                        bean.setCurrCheckId(nextUser.getId().toString());
                        bean.setCurrCheckName(nextUser.getName());
                    }else {
                        //????????????????????????????????????
                        System.out.print("????????????????????????????????????");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        throw new Exception();
                    }
                    //?????????????????????
                    updateById(bean);
                }else if ("update".equals(addOrUpdate)){
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("ID_R",bean.getIdR());
                    entityWrapper.orderBy("ORDER_OF_CURRENT_NODE",true);
                    //??????????????????
                    List<BussinessFlowProQuestionnaire> workFlowList = bussinessFlowProQuestionnaireService.selectList(entityWrapper);
                    //????????????
                    BussinessFlowProQuestionnaire currentWorkFlow = new BussinessFlowProQuestionnaire();
                    //???????????????
                    BussinessFlowProQuestionnaire nextWorkFlow = new BussinessFlowProQuestionnaire();
                    //??????????????????
                    RcUser nextUser = new RcUser();

                    //???????????????????????????????????????
                    if (!user.getId().equals(workFlowList.get(workFlowList.size()-1).getCheckUserId())){
                        for (int i = 0; i < workFlowList.size(); i++) {
                            if ("1".equals(workFlowList.get(i).getOrderOfCurrentNode())){
                                //?????????
                                currentWorkFlow = workFlowList.get(i);
                                nextWorkFlow = workFlowList.get(i+1);
                            }
                        }
                        nextUser = rcUserMapper.selectById(nextWorkFlow.getCheckUserId());
                        //?????????????????????
                        bean.setCurrCheckId(nextUser.getId().toString());
                        bean.setCurrCheckName(nextUser.getName());
                        nextWorkFlow.setNodeIsActive("1");
                        nextWorkFlow.setCurrentNodeStatus("0");
                        currentWorkFlow.setNodeIsActive("0");
                        currentWorkFlow.setCurrentNodeStatus("1");
                        bussinessFlowProQuestionnaireService.updateById(currentWorkFlow);
                        bussinessFlowProQuestionnaireService.updateById(nextWorkFlow);
                    }else {
                        //????????????????????????????????????
                        System.out.print("????????????????????????????????????");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        throw new ServiceException("????????????????????????????????????");
                    }
                    //?????????????????????
                    updateById(bean);
                }
                //??????????????????
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
     * ??????????????????
     * @author: ?????????
     * @createDate: 2021/4/6 16:42
     * @updater: ?????????
     * @updateDate: 2021/4/6 16:42
     * @param idR : RelationProQuestionnaire?????????
     * @param user : ???????????????
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
     * ??????
     * @author: ?????????
     * @createDate: 2021/4/6 16:43
     * @updater: ?????????
     * @updateDate: 2021/4/6 16:43
     * @param questionnaireCheckRec : ????????????
     * @param user : ???????????????
     * @return
     **/

    @Override
    public Boolean check(TQuestionnaireCheckRec questionnaireCheckRec, ShiroUser user) {
        //???????????????
        DesigneeRecProQuestionnaire designeeRecProQuestionnaire = questionnaireCheckRec.getDesigneeRecProQuestionnaire();
        //??????????????????????????????
        RelationProQuestionnaire relationProQuestionnaire = baseMapper.selectById(questionnaireCheckRec.getIdR());
        //???????????????
        EntityWrapper workFlowEntityWrapper = new EntityWrapper();
        workFlowEntityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
        workFlowEntityWrapper.orderBy("ORDER_OF_CURRENT_NODE",true);
        List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList = bussinessFlowProQuestionnaireService.selectList(workFlowEntityWrapper);
        //???????????????
        BussinessFlowProQuestionnaire currentBussinessFlow = bussinessFlowProQuestionnaireService.getCurrentBussinessFlow(bussinessFlowProQuestionnaireList);
        //??????????????????????????????
        //????????????????????????
        if (StringUtils.isEmpty(designeeRecProQuestionnaire.getDesigneeId())){
            //?????????
            if (bussinessFlowProQuestionnaireList.size() <= 0){
                throw new ServiceException("???????????????????????????");
            }else {
                if ("0".equals(questionnaireCheckRec.getCheckResult())){
                    //?????????
                    relationProQuestionnaire.setCreateStauts("-1");
                    relationProQuestionnaire.setCurrCheckId(null);
                    relationProQuestionnaire.setCurrCheckName(null);
                    //????????????????????????????????????
                    for (int i = 0; i < bussinessFlowProQuestionnaireList.size(); i++) {
                        BussinessFlowProQuestionnaire bussinessFlowProQuestionnaire = bussinessFlowProQuestionnaireList.get(i);
                        bussinessFlowProQuestionnaire.setCurrentNodeStatus("0");
                        if (i == 0){
                            bussinessFlowProQuestionnaire.setNodeIsActive("1");
                        }
                        bussinessFlowProQuestionnaireService.updateById(bussinessFlowProQuestionnaire);
                    }
                }else {
                    //??????
                    //??????????????????
                    BussinessFlowProQuestionnaire nextBussinessFlow = bussinessFlowProQuestionnaireService.getNextBussinessFlow(bussinessFlowProQuestionnaireList);
                    RcUser nextUser = new RcUser();
                    if (null != nextBussinessFlow){
                        //?????????????????????
                        nextUser = rcUserMapper.selectById(nextBussinessFlow.getCheckUserId());
                        relationProQuestionnaire.setCurrCheckId(nextUser.getId().toString());
                        relationProQuestionnaire.setCurrCheckName(nextUser.getName());
                        //??????????????????
                        currentBussinessFlow.setNodeIsActive("0");
                        currentBussinessFlow.setCurrentNodeStatus("1");
                        nextBussinessFlow.setNodeIsActive("1");
                        bussinessFlowProQuestionnaireService.updateById(currentBussinessFlow);
                        bussinessFlowProQuestionnaireService.updateById(nextBussinessFlow);
                    }else {
                        //?????????????????????
                        relationProQuestionnaire.setCreateStauts("2");
                        relationProQuestionnaire.setCurrCheckId(null);
                        relationProQuestionnaire.setCurrCheckName(null);
                        //???????????????????????????
                        currentBussinessFlow.setNodeIsActive("0");
                        currentBussinessFlow.setCurrentNodeStatus("1");
                        bussinessFlowProQuestionnaireService.updateById(currentBussinessFlow);
                        //????????????????????????????????????
                        TProPerformanceInfo pro = proPerformanceInfoMapper.selectById(relationProQuestionnaire.getIdA());
                        if (1 == relationProQuestionnaire.getPreOrScheme()){
                            pro.setQuesCheckStatusC("1");
                        }else {
                            pro.setQuesCheckStatusY("1");
                        }
                        proPerformanceInfoMapper.updateById(pro);
                    }
                }

                //???????????????????????????
                baseMapper.updateById(relationProQuestionnaire);
                //??????????????????
                questionnaireCheckRec.setCheckTime(new Date());
                questionnaireCheckRec.setCheckUserId(user.getId().toString());
                questionnaireCheckRec.setCheckUser(user.getName());
                questionnaireCheckRec.setCheckDataStatus("1");
                questionnaireCheckRec.setOrderOfCurrentNode(currentBussinessFlow.getOrderOfCurrentNode());
                //?????????????????????????????????
                if (!StringUtils.isEmpty(currentBussinessFlow.getDesigneeId()) && user.getId().equals(currentBussinessFlow.getDesigneeId())){
                    //??????????????????????????????????????????
                    //???????????????????????????
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
                    entityWrapper.eq("CHECK_USER_ID",currentBussinessFlow.getCheckUserId());
                    entityWrapper.eq("ORDER_OF_CURRENT_NODE",currentBussinessFlow.getOrderOfCurrentNode());
                    entityWrapper.orderBy("DESIGNEE",true);
                    //?????????????????????????????????
                    List<DesigneeRecProQuestionnaire> currentDesigneeRecProQuestionnaire = designeeRecProQuestionnaireMapper.selectList(entityWrapper);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String str = sdf.format(currentDesigneeRecProQuestionnaire.get(currentDesigneeRecProQuestionnaire.size()-1).getDesignee());
                    questionnaireCheckRec.setRemark(str+"?????? ??? "+currentBussinessFlow.getCheckUser()+" ??? "+
                            user.getName()+" ?????????");
                }
                questionnaireCheckRecMapper.insert(questionnaireCheckRec);
            }
        }else {
            //??????
            //??????????????????
            designeeRecProQuestionnaire.setIdR(relationProQuestionnaire.getIdR());
            designeeRecProQuestionnaire.setDesignee(new Date());
            designeeRecProQuestionnaire.setCheckUserId(currentBussinessFlow.getCheckUserId());
            designeeRecProQuestionnaire.setCheckUser(currentBussinessFlow.getCheckUser());
            designeeRecProQuestionnaire.setOrderOfCurrentNode(currentBussinessFlow.getOrderOfCurrentNode());
            designeeRecProQuestionnaireMapper.insert(designeeRecProQuestionnaire);
            //????????????????????????
            RcUser designeeUser = rcUserMapper.selectById(designeeRecProQuestionnaire.getDesigneeId());
            currentBussinessFlow.setDesigneeId(designeeUser.getId().toString());
            currentBussinessFlow.setDesigneeName(designeeUser.getName());
            currentBussinessFlow.setDesigneeDeptId(designeeUser.getDeptId());
            currentBussinessFlow.setDesigneeDeptName(designeeUser.getDeptName());
            currentBussinessFlow.setDesigneeJobNumber(designeeUser.getGroupMemberCode());
            bussinessFlowProQuestionnaireService.updateById(currentBussinessFlow);
            //???????????????????????????
            relationProQuestionnaire.setCurrCheckId(designeeRecProQuestionnaire.getDesigneeId());
            relationProQuestionnaire.setCurrCheckName(designeeRecProQuestionnaire.getDesigneeName());
            //???????????????????????????
            baseMapper.updateById(relationProQuestionnaire);
        }
        return true;
    }


}
