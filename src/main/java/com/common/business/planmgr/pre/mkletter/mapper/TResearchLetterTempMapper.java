package com.common.business.planmgr.pre.mkletter.mapper;

import com.common.business.index.entity.TOperationManualRegulation;
import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 调研函模板表 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
public interface TResearchLetterTempMapper extends BaseMapper<TResearchLetterTemp> {
    List<TResearchLetterTemp> listForPage(TResearchLetterTemp bean)  throws  Exception;
}