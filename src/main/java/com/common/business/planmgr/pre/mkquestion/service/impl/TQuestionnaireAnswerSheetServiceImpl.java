package com.common.business.planmgr.pre.mkquestion.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.pre.mkquestion.entity.TAnswerSheetSubjectsOptions;
import com.common.business.planmgr.pre.mkquestion.entity.TQuestionnaire;
import com.common.business.planmgr.pre.mkquestion.entity.TQuestionnaireAnswerSheet;
import com.common.business.planmgr.pre.mkquestion.entity.TSubjectsOptionsAnswer;
import com.common.business.planmgr.pre.mkquestion.mapper.TAnswerSheetSubjectsOptionsMapper;
import com.common.business.planmgr.pre.mkquestion.mapper.TQuestionnaireAnswerSheetMapper;
import com.common.business.planmgr.pre.mkquestion.mapper.TSubjectsOptionsAnswerMapper;
import com.common.business.planmgr.pre.mkquestion.service.TQuestionnaireAnswerSheetService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mkquestion.web.RelationProQuestionnaireVo;
import com.common.system.exception.ServiceException;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Service
public class TQuestionnaireAnswerSheetServiceImpl extends ServiceImpl<TQuestionnaireAnswerSheetMapper, TQuestionnaireAnswerSheet> implements TQuestionnaireAnswerSheetService {

    @Autowired
    private TAnswerSheetSubjectsOptionsMapper answerSheetSubjectsOptionsMapper;
    @Autowired
    private TSubjectsOptionsAnswerMapper subjectsOptionsAnswerMapper;
    

    /**
     * 保存答卷信息
     * @param beanVo:前台接受数据
     * @param user:当前登录人
     * @return
     */
    @Override
    public Boolean saveAnswerSheet(RelationProQuestionnaireVo beanVo, ShiroUser user){
        //答卷信息表
        TQuestionnaireAnswerSheet questionnaireAnswerSheet = beanVo.getQuestionnaireAnswerSheet();
        //答卷题目表和答项信息
        List<TAnswerSheetSubjectsOptions> answerSheetSubjectsOptionsList = beanVo.getAnswerSheetSubjectsOptions();
        //调查问卷
        TQuestionnaire questionnaire = beanVo.getQuestionnaire();
        if (null == questionnaireAnswerSheet.getIdC()){
            //新增
            questionnaireAnswerSheet.setIdB(questionnaire.getIdB());
            if (StringUtils.isEmpty(questionnaireAnswerSheet.getSubmitUserId())){
                questionnaireAnswerSheet.setSubmitUserId(user.getId().toString());
                questionnaireAnswerSheet.setSubmitUserName(user.getName());
            }
            baseMapper.insert(questionnaireAnswerSheet);
            //答卷题目
            for (int i = 0; i < answerSheetSubjectsOptionsList.size(); i++) {
                TAnswerSheetSubjectsOptions answerSheetSubjectsOptions = answerSheetSubjectsOptionsList.get(i);
                answerSheetSubjectsOptions.setIdC(questionnaireAnswerSheet.getIdC());
                answerSheetSubjectsOptionsMapper.insert(answerSheetSubjectsOptions);
                //答卷答项
                List<TSubjectsOptionsAnswer> subjectsOptionsAnswerList = answerSheetSubjectsOptions.getSubjectsOptionsAnswer();
                for (int j = 0; j < subjectsOptionsAnswerList.size(); j++) {
                    TSubjectsOptionsAnswer subjectsOptionsAnswer = subjectsOptionsAnswerList.get(j);
                    subjectsOptionsAnswer.setIdD(answerSheetSubjectsOptions.getIdD());
                    subjectsOptionsAnswerMapper.insert(subjectsOptionsAnswer);
                }
            }
        }else {
            //修改
            TQuestionnaireAnswerSheet oldquestionnaireAnswerSheet = baseMapper.selectById(questionnaireAnswerSheet.getIdC());
            //删除原来数据
            EntityWrapper  answerSheetEntityWrapper = new EntityWrapper();
            answerSheetEntityWrapper.eq("ID_C",questionnaireAnswerSheet.getIdC());
            answerSheetEntityWrapper.orderBy("ID_C",true);
            List<TAnswerSheetSubjectsOptions> oldanswerSheetSubjectsOptions = answerSheetSubjectsOptionsMapper.selectList(answerSheetEntityWrapper);
            for (TAnswerSheetSubjectsOptions oldanswerSheetSubjectsOption : oldanswerSheetSubjectsOptions) {
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_D",oldanswerSheetSubjectsOption.getIdD());
                List<TSubjectsOptionsAnswer> oldsubjectsOptionsAnswerList = subjectsOptionsAnswerMapper.selectList(entityWrapper);
                for (TSubjectsOptionsAnswer tSubjectsOptionsAnswer : oldsubjectsOptionsAnswerList) {
                    subjectsOptionsAnswerMapper.deleteById(tSubjectsOptionsAnswer.getIdE());
                }
                answerSheetSubjectsOptionsMapper.deleteById(oldanswerSheetSubjectsOption.getIdD());
            }
            //设置状态
            oldquestionnaireAnswerSheet.setAnswerStatus(questionnaireAnswerSheet.getAnswerStatus());
            questionnaireAnswerSheet = oldquestionnaireAnswerSheet;
            //答卷题目
            for (int i = 0; i < answerSheetSubjectsOptionsList.size(); i++) {
                TAnswerSheetSubjectsOptions answerSheetSubjectsOptions = answerSheetSubjectsOptionsList.get(i);
                answerSheetSubjectsOptions.setIdC(questionnaireAnswerSheet.getIdC());
                answerSheetSubjectsOptionsMapper.insert(answerSheetSubjectsOptions);
                //答卷答项
                List<TSubjectsOptionsAnswer> subjectsOptionsAnswerList = answerSheetSubjectsOptions.getSubjectsOptionsAnswer();
                for (int j = 0; j < subjectsOptionsAnswerList.size(); j++) {
                    TSubjectsOptionsAnswer subjectsOptionsAnswer = subjectsOptionsAnswerList.get(j);
                    subjectsOptionsAnswer.setIdD(answerSheetSubjectsOptions.getIdD());
                    subjectsOptionsAnswerMapper.insert(subjectsOptionsAnswer);
                }
            }
        }
        if ("1".equals(questionnaireAnswerSheet.getAnswerStatus())){
            questionnaireAnswerSheet.setSubmitTime(new Date());
            baseMapper.updateById(questionnaireAnswerSheet);
        }

        return true;
    }

    @Override
    public PageInfo<TQuestionnaireAnswerSheet> closeQuestionnaire(Integer pageNum, Integer pageSize, String search, TQuestionnaireAnswerSheet questionnaireAnswerSheet, ShiroUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<TQuestionnaireAnswerSheet> list = baseMapper.closeQuestionnaire(pageNum,pageSize,search,questionnaireAnswerSheet,user);
        return new PageInfo<TQuestionnaireAnswerSheet>(list);
    }


}
