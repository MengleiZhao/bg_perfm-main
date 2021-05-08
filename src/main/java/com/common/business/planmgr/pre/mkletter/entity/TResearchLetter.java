package com.common.business.planmgr.pre.mkletter.entity;

import java.io.Serializable;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 拟定调研函
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_research_letter")
public class TResearchLetter extends Model<TResearchLetter> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_R")
	private Integer idR;
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
     * 文件大小(单位：MB)
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
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
