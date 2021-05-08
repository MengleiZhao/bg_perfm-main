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
 * 填写_业务流程表（三级指标与指标底稿审批）
 * </p>
 *
 * @author 安达
 * @since 2021-04-20
 */
@Data
@Accessors(chain = true)
@TableName("bussiness_flow_index_working_manuscript_fill")
public class BussinessFlowIndexWorkingManuscriptFill extends Model<BussinessFlowIndexWorkingManuscriptFill> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_FILL", type= IdType.AUTO)
	private Integer idFill;
	@TableField("ID_D")
	private Integer idD;
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
     * 当前节点审批顺序
     */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;
    /**
     * 是否活跃
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
	@TableField("UPDATE_TIME")
	private Date updateTime;
    /**
     * 审批人工号
     */
	@TableField("CHECK_USER_JOB_NUMBER")
	private String checkUserJobNumber;
    /**
     * 当前节点状态
     */
	@TableField("CURRENT_NODE_STATUS")
	private String currentNodeStatus;

	@TableField(exist = false)
	private TIndexWorkingManuscriptFillCheckRec indexWorkingManuscriptFillCheckRec;

	@Override
	protected Serializable pkVal() {
		return this.idFill;
	}

}
