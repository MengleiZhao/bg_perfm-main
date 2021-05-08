package com.common.business.planmgr.indexdesign.web;


import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.planmgr.indexdesign.entity.*;
import com.common.business.planmgr.indexdesign.service.TIndexSystemDseignService;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 指标体系设计表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
@RestController
@Api(tags = {"拟定指标体系接口"})
@RequestMapping("/tIndexSystemDseign")
public class TIndexSystemDseignController   extends BaseController {
    @Autowired
    TIndexSystemDseignService tIndexSystemDseignService;
    @Autowired
    TPerformanceWorkingGroupService tPerformanceWorkingGroupService;


    /**
     *
     * @author:安达
     * @date:2021年3月17日 9：51
     * @Description: 拟定指标体系
     * @param idA
     * @return String
     * @throws
     */
    @ApiOperation(value="拟定指标体系")
    @GetMapping(value="findIndexSystemDseignListByIdA")
    public  Result findIndexSystemDseignListByIdA(@RequestParam(value = "idA") Integer idA){
        try {
            List<TIndexSystemDseign> list =tIndexSystemDseignService.findIndexSystemDseignListByIdA(idA);
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     *
     * @author:安达
     * @date:2021年3月17日 9：51
     * @Description: 根据idB查询指标说明（评分要点）列表
     * @param idB
     * @return String
     * @throws
     */
    @ApiOperation(value="根据idB查询指标说明（评分要点）列表")
    @GetMapping(value="findFIndexDescScoringPointsListByIdB")
    public  Result findFIndexDescScoringPointsListByIdB(@RequestParam(value = "idB") Integer idB){
        try {
            List<TIndexScoringPoints> list =tIndexSystemDseignService.findFIndexDescScoringPointsListByIdB(idB);
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * 项目支出绩效评价指标库列表
     * @author:安达
     * @date:2021年3月9日 9：51
     * @return
     * @throws Exception
     */
    @ApiOperation(value="项目支出绩效评价指标库列表")
    @PostMapping(value="findTLibraryIndexSystem")
    public  Result findTLibraryIndexSystem(@RequestBody TIndexSystemDseignVo vo){
        try {
            List<TIndexSystemDseign> list =tIndexSystemDseignService.findTLibraryIndexSystem(vo.getOldList(),vo.getDeleteCodeStr());
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     *
     * @author:安达
     * @date:2021年3月17日 9：51
     * @Description: 指标体系编辑
     * @param idB
     * @return String
     * @throws
     */
    @ApiOperation(value="指标体系编辑")
    @GetMapping(value="getTLibraryIndexSystemByIdB")
    public  Result getTLibraryIndexSystemByIdB(@RequestParam(value = "idB") Integer idB){
        try {
            TIndexSystemDseign bean =tIndexSystemDseignService.getTLibraryIndexSystemByIdB(idB);
            return getJsonResult(true,"查询成功",bean);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }




    /** 
     * @Description: 一级指标下拉框 
     * @Author: 安达
     * @Date: 2021/3/18 9:43
     * @Param: 
     * @Return: 
     */

    @ApiOperation(value="一级指标下拉框 ")
    @GetMapping(value="findLevelOneIndexComboJson" )
    public Result findLevelOneIndexComboJson(){
        try {
            List<TLibraryIndexSystem> list = tIndexSystemDseignService.findLevelOneIndexList();
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * @Description: 二级指标下拉框
     * @Author: 安达
     * @Date: 2021/3/18 9:43
     * @Param:
     * @Return:
     */
    @ApiOperation(value="二级指标下拉框")
    @GetMapping(value="findLevelTwoIndexComboJson" )
    public Result findLevelTwoIndexComboJson(){
        try {
            List<TLibraryIndexSystem> list = tIndexSystemDseignService.findLevelTwoIndexList();
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * @Description: 考核对象集合
     * @Author: 安达
     * @Date: 2021/3/18 9:43
     * @Param:
     * @Return:
     */
    @ApiOperation(value="考核对象集合")
    @PostMapping(value="findEvalUnitList" )
    public Result findEvalUnitList(@RequestBody TIndexSystemDseignVo vo){
        try {
            //根据项目idA获取被评价单位列表
            List<TAssessmentObjectByIndex> list=tIndexSystemDseignService.findEvalUnitList(vo.getIdA(),vo.getOldObjectList(),vo.getDeleteIdStr());
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 选中的考核对象集合
     * @Author: 安达
     * @Date: 2021/3/18 9:43
     * @Param:
     * @Return:
     */
    @ApiOperation(value="选中的考核对象集合")
    @PostMapping(value="findTAssessmentObjectByIndexList" )
    public Result findTAssessmentObjectByIndexList(Integer idB){
        try {
            //选中的考核对象集合
            List<TAssessmentObjectByIndex> list=tIndexSystemDseignService.findTAssessmentObjectByIndexList(idB);
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * @Description: 评分要点考核对象集合
     * @Author: 安达
     * @Date: 2021/3/18 9:43
     * @Param:
     * @Return:
     */
    @ApiOperation(value="评分要点考核对象集合")
    @PostMapping(value="findTAssessmentObjectByPointsList" )
    public Result findTAssessmentObjectByPointsList(Integer idC){
        try {
            //选中的考核对象集合
            List<TAssessmentObjectByPoints> list=tIndexSystemDseignService.findTAssessmentObjectByPointsList(idC);
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * 佐证材料池
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param vo
     * @throws Exception
     */
    @ApiOperation(value="佐证材料池")
    @PostMapping(value="findEvidencePool")
    public Result findEvidencePool(@RequestBody TIndexSystemDseignVo vo){
        try {
            List<TEvidencePools> list=tIndexSystemDseignService.findEvidencePool(vo.getIdA(),vo.getOldEvidencePoolList(),vo.getDeleteIdStr());
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * @Description: 根据idB查询已经选择的佐证材料
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    @ApiOperation(value="根据idB查询佐证材料池")
    @GetMapping(value="findTEvidencePoolsListByIdB")
    public Result findTEvidencePoolsListByIdB(Integer idB){
        try {
            List<TEvidencePools> list=tIndexSystemDseignService.findTEvidencePoolsListByIdB(idB);
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 根据idA查询资料清单集合，且把资料清单转换为佐证材料池对象
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    @ApiOperation(value="根据idA查询资料清单集合，且把资料清单转换为佐证材料池对象")
    @GetMapping(value="findTEvidencePoolsListByIdA")
    public Result findTEvidencePoolsListByIdA(Integer idA){
        try {
            List<TEvidencePools> list=tIndexSystemDseignService.findTEvidencePoolsListByIdA(idA);
            return getJsonResult(true,"查询成功",list);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }


    /**
     * @Description: 保存指标体系设计
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idA  子项目id
     * @Param stauts 版本状态
     * @Param evidencePoolsList  佐证材料池集合
     * @Param scoringPointsList  评分要点集合
     * @Param systemDseign  绩效指标对象
     * @Param user 当前登陆者
     * @Return:
     */
    @ApiOperation(value="保存指标体系设计")
    @PostMapping(value="saveSystemDseign")
    public Result saveSystemDseign(@RequestBody TIndexSystemDseignVo vo){
        try {
            TIndexSystemDseign systemDseign= tIndexSystemDseignService.saveSystemDseign(vo.getAssessmentObjectList(),vo.getEvidencePoolsList(),
                    vo.getScoringPointsList(),vo.getSystemDseign());
            return getJsonResult(true,"操作成功",systemDseign);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"操作失败","");
        }
    }
}
