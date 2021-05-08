package com.common.business.library.uptrequest.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("bussiness_flow_library_index_system")
public class BussinessFlowLibraryIndexSystem extends Model<BussinessFlowLibraryIndexSystem> {

    private static final long serialVersionUID = 1L;

    /**
     * 业务流程表（绩效指标库审批） 主键值
     */
	@TableId(value="ID_CHE", type= IdType.AUTO)
	private Integer idChe;
    /**
     * 绩效指标库 主键值
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
     * 当前节点审批顺序
     */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;
    /**
     * 是否活跃
0-不活跃（默认）
1-活跃
仅有一个节点保持活跃
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
0-未开始
1-已完成
     */
	@TableField("CURRENT_NODE_STATUS")
	private String currentNodeStatus;


	@Override
	protected Serializable pkVal() {
		return this.idChe;
	}

}
