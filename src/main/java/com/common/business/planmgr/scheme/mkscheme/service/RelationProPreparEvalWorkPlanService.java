package com.common.business.planmgr.scheme.mkscheme.service;

import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.scheme.mkscheme.entity.TPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.web.RelationProPreparEvalWorkPlanVo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.page.Result;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  项目编制评价工作方案关系表
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
public interface RelationProPreparEvalWorkPlanService extends IService<RelationProPreparEvalWorkPlan> {


    /**
     * 项目编制评价工作方案列表分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/8 16:09
     * @updater: 陈睿超
     * @updateDate: 2021/4/8 16:09
     * @param pageNum
     * @param pageSize
     * @param search 模糊查询
     * @param bean 精确查询
     * @param user 当前用户
     * @return 
     **/
    PageInfo<RelationProPreparEvalWorkPlan> pagelist(Integer pageNum, Integer pageSize, String search, RelationProPreparEvalWorkPlan bean, ShiroUser user);
    
    /**
     * Title: checkPagelist
     * Description: 待审批分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/21 17:44
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 17:44
     * @param pageNum
     * @param pageSize
     * @param search 模糊查询
     * @param bean 精确查询
     * @param user 当前用户
     * @return 
     **/
    PageInfo<RelationProPreparEvalWorkPlan> checkPagelist(Integer pageNum, Integer pageSize, String search, RelationProPreparEvalWorkPlan bean, ShiroUser user);

    /**
     * 保存
     * @author: 陈睿超
     * @createDate: 2021/4/14 20:07
     * @updater: 陈睿超
     * @updateDate: 2021/4/14 20:07
     * @param relationProPreparEvalWorkPlanVo:编制评价工作方案接受数据类
     * @param user:当前登录人
     * @return 成功返回true,失败返回false
     **/
    Boolean save(RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo, ShiroUser user);


    /**
     * 组装需要替换文字
     * @return
     */
    Map<String ,Object> ExportWordData(TProPerformanceInfo pro );

    /**
     * 组装需要替换段落
     * @param pro
     * @return
     */
    Map<String ,Object> ExportWordTextMap(TProPerformanceInfo pro );
    
}
