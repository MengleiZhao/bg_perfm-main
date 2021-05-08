package com.common.business.planmgr.otherdraftdesign.entity;

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
 * 项目底稿设计-调研总结类底稿
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@Data
@Accessors(chain = true)
@TableName("t_pro_working_manuscript_design_research")
public class TProWorkingManuscriptDesignResearch extends Model<TProWorkingManuscriptDesignResearch> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_R")
	private Integer idR;
	/**
	 * 部门（单位）
	 */
	@TableField("UNIT_NAME")
	private String unitName;
	/**
	 * 部门（地区）ID
	 */
	@TableField("UNIT_NAME_ID")
	private String unitNameId;
    /**
     * 对被调研地项目必要性的看法
     */
	@TableField("SATISFACTION")
	private String satisfaction;
    /**
     * 对被调研地项目实施效果的评价
     */
	@TableField("EFFECT_EVALUATION")
	private String effectEvaluation;
    /**
     * 对项目组织实施的建议
     */
	@TableField("SUGGEST_ON_PROJECT")
	private String suggestOnProject;
    /**
     * 其他
     */
	@TableField("OTHER_CONTENT")
	private String otherContent;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
