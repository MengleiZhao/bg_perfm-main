package com.common.business.library.regulations.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_administrative_region_info")
public class TAdministrativeRegionInfo extends Model<TAdministrativeRegionInfo> {

    private static final long serialVersionUID = 1L;
	/**
	 * 主键id值
	 */
	@TableField("ID_X")
	private Integer idX;
	/**
	 * 父节点
	 */
	@TableField("PARAENT_ID")
	private Integer paraentId;
	/**
	 * 地区编号（包含省级编号 和 地区编号）
	 */
	@TableField("ORDER_NO")
	private Integer orderNo;
	/**
	 * 地区名称
	 */
	@TableField("AR_NAME")
	private String arName;
	@TableField("LEVEL_DESC")
	/**
	 * 地区级别描述
	 */
	private String levelDesc;
	/**
	 * 地区包含数（如：河北省包含11个市）
	 */
	@TableField("AR_TOTAL")
	private String arTotal;
	/**
	 * 备注
	 */
	@TableField("REMARK")
	private String remark;

	/**
	 * 子节点
	 */
	@TableField(exist = false)
	private List<TAdministrativeRegionInfo> ChildList=new ArrayList<>();

	@Override
	protected Serializable pkVal() {
		return this.idX;
	}

}
