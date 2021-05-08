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

import com.common.system.entity.EntityDao;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目底稿设计-其他类底稿
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@Data
@Accessors(chain = true)
@TableName("t_pro_working_manuscript_design_other")
public class TProWorkingManuscriptDesignOther extends Model<TProWorkingManuscriptDesignOther> implements EntityDao {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_R")
	private Integer idR;
    /**
     * 文件名称
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
     * 上传人ID
     */
	@TableField("CREATEOR_ID")
	private String createorId;
    /**
     * 上传人
     */
	@TableField("CREATEOR")
	private String createor;
    /**
     * 上传时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

	@Override
	public String getJoinTable() {
		return "t_pro_working_manuscript_design_other";
	}

	@Override
	public Integer getEntryId() {
		return idB;
	}

	//上传人
	@Override
	public void setCreateor(String createor) {
		this.createor=createor;
	}

	//上传时间
	@Override
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}

	//文件名字
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	//文件路径
	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	//文件大小
	@Override
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}
