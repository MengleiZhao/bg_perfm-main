package com.common.business.workgroup.establish.mapper;

import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
  * 绩效工作组 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-08
 */
public interface TPerformanceWorkingGroupMapper extends BaseMapper<TPerformanceWorkingGroup> {

/*======================================================================================================================*/
/*          组建专家组   开始         author:田鑫艳                                                                       */
/*======================================================================================================================*/

    /**
     * 1.TPerformanceExpertGroupInfoMapper [组建工作组-->组建专家组   Mapper接口]
     * 查询每个主/子项目中的组员信息集合
     * @param
     * @return List<TPerformanceWorkingGroup> 组员信息集合
     * @author 田鑫艳
     * @createTime 2021/3/9 15:51
     * @updateTime 2021/3/9 15:51
     */
    List<TPerformanceWorkingGroup> selectExceptMembers(Integer idA);

    /**
     * 2.组建工作组的最晚时间
     * @param idA 子项目id值
     * @return Date 返回的是该项目中最晚的时间
     * @author 田鑫艳
     * @createTime 2021/3/12 0:48
     * @updateTime 2021/3/12 0:48
     */
    Date selectMemberTime(Integer idA);

    /**
     * 3.查询该子项目中的员工数
     * @param idA 子项目id值
     * @return Integer 专家人员数
     * @author 田鑫艳
     * @createTime 2021/3/12 9:13
     * @updateTime 2021/4/16 14:57
     */
    Integer selectMemberNum(Integer idA);


    /*======================================================================================================================*/
/*          组建专家组   结束         author:田鑫艳                                                                       */
/*======================================================================================================================*/

    /**
     * @Title: 
     * @Description: 根据项目id和用户账号状态查询工作组成员
     * @author: 陈睿超
     * @createDate: 2021/4/14 10:26
     * @updater: 陈睿超
     * @updateDate: 2021/4/14 10:26
     * @param idA:项目id
     * @param status:sql中用in连接，传入需要的值。状态（0:管理员，1：工作组成员）
     * @return 
     **/
    List<TPerformanceWorkingGroup> findWorkGroupByStatus(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                                         @Param("search") String search,@Param("idA") Integer idA,@Param("status") String status);

    /**
     * 4.根据主子项目id主键值，查询该项目下的员工编号集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/15 20:43
     * @updateTime 2021/4/19 10:42
     */
    List<String> selectMemberName(Integer idA);

    /**
     * 5.得到该项目下组建的员工信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/15 21:00
     * @updateTime 2021/4/15 21:00
     */
    List<TPerformanceWorkingGroup> queryLiveMemeber(@Param("performanceWorkingGroup") TPerformanceWorkingGroup performanceWorkingGroup,
                                                    @Param("search") String search,
                                                    @Param("idA") Integer idA);

    /**
     * 6.根据idA查询外勤主管
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 14:58
     * @updateTime 2021/4/16 14:58
     */
    TPerformanceWorkingGroup queryWorkCharge(Integer idA);
}