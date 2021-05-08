package com.common.business.project.approval.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.common.system.entity.Combobox;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 被评价单位信息
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-08
 */
@Data
@Accessors(chain = true)
@TableName("t_eval_unit_info")
public class TEvalUnitInfo extends Model<TEvalUnitInfo>  implements Combobox {

    private static final long serialVersionUID = 1L;

    
    @TableId( value = "ID_B", type = IdType.AUTO)
	private Integer idB;
	/**
	 * 外键
	 */
	@TableField("ID_A")
	private Integer idA;

	/**
	 * 被评价二级项目名称
	 */
	@Excel(name = "被评价二级项目名称")
	@TableField("EVA_PROJECT_NAME")
	private String evaProjectName;
	
	/**
	 * 评价单位名称
	 */
	@Excel(name = "被评价单位名称")
	@TableField("UNIT_NAME")
	private String unitName;
	/**
	 * 单位联系人
	 */
	@Excel(name = "工作联系人")
	@TableField("LINK_MAN")
	private String linkMan;
	/**
	 * 联系电话
	 */
	@Excel(name = "联系方式")
	@TableField("TEL")
	private String tel;	



	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

	@Override
	public Integer getId() {
		return idB;
	}

	@Override
	public String getCode() {
		return null;
	}

	@Override
	public String getText() {
		return unitName;
	}

	@Override
	public String getDesc() {
		return null;
	}
}
