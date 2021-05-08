package com.common.business.planmgr.scheme.mkscheme.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.common.system.entity.EntityDao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 编制评价工作方案
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@Data
@Accessors(chain = true)
@TableName("t_prepar_eval_work_plan")
public class TPreparEvalWorkPlan extends Model<TPreparEvalWorkPlan> implements EntityDao {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	/**
	 * 项目编制评价工作方案关系表的外键
	 */
	@TableField("ID_R")
	private Integer idR;
	/**
	 * 方案名称
	 */
	@TableField("FILE_NAME")
	private String fileName;
	/**
	 * 存储路径
	 */
	@TableField("FILE_PATH")
	private String filePath;
	/**
	 * 文件大小
	 */
	@TableField("FILE_SIZE")
	private String fileSize;
	/**
	 * 编制时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 编制人
	 */
	@TableField("CREATEOR")
	private String createor;
	/**
	 * 编制人ID
	 */
	@TableField("CREATEOR_ID")
	private String createorId;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

	@Override
	public String getJoinTable() {
		return "t_prepar_eval_work_plan";
	}
	
	@Override
	public Integer getEntryId() {
		return idB;
	}

	/**
	 * 上传人
	 */
	@Override
	public void setCreateor(String createor) {
		this.createor=createor;
	}

	/**
	 * 上传时间
	 * @param createTime
	 */
	@Override
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}

	/**
	 * 文件名字
	 * @param fileName
	 */
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 文件路径
	 * @param filePath
	 */
	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 文件大小
	 * @param fileSize
	 */
	@Override
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}
