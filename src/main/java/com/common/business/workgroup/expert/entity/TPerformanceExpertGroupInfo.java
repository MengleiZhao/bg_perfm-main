package com.common.business.workgroup.expert.entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 存放项目的绩效专家组信息
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-12
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
@TableName("t_performance_expert_group_info")
public class TPerformanceExpertGroupInfo extends Model<TPerformanceExpertGroupInfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 绩效专家表主键id
	 */
	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	/**
	 * 项目信息主子表主键id
	 */
	@TableField("ID_A")
	private Integer idA;
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
	 * 专家权限  存放菜单id
	 用竖线分隔 “|”
	 */
	@TableField("AUTHORITY_OF_EXPERTS")
	private String authorityOfExperts;

	//接收前端传递过来的专家权限数组
	@TableField(exist = false)
	private List<String> autorityExpets=new ArrayList<>();//初始化为空
	/**
	 * 是否创建专家账号   0-否（默认）  1-是
	 * 	如选择是，则触发逻辑：后台在rc_user表中自动生成专家账号数据，并激活
	 *	账号规则：（注意判断重复）
	 *	EX+4位年份+5位随机数
	 */
	@TableField("IS_CREATE_ACCOUNT")
	private String isCreateAccount;
	/**
	 * 专家账号
	 */
	@TableField("EXP_ACCOUNT")
	private String expAccount;
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


	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}