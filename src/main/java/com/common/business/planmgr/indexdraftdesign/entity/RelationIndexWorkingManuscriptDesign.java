package com.common.business.planmgr.indexdraftdesign.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 三级指标与指标底稿关系表
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
@Data
@Accessors(chain = true)
@TableName("relation_index_working_manuscript_design")
public class RelationIndexWorkingManuscriptDesign extends Model<RelationIndexWorkingManuscriptDesign> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	@TableField("ID_B")
	private Integer idB;
    /**
     * 版本号
     */
	@TableField("VERSION_NO")
	private String versionNo;
    /**
     * 指标底稿设计（申请）时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 版本状态 -1：退货，0：暂存，1：审批中，2：已完成
     */
	@TableField("CREATE_STAUTS")
	private String createStauts;
    /**
     * 指标工作底稿类型 1-常规类工作底稿 2-满意度类工作底稿 3-其他类工作底稿
     */
	@TableField("INDEX_WORKING_PAPER_TYPE")
	private String indexWorkingPaperType;
    /**
     * 指标工作底稿填报状态
     */
	@TableField("INDEX_WORKING_PAPER_STAUTS")
	private String indexWorkingPaperStauts;
    /**
     * 设计人
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
	 * 存在问题
	 */
	@TableField(exist = false)
	private String existingProblems;


	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
