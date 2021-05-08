package com.common.business.workgroup.taskmgr.entity;

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
 * 绩效评价提纲表（组建工作组）
 * </p>
 *
 * @author 安达
 * @since 2021-03-11
 */
@Data
@Accessors(chain = true)
@TableName("t_research_outline_gzz")
public class TResearchOutlineGzz extends Model<TResearchOutlineGzz> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_A")
	private Integer idA;
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
