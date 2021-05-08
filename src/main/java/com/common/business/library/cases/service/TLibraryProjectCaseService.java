package com.common.business.library.cases.service;

import com.common.business.library.cases.entity.TLibraryProjectCase;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-17
 */
public interface TLibraryProjectCaseService extends IService<TLibraryProjectCase> {

    /**
     * @Title:
     * @Description: 分页查询案例库
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @author: 陈睿超
     * @createDate: 2021/3/24 11:03
     * @updater: 陈睿超
     * @updateDate: 2021/3/24 11:03
     * @return
     */
    PageInfo<TLibraryProjectCase> findPagelist(Integer pageNum, Integer pageSize, String search, TLibraryProjectCase bean);
}
