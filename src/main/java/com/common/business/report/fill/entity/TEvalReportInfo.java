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

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 编写评价报告
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-22
 */
@Data
@Accessors(chain = true)
@TableName("t_eval_report_info")
public class TEvalReportInfo extends Model<TEvalReportInfo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
    /**
     * 外键
     */
	@TableField("ID_R")
	private Integer idR;
    /**
     * 报告名称
     */
	@TableField("REPORT_NAME")
	private String reportName;
    /**
     * 报告内容
     */
	@TableField("REPORT_CONTENT")
	private String reportContent;
    /**
     * 报告状态
如在线编辑模式，支持数据暂存
1-暂存
2-提交
     */
	@TableField("REPORT_STATUS")
	private String reportStatus;
    /**
     * 上次编辑时间
     */
	@TableField("LAST_EDIT_TIME")
	private Date lastEditTime;
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
     * 文件大小（单位：MB）
     */
	@TableField("FILE_SIZE")
	private String fileSize;
    /**
     * 编制时间
     */
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

}
