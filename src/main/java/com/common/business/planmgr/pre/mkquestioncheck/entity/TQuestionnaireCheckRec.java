package com.common.business.planmgr.pre.mkquestioncheck.entity;

import java.io.Serializable;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 调查问卷审批记录表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("t_questionnaire_check_rec")
public class TQuestionnaireCheckRec extends Model<TQuestionnaireCheckRec> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@NotNull(message = "项目拟定调查问卷关系表的idR不准为空")
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
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CHECK_TIME")
	private Date checkTime;
	/**
	 * 审批结果
	 1-通过（默认）
	 0-不通过（手动选择）
	 */
	@TableField("CHECK_RESULT")
	private String checkResult;
	/**
	 * 审批意见,输入审批意见（200字内）
	 */
	@TableField("CHECK_OPINION")
	private String checkOpinion;
	/**
	 * 备注
	 如转办，系统自动备注
	 “XXX时间 由 XX 转 XX 承办”
	 */
	@TableField("REMARK")
	private String remark;
	/**
	 * 审批数据状态
	 0-历史数据
	 1-本次数据
	 */
	@TableField("CHECK_DATA_STATUS")
	private String checkDataStatus;
	/**
	 * 当前节点审批顺序
	 */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;
	
	/**
	 * 指派记录表（拟定调查问卷审批）
	 */
	@TableField(exist = false)
	private DesigneeRecProQuestionnaire designeeRecProQuestionnaire;

	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
