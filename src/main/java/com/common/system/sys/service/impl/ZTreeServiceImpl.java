package com.common.system.sys.service.impl;

import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.common.business.project.approval.service.TBudgetExpendFunctionClassService;
import com.common.business.project.approval.service.TClassifcationOfNationalEconmyService;
import com.common.system.sys.entity.RcMenu;
import com.common.system.sys.entity.ZTreeNode;
import com.common.system.sys.service.MenuService;
import com.common.system.sys.service.ZTreeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/8/7.
 * Time:14:04
 * ProjectName:bg_perfm
 */
@Service
public class ZTreeServiceImpl implements ZTreeService {

    private static final Log LOG = LogFactory.getLog(ZTreeServiceImpl.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private TBudgetExpendFunctionClassService tBudgetExpendFunctionClassService;
    @Autowired
    private TClassifcationOfNationalEconmyService tClassifcationOfNationalEconmyService;



    @Override
    public List<ZTreeNode> getMenuZTreeNodes() {
        List<RcMenu> list = menuService.getMenu();
        List<ZTreeNode> zTreeNodeList = new ArrayList();
        for (RcMenu menu:list
                ) {
            ZTreeNode node = new ZTreeNode();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setpId(menu.getpId());
            node.setCode(menu.getCode());
            node.setLevel(menu.getLevel());
            if (menu.getLevel()==2){
                node.setOpen(false);
            }
            zTreeNodeList.add(node);
        }
        return zTreeNodeList;
    }

    @Override
    public String buildZTree(List<ZTreeNode> zTreeNodeList) {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(zTreeNodeList);
            LOG.info(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }


    @Override
    public List<ZTreeNode> getBudgetExpendTreeNodes(String type) {
        List<TBudgetExpendFunctionClass> list = new ArrayList<TBudgetExpendFunctionClass>();
        if("add".equals(type)){
            list = tBudgetExpendFunctionClassService.getBudgetExpend();
        }else if("edit".equals(type)){
            list = tBudgetExpendFunctionClassService.getBudgetExpendLeftJion();
        }
        List<ZTreeNode> zTreeNodeList = new ArrayList<ZTreeNode>();
        int i = 0;
        for (TBudgetExpendFunctionClass tBudgetExpendFunctionClass : list){
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(String.valueOf(tBudgetExpendFunctionClass.getBId()));
            zTreeNode.setName(tBudgetExpendFunctionClass.getFuncName());
            zTreeNode.setCode(tBudgetExpendFunctionClass.getFuncCode());
            zTreeNode.setpId(String.valueOf(tBudgetExpendFunctionClass.getParaentId()));
            zTreeNode.setParaentName(tBudgetExpendFunctionClass.getParaentName());
            zTreeNode.setParaentId(String.valueOf(tBudgetExpendFunctionClass.getParaentId()));
            zTreeNode.setLevel(tBudgetExpendFunctionClass.getLevel());

            if ("".equals(tBudgetExpendFunctionClass.getParaentId()) || tBudgetExpendFunctionClass.getParaentId() == null){
                zTreeNode.setOpen(false);
            }else{
                zTreeNode.setOpen(false);
            }
            zTreeNodeList.add(zTreeNode);
            i++;
        }
        return zTreeNodeList;
    }

    @Override
    public List<ZTreeNode> getBudgetClassTreeNodes(String type) {
        List<TClassifcationOfNationalEconmy> list = new ArrayList<TClassifcationOfNationalEconmy>();
        if("add".equals(type)){
            list = tClassifcationOfNationalEconmyService.getbudgetExpendFunctionClass();
        }else if("edit".equals(type)){
            list = tClassifcationOfNationalEconmyService.getbudgetExpendFunctionClassLeftJion();
        }
        List<ZTreeNode> zTreeNodeList = new ArrayList<ZTreeNode>();
        for (TClassifcationOfNationalEconmy tClassifcationOfNationalEconmy : list){
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(String.valueOf(tClassifcationOfNationalEconmy.getCId()));
            zTreeNode.setName(tClassifcationOfNationalEconmy.getClassName());
            zTreeNode.setCode(tClassifcationOfNationalEconmy.getClassCode());
            zTreeNode.setpId(String.valueOf(tClassifcationOfNationalEconmy.getParaentId()));
            zTreeNode.setParaentName(tClassifcationOfNationalEconmy.getParaentName());
            zTreeNode.setParaentId(String.valueOf(tClassifcationOfNationalEconmy.getParaentId()));
            zTreeNode.setLevel(tClassifcationOfNationalEconmy.getLevel());

            if (StringUtils.isEmpty(tClassifcationOfNationalEconmy.getParaentId())){
                zTreeNode.setOpen(false);
            }else{
                zTreeNode.setOpen(false);
            }
            zTreeNodeList.add(zTreeNode);
        }
        return zTreeNodeList;
    }
}
