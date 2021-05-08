package com.common.business.index.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.io.Serializable;

/**
 * <p>
 * 绩效新闻附件表
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-05
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
@TableName("t_performance_news_mgr_atta")
public class TPerformanceNewsMgrAtta extends Model<TPerformanceNewsMgrAtta> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_Y", type= IdType.AUTO)
	private Integer idY;
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
     * 上传人
     */
	@TableField("CREATEOR")
	private String createor;
    /**
     * 上传时间
     */
	@TableField("CREATE_TIME")
	private String createTime;



	@Override
	protected Serializable pkVal() {
		return this.idY;
	}


}
