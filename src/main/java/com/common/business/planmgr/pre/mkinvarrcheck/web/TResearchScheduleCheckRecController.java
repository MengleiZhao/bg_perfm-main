package com.common.business.planmgr.pre.mkinvarrcheck.web;


import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.TResearchScheduleCheckRec;
import com.common.business.planmgr.pre.mkinvarrcheck.service.TResearchScheduleCheckRecService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 调研安排审批记录表 前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
@RequestMapping("/tResearchScheduleCheckRec")
@RestController
@Api(value = "/tResearchScheduleCheckRec",tags = {"T-拟定调研安排审批接口"})
public class TResearchScheduleCheckRecController extends BaseController {
    @Autowired
    private TResearchScheduleCheckRecService researchScheduleCheckRecService;//调研安排审批记录 服务层

    /**
     * 1.审批界面 主页面显示
     * 约束条件：审批人是当前登录对象
     * @param preOrScheme 0-预调研  1-编制评价方案
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 11:30
     * @updateTime 2021/4/19 11:30
     */
    @RequestMapping(value = "/checkPage",method=RequestMethod.POST)
    @ApiOperation("1-审批界面 主页面显示")
    public Result checkPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TProPerformanceInfo proPerformanceInfo,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value="preOrScheme") Integer preOrScheme
            ){
        if(proPerformanceInfo==null){
            proPerformanceInfo=new TProPerformanceInfo();
        }
        try {
            PageInfo<TProPerformanceInfo> pageInfo=researchScheduleCheckRecService.checkPage(current,size,proPerformanceInfo,search,preOrScheme,getUser());
            if(pageInfo!=null && pageInfo.getSize()>0){
                Result result=new Result();
                result.setData(pageInfo);
                return result;
            }else{
                return getJsonResult(false,"没有需要您进行审核的项目","");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }

    }


    /**
     * 2.审批流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 21:16
     * @updateTime 2021/4/19 21:16
     */
    @ApiOperation("2-审批流数据")
    @RequestMapping(value = "/queryFlowCheck",method = RequestMethod.POST)
    public Result queryFlowCheck(Integer idR){
        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的拟定调研安排表主键值","");
            }
            RelationProResearchSchedule researchSchedule=researchScheduleCheckRecService.queryFlowCheck(idR);
            Result result=new Result();
            result.setData(researchSchedule);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false ,"查询失败","");
        }
    }

    /**
     * 3.审批
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/20 9:25
     * @updateTime 2021/4/20 9:25
     */
    @ApiOperation("3-审批")
    @RequestMapping(value = "/checkResearch",method = RequestMethod.POST)
    public Result checkResearch(@RequestBody TResearchScheduleCheckRec researchScheduleCheck){
        try {
            //判断前端传递的值是否正确
            if(researchScheduleCheck.getIdR()==null){
                return getJsonResult(false,"请传递正确的拟定调研安排关系表主键值","");
            }else if(null==researchScheduleCheck.getCheckResult() || "".equals(researchScheduleCheck.getCheckResult())){
                return getJsonResult(false,"请传递正确的审批结果：0-不通过  1-通过","");
            }
            //调用服务层的方法，进行审批
            researchScheduleCheckRecService.checkResearch(researchScheduleCheck,getUser());
            return getJsonResult(true,"审批成功","");

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"审批失败","");
        }
    }

    /**
     * 4-选择被转派人(分页显示)除去当前登录人
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/20 9:48
     * @updateTime 2021/4/20 9:48
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
            List<TPerformanceWorkingGroup> transPeople=researchScheduleCheckRecService.chooseTransPeople(current,size,workingGroup,search,idA,getUser());
            Result result=new Result();
            result.setData(transPeople);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * 5.转派
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/20 9:34
     * @updateTime 2021/4/20 9:34
     */
    @ApiOperation("5-转派")
    @RequestMapping(value = "/transfer",method = RequestMethod.POST)
    public Result transfer(Integer idR,@RequestBody TPerformanceWorkingGroup workingGroup){
        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的拟定调研安排表主键值","");
            }
            researchScheduleCheckRecService.transfer(idR,workingGroup,getUser());
            return getJsonResult(true,"转派成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"转派失败","");
        }

    }














}
