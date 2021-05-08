package com.common.business.collection.means.service;

import com.common.business.collection.means.entity.TInformations;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.collection.means.web.InformationsVo;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * TInformationsService [资料收集上传 Service服务层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-16
 */
public interface TInformationsService extends IService<TInformations>{

    /**
     * 1.TInformationsService [资料收集上传 Service服务层]
     * 资料收集上传的主页面显示
     * @param current             开始查询的页码数 默认为第1页
     * @param size                每页的大小  默认每页显示10条数据
     * @param proPerformanceInfo  精确查询的字段（封装成了TProPerformanceInfo）对象
     * @param search              综合查询的字段
     * @param userId      当前登录用户id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/16 16:07
     * @updateTime 2021/3/16 16:07
     */
    PageInfo<TProPerformanceInfo> queryForPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search,String userId) throws Exception;

    /**
     * 2.TInformationsService [资料收集上传 Service服务层]
     * 选择一个项目，进行该项目的资料上传
     * @param idR 该项目下的资料清单关系表主键id
     * @param versionNO 该项目需要上传的最新版本号
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/18 13:59
     * @updateTime 2021/3/18 13:59
     */
    List<TDevelopmentInformationList> chooseProjectUpload(Integer idR, String versionNO,ShiroUser user) throws Exception;

    /**
     * 3.TInformationsService [资料收集上传 Service服务层]
     * 选择一个模块的资料进行上传
     * @param information 要上传的资料信息
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 11:02
     * @updateTime 2021/3/19 11:02
     */
    @Transactional
    void saveClassUpload(TInformations information,ShiroUser user) throws Exception;

    @Transactional
    void updateCoumnById(Integer idA,Integer operation,Integer idR,ShiroUser user) throws  Exception;

    /**
     * 4.TInformationsService [资料收集上传 Service服务层]
     * 删除资料
     * @param deleteInformanceIdCs 要删除的资料id集合
     * @param idB  要删除的idB 资料清单拟定的主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 13:46
     * @updateTime 2021/3/19 13:46
     */
    @Transactional
    void chooseClassDelete(List<String> deleteInformanceIdCs, Integer idB) throws Exception;

    /**
     * 5.TInformationsService [资料收集上传 Service服务层]
     * 根据要idC集合和idB来查询 资料表中的数据
     * @param informanceIdCs 要删除的资料表中的主键id集合
     * @param idB 要删除的idB 资料清单拟定的主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 14:26
     * @updateTime 2021/3/19 14:26
     */
    List<TInformations> selectInformations(List<String> informanceIdCs, Integer idB)throws Exception;

    /**
     * 6.修改资料表中的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/22 16:39
     * @updateTime 2021/3/22 16:39
     */
    void modifyColumnStatus(Integer idC) throws Exception;

    /**
     * 1.资料收集审核
     * 分页显示数据
     * @param current                   开始查询的页码数 默认为第1页
     * @param size                      每页的大小  默认每页显示10条数据
     * @param proPerformanceInfo        精确查询封装的对象
     * @param search                    综合查询的字段
     * @param userId                   项目组员id 当前登录人id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 10:38
     * @updateTime 2021/3/24 10:38
     */
    PageInfo<TProPerformanceInfo> agreePage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String userId, String search) throws Exception;

    /**
     * 2.资料收集审核
     * 该项目下需要审核认证的资料信息界面
     * @param idR 拟定关系表中的主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 11:26
     * @updateTime 2021/3/24 11:26
     */
    List<TDevelopmentInformationList> fileDetails(Integer idR,Integer detail) throws Exception;

    /**
     * 3.资料收集审核
     * 单个/批量 认证
     * @param informations 前端传过来的 资料集合
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 13:43
     * @updateTime 2021/3/24 13:43
     */
    void agreeFiles(InformationsVo informations,ShiroUser user) throws Exception;


}
