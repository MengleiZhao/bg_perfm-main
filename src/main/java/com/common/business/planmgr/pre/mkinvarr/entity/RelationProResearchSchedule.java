package com.common.business.planmgr.pre.mkinvarr.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.TResearchScheduleCheckRec;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 项目调研安排关系表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("relation_pro_research_schedule")
public class RelationProResearchSchedule extends Model<RelationProResearchSchedule> {

    private static final long serialVersionUID = 1L;

    /**
     * 项目调研安排关系表主键id
     */
	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
    /**
     * 项目主子表主键id
     */
	@TableField("ID_A")
	private Integer idA;
    /**
     * 版本号
     */
	@TableField("VERSION_NO")
	private String versionNo;
    /**
     * 调研安排（申请）时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 版本状态
	 * -1：退回
	 *  0：暂存
	 *  1：审批中
	 *  2：已完成
     */
	@TableField("CREATE_STAUTS")
	private String createStauts;
    /**
     * 申请人
     */
	@TableField("CREATE_UASE_NAME")
	private String createUaseName;
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
	 * “预调研”还是“编制评价方案”
	 * 0-预调研
	 * 1-编制评价方案
	 */
	@TableField("PRE_OR_SCHEME")
	private Integer preOrScheme;

	@TableField(exist = false)
	private List<TResearchSchedule> researchSchedules;//一个调研拟定关系表有多个调研安排表


	/**
	 * 一个关系表，有多个审批流数据
	 */
	@TableField(exist = false)
	private List<BussinessFlowProResearchSchedule> bussinessFlowProResearchSchedules;

	/**
	 * 一个关系表，有多个审批记录数据
	 */
	@TableField(exist=false)
	private List<TResearchScheduleCheckRec> researchScheduleCheckRecs;


	@Override
	protected Serializable pkVal() {
		return this.idR;
	}


}
