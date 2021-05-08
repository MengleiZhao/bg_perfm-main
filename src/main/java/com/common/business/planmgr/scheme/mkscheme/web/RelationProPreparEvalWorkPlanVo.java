package com.common.business.planmgr.scheme.mkscheme.web;

import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.entity.TPreparEvalWorkPlan;
import com.common.business.planmgr.schemecheck.entity.BussinessFlowPreparEvalWorkPlan;
import com.common.business.planmgr.schemecheck.entity.DesigneeRecPreparEvalWorkPlan;
import com.common.business.planmgr.schemecheck.entity.TPreparEvalWorkPlanCheckRec;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Title: RelationProPreparEvalWorkPlanVo
 * Description：编制评价工作方案接受数据类
 * Author: 陈睿超
 * Date: 2021/4/14 19:57
 * Updater: 陈睿超
 * Date: 2021/4/14 19:57
 * Company: 天职国际
 * Version:
 **/
@Data
@Accessors(chain = true)
public class RelationProPreparEvalWorkPlanVo {

    /**
     * 项目编制评价工作方案关系表
     */
    private RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan;
    /**
     * 编制评价工作方案
     */
    private List<TPreparEvalWorkPlan> preparEvalWorkPlanList;
    /**
     * 前端传过来的集合
     */
    private MultipartFile[] files;
    /**
     * 审批信息
     */
    private BussinessFlowPreparEvalWorkPlan bussinessFlowPreparEvalWorkPlan;
    /**
     * 指派记录
     */
    private DesigneeRecPreparEvalWorkPlan designeeRecPreparEvalWorkPlan;
    /**
     * 审批记录
     */
    private TPreparEvalWorkPlanCheckRec preparEvalWorkPlanCheckRec;
    
    
}
