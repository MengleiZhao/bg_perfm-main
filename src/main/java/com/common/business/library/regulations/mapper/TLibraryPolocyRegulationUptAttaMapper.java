package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.TLibraryPolocyRegulationUptAtta;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 政策法规修改附件表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-28
 */
public interface TLibraryPolocyRegulationUptAttaMapper extends BaseMapper<TLibraryPolocyRegulationUptAtta> {
    /**
     * 1-批量插入
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 11:50
     * @updateTime 2021/4/28 11:50
     */
    void insertBatches(@Param("uptAttas") List<TLibraryPolocyRegulationUptAtta> uptAttas);
    /**
     * 2-查询修改政法附件表（去除删除）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 15:38
     * @updateTime 2021/4/28 15:38
     */
    List<TLibraryPolocyRegulationUptAtta> queryUptAttas(@Param("deleteUptAttaIduaList") List<String> deleteUptAttaIduaList);

    /**
     * 批量插入，并返回主键id值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 15:43
     * @updateTime 2021/4/29 15:43
     */
    List<TLibraryPolocyRegulationUptAtta> insertBatchesBackKeys(List<TLibraryPolocyRegulationUptAtta> uptAttaList);
}