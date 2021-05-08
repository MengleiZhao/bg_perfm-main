package com.common.business.planmgr.indexcheck.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 指标设计审批记录表
 * </p>
 *
 * @author 安达
 * @since 2021-03-30
 */
@Data
@Accessors(chain = true)
@TableName("t_index_system_dseign_check_rec")
public class TIndexSystemDseignCheckRec extends Model<TIndexSystemDseignCheckRec> {

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
     * 审批结果
     */
	@TableField("CHECK_RESULT")
	private String checkResult;
    /**
     * 审批意见
     */
	@TableField("CHECK_OPINION")
	private String checkOpinion;
    /**
     * 备注
     */
	@TableField("REMARK")
	private String remark;
	/**
	 * 审批数据状态
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


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
