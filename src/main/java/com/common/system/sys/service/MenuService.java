package com.common.system.sys.service;

import com.common.system.sys.entity.RcMenu;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/8/2.
 * Time:13:59
 * ProjectName:bg_perfm
 */
public interface MenuService {

    int deleteByPrimaryKey(String id);

    int insert(RcMenu record) throws Exception;

    RcMenu selectByPrimaryKey(String id);

    RcMenu selectCode(String code);

    int update(RcMenu record);

    int updatePcode(String oldPcode,String newPcode);

    PageInfo<RcMenu> listForPage(Integer pageNum, Integer pageSize);

    List<RcMenu> getMenu();
    List<RcMenu> selectInIds(List<String> ids,List<Integer> wantLevel);

    List<RcMenu> getByParentId(String pId);
}
