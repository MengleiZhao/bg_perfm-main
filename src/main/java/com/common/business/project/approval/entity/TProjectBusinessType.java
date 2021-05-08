package com.common.business.project.approval.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import com.common.system.util.tree.DataTree;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-18
 */
@Data
@Accessors(chain = true)
@TableName("t_project_business_type")
public class TProjectBusinessType extends Model<TProjectBusinessType> implements DataTree {

    private static final long serialVersionUID = 1L;

	@TableField("ID_X")
	private Integer idX;
	@TableField("BUSS_NAME")
	private String bussName;
	@TableField("BUSS_LEAVEL")
	private String bussLeavel;
	@TableField("PARENT_ID")
	private String bussparentId;
	
	@TableField(exist = false)
	private List<TProjectBusinessType> childList;
	


	@Override
	protected Serializable pkVal() {
		return this.idX;
	}

	@Override
	public Integer getId() {
		return this.idX;
	}

	@Override
	public Integer getParentId() {
		return Integer.valueOf(this.bussparentId);
	}

	@Override
	public void setChildList(List childList) {
		this.childList = childList;
	}


}
