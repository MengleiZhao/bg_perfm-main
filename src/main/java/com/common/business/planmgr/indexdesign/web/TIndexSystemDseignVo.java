package com.common.business.planmgr.indexdesign.web;

import com.common.business.planmgr.indexdesign.entity.TIndexScoringPoints;
import com.common.business.planmgr.indexdesign.entity.TAssessmentObjectByIndex;
import com.common.business.planmgr.indexdesign.entity.TEvidencePools;
import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import lombok.Data;

import java.util.List;

@Data
public class TIndexSystemDseignVo {

    /**
     * 子项目id
     */
    private Integer idA;
    /**
     * 版本状态： -1：退货，0：暂存，1：审批中，2：已完成
     */
    private String stauts;

    /**
     * 指标体系集合
     */
    private List<TIndexSystemDseign> indexSystemDseignList;
    /**
     * 佐证材料池集合
     */
    private List<TEvidencePools> evidencePoolsList;
    /**
     * 考核对象集合
     */
    private List<TAssessmentObjectByIndex> assessmentObjectList;
    /**
     * 评分要点集合
     */
    private List<TIndexScoringPoints> scoringPointsList;
    /**
     * 绩效指标对象
     */
    private TIndexSystemDseign systemDseign;

    /**
     * 分值设置
     */
    private String scoreType;
    /**
     * 设计人
     */
    private String createUaseName;
    /**
     * 设计人ID
     */
    private String createUaseId;
    /**
     * 老数据集合
     */
    private  List<TIndexSystemDseign> oldList;
    /**
     * 被删除的指标编码串
     */
    private   String deleteCodeStr;

    /**
     * 考核对象老数据集合
     */
    private  List<TAssessmentObjectByIndex> oldObjectList;
    /**
     * 考核对象被删除的指标编码串
     */
    private   String deleteIdStr;
    /**
     * 佐证材料池老数据集合
     */
    private  List<TEvidencePools> oldEvidencePoolList;

}
