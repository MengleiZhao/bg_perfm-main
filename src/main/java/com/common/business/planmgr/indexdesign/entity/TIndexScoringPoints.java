package com.common.business.planmgr.indexdesign.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 评分要点表
 * </p>
 *
 * @author 安达
 * @since 2021-03-19
 */
@Data
@Accessors(chain = true)
@TableName("t_index_scoring_points")
public class TIndexScoringPoints extends Model<TIndexScoringPoints> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_B")
	private Integer idB;
    /**
     * 评分要点
     */
	@TableField("POINTS_NAME")
	private String pointsName;
    /**
     * 要点评价开始年度
     */
	@TableField("POINTS_YEARS_1")
	private Date pointsYears1;
    /**
     * 要点评价结束年度
     */
	@TableField("POINTS_YEARS_2")
	private Date pointsYears2;
    /**
     * 要点分值
     */
	@TableField("POINTS_SCORE")
	private Double pointsScore;
    /**
     * 评分方式ID
     */
	@TableField("SCORING_METHOD_1_ID")
	private String scoringMethod1Id;
    /**
     * 评分方式
     */
	@TableField("SCORING_METHOD_1")
	private String scoringMethod1;
    /**
     * 评分方法ID
     */
	@TableField("SCORING_METHOD_2_ID")
	private String scoringMethod2Id;
    /**
     * 评分方法
     */
	@TableField("SCORING_METHOD_2")
	private String scoringMethod2;
	/**
	 * 计划产出数
	 */
	private Double plannedOutput;

	/**
	 * 上年完成值
	 */
	private Double completedValueOfLastYear;

	/**
	 * 上年完成率
	 */
	private Double completedRateOfLastYear;

	/**
	 * 上年增长率
	 */
	private Double growthRateOfLastYear;

	/**
	 * 样本总数量
	 */
	private Double totalNumberOfSamples;

	/**
	 * 扣分比例
	 */
	private Double pointsDeduPercent;

	/**
	 * 扣分分值
	 */
	private Double pointsDeducted;
    /**
     * 备注
     */
	@TableField("POINTS_REMARK")
	private String pointsRemark;

	/**
	 * 评分标准及评分规则明细 集合
	 */
	@TableField(exist = false)
	private List<TScoringStandardsAndRuilesDetail> scoringStandardsAndRuilesDetailList =new ArrayList<>();

	/**s
	 * 评分要点考核对象 集合
	 */
	@TableField(exist = false)
	private List<TAssessmentObjectByPoints> assessmentObjectByPointsList =new ArrayList<>();

	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}
