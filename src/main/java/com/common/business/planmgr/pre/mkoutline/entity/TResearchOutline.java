package com.common.business.planmgr.pre.mkoutline.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 拟定调研提纲
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_research_outline")
public class TResearchOutline extends Model<TResearchOutline> {

	private static final long serialVersionUID = 1L;

	/**
	 * 拟定调研提纲 主键值
	 */
	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	/**
	 * 项目调研提纲关系表 主键值
	 */
	@TableField("ID_R")
	private Integer idR;
	/**
	 * 提纲序号
	 */
	@TableField("ORDER_NO")
	private Integer orderNo;
	/**
	 * 提纲名称
	 */
	@TableField("OUTLINE_NAME")
	private String outlineName;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
