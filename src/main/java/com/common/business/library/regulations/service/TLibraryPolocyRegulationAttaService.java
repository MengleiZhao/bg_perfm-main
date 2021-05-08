package com.common.business.library.regulations.service;

import com.common.business.library.regulations.entity.TLibraryPolocyRegulationAtta;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 政策法规附件表 服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-28
 */
public interface TLibraryPolocyRegulationAttaService extends IService<TLibraryPolocyRegulationAtta> {
    /**
     * 1-查询传进来的idX集合下的附件集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 14:10
     * @updateTime 2021/4/28 14:10
     */
    List<TLibraryPolocyRegulationAtta> queryByIdXs(List<String> chooseIdXs) throws  Exception;
}
