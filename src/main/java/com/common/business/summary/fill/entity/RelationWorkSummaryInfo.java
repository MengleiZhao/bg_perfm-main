package com.common.business.summary.fill.entity;

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

import com.common.business.summary.fillcheck.entity.BussinessFlowWorkSummaryInfo;
import com.common.business.summary.fillcheck.entity.TWorkSummaryCheckRec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 项目编写工作总结关系表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("relation_work_summary_info")
public class RelationWorkSummaryInfo extends Model<RelationWorkSummaryInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 项目编写工作总结关系表 主键值
     */
	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
    /**
     * 主子项目主键值
     */
	@TableField("ID_A")
	private Integer idA;
    /**
     * 版本号
     */
	@TableField("VERSION_NO")
	private String versionNo;
    /**
     * 工作总结（申请）时间
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
	 * 工作总结表
	 */
	@TableField(exist = false)
	private TWorkSummaryInfo workSummaryInfo;

	/**
	 * 一个关系表对应多个业务流数据
	 */
	@TableField(exist = false)
	private List<BussinessFlowWorkSummaryInfo> bussinessFlowWorkSummaryInfos;

	/**
	 * 一个关系表对应多个审批记录数据
	 */
	@TableField(exist = false)
	private List<TWorkSummaryCheckRec> workSummaryCheckRecs;

	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}
