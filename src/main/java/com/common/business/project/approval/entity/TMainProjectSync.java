package com.common.business.project.approval.entity;

import java.io.Serializable;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主项目信息表
 * 同步项目管理系统中项目数据信息（定时同步）
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-09
 */
@Data
@Accessors(chain = true)
@TableName("t_main_project_sync")
public class TMainProjectSync extends Model<TMainProjectSync> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID_A" ,type = IdType.AUTO)
	private Integer idA;
	/**
	 * 项目状态
	 * 0-未立项（默认）
	 * 1-已立项
	 * 注意：当项目立项暂存或提交时，将状态修改为1， 项目立项申请中将不再显示已立项的项目数据。
	 */
	@TableField("PRO_STATUS")
	private String proStatus;
	/**
	 * 主项目编号
	 * 拆分子项目时写入。
	 */
	@TableField("MAIN_PRO_CODE")
	private String mainProCode;
	/**
	 * 主项目名称
	 */
	@TableField("MAIN_PRO_NAME")
	private String mainProName;
	/**
	 * 业务类型
	 */
	@TableField("BUSSINESS_TYPE")
	private String bussinessType;
	/**
	 * 项目四级分类ID
	 */
	@TableField("PRO_LEVEL4_CLASS_ID")
	private String proLevel4ClassId;
	/**
	 *	项目四级分类
	 */
	@TableField("PRO_LEVEL4_CLASS_NAME")
	private String proLevel4ClassName;
	/**
	 *	项目负责合伙人ID
	 */
	@TableField("PRO_PARTEN_ID")
	private String proPartenId;
	/**
	 *	项目负责合伙人姓名
	 */
	@TableField("PRO_PARTEN_NAME")
	private String proPartenName;
	/**
	 *	项目经理ID
	 */
	@TableField("PRO_MANAGER_ID")
	private String proManagerId;
	/**
	 *	项目经理
	 */
	@TableField("PRO_MANAGER_NAME")
	private String proManagerName;
	/**
	 * 主项目立项时间（同步）
	 */
	@DateTimeFormat
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@TableField("MPRO_APPROVAL_TIME")
	private Date mproApprovalTime;

	
	
	@Override
	protected Serializable pkVal() {
		return this.idA;
	}

}
