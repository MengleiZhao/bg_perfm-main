package com.common.business.library.experts.entity;

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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 项目专家库
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-10
 */
@Data
@Log
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("t_library_performance_expert")
public class TLibraryPerformanceExpert extends Model<TLibraryPerformanceExpert> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="ID_X", type= IdType.AUTO)
	private Integer idX;
    /**
     * 专家编号
     */
	@TableField("EXP_CODE")
	private String expCode;
    /**
     * 姓名
     */
	@TableField("EXP_NAME")
	private String expName;
    /**
     * 身份证号
     */
	@TableField("ID_NUMBER")
	private String idNumber;
    /**
     * 专家级别
	 * senior-高级
	 * intermediate-中级
	 * junior-初级
     */
	@TableField("EXP_LEAVEL")
	private String expLeavel;
    /**
     * 职称
     */
	@TableField("EXP_TITLE")
	private String expTitle;
    /**
     * 学历
     */
	@TableField("EDUCATION")
	private String education;
    /**
     * 院校
     */
	@TableField("GRADUATED_FROM")
	private String graduatedFrom;
    /**
     * 电话
     */
	@TableField("TEL_NUMBER")
	private String telNumber;
    /**
     * 邮箱
     */
	@TableField("EMAIL_ADDRESS")
	private String emailAddress;
    /**
     * 常驻地_省
     */
	@TableField("PERMANENT_RESIDENCE_PROVINCE")
	private String permanentResidenceProvince;
    /**
     * 常驻地_市
     */
	@TableField("PERMANENT_RESIDENCE_CITY")
	private String permanentResidenceCity;
    /**
     * 常驻地_县
     */
	@TableField("PERMANENT_RESIDENCE_COUNTY")
	private String permanentResidenceCounty;
    /**
     * 主要研究方向
     */
	@TableField("MAIN_RESEARCH_DIRECTIONS")
	private String mainResearchDirections;
    /**
     * 主要著作
     */
	@TableField("MAIN_WORKS")
	private String mainWorks;
    /**
     * 课题成果
     */
	@TableField("RESEARCH_ACHIEVEMENTS")
	private String researchAchievements;
    /**
     * 参与所内项目数
     */
	@TableField("PARTIC_PROJECT_NUMBER")
	private Integer particProjectNumber;
    /**
     * 主要服务分所
     */
	@TableField("MAIN_SERVICE_BRANCH")
	private String mainServiceBranch;
    /**
     * 所在行业（政府预算支出功能分类）一级分类
     */
	@TableField("INDUSTRY_ZFYS_LEVEL1")
	private String industryZfysLevel1;
    /**
     * 所在行业（政府预算支出功能分类）二级分类
     */
	@TableField("INDUSTRY_ZFYS_LEVEL2")
	private String industryZfysLevel2;
    /**
     * 所在行业（国民经济分类）门类
     */
	@TableField("INDUSTRY_GMJJ_LEVEL1")
	private String industryGmjjLevel1;
    /**
     * 所在行业（国民经济分类）大类
     */
	@TableField("INDUSTRY_GMJJ_LEVEL2")
	private String industryGmjjLevel2;
    /**
     * 服务登记评定
     */
	@TableField("SERV_REG_ASSE")
	private String servRegAsse;
    /**
     * 专家权限
     */
	@TableField("AUTHORITY_OF_EXPERTS")
	private String authorityOfExperts;
    /**
     * 创建人
     */
	@TableField("CREATEOR")
	private String createor;
    /**
     * 创建时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 修改人
     */
	@TableField("UPDATEOR")
	private String updateor;
    /**
     * 修改时间
     */
	@TableField("UPDATE_TIME")
	private Date updateTime;
    /**
     * 专家状态  1-正常  2-锁定
     */
	@TableField("EXP_STATUS")
	private String expStatus;
    /**
     * 入库人
     */
	@TableField("CREATEOR2")
	private String createor2;
    /**
     * 入库时间
     */
	@TableField("CREATE_TIME2")
	private Date createTime2;
    /**
     * 数据权限   1-公开  2-私有
     */
	@TableField("DATA_RIGHTS")
	private String dataRights;
    /**
     * 专家签名（图片）
     */
	@TableField("EXP_SING_IMG")
	private String expSingImg;
    /**
     * 数据状态 1-在库（默认） 2-出库审批中  3-已出库
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
