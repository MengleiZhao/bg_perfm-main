package com.common.business.library.regulations.entity;

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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 政策法规审批附件表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
@Data
@Log
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("t_library_polocy_regulation_check_atta")
public class TLibraryPolocyRegulationCheckAtta extends Model<TLibraryPolocyRegulationCheckAtta>implements EntityDao {

    private static final long serialVersionUID = 1L;

    /**
     * 政策法规审批附件表 主键id
     */
	@TableId(value="ID_Z", type= IdType.AUTO)
	private Integer idZ;
    /**
     * 政策法规库审批表 主键id
     */
	@TableField("ID_B")
	private Integer idB;
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
		return this.idZ;
	}


	//表名
	@Override
	public String getJoinTable() {
		return "t_library_polocy_regulation_check_atta";
	}

	//实体类id
	@Override
	public Integer getEntryId() {
		return this.idZ;
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
