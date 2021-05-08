package com.common.business.library.indexs.service;

import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 绩效指标库 服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
public interface TLibraryIndexSystemService extends IService<TLibraryIndexSystem> {

    /**
     * 1-绩效指标库 主页面 树状显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 21:08
     * @updateTime 2021/4/25 21:08
     */
    PageInfo<TLibraryIndexSystem> indexSystemPage(Integer current, Integer size, TLibraryIndexSystem libraryIndexSystem, String search) throws Exception;
}
