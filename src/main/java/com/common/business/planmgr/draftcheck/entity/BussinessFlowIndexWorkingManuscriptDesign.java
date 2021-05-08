package com.common.business.planmgr.draftcheck.entity;

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
 * 业务流程表（三级指标与指标底稿审批）
 * </p>
 *
 * @author 安达
 * @since 2021-03-30
 */
@Data
@Accessors(chain = true)
@TableName("bussiness_flow_index_working_manuscript_design")
public class BussinessFlowIndexWorkingManuscriptDesign extends Model<BussinessFlowIndexWorkingManuscriptDesign> {

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
     * 当前节点审批顺序
     */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;
    /**
     * 是否活跃，0-不活跃（默认）1-活跃   仅有一个节点保持活跃
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

	@TableField(exist = false)
	private TIndexWorkingManuscriptDesignCheckRec indexWorkingManuscriptDesignCheckRec;

	@Override
	protected Serializable pkVal() {
		return this.idChe;
	}

}
