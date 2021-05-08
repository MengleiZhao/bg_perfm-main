package com.common.system.sys.service;

import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.common.system.sys.entity.ZTreeNode;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/8/7.
 * Time:14:03
 * ProjectName:bg_perfm
 */
public interface ZTreeService {
    List<ZTreeNode> getMenuZTreeNodes();
    String buildZTree( List<ZTreeNode> zTreeNodeList);

    /**
     * 获取预算支出功能分类树形数据
     * @return list集合
     * @author 陈睿超
     * @param type 调用类型：新增 add, 修稿：edit
     * @createtime 2021年3月2日
     * @updator 陈睿超
     * @updatetime 2021年3月2日
     */
    List<ZTreeNode> getBudgetExpendTreeNodes(String type);


    /**
     * 获取国民经济分类树形数据
     * @return list集合
     * @author 陈睿超
     * @param type 调用类型：新增 add, 修稿：edit
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    List<ZTreeNode> getBudgetClassTreeNodes(String type);

}
