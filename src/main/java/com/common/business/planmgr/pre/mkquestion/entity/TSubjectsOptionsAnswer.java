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
 * 答卷答项表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("t_subjects_options_answer")
public class TSubjectsOptionsAnswer extends Model<TSubjectsOptionsAnswer> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_E", type= IdType.AUTO)
	private Integer idE;
	@TableField("ID_D")
	private Integer idD;
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
		return this.idE;
	}

}
