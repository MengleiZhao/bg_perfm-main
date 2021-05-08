package com.common.business.planmgr.pre.mkquestion.service.impl;

import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.common.business.planmgr.pre.mkquestion.entity.TQuestionnaire;
import com.common.business.planmgr.pre.mkquestion.mapper.TQuestionnaireMapper;
import com.common.business.planmgr.pre.mkquestion.service.TQuestionnaireService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 调查问卷 服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Service
@Transactional
public class TQuestionnaireServiceImpl extends ServiceImpl<TQuestionnaireMapper, TQuestionnaire> implements TQuestionnaireService {

    
    @Override
    public Boolean releaseQuestionnaire(Integer id) {
        TQuestionnaire questionnaire = baseMapper.selectById(id);
        questionnaire.setQuesStatus("1");
        questionnaire.setQuesReleaseTime(new Date());
        baseMapper.updateById(questionnaire);
        return true;
    }

    
    @Override
    public Boolean closeQuestionnaire(Integer id) {
        TQuestionnaire questionnaire = baseMapper.selectById(id);
        questionnaire.setQuesStatus("9");
        baseMapper.updateById(questionnaire);
        return true;
    }


}
