package com.common.business.planmgr.pre.mkoutline.mapper;

import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 项目调研提纲关系表  Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
public interface RelationProResearchOutlineMapper extends BaseMapper<RelationProResearchOutline> {

    /**
     * 1.根据idA查询该项目的最大版本号
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 11:14
     * @updateTime 2021/4/22 11:14
     */
    String queryMaxVersionNO(Integer idA);

    /**
     * 2.新增拟定调研提纲关系表，并返回主键
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 11:31
     * @updateTime 2021/4/22 11:31
     */
    void insertBackKey(@Param("installOutline") RelationProResearchOutline installOutline);

    /**
     * 3.修改关系表的状态为 -1 退回 / 2 已完成  审核人为空
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:33
     * @updateTime 2021/4/22 17:33
     */
    void updateStatus(@Param("status") String status,
                      @Param("idR") Integer idR);

    /**
     * 4.修改关系表的审批人为下级审批人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:35
     * @updateTime 2021/4/22 17:35
     */
    void updateNextChecker(@Param("checkUserId") String checkUserId,
                           @Param("checkUser") String checkUser,
                           @Param("idR") Integer idR);
}