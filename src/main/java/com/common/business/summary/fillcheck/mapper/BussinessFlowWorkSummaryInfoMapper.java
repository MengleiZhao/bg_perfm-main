package com.common.business.summary.fillcheck.mapper;

import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.common.business.summary.fillcheck.entity.BussinessFlowWorkSummaryInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
  * 业务流程表（编写工作总结审批） Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
public interface BussinessFlowWorkSummaryInfoMapper extends BaseMapper<BussinessFlowWorkSummaryInfo> {

    /**
     * 1.向业务表种批量插入数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 10:37
     * @updateTime 2021/4/25 10:37
     */
    void insertBatches(@Param("flows") List<BussinessFlowWorkSummaryInfo> flows);

    /**
     * 2.根据idR查询业务流数据，并根据审批节点顺序正向排序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 13:41
     * @updateTime 2021/4/25 13:41
     */
    List<BussinessFlowWorkSummaryInfo> queryByIdR(Integer idR);
    /**
     * 3.根据idR查询业务流数据Map，并根据审批节点顺序正向排序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:04
     * @updateTime 2021/4/25 14:04
     */
    @MapKey("ORDER_OF_CURRENT_NODE")
    HashMap<Integer, BussinessFlowWorkSummaryInfo> queryMapByIdR(Integer idR);

    /**
     * 4.根据idR和节点审批顺序 修改 “活跃状态”和“当前节点状态
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 14:41
     * @updateTime 2021/4/25 14:41
     */
    void updateFlowStatus(@Param("flow") BussinessFlowWorkSummaryInfo flow);

    /**
     * 5.根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序数据对象
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:09
     * @updateTime 2021/4/25 15:09
     */
    BussinessFlowWorkSummaryInfo queryOrderOfNodeValue(@Param("userId") Integer userId,
                                                           @Param("idR") Integer idR);

    /**
     * 6.根据idR和当前用户id值 从业务表中得到当前登录人的审批顺序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:14
     * @updateTime 2021/4/25 15:14
     */
    Integer queryOrderOfNode(@Param("userId") Integer userId,
                             @Param("idR") Integer idR);

    /**
     * 7.修改业务表的被指派人数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:54
     * @updateTime 2021/4/25 15:54
     */
    void updateTransfer(@Param("flow") BussinessFlowWorkSummaryInfo flow);
}