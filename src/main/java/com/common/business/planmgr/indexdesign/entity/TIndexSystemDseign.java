package com.common.business.planmgr.indexdesign.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 指标体系设计表
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@TableName("t_index_system_dseign")
public class  TIndexSystemDseign extends Model<TIndexSystemDseign> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_R")
	private Integer idR;
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
	 */
	@TableField("DATA_SOURCES")
	private String dataSources;
	/**
	 * 备注
	 */
	@TableField("INDEX_REMARK")
	private String indexRemark;
	/**
	 * 创建人ID
	 */
	@TableField("CREATEOR_ID")
	private String createorId;
	/**
	 * 创建时间
	 */
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 修改人ID
	 */
	@TableField("UPDATEOR_ID")
	private String updateorId;
	/**
	 * 修改时间
	 */
	@TableField("UPDATE_TIME")
	private Date updateTime;
	/**
	 * 指标评价得分
	 */
	@TableField("EVALUATION_SCORE")
	private Double evaluationScore;
	/**
	 * 底稿设计人
	 */
	@TableField("ASSESSMENT_OBJECT_ID")
	private String assessmentObjectId;
	/**
	 * 底稿设计人ID
	 */
	@TableField("ASSESSMENT_OBJECT")
	private String assessmentObject;
	/**
	 * 底稿填写人
	 */
	@TableField("FILL_USER_NAME")
	private String fillUserName;
	/**
	 * 底稿填写人ID
	 */
	@TableField("FILL_USER_ID")
	private String fillUserId;

	/**
	 * 指标说明
	 */
	@TableField(exist = false)
	private String  scoringPoints;
	/**
	 * 要点分值
	 */
	@TableField(exist = false)
	private Double  pointsScore;
	/**
	*评价标准及平分规则
	 */
	@TableField(exist = false)
	private String  pointsRule;
	/**
	 *存在问题
	 */
	@TableField(exist = false)
	private String  existingProblems;
	/**
	 *底稿设计状态
	 */
	@TableField(exist = false)
	private String  status;


	/**
	 * 预算支出功能分类
	 */
	@TableField(exist = false)
	private String budFunctClassName;

	/**
	 * 国民经济行业分类
	 */
	@TableField(exist = false)
	private String nationEcoIndustClassName;

	/**
	 * 指标类别，0-请选择  1-共性  2-个性
	 */
	@TableField(exist = false)
	private String indexType;




	/**
	 * 考核对象集合
	 */
	@TableField(exist = false)
	private List<TAssessmentObjectByIndex> assessmentObjectList;
	/**
	 * 佐证材料池集合
	 */
	@TableField(exist = false)
	private List<TEvidencePools> evidencePoolsList;
	/**
	 * 评分要点集合
	 */
	@TableField(exist = false)
	private List<TIndexScoringPoints> scoringPointsList;
	/**
	 * 绩效指标对象
	 */
	@TableField(exist = false)
	private TIndexSystemDseign systemDseign;
	/**
	 * 分值设置
	 */
	@TableField(exist = false)
	private String scoreType;
	/**
	 * 设计人
	 */
	@TableField(exist = false)
	private String createUaseName;
	/**
	 * 设计人ID
	 */
	@TableField(exist = false)
	private String createUaseId;

	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
