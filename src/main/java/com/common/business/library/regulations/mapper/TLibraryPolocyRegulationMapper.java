package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.LibraryPolocyRegulation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.library.regulations.web.LibraryPolocyRegulationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * TLibraryPolocyRegulationMapper [政策法规库 Mapper 接口]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
public interface TLibraryPolocyRegulationMapper extends BaseMapper<LibraryPolocyRegulation> {


    /**
     * 1.TLibraryPolocyRegulationMapper [政策法规库 Mapper 接口]
     * 政策法规 主页面分页显示
     * @param libraryPolocyRegulation  精确查询对象
     * @param search                   综合查询
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 14:22
     * @updateTime 2021/3/26 14:22
     */
    List<LibraryPolocyRegulation> queryLibPolocyRegs(@Param("libraryPolocyRegulation") LibraryPolocyRegulation libraryPolocyRegulation,
                                                     @Param("search") String search);

    /**
     * 2.TLibraryPolocyRegulationMapper [政策法规库 Mapper 接口]
     * 根据主键id 查看该法规的详情（text文本）
     * @param idX 政策法规库主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 16:16
     * @updateTime 2021/3/26 16:16
     */
    LibraryPolocyRegulation policyDetail(Integer idX);

    //数据库更新-->政策法规 [开始]
    /**
     * 1.数据库更新-->政法数库：主页面显示（入库、修改库、出库等的状态，即 除了“私有”的政法都要显示）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/29 19:54
     * @updateTime 2021/3/29 19:54
     */
    List<LibraryPolocyRegulation> queryRenewPage(@Param("libraryPolocyRegulation") LibraryPolocyRegulation libraryPolocyRegulation,
                                                 @Param("search") String search);


    /**
     * 2.根据政策法规名进行查询 该数据是否存在
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/29 13:31
     * @updateTime 2021/3/30 17:56
     */
    Integer queryByPolocyName(String policyName);

    /**
     * 3.查询 修改和出库申请 选择要申请的政法数据列表
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 11:10
     * @updateTime 2021/3/30 11:10
     */
    List<LibraryPolocyRegulation> queryUpdateOutPolicy(@Param("libraryPolocyRegulation") LibraryPolocyRegulation libraryPolocyRegulation,
                                                       @Param("search") String search);

    /**
     * 4.出库申请 修改一部分字段
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 19:51
     * @updateTime 2021/3/30 19:51
     */
    void updateOutPolicy(@Param("libraryPolocyRegulation") LibraryPolocyRegulationVo libraryPolocyRegulation);

    /**
     * 5.根据id判断该政法是否存在
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/1 13:34
     * @updateTime 2021/4/1 13:34
     */
    Integer queryAlive(@Param("idX")Integer idX);

    /**
     * 根据政策法规的法规名查询原来是否有数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 10:04
     * @updateTime 2021/4/6 10:04
     */
    LibraryPolocyRegulation queryPolicyAlive(String polocyName);


    /**
     * 6.新增政法数据，并且返回主键
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 14:22
     * @updateTime 2021/4/2 14:22
     */
    void insertBackKey(@Param("libraryPolocyRegulation") LibraryPolocyRegulation libraryPolocyRegulation);


    /**
     * 7.针对于修改申请，修改政法表中某些字段值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 19:14
     * @updateTime 2021/4/6 19:14
     */
    void updateSome(@Param("prolicyRegulation") LibraryPolocyRegulation prolicyRegulation);

    /**
     * 8.针对于 修改、出库申请的删除操作，修改 已入库、已审核、当前审核人id及审核人姓名都为null
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 21:22
     * @updateTime 2021/4/6 21:22
     */
    void updateByIdX(Integer idX);

    //数据库更新-->政策法规 [结束]

    //数据库审核-->政策法规审核  [开始]
    /**
     * 1.政策法规申请审核主界面显示
     * 约束：入库、修改、出库 已经提交，并且政法表中的当前申请人是当前登录人(且上个人的活跃状态为活跃,并且已完成。该约束不在sql里做)
     * @param libraryPolocyRegulation 精确查询字段
     * @param search 综合查询字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 16:17
     * @updateTime 2021/3/31 16:17
     */
    List<LibraryPolocyRegulation> policyCheckPage(@Param("libraryPolocyRegulation") LibraryPolocyRegulation libraryPolocyRegulation,
                                                  @Param("search") String search,
                                                  @Param("userId") String userId);

    /**
     * 2.修改政法表的"数据状态"(DATA_STAUTS)的值（-1 退回  0-暂存  1-审批中  2-已审批）
     *   审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为null
     * @param status 要设置的状态值
     * @param idX    要修改的政策法规主键值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 16:45
     * @updateTime 2021/4/2 16:45
     */
    void updateStatus(@Param("status") String status,@Param("idX")Integer idX);

    /**
     * 3.修改政法表 当前审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为下级审批人的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 16:58
     * @updateTime 2021/4/2 16:58
     */
    void updateLastCheck(@Param("checkUserId") String checkUserId,
                         @Param("checkUser") String checkUser,
                         @Param("idX") Integer idX);

    /**
     * 4.根据政策法规主键值集合查询政策法规数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 20:24
     * @updateTime 2021/4/19 20:24
     */
    List<LibraryPolocyRegulation> queryByIdXs(@Param("idXs") List<Integer> idXs);


    //数据库审核-->政策法规审核  [结束]
}