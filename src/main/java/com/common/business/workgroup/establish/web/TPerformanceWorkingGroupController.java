package com.common.business.workgroup.establish.web;


import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.sys.entity.RcUser;
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
 * 绩效工作组 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-08
 */
@RestController
@Api(tags = {"绩效工作组接口"})
@RequestMapping("/tPerformanceWorkingGroup")
public class TPerformanceWorkingGroupController  extends BaseController {
    @Autowired
    TPerformanceWorkingGroupService tPerformanceWorkingGroupService;

    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 查询已组建工作组或者组件中的项目
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="组建工作组列表页")
    @PostMapping(value="findHadEstablishedList")
    public  Result findHavingEstablishedPage(
            @RequestParam(value = "current", defaultValue = "0") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request,  TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =tPerformanceWorkingGroupService.findHavingEstablishedPage(current,size,search,bean,getUser());
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
     * @Description: 查询已经立项的未组建的项目
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="查询已经立项的未组建的项目")
    @PostMapping(value="findToEstablishedList")
    public   Result findToEstablishedPage(
             @RequestParam(value = "current", defaultValue = "0") Integer current,
             @RequestParam(value = "size", defaultValue = "10") Integer size,
             @RequestParam(value = "search", required = false) String search,
             HttpServletRequest request,TProPerformanceInfo bean){
        try {

            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =tPerformanceWorkingGroupService.findToEstablishedPage(current,size,search,bean,getUser());
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
            TProPerformanceInfo bean=tPerformanceWorkingGroupService.getTProPerformanceInfoByIdA(idA);
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
     * @Description: 根据idA获取被评价单位名称集合
     * @param idA
     * @return String
     * @throws
     */
    @ApiOperation(value="根据项目idA获取被评价单位列表")
    @GetMapping(value="findEvalUnitList")
    public  Result findEvalUnitList(@RequestParam(value = "idA") Integer idA){
        try {
            Result result=new Result();
            List<TEvalUnitInfo> list=tPerformanceWorkingGroupService.selectList(idA);
            result.setData(list);
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
     * @Description: 根据主项目编号查询子项目集合
     * @param parentProCode
     * @return String
     * @throws
     */
    @ApiOperation(value="根据主项目编号查询子项目和子项目工作组集合")
    @GetMapping(value="findSonListAndGroupsByParentProCode")
    public  Result findSonListAndGroupsByParentProCode(@RequestParam(value = "parentProCode") String parentProCode){
        try {
            Result result=new Result();
            List<TProPerformanceInfo> list=tPerformanceWorkingGroupService.findSonListAndGroupsByParentProCode(parentProCode);
            result.setData(list);
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
     * @Description: 根据主项目编号查询子项目集合
     * @param parentProCode
     * @return String
     * @throws
     */
    @ApiOperation(value="根据主项目编号查询子项目集合")
    @GetMapping(value="findSonListByParentProCode")
    public  Result findSonListByParentProCode(@RequestParam(value = "parentProCode") String parentProCode){
        try {
            Result result=new Result();
            List<TProPerformanceInfo> list=tPerformanceWorkingGroupService.findSonListByParentProCode(parentProCode);
            result.setData(list);
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
     * @Description: 选择人员信息
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="选择秘书")
    @RequestMapping(value="findSecretaryList")
    public   Result findSecretaryList(@RequestBody TPerformanceWorkingGroupVo vo){
        try {
            Result result=new Result();
            List<RcUser> list=tPerformanceWorkingGroupService.findSecretaryList(vo.getRcUser());
            result.setData(list);
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
     * @Description: 选择人员信息
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="选择人员信息")
    @RequestMapping(value="findToEstablishedWorkingGroupList")
    public   Result findToEstablishedWorkingGroupList(@RequestBody TPerformanceWorkingGroupVo vo){
        try {
            Result result=new Result();
            List<RcUser> list=tPerformanceWorkingGroupService.findToEstablishedWorkingGroupList(vo.getRcUser(),vo.getIdA(),
                    vo.getGroupMemberCodes(),vo.getChooseList());
            result.setData(list);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     *
     * @author:安达
     * @date:2021年3月15日 9：51
     * @Description: 选中人员信息且返回给前端
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="选中人员信息,跟老数据匹配且返回给前端")
    @RequestMapping(value="findTotalEstablishedWorkingGroupList", method = RequestMethod.POST)
    public  Result findTotalEstablishedWorkingGroupList(@RequestBody TPerformanceWorkingGroupVo vo){
        try {
            Result result=new Result();
            List<TPerformanceWorkingGroup> list=tPerformanceWorkingGroupService.findTotalEstablishedWorkingGroupList(vo.getUserList(),vo.getIdA(),
                    vo.getGroupMemberCodes(),vo.getChooseList());
            result.setData(list);
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
     * @Description: 保存组建工作组
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="保存组建工作组")
    @Transactional
    @PostMapping(value = "saveWorkingGroup")
    public  Result saveWorkingGroup(@RequestBody TPerformanceWorkingGroupVo vo){
        try {
            Result result=new Result();
            tPerformanceWorkingGroupService.saveWorkingGroup(vo.getIsSetupWorkingGroup(),vo.getProSecretaryId(),vo.getProSecretaryName(),vo.getProPerformanceInfoList(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
}
