package com.common.business.planmgr.pre.mkinvarr.mapper;

import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 调研安排表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
public interface TResearchScheduleMapper extends BaseMapper<TResearchSchedule> {

    /**
     * 1.批量向调研安排表中批量插入数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 17:02
     * @updateTime 2021/4/16 17:02
     */
    void insertBatches(@Param("researchSchedules") List<TResearchSchedule> researchSchedules);
}