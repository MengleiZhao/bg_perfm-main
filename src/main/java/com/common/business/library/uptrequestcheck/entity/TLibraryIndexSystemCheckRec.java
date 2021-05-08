package com.common.business.library.uptrequestcheck.entity;

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
 * 绩效指标库审批表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_library_index_system_check_rec")
public class TLibraryIndexSystemCheckRec extends Model<TLibraryIndexSystemCheckRec> {

    private static final long serialVersionUID = 1L;

    /**
     * 绩效指标库审批表 主键值
     */
	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
    /**
     * 绩效指标库主键值
     */
	@TableField("ID_X")
	private Integer idX;
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
     * 审批意见
输入审批意见（200字内）
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
