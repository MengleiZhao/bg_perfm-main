package com.common.business.planmgr.indexdesign.entity;

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
 * 绩效指标库模板
 * </p>
 *
 * @author 安达
 * @since 2021-04-16
 */
@Data
@Accessors(chain = true)
@TableName("t_index_system_temp")
public class TIndexSystemTemp extends Model<TIndexSystemTemp> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
    /**
     * 一级指标编码
     */
	@TableField("INDEX_CODE_1")
	private String indexCode1;
    /**
     * 一级指标名称
     */
	@TableField("INDEX_NAME_1")
	private String indexName1;
    /**
     * 二级指标编码
     */
	@TableField("INDEX_CODE_2")
	private String indexCode2;
    /**
     * 二级指标名称
     */
	@TableField("INDEX_NAME_2")
	private String indexName2;
    /**
     * 三级指标编码
     */
	@TableField("INDEX_CODE_3")
	private String indexCode3;
    /**
     * 三级指标名称
     */
	@TableField("INDEX_NAME_3")
	private String indexName3;
    /**
     * 三级指标分值
     */
	@TableField("INDEX_SCORE")
	private Double indexScore;
    /**
     * 指标解释
     */
	@TableField("INDEX_EXPLANATION")
	private String indexExplanation;


	@Override
	protected Serializable pkVal() {
		return this.idX;
	}

}
