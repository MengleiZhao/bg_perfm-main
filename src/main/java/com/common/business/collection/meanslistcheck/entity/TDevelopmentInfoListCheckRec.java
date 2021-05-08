package com.common.business.collection.meanslistcheck.entity;

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
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 *	资料清单审批记录表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@TableName("t_development_info_list_check_rec")
public class TDevelopmentInfoListCheckRec extends Model<TDevelopmentInfoListCheckRec> {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	/**
	 * 外键（RelationProList表的主键idR）
	 */
	@NotNull(message = "请上传RelationProList表的主键idR")
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
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CHECK_TIME")
	private Date checkTime;
	/**
	 * 审批结果:
	 * 1-通过（默认）
	 0-不通过（手动选择）
	 */
	@NotNull(message = "请选择审批结果")
	@TableField("CHECK_RESULT")
	private String checkResult;
	/**
	 * 审批意见:输入审批意见（200字内）
	 */
	@Size(max = 200,message = "审批意见（200字内）")
	@TableField("CHECK_OPINION")
	private String checkOpinion;
	/**
	 * 审批数据状态
	 * 0-历史数据
	 * 1-本次数据
	 */
	@Size(max = 2,message = "审批数据状态不允许超过1个字段")
	@TableField("CHECK_DATA_STATUS")
	private String checkDataStatus;
	/**
	 * 当前审批记录对应流程节点
	 */
	@TableField("ORDER_OF_CURRENT_NODE")
	private Integer orderOfCurrentNode;
	/**
	 * 备注:如转办，系统自动备注
	 “XXX时间 由 XX 转 XX 承办”
	 */
	@TableField("REMARK")
	private String remark;
	/**
	 * 保存是否转派信息
	 */
	@TableField(exist = false)
	private BussinessFlowProList bussinessFlowPro;

	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
