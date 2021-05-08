package com.common.business.planmgr.pre.mkoutlinecheck.web;


import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.mapper.TResearchScheduleMapper;
import com.common.business.planmgr.pre.mkinvarrcheck.entity.TResearchScheduleCheckRec;
import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.common.business.planmgr.pre.mkoutlinecheck.entity.TResearchOutlineCheckRec;
import com.common.business.planmgr.pre.mkoutlinecheck.service.TResearchOutlineCheckRecService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 调研报告审批记录表 前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/tResearchOutlineCheckRec")
@Api(value = "/tResearchOutlineCheckRec",tags = "T-审核调研提纲")
public class TResearchOutlineCheckRecController extends BaseController {

    @Autowired
    private TResearchOutlineCheckRecService researchOutlineCheckRecService;//调研审核 service

    /**
     * 1-审批调研提纲 主页面显示
     * 约束条件：审批人是当前登录对象
     * @param preOrScheme 0-预调研  1-编制评价方案
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 16:03
     * @updateTime 2021/4/22 16:03
     */
    @RequestMapping(value = "/checkOutlinePage",method= RequestMethod.POST)
    @ApiOperation("1-审批调研提纲 主页面显示")
    public Result checkOutlinePage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TProPerformanceInfo proPerformanceInfo,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value="preOrScheme") Integer preOrScheme
    ){
        try {
            if(proPerformanceInfo==null){
                proPerformanceInfo=new TProPerformanceInfo();
            }
            PageInfo<TProPerformanceInfo> pageInfo=researchOutlineCheckRecService.checkOutlinePage(current,size,proPerformanceInfo,search,preOrScheme,getUser());
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
     * @createTime 2021/4/22 16:20
     * @updateTime 2021/4/22 16:20
     */
    @ApiOperation("2-审批流数据")
    @RequestMapping(value = "/queryFlowCheck",method = RequestMethod.POST)
    public Result queryFlowCheck(Integer idR){
        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的拟定调研提纲主键值","");
            }
            RelationProResearchOutline rsearchOutline=researchOutlineCheckRecService.queryFlowCheck(idR);
            Result result=new Result();
            result.setData(rsearchOutline);
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
    public Result checkResearch(@RequestBody TResearchOutlineCheckRec researchOutlineCheckRec){
        try {
            //判断前端传递的值是否正确
            if(researchOutlineCheckRec.getIdR()==null){
                return getJsonResult(false,"请传递正确的拟定调研提纲关系表主键值","");
            }else if(null==researchOutlineCheckRec.getCheckResult() || "".equals(researchOutlineCheckRec.getCheckResult())){
                return getJsonResult(false,"请传递正确的审批结果：0-不通过  1-通过","");
            }
            //调用服务层的方法，进行审批
            researchOutlineCheckRecService.checkOutline(researchOutlineCheckRec,getUser());
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
     * @createTime 2021/4/22 17:39
     * @updateTime 2021/4/22 17:39
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
            List<TPerformanceWorkingGroup> transPeople=researchOutlineCheckRecService.chooseTransPeople(current,size,workingGroup,search,idA,getUser());
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
     * @createTime 2021/4/22 17:42
     * @updateTime 2021/4/22 17:42
     */
    @ApiOperation("5-转派")
    @RequestMapping(value = "/transfer",method = RequestMethod.POST)
    public Result transfer(Integer idR,@RequestBody TPerformanceWorkingGroup workingGroup){
        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的拟定调研安排表主键值","");
            }
            researchOutlineCheckRecService.transfer(idR,workingGroup,getUser());
            return getJsonResult(true,"转派成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"转派失败","");
        }

    }









}
