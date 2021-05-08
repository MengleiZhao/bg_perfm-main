package com.common.business.library.uptrequest.web;


import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.library.uptrequest.service.BussinessFlowLibraryIndexSystemService;
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
 *  前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@RestController
@Api(value = "/bussinessFlowLibraryIndexSystem",tags = "T-绩效指标申请")
@RequestMapping("/bussinessFlowLibraryIndexSystem")
public class BussinessFlowLibraryIndexSystemController extends BaseController {
    @Autowired
    private BussinessFlowLibraryIndexSystemService libraryIndexSystemService;//业务服务层

    /**
     * 1-申请主页面显示
     * 约束条件：除了出库已审批的，其他的都显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/26 11:56
     * @updateTime 2021/4/26 11:56
     */
    @ApiOperation("1-申请主页面显示")
    @RequestMapping(value = "/applicyPage",method = RequestMethod.POST)
    public Result applicyPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TLibraryIndexSystem libraryIndexSystem,
            @RequestParam(value = "search", required = false) String search
    ){
        try {
            if(libraryIndexSystem==null){
                libraryIndexSystem=new TLibraryIndexSystem();
            }
            PageInfo<TLibraryIndexSystem> libraryIndexSystemPageInfo= libraryIndexSystemService.applicyPage(current,size,libraryIndexSystem,search);
            if(libraryIndexSystemPageInfo.getSize()>0){
                Result result=new Result();
                result.setData(libraryIndexSystemPageInfo);
                return result;
            }else{
                return getJsonResult(true,"还没有申请的绩效指标库数据","");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * 3-绩效指标库 出库申请 提交/暂存
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 16:34
     * @updateTime 2021/4/27 16:34
     */
    public Result outIndexSystem(@RequestBody TLibraryIndexSystem indexSystem){
        try {
            if(indexSystem==null ||!"1".equals(indexSystem.getUptType())){
                return getJsonResult(false,"请传送正确的调整类型：1-出库申请","");
            }
            if(indexSystem==null || indexSystem.getIdX()==null){
                return getJsonResult(false,"请传要出库的指标体系主键值","");
            }
            libraryIndexSystemService.outIndexSystem(indexSystem,getUser());

            return getJsonResult(true,"出库操作成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"出库操作失败","");
        }

    }









	
}
