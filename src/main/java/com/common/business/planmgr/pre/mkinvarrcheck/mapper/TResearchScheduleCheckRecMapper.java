package com.common.business.planmgr.pre.mkinvarrcheck.mapper;

import com.common.business.planmgr.pre.mkinvarrcheck.entity.TResearchScheduleCheckRec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 调研安排审批记录表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
public interface TResearchScheduleCheckRecMapper extends BaseMapper<TResearchScheduleCheckRec> {

    /**
     * 1.如果该ID_R下有这个人的之前的数据，则将之前的数据改为 0-历史数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 9:09
     * @updateTime 2021/4/21 9:09
     */
    void updateIfOld(@Param("userId") Integer userId, @Param("idR") Integer idR);

    /**
     * 2.根据idR查询审批记录数据，并根据 审批时间进行排序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 19:31
     * @updateTime 2021/4/21 19:31
     */
    List<TResearchScheduleCheckRec> queryByIdR(Integer idR);
}