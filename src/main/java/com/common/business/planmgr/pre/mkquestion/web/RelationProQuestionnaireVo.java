package com.common.business.planmgr.pre.mkquestion.web;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import com.common.business.planmgr.pre.mkquestion.entity.*;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目拟定调查问卷接受对象
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-01
 */
@Data
@Accessors(chain = true)
public class RelationProQuestionnaireVo{
    
    /**
     * 项目拟定调查问卷关系表
     */
    private RelationProQuestionnaire bean;
    /**
     * 拟定调查问卷
     */
    private TQuestionnaire questionnaire; 
    /**
     * 问卷题目表
     */
    private List<TQuestionnaireSubjects> questionnaireSubjects;
    /**
     * 答卷信息表
     */
    private TQuestionnaireAnswerSheet questionnaireAnswerSheet;
    /**
     * 答卷题目表
     */
    private List<TAnswerSheetSubjectsOptions> answerSheetSubjectsOptions;
    /**
     * 子项目
     */
    private TProPerformanceInfo proPerformanceInfo;



}
