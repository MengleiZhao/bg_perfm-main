package com.common.business.planmgr.indexdesign.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.planmgr.indexdesign.entity.*;
import com.common.system.shiro.ShiroUser;

import java.util.List;

/**
 * <p>
 * 指标体系设计表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
public interface TIndexSystemDseignService extends IService<TIndexSystemDseign> {


    /**
     * 根据idA查询指标体系列表
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @return
     * @throws Exception
     */
    List<TIndexSystemDseign> findIndexSystemDseignListByIdA(Integer idA) throws Exception;

    /**
     * 根据idB查询指标说明（评分要点）列表
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idB
     * @return
     * @throws Exception
     */
    List<TIndexScoringPoints> findFIndexDescScoringPointsListByIdB(Integer idB) throws Exception;
    /**
     * 项目支出绩效评价指标库列表
     * @author:安达
     * @date:2021年3月9日 9：51
     * @return
     * @throws Exception
     */
    List<TIndexSystemDseign> findTLibraryIndexSystem(List<TIndexSystemDseign> oldList, String deleteCodeStr) throws Exception;

    /**
     * @Description:根据项目idB查询指标体系对象
     * @Author: 安达
     * @Date: 2021/3/18 16:19
     * @Param:
     * @Return:
     */
    TIndexSystemDseign  getTLibraryIndexSystemByIdB(Integer idB) throws Exception;

    /** 
     * @Description:  获取一级指标集合
     * @Author: 安达
     * @Date: 2021/3/18 9:36
     * @Param:
     * @Return: 
     */
    List<TLibraryIndexSystem>  findLevelOneIndexList() throws Exception;

    /**
     * @Description:  获取二级指标集合
     * @Author: 安达
     * @Date: 2021/3/18 9:36
     * @Param:
     * @Return:
     */
    List<TLibraryIndexSystem>  findLevelTwoIndexList() throws Exception;

    /**
     * 考核对象集合
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    List<TAssessmentObjectByIndex> findEvalUnitList(Integer idA, List<TAssessmentObjectByIndex> oldList, String deleteIdStr) throws Exception;

    /**
     * 选中的考核对象集合
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idB
     * @throws Exception
     */
    List<TAssessmentObjectByIndex> findTAssessmentObjectByIndexList(Integer idB) throws Exception;

    /**
     * 评分要点考核对象集合
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idC
     * @throws Exception
     */
    List<TAssessmentObjectByPoints> findTAssessmentObjectByPointsList(Integer idC) throws Exception;


    /**
     * 佐证材料池
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    List<TEvidencePools> findEvidencePool(Integer idA, List<TEvidencePools> oldList, String deleteIdStr) throws Exception;

    /**
     * @Description: 根据idB查询已经选择的佐证材料
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    List<TEvidencePools> findTEvidencePoolsListByIdB(Integer idB)throws Exception;
    /**
     * @Description: 根据idA查询资料清单集合，且把资料清单转换为佐证材料池对象
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    List<TEvidencePools> findTEvidencePoolsListByIdA(Integer idA)throws Exception;
    /**
     * @Description: 保存指标体系设计
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param evidencePoolsList  佐证材料池集合
     * @Param scoringPointsList  评分要点集合
     * @Param systemDseign  绩效指标对象
     * @Return:
     */
    TIndexSystemDseign saveSystemDseign( List<TAssessmentObjectByIndex> assessmentObjectList, List<TEvidencePools> evidencePoolsList,
                          List<TIndexScoringPoints> scoringPointsList, TIndexSystemDseign systemDseign) throws Exception;

    /**
     * 导出项目绩效评价工作方案（以我所名义出具）指标信息组装
     * @param indexSystemDseignList
     * @return
     */
    List<String[]> getTableInfo( List<TIndexSystemDseign> indexSystemDseignList);
    
    
}
