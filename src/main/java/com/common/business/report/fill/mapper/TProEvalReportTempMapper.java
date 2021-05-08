package com.common.business.report.fill.mapper;

import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.common.business.report.fill.entity.TProEvalReportTemp;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 项目评价报告模板表 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
public interface TProEvalReportTempMapper extends BaseMapper<TProEvalReportTemp> {
    List<TProEvalReportTemp> listForPage(TProEvalReportTemp bean)  throws  Exception;
}