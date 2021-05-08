package com.common.business.planmgr.pre.mkletter.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.common.system.entity.EntityDao;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.io.Serializable;

/**
 * <p>
 * 调研函模板表
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
@Data
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Log
@TableName("t_research_letter_temp")
public class TResearchLetterTemp extends Model<TResearchLetterTemp> implements EntityDao {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
    /**
     * 调研函文件名称
     */
	@TableField("RESEARCH_NAME")
	private String researchName;
    /**
     * 调研函内容
     */
	@TableField("RESEARCH_CONTENT")
	private String researchContent;
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
		this.researchName = fileName;
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
