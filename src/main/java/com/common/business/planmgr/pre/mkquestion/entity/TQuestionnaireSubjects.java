package com.common.business.planmgr.pre.mkquestion.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 问卷题目表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("t_questionnaire_subjects")
public class TQuestionnaireSubjects extends Model<TQuestionnaireSubjects> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_B")
	private Integer idB;
	/**
	 * 题目类型
	 X1-投票单选
	 X2-投票多选
	 X3-普通单选
	 X4-普通多选
	 X5-填空题
	 X6-矩阵填空
	 X7-文件上传
	 */
	@TableField("SUB_TYPE")
	private String subType;
	/**
	 * 题目序号
	 */
	@TableField("SUB_NO")
	private Integer subNo;
	/**
	 * 题目内容
	 */
	@TableField("SUB_CONTENT")
	private String subContent;
	/**
	 * 是否限制答题时间
	 0-否（默认）
	 1-是
	 当选择”是“，程序判断”答题时限“并在页面倒计时控制答题时间。
	 */
	@TableField("IS_CONTROL_TIME")
	private String isControlTime;
	/**
	 * 答题时限（秒）
	 */
	@TableField("TIME_LIMIT")
	private Integer timeLimit;
	/**
	 * 是否开放统计,题目类型为投票单选 或 投票多选时 设置
	 * 0-否（默认）
	 * 1-是
	 */
	@TableField("IS_OPEN_STATISTICS")
	private String isOpenStatistics;

	/**
	 * 问卷答项表
	 */
	@TableField(exist = false)
	private List<TQuestionnaireSubjectsOptions> questionnaireSubjectsOptions;
	
	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}
