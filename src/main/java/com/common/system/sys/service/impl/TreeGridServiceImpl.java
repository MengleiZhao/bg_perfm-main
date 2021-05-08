package com.common.system.sys.service.impl;

import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.common.business.project.approval.service.TBudgetExpendFunctionClassService;
import com.common.business.project.approval.service.TClassifcationOfNationalEconmyService;
import com.common.system.sys.entity.RcMenu;
import com.common.system.sys.entity.TreeGridNode;
import com.common.system.sys.entity.TreeGridWrapper;
import com.common.system.sys.service.MenuService;
import com.common.system.sys.service.TreeGridService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/9/12.
 * Time:17:41
 * ProjectName:bg_perfm
 */
@Service
public class  TreeGridServiceImpl implements TreeGridService {
    private static final Log LOG = LogFactory.getLog(TreeGridServiceImpl.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private TBudgetExpendFunctionClassService tBudgetExpendFunctionClassService;
    @Autowired
    private TClassifcationOfNationalEconmyService tClassifcationOfNationalEconmyService;


    @Override
    public List<TreeGridNode> getMenuTreeGridNodes() {
        List<RcMenu> list = menuService.getMenu();
        List<TreeGridNode> treeGridNodeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (RcMenu menu : list
                    ) {
                TreeGridNode treeNode = new TreeGridNode();
                treeNode.setId(Long.valueOf(menu.getId()));
                treeNode.setName(menu.getName());
                treeNode.setUrl(menu.getUrl());
                if (menu.getpId().equals("0")) {
                    treeNode.set_parentId(null);
                } else {
                    treeNode.set_parentId(Long.valueOf(menu.getpId()));
                }
                treeNode.setMenuId(menu.getId());
                treeNode.setLevel(menu.getLevel());
                treeNode.setSort(menu.getSort());
                treeNode.setCode(menu.getCode());
                treeNode.setCreateDate(menu.getCreateTime());
                treeGridNodeList.add(treeNode);

            }
            treeGridNodeList.sort(new Comparator<TreeGridNode>() {
                @Override
                public int compare(TreeGridNode o1, TreeGridNode o2) {
                    if (o1.getSort()==o2.getSort()){
                        return 0;
                    }
                    if (o1.getSort() > o2.getSort()){
                        return 1;
                    }
                    return -1;
                }
            });
        }
        return treeGridNodeList;
    }

    @Override
    public List<TreeGridNode> getBudgetExpendTreeGridNodes() {
        List<TBudgetExpendFunctionClass> list = tBudgetExpendFunctionClassService.getBudgetExpend();
        List<TreeGridNode> treeList = new ArrayList<TreeGridNode>();

        if(list != null &&list.size() > 0){
            for (TBudgetExpendFunctionClass budgetExpend : list){
                TreeGridNode treeNode = new TreeGridNode();
                treeNode.setId(Long.valueOf(budgetExpend.getBId()));
                treeNode.setName(budgetExpend.getFuncName());
                treeNode.setUrl(budgetExpend.getRemark());
                if ("".equals(budgetExpend.getParaentId()) || budgetExpend.getParaentId() == null) {
                    treeNode.set_parentId(null);
                } else {
                    treeNode.set_parentId(Long.valueOf(budgetExpend.getParaentId()));
                }
                treeNode.setMenuId(String.valueOf(budgetExpend.getBId()));
                treeNode.setLevel(budgetExpend.getLevel());
//                treeNode.setSort(budgetExpend.getSort());
                treeNode.setCode(budgetExpend.getFuncCode());
                treeList.add(treeNode);


            }
        }
        return treeList;
    }

    @Override
    public List<TreeGridNode> getBudgetClassTreeGridNodes() {
        List<TClassifcationOfNationalEconmy> list = tClassifcationOfNationalEconmyService.getbudgetExpendFunctionClass();
        List<TreeGridNode> treeList = new ArrayList<TreeGridNode>();

        if(list != null &&list.size() > 0){
            for (TClassifcationOfNationalEconmy classifcationOfNationalEconmy : list){
                TreeGridNode treeNode = new TreeGridNode();
                treeNode.setId(Long.valueOf(classifcationOfNationalEconmy.getCId()));
                treeNode.setName(classifcationOfNationalEconmy.getClassName());
                treeNode.setUrl(classifcationOfNationalEconmy.getRemark());
                if (StringUtils.isEmpty(classifcationOfNationalEconmy.getParaentId())) {
                    treeNode.set_parentId(null);
                } else {
                    treeNode.set_parentId(Long.valueOf(classifcationOfNationalEconmy.getParaentId()));
                }
                treeNode.setMenuId(String.valueOf(classifcationOfNationalEconmy.getCId()));
                treeNode.setLevel(classifcationOfNationalEconmy.getLevel());
//                treeNode.setSort(classifcationOfNationalEconmy.getSort());
                treeNode.setCode(classifcationOfNationalEconmy.getClassCode());
                treeList.add(treeNode);
            }
        }
        return treeList;
    }
}
