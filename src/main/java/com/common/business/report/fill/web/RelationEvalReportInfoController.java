package com.common.business.report.fill.web;


import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.service.TProPerformanceInfoService;
import com.common.business.report.fill.entity.RelationEvalReportInfo;
import com.common.business.report.fill.service.RelationEvalReportInfoService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import com.sun.swing.internal.plaf.basic.resources.basic_es;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 项目编写评价报告关系表 前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/relationEvalReportInfo")
public class RelationEvalReportInfoController extends BaseController {

    @Autowired
    private RelationEvalReportInfoService relationProPreparEvalWorkPlan;
    @Autowired
    private TProPerformanceInfoService proPerformanceInfoService;
    
    /**
     * Title: 
     * Description: 项目编写评价报告列表分页查询方法
     * @author: 陈睿超
     * @createDate: 2021/4/22 17:39
     * @updater: 陈睿超
     * @updateDate: 2021/4/22 17:39
     * @param start
     * @param pageSize
     * @param search:模糊查询
     * @param relationEvalReportInfo :精确查询
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "项目编写评价报告列表分页查询方法")
    @PostMapping(value = "page")
    public Result page(@RequestParam(value = "current", defaultValue = "1") int start,
                       @RequestParam(value = "size", defaultValue = "10") int pageSize,
                       @RequestParam(value = "date", required = false) String date,
                       @RequestParam(value = "search", required = false) String search,
                       HttpServletRequest request, RelationEvalReportInfo relationEvalReportInfo){
        try {
            PageInfo<RelationEvalReportInfo> page = relationProPreparEvalWorkPlan.pageList(start, pageSize, search, relationEvalReportInfo, getUser());
            return getJsonResult(true,null,page);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * Title: chooseProPage
     * Description: 新增项目编写评价报告时查询项目
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @param preOrScheme
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "新增项目编写评价报告时查询项目",notes = "")
    @PostMapping(value = "chooseProPage")
    public Result chooseProPage(@RequestParam(value = "current", defaultValue = "1") int start,
                                @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                @RequestParam(value = "date", required = false) String date,
                                @RequestParam(value = "search", required = false) String search,
                                HttpServletRequest request, TProPerformanceInfo bean, Integer preOrScheme){
        try {
            PageInfo<TProPerformanceInfo> list = proPerformanceInfoService.choosePreparEvalWorkPro(start, pageSize, preOrScheme,
                    bean, search,getUser());
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }
    
    
    
    
    
    
}
