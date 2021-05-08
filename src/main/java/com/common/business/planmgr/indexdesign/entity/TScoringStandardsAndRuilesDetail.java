package com.common.business.planmgr.indexdesign.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 评分标准及评分规则明细表
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@TableName("t_scoring_standards_and_ruiles_detail")
public class TScoringStandardsAndRuilesDetail extends Model<TScoringStandardsAndRuilesDetail> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_E", type= IdType.AUTO)
	private Integer idE;
	@TableField("ID_C")
	private Integer idC;
    /**
     * 指标完成率1
     */
	@TableField("INDEX_COMP_RATE1")
	private Double indexCompRate1;
    /**
     * 指标完成率2
     */
	@TableField("INDEX_COMP_RATE2")
	private Double indexCompRate2;
    /**
     * 分值1
     */
	@TableField("SCORE1")
	private Double score1;
    /**
     * 增长比例1
     */
	@TableField("GROWTH_RATIO1")
	private Double growthRatio1;
    /**
     * 增长比例2
     */
	@TableField("GROWTH_RATIO2")
	private Double growthRatio2;
    /**
     * 分值2
     */
	@TableField("SCORE2")
	private Double score2;
    /**
     * 偏差率1
     */
	@TableField("DEVIATION_RATE1")
	private Double deviationRate1;
    /**
     * 偏差率2
     */
	@TableField("DEVIATION_RATE2")
	private Double deviationRate2;
    /**
     * 分值3
     */
	@TableField("SCORE3")
	private Double score3;

	/**
	 * 档位
	 */
	@TableField("gear_level")
	private String gearLevel;

	/**
	 * 档位得分
	 */
	@TableField("score4")
	private Double score4;

	/**
	 * 区间扣分比例
	 */
	@TableField("points_dedu_percent")
	private Double pointsDeduPercent;

	/**
	 * 区间扣分分值
	 */
	@TableField("score5")
	private Double score5;

	@Override
	protected Serializable pkVal() {
		return this.idE;
	}

}
