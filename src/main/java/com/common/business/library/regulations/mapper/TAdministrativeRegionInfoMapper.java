package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.TAdministrativeRegionInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.HashMap;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-27
 */
public interface TAdministrativeRegionInfoMapper extends BaseMapper<TAdministrativeRegionInfo> {
    /**
     * 1-查询省市 (树状）
     * @param @MapKey("idX")//值是 实体类中的属性
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 13:54
     * @updateTime 2021/4/27 13:54
     */
    @MapKey("idX")//值是 实体类中的属性
    HashMap<Integer, TAdministrativeRegionInfo> queryProvinceCity();
}