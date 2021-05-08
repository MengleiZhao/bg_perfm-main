package com.common.business.planmgr.indexdesign.mapper;

import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.planmgr.indexdesign.entity.TEvidencePools;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 佐证材料池 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-20
 */
public interface TEvidencePoolsMapper extends BaseMapper<TEvidencePools> {
    /**
     * @Description: 根据idA查询资料清单集合，且把资料清单转换为佐证材料池对象
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    List<TEvidencePools> findTEvidencePoolsListByIdA(Integer idA);
}