package com.common.business.report.fill.entity;

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
 * 项目评价报告模板表
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
@Data
@Accessors(chain = true)
@TableName("t_pro_eval_report_temp")
public class TProEvalReportTemp extends Model<TProEvalReportTemp> implements EntityDao {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
    /**
     * 评价报告名称
     */
	@TableField("REPORT_NAME")
	private String reportName;
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
     * 上传时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 上传人
     */
	@TableField("CREATEOR")
	private String createor;
    /**
     * 备注
     */
	@TableField("REMARK")
	private String remark;

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
		this.reportName = fileName;
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
