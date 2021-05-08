package com.common.system.sys.service;

import com.common.system.sys.entity.TreeGridNode;
import com.common.system.sys.entity.TreeGridWrapper;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/9/12.
 * Time:17:40
 * ProjectName:bg_perfm
 */
public interface TreeGridService {
    List<TreeGridNode> getMenuTreeGridNodes();

    List<TreeGridNode> getBudgetExpendTreeGridNodes();

    List<TreeGridNode> getBudgetClassTreeGridNodes();
}
