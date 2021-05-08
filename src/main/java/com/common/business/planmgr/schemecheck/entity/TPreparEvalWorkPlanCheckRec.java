package com.common.business.planmgr.schemecheck.entity;

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

import javax.validation.constraints.Size;

/**
 * <p>
 * 工作方案审批记录表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@Data
@Accessors(chain = true)
@TableName("t_prepar_eval_work_plan_check_rec")
public class TPreparEvalWorkPlanCheckRec extends Model<TPreparEvalWorkPlanCheckRec> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	/**
	 * 项目编制评价工作方案关系表主键
	 */
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
	 * 审批结果
	 1-通过（默认）
	 0-不通过（手动选择）
	 */
	@TableField("CHECK_RESULT")
	private String checkResult;
	/**
	 * 审批意见,输入审批意见（200字内）
	 */
	@Size(max = 200)
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


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
