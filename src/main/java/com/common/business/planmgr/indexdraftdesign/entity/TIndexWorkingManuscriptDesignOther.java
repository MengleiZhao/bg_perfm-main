package com.common.business.planmgr.indexdraftdesign.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.common.system.entity.EntityDao;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 指标底稿设计-其他类底稿
 * </p>
 *
 * @author 安达
 * @since 2021-03-26
 */
@Data
@Accessors(chain = true)
@TableName("t_index_working_manuscript_design_other")
public class TIndexWorkingManuscriptDesignOther extends Model<TIndexWorkingManuscriptDesignOther>  implements EntityDao {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
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
		return this.idC;
	}

	@Override
	public String getJoinTable() {
		return "t_index_working_manuscript_design_other";
	}

	@Override
	public Integer getEntryId() {
		return idC;
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
