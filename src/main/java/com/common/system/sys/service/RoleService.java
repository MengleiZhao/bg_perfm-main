package com.common.system.sys.service;

import com.common.system.sys.entity.RcRole;
import com.common.system.sys.entity.RcRoleWrapper;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/6/22.
 * Time:14:15
 * ProjectName:bg_perfm
 */
public interface RoleService {

    PageInfo<RcRole> listForPage(Integer pageNum, Integer pageSize);

    int deleteById(Integer id);

    Result<RcRole> selectById(Integer id);

    RcRole selectByRoleName(String roleName);

    RcRole selectByRoleValue(String roleValue);

    Result<Integer> save(RcRole role, List<Integer> permissionIds);
    Result<Integer> update(RcRole role);

    List<RcRoleWrapper> getRoleWrapperList();
}
