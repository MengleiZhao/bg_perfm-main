package com.common.business.library.regulations.web;


import com.common.business.library.regulations.entity.TAdministrativeRegionInfo;
import com.common.business.library.regulations.service.TAdministrativeRegionInfoService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-27
 */
@RestController
@Api(value = "/tAdministrativeRegionInfo",tags = "省市接口")
@RequestMapping("/tAdministrativeRegionInfo")
public class TAdministrativeRegionInfoController extends BaseController {

    @Autowired
    private TAdministrativeRegionInfoService administrativeRegionInfoService;//省市service
    /**
     * 1-查询省市 (树状）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 11:32
     * @updateTime 2021/4/27 11:32
     */
    @ApiOperation("1-查询省市(树状)")
    @RequestMapping(value = "/queryProvincesCities", method = RequestMethod.POST)
    public Result queryProvinceCity() {
        try {
            List<TAdministrativeRegionInfo> administrativeRegionInfos=administrativeRegionInfoService.queryProvinceCity();
            Result result=new Result();
            result.setData(administrativeRegionInfos);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }

}
