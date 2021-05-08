package com.common.business.summary.fillcheck.web;


import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.summary.fill.entity.RelationWorkSummaryInfo;
import com.common.business.summary.fillcheck.entity.TWorkSummaryCheckRec;
import com.common.business.summary.fillcheck.service.TWorkSummaryCheckRecService;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 工作总结审批记录表 前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-23
 */
@RestController
@Api(value = "/tWorkSummaryCheckRec",tags = "T-工作总结审核接口")
@RequestMapping("/tWorkSummaryCheckRec")
public class TWorkSummaryCheckRecController extends BaseController {
	@Autowired
    private TWorkSummaryCheckRecService workSummaryCheckRecService;//工作审核 service

    /**
     * 1-审核工作总结 主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 11:16
     * @updateTime 2021/4/25 11:16
     */
    @RequestMapping(value = "/summaryCheckPage",method = RequestMethod.POST)
    @ApiOperation("1-审核工作总结 主页面显示")
    public Result summaryCheckPage(
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
            if(StringUtils.isNotEmpty(userId)){
                userId=String.valueOf(getUser().getId());
            }
            PageInfo<TProPerformanceInfo> performanceInfos=workSummaryCheckRecService.summaryCheckPage(current,size,proPerformanceInfo,search,userId);
            if(performanceInfos.getSize()>0){
                Result result=new Result();
                result.setData(performanceInfos);
                return result;
            }else{
                return getJsonResult(true,"还没有需要您进行审核工作总结的项目","");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     * 2-审批流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 11:53
     * @updateTime 2021/4/25 11:53
     */
    @ApiOperation("2-审批流数据")
    @RequestMapping(value = "/queryFlowCheck",method = RequestMethod.GET)
    public Result queryFlowCheck(Integer idR){
        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的工作总结关系表主键值","");
            }
            RelationWorkSummaryInfo workSummaryInfo=workSummaryCheckRecService.queryFlowCheck(idR);
            Result result=new Result();
            result.setData(workSummaryInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }

    }


    /**
     * 3-审批
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 13:52
     * @updateTime 2021/4/25 13:52
     */
    @RequestMapping(value = "/checkSummary",method = RequestMethod.POST)
    @ApiOperation("3-审批")
    public Result checkSummary(@RequestBody TWorkSummaryCheckRec workSummaryCheckRec){
        try {
            //判断前端传递的值是否正确
            if(workSummaryCheckRec.getIdR()==null){
                return getJsonResult(false,"请传递正确的工作总结关系表主键值","");
            }else if(null==workSummaryCheckRec.getCheckResult() || "".equals(workSummaryCheckRec.getCheckResult())){
                return getJsonResult(false,"请传递正确的审批结果：0-不通过  1-通过","");
            }
            workSummaryCheckRecService.checkSummary(workSummaryCheckRec,getUser());
            return getJsonResult(true,"操作成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"操作失败","");
        }
    }


    /**
     * 4-选择被转派人(分页显示)除去当前登录人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:03
     * @updateTime 2021/4/25 15:03
     */
    @ApiOperation("4-选择被转派人(分页显示)除去当前登录人")
    @RequestMapping(value = "/chooseTransPeople",method = RequestMethod.POST)
    public Result chooseTransPeople(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TPerformanceWorkingGroup workingGroup,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "idA")Integer idA){
        try {
            if(workingGroup==null){
                workingGroup=new TPerformanceWorkingGroup();
            }
            if(idA==null){
                return getJsonResult(false,"请传递正确的项目主键值","");
            }
            List<TPerformanceWorkingGroup> transPeople=workSummaryCheckRecService.chooseTransPeople(current,size,workingGroup,search,idA,getUser());
            Result result=new Result();
            result.setData(transPeople);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     * 5-转派
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 15:05
     * @updateTime 2021/4/25 15:05
     */
    @ApiOperation("5-转派")
    @RequestMapping(value = "/transfer",method = RequestMethod.POST)
    public Result transfer(Integer idR,@RequestBody TPerformanceWorkingGroup workingGroup){
        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的拟定调研安排表主键值","");
            }
            workSummaryCheckRecService.transfer(idR,workingGroup,getUser());
            return getJsonResult(true,"转派成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"转派失败","");
        }

    }



}
