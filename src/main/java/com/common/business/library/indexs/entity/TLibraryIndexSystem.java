package com.common.business.library.indexs.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.ArrayList;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 绩效指标库
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_library_index_system")
public class TLibraryIndexSystem extends Model<TLibraryIndexSystem> {

    private static final long serialVersionUID = 1L;

    /**
     * 绩效指标库 主键值
     */
	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
    /**
     * 指标类别，0-请选择
1-共性
2-个性
     */
	@TableField("INDEX_TYPE")
	private String indexType;
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
	@TableField("INDEX_SCORE_3")
	private Double indexScore3;
    /**
     * 指标解释(三级)
     */
	@TableField("INDEX_EXPLANATION")
	private String indexExplanation;
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
    /**
     * 数据权限
1-公开
2-私有
     */
	@TableField("DATA_RIGHTS")
	private String dataRights;
    /**
     * 数据状态
-1 退回
 0-暂存
1-审批中
2-已审批

     */
	@TableField("DATA_STAUTS")
	private String dataStauts;
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
     * 申请人ID
     */
	@TableField("APPLICANT_ID")
	private String applicantId;
    /**
     * 申请人
     */
	@TableField("APPLICANT_NAME")
	private String applicantName;
    /**
     * 申请时间
     */
	@TableField("APPLY_TIME")
	private Date applyTime;
    /**
     * 申请原因
     */
	@TableField("APPLY_DESC")
	private String applyDesc;
    /**
     * 调整类型
		1-出库申请
		2-入库申请
		3-修改申请
     */
	@TableField("UPT_TYPE")
	private String uptType;
    /**
     * 当前审批人ID
     */
	@TableField("CURR_CHECK_ID")
	private String currCheckId;
    /**
     * 当前审批人姓名
     */
	@TableField("CURR_CHECK_NAME")
	private String currCheckName;
    /**
     * 发布时间
     */
	@TableField("RELEASE_TIME")
	private Date releaseTime;

	/**
	 * 子集合
	 */
	@TableField(exist = false)
	private List<TLibraryIndexSystem> childrenList=new ArrayList<>();

	/**
	 * 用于 前端展示 发布时间去掉  时:分:秒
	 */
	@TableField(exist = false)
	private String releaseDate;

	/**
	 * 用于前端精确查询发布时间==》开始时间
	 */
	@TableField(exist = false)
	private String releaseStartTime;
	/**
	 * 用于前端精确查询发布时间==》结束时间
	 */
	@TableField(exist = false)
	private String releaseEndTime;
	/**
	 * 用于前端精确查询申请时间==》开始时间
	 */
	@TableField(exist = false)
	private String applyStartTime;
	/**
	 * 用于前端精确查询申请时间==》结束时间
	 */
	@TableField(exist = false)
	private String applyEndTime;

	@Override
	protected Serializable pkVal() {
		return this.idX;
	}

}
