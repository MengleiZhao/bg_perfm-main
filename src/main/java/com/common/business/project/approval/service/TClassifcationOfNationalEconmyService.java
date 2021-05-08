package com.common.business.project.approval.service;

import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.util.tree.TreeEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 存放绩效项目国民经济分类信息 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
public interface TClassifcationOfNationalEconmyService extends IService<TClassifcationOfNationalEconmy> {


    /**
     * 查询list分页数据
     * @param pageNum
     * @param pageSize
     * @param tBudgetExpendFunctionClass
     * @return PageInfo<TClassifcationOfNationalEconmy>
     * @author 陈睿超
     */
    PageInfo<TClassifcationOfNationalEconmy> listforpage(Integer pageNum, Integer pageSize, TClassifcationOfNationalEconmy tBudgetExpendFunctionClass);

    /**
     * 获取全部集合
     * @return
     */
    List<TClassifcationOfNationalEconmy> getbudgetExpendFunctionClass();

    /**
     * 内连接查询自己
     * @return
     */
    List<TClassifcationOfNationalEconmy> getbudgetExpendFunctionClassLeftJion();

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
