package com.common.business.project.approval.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import com.common.business.workgroup.taskmgr.entity.TPerformanceTaskAllocation;
import com.common.business.workgroup.taskmgr.entity.TResearchLetterGzz;
import com.common.business.workgroup.taskmgr.entity.TResearchOutlineGzz;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 绩效项目信息表（主子项目）
 * 存放绩效项目的立项信息
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-08
 */
@Log
@Getter
@Setter
@ToString
@Data
@ExcelTarget("proPerformanceInfo")
@Accessors(chain = true)
@TableName("t_pro_performance_info")
public class TProPerformanceInfo extends Model<TProPerformanceInfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value="ID_A", type= IdType.AUTO)
	private Integer idA;
	/**
	 *项目状态
	 * 0-暂存
	 * 1-已立项
	 * 2-已完结
	 * 9-删除
	 */
	@Excel(name = "状态" ,replace = {"暂存_0","已立项_1","已完结_2"} ,isImportField = "true_st",orderNum = "9")
	@TableField("PRO_STATUS")
	private String proStatus;
	/**
	 *子项目编号
	 */
	@Excel(name = "子项目编号" , isImportField = "true_st",orderNum = "4")
	@TableField("PRO_CODE")
	private String proCode;
	/**
	 *子项目名称
	 */
	@Excel(name = "子项目名称" , isImportField = "true_st",orderNum = "5")
	@TableField("PRO_NAME")
	private String proName;
	/**
	 *项目负责合伙人ID
	 */
	@TableField("PRO_PARTEN_ID")
	private String proPartenId;
	/**
	 *项目负责合伙人姓名
	 */
	@TableField("PRO_PARTEN_NAME")
	private String proPartenName;
	/**
	 *项目经理ID
	 */
	@TableField("PRO_MANAGER_ID")
	private String proManagerId;
	/**
	 *项目经理
	 */
	@TableField("PRO_MANAGER_NAME")
	private String proManagerName;
	/**
	 *是否拆分子项目
	 * 0-不拆分（默认）
	 * 1-拆分
	 */
	@NotNull
	@Pattern(regexp = "[01]")
	@TableField("PRO_ISDISMANT")
	private String proIsdismant;
	/**
	 *是否涉密项目
	 * 0-不涉密（默认）
	 * 1-涉密
	 */
	@NotNull
	@Pattern(regexp = "[01]")
	@TableField("PRO_IS_SECRET")
	private String proIsSecret;
	/**
	 *项目秘书ID
	 */
	@TableField("PRO_SECRETARY_ID")
	private String proSecretaryId;
	/**
	 *项目秘书
	 */
	@TableField("PRO_SECRETARY_NAME")
	private String proSecretaryName;
	/**
	 *项目独立复核人ID
	 */
	@TableField("PRO_INDEP_REVIEW_ID")
	private String proIndepReviewId;
	/**
	 *项目独立复核人
	 */
	@TableField("PRO_INDEP_REVIEW_NAME")
	private String proIndepReviewName;
	/**
	 *预算支出功能分类ID
	 */
	@TableField("BUD_FUNCT_CLASS_ID")
	private String budFunctClassId;
	/**
	 *预算支出功能分类
	 */
	@Excel(name = "预算支出功能分类" , isImportField = "true_st", orderNum="6")
	@TableField("BUD_FUNCT_CLASS_NAME")
	private String budFunctClassName;
	/**
	 *项目四级分类ID
	 */
	@TableField("PRO_LEVEL4_CLASS_ID")
	private String proLevel4ClassId;
	/**
	 *项目四级分类
	 */
	@NotNull(message = "请填写项目四级分类")
	@TableField("PRO_LEVEL4_CLASS_NAME")
	private String proLevel4ClassName;
	/**
	 *国民经济行业分类ID
	 */
	@TableField("NATION_ECO_INDUST_CLASS_ID")
	private String nationEcoIndustClassId;
	/**
	 *国民经济行业分类
	 */
	@Excel(name = "国民经济行业分类" , isImportField = "true_st",orderNum = "7")
	@TableField("NATION_ECO_INDUST_CLASS_NAME")
	private String nationEcoIndustClassName;
	/**
	 *风险等级
	 * A
	 * B
	 * C
	 */
	@Excel(name = "风险级别" , isImportField = "true_st",orderNum = "8")
	@TableField("RISK_LEVEL")
	private String riskLevel;
	/**
	 *主项目编号
	 * 拆分子项目时写入。
	 */
	@Excel(name = "主项目编号" , isImportField = "true_st" , needMerge = true,orderNum = "2")
	@TableField("PARENT_PRO_CODE")
	private String parentProCode;
	/**
	 *主项目名称
	 * 拆分子项目时写入。
	 */
	@Excel(name = "主项目名称" , isImportField = "true_st" , needMerge = true,orderNum = "3")
	@TableField("PARENT_PRO_NAME")
	private String parentProName;

	/**
	 *业务类型id
	 */
	@TableField("BUSSINESS_TYPE_ID")
	private String bussinessTypeId;

	/**
	 *业务类型
	 */
	@TableField("BUSSINESS_TYPE")
	private String bussinessType;

	/**
	 * 评价对象
	 * 0-单位名称（默认）
	 * 1-二级项目名称
	 */
	@TableField(value = "EVALUATION_OBJ")
	private String evaluationObj;

	/**
	 *是否已组建工作组
	 * Y-是，组建工作组完成后更新为Y
	 * N-否（默认）
	 */
	@TableField("IS_SETUP_WORKING_GROUP")
	private String isSetupWorkingGroup;

	/**
	 *是否已分配任务
	 * Y-分配完毕，组建工作组完成后更新为Y
	 * Z-分配中，如果是暂存，则更新未Z
	 * N-否（默认）
	 */
	@TableField("IS_TASK_ASSIGNED")
	private String isTaskAssigned;


	/**
	 *项目基本情况
	 * 绩效任务分配阶段写入
	 */
	@TableField("PRO_BASE_INFO")
	private String proBaseInfo;
	/**
	 *绩效评价目标
	 * 绩效任务分配阶段写入
	 */
	@TableField("PER_EVAL_OBJ")
	private String perEvalObj;
	/**
	 *工作要求
	 * 绩效任务分配阶段写入
	 */
	@TableField("JOB_REQUIREMENTS")
	private String jobRequirements;
	/**
	 *其他
	 * 绩效任务分配阶段写入
	 */
	@TableField("OTHER_DESC")
	private String otherDesc;
	/**
	 *立项时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@TableField("PRO_APPROVAL_TIME")
	private Date proApprovalTime;
	/**
	 *立项人
	 */
	@TableField("PRO_APPROVALER")
	private String proApprovaler;
	/**
	 *修改时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@TableField("UPDATE_TIME")
	private Date updateTime;
	/**
	 *修改人
	 */
	@TableField("UPDATER")
	private String updater;
	/**
	 *是否已组建专家组
	 * 0-未组建（默认）
	 * 1-已组建
	 * 注意：当专家组组建后，需修改本状态为1
	 */
	@TableField("EXPERT_GROUP_ISFORMED")
	private String expertGroupIsformed;
	/**
	 *资料清单审核状态
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("DEVELOPMENT_STATUS")
	private String developmentStatus;
	/**
	 *指标体系设计审核状态
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("INDEX_DSEIGN_STATUS")
	private String indexDseignStatus;
	/**
	 *工作底稿设计审核状态
	 * 工作底稿设计 = 指标底稿设计 + 项目底稿设计
	 */
	@TableField("WORKING_DESIGN_STATUS")
	private String workingDesignStatus;
	/**
	 *调研安排审核状态（预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("RESEARCH_SCHEDULE_STATUS_Y")
	private String researchScheduleStatusY;
	/**
	 *调研安排审核状态（非预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("RESEARCH_SCHEDULE_STATUS_C")
	private String researchScheduleStatusC;
	/**
	 *调研提纲审核状态（预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("RESEARCH_OUTLINE_STATUS_Y")
	private String researchOutlineStatusY;
	/**
	 *调研提纲审核状态（非预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("RESEARCH_OUTLINE_STATUS_C")
	private String researchOutlineStatusC;
	/**
	 *调研函审核状态（预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("RESEARCH_LETTER_STATUS_Y")
	private String researchLetterStatusY;
	/**
	 *调研函审核状态（非预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("RESEARCH_LETTER_STATUS_C")
	private String researchLetterStatusC;
	/**
	 *调查问卷审核状态（预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("QUES_CHECK_STATUS_Y")
	private String quesCheckStatusY;
	/**
	 *调查问卷审核状态（非预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("QUES_CHECK_STATUS_C")
	private String quesCheckStatusC;
	/**
	 *工作底稿填写审核状态（预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("WORKING_WRITE_STATUS_Y")
	private String workingWriteStatusY;
	/**
	 *工作底稿填写审核状态（非预调研）
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("WORKING_WRITE_STATUS_C")
	private String workingWriteStatusC;
	/**
	 *评价工作方案审核状态
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("PERPAR_WORKPLAN_STATUS")
	private String perparWorkplanStatus;
	/**
	 *评价结论审核状态
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("PRELIM_EVAL_CONC_STATUS")
	private String prelimEvalConcStatus;
	/**
	 *评价报告审核状态
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("EVAL_REPORT_STATUS")
	private String evalReportStatus;
	/**
	 *工作总结审核状态
	 * 0-未审核（默认）含审批中
	 * 1-已审核
	 */
	@TableField("WORK_SUMMARY_STATUS")
	private String workSummaryStatus;
	/**
	 *项目档案分类
	 * 1-财政部项目
	 * 2-其他项目
	 */
	@TableField("ARCHIVES_CLASS")
	private String archivesClass;
	/**
	 *项目档案归档审核状态
	 * 0-未归档（默认）
	 * 1-暂存
	 * 2-审核中
	 * 3-退回/撤回
	 * 9-已完成
	 */
	@TableField("ARCHIVES_CLASS_STATUS")
	private String archivesClassStatus;


	/**
	 * 序号
	 */
	@Excel(name = "序号",orderNum = "0")
	@TableField(exist = false)
	private Integer num;

	/**
	 * 被评价单位集合json
	 */
	@TableField(exist = false)
	private List<TEvalUnitInfo> evalUnitInfoList;


	@TableField(exist = false)
	private List<TProPerformanceInfo> proPerformanceInfolist;
	
	
	
	
	/*@TableField(exist = false)
	private String tEvalUnitInfoList;
	
	@TableField(exist = false)
	private String tProPerformanceInfolist;*/


	/**
	 * 主项目id
	 */
	@TableField(exist = false)
	private Integer idMainA;

	/**
	 * 数据库不存在字段   工作组集合
	 */
	@TableField(exist = false)
	private  List<TPerformanceWorkingGroup> performanceWorkingGroupList;
	/**
	 * 数据库不存在字段   绩效评价调研函（工作组）
	 */
	@TableField(exist = false)
	private   TResearchLetterGzz  researchLetterGzz;
	/**
	 * 数据库不存在字段   绩效评价提纲（组建工作组）
	 */
	@TableField(exist = false)
	private   List<TResearchOutlineGzz>  researchOutlineGzzList;
	/**
	 * 数据库不存在字段   工作任务树列表
	 */
	@TableField(exist = false)
	private  List<TPerformanceTaskAllocation> taskAllocationTrees;

	/**
	 *数据库不存在字段   组建工作组时间
	 */
	@TableField(exist = false)
	private transient Date workingGroupTime;
	/**
	 *数据库不存在字段   工作分配时间
	 */
	@TableField(exist = false)
	private transient Date assignTasksTime;
	/**
	 *数据库不存在字段   任务分配人
	 */
	@TableField(exist = false)
	private String assignTaskstor;

	/*==组建工作组-->组建专家组  所需要的属性字段    Author:田鑫艳     [开始]====================================================*/
	@TableField(exist = false)
	private TPerformanceWorkingGroup performanceWorkingGroup;//绩效工作组 实体类，用于主页面显示时的一对一映射和查询外勤主管
	@TableField(exist = false)
	private List<TEvalUnitInfo> evalUnitInfos;//被评单位信息 实体类，用于“详情”中的“项目信息”里的被评单位 查询
	@TableField(exist = false)
	private List<TPerformanceWorkingGroup> performanceWorkingGroups;//绩效工作组 实体类，用于“详情”中的“工作组信息”里的 组员信息 查询
	@TableField(exist = false)
	private List<TPerformanceExpertGroupInfo> performanceExpertGroupInfos;//专家组信息

	/*==组建工作组-->组建专家组  所需要的属性字段    Author:田鑫艳     [结束]====================================================*/

	/*==组建工作组-->工作组台账  所需要的属性字段    Author:田鑫艳     [开始]====================================================*/
	@TableField(exist = false)
	private Integer memberNum;//成员数量：包含 普通成员和专家成员
	@TableField(exist = false)
	private Date workCreateTime;//组建工作组的时间(组建专家组：专家组的最晚时间；工作组的台账：工作组跟专家组的最晚时间)

	/*==组建工作组-->工作组台账  所需要的属性字段    Author:田鑫艳     [结束]====================================================*/

	/**
	 *数据库不存在字段   项目外勤主管姓名
	 */
	@TableField(exist = false)
	private String groupMemberName;
	/**
	 *数据库不存在字段   项目外勤主管组员id
	 */
	@TableField(exist = false)
	private String groupMemberId;
	/**
	 *数据库不存在字段   项目外勤主管编号
	 */
	@TableField(exist = false)
	private String groupMemberCode;

	/**
	 *数据库不存在字段   设计人
	 */
	@TableField(exist = false)
	private String systemDseigntor;
	/**
	 *数据库不存在字段  设计时间
	 */
	@TableField(exist = false)
	private Date systemDseignTime;
	/**
	 *数据库不存在字段   设计状态   0-暂存   1-审批中   2-已审批
	 */
	@TableField(exist = false)
	private String systemDseignStatus;

	/*==资料收集上传   所需要的属性字段    Author:田鑫艳     [开始]====================================================*/
	@TableField(exist = false)
	private Integer idR;//资料清单拟定ID_R  资料清单关系表ID_R
	@TableField(exist = false)
	private String informationVersionNO;//版本
	/**
	 * 0-未上传
	 * 1-已上传
	 * 2-已认证
	 */
	@TableField(exist = false)
	private Integer informationISEnd;
	@TableField(exist = false)
	private Integer fileCertificISEnd;//资料审核-->审核状态  1 已审核，2待审核
	@TableField(exist = false)
	private String fileUpdateor;//资料审核，上传人姓名=拟定清单中的拟定人姓名
	@TableField(exist = false)
	private Integer fileUpdateNum;//资料上传个数
	/*==资料收集上传  所需要的属性字段    Author:田鑫艳     [结束]====================================================*/


	@TableField(exist = false)
	private RelationProResearchSchedule relationProResearchSchedule;//拟定调研安排-->项目调研安排关系表
	@TableField(exist = false)
	private String versionNO;//拟定调研安排-->版本(后面有版本的也可以使用这个字段)
	@TableField(exist = false)
	private Date createTime;//拟定调研安排-->拟定时间 (申请时间)
	@TableField(exist = false)
	private String createStauts;//拟定调研安排-->版本状态（ -1 退回 0-暂存 1-审批中 2-已完成）





	@Override
	protected Serializable pkVal() {
		return this.idA;
	}

}
