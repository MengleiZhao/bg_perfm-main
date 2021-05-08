package com.common.business.report.fill.service;

import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.common.business.report.fill.entity.TProEvalReportTemp;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 项目评价报告模板表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
public interface TProEvalReportTempService extends IService<TProEvalReportTemp> {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<TProEvalReportTemp> listForPage(Integer pageNum, Integer pageSize, TProEvalReportTemp bean) throws  Exception;
}
