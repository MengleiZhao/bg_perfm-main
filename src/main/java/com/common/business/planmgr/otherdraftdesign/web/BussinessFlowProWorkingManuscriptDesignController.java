package com.common.business.planmgr.otherdraftdesign.web;


import com.common.business.planmgr.otherdraftdesign.entity.BussinessFlowProWorkingManuscriptDesign;
import com.common.business.planmgr.otherdraftdesign.entity.TWorkingManuscriptDesignCheckRec;
import com.common.business.planmgr.otherdraftdesign.service.BussinessFlowProWorkingManuscriptDesignService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 业务流程表（项目与指标底稿审批） 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@Controller
@RequestMapping("/bussinessFlowProWorkingManuscriptDesign")
public class BussinessFlowProWorkingManuscriptDesignController  extends BaseController {
    @Autowired
    BussinessFlowProWorkingManuscriptDesignService bussinessFlowProWorkingManuscriptDesignService;

    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 项目底稿设计审核列表页
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="项目底稿设计审核列表页")
    @PostMapping(value="" +
            "")
    public Result proManuscriptDesignCheckPage(
            @RequestParam(value = "current", defaultValue = "0") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =bussinessFlowProWorkingManuscriptDesignService.proManuscriptDesignCheckPage(current,size,search,bean,getUser());
            result.setData(pageInfo);
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
    @GetMapping(value="findCheckRecServiceList")
    public   Result findCheckRecServiceList(@RequestParam(value = "idR") Integer idR){
        try{
            Result result=new Result();
            List<TWorkingManuscriptDesignCheckRec> list=bussinessFlowProWorkingManuscriptDesignService.findCheckRecServiceList(idR);
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
    @GetMapping(value="findCheckNodes")
    public   Result findCheckNodes(@RequestParam(value = "idR") Integer idR){
        try{
            Result result=new Result();
            List<BussinessFlowProWorkingManuscriptDesign> list=bussinessFlowProWorkingManuscriptDesignService.findCheckNodes(idR);
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
    public   Result checkRelationProIndex(TWorkingManuscriptDesignCheckRec checkRec){
        try{
            Result result=new Result();
            bussinessFlowProWorkingManuscriptDesignService.checkBussinessFlow(checkRec,getUser());
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
            bussinessFlowProWorkingManuscriptDesignService.designeeCheckUser(idR,groupMemberId);
            return getJsonResult(true,"转派成功",null);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"转派失败","");
        }
    }
}
