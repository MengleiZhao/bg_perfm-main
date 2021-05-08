package com.common.business.planmgr.pre.mkquestion.entity;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 项目拟定调查问卷关系表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("relation_pro_questionnaire")
public class RelationProQuestionnaire extends Model<RelationProQuestionnaire> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	/**
	 * 外键（项目表主键）
	 */
	@NotNull
	@TableField("ID_A")
	private Integer idA;
	/**
	 * 版本号
	 */
	@TableField("VERSION_NO")
	private String versionNo;
	/**
	 * 问卷名称
	 */
	@TableField("QUES_NAME")
	private String quesName;
	/**
	 * 调查问卷（申请）时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 版本状态
	 * -1-退回
	 * 0-暂存
	 * 1-审批中
	 * 2-已完成
	 */
	@TableField("CREATE_STAUTS")
	private String createStauts;
	/**
	 * 申请人ID
	 */
	@TableField("CREATE_UASE_ID")
	private String createUaseId;
	/**
	 * 申请人
	 */
	@TableField("CREATE_UASE_NAME")
	private String createUaseName;
	/**
	 * 当前审批人ID
	 */
	@TableField("CURR_CHECK_ID")
	private String currCheckId;
	/**
	 * 当前审批人姓名
	 */
	@TableField("CURR_CHECK_NAME")
	private String currCheckName;
	/**
	 * 单据状态
	 * 1-正常
	 * 9-删除
	 */
	@TableField("RELATION_STATUS")
	private String relationStatus;
	/**
	 * 预调研还是编制评价方案
	 * 0-预调研
	 * 1-编制评价方案
	 */
	@NotNull(message = "预调研还是编制评价方案不允许为空")
	@TableField("PRE_OR_SCHEME")
	private Integer preOrScheme;
	
	/**
	 * 子项目
	 */
	@ExcelEntity()
	@TableField(exist = false)
	private TProPerformanceInfo proPerformanceInfo;

	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
