package com.common.business.project.approval.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.shiro.ShiroUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目 Mapper sql接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-08
 */
@Mapper
public interface TProPerformanceInfoMapper extends BaseMapper<TProPerformanceInfo> {


    /**
     * 单表查询
     * @param tProPerformanceInfo
     * @return
     */
    List<TProPerformanceInfo> select(TProPerformanceInfo tProPerformanceInfo);

    /**
     * 返回List
     * @param tProPerformanceInfo
     * @return
     */
    List<TProPerformanceInfo> selectReturnList(TProPerformanceInfo tProPerformanceInfo);


    /**
     * 项目立项分页查询
     * @param pageNum
     * @param pagesize
     * @param tProPerformanceInfo 查询条件类
     * @return 查询结果list
     */
    List<TProPerformanceInfo> selectPageList(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo);

    /**
     * 模糊查询
     * @param pageNum
     * @param pagesize
     * @param tProPerformanceInfo
     * @param search
     * @return
     */
    List<TProPerformanceInfo> selectLikePageList(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);

    /**
     * 模糊查询统计
     * @param tProPerformanceInfo
     * @param search
     * @return
     */
    Integer countLikePage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);
    
    
    
    /**
     * 台账页面查询
     * @param pageNum
     * @param pagesize
     * @param tProPerformanceInfo
     * @return
     */
    List<TProPerformanceInfo> selectLedgerPageList(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo);

    /**
     * 台账页面模糊查询
     * @param pageNum
     * @param pagesize
     * @param tProPerformanceInfo
     * @param search
     * @return
     */
    List<TProPerformanceInfo> selectLikeLedgerPageList(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);

    /**
     * 台账页面统计
     * @param tProPerformanceInfo
     * @param search
     * @return
     */
    Integer countLikeLedgerPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);


    /**
     * 项目立项申请list页面分页数据
     * @param pageNum
     * @param pagesize
     * @param tProPerformanceInfo
     * @return
     */
    List<TProPerformanceInfo> selectApprovalPageList(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo);


/*======================================================================================================================*/
/*          组建专家组   开始         author:田鑫艳                                                                       */
/*======================================================================================================================*/

    /**
     * 1. [组建工作组-->组建专家组   Mapper接口]
     * 分页查询已经立项、已经组建专家组、已经组建工作组、是外勤主管
     * @param search 综合查询的字段
     * @param parentProCode 主项目编号
     * @return List<TProPerformanceInfo> 返回的TProPerformanceInfo类型的集合
     * @author 田鑫艳
     * @createTime 2021/3/8 17:04
     * @updateTime 2021/3/11 11:10
     */
    List<TProPerformanceInfo> queryExceptTPerformanceInfo(@Param("search") String search,
                                                    @Param("parentProCode")String parentProCode,
                                                    @Param("tProPerformanceInfo")TProPerformanceInfo tProPerformanceInfo,
                                                    @Param("proManagerId")String proManagerId);


    /**
     * 2.[组建工作组-->组建专家组   Mapper接口]
     * 查询该主项目编码下的主子项目信息
     * @param parentProCode TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return List<TProPerformanceInfo>  TProPerformanceInfo类型的集合
     * @author 田鑫艳
     * @createTime 2021/3/8 22:27
     * @updateTime 2021/3/8 22:27
     */
    List<TProPerformanceInfo> selectExceptProjectInfo(@Param("parentProCode") String parentProCode,
                                                      @Param("userId")Integer userId,
                                                      @Param("isExpert")Integer isExpert);

    /**
     * 3.[组建工作组-->组建专家组   Mapper接口]
     * 分页查询该项目经理下的所有未组建专家组 的信息
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/10 9:29
     * @updateTime 2021/3/10 9:29
     */
    List<TProPerformanceInfo> selectNoExcepertGroupInfo(String proManagerId);

    /**
     * 4.查询主项目信息（主子项目的  项目负责人、秘书、经理  都是一样的）
     * @param idA
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/13 21:24
     * @updateTime 2021/3/13 21:24
     */
    TProPerformanceInfo selectProjectMainInfo(Integer idA);

    /**
     * 5..TPerformanceExpertGroupInfoMapper [组建工作组-->组建专家组   Mapper接口]
     * 专家组提交时，修改 “是否已经组建专家组”字段为1  0代表未组建专家组  1代表已组建专家组
     * @param idA 项目主键id值
     * @return void
     * @author 田鑫艳
     * @createTime 2021/3/15 16:24
     * @updateTime 2021/3/15 16:24
     */
    void updateExpertGroupIsformed(@Param("expertGroupIsformed") String expertGroupIsformed,@Param("idA")Integer idA);



    /*======================================================================================================================*/
/*          组建专家组   结束          author:田鑫艳                                                                      */
/*======================================================================================================================*/


/*======================================================================================================================*/
/*          工作组台账   开始          author:田鑫艳                                                                      */
/*======================================================================================================================*/


    /**
     * 1.查询所有的工作组信息（普通和专家组的都有）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/12 0:10
     * @updateTime 2021/3/12 0:10
     */
    List<TProPerformanceInfo> selectWorkInfo(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                             @Param("search") String search,
                                             @Param("userId") String userId);


