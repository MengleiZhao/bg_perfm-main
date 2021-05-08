package com.common.business.planmgr.scheme.mkoutline.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.io.Serializable;

/**
 * <p>
 * 调研提纲模板表
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
@TableName("t_research_outline_temp")
public class TResearchOutlineTemp extends Model<TResearchOutlineTemp> {

    private static final long serialVersionUID = 1L;
	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
    /**
     * 提纲序号
     */
	@TableField("ORDER_NO")
	private Integer orderNo;
    /**
     * 提纲名称
     */
	@TableField("OUTLINE_NAME")
	private String outlineName;
    /**
     * 创建时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 创建人
     */
	@TableField("CREATEOR")
	private String createor;


	@Override
	protected Serializable pkVal() {
		return this.idX;
	}


}
