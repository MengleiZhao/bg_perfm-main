package com.common.business.planmgr.pre.mkoutlinecheck.service;

import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.common.business.planmgr.pre.mkoutlinecheck.entity.TResearchOutlineCheckRec;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 调研报告审批记录表 服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
public interface TResearchOutlineCheckRecService extends IService<TResearchOutlineCheckRec> {

    /**
     * 1.审批界面 主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 16:05
     * @updateTime 2021/4/22 16:05
     */
    PageInfo<TProPerformanceInfo> checkOutlinePage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, Integer preOrScheme, ShiroUser user)throws Exception;

    /**
     * 2.审批流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 16:21
     * @updateTime 2021/4/22 16:21
     */
    RelationProResearchOutline queryFlowCheck(Integer idR)throws Exception;

    /**
     * 3.审批
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 16:52
     * @updateTime 2021/4/22 16:52
     */
    @Transactional
    void checkOutline(TResearchOutlineCheckRec researchOutlineCheckRec, ShiroUser user)throws Exception;

    /**
     * 4.选择被转派人(分页显示)除去当前登录人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:40
     * @updateTime 2021/4/22 17:40
     */
    List<TPerformanceWorkingGroup> chooseTransPeople(Integer current, Integer size, TPerformanceWorkingGroup workingGroup, String search, Integer idA, ShiroUser user)throws Exception;

    /**
     * 5.转派
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 17:43
     * @updateTime 2021/4/22 17:43
     */
    void transfer(Integer idR, TPerformanceWorkingGroup workingGroup, ShiroUser user) throws Exception;
}