/*======================================================================================================================*/
/*          工作组台账   结束          author:田鑫艳                                                                      */
/*======================================================================================================================*/

/*======================================================================================================================*/
/*          资料搜集上传   开始          author:田鑫艳                                                                      */
/*======================================================================================================================*/
    /**
     * 1.资料搜集上传 主页面显示
     * @param proPerformanceInfo   精确查询的字段（封装成了TProPerformanceInfo）对象
     * @param search               综合查询的字段
     * @param userId       当前登录用户的id
     * @return 当前登录人 该项目下，已经审批通过的，要上传的
     * @author 田鑫艳
     * @createTime 2021/3/16 16:42
     * @updateTime 2021/3/16 16:42
     */
    List<TProPerformanceInfo> queryInformationPerformanceInfo(
            @Param("search") String search,
            @Param("proPerformanceInfo")TProPerformanceInfo proPerformanceInfo,
            @Param("userId")String userId);


    /**
     * 根据主项目编号查询主子项目信息：当前登录用户为空的情况下
     * 约束条件：项目已经立项、项目已经组建工作组
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 15:49
     * @updateTime 2021/4/12 15:49
     */
    List<TProPerformanceInfo> selectInfoByProCode(@Param("parentProCode") String parentProCode);

    /**
     * 1.资料认证（审核）信息
     * 分页显示 资料审批 界面的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/23 9:51
     * @updateTime 2021/3/23 9:51
     */
    List<TProPerformanceInfo> pageForFileCertificInfos(@Param("proPerformanceInfo") TProPerformanceInfo proPerformanceInfo,
                                                       @Param("userId") String userId,
                                                       @Param("search") String search);

