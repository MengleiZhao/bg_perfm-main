package com.common.business.library.regulations.entity;

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

import com.common.system.entity.EntityDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 政策法规附件表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_library_polocy_regulation_atta")
public class TLibraryPolocyRegulationAtta extends Model<TLibraryPolocyRegulationAtta> implements EntityDao {

    private static final long serialVersionUID = 1L;

    /**
     * 政策法规附件表 主键值
     */
	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
    /**
     * 政策法规主键值
     */
	@TableField("ID_X")
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



	//表名
	@Override
	public String getJoinTable() {
		return "t_library_polocy_regulation";
	}

	//实体类id
	@Override
	public Integer getEntryId() {
		return this.idC;
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
