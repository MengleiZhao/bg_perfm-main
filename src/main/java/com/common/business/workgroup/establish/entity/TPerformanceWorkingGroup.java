package com.common.business.workgroup.establish.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 绩效工作组
 * </p>
 *
 * @author 安达
 * @since 2021-03-08
 */
@Data
@Accessors(chain = true)
@TableName("t_performance_working_group")
public class TPerformanceWorkingGroup extends Model<TPerformanceWorkingGroup> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	@TableField("ID_A")
	private Integer idA;
    /**
     * 项目组员ID
     */
	@TableField("GROUP_MEMBER_ID")
	private String groupMemberId;
    /**
     * 组员姓名
     */
	@TableField("GROUP_MEMBER_NAME")
	private String groupMemberName;
    /**
     * 员工编号
     */
	@TableField("GROUP_MEMBER_CODE")
	private String groupMemberCode;
    /**
     * 所在分所ID
     */
	@TableField("BRANCH_OFFICE_ID")
	private String branchOfficeId;
    /**
     * 所在分所
     */
	@TableField("BRANCH_OFFICE_NAME")
	private String branchOfficeName;
	/**
	 * 所在部门ID
	 */
	@TableField("DEPT_ID")
	private String deptId;
	/**
	 * 所在部门
	 */
	@TableField("DEPT_NAME")
	private String deptName;
    /**
     * 人员级别
     */
	@TableField("USER_LEAVEL")
	private String userLeavel;
    /**
     * 毕业院校
     */
	@TableField("GRADUATED_FROM")
	private String graduatedFrom;
    /**
     * 学历
     */
	@TableField("EDUCATION")
	private String education;
    /**
     * 专业
     */
	@TableField("MAJOR")
	private String major;
    /**
     * 是否有主评人资格
     */
	@TableField("IS_QUALIFIED_MAIN_REVIEWER")
	private String isQualifiedMainReviewer;
    /**
     * 项目类型
     */
	@TableField("PROJECT_TYPE")
	private String projectType;
    /**
     * 曾担任项目角色
     */
	@TableField("PREVIOUS_PROJECT_ROLE")
	private String previousProjectRole;
    /**
     * 年限/经验
     */
	@TableField("YEARS_EXPERIENCE")
	private String yearsExperience;
    /**
     * 备注
     */
	@TableField("REMARK")
	private String remark;
    /**
     * 是否外勤主管,Y-是  页面指定后变更（逻辑：一个工作组只允许一名外勤主管）
	 * N-否（默认）
     */
	@TableField("IS_WORK_CHARGE")
	private String isWorkCharge;
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
