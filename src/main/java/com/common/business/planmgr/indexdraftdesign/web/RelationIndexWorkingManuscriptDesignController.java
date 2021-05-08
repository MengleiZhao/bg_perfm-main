package com.common.business.planmgr.indexdraftdesign.web;


import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignOther;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignRoutine;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignSatisfaction;
import com.common.business.planmgr.indexdraftdesign.mapper.TIndexWorkingManuscriptDesignOtherMapper;
import com.common.business.planmgr.indexdraftdesign.service.RelationIndexWorkingManuscriptDesignService;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignOther;
import com.common.business.planmgr.otherdraftdesign.mapper.TProWorkingManuscriptDesignOtherMapper;
import com.common.business.planmgr.otherdraftdesign.web.RelationProWorkingManuscriptDesignVo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.entity.EntityDao;
import com.common.system.page.BaseController;
import com.common.system.page.ComboboxJson;
import com.common.system.page.Result;
import com.common.system.sys.entity.RcDict;
import com.common.system.sys.service.RcDictService;
import com.common.system.util.FileUpLoadUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 三级指标与指标底稿关系表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
@RestController
@Api(tags = {"指标底稿设计接口"})
@RequestMapping("/relationIndexWorkingManuscriptDesign")
public class RelationIndexWorkingManuscriptDesignController  extends BaseController {
    @Autowired
    RelationIndexWorkingManuscriptDesignService relationIndexWorkingManuscriptDesignService;
    @Autowired
    private RcDictService rcDictService;
    @Autowired
    private TIndexWorkingManuscriptDesignOtherMapper tIndexWorkingManuscriptDesignOtherMapper;

    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 指标底稿设计列表页
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="指标底稿设计列表页")
    @PostMapping(value="indexdraftdesignPage")
    public Result indexdraftdesignPage(
            @RequestParam(value = "current", defaultValue = "0") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =relationIndexWorkingManuscriptDesignService.indexdraftdesignPage(current,size,search,bean,getUser());
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
     * @Description: 根据idR查询底稿设计表头信息
     * @param idR
     * @return String
     * @throws
     */
    @ApiOperation(value="底稿设计表头信息")
    @PostMapping(value="findIndexSystemDseignListByIdR")
    public Result findIndexSystemDseignListByIdR(Integer idR){
        try {
            Result result=new Result();
            List<TIndexSystemDseign> list=relationIndexWorkingManuscriptDesignService.findIndexSystemDseignListByIdR(idR);
            result.setData(list);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 指标底稿类型下拉框
     * @Author: 安达
     * @Date: 2021/3/18 9:43
     * @Param:
     * @Return:
     */

    @ApiOperation(value="指标底稿类型下拉框")
    @GetMapping(value="findIndexSystemDseignComboJson" )
    public Result findIndexSystemDseignComboJson(){
        try {
            Result result=new Result();
            //到字典表查询底稿类型集合
            List<RcDict>  list=rcDictService.findRcDictListByType("indexManuscript");
            List<ComboboxJson> comboboxList= getComboboxJson(list);
            return getJsonResult(true,"查询成功",comboboxList);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 工作底稿信息    常规类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @ApiOperation(value="常规类底稿")
    @GetMapping(value="getInformationDesignRoutineByIdB")
    public Result getInformationDesignRoutineByIdB(
            @RequestParam  Integer idB,
            @RequestParam  Integer idR,
            @RequestParam Integer year1,
            @RequestParam Integer year2,
            HttpServletRequest request){
        try {
            Result result=new Result();
            TIndexWorkingManuscriptDesignRoutine designRoutine =relationIndexWorkingManuscriptDesignService.getInformationDesignRoutineByIdB(idB,idR,year1,year2);
            result.setData(designRoutine);
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
     * @Description: 保存常规类底稿
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="保存常规类底稿")
    @Transactional
    @PostMapping(value = "saveDesignRoutine")
    public  Result saveDesignRoutine(@RequestBody RelationIndexWorkingManuscriptDesignVo vo){
        try {
            Result result=new Result();
            relationIndexWorkingManuscriptDesignService.saveDesignRoutine(vo.getIdB(),vo.getStauts(),vo.getIndexWorkingPaperType(),
                    vo.getIndexWorkingManuscriptDesignRoutine(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 工作底稿信息    满意度类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @ApiOperation(value="满意度类底稿")
    @GetMapping(value="findSatisfactionListByIdB")
    public Result findSatisfactionListByIdB(
            @RequestParam  Integer idB,
            @RequestParam  Integer idR,
            @RequestParam Integer year1,
            @RequestParam Integer year2,
            HttpServletRequest request){
        try {
            Result result=new Result();
            List<TIndexWorkingManuscriptDesignSatisfaction> satisfactionList =relationIndexWorkingManuscriptDesignService.findSatisfactionListByIdB(idB,idR,year1,year2);
            result.setData(satisfactionList);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 保存满意度底稿
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idB  指标体系设计主键
     * @Param stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param designSatisfactionList  满意度底稿集合
     * @Param user 当前登陆者
     * @Return:
     */
    @ApiOperation(value="保存满意度底稿")
    @Transactional
    @PostMapping(value = "saveDesignSatisfaction")
    public  Result saveDesignSatisfaction(@RequestBody RelationIndexWorkingManuscriptDesignVo vo){
        try {
            Result result=new Result();
            relationIndexWorkingManuscriptDesignService.saveDesignSatisfaction(vo.getIdB(),vo.getStauts(),vo.getIndexWorkingPaperType(),
                    vo.getDesignSatisfactionList(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 工作底稿信息    其他类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @ApiOperation(value="其他类底稿")
    @GetMapping(value="findOtherListByIdR")
    public Result findOtherListByIdR(
            @RequestParam  Integer idB,
            @RequestParam  Integer idR,
            @RequestParam Integer year1,
            @RequestParam Integer year2,
            HttpServletRequest request){
        try {
            Result result=new Result();
           List<TIndexWorkingManuscriptDesignOther> satisfactionList =relationIndexWorkingManuscriptDesignService.findOtherListByIdR(idR);
            result.setData(satisfactionList);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * @Description: 保存其他类底稿
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idB  指标体系设计主键
     * @Param stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param designSatisfactionList  其他类底稿集合
     * @Param user 当前登陆者
     * @Return:
     */
    @ApiOperation(value="保存其他类底稿")
    @Transactional
    @PostMapping(value = "saveDesignOther")
    public  Result saveDesignOther(@RequestBody RelationIndexWorkingManuscriptDesignVo vo){
        try {
            Result result=new Result();
            relationIndexWorkingManuscriptDesignService.saveDesignOther(vo.getIdB(),vo.getStauts(),vo.getIndexWorkingPaperType(),
                    vo.getDesignOtherList(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    @ApiOperation("其他类底稿上传")
    @RequestMapping(value = "/chooseClassUpload", method = RequestMethod.POST)
    @Transactional
    public Result chooseClassUpload( RelationProWorkingManuscriptDesignVo vo) {
        try {
            Integer idA=vo.getIdA();//主子项目id主键
            MultipartFile[] files=vo.getFiles();//前端传过来的集合
            //1.将上传的文件保存到本地服务器中
            //遍历 前台上传的文件个数，将每一个文件上传到本地服务器中， 并将每一个文件的相关数据插入到数据表中
            if (files!=null) {
                List<TIndexWorkingManuscriptDesignOther> designOtherList =new ArrayList<>();
                if(files.length > 0) {

                    //遍历最终要上传的文件，进行上传
                    for (MultipartFile listFile:files) {
                        //如果当前文件的大小大于0，说明这是个文件，那么才可以对该文件进行上传操作（getSize方法用来获取文件的大小，单位是字节。）
                        if (listFile.getSize()> 0) {

                            //向上转型 成接口类 EntityDao
                            EntityDao entity = new TIndexWorkingManuscriptDesignOther();
                            //调用文件保存方法，并且返回接口类  (接口类，文件类型，文件实体类，当前登录对象，上传的是哪个项目的(主子项目id值))
                            entity = FileUpLoadUtil.upload(entity, "3-方案制定", listFile, getUser(),idA);

                            //将返回的接口 进行向下转型
                            TIndexWorkingManuscriptDesignOther designOther = (TIndexWorkingManuscriptDesignOther) entity;
                            //调用服务层的方法进行 将相关数据插入到数据表中
                            tIndexWorkingManuscriptDesignOtherMapper.insert(designOther);
                            designOtherList.add(designOther);
                        }
                    }
                }
                return getJsonResult(true, "上传成功", designOtherList);

            } else {
                return getJsonResult(true, "您没有选择任何一个文件进行上传", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "上传失败", "");
        }

    }
}
