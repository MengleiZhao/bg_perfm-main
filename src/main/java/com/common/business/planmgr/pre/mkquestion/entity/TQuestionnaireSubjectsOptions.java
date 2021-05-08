package com.common.business.planmgr.pre.mkquestion.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 *	问卷答项表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("t_questionnaire_subjects_options")
public class TQuestionnaireSubjectsOptions extends Model<TQuestionnaireSubjectsOptions> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_D", type= IdType.AUTO)
	private Integer idD;
	@TableField("ID_C")
	private Integer idC;
	/**
	 * 答项排列方式
	 T1-按大写字母ABCD排列
	 T2-按大写数字”一、二、三、四“排列
	 T3-按大写罗马数字排列I ,II ,III,IV ,V ,VI,VII ,VIII ,IX,X
	 */
	@TableField("OPTION_ORDER_TYPE")
	private String optionOrderType;
	/**
	 * 答项排列号
	 排列号默认拼上括号。例：
	 （A）内容 
	 （B） 内容

	 （一）内容
	 （二）内容

	 （I）内容
	 （II）内容
	 */
	@TableField("OPTION_ORDER_NO")
	private String optionOrderNo;
	/**
	 * 答项内容
	 */
	@TableField("OPTION_CONTENT")
	private String optionContent;


	@Override
	protected Serializable pkVal() {
		return this.idD;
	}

}
