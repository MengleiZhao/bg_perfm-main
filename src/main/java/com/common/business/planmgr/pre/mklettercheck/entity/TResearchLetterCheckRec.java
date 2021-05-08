package com.common.business.planmgr.pre.mklettercheck.entity;

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

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 调研函审批记录表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
@Data
@Accessors(chain = true)
@TableName("t_research_letter_check_rec")
public class TResearchLetterCheckRec extends Model<TResearchLetterCheckRec> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_R")
	private Integer idR;
	/**
	 * 审批人ID
	 */
	@TableField("CHECK_USER_ID")
	private String checkUserId;
	/**
	 * 审批人
	 */
	@TableField("CHECK_USER")
	private String checkUser;
	/**
	 * 审批时间
	 */
	@TableField("CHECK_TIME")
	private Date checkTime;
	/**
	 * 审批结果:
	 * 1-通过（默认）
	 * 0-不通过（手动选择）
	 */
	@NotNull
	@TableField("CHECK_RESULT")
	private String checkResult;
	/**
	 * 审批意见:输入审批意见（200字内）
	 */
	@TableField("CHECK_OPINION")
	private String checkOpinion;
	/**
	 * 备注
	 */
	@TableField("REMARK")
	private String remark;
	/**
	 * 审批数据状态:
	 * 0-历史数据
	 * 1-本次数据
	 */
	@TableField("CHECK_DATA_STATUS")
	private String checkDataStatus;
	/**
	 * 当前节点审批顺序
	 */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;

	/**
	 * 指派记录
	 */
	@TableField(exist = false)
	private DesigneeRecProResearchLetter designeeRecProResearchLetter;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
