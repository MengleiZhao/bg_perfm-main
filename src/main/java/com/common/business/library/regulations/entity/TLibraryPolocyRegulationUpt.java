package com.common.business.library.regulations.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 *  政策法规库修改申请表
 *  用于存放修改申请 的数据
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-06
 */
@Data
@Log
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("t_library_polocy_regulation_upt")
public class TLibraryPolocyRegulationUpt extends Model<TLibraryPolocyRegulationUpt> {

    private static final long serialVersionUID = 1L;

    /**
     * 用于存放修改申请 的数据表 主键值
     */
	@TableId(value="ID_U", type= IdType.AUTO)
	private Integer idU;
    /**
     * 政策法规主键值
     */
	@TableField("ID_X")
	private Integer idX;
    /**
     * 政策法规名称
     */
	@TableField("POLOCY_NAME")
	private String polocyName;
    /**
     * 文号
     */
	@TableField("DOC_NUMBER")
	private String docNumber;
    /**
     * 发文单位
     */
	@TableField("UNIT_NAME")
	private String unitName;
    /**
     * 关键词
     */
	@TableField("KEY_WORDS")
	private String keyWords;
    /**
     * 行政地区
     */
	@TableField(exist = false)
	private String administrativeRegion;



    /**
     * 备注
     */
	@TableField("REMARK")
	private String remark;
    /**
     * 正文
     */
	@TableField("CONTENT")
	private String content;


	/**
	 * 行政地区-省ID
	 */
	@TableField("ADM_REGION_PROVINCE_ID")
	private String admRegionProvinceId;
	/**
	 * 行政地区-省
	 */
	@TableField("ADM_REGION_PROVINCE")
	private String admRegionProvince;
	/**
	 * 行政地区-市ID
	 */
	@TableField("ADM_REGION_CITY_ID")
	private String admRegionCityId;
	/**
	 * 行政地区-市
	 */
	@TableField("ADM_REGION_CITY")
	private String admRegionCity;
	/**
	 * 行政地区-县ID
	 */
	@TableField("ADM_REGION_COUNTY_ID")
	private String admRegionCountyId;
	/**
	 * 行政地区-县
	 */
	@TableField("ADM_REGION_COUNTY")
	private String admRegionCounty;


	/**
	 * 一个修改政法 有多个修改的附件
	 */
	@TableField(exist = false)
	private List<TLibraryPolocyRegulationUptAtta> libraryPolocyRegulationUptAtta;


	@Override
	protected Serializable pkVal() {
		return this.idU;
	}

}
