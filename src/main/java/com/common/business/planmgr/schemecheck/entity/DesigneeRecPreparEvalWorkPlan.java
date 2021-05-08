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

/**
 * <p>
 * 指派记录表（编制评价工作方案审批）
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@Data
@Accessors(chain = true)
@TableName("designee_rec_prepar_eval_work_plan")
public class DesigneeRecPreparEvalWorkPlan extends Model<DesigneeRecPreparEvalWorkPlan> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_DESI", type= IdType.AUTO)
	private Integer idDesi;
    /**
     * 项目编制评价工作方案关系表主键
     */
	@TableField("ID_R")
	private Integer idR;
    /**
     * 指派时间
     */
	@TableField("DESIGNEE")
	private Date designee;
    /**
     * 原审批人ID
     */
	@TableField("CHECK_USER_ID")
	private String checkUserId;
    /**
     * 原审批人姓名
     */
	@TableField("CHECK_USER")
	private String checkUser;
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
     * 当前节点审批顺序
     */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;


	@Override
	protected Serializable pkVal() {
		return this.idDesi;
	}

}
