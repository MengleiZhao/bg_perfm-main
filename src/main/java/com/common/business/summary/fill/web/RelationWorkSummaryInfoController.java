package com.common.business.summary.fill.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.summary.fill.entity.RelationWorkSummaryInfo;
import com.common.business.summary.fill.entity.TWorkSummaryInfo;
import com.common.business.summary.fill.service.RelationWorkSummaryInfoService;
import com.common.business.summary.fill.service.TWorkSummaryInfoService;
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
 * 项目编写工作总结关系表 前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
@RestController
@Api(value = "/relationWorkSummaryInfo",tags = "T-编写工作总结接口")
@RequestMapping("/relationWorkSummaryInfo")
public class RelationWorkSummaryInfoController extends BaseController {

    @Autowired
    private RelationWorkSummaryInfoService relationWorkSummaryInfoService;//编写工作总结关系表 服务层
    @Autowired
    private TWorkSummaryInfoService workSummaryInfoService;//工作总结表 服务层



    /**
     * 1-编写工作总结 主页面显示（分页显示）
     * 约束条件：编写评价报告 审核通过的情况下才可以编写工作总结、当前登录人是外勤主管
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 15:11
     * @updateTime 2021/4/23 15:11
     */
    @RequestMapping(value = "/summaryPage",method = RequestMethod.POST)
    @ApiOperation("1-编写工作总结 主页面显示")
    public Result summaryPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TProPerformanceInfo proPerformanceInfo,
            @RequestParam(value = "search", required = false) String search,
            String userId
    ){
        try {
            if(proPerformanceInfo==null){
                proPerformanceInfo=new TProPerformanceInfo();
            }
            if(userId==null || userId==""){
                userId=String.valueOf(getUser().getId());
            }
            PageInfo<TProPerformanceInfo> summaries=relationWorkSummaryInfoService.summaryPage(current,size,proPerformanceInfo,search,userId);
            Result result=new Result();
            result.setData(summaries);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     * 2-选择需要编写工作总结的项目(分页显示)
     * 约束条件：编写评价报告 审核通过的情况下才可以编写工作总结、当前登录人是外勤主管
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 15:44
     * @updateTime 2021/4/23 15:44
     */
    @RequestMapping(value = "/chooseSummaryProject",method =RequestMethod.POST)
    @ApiOperation( "2-选择需要编写工作总结的项目(分页显示)")
    public Result chooseSummaryProject(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TProPerformanceInfo proPerformanceInfo,
            @RequestParam(value = "search", required = false) String search,
            String userId
    ){
        try {
            if(proPerformanceInfo==null){
                proPerformanceInfo=new TProPerformanceInfo();
            }
            if(userId==null || userId==""){
                userId=String.valueOf(getUser().getId());
            }
            PageInfo<TProPerformanceInfo> chooseSummaryProject=relationWorkSummaryInfoService.chooseSummaryProject(current,size,proPerformanceInfo,search,userId);
            Result result=new Result();
            result.setData(chooseSummaryProject);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     * 3-查看工作总结详情
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 17:44
     * @updateTime 2021/4/23 17:44
     */
    @RequestMapping(value = "/showSummary",method = RequestMethod.GET)
    @ApiOperation("3-查看工作总结详情")
    public Result showSummary(Integer idR){
        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的工作总结关系表主键值","");
            }
            EntityWrapper idRwrapper=new EntityWrapper();
            idRwrapper.eq("ID_R",idR);
            TWorkSummaryInfo workSummaryInfo=workSummaryInfoService.selectOne(idRwrapper);
            Result result=new Result();
            result.setData(workSummaryInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * 4-暂存/提交
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 17:56
     * @updateTime 2021/4/23 17:56
     */
    @RequestMapping(value = "/tempAndSubmit",method = RequestMethod.POST)
    @ApiOperation("4-暂存/提交")
    public Result tempAndSubmit(@RequestBody RelationWorkSummaryInfo relationWorkSummaryInfo){
        try {
            if(relationWorkSummaryInfo.getIdA()==null){
                return getJsonResult(false,"请传递正确主子项目主键值","");
            }
            relationWorkSummaryInfoService.tempAndSubmit(relationWorkSummaryInfo,getUser());
            return getJsonResult(true,"操作成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"操作失败","");
        }
    }


	
}
