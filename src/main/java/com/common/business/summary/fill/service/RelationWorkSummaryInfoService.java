package com.common.business.summary.fill.service;

import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.summary.fill.entity.RelationWorkSummaryInfo;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.summary.fill.entity.TWorkSummaryInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 项目编写工作总结关系表 服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
public interface RelationWorkSummaryInfoService extends IService<RelationWorkSummaryInfo> {

    /**
     * 1-编写工作总结 主页面显示（分页显示）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 15:55
     * @updateTime 2021/4/23 15:55
     */
    PageInfo<TProPerformanceInfo> summaryPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId) throws Exception;

    /**
     * 2-选择需要编写工作总结的项目(分页显示)
     * 约束条件：编写评价报告 审核通过的情况下才可以编写工作总结、当前登录人是外勤主管
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 17:23
     * @updateTime 2021/4/23 17:23
     */
    PageInfo<TProPerformanceInfo> chooseSummaryProject(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId)throws Exception;


    /**
     * 3-暂存/提交
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 18:02
     * @updateTime 2021/4/23 18:02
     */
    void tempAndSubmit(RelationWorkSummaryInfo relationWorkSummaryInfo, ShiroUser user) throws Exception;
}
