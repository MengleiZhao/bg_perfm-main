package com.common.business.planmgr.indexcheck.entity;

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
 * 项目指标体系关系表
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
@Data
@Accessors(chain = true)
@TableName("relation_pro_index")
public class RelationProIndex extends Model<RelationProIndex> {

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
     * 指标设计（申请）时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 版本状态  -1-退回  0-暂存  1-审批中  2-已完成
     */
	@TableField("CREATE_STAUTS")
	private String createStauts;
    /**
     * 设计人
     */
	@TableField("CREATE_UASE_NAME")
	private String createUaseName;
	/**
	 * 设计人ID
	 */
	@TableField("CREATE_UASE_ID")
	private String createUaseId;

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
	 * 分值设置 1 - 100分
	 * 2 - 10分
	 * 3 - 其他
	 */
	@TableField("SCORE_TYPE")
	private String scoreType;

	/**
	 * 分值
	 */
	@TableField("TOTAL_SCORES")
	private String totalScores;

	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
