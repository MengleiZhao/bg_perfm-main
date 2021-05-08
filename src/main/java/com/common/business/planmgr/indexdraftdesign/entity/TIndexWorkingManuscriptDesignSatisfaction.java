package com.common.business.planmgr.indexdraftdesign.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 指标底稿设计-满意度类底稿
 * </p>
 *
 * @author 安达
 * @since 2021-03-26
 */
@Data
@Accessors(chain = true)
@TableName("t_index_working_manuscript_design_satisfaction")
public class TIndexWorkingManuscriptDesignSatisfaction extends Model<TIndexWorkingManuscriptDesignSatisfaction> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_R")
	private Integer idR;
    /**
     * 评分要点活动名称
     */
	@TableField("POINTS_NAME")
	private String pointsName;
    /**
     * 评价年度
     */
	@TableField("POINTS_YEARS")
	private String pointsYears;
    /**
     * 满意度
     */
	@TableField("SATISFACTION")
	private String satisfaction;
    /**
     * 评价得分
     */
	@TableField("EVALUATION_SCORE")
	private Double evaluationScore;
    /**
     * 存在问题
     */
	@TableField("EXISTING_PROBLEMS")
	private String existingProblems;
    /**
     * 原因分析
     */
	@TableField("CAUSE_ANALYSIS")
	private String causeAnalysis;


	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}
