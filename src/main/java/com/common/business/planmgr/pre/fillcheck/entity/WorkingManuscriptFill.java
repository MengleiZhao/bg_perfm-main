package com.common.business.planmgr.pre.fillcheck.entity;

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
 * 指标底稿填写表
 * </p>
 *
 * @author 安达
 * @since 2021-04-22
 */
@Data
@Accessors(chain = true)
@TableName("working_manuscript_fill")
public class WorkingManuscriptFill extends Model<WorkingManuscriptFill> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_D", type= IdType.AUTO)
	private Integer idD;
	@TableField("ID_R")
	private Integer idR;
    /**
     * 底稿（填写）状态
     */
	@TableField("WORKING_PAPER_FILL_STAUTS")
	private String workingPaperFillStauts;
    /**
     * 底稿填写时间
     */
	@TableField("WORKING_PAPER_FILL_TIME")
	private Date workingPaperFillTime;
    /**
     * 底稿填写人
     */
	@TableField("WORKING_PAPER_FILL_USER_NAME")
	private String workingPaperFillUserName;
    /**
     * 底稿填写人ID
     */
	@TableField("WORKING_PAPER_FILL_USER_ID")
	private String workingPaperFillUserId;
	/**
	 * 当前审批人ID
	 */
	@TableField("CURR_CHECK_ID")
	private String currCheckId;
	/**
	 * 当前审批人姓名
	 */
	@TableField("CURR_CHECK_NAME")
	private String currCheckName;

	@Override
	protected Serializable pkVal() {
		return this.idD;
	}

}
