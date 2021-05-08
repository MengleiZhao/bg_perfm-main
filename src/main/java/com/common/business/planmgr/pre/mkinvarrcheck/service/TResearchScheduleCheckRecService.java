package com.common.business.planmgr.pre.mkinvarrcheck.service;

import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.TResearchScheduleCheckRec;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 调研安排审批记录表 服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
public interface TResearchScheduleCheckRecService extends IService<TResearchScheduleCheckRec> {

    /**
     * 1.审批界面 主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 20:52
     * @updateTime 2021/4/19 20:52
     */
    PageInfo<TProPerformanceInfo> checkPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, Integer preOrScheme, ShiroUser user) throws Exception;

    /**
     * 2.审批流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 21:29
     * @updateTime 2021/4/19 21:29
     */
    RelationProResearchSchedule queryFlowCheck(Integer idR)throws  Exception;

    /**
     * 3.审批
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/20 10:01
     * @updateTime 2021/4/20 10:01
     */
    @Transactional
    void checkResearch(TResearchScheduleCheckRec researchScheduleCheck, ShiroUser user) throws  Exception;


    /**
     * 4.选择被转派人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 15:30
     * @updateTime 2021/4/21 15:30
     */
    List<TPerformanceWorkingGroup> chooseTransPeople(Integer current, Integer size, TPerformanceWorkingGroup workingGroup, String search, Integer idA,ShiroUser user)throws  Exception;

    /**
     * 5.转派
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 15:50
     * @updateTime 2021/4/21 15:50
     */
    void transfer(Integer idR, TPerformanceWorkingGroup workingGroup,ShiroUser user)throws  Exception;
}
