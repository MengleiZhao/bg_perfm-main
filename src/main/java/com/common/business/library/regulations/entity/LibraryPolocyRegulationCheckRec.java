package com.common.business.library.regulations.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 政策法规库审批表
入库、出库、查阅 审批记录表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_library_polocy_regulation_check_rec")
public class LibraryPolocyRegulationCheckRec extends Model<LibraryPolocyRegulationCheckRec> {

	private static final long serialVersionUID = 1L;

	/**
	 * 政策法规库审批记录表 主键id值
	 */
	@TableId("ID_B")
	private Integer idB;
	/**
	 * 政策法规表 主键id值
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
	 * 1-通过（默认）
	 * 0-不通过（手动选择）
	 */
	@TableField("CHECK_RESULT")
	private String checkResult;
	/**
	 * 审批意见
	 * 输入审批意见（200字内）
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

	@TableField(exist = false)
	private MultipartFile file;//审批时上传的附件

	@TableField(exist = false)
	private TLibraryPolocyRegulationCheckAtta libraryPolocyRegulationCheckAtta;//一个审批记录表可能有一个审批附件表

	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
