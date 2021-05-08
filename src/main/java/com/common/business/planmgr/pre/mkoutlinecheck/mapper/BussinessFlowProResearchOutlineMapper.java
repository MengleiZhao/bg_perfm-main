package com.common.business.planmgr.pre.mkoutlinecheck.mapper;

import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.common.business.planmgr.pre.mkoutlinecheck.entity.BussinessFlowProResearchOutline;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 业务流程表（拟定调研提纲审批） Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
public interface BussinessFlowProResearchOutlineMapper extends BaseMapper<BussinessFlowProResearchOutline> {
    /**
     * 1.批量插入业务表数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 14:21
     * @updateTime 2021/4/22 14:21
     */
    void insertBatches(@Param("flowsList") List<BussinessFlowProResearchOutline> flowsList);

    /**
     * 2.根据idR查询审批业务流数据 并根据审批节点进行排序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 16:31
     * @updateTime 2021/4/22 16:31
     */
    List<BussinessFlowProResearchOutline> queryByIdR(Integer idR);

    /**
     * 3.根据当前审核用户和idR得到当前登录用户的审批节点
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:11
     * @updateTime 2021/4/22 17:11
     */
    Integer queryOrderOfNode(@Param("userId") Integer userId, @Param("idR") Integer idR);

    /**
     * 4.根据idR和节点审批顺序 修改 “活跃状态”和“当前节点状态”
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:29
     * @updateTime 2021/4/22 17:29
     */
    void updateFlowStatus(@Param("flow") BussinessFlowProResearchSchedule flow);

    /**
     * 5.根据用户id值 跟 idR,得到当前登录用户的业务流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:56
     * @updateTime 2021/4/22 17:56
     */
    BussinessFlowProResearchSchedule queryOrderOfNodeValue(@Param("userId") Integer userId,
                                                           @Param("idR") Integer idR);

    /**
     * 6.修改业务表的被指派人数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:49
     * @updateTime 2021/4/25 15:49
     */
    void updateTransfer(@Param("flow")BussinessFlowProResearchSchedule orderOfNodeValue);
}