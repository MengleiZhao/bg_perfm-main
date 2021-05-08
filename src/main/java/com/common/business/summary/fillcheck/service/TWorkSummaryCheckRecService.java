package com.common.business.summary.fillcheck.service;

import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.summary.fill.entity.RelationWorkSummaryInfo;
import com.common.business.summary.fillcheck.entity.TWorkSummaryCheckRec;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 工作总结审批记录表 服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
public interface TWorkSummaryCheckRecService extends IService<TWorkSummaryCheckRec> {

    /**
     * 1-审核工作总结 主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 11:21
     * @updateTime 2021/4/25 11:21
     */
    PageInfo<TProPerformanceInfo> summaryCheckPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId)throws  Exception;

    /**
     * 2-审批流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 11:59
     * @updateTime 2021/4/25 11:59
     */
    RelationWorkSummaryInfo queryFlowCheck(Integer idR) throws Exception;

    /**
     * 3-审批
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 13:58
     * @updateTime 2021/4/25 13:58
     */
    void checkSummary(TWorkSummaryCheckRec workSummaryCheckRec, ShiroUser user) throws Exception;

    /**
     * 4-选择被转派人(分页显示)除去当前登录人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:04
     * @updateTime 2021/4/25 15:04
     */
    List<TPerformanceWorkingGroup> chooseTransPeople(Integer current, Integer size, TPerformanceWorkingGroup workingGroup, String search, Integer idA, ShiroUser user)throws Exception;

    /**
     * 5-转派
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:06
     * @updateTime 2021/4/25 15:06
     */
    void transfer(Integer idR, TPerformanceWorkingGroup workingGroup, ShiroUser user)throws Exception;
}
