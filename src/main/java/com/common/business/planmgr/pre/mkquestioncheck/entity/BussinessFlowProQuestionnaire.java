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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *	业务流程表（拟定调查问卷审批）
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("bussiness_flow_pro_questionnaire")
public class BussinessFlowProQuestionnaire extends Model<BussinessFlowProQuestionnaire> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_CHE", type= IdType.AUTO)
	private Integer idChe;
	@TableField("ID_R")
	private Integer idR;
	/**
	 * 审批人ID
	 */
	@TableField("CHECK_USER_ID")
	private String checkUserId;
	/**
	 * 审批人姓名
	 */
	@TableField("CHECK_USER")
	private String checkUser;
	/**
	 * 审批人部门ID
	 */
	@TableField("CHECK_USER_DEPT_ID")
	private String checkUserDeptId;
	/**
	 * 审批人部门名称
	 */
	@TableField("CHECK_USER_DEPT_NAME")
	private String checkUserDeptName;
	/**
	 * 被转派人ID
	 */
	@TableField("DESIGNEE_ID")
	private String designeeId;
	/**
	 * 被转派人姓名
	 */
	@TableField("DESIGNEE_NAME")
	private String designeeName;
	/**
	 * 被指派人部门ID
	 */
	@TableField("DESIGNEE_DEPT_ID")
	private String designeeDeptId;
	/**
	 * 被指派人部门名称
	 */
	@TableField("DESIGNEE_DEPT_NAME")
	private String designeeDeptName;
	/**
	 * 被指派人工号
	 */
	@TableField("DESIGNEE_JOB_NUMBER")
	private String designeeJobNumber;
	/**
	 * 当前节点审批顺序
	 */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;
	/**
	 * 是否活跃
	 * 	 0-不活跃（默认）
	 * 	 1-活跃
	 * 	 仅有一个节点保持活跃
	 */
	@TableField("NODE_IS_ACTIVE")
	private String nodeIsActive;
	/**
	 * 创建人
	 */
	@TableField("CREATEOR")
	private String createor;
	/**
	 * 创建时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 修改人
	 */
	@TableField("UPDATEOR")
	private String updateor;
	/**
	 * 修改时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("UPDATE_TIME")
	private Date updateTime;
	/**
	 * 审批人工号
	 */
	@TableField("CHECK_USER_JOB_NUMBER")
	private String checkUserJobNumber;
	/**
	 * 当前节点状态
	 0-未开始
	 1-已完成
	 */
	@TableField("CURRENT_NODE_STATUS")
	private String currentNodeStatus;

	/**
	 * 审批时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField(exist = false)
	private Date checkTime;
	/**
	 * 审批结果:
	 * 1-通过（默认）
	 0-不通过（手动选择）
	 */
	@TableField(exist = false)
	private String checkResult;
	/**
	 * 审批意见:输入审批意见（200字内）
	 */
	@TableField(exist = false)
	private String checkOpinion;
	/**
	 * 备注:如转办，系统自动备注
	 * “XXX时间 由 XX 转 XX 承办”
	 */
	@TableField(exist = false)
	private String remark;
	
	@Override
	protected Serializable pkVal() {
		return this.idChe;
	}

}
