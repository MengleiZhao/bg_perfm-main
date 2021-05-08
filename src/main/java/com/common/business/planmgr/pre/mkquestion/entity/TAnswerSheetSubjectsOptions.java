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
 * 答卷题目表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@Data
@Accessors(chain = true)
@TableName("t_answer_sheet_subjects_options")
public class TAnswerSheetSubjectsOptions extends Model<TAnswerSheetSubjectsOptions> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_D", type= IdType.AUTO)
	private Integer idD;
	@TableField("ID_C")
	private Integer idC;
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
     * 我的答案
     */
	@TableField("ANSWER")
	private String answer;

	/**
	 * 答卷答项
	 */
	@TableField(exist = false)
	private List<TSubjectsOptionsAnswer> subjectsOptionsAnswer;
	
	@Override
	protected Serializable pkVal() {
		return this.idD;
	}

}
