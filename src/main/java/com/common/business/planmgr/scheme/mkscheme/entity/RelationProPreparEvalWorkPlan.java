package com.common.business.planmgr.scheme.mkscheme.entity;

import java.io.Serializable;

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
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 项目编制评价工作方案关系表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@Data
@Accessors(chain = true)
@TableName("relation_pro_prepar_eval_work_plan")
public class RelationProPreparEvalWorkPlan extends Model<RelationProPreparEvalWorkPlan> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	/**
	 * 外键
	 */
	@TableField("ID_A")
	private Integer idA;
	/**
	 * 版本号
	 */
	@TableField("VERSION_NO")
	private String versionNo;
	/**
	 * 编制评价工作方案（申请）时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 版本状态
	 -1-退回
	 0-暂存
	 1-审批中
	 2-已完成
	 */
	@TableField("CREATE_STAUTS")
	private String createStauts;
	/**
	 * 申请人
	 */
	@TableField("CREATE_UASE_NAME")
	private String createUaseName;	
	/**
	 * 申请人ID
	 */
	@TableField("CREATE_UASE_ID")
	private String createUaseId;
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
	 * 项目信息
	 */
	@TableField(exist = false)
	private TProPerformanceInfo proPerformanceInfo;
	
	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
