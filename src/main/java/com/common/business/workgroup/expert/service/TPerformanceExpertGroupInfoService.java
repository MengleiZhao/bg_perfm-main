package com.common.business.workgroup.expert.service;

import com.common.business.library.experts.entity.TLibraryPerformanceExpert;
import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.web.TPerformanceWorkingGroupVo;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.workgroup.expert.web.TPerformanceExpertGroupInfoVo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  TPerformanceExpertGroupInfoService服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-09
 */
public interface TPerformanceExpertGroupInfoService extends IService<TPerformanceExpertGroupInfo> {


    /**
     * 1.TPerformanceExpertGroupInfoService [组建工作组-->组建专家组  服务层]
     * 分页查询数据
     * @param pageNum 开始的页码数
     * @param pageSize 每页的大小
     * @param tProPerformanceInfo  封装成对象的搜素字段
     * @param search  综合查询的字段
     * @return PageInfo<TProPerformanceInfo>
     * @author 田鑫艳
     * @createTime 2021/3/8 17:00
     * @updateTime 2021/3/11 11:00
     */
    PageInfo<TProPerformanceInfo> queryForPage(Integer pageNum, Integer pageSize,TProPerformanceInfo tProPerformanceInfo, String search,String proManagerId) throws Exception;

    /**
     * 2.TPerformanceExpertGroupInfoService [组建工作组-->组建专家组  服务层]
     * 查询 项目信息
     * @param idA TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return  List<TProPerformanceInfo> TProPerformanceInfo类型的集合
     * @author 田鑫艳
     * @createTime 2021/3/8 22:20
     * @updateTime 2021/3/8 22:20
     */
    TProPerformanceInfo selectProjectInfo(Integer idA) throws Exception;

    /**
     * 3.TPerformanceExpertGroupInfoService [组建工作组-->组建专家组  服务层]
     * 查询 项目信息 -->主子项目的被评单位
     * @param idA 主键id值 任意一个主项目下面的id主键
     * @return List<TEvalUnitInfo>
     * @author 田鑫艳
     * @createTime 2021/3/11 13:41
     * @updateTime 2021/3/11 13:41
     */
    List<TEvalUnitInfo> selectPEvaluatedInfo(Integer idA) throws Exception;


    /**
     * 4.TPerformanceExpertGroupInfoService [组建工作组-->组建专家组  服务层]
     * 点击“详情” 按钮 查询-->工作组信息
     * @param parentProCode TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return  List<TProPerformanceInfo> TProPerformanceInfo类型的集合
     * @author 田鑫艳
     * @createTime 2021/3/9 15:03
     * @updateTime 2021/3/9 15:03
     */
    List<TProPerformanceInfo> selectWorkGroupInfo(String parentProCode,ShiroUser user) throws Exception;

    /**ok
     * 5.TProPerformanceInfoService [组建工作组-->组建专家组  服务层]
     * 点击“详情” 按钮 查询-->专家组信息
     * @param parentProCode TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return List<TProPerformanceInfo> TProPerformanceInfo类型的集合
     * @author 田鑫艳
     * @createTime 2021/3/9 9:45
     * @updateTime 2021/3/9 9:45
     */
    List<TProPerformanceInfo> selectExpertGroup(String parentProCode,ShiroUser user) throws Exception;



    /**
     * 6.TProPerformanceInfoService [组建工作组-->组建专家组  服务层]
     * 分页查询该项目经理下的未组建专家组 的信息
     * @param pageNum 开始查询的页码数 默认为第1页
     * @param pageSize 每页的大小  默认每页显示10条数据
     * @param proManagerId ProPerformanceInfo实体类中proManagerId属性，即：项目经理的id
     * @return List<TProPerformanceInfo> 得到是所有未组建专家组的集合数据
     * @author 田鑫艳
     * @createTime 2021/3/10 9:25
     * @updateTime 2021/3/10 9:25
     */
    PageInfo<TProPerformanceInfo> selectNoExcepertGroupInfo(Integer pageNum, Integer pageSize, String proManagerId) throws Exception;


    /**
     * 7.TProPerformanceInfoService [组建工作组-->组建专家组  服务层]
     * 分页显示在库的专家数据
     * @param pageNum                开始页(即：第几页)
     * @param pageSize                   每页的大小(即：每页的size)
     * @param tLibraryPerformanceExpert 精确的查询的字段
     * @param search                 综合查询字段
     * @param expertGroupInfoVo      该项目中的idA和前端已经删除的专家信息
     * @return List<TLibraryPerformanceExpert> 返回的是所有在库且专家状态为正常的专家数据信息集合
     * @author 田鑫艳
     * @createTime 2021/3/10 14:58
     * @updateTime 2021/3/18 11:08
     */
    PageInfo<TLibraryPerformanceExpert> selectSetUpShowExpertGroups(Integer pageNum, Integer pageSize, TLibraryPerformanceExpert tLibraryPerformanceExpert, String search, TPerformanceExpertGroupInfoVo expertGroupInfoVo) throws Exception;

    /**
     * 8.选中专家信息,跟老数据匹配且返回给前端
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 21:50
     * @updateTime 2021/3/17 21:50
     */
    List<TPerformanceExpertGroupInfo> goBackExpertGroups(TPerformanceExpertGroupInfoVo expertGroupInfoVo);


    /**
     * 9.TProPerformanceInfoService [组建工作组-->组建专家组  服务层]
     * 提交组建的专家成员集合信息
     * @param proPerformanceInfos
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/10 21:23
     * @updateTime 2021/3/10 21:23
     */
   
    boolean setUpSaveExpertGroups(TPerformanceWorkingGroupVo proPerformanceInfos,ShiroUser user) throws Exception;

 /*======工作组台账==============================================================================================================*/

    /**
     * 1.工作组台账显示的界面
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param tProPerformanceInfo 精准查询封装的对象
     * @param search        综合查询的字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/12 0:04
     * @updateTime 2021/3/12 0:04
     */
    PageInfo<TProPerformanceInfo> pagebookList(Integer current, Integer size, TProPerformanceInfo tProPerformanceInfo, String search,ShiroUser user);




    /**
     * 根据主项目编号查询查询该项目下的每个项目下的组员信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 15:42
     * @updateTime 2021/4/12 15:42
     */
    List<TProPerformanceInfo> selectInfoByProCode(String parentProCode);

    /**
     * 根据主项目编号查询需要组建专家组的子项目项目信息和专家信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 16:00
     * @updateTime 2021/4/12 16:00
     */
    List<TProPerformanceInfo> infoExpertByProCode(String parentProCode);
}
