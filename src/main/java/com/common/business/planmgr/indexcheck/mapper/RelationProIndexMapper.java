package com.common.business.planmgr.indexcheck.mapper;

import com.common.business.planmgr.indexcheck.entity.RelationProIndex;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 项目指标体系关系表 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
public interface RelationProIndexMapper extends BaseMapper<RelationProIndex> {
    /** 
     * @Description: 根据 指标体系设计表idB查询项目idA
     * @Author: 安达
     * @Date: 2021/3/30 10:34
     * @Param: 
     * @Return: 
     */
    public Integer getIdaByIdb(Integer idB);
}