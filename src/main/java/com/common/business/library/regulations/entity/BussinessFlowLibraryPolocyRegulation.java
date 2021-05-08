package com.common.business.library.regulations.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 业务流程表（政策法规库审批）
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-01
 */
@Data
@Log
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("bussiness_flow_library_polocy_regulation")
public class BussinessFlowLibraryPolocyRegulation extends Model<BussinessFlowLibraryPolocyRegulation> {

    private static final long serialVersionUID = 1L;

    /**
     * 业务流程表（政策法规库审批） 主键id
     */
    @TableId(value="ID_CHE", type= IdType.AUTO)
	private Integer idChe;
    /**
     * 政策法规库 主键id
     */
	@TableField("ID_X")
	private Integer idX;
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
     * 当前节点审批顺序(1-发起人\2-一级审批人\3-二级审批人)
     */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;
    /**
     * 是否活跃( 0-不活跃[默认] \ 1-活跃 \ 仅有一个节点保持活跃)
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
     * 当前节点状态（完成状态：0-未开始  1-已完成）
     */
	@TableField("CURRENT_NODE_STATUS")
	private String currentNodeStatus;


	@Override
	protected Serializable pkVal() {
		return this.idChe;
	}

}
