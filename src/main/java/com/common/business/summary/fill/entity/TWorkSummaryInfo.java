package com.common.business.summary.fill.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 编写工作总结
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_work_summary_info")
public class TWorkSummaryInfo extends Model<TWorkSummaryInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 编写工作总结 主键值
     */
	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
    /**
     * 关系表主键值
     */
	@TableField("ID_R")
	private Integer idR;
    /**
     * 项目实施过程中的经验做法
     */
	@TableField("PRO_IMPL_EXP")
	private String proImplExp;
    /**
     * 存在的不足
     */
	@TableField("EXISTIONG_PROBLEM")
	private String existiongProblem;
    /**
     * 建议
     */
	@TableField("PROPOSAL")
	private String proposal;
    /**
     * 其他
     */
	@TableField("OTHER_INFO")
	private String otherInfo;


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}
