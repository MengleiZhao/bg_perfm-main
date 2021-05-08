package com.common.business.planmgr.pre.mkoutline.entity;

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

import com.common.business.planmgr.pre.mkoutlinecheck.entity.BussinessFlowProResearchOutline;
import com.common.business.planmgr.pre.mkoutlinecheck.entity.TResearchOutlineCheckRec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目调研提纲关系表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("relation_pro_research_outline")
public class RelationProResearchOutline extends Model<RelationProResearchOutline> {

	private static final long serialVersionUID = 1L;

	/**
	 * 项目调研提纲关系表 主键值
	 */
	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	/**
	 * 主子项目 主键值
	 */
	@TableField("ID_A")
	private Integer idA;
	/**
	 * 版本号
	 */
	@TableField("VERSION_NO")
	private String versionNo;
	/**
	 * 调研提纲（申请）时间
	 */
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 版本状态
	 -1-退回
	 0-暂存
	 1-审批中
	 2-已完成
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
	 0-预调研
	 1-编制评价方案
	 */
	@TableField("PRE_OR_SCHEME")
	private String preOrScheme;

	/**
	 * 一个关系表对应多个调研提纲
	 */
	@TableField(exist = false)
	private List<TResearchOutline> researchOutlines;

	/**
	 * 一个关系表对应多个审批业务数据
	 */
	@TableField(exist = false)
	private List<BussinessFlowProResearchOutline> bussinessFlowProResearchOutlines;

	/**
	 * 一个关系表对应多个审批记录数据
	 */
	@TableField(exist = false)
	private List<TResearchOutlineCheckRec> researchOutlineCheckRecs;

	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
