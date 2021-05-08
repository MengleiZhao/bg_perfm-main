package com.common.business.project.approval.service;

import com.common.business.project.approval.entity.TMainProjectSync;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-09
 */
public interface TMainProjectSyncService extends IService<TMainProjectSync> {


    /**
     * 分页查询主项目
     * @param pageNum
     * @param pageSize
     * @param bean
     * @return
     */
    PageInfo<TMainProjectSync> listForPage(Integer pageNum, Integer pageSize, TMainProjectSync bean, String search);
}
