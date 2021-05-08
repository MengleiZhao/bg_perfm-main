package com.common.business.library.regulations.service;

import com.common.business.collection.means.entity.TInformations;
import com.common.business.library.regulations.entity.LibraryPolocyRegulation;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationAtta;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationUptAtta;
import com.common.business.library.regulations.web.LibraryPolocyRegulationVo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * TLibraryPolocyRegulationService 【政策法规库 服务层】
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
public interface TLibraryPolocyRegulationService extends IService<LibraryPolocyRegulation> {





    /**
     * 1.TLibraryPolocyRegulationService 【政策法规库 服务层】
     * 政策法规 主页面显示
     * @param current                  开始查询的页码数 默认为第1页
     * @param size                     每页的大小  默认每页显示10条数据
     * @param libraryPolocyRegulation  精确查询对象
     * @param search                   综合查询
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 14:13
     * @updateTime 2021/3/26 14:13
     */
    PageInfo<LibraryPolocyRegulation> queryForPage(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search) throws Exception;


    /**
     * 2.TLibraryPolocyRegulationService 【政策法规库 服务层】
     * 根据主键id 查看该法规的详情（text文本）
     * @param idX 政策法规库主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 16:12
     * @updateTime 2021/3/26 16:12
     */
    LibraryPolocyRegulation policyDetail(Integer idX);



//数据库更新-->政法数据库 [开始]
    /**
     * 1.数据库更新-->政法数据库-->主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/29 19:49
     * @updateTime 2021/3/29 19:49
     */
    PageInfo<LibraryPolocyRegulation> queryRenewPage(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search) throws Exception;

    /**
     * 2.数据库更新-->政法数据库：根据政策法规名进行查询 该数据是否存在
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/29 10:27
     * @updateTime 2021/3/30 17:56
     */
    Integer queryByPolocyName(String policyName) throws Exception;

    /**
     * 3.数据库更新-->政法数据库：申请入库，向表格中插入数据
     * libraryPolocy             文件对象转换的政法对象
     * @param libraryPolocyRegulation   政法数据库对象
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 10:51
     * @updateTime 2021/3/30 10:51
     */
    @Transactional
    void inUpdatePolicy(List<TLibraryPolocyRegulationAtta> policyAttas, LibraryPolocyRegulation libraryPolocyRegulation, ShiroUser user) throws Exception;

    /**
     * 4.数据库更新-->政法数据库：查询 修改和出库申请 选择要申请的政法数据列表
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 11:06
     * @updateTime 2021/3/30 11:06
     */
    PageInfo<LibraryPolocyRegulation> queryUpdateOutPolicy(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search)throws Exception;

    /**
     * 5.数据库更新-->政法数据库：出库申请 暂存/提交
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 19:35
     * @updateTime 2021/3/30 19:35
     */
    @Transactional
    void outPolicy(LibraryPolocyRegulationVo libraryPolocyRegulation, ShiroUser user)throws Exception;

    /**
     * 6.数据库更新-->政法数据库：选择项目:所有已经完结并且上传了资料的项目都可以选择（显示的是当前登录人的所在的项目，是该项目的成员）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 21:42
     * @updateTime 2021/3/30 21:42
     */
    PageInfo<TProPerformanceInfo> chooseProject(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId)throws Exception;

    /**
     * 7.数据库更新-->政法数据库：针对于数据来源是项目中已经上传审核资料的数据，通过资料主键idC拿到该数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 9:35
     * @updateTime 2021/3/31 9:35
     */
    List<TInformations> queryInformationByIdC(List<String> idCs)throws Exception;
    /**
     * 8.入库/修改/出库 暂存/被退回 的删除操作
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 21:06
     * @updateTime 2021/4/6 21:06
     */
    @Transactional
    void deletePolicyApply(Integer idX, String dataStatus, String uptType) throws  Exception;
    /**
     * 9. 显示原有的数据
     * @param idX 要删除的政策法规主键id值
     * @param uptType 调整类型（2-入库  3-修改）
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 22:23
     * @updateTime 2021/4/6 22:23
     */
    LibraryPolocyRegulation queryUpdatePolicy(Integer idX,String uptType)throws Exception;

    /**
     * 10.根据政策法规主键值集合查询政策法规数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 20:10
     * @updateTime 2021/4/19 20:10
     */
    List<LibraryPolocyRegulation> queryByIdXs(List<String> chooseIdXs)throws Exception;

    /**
     * 插入 入库附件
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 15:26
     * @updateTime 2021/4/29 15:26
     */
    @Transactional
    List<TLibraryPolocyRegulationAtta> inPolicyAtta(List<TLibraryPolocyRegulationAtta> attaList,ShiroUser user)throws Exception;

    /**
     * 插入 修改库附件
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 15:27
     * @updateTime 2021/4/29 15:27
     */
    @Transactional
    List<TLibraryPolocyRegulationUptAtta> inPolicyUptAtta(List<TLibraryPolocyRegulationUptAtta> uptAttaList,ShiroUser user)throws Exception;
    /**
     * 入库申请
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 16:14
     * @updateTime 2021/4/29 16:14
     */
    void inPolicy(List<TLibraryPolocyRegulationAtta> attas,List<TLibraryPolocyRegulationAtta> delAttas, LibraryPolocyRegulation libraryPolocyRegulation, ShiroUser user) throws Exception;


    /**
     * 修改库申请
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 16:14
     * @updateTime 2021/4/29 16:14
     */
    void uptPolicy(List<TLibraryPolocyRegulationUptAtta> uptAttas, List<TLibraryPolocyRegulationUptAtta> delUptAttas,LibraryPolocyRegulation libraryPolocyRegulation, ShiroUser user)throws Exception;



    //数据库更新-->政法数据库 [结束]

    //List<RcUser> insertChecker(Integer idX, List<Integer> ids);











}



