package com.common.business.planmgr.indexdraftdesign.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.planmgr.indexdraftdesign.entity.RelationIndexWorkingManuscriptDesign;

import java.util.List;

/**
 * <p>
  * 三级指标与指标底稿关系表 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
public interface RelationIndexWorkingManuscriptDesignMapper extends BaseMapper<RelationIndexWorkingManuscriptDesign> {
    public List<RelationIndexWorkingManuscriptDesign> findByIdaList(List<Integer> idaList);
}