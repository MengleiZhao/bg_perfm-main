package com.common.business.project.approval.mapper;

import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 存放绩效项目国民经济分类信息 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
public interface TClassifcationOfNationalEconmyMapper extends BaseMapper<TClassifcationOfNationalEconmy> {

    /**
     * 分页查询
     * @param pageNum
     * @param pagesize
     * @param tClassifcationOfNationalEconmy
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    List<TClassifcationOfNationalEconmy> selectPageList(@Param("pageNum") int pageNum, @Param("pageSize")int pagesize, @Param("tClassifcationOfNationalEconmy")TClassifcationOfNationalEconmy tClassifcationOfNationalEconmy);

    /**
     *
     * @param tClassifcationOfNationalEconmy
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    List<TClassifcationOfNationalEconmy> selectbudgetClassList(TClassifcationOfNationalEconmy tClassifcationOfNationalEconmy);

    /**
     * 内连接查询自己
     * @param tClassifcationOfNationalEconmy
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    List<TClassifcationOfNationalEconmy> selectbudgetClassListLeftJion(TClassifcationOfNationalEconmy tClassifcationOfNationalEconmy);

    /**
     * 内连接查询自己
     * @param fvalue 值
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    TClassifcationOfNationalEconmy selectListLeftJion(@Param("fvalue") String fvalue);

}