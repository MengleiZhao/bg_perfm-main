package com.common.business.planmgr.otherdraftdesign.entity;

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
 * 指派记录表（项目与指标底稿审批）
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@Data
@Accessors(chain = true)
@TableName("designee_rec_pro_working_manuscript_design")
public class DesigneeRecProWorkingManuscriptDesign extends Model<DesigneeRecProWorkingManuscriptDesign> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_DESI", type= IdType.AUTO)
	private Integer idDesi;
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
