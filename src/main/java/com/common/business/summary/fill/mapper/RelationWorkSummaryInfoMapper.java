package com.common.business.summary.fill.mapper;

import com.common.business.summary.fill.entity.RelationWorkSummaryInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.zip.DeflaterInputStream;

/**
 * <p>
  * 项目编写工作总结关系表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
public interface RelationWorkSummaryInfoMapper extends BaseMapper<RelationWorkSummaryInfo> {

    /**
     * 1.新增工作总结关系表并返回主键
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 9:30
     * @updateTime 2021/4/25 9:30
     */
    void insertBackKey(@Param("workSummaryInfo") RelationWorkSummaryInfo workSummaryInfo);

    /**
     * 2.根据idA得到该项目目前的最大版本号
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 9:49
     * @updateTime 2021/4/25 9:49
     */
    String queryMaxVersionNO(Integer idA);

    /**
     * 3.修改关系表的状态为 -1 退回 / 2 已完成  审核人为空
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:56
     * @updateTime 2021/4/25 14:56
     */
    void updateStatus(@Param("status") String status,
                      @Param("idR") Integer idR);

    /**
     * 4.修改关系表的审批人为下级审批人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:59
     * @updateTime 2021/4/25 14:59
     */
    void updateNextChecker(@Param("checkUserId") String checkUserId,
                           @Param("checkUser") String checkUser,
                           @Param("idR") Integer idR);
}