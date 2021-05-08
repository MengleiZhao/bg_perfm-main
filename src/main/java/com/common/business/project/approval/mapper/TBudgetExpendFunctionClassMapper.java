package com.common.business.project.approval.mapper;

import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.util.tree.TreeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 存放绩效项目预算支出功能分类信息 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
public interface TBudgetExpendFunctionClassMapper extends BaseMapper<TBudgetExpendFunctionClass> {

    /**
     * 分页查询
     * @param pageNum
     * @param pagesize
     * @param tBudgetExpendFunctionClass
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    List<TBudgetExpendFunctionClass> selectPageList(@Param("pageNum") int pageNum, @Param("pageSize")int pagesize, @Param("tBudgetExpendFunctionClass")TBudgetExpendFunctionClass tBudgetExpendFunctionClass);

    /**
     *
     * @param tBudgetExpendFunctionClass
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    List<TBudgetExpendFunctionClass> selectBudgetExpendList(TBudgetExpendFunctionClass tBudgetExpendFunctionClass);

    /**
     * 内连接查询自己
     * @param tBudgetExpendFunctionClass
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    List<TBudgetExpendFunctionClass> selectBudgetExpendListLeftJion(TBudgetExpendFunctionClass tBudgetExpendFunctionClass);

    /**
     * 内连接查询自己
     * @param fvalue 值
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    TBudgetExpendFunctionClass selectListLeftJion(@Param("fvalue") String fvalue);

    
    
}