package com.common.business.planmgr.indexdesign.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * 指标设计体系专家意见表
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@TableName("t_expert_opinion_from_index_design")
public class TExpertOpinionFromIndexDesign extends Model<TExpertOpinionFromIndexDesign> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_B")
	private Integer idB;
    /**
     * 专家编码
     */
	@TableField("EXPERT_CODE")
	private String expertCode;
    /**
     * 专家姓名
     */
	@TableField("EXPERT_NAME")
	private String expertName;
    /**
     * 专家意见
     */
	@TableField("EXPERT_OPINION")
	private String expertOpinion;
    /**
     * 其他意见
     */
	@TableField("OTHER_OPINION")
	private String otherOpinion;
    /**
     * 整体评价意见
     */
	@TableField("OVERALL_COMMENTS")
	private String overallComments;
    /**
     * 问题和建议
     */
	@TableField("PROBLEMS_AND_SUGGEST")
	private String problemsAndSuggest;
    /**
     * 日期
     */
	@TableField("SUBMIT_DATE")
	private Date submitDate;


	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}
