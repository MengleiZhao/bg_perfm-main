package com.common.business.workgroup.taskmgr.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.taskmgr.entity.TPerformanceTaskAllocation;
import com.common.business.workgroup.taskmgr.entity.TResearchLetterGzz;
import com.common.business.workgroup.taskmgr.entity.TResearchOutlineGzz;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 绩效任务分配表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-09
 */
public interface TPerformanceTaskAllocationService extends IService<TPerformanceTaskAllocation> {
    /**
     * 查询已组建工作组的项目(所有子项目)
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> findToTaskAllocationPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean,ShiroUser user) throws Exception;

    /**
     * 查询已归档未涉密的项目
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> findHadFilePage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean,ShiroUser user) throws Exception;


    /**
     * 根据项目idA查询工作组集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @throws Exception
     */
    List<TPerformanceWorkingGroup> findTPerformanceWorkingGroupListByIdA(Integer idA)throws Exception;

    /**
     * 根据项目idA查询调研提纲拟定列表
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    List<TResearchOutlineGzz> getResearchOutlineGzzListByIdA(Integer idA)throws Exception;

    /**
     * 根据项目idA查询绩效评价调研函（工作组）
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
     TResearchLetterGzz  getResearchLetterGzzByIdA(Integer idA)throws Exception;


    /**
     * 根据项目idA查询工作任务树列表
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    public List<TPerformanceTaskAllocation> getTaskAllocationTrees(Integer idA)throws Exception;

    /**
     * 根据项目parentProCode查询分配工作的列表信息
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param parentProCode
     * @throws Exception
     */
    public List<TProPerformanceInfo> findToTaskAllocationListByParentProCode(String parentProCode) throws Exception;

     /**
     * 组建工分配任务
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param isTaskAssigned  是否已分配绩效任务  Y-保存， Z-暂存
     * @param proPerformanceInfoList
     * @throws Exception
     */
    void saveTPerformanceTaskAllocation (String isTaskAssigned, List<TProPerformanceInfo> proPerformanceInfoList, ShiroUser user)throws Exception;
}
