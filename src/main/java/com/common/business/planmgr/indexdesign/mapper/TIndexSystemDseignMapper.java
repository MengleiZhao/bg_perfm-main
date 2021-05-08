package com.common.business.planmgr.indexdesign.mapper;

import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 指标体系设计表 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
public interface TIndexSystemDseignMapper extends BaseMapper<TIndexSystemDseign> {


    /**
     * 根据一级指标分组查询
     * @author: 陈睿超
     * @createDate: 2021/4/28 10:26
     * @updater: 陈睿超
     * @updateDate: 2021/4/28 10:26
     * @param idR：外键
     * @return
     */
    List<String[]> selectGroupbyCode(Integer idR);

    /**
     * 根据外键和一级指标名称取分值合计
     * @author: 陈睿超
     * @createDate: 2021/4/28 10:26
     * @updater: 陈睿超
     * @updateDate: 2021/4/28 10:26
     * @param idR：外键
     * @return
     */
    List<String[]> sumIndexScore3ByIndex1(@Param(value = "idR") Integer idR);

    /**
     * 根据外键和二级指标名称取分值合计
     * @author: 陈睿超
     * @createDate: 2021/4/28 10:26
     * @updater: 陈睿超
     * @updateDate: 2021/4/28 10:26
     * @param idR：外键
     * @param indexName1：一级指标名称
     * @return
     */
    List<String[]> sumIndexScore3ByIndex2(@Param(value = "idR") Integer idR,@Param(value = "indexName1") String indexName1);
    
    
    
    
    
}