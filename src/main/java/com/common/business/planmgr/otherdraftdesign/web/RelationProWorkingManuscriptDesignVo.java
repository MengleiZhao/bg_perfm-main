package com.common.business.planmgr.otherdraftdesign.web;

import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignRoutine;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignOther;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignResearch;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignSuggest;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 指标底稿设计 前端接收参数对象
 * </p>
 *
 * @author 安达
 * @since 2021-03-26
 */
@Data
public class RelationProWorkingManuscriptDesignVo {

    /**
     * 子项目id
     */
    private Integer idA;
    /**
     * 版本状态： -1：退回，0：暂存，1：审批中，2：已完成
     */
    private String stauts;
    /**
     * 其他底稿类型 1：问题建议类底稿，2：调研总结类底稿，3：其他类底稿
     */
    private String indexWorkingPaperType;
    /**
     * 问题建议类底稿 对象
     */
    private TProWorkingManuscriptDesignSuggest proWorkingManuscriptDesignSuggest;
    /**
     * 调研总结类底稿 对象
     */
    private TProWorkingManuscriptDesignResearch proWorkingManuscriptDesignResearch;
    /**
     * 其他类底稿 对象
     */
    private List<TProWorkingManuscriptDesignOther> proWorkingManuscriptDesignOtherList;
    /**
     * 前端传过来的文件集合
     */
    private MultipartFile[] files;

}