/*======================================================================================================================*/
/*          资料搜集上传   结束          author:田鑫艳                                                                      */
/*======================================================================================================================*/


    /**
     * 查询已经立项,但是未组建工作组的项目 （组建工作组）
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> findToEstablishedList(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);


    /**
     * 查询已组建工作组或者组件中的项目
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> findHavingEstablishedList(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);

    /**
     * 查询已组建工作组的项目（组建工作组）
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> findHadEstablishedList(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);


    /**
     * 自定义条件查询
     * @param cloumn 条件
     * @param val 值
     * @author 陈睿超
     * @return
     */
    List<TProPerformanceInfo> findByCloumn(@Param("cloumn") String cloumn,@Param("val") String val);

    /**
     * 项目资料清单关系表 点击新增查询项目
     * @param pageNum
     * @param pagesize
     * @param tProPerformanceInfo
     * @param search   
     * @author 陈睿超
     * @return
     */
    List<TProPerformanceInfo> findRelationProPage(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, @Param("search") String search);





    /**
     * 查询需要预调研的绩效项目
     * @author: 陈睿超
     * @createDate: 2021/3/24 20:31
     * @updater: 陈睿超
     * @updateDate: 2021/3/24 20:31
     * @param search：模糊查询字段
     * @param tProPerformanceInfo：精确查询
     * @return
     **/
    List<TProPerformanceInfo> chooseResearchPro(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, 
                                                @Param("preOrScheme") Integer preOrScheme, 
                                                @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, 
                                                @Param("search") String search);

    /**
     * 查询需要评价绩效评价方案的项目
     * @param pageNum
     * @param pagesize
     * @param preOrScheme
     * @param tProPerformanceInfo
     * @param search
     * @return
     */
    List<TProPerformanceInfo> choosePreparEvalWorkPro(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, 
                                                      @Param("preOrScheme") Integer preOrScheme, 
                                                      @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo, 
                                                      @Param("search") String search, @Param(value = "user") ShiroUser user);

    /**
     * 项目编写评价报告列表分页查询方
     * @param pageNum
     * @param pagesize
     * @param preOrScheme
     * @param tProPerformanceInfo
     * @param search
     * @param user
     * @return
     */
    List<TProPerformanceInfo> chooseEvalReportPro(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize,
                                                      @Param("preOrScheme") Integer preOrScheme,
                                                      @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                      @Param("search") String search, @Param(value = "user") ShiroUser user);



    /**
     * 1.政法数据库：针对于数据来源是 项目的操作
     * 选择项目:所有已经完结并且上传了资料的项目都可以选择（显示的是当前登录人的所在的项目，是该项目的成员）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 21:50
     * @updateTime 2021/3/30 21:50
     */
    List<TProPerformanceInfo> chooseProject(@Param("proPerformanceInfo") TProPerformanceInfo proPerformanceInfo,
                                            @Param("search") String search,
                                            @Param("userId") String userId);


    /**
     * 调查问卷新增查询项目
     * @param pro
     * @param workingGroup
     * @param user 当前登录人
     * @return
     */
    List<TProPerformanceInfo> selectProByGroup(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize, @Param("preOrScheme") Integer preOrScheme, @Param(value = "pro") TProPerformanceInfo pro, @Param(value = "workingGroup") TPerformanceWorkingGroup workingGroup, @Param(value = "user") ShiroUser user);


    /**
     * 指标体系设计列表——所有版本
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> allRelationProIndexPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                   @Param("search") String search, @Param("currCheckId") String currCheckId);
    /**
     * 指标体系设计列表(待拟定的项目)
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> relationProIndexPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                        @Param("search") String search, @Param("currCheckId") String currCheckId);

    /**
     * 指标体系审核列表(设计了，轮到我审批的项目)
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> relationProIndexCheckPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                        @Param("search") String search, @Param("currCheckId") String currCheckId);
    /**
     * 底稿设计审核列表页(设计了，轮到我审批的项目)
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> manuscriptDesignCheckPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                        @Param("search") String search, @Param("currCheckId") String currCheckId);
    /**
     * 项目底稿设计列表页
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> proRaftdesignPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                        @Param("search") String search, @Param("currCheckId") String currCheckId);
    /**
     * 项目底稿设计审核列表页
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> proManuscriptDesignCheckPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                        @Param("search") String search, @Param("currCheckId") String currCheckId);
    /**
     * 底稿填写列表页
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> manuscriptFillPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                @Param("search") String search, @Param("currCheckId") String currCheckId);
    /**
     * 底稿填写审核列表页
     * @param
     * @return
     * @author 安达
     * @createTime 2021/3/12 10:10
     * @updateTime 2021/3/12 10:10
     */
    List<TProPerformanceInfo> manuscriptFillCheckPage(@Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                           @Param("search") String search, @Param("currCheckId") String currCheckId);

    /**
     * 1.拟定调研安排 主页面显示
     * 约束条件：当前登录人是项目主管/或者组员、明确工作任务中有“拟定调研安排”、项目已经立项、已经创建工作组、跟拟定关系表相关联
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 10:51
     * @updateTime 2021/4/19 10:03
     */
    List<TProPerformanceInfo> pageForResearchSchedule(@Param("proPerformanceInfo")TProPerformanceInfo proPerformanceInfo,
                                                      @Param("search")String search,
                                                      @Param("userId") String userId,
                                                      @Param("preOrScheme")Integer preOrScheme);
    /**
     * 2.拟定调研安排 分页显示 选择要拟定调研安排的项目
     * @param userId 当前登录用户
     * @param proPerformanceInfo 精确查询
     * @param search 综合查询
     * @param preOrScheme 0-预调研  1-编制评价方案
     * @param module 1-拟定调研安排  2-拟定调研提纲
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/15 14:47
     * @updateTime 2021/4/19 10:03
     */
    List<TProPerformanceInfo> chooseRecarchProject(@Param("userId") String userId,
                                                   @Param("proPerformanceInfo") TProPerformanceInfo proPerformanceInfo,
                                                   @Param("search") String search,
                                                   @Param("preOrScheme") Integer preOrScheme,
                                                   @Param("module")Integer module);

    /**
     * 3.拟定调研安排 根据主子项目id值查询该项目的项目经理ID
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 17:41
     * @updateTime 2021/4/16 17:41
     */
    String queryManagerId(Integer idA);

    /**
     * 1.拟定调研 安排/提纲 审核 分页显示数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 21:03
     * @updateTime 2021/4/19 21:03
     */
    List<TProPerformanceInfo> scheduleOutlinePage(@Param("proPerformanceInfo") TProPerformanceInfo proPerformanceInfo,
                                                   @Param("search") String search,
                                                   @Param("preOrScheme") Integer preOrScheme,
                                                   @Param("userId") Integer userId,
                                                   @Param("module")Integer module);
    /**
     * 1.拟定调研提纲 分页显示数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 21:10
     * @updateTime 2021/4/21 21:10
     */
    List<TProPerformanceInfo> pageForOutlineSchedule(TProPerformanceInfo proPerformanceInfo, String search, String userId, Integer preOrScheme);

    /**
     * 1.编写工作总结 主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 16:06
     * @updateTime 2021/4/23 16:06
     */
    List<TProPerformanceInfo> summaryPage(@Param("proPerformanceInfo") TProPerformanceInfo proPerformanceInfo,
                                          @Param("search") String search,
                                          @Param("userId") String userId);

    /**
     * 2-选择需要编写工作总结的项目(分页显示)
     * 约束条件：编写评价报告 审核通过的情况下才可以编写工作总结、当前登录人是外勤主管
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 17:30
     * @updateTime 2021/4/23 17:30
     */
    List<TProPerformanceInfo> chooseSummaryProject(@Param("proPerformanceInfo") TProPerformanceInfo proPerformanceInfo,
                                                   @Param("search") String search,
                                                   @Param("userId") String userId);

    /**
     * 1-审核工作总结 主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 11:26
     * @updateTime 2021/4/25 11:26
     */
    List<TProPerformanceInfo> summaryCheckPage(@Param("proPerformanceInfo") TProPerformanceInfo proPerformanceInfo,
                                               @Param("search") String search,
                                               @Param("userId") String userId);
}