package com.common.business.planmgr.pre.mkoutline.web;


import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.common.business.planmgr.pre.mkoutline.entity.TResearchOutline;
import com.common.business.planmgr.pre.mkoutline.service.RelationProResearchOutlineService;
import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.sys.entity.RcUser;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 项目调研提纲关系表  前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/relationProResearchOutline")
@Api(value = "/relationProResearchOutline",tags = {"T-拟定调研提纲接口"})
public class RelationProResearchOutlineController extends BaseController {
    @Autowired
    private RelationProResearchOutlineService researchOutlineService;//关系表 服务层

    /**
     * 1-拟定调研提纲主页面显示
     * 约束条件：当前登录人是项目主管/或者组员、明确工作任务时有“拟定调研提纲” 且已经勾选、已经立项、已经创建工作组、跟拟定关系表相关联
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 21:02
     * @updateTime 2021/4/21 21:02
     */
    @ApiOperation("1-拟定调研提纲 主页面显示")
    @RequestMapping(value = "/outlinePage", method = RequestMethod.POST)
    public Result outlinePage(
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
            PageInfo<TProPerformanceInfo> pageInfo = researchOutlineService.queryForPage(current, size, proPerformanceInfo, search, userId,preOrScheme);
            if (pageInfo != null && pageInfo.getSize() > 0) {
                //2.封装成 Result对象
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            } else {
                return getJsonResult(true, "您没有已经 拟定好的调研提纲项目", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**
     * 2-分页显示 选择要拟定调研提纲的项目
     * @param preOrScheme 0-预调研   1-编制评价方案
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 9:07
     * @updateTime 2021/4/22 9:07
     */
    //@RequestParam 接收url地址中的参数
    @ApiOperation("2-分页显示 选择要拟定调研提纲的项目")
    @RequestMapping(value = "/chooseOutlineProject",method = RequestMethod.POST)
    public Result chooseOutlineProject(@RequestParam(value = "current",defaultValue = "1") Integer current,
                                       @RequestParam(value = "size",defaultValue = "10") Integer size,
                                       @RequestBody(required = false) TProPerformanceInfo proPerformanceInfo,
                                       @RequestParam(value = "search",required = false) String search,
                                       @RequestParam(value="preOrScheme")Integer preOrScheme){
        try {
            if(proPerformanceInfo==null){
                proPerformanceInfo=new TProPerformanceInfo();
            }
            String userId=String.valueOf(getUser().getId());
            List<TProPerformanceInfo> proPerformanceInfos=researchOutlineService.chooseOutlineProject(current,size,userId,proPerformanceInfo,search,preOrScheme);
            if(proPerformanceInfos!=null && proPerformanceInfos.size()>0){
                Result result=new Result();
                result.setData(proPerformanceInfos);
                return result;
            }else{
                return getJsonResult(true,"没有需要您进行拟定调研提纲的项目",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败",null);
        }
    }


    /**
     * 3-查看该版本的拟定调研提纲详情
     * @param idR 拟定关系表主键idR值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 9:33
     * @updateTime 2021/4/22 9:33
     */
    @ApiOperation("3-查看该版本的拟定调研提纲详情")
    @RequestMapping(value = "/showOutlines",method = RequestMethod.GET)
    public Result showOutlines(Integer idR){

        try {
            if(idR==null){
                return getJsonResult(false,"请传递正确的拟定关系表主键idR值","");
            }
            //调研提纲集合
            List<TResearchOutline> researchOutlines=researchOutlineService.showOutlines(idR);
            if(researchOutlines!=null && researchOutlines.size()>0){
                Result result=new Result();
                result.setData(researchOutlines);
                return result;
            }else{
                return getJsonResult(true,"该版本还没有组建调研提纲",null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败",null);
        }

    }

    /**
     * 4-显示初始调研提纲数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 10:04
     * @updateTime 2021/4/22 10:04
     */
    @ApiOperation("4-显示初始调研提纲数据")
    @RequestMapping(value = "/initialOutlines",method = RequestMethod.GET)
    public Result initialOutlines(){
        try {

            List<TResearchOutlineTemp> initials=researchOutlineService.initialOutlines();
            Result result=new Result();
            result.setData(initials);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     * 5-提交/暂存
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 10:22
     * @updateTime 2021/4/22 10:22
     */
    @ApiOperation("5-提交/暂存")
    @RequestMapping(value = "/tempAndSubmit",method = RequestMethod.POST)
    public Result tempAndSubmit(@RequestBody RelationProResearchOutline proResearchOutline){
        try {
            Integer idA=proResearchOutline.getIdA();
            if(idA==null){
                return getJsonResult(false,"请传递正确主子项目主键id值","");
            }
            researchOutlineService.tempAndSubmit(proResearchOutline,getUser());
            return getJsonResult(true,"操作成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"操作失败","");
        }
    }












}
