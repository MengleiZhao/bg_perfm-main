package com.common.business.planmgr.indexdraftdesign.web;

import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignOther;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignRoutine;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignSatisfaction;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 其他项目底稿关系表 前端接收参数对象
 * </p>
 *
 * @author 安达
 * @since 2021-03-26
 */
@Data
public class RelationIndexWorkingManuscriptDesignVo {
    /**
     * 指标体系设计表 id
     */
    private Integer idB;
    /**
     * 版本状态： -1：退货，0：暂存，1：审批中，2：已完成
     */
   private String stauts;
    /**
     * 指标工作底稿类型
     */
   private String indexWorkingPaperType;
    /**
     * 常规类底稿 对象
     */
    private TIndexWorkingManuscriptDesignRoutine indexWorkingManuscriptDesignRoutine;
    /**
    *满意度底稿集合
     */
    private List<TIndexWorkingManuscriptDesignSatisfaction> designSatisfactionList;

    /**
     * 其他类底稿集合
     */
    private List<TIndexWorkingManuscriptDesignOther>  designOtherList;
}
