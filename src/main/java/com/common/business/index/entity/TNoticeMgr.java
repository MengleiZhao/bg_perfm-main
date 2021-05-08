package com.common.business.index.entity;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 通知公告管理 fff
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-02
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
@TableName("t_notice_mgr")
public class TNoticeMgr extends Model<TNoticeMgr> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idx;
    /**
     * 公告标题
     */
	@TableField("TITLE")
	private String title;
    /**
     * 副标题
     */
	@TableField("SUBTITLE")
	private String subtitle;
    /**
     * 公告内容
     */
	@TableField("NOTICE_CONTENT")
	private String noticeContent;
    /**
     * 发布时间
     */
	@TableField("CREATE_TIME")
	private String createTime;

    /**
     * 发布人
     */
	@TableField("CREATEOR")
	private String createor;
    /**
     * 是否置顶,0-不置顶（默认）
1-置顶
布置顶时按发布时间倒序排列
     */
	@TableField("IS_TOP")
	private String isTop;


	@Override
	protected Serializable pkVal() {
		return this.idx;
	}



}
