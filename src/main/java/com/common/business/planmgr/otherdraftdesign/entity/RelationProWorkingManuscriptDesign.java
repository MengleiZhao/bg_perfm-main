package com.common.business.planmgr.otherdraftdesign.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * 项目底稿关系表
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@Data
@Accessors(chain = true)
@TableName("relation_pro_working_manuscript_design")
public class RelationProWorkingManuscriptDesign extends Model<RelationProWorkingManuscriptDesign> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	@TableField("ID_A")
	private Integer idA;
    /**
     * 版本号
     */
	@TableField("VERSION_NO")
	private String versionNo;
    /**
     * 底稿设计（申请）时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 其他底稿类型 1：问题建议类底稿，2：调研总结类底稿，3：其他类底稿
	 */
	@TableField("INDEX_WORKING_PAPER_TYPE")
	private String indexWorkingPaperType;
    /**
     * 0-暂存1-审批中2-已完成
     */
	@TableField("CREATE_STAUTS")
	private String createStauts;
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


	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
