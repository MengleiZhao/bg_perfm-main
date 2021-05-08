package com.common.business.planmgr.pre.mkletter.entity;

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

import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>
 * 项目拟定调研函关系表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("relation_pro_research_letter")
public class RelationProResearchLetter extends Model<RelationProResearchLetter> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	@NotNull(message = "请上传项目主键id")
	@TableField("ID_A")
	private Integer idA;
	/**
	 * 版本号
	 */
	@TableField("VERSION_NO")
	private String versionNo;
	/**
	 * 调研函（申请）时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 版本状态:
	 * -1-退回
	 * 0-暂存
	 * 1-审批中
	 * 2-已完成
	 */
	@NotNull(message = "请上传版本状态")
	@TableField("CREATE_STAUTS")
	private String createStauts;
	/**
	 * 申请人
	 */
	@TableField("CREATE_UASE_NAME")
	private String createUaseName;
	/**
	 * 申请人id
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
	 * 预调研还是编制评价方案
	 * 0-预调研
	 * 1-编制评价方案
	 */
	@Pattern(regexp = "[01]")
	@NotNull(message = "预调研还是编制评价方案不允许为空")
	@TableField("PRE_OR_SCHEME")
	private Integer preOrScheme;
	
	/**
	 * 子项目类
	 */
	@TableField(exist = false)
	private TProPerformanceInfo proPerformanceInfo;
	/**
	 * 拟定调研函
	 */
	@TableField(exist = false)
	private TResearchLetter researchLetter;

	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
