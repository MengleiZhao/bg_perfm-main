package com.common.business.planmgr.indexdraftdesign.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import com.common.business.planmgr.indexdraftdesign.entity.RelationIndexWorkingManuscriptDesign;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignOther;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignRoutine;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignSatisfaction;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 三级指标与指标底稿关系表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
public interface RelationIndexWorkingManuscriptDesignService extends IService<RelationIndexWorkingManuscriptDesign> {
    /**
     * 指标底稿设计列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> indexdraftdesignPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean,ShiroUser user) throws Exception;

    /**
     * 根据idR查询底稿设计表头信息
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idR
     * @return
     * @throws Exception
     */
    public List<TIndexSystemDseign> findIndexSystemDseignListByIdR(Integer idR) throws Exception;

    /**
     * @Description: 工作底稿信息    常规类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    public TIndexWorkingManuscriptDesignRoutine getInformationDesignRoutineByIdB(Integer idB,Integer idR, Integer year1, Integer year2) throws Exception;

    /**
     * @Description: 保存常规类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idB  指标体系设计主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: indexWorkingManuscriptDesignRoutine  常规类底稿对象
     * @Param: user 当前登陆者
     * @Return:
     */
   public void saveDesignRoutine(Integer idB,String stauts,String indexWorkingPaperType, TIndexWorkingManuscriptDesignRoutine indexWorkingManuscriptDesignRoutine, ShiroUser user) throws Exception;

    /** 
     * @Description: 工作底稿信息    满意度类底稿 
     * @Author: 安达
     * @Date: 2021/3/26 10:42
     * @Param: 
     * @Return: 
     */
    public List<TIndexWorkingManuscriptDesignSatisfaction> findSatisfactionListByIdB(Integer idB,Integer idR, Integer year1, Integer year2) throws Exception;
    /**
     * @Description: 保存满意度底稿
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idB  指标体系设计主键
     * @Param stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param designSatisfactionList  满意度底稿集合
     * @Param user 当前登陆者
     * @Return:
     */
    public void saveDesignSatisfaction(Integer idB,String stauts,String indexWorkingPaperType,
                                       List<TIndexWorkingManuscriptDesignSatisfaction>  designSatisfactionList, ShiroUser user) throws Exception;
    /**
     * @Description: 工作底稿信息    其他类底稿
     * @Author: 安达
     * @Date: 2021/3/26 10:42
     * @Param:
     * @Return:
     */
    public List<TIndexWorkingManuscriptDesignOther> findOtherListByIdR( Integer idR ) throws Exception;

    /**
     * @Description: 保存其他类底稿
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idB  指标体系设计主键
     * @Param stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param designOtherList  其他类底稿集合
     * @Param user 当前登陆者
     * @Return:
     */
    public void saveDesignOther(Integer idB,String stauts,String indexWorkingPaperType,
                                List<TIndexWorkingManuscriptDesignOther>  designOtherList, ShiroUser user) throws Exception;


}
