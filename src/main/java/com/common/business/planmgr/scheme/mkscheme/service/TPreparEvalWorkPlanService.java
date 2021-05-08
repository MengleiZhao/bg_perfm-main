package com.common.business.planmgr.scheme.mkscheme.service;

import com.common.business.planmgr.scheme.mkscheme.entity.TPreparEvalWorkPlan;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.scheme.mkscheme.web.RelationProPreparEvalWorkPlanVo;
import com.common.system.shiro.ShiroUser;

import java.util.List;

/**
 * <p>
 *  编制评价工作方案
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
public interface TPreparEvalWorkPlanService extends IService<TPreparEvalWorkPlan> {




    /**
     * 上传附件
     * @param relationProPreparEvalWorkPlanVo 接收前台传过来数据对象
     * @param user 当前登录人
     * @return 返回附件id集合字符串
     * @author: 陈睿超
     * @createDate: 2021/4/14 20:07
     * @updater: 陈睿超
     * @updateDate: 2021/4/14 20:07
     */
    List<TPreparEvalWorkPlan> upLoadFiles(RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo, ShiroUser user) throws Exception;

    
}
