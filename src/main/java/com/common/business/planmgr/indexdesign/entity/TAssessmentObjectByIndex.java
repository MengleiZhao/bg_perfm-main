package com.common.business.planmgr.indexdesign.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * 指标考核对象表
 * </p>
 *
 * @author 安达
 * @since 2021-04-20
 */
@Data
@Accessors(chain = true)
@TableName("t_assessment_object_by_index")
public class TAssessmentObjectByIndex extends Model<TAssessmentObjectByIndex> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_B")
	private Integer idB;
    /**
     * 考核对象ID
     */
	@TableField("ASSESSMENT_OBJECT_ID")
	private String assessmentObjectId;
    /**
     * 考核对象名称
     */
	@TableField("ASSESSMENT_OBJECT_NAME")
	private String assessmentObjectName;


	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}
