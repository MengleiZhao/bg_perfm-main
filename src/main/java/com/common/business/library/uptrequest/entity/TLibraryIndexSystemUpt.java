package com.common.business.library.uptrequest.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * 绩效指标库修改申请表
用于存放修改申请 的数据
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_library_index_system_upt")
public class TLibraryIndexSystemUpt extends Model<TLibraryIndexSystemUpt> {

    private static final long serialVersionUID = 1L;

    /**
     * 绩效指标库修改申请表 主键值
     */
	@TableId(value="ID_U", type= IdType.AUTO)
	private Integer idU;
    /**
     * 绩效指标库 主键值
     */
	@TableField("ID_X")
	private Integer idX;
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
     * 指标评价开始年度
     */
	@TableField("INDEX_YEARS_1")
	private Date indexYears1;
    /**
     * 指标评价结束年度
     */
	@TableField("INDEX_YEARS_2")
	private Date indexYears2;
    /**
     * 三级指标分值
     */
	@TableField("INDEX_SCORE_3")
	private Double indexScore3;
    /**
     * 指标解释（三级）
     */
	@TableField("INDEX_EXPLANATION")
	private String indexExplanation;
    /**
     * 数据来源
1-项目
2-非项目
     */
	@TableField("DATA_SOURCES")
	private String dataSources;
    /**
     * 备注
     */
	@TableField("INDEX_REMARK")
	private String indexRemark;
    /**
     * 预算支出功能分类ID
     */
	@TableField("BUD_FUNCT_CLASS_ID")
	private String budFunctClassId;
    /**
     * 预算支出功能分类
     */
	@TableField("BUD_FUNCT_CLASS_NAME")
	private String budFunctClassName;
    /**
     * 国民经济行业分类ID
     */
	@TableField("NATION_ECO_INDUST_CLASS_ID")
	private String nationEcoIndustClassId;
    /**
     * 国民经济行业分类
     */
	@TableField("NATION_ECO_INDUST_CLASS_NAME")
	private String nationEcoIndustClassName;


	@Override
	protected Serializable pkVal() {
		return this.idU;
	}

}
