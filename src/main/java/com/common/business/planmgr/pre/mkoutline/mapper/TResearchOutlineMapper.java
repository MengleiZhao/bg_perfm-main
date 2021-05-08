package com.common.business.planmgr.pre.mkoutline.mapper;

import com.common.business.planmgr.pre.mkoutline.entity.TResearchOutline;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 拟定调研提纲 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
public interface TResearchOutlineMapper extends BaseMapper<TResearchOutline> {
    /**
     * 1.批量新增 拟定调研提纲数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 13:54
     * @updateTime 2021/4/22 13:54
     */
    void insertBatches(@Param("researchOutlines") List<TResearchOutline> researchOutlines);
}