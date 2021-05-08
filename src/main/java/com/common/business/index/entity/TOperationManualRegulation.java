package com.common.business.index.entity;

import java.beans.Transient;
import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.common.system.entity.EntityDao;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 操作手册及业规管理
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Data
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Log
@TableName("t_operation_manual_regulation")
public class TOperationManualRegulation extends Model<TOperationManualRegulation> implements EntityDao {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
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
     * 文件大小,单位：MB
     */
	@TableField("FILE_SIZE")
	private String fileSize;
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


	private transient  String beginTime;

	private transient  String endTime;

	@Override
	protected Serializable pkVal() {
		return this.idX;
	}

	@Override
	public String getJoinTable() {
		return "t_operation_manual_regulation";
	}

	@Override
	public Integer getEntryId() {
		return this.idX;
	}

	public void setIdX(Integer idX) {
		this.idX = idX;
	}
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Override
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public void setCreateor(String createor) {
		this.createor = createor;
	}
	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
