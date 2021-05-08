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
 * 绩效新闻管理实体类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-04
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
@TableName("t_performance_news_mgr")
public class TPerformanceNewsMgr extends Model<TPerformanceNewsMgr> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
    /**
     * 新闻标题
     */
	@TableField("TITLE")
	private String title;
    /**
     * 副标题
     */
	@TableField("SUBTITLE")
	private String subtitle;
    /**
     * 新闻内容
     */
	@TableField("NOTICE_CONTENT")
	private String noticeContent;
    /**
     * 发布时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 发布人
     */
	@TableField("CREATEOR")
	private String createor;
    /**
     * 是否置顶
     */
	@TableField("IS_TOP")
	private String isTop;



	@Override
	protected Serializable pkVal() {
		return this.idX;
	}


}
