package com.common.business.planmgr.pre.mkletter.service;

import com.common.business.index.entity.TOperationManualRegulation;
import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 调研函模板表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
public interface TResearchLetterTempService extends IService<TResearchLetterTemp> {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<TResearchLetterTemp> listForPage(Integer pageNum, Integer pageSize, TResearchLetterTemp bean) throws  Exception;
}
