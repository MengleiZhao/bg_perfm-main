package com.common.business.index.service;

import com.common.business.index.entity.TOperationManualRegulation;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.sys.entity.RcDict;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 操作手册及业规管理 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
public interface TOperationManualRegulationService extends IService<TOperationManualRegulation> {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<TOperationManualRegulation> listForPage(Integer pageNum, Integer pageSize,TOperationManualRegulation bean) throws  Exception;
}
