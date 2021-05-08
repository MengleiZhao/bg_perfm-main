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
 * 项目底稿设计-问题建议类底稿
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@Data
@Accessors(chain = true)
@TableName("t_pro_working_manuscript_design_suggest")
public class TProWorkingManuscriptDesignSuggest extends Model<TProWorkingManuscriptDesignSuggest> {

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
     * 经验做法
     */
	@TableField("EXPERIENCE_AND_PRACTICE")
	private String experienceAndPractice;
    /**
     * 存在的问题困难
     */
	@TableField("EXISTING_PROBLEMS")
	private String existingProblems;
    /**
     * 有关建议
     */
	@TableField("SUGGEST_FOR_PROBLEMS")
	private String suggestForProblems;
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
