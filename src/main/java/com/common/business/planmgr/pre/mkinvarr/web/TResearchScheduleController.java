package com.common.business.planmgr.pre.mkinvarr.web;


import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.service.TResearchScheduleService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * TResearchScheduleController    [调研安排表 控制层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
@RestController
@RequestMapping("/tResearchSchedule")
@Api(value = "/tResearchSchedule",tags = {"T-拟定调研安排接口"})
public class TResearchScheduleController extends BaseController {

    @Autowired
    private TResearchScheduleService researchScheduleService;//拟定调研安排 服务层

    /**
     * ok
     * 1.TResearchScheduleController    [调研安排表 控制层]
     * 主页面显示
     * 约束条件：当前登录人是项目主管/或者组员、明确工作任务时有“拟定调研安排” 且已经勾选、已经立项、已经创建工作组、跟拟定关系表相关联
     * @param current   开始查询的页码数 默认为第1页
     * @param size      每页的大小  默认每页显示10条数据
     * @param search    综合查询的字段
     * @param preOrScheme 0-预调研   1-编制评价方案
     * @param userId    当前登录用户的id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 10:05
     * @updateTime 2021/4/19 10:03
     */
    @ApiOperation("1-拟定调研安排 主页面显示")
    @RequestMapping(value = "/researchPage", method = RequestMethod.POST)
    public Result researchPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) TProPerformanceInfo proPerformanceInfo,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value="preOrScheme") Integer preOrScheme,
            String userId) {
        try {
            if(proPerformanceInfo==null){
                proPerformanceInfo=new TProPerformanceInfo();
            }

            if(null==userId||"".equals(userId)){
                userId=String.valueOf(getUser().getId());
            }

            //1.调用服务层的方法拿到值
            PageInfo<TProPerformanceInfo> pageInfo = researchScheduleService.queryForPage(current, size, proPerformanceInfo, search, userId,preOrScheme);
            if (pageInfo != null && pageInfo.getSize() > 0) {
                //2.封装成 Result对象
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            } else {
                return getJsonResult(true, "您没有已经 拟定好的调研安排的项目", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }

    /**
     * 2.分页显示 选择要拟定调研安排的项目
     * @param preOrScheme 0-预调研   1-编制评价方案
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/15 14:23
     * @updateTime 2021/4/19 10:03
     */
    //@RequestParam 接收url地址中的参数
    @ApiOperation("2-分页显示 选择要拟定调研安排的项目")
    @RequestMapping(value = "/chooseRecarchProject",method = RequestMethod.POST)
    public Result chooseRecarchProject(@RequestParam(value = "current",defaultValue = "1") Integer current,
                                       @RequestParam(value = "size",defaultValue = "10") Integer size,
                                       TProPerformanceInfo proPerformanceInfo,
                                       @RequestParam(value = "search",required = false) String search,
                                       @RequestParam(value="preOrScheme")Integer preOrScheme){
        try {
            String userId=String.valueOf(getUser().getId());
            List<TProPerformanceInfo> proPerformanceInfos=researchScheduleService.chooseRecarchProject(current,size,userId,proPerformanceInfo,search,preOrScheme);
            if(proPerformanceInfos!=null && proPerformanceInfos.size()>0){
                Result result=new Result();
                result.setData(proPerformanceInfos);
                return result;
            }else{
                return getJsonResult(true,"没有需要您进行拟定调研安排的项目",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败",null);
        }
    }

    /**
     * 3.显示该idR下的原来的拟定表数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 14:01
     * @updateTime 2021/4/19 14:01
     */
    @ApiOperation("3-显示该idR下的原来的拟定表数据集合")
    @RequestMapping(value = "/showResearchs",method = RequestMethod.POST)
    public Result showResearchs(Integer idR){

        try {
            //调研安排集合
            List<TResearchSchedule> researchSchedules=researchScheduleService.showResearchs(idR);
            if(researchSchedules!=null && researchSchedules.size()>0){
                Result result=new Result();
                result.setData(researchSchedules);
                return result;
            }else{
                return getJsonResult(true,"该版本还没有组建调研安排",null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败",null);
        }

    }



    /**
     * 4.TResearchScheduleController    [调研安排表 控制层]
     * 选择组员或组长
     * @param researchVo
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 14:22
     * @updateTime 2021/4/19 10:05
     */
    @ApiOperation("4-选择组员或组长")
    @RequestMapping(value = "/chooseResearchPeople", method = RequestMethod.POST)
    public Result chooseResearchPeople(
            @RequestParam(value = "current" ,defaultValue = "1") Integer current,
            @RequestParam(value="size",defaultValue = "10") Integer size,
            @RequestBody(required = false) TPerformanceWorkingGroup workingGroup,
            @RequestParam(value="search",required = false) String search,
            @RequestBody ResearchScheduleVo researchVo) {
        try {
            if(workingGroup==null){
                workingGroup=new TPerformanceWorkingGroup();
            }

            Integer idA=researchVo.getIdA();
            if(idA!=null){
                PageInfo<TPerformanceWorkingGroup> performanceWorkingGroups=researchScheduleService.chooseResearchPeople(current,size,workingGroup,search,researchVo);
                //将查询结果 封装成Result对象
                Result result = new Result();
                result.setData(performanceWorkingGroups);
                return result;
            }else{
                return getJsonResult(true, "请传递正确的项目主键id值", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }

    /**
     * 5.选择组员信息后，返还给前端,是前端的“分配”界面
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 8:52
     * @updateTime 2021/4/16 8:52
     */
    @ApiOperation("5-选择组员信息后，返还给前端")
    @RequestMapping(value = "/goBackMembersGroups", method = RequestMethod.POST)
    public Result goBackMembersGroups(@RequestBody ResearchScheduleVo researchVo){
        try {
            if(researchVo.getIdA()==null){
                return getJsonResult(false,"请传递正确的项目主键id值","");
            }
            TResearchSchedule performanceWorkingGroups=researchScheduleService.goBackMembersGroups(researchVo);

            if(performanceWorkingGroups!=null ){
                Result result=new Result();
                result.setData(performanceWorkingGroups);
                return result;
            }else{
                return getJsonResult(true,"您已经选择了该项目下的所有组员，已经没有组员供您选择了","");
            }

        } finally {
            return getJsonResult(false,"查询失败","");
        }
    }



    /**
     * 6.TResearchScheduleController    [调研安排表 控制层]
     * 拟定调研安排 提交/暂存  “暂存”为0  “提交”就是“审批中”为1  新增/修改都是这个接口
     * @param proResearchSchedule 调研安排关系表对象，每一个关系表对应多个调研安排表（集合）
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 15:22
     * @updateTime 2021/4/19 11:06
     */
    @ApiOperation("6-提交/暂存")
    @RequestMapping(value = "/tempAndSubmit", method = RequestMethod.POST)
    public Result tempAndSubmit(@RequestBody  RelationProResearchSchedule proResearchSchedule) {
        try {
            if(proResearchSchedule!=null && proResearchSchedule.getResearchSchedules()!=null && proResearchSchedule.getResearchSchedules().size()>0 ){
                researchScheduleService.tempAndSubmit(proResearchSchedule,getUser());
                //3.封装成 Result对象
                Result result = new Result();
                return getJsonResult(true, "操作成功", "");
            }else{
                return getJsonResult(true, "请传递正确的调研安排数据", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "操作失败", "");
        }

    }

























}
