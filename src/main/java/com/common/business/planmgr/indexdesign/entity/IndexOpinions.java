package com.common.business.planmgr.indexdesign.entity;

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
 * 指标意见表（专家填写）
 * </p>
 *
 * @author 安达
 * @since 2021-04-26
 */
@Data
@Accessors(chain = true)
@TableName("index_opinions")
public class IndexOpinions extends Model<IndexOpinions> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_OP", type= IdType.AUTO)
	private Integer idOp;
	@TableField("ID_B")
	private Integer idB;
    /**
     * 专家ID
     */
	@TableField("EXPERTS_ID")
	private String expertsId;
    /**
     * 专家姓名
     */
	@TableField("EXPERTS_NAME")
	private String expertsName;
    /**
     * 提交日期
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 专家意见
     */
	@TableField("EXPERTS_OPINION")
	private String expertsOpinion;


	@Override
	protected Serializable pkVal() {
		return this.idOp;
	}

}
