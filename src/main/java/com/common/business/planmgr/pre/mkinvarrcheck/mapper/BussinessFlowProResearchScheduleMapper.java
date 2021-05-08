package com.common.business.planmgr.pre.mkinvarrcheck.mapper;

import com.common.business.planmgr.pre.mkinvarrcheck.entity.BussinessFlowProResearchSchedule;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 业务流程表（项目调研安排审批） Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
public interface BussinessFlowProResearchScheduleMapper extends BaseMapper<BussinessFlowProResearchSchedule> {

    /**
     * 1.根据用户id值 跟 idR,得到当前登录用户的审批节点顺序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 9:26
     * @updateTime 2021/4/21 9:26
     */
    Integer queryOrderOfNode(@Param("userId") Integer userId, @Param("idR") Integer idR);

    /**
     * 2.根据idR和节点审批顺序 修改 “活跃状态”和“当前节点状态”
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 9:53
     * @updateTime 2021/4/21 9:53
     */
    void updateFlowStatus(@Param("flow") BussinessFlowProResearchSchedule flow);

    /**
     * 3.根据用户id值 跟 idR,得到当前登录用户的业务流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 17:34
     * @updateTime 2021/4/21 17:34
     */
    BussinessFlowProResearchSchedule queryOrderOfNodeValue(@Param("userId") Integer userId, @Param("idR") Integer idR);

    /**
     * 4.修改业务表的被指派人数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 17:54
     * @updateTime 2021/4/21 17:54
     */
    void updateTransfer(@Param("flow") BussinessFlowProResearchSchedule flow);

    /**
     * 5.根据idR查询 业务流数据，且根据 审批节点顺序进行排序
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 19:28
     * @updateTime 2021/4/21 19:28
     */
    List<BussinessFlowProResearchSchedule> queryByIdR(Integer idR);
}