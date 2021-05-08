package com.common.business.library.regulations.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.List;

import com.common.system.entity.EntityDao;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

/**
 * <p>
 * 政策法规库
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_library_polocy_regulation")
public class LibraryPolocyRegulation extends Model<LibraryPolocyRegulation>  {

	private static final long serialVersionUID = 1L;

	/**
	 * 政策法规库 主键id
	 */
	@TableId(value="ID_X", type= IdType.AUTO)
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
	 * 数据权限
	 1-公开
	 2-私有
	 */
	@TableField("DATA_RIGHTS")
	private String dataRights;
	/**
	 * 数据状态
	 0-暂存
	 1-审批中
	 2-已审批
	 */
	@TableField("DATA_STAUTS")
	private String dataStauts;
	/**
	 * 申请人ID
	 包含了
	 出库申请人
	 入库申请人
	 更新申请人
	 */
	@TableField("APPLICANT_ID")
	private String applicantId;
	/**
	 * 申请人
	 包含了
	 出库申请人
	 入库申请人
	 更新申请人
	 */
	@TableField("APPLICANT_NAME")
	private String applicantName;
	/**
	 * 申请时间
	 包含了
	 出库申请时间
	 入库申请时间
	 更新申请时间
	 */
	@TableField("APPLY_TIME")
	private Date applyTime;
	/**
	 * 申请原因
	 包含了
	 出库申请原因
	 入库申请原因
	 更新申请原因
	 */
	@TableField("APPLY_DESC")
	private String applyDesc;
	/**
	 * 调整类型
	 1-出库申请
	 2-入库申请
	 3-修改申请
	 */
	@TableField("UPT_TYPE")
	private String uptType;
	/**
	 * 当前审批人ID
	 */
	@TableField("CURR_CHECK_ID")
	private String currCheckId;
	/**
	 * 当前审批人姓名
	 */
	@TableField("CURR_CHECK_NAME")
	private String currCheckName;

	/**
	 * 发布时间
	 */
	@TableField("RELEASE_TIME")
	private Date releaseTime;


	/**
	 * 数据来源
	 * 1-项目 2-非项目
	 */
	@TableField("DATA_SOURCES")
	private String dataSources;


	/**
	 * 行政地区-省ID
	 */
	@TableField("ADM_REGION_PROVINCE_ID")
	private Integer admRegionProvinceId;
	/**
	 * 行政地区-省
	 */
	@TableField("ADM_REGION_PROVINCE")
	private String admRegionProvince;
	/**
	 * 行政地区-市ID
	 */
	@TableField("ADM_REGION_CITY_ID")
	private Integer admRegionCityId;
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
	 * 行政地区（省-市-县  的拼接）
	 */
	@TableField(exist = false)
	private String administrativeRegion;

	@TableField(exist = false)
	private MultipartFile[] files;//批量上传文件

	@TableField(exist = false)
	private String idCs;//针对于 政策法规的来源是 “项目”的情况，可以选择多个资料，idC为该资料文件的id主键（中间用逗号分割）

	@TableField(exist = false)
	private List<LibraryPolocyRegulationCheckRec> libraryPolocyRegulationCheckRecs;//一个政策法规有多个审批记录

	@TableField(exist = false)
	private List<BussinessFlowLibraryPolocyRegulation> bussinessFlowLibraryPolocyRegulations;//一个政策法规有多个审批人 就有多个审批业务


	/**
	 * 用于显示前端的 去掉时间的时分秒 和查询时间字段
	 */
	@TableField(exist = false)
	private String releaseDate;

	/**
	 * 用于前端精确查询发布时间==》开始时间
	 */
	@TableField(exist = false)
	private String releaseStartTime;
	/**
	 * 用于前端精确查询发布时间==》结束时间
	 */
	@TableField(exist = false)
	private String releaseEndTime;
	/**
	 * 用于前端精确查询申请时间==》开始时间
	 */
	@TableField(exist = false)
	private String applyStartTime;
	/**
	 * 用于前端精确查询申请时间==》结束时间
	 */
	@TableField(exist = false)
	private String applyEndTime;

	/**
	 * 一个政法 有多个附件
	 */
	@TableField(exist = false)
	private List<TLibraryPolocyRegulationAtta> libraryPolocyRegulationAttas;

	/**
	 * 一个政法 有多个修改的附件
	 */
	@TableField(exist = false)
	private List<TLibraryPolocyRegulationUptAtta> libraryPolocyRegulationUptAttas;

	/**
	 * 前端删除政法附件的政法主键值
	 */
	/*@TableField(exist = false)
	private Integer deleteIdX;*/

	/**
	 * 前端删除的政法附件主键值(中间用逗号分割)
	 */
	@TableField(exist = false)
	private String deleteAttaIdcs;

	/**
	 * 前端删除的政法修改附件主键值(中间用逗号分割)
	 */
	@TableField(exist = false)
	private String deleteUptAttaIduas;

	/**
	 * 针对于审批界面：0-未审批  1-已审批
	 */
	@TableField(exist = false)
	private String status;

	/**
	 * 文件名称
	 *//*
	@TableField(exist = false)
	private String fileName;
	*//**
	 * 存储路径
	 *//*
	@TableField(exist = false)
	private String filePath;
	*//**
	 * 文件大小
	 *//*
	@TableField(exist = false)
	private String fileSize;*/


	@Override
	protected Serializable pkVal() {
		return this.idX;
	}


	/*public void setContent(String content) {
		HtmlUtils.htmlUnescape(this.content = content);
		System.out.println(this.content);
		//StringEscapeUtils.unescapeHtml4(this.content = content);

	}*/



}
