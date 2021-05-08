package com.common.business.planmgr.pre.mkquestion.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 答卷信息表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("t_questionnaire_answer_sheet")
public class TQuestionnaireAnswerSheet extends Model<TQuestionnaireAnswerSheet> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_B")
	private Integer idB;
	/**
	 * 答卷状态
	 0-未答卷
	 1-暂存
	 2-已提交
	 */
	@TableField("ANSWER_STATUS")
	private String answerStatus;
	/**
	 * 答卷人ID
	 */
	@TableField("SUBMIT_USER_ID")
	private String submitUserId;
	/**
	 * 答卷人姓名
	 */
	@TableField("SUBMIT_USER_NAME")
	private String submitUserName;
	/**
	 * 单位名称
	 */
	@TableField("SUBMIT_UNIT_NAME")
	private String submitUnitName;
	/**
	 * 提交时间
	 */
	@TableField("SUBMIT_TIME")
	private Date submitTime;
	/**
	 * 绑定微信账号
	 */
	@TableField("WECHAT_ACCOUNT")
	private String wechatAccount;
	/**
	 * 手机号
	 */
	@TableField("MOBILE_TEL")
	private String mobileTel;


	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}
