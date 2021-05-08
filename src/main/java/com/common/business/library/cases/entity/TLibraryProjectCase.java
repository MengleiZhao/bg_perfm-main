package com.common.business.library.cases.entity;

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
 * 项目案例库
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-17
 */
@Data
@Accessors(chain = true)
@TableName("t_library_project_case")
public class TLibraryProjectCase extends Model<TLibraryProjectCase> {

	private static final long serialVersionUID = 1L;

	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
	/**
	 * 项目编号
	 */
	@TableField("PRO_CODE")
	private String proCode;
	/**
	 * 项目名称
	 */
	@TableField("PRO_NAME")
	private String proName;
	/**
	 * 业务类型
	 */
	@TableField("BUSSINESS_TYPE")
	private String bussinessType;
	/**
	 * 业务级别
	 */
	@TableField("BUSSINESS_LEVEL")
	private String bussinessLevel;
	/**
	 * 客户所属区域
	 */
	@TableField("CUSTOMER_REGION")
	private String customerRegion;
	/**
	 * 备注
	 */
	@TableField("REMARK")
	private String remark;
	/**
	 * 按政府预算支出功能分类（一级分类）
	 */
	@TableField("INDUSTRY_ZFYS_LEVEL1")
	private String industryZfysLevel1;
	/**
	 * 按政府预算支出功能分类（二级分类）
	 */
	@TableField("INDUSTRY_ZFYS_LEVEL2")
	private String industryZfysLevel2;
	/**
	 * 按国民经济行业分类（门类）
	 */
	@TableField("INDUSTRY_GMJJ_LEVEL1")
	private String industryGmjjLevel1;
	/**
	 * 按国民经济行业分类（大类）
	 */
	@TableField("INDUSTRY_GMJJ_LEVEL2")
	private String industryGmjjLevel2;
	/**
	 * 数据状态:1-在库（默认）
	 2-出库审批中
	 3-已出库
	 */
	@TableField("DATA_STAUTS")
	private String dataStauts;
	/**
	 * 入库原因
	 */
	@TableField("INSCREASE_DESC")
	private String inscreaseDesc;
	/**
	 * 出库原因
	 */
	@TableField("REDUCE_DESC")
	private String reduceDesc;


	@Override
	protected Serializable pkVal() {
		return this.idX;
	}

}
