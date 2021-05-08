package com.common.business.planmgr.scheme.mkscheme.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 绩效工作评价方案模板表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-22
 */
@Data
@Accessors(chain = true)
@TableName("t_prepar_eval_work_plan_temp")
public class TPreparEvalWorkPlanTemp extends Model<TPreparEvalWorkPlanTemp> {

    private static final long serialVersionUID = 1L;

    @TableId(value="F_HM_ID",type= IdType.AUTO)
	private Integer fHmId;
	/**
	 * 评价方案名称
	 */
	@TableField("F_PLAN_NAME")
	private String fPlanName;
	/**
	 * 存储路径
	 */
	@TableField("F_FILE_PATH")
	private String fFilePath;
	/**
	 * 文件大小
	 */
	@TableField("F_FILE_SIZE")
	private String fFileSize;
	/**
	 * 上传时间
	 */
	@TableField("F_CREATE_TIME")
	private Date fCreateTime;
	/**
	 * 上传人
	 */
	@TableField("F_CREATEOR")
	private String fCreateor;
	/**
	 * 备注
	 */
	@TableField("F_REMARK")
	private String fRemark;


	@Override
	protected Serializable pkVal() {
		return this.fHmId;
	}

}
