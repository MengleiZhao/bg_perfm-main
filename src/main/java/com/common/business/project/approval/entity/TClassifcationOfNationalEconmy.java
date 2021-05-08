package com.common.business.project.approval.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.enums.IdType;
import com.common.system.util.tree.DataTree;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 存放绩效项目国民经济分类信息
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
@TableName("t_classifcation_of_national_econmy")
public class TClassifcationOfNationalEconmy extends Model<TClassifcationOfNationalEconmy> implements DataTree {

    private static final long serialVersionUID = 1L;

	/**
	 *主键
	 */
    @TableId(value = "C_ID",type = IdType.AUTO)
	private Integer cId;
	/**
	 * 分类代码
	 */
	@TableField("CLASS_CODE")
	private String classCode;
	/**
	 * 类别名称
	 */
	@TableField("CLASS_NAME")
	private String className;
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
		return this.cId;
	}

	@Override
	public Integer getId() {
		return this.cId;
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
