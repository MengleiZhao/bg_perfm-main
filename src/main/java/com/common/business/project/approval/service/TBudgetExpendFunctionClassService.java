package com.common.business.project.approval.service;

import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.util.tree.TreeEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 存放绩效项目预算支出功能分类信息 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
public interface TBudgetExpendFunctionClassService extends IService<TBudgetExpendFunctionClass> {


    /**
     * 查询list分页数据
     * @param pageNum
     * @param pageSize
     * @param tBudgetExpendFunctionClass
     * @return PageInfo<TBudgetExpendFunctionClass>
     * @author 陈睿超
     */
    PageInfo<TBudgetExpendFunctionClass> listforpage(Integer pageNum, Integer pageSize,TBudgetExpendFunctionClass tBudgetExpendFunctionClass);

    /**
     * 获取全部集合
     * @return
     */
    List<TBudgetExpendFunctionClass> getBudgetExpend();

    /**
     * 内连接查询自己
     * @return
     */
    List<TBudgetExpendFunctionClass> getBudgetExpendLeftJion();

    /**
     * Title: 获取树形数据
     * Description 
     * @author: 陈睿超
     * @createDate: 2021/3/10 16:01
     * @updater: 陈睿超
     * @updateDate: 2021/3/10 16:01
     * @param
     * @return
     **/
    List<TreeEntity> getTreeEntity();
}
