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
 * 调研安排表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_research_schedule")
public class TResearchSchedule extends Model<TResearchSchedule> {

    private static final long serialVersionUID = 1L;

    /**
     * 调研安排表主键id
     */
	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
    /**
     * 调研安排关系表主键id
     */
	@TableField("ID_R")
	private Integer idR;
    /**
     * 调研地-省
     */
	@TableField("RESEARCH_PLACE_PROVINCE")
	private String researchPlaceProvince;
    /**
     * 调研地-市
     */
	@TableField("RESEARCH_PLACE_CITY")
	private String researchPlaceCity;
    /**
     * 调研地-县
     */
	@TableField("RESEARCH_PLACE_COUNTY")
	private String researchPlaceCounty;
    /**
     * 详细地址
     */
	@TableField("DETAILED_ADDRESS")
	private String detailedAddress;
    /**
     * 调研日期
     */
	@TableField("RESEARCH_DATE")
	private Date researchDate;
    /**
     * 调研天数
     */
	@TableField("RESEARCH_DAYS")
	private Integer researchDays;
    /**
     * 具体形程
     */
	@TableField("SPECIFIC_SHAPE")
	private String specificShape;
    /**
     * 组长ID
     */
	@TableField("GROUP_LEADER_ID")
	private String groupLeaderId;
    /**
     * 组长姓名
     */
	@TableField("GROUP_LEADER")
	private String groupLeader;
    /**
     * 组员，（1,张三|2,李四）
     */
	@TableField("GROUP_MEMBERS")
	private String groupMembers;

	@TableField(exist = false)
	private String memberNames;//员工姓名，中间用|分割

	@TableField(exist = false)
	private List<String> memeberIds;//员工id集合

    /**
     * 选择依据
     */
	@TableField("SELECTION_BASIS")
	private String selectionBasis;
    /**
     * 数据来源
     */
	@TableField("DATA_SOURCES")
	private String dataSources;



	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
