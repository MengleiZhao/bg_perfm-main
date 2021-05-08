package com.common.business.planmgr.pre.mkquestion.entity;

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
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 拟定调查问卷
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("t_questionnaire")
public class TQuestionnaire extends Model<TQuestionnaire> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	/**
	 * 外键（项目拟定调查问卷关系表主键）
	 */
	@TableField("ID_R")
	private Integer idR;
	/**
	 * 问卷ID
	 * 9位数字组成
	 * 规则为：年份后两位 + 月份 + 日期 + 三位随机数
	 */
	@TableField("QUES_ID")
	private String quesId;
	/**
	 * 问卷标题
	 */
	@TableField("QUES_TITLE")
	private String quesTitle;
	/**
	 * 问卷说明
	 */
	@TableField("QUES_DESC")
	private String quesDesc;
	/**
	 * 问卷状态
	 0-未发布
	 1-已发布
	 9-已关闭
	 */
	@TableField("QUES_STATUS")
	private String quesStatus;
	/**
	 * 答卷数统计
	 */
	@TableField("TOTAL_ANSWER")
	private Integer totalAnswer;
	/**
	 * 问卷创建时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("QUES_CREATE_TIME")
	private Date quesCreateTime;
	/**
	 * 问卷创建人
	 */
	@TableField("QUES_CREATEOR")
	private String quesCreateor;
	/**
	 * 问卷发布时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("QUES_RELEASE_TIME")
	private Date quesReleaseTime;
	/**
	 * 是否时间控制
	 0-否（默认）
	 1-是
	 */
	@TableField("IS_TIME_CONTROL")
	private String isTimeControl;
	/**
	 * 问卷开始时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("QUES_START_TIME")
	private Date quesStartTime;
	/**
	 * 问卷结束时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("QUES_END_TIME")
	private Date quesEndTime;
	/**
	 * 是否设置答题密码
	 0-否（默认）
	 1-是
	 */
	
	@TableField("IS_SET_PASSWORD")
	private String isSetPassword;
	/**
	 * 答题密码
	 */
	@TableField("PASSWORD")
	private String password;
	/**
	 * 提交后显示语
	 */
	@TableField("END_TIP")
	private String endTip;
	/**
	 * 作答次数限制
	 1/1 每个用户只允许填写一次 
	 1/N 每个用户每天可以填写一次
	 */
	@TableField("NUM_OF_ANSWER")
	private Integer numOfAnswer;
	/**
	 * 已作答次数
	 */
	@TableField("TOTAL_OF_ANSWER")
	private Integer totalOfAnswer;
	/**
	 * 是否允许断点续答
	 0-否（默认）
	 1-是
	 */
	@TableField("IS_ALLOW_CONTINUE")
	private String isAllowContinue;
	/**
	 * 是否允许提交前预览答卷
	 0-否（默认）
	 1-是
	 */
	@TableField("IS_ALLOW_PREVIEW")
	private String isAllowPreview;
	/**
	 * 问卷链接（二维码）
	 */
	@TableField("URL_FOR_2DCODR")
	private String urlFor2dcodr;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
