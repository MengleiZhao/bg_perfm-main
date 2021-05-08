package com.common.business.workgroup.taskmgr.entity;

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
 * 绩效评价调研函（工作组）
 * </p>
 *
 * @author 安达
 * @since 2021-03-11
 */
@Data
@Accessors(chain = true)
@TableName("t_research_letter_gzz")
public class TResearchLetterGzz extends Model<TResearchLetterGzz> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_A")
	private Integer idA;
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


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
