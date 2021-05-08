package com.common.business.library.regulations.service;

import com.common.business.library.regulations.entity.TAdministrativeRegionInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-27
 */
public interface TAdministrativeRegionInfoService extends IService<TAdministrativeRegionInfo> {
    /**
     * 1-查询省市 (树状）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 13:46
     * @updateTime 2021/4/27 13:46
     */
    List<TAdministrativeRegionInfo> queryProvinceCity() throws Exception;
}
