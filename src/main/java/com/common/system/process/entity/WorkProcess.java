package com.common.system.process.entity;

import lombok.Data;

/**
 * Title: Process
 * Description： 工作流
 * Author: 陈睿超
 * Date: 2021/3/18 15:05
 * Updater: 陈睿超
 * Date: 2021/3/18 15:05
 * Company: 天职国际
 * Version:
 **/
@Data
public class WorkProcess {
    
    //审批人id
    private String  userId;
    //审批人
    private String  userName;
    //部门id
    private String  deptId;
    //部门
    private String  deptName;
    //角色id
    private String  roleId;
    //角色
    private String  roleName;
    //工作流序号
    private String  processNum;
    //审批状态（0-不通过，1-通过）
    private String  checkStatus;
    //审批内容
    private String  checkText;
    //被替代人id
    private String  replacedId;
    //被替代人
    private String  replacedName;
    
    
    
    
    
}
