package com.common.business.planmgr.pre.mkoutlinecheck.mapper;

import com.common.business.planmgr.pre.mkoutlinecheck.entity.TResearchOutlineCheckRec;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 调研报告审批记录表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
public interface TResearchOutlineCheckRecMapper extends BaseMapper<TResearchOutlineCheckRec> {

    /**
     * 1.根据idR查询审批记录数据 ，并根据时间进行排序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 16:38
     * @updateTime 2021/4/22 16:38
     */
    List<TResearchOutlineCheckRec> queryByIdR(Integer idR);

    /**
     * 2.根据idR判断原来这个人是否有审批数据，如果有，则将原来的 审批数据状态改为 0-历史记录
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:23
     * @updateTime 2021/4/22 17:23
     */
    void updateIfOld(Integer id, Integer idR);
}