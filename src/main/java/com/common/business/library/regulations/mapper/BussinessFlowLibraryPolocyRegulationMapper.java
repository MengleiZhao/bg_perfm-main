package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.BussinessFlowLibraryPolocyRegulation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 业务流程表（政策法规库审批） Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-01
 */
public interface BussinessFlowLibraryPolocyRegulationMapper extends BaseMapper<BussinessFlowLibraryPolocyRegulation> {
    /**
     * 1.根据政法主键idX判断是否有数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/1 15:16
     * @updateTime 2021/4/1 15:16
     */
    Integer queryAliveByIdX(Integer idX);

    /**
     * 2.根据穿过来的审批顺序的数值，查询当前的政法数据表的审批顺序
     *  1）如果顺序一致，返回的按节点顺序(1-发起人,2-一级审批人,3-二级审批人)拍好序的 ID_CHE 主键值(此时，返回的数据有用)
     *  2）否则返回的是，匹配上几个返回几个主键值（没用）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/1 15:16
     * @updateTime 2021/4/1 15:16
     */
    List<Integer> queryCherkerAlive(@Param("idX") Integer idX, @Param("userId") Integer userId,
                                    @Param("firstId") Integer firstId, @Param("secondId") Integer secondId);

    /**
     * 3.批量插入数据到 政法业务审批流程表中
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/1 16:34
     * @updateTime 2021/4/1 16:34
     */
    void insertBath(@Param("flowLibraryPolocyRegulations") List<BussinessFlowLibraryPolocyRegulation> flowLibraryPolocyRegulations);


    /**
     * 4.根据主键idChe值 修改部分数据（针对于 政法申请被退回，且设置的审批人(包括发起人)的顺序相同时，才回调用此方法）
     * @param flowLibraryPolicy 要修改的数据封装的对象
     * @param idChe            业务表主键值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/1 17:01
     * @updateTime 2021/4/1 17:01
     */
    void updateSome(@Param("flowLibraryPolicy") BussinessFlowLibraryPolocyRegulation flowLibraryPolicy,
                    @Param("idChe") Integer idChe);




    /**
     * 5.根据当前登录人（审批人员）的id值查询上一级别的 活跃状态和完成状态
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 10:39
     * @updateTime 2021/4/2 10:39
     */
    BussinessFlowLibraryPolocyRegulation previousLevelStatus(String userId);

    /**
     * 6.根据当前审批人的id从业务表中得到审批节点
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 15:37
     * @updateTime 2021/4/2 15:37
     */
    Integer selectOrderOfCurrentNode(@Param("userId") Integer userId,@Param("idX") Integer idX,@Param("deptId")Integer deptId);

    /**
     * 7.查询当前审批人的下级节点/上级节点
     * @param userId 当前审批人主键id值
     * @param compare 下一级(&gt;大于)、上一级(&lt;小于)
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 16:39
     * @updateTime 2021/4/2 16:39
     */
    BussinessFlowLibraryPolocyRegulation selectFlow(@Param("userId") Integer userId,@Param("compare") String compare);
    /**
     * 8.查询当前节点
     * @param userId 当前审批人主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 17:57
     * @updateTime 2021/4/2 17:57
     */
    BussinessFlowLibraryPolocyRegulation selectNowFlow(Integer userId);

    /**
     * 9.查询该政法的所有业务审批节点
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/4 8:59
     * @updateTime 2021/4/4 8:59
     */
    List<BussinessFlowLibraryPolocyRegulation> queryAllFlowsByIdX(Integer idX);

    /**
     * 10.修改业务表的 活跃状态、 节点状态、修改人和修改时间（是当前审批人）
     * 针对于
     *  1）审批通过时，
     * 		上一级审批人(可能是发起人)的修改： 活跃状态为 0-不活跃
     * 		当前审批人的修改：活跃状态为1-活跃 节点状态为 1-已完成
     * 	2）不通过时，所有该政法的状态都为 	0-不活跃、0-已完成
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/4 9:41
     * @updateTime 2021/4/4 9:41
     */
    void updateFlow(@Param("flow") BussinessFlowLibraryPolocyRegulation flow,
                    @Param("idX") Integer idX);
}