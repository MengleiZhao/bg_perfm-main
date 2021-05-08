package com.common.business.workgroup.taskmgr.web;


import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.taskmgr.entity.TPerformanceTaskAllocation;
import com.common.business.workgroup.taskmgr.entity.TResearchLetterGzz;
import com.common.business.workgroup.taskmgr.entity.TResearchOutlineGzz;
import com.common.business.workgroup.taskmgr.service.TPerformanceTaskAllocationService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 绩效任务分配表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-09
 */
@RestController
@Api(tags = {"绩效任务分配接口"})
@RequestMapping("/tPerformanceTaskAllocation")
public class TPerformanceTaskAllocationController  extends BaseController {
    @Autowired
    TPerformanceTaskAllocationService tPerformanceTaskAllocationService;

    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 查询已组建工作组的项目
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="查询已组建工作组的项目")
    @RequestMapping(value="findToTaskAllocationList", method = RequestMethod.POST)
    public Result findToTaskAllocationPage(@RequestParam(value = "current", defaultValue = "0") Integer current,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "search", required = false) String search,
                                    HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo=tPerformanceTaskAllocationService.findToTaskAllocationPage(current,size,search,bean,getUser());
            result.setData(pageInfo);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 查询已归档未涉密的项目
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="查询已归档未涉密的项目")
    @RequestMapping(value="findHadFilePage", method = RequestMethod.POST)
    public Result findHadFilePage(@RequestParam(value = "current", defaultValue = "0") Integer current,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           @RequestParam(value = "search", required = false) String search,
                                           HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo=tPerformanceTaskAllocationService.findHadFilePage(current,size,search,bean,getUser());
            result.setData(pageInfo);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     *
     * @author:安达
     * @date:2021年3月11日 9：51
     * @Description: 根据项目idA查询调研提纲拟定列表
     * @param idA
     * @return String
     * @throws
     */
    @ApiOperation(value="根据项目idA查询调研提纲拟定列表")
    @RequestMapping(value="getResearchOutlineGzzListByIdA", method = RequestMethod.GET)
    public   Result getResearchOutlineGzzListByIdA(@RequestParam(value = "idA") Integer idA){

        try {
            Result result=new Result();
            List<TResearchOutlineGzz> researchOutlineGzzList=tPerformanceTaskAllocationService.getResearchOutlineGzzListByIdA(idA);
            result.setData(researchOutlineGzzList);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     *
     * @author:安达
     * @date:2021年3月11日 9：51
     * @Description: 根据项目idA查询调研函
     * @param idA
     * @return String
     * @throws
     */
    @ApiOperation(value="根据项目idA查询调研函")
    @RequestMapping(value="getResearchLetterGzzByIdA" , method = RequestMethod.GET)
    public  Result getResearchLetterGzzByIdA(@RequestParam(value = "idA") Integer idA){

        try {
            Result result=new Result();
            TResearchLetterGzz  researchLetterGzz=tPerformanceTaskAllocationService.getResearchLetterGzzByIdA(idA);
            result.setData(researchLetterGzz);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     *
     * @author:安达
     * @date:2021年3月11日 9：51
     * @Description: 根据项目idA查询工作任务树列表
     * @param idA
     * @return String
     * @throws
     */
    @ApiOperation(value="根据项目idA查询工作任务树列表")
    @RequestMapping(value = "getTreeData", method = RequestMethod.GET)
    public Result getTreeData(@RequestParam(value = "idA") Integer idA){
        try {
            //查询数据，结果类型是List<TreeEntity>
            List<TPerformanceTaskAllocation> list = tPerformanceTaskAllocationService.getTaskAllocationTrees(idA);
            // 调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
            return getJsonResult(true,"查询成功", list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     *
     * @author:安达
     * @date:2021年3月11日 9：51
     * @Description: 根据项目parentProCode查询分配工作的列表信息
     * @param parentProCode
     * @return String
     * @throws
     */
    @ApiOperation(value="根据项目parentProCode查询分配工作的列表信息")
    @RequestMapping(value = "findToTaskAllocationListByParentProCode", method = RequestMethod.GET)
    public Result findToTaskAllocationListByParentProCode(String parentProCode){
        try {
            //查询数据，结果类型是List<TreeEntity>
            List<TProPerformanceInfo> list = tPerformanceTaskAllocationService.findToTaskAllocationListByParentProCode(parentProCode);
            // 调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
            return getJsonResult(true,"查询成功", list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 保存绩效任务分配
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="保存绩效任务分配")
    @Transactional
    @RequestMapping(value = "saveTPerformanceTaskAllocation",method = RequestMethod.POST)
    public  Result saveTPerformanceTaskAllocation(@RequestBody TPerformanceTaskAllocationVo vo){
        try {
            Result result=new Result();
            tPerformanceTaskAllocationService.saveTPerformanceTaskAllocation(vo.getIsTaskAssigned(),vo.getProPerformanceInfoList(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

}
