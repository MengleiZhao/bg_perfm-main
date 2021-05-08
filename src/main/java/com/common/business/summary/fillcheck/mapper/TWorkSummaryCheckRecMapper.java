package com.common.business.summary.fillcheck.mapper;

import com.common.business.summary.fillcheck.entity.TWorkSummaryCheckRec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 工作总结审批记录表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
public interface TWorkSummaryCheckRecMapper extends BaseMapper<TWorkSummaryCheckRec> {

    /**
     * 1-根据idR查询审批记录数据，并以审批时间进行正序排序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 13:48
     * @updateTime 2021/4/25 13:48
     */
    List<TWorkSummaryCheckRec> queryByIdR(Integer idR);

    /**
     * 2-根据idR判断这个人原来是否有审批数据，如果有，则将原来的 审批数据状态改为 0-历史记录
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:24
     * @updateTime 2021/4/25 14:24
     */
    void updateIfOld(@Param("userId") Integer userId,
                     @Param("idR") Integer idR);
}