package com.common.system.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@TableName("rc_user")
public class RcUser extends Model<RcUser> implements Serializable{
    private static final long serialVersionUID = -8597875106667295283L;
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    @Excel(name = "用户名",width = 30)
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("salt")
    private String salt;
    @Excel(name = "姓名",width = 30)
    @TableField("name")
    private String name;
    /**
     *  状态   0:管理员   1：工作组成员
     */
    @TableField("status")
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private Date updateTime;
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
     * 工作年限（经验）
     */
    @TableField("YEARS_EXPERIENCE")
    private String yearsExperience;
    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;
    /**
     * 数据状态
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
        return this.id;
    }
    @TableField(exist = false)
    private List<RcRole> roleList;

}