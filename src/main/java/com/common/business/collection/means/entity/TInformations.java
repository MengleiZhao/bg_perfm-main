package com.common.business.collection.means.entity;

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
 * 存储上传资料的信息
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
@TableName("t_informations")
public class TInformations extends Model<TInformations> implements EntityDao {


	private static final long serialVersionUID = 1L;

	/**
	 * 资料表主键id
	 */
	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	/**
	 * 拟定表主键id
	 */
	@TableField("ID_B")
	private Integer idB;
	/**
	 * 上传资料名称
	 */
	@TableField("F_FILE_NAME")
	private String fileName;
	/**
	 * 上传人ID
	 */
	@TableField("F_UPDATEOR_ID")
	private String updateorId;
	/**
	 * 上传人
	 */
	@TableField("F_UPDATEOR")
	private String updateor;
	/**
	 * 上传时间
	 */
	@TableField("F_UPDATE_TIME")
	private Date updateTime;
	/**
	 * 下载次数
	 */
	@TableField("F_DOWNLOADS_NUM")
	private Integer downloadsNum;
	/**
	 * 预览次数
	 */
	@TableField("F_PREVIEW_NUM")
	private Integer previewNum;
	/**
	 * 文档存储路径
	 */
	@TableField("F_FILE_PATH")
	private String filePath;
	/**
	 * 文件大小
	 */
	@TableField("F_FILE_SIZE")
	private String fileSize;
	/**
	 * 资料认证人ID
	 */
	@TableField("CE_ID")
	private String ceId;
	/**
	 * 资料认证人姓名
	 */
	@TableField("CE_NAME")
	private String ceName;
	/**
	 * 认证时间
	 */
	@TableField("CE_DATE")
	private Date ceDate;
	/**
	 * 认证意见 针对于 不通过时的意见
	 */
	@TableField("CE_REMARK")
	private String ceRemark;
	/**
	 * 认证结果 
	 * 0-未认证
	 * 1-合格
	 * 2-不合格
	 */
	@TableField("CE_RESULT")
	private String ceResult;



	@Override
	protected Serializable pkVal() {
		return this.idC;
	}


	//表名
	@Override
	public String getJoinTable() {
		return "t_informations";
	}

	//实体类id
	@Override
	public Integer getEntryId() {
		return this.idC;
	}

	//上传人
	@Override
	public void setCreateor(String createor) {
		this.updateor=createor;
	}

	//上传时间
	@Override
	public void setCreateTime(Date createTime) {
		this.updateTime=createTime;
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
