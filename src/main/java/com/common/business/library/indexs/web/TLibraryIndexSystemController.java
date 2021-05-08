package com.common.business.library.indexs.web;


import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.library.indexs.service.TLibraryIndexSystemService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 绩效指标库 前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@RestController
@Api(value = "/tLibraryIndexSystem",tags = "T-绩效指标库")
@RequestMapping("/tLibraryIndexSystem")
public class TLibraryIndexSystemController extends BaseController {

    @Autowired
    private TLibraryIndexSystemService libraryIndexSystemService;//绩效指标库 service

    /**
     * 1-绩效指标库 主页面 树状显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 21:00
     * @updateTime 2021/4/25 21:00
     */
    @ApiOperation("1-绩效指标库 主页面 树状显示")
    @RequestMapping(value = "/indexSystemPage",method = RequestMethod.POST)
    public Result indexSystemPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TLibraryIndexSystem libraryIndexSystem,
            @RequestParam(value = "search", required = false) String search
    ){
        try {
            if(libraryIndexSystem==null){
                libraryIndexSystem=new TLibraryIndexSystem();
            }
            PageInfo<TLibraryIndexSystem> pageInfo=libraryIndexSystemService.indexSystemPage(current,size,libraryIndexSystem,search);
            //if (pageInfo!=null && pageInfo.getSize() > 0) {
                //3.返回
                return getJsonResult(true,"查询成功",pageInfo);


        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
	
}
