package com.common.business.planmgr.otherdraftdesign.service;

import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignRoutine;
import com.common.business.planmgr.otherdraftdesign.entity.RelationProWorkingManuscriptDesign;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignOther;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignResearch;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignSuggest;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 项目底稿关系表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
public interface RelationProWorkingManuscriptDesignService extends IService<RelationProWorkingManuscriptDesign> {

    /**
     * 项目底稿设计列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> proRaftdesignPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception;


    /**
     * @Description: 工作底稿信息    常规类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    public TProWorkingManuscriptDesignSuggest getTProWorkingManuscriptDesignSuggestByIdA(Integer idA) throws Exception;
    /**
     * @Description: 调研总结类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    public TProWorkingManuscriptDesignResearch getTProWorkingManuscriptDesignResearchByIdA(Integer idA) throws Exception;
    /**
     * @Description: 其他类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    public List<TProWorkingManuscriptDesignOther> getTProWorkingManuscriptDesignOtherListByIdA(Integer idA) throws Exception;
    /**
     * @Description: 保存问题建议类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idA  子项目主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: proWorkingManuscriptDesignSuggest  问题建议类底稿
     * @Param: user 当前登陆者
     * @Return:
     */
    public void saveTProWorkingManuscriptDesignSuggest(Integer idA, String stauts, String indexWorkingPaperType,
                                                       TProWorkingManuscriptDesignSuggest proWorkingManuscriptDesignSuggest, ShiroUser user) throws Exception;
    /**
     * @Description: 保存调研总结类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idA  子项目主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: proWorkingManuscriptDesignSuggest  调研总结类底稿
     * @Param: user 当前登陆者
     * @Return:
     */
    public void saveTProWorkingManuscriptDesignResearch(Integer idA, String stauts, String indexWorkingPaperType,
                                                        TProWorkingManuscriptDesignResearch proWorkingManuscriptDesignResearch, ShiroUser user) throws Exception;
    /**
     * @Description: 保存其他类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idA  子项目主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: proWorkingManuscriptDesignOther  其他类底稿
     * @Param: user 当前登陆者
     * @Return:
     */
    public void saveTProWorkingManuscriptDesignOther(Integer idA, String stauts, String indexWorkingPaperType,
                                                       List<TProWorkingManuscriptDesignOther> proWorkingManuscriptDesignOtherList, ShiroUser user) throws Exception;
}
