package com.common.system.sys.service;

import com.common.system.sys.entity.RcPrivilege;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/8/7.
 * Time:11:49
 * ProjectName:bg_perfm
 */
public interface PrivilegeService {
    int add(RcPrivilege privilege);
    int delete(RcPrivilege privilege);
    int deleteByRoleId(Integer roleId);
    List<RcPrivilege> getByRoleId(Integer roleId);
}
