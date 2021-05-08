package com.common.business.report.fill.entity;

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
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目编写评价报告关系表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-22
 */
@Data
@Accessors(chain = true)
@TableName("relation_eval_report_info")
public class RelationEvalReportInfo extends Model<RelationEvalReportInfo> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	/**
	 * 项目关联外检
	 */
	@TableField("ID_A")
	private Integer idA;
	/**
	 * 版本号
	 */
	@TableField("VERSION_NO")
	private String versionNo;
	/**
	 * 评价报告（申请）时间
	 */
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
	 * 项目信息
	 */
	@TableField(exist = false)
	private TProPerformanceInfo proPerformanceInfo;

	
	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
