package com.common.business.planmgr.indexdraftdesign.entity;

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
 * 常规类底稿评分要点表
 * </p>
 *
 * @author 安达
 * @since 2021-03-25
 */
@Data
@Accessors(chain = true)
@TableName("t_index_working_manuscript_points")
public class TIndexWorkingManuscriptPoints extends Model<TIndexWorkingManuscriptPoints> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_D", type= IdType.AUTO)
	private Integer idD;
	@TableField("ID_C")
	private Integer idC;
    /**
     * 评分要点活动名称
     */
	@TableField("POINTS_NAME")
	private String pointsName;
    /**
     * 设计常规类工作底稿时使用，默认值0,
0-未开展
1-已开展
     */
	@TableField("IS_DEVELOP_ACTIVITY")
	private String isDevelopActivity;
    /**
     * 评价年度
     */
	@TableField("POINTS_YEARS")
	private String pointsYears;


	@Override
	protected Serializable pkVal() {
		return this.idD;
	}

}
