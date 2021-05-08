package com.common.business.planmgr.pre.mkinvarr.mapper;

import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * RelationProResearchScheduleMapper [项目调研安排关系表 Mapper 接口]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
public interface RelationProResearchScheduleMapper extends BaseMapper<RelationProResearchSchedule> {

    /**
     * 1.RelationProResearchScheduleMapper [项目调研安排关系表 Mapper 接口]
     * 根据项目主键id值，查询该项目下的最新拟定的 调研安排
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 13:35
     * @updateTime 2021/3/25 13:35
     */
    RelationProResearchSchedule queryLatestVersion(Integer idA);

    /**
     * 2.RelationProResearchScheduleMapper [项目调研安排关系表 Mapper 接口]
     * 根据idA得到关系表跟调研安排表数据 一对多查询 最新版本下的
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 17:26
     * @updateTime 2021/3/25 17:26
     */
    List<RelationProResearchSchedule> queryResearcheInfos(Integer idA);

    /**
     * 3.根据idA查询该项目下的最大版本号
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 15:25
     * @updateTime 2021/4/16 15:25
     */
    String queryMaxVersionNO(Integer idA);

    /**
     * 4.插入调研安排表数据，并返回主键值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 15:52
     * @updateTime 2021/4/16 15:52
     */
    void insertBackKey(@Param("proSchedule") RelationProResearchSchedule proSchedule);

    /**
     * 5.修改关系表的状态为 -1 退回 / 2 已完成  审核人为空
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 11:35
     * @updateTime 2021/4/21 11:35
     */
    void updateStatus(@Param("status") String status, @Param("idR") Integer idR);

    /**
     * 6.修改关系表的审批人为下级审批人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 11:41
     * @updateTime 2021/4/21 11:41
     */
    void updateNextChecker(@Param("checkUserId") String checkUserId,
                           @Param("checkUser") String checkUser,
                           @Param("idR") Integer idR);
}