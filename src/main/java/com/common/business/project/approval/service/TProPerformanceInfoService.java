package com.common.business.project.approval.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * <p>
 * 子项目 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-08
 */
public interface TProPerformanceInfoService extends IService<TProPerformanceInfo> {

    /**
     * 项目立项分页查询
     * @param pageNum
     * @param pageSize
     * @param bean 查询条件类
     * @param search 模糊查询条件
     * @return
     */
    PageInfo<TProPerformanceInfo> listForPage(Integer pageNum, Integer pageSize, TProPerformanceInfo bean,String search);


    /**
     * 台账页面分页查询
     * @param pageNum
     * @param pageSize
     * @param bean
     * @param search 模糊查询条件
     * @return
     */
    PageInfo<TProPerformanceInfo> listForLedgerPage(Integer pageNum, Integer pageSize, TProPerformanceInfo bean, String search);

    /**
     * 项目立项申请list页面分页数据
     * @param pageNum
     * @param pageSize
     * @param bean
     * @return
     */
    PageInfo<TProPerformanceInfo> listApprovalForPage(Integer pageNum, Integer pageSize, TProPerformanceInfo bean);

    /**
     * 保存
     * @param idMainA 主项目ID
     * @param tProPerformanceInfo 保存基础类
     * @param tEvalUnitInfoJson 被评价单位信息json
     * @param tProPerformanceInfoJson 子项目json
     * @return true操作成功
     */
    Boolean save(Integer  idMainA, TProPerformanceInfo tProPerformanceInfo, ShiroUser user);

    /**
     * 逻辑删除表单，同时修改主项目表状态
     * @param id 子项目表id
     * @return
     */
    Boolean deleteByProStatus(Integer id) throws Exception;

    /**
     * Title: 
     * Description :导出
     * @author: 陈睿超
     * @createDate: 2021/3/11 14:13
     * @updater: 陈睿超
     * @updateDate: 2021/3/11 14:13
     * @param pageNum
     * @param pageSize
     * @param bean 查询条件
     * @param search 模糊查询条件
     * @return
     **/
    Workbook expertLedgerExcel(Integer pageNum, Integer pageSize, TProPerformanceInfo bean, String search);


    List<TProPerformanceInfo> findRelationProPage(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize,
                                                  @Param("tProPerformanceInfo") TProPerformanceInfo tProPerformanceInfo,
                                                  @Param("search") String search);

    /**
     * 查询需要预调研的绩效项目
     * @author: 陈睿超
     * @createDate: 2021/3/24 20:49
     * @updater: 陈睿超
     * @updateDate: 2021/3/24 20:49
     * @param pageNum
     * @param pageSize
     * @param bean 查询条件
     * @param search 模糊查询条件
     * @return
     **/
    PageInfo<TProPerformanceInfo> chooseResearchPro(Integer pageNum, Integer pageSize, Integer preOrScheme,
                                                    TProPerformanceInfo bean,String search);

    /**
     * 查询需要预调研的绩效项目
     * @author: 陈睿超
     * @createDate: 2021/3/24 20:49
     * @updater: 陈睿超
     * @updateDate: 2021/3/24 20:49
     * @param pageNum
     * @param pageSize
     * @param bean 查询条件
     * @param search 模糊查询条件
     * @return
     **/
    PageInfo<TProPerformanceInfo> choosePreparEvalWorkPro(Integer pageNum, Integer pageSize, Integer preOrScheme,
                                                          TProPerformanceInfo bean,String search,ShiroUser user);


    /**
     * 项目编写评价报告列表分页查询方法
     * @param pageNum
     * @param pageSize
     * @param preOrScheme
     * @param bean
     * @param search
     * @param user
     * @return
     */
    PageInfo<TProPerformanceInfo> chooseEvalReportPro(Integer pageNum, Integer pageSize, Integer preOrScheme,
                                                          TProPerformanceInfo bean,String search,ShiroUser user);

}
