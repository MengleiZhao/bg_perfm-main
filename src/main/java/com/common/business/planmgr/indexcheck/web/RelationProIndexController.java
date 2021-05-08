package com.common.business.planmgr.indexcheck.web;


import com.common.business.planmgr.indexcheck.entity.BussinessFlowProIndex;
import com.common.business.planmgr.indexcheck.entity.TIndexSystemDseignCheckRec;
import com.common.business.planmgr.indexcheck.service.RelationProIndexService;
import com.common.business.planmgr.indexdesign.web.TIndexSystemDseignVo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 项目指标体系关系表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
@RestController
@Api(tags = {"指标体系设计接口"})
@RequestMapping("/relationProIndex")
public class RelationProIndexController  extends BaseController {
    @Autowired
    RelationProIndexService relationProIndexService;
    @Autowired
    TPerformanceWorkingGroupService tPerformanceWorkingGroupService;

    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 指标体系设计列表——所有版本
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="指标体系设计列表——所有版本")
    @PostMapping(value="allRelationProIndexPage")
    public Result allRelationProIndexPage(
            @RequestParam(value = "current", defaultValue = "0") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =relationProIndexService.allRelationProIndexPage(current,size,search,bean,getUser());
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
     * @Description: 指标体系设计列表页
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="指标体系设计列表页")
    @PostMapping(value="relationProIndexPage")
    public Result relationProIndexPage(
            @RequestParam(value = "current", defaultValue = "0") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =relationProIndexService.relationProIndexPage(current,size,search,bean,getUser());
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
     * @Description: 指标体系审核列表页
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="指标体系审核列表页")
    @PostMapping(value="relationProIndexCheckPage")
    public Result relationProIndexCheckPage(
            @RequestParam(value = "current", defaultValue = "0") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =relationProIndexService.relationProIndexCheckPage(current,size,search,bean,getUser());
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
     * @date:2021年3月8日 下午19：51
     * @Description: 根据项目idA获取项目信息
     * @param idA
     * @return String
     * @throws
     */
    @ApiOperation(value="根据项目idA获取项目信息")
    @GetMapping(value="getTProPerformanceInfoByIdA")
    public   Result getTProPerformanceInfoByIdA(@RequestParam(value = "idA") Integer idA){
        try {
            Result result=new Result();
            TProPerformanceInfo bean=relationProIndexService.getTProPerformanceInfoByIdA(idA);
            result.setData(bean);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     *
     * @author:安达
     * @date:2021年3月8日 下午19：51
     * @Description: 根据项目idA查询工作组集合
     * @param idA
     * @return String
     * @throws
     */
    @ApiOperation(value="根据项目idA查询工作组集合")
    @GetMapping(value="findTPerformanceWorkingGroupListByIdA")
    public   Result findTPerformanceWorkingGroupListByIdA(@RequestParam(value = "idA") Integer idA){
        try{
            Result result=new Result();
            List<TPerformanceWorkingGroup> list=tPerformanceWorkingGroupService.findTPerformanceWorkingGroupListByIdA(idA);
            result.setData(list);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * @Description:  审批记录信息
     * @Author: 安达
     * @Date: 2021/4/1 18:14
     * @Param:
     * @Return:
     */
    @ApiOperation(value="审批记录信息")
    @GetMapping(value="findTIndexSystemDseignCheckRecServiceListByIdR")
    public   Result findTIndexSystemDseignCheckRecServiceListByIdR(@RequestParam(value = "idR") Integer idR){
        try{
            Result result=new Result();
            List<TIndexSystemDseignCheckRec> list=relationProIndexService.findTIndexSystemDseignCheckRecServiceListByIdR(idR);
            result.setData(list);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description:  审批流程节点信息
     * @Author: 安达
     * @Date: 2021/4/1 18:14
     * @Param:
     * @Return:
     */
    @ApiOperation(value="审批流程节点信息")
    @GetMapping(value="findTIndexSystemDseignCheckRecServiceNodesByIdR")
    public   Result findTIndexSystemDseignCheckRecServiceNodesByIdR(@RequestParam(value = "idR") Integer idR){
        try{
            Result result=new Result();
            List<BussinessFlowProIndex> list=relationProIndexService.findTIndexSystemDseignCheckRecServiceNodesByIdR(idR);
            result.setData(list);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     * @Description: 审批
     * @Author: 安达
     * @Date: 2021/3/31 10:58
     * @Param:
     * @Return:
     */
    @ApiOperation(value="审批")
    @PostMapping(value="checkRelationProIndex")
    public   Result checkRelationProIndex(TIndexSystemDseignCheckRec indexSystemDseignCheckRec){
        try{
            Result result=new Result();
            relationProIndexService.checkRelationProIndex(indexSystemDseignCheckRec,getUser());
            return getJsonResult(true,"审批成功",null);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"审批失败","");
        }
    }


    /**
     * @Description: 转派（指标体系审批）
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    @ApiOperation(value="转派")
    @PostMapping(value="designeeCheckUser")
    public   Result designeeCheckUser(Integer idR,String groupMemberId){
        try{
            Result result=new Result();
            relationProIndexService.designeeCheckUser(idR,groupMemberId);
            return getJsonResult(true,"转派成功",null);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"转派失败","");
        }
    }

    /**
     * @Description: 保存指标体系关系
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idA  子项目id
     * @Param stauts 版本状态
     * @Param indexSystemDseignList  指标体系集合
     * @Param user 当前登陆者
     * @Return:
     */
    @ApiOperation(value="保存指标体系设计")
    @PostMapping(value="saveRelationProIndex")
    public Result saveRelationProIndex(@RequestBody TIndexSystemDseignVo vo){
        try {
            relationProIndexService.saveRelationProIndex(vo.getIdA(),vo.getStauts(),vo.getIndexSystemDseignList(),getUser());
            return getJsonResult(true,"操作成功","");
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"操作失败","");
        }
    }
}
