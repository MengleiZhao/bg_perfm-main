package com.common.business.workgroup.taskmgr.web;

import com.common.business.project.approval.entity.TProPerformanceInfo;
import lombok.Data;

import java.util.List;

@Data
public class TPerformanceTaskAllocationVo {

    /**
     *是否已分配任务
     * Y-分配完毕，组建工作组完成后更新为Y
     * Z-分配中，如果是暂存，则更新未Z
     * N-否（默认）
     */
    private String isTaskAssigned;

    /**
     * 立项项目集合
     */
    private List<TProPerformanceInfo> proPerformanceInfoList;


}
