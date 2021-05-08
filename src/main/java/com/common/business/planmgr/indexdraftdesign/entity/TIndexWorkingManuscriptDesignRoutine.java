package com.common.business.planmgr.indexdraftdesign.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 指标底稿设计-常规类底稿
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@TableName("t_index_working_manuscript_design_routine")
public class TIndexWorkingManuscriptDesignRoutine extends Model<TIndexWorkingManuscriptDesignRoutine> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_R")
	private Integer idR;
    /**
     * 工作底稿编码
     */
	@TableField("MANUSCRIPT_CODE")
	private String manuscriptCode;
    /**
     * 评价期间年度数
     */
	@TableField("EVALUATION_PERIOD")
	private Integer evaluationPeriod;
    /**
     * 部门（地区）
     */
	@TableField("UNIT_NAME")
	private String unitName;
	/**
	 * 部门（地区）ID
	 */
	@TableField("UNIT_NAME_ID")
	private String unitNameId;
    /**
     * 评分要点
     */
	@TableField("KEY_POINTS_DESC")
	private String keyPointsDesc;
    /**
     * 合计，1-包含（默认） 2-不包含
     */
	@TableField("TOTAL")
	private Integer total;
    /**
     * 存在问题，满意度类工作底稿使用
     */
	@TableField("EXISTING_PROBLEMS")
	private String existingProblems;
    /**
     * 原因分析，满意度类工作底稿使用
     */
	@TableField("CAUSE_ANALYSIS")
	private String causeAnalysis;


	@TableField(exist = false)
	private Map<String,List<TIndexWorkingManuscriptPoints>> yearIndexScoringPointsMap= new HashMap<>();

	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}
