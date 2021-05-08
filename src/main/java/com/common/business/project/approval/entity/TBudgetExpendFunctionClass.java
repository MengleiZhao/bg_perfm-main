package com.common.business.project.approval.entity;

import java.beans.Transient;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.enums.IdType;
import com.common.system.util.tree.DataTree;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 存放绩效项目预算支出功能分类信息
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
@TableName("t_budget_expend_function_class")
public class TBudgetExpendFunctionClass extends Model<TBudgetExpendFunctionClass> implements DataTree {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "B_ID" ,type = IdType.AUTO)
	private Integer bId;
	/**
	 * 支出功能编号
	 */
	@TableField("FUNC_CODE")
	private String funcCode;
	/**
	 * 支出功能名称
	 */
	@TableField("FUNC_NAME")
	private String funcName;
	/**
	 * 上级ID
	 */
	@TableField("PARAENT_ID")
	private Integer paraentId;
	/**
	 * 备注
	 */
	@TableField("REMARK")
	private String remark;

	/**
	 * 级别
	 */
	@TableField("LEVEL")
	private Integer level;

	/**
	 * 上级名称（不在数据库中）
	 */
	@TableField(exist = false)
	private String paraentName;

	/**
	 * 不在数据库tree使用
	 */
	@TableField(exist = false)
	private List<TBudgetExpendFunctionClass> childList;

	
	@Override
	protected Serializable pkVal() {
		return this.bId;
	}


	@Override
	public Integer getId() {
		return this.bId;
	}

	@Override
	public Integer getParentId() {
		return this.paraentId;
	}

	@Override
	public void setChildList(List childList) {
		this.childList = childList;
	}

	@Override
	public List getChildList() {
		return this.childList;
	}
}
