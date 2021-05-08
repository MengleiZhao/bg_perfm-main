package com.common.business.planmgr.otherdraftdesign.web;


import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.means.web.InformationsVo;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignRoutine;
import com.common.business.planmgr.indexdraftdesign.web.RelationIndexWorkingManuscriptDesignVo;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignOther;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignResearch;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignSuggest;
import com.common.business.planmgr.otherdraftdesign.mapper.TProWorkingManuscriptDesignOtherMapper;
import com.common.business.planmgr.otherdraftdesign.service.RelationProWorkingManuscriptDesignService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.entity.EntityDao;
import com.common.system.page.BaseController;
import com.common.system.page.ComboboxJson;
import com.common.system.page.Result;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcDict;
import com.common.system.sys.service.RcDictService;
import com.common.system.util.FileUpLoadUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 项目底稿关系表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@RestController
@Api(tags = {"项目底稿关系接口"})
@RequestMapping("/relationProWorkingManuscriptDesign")
public class RelationProWorkingManuscriptDesignController  extends BaseController {
    @Autowired
    private RcDictService rcDictService;
    @Autowired
    private RelationProWorkingManuscriptDesignService relationProWorkingManuscriptDesignService;
    @Autowired
    private TProWorkingManuscriptDesignOtherMapper tProWorkingManuscriptDesignOtherMapper;
    /**
     *
     * @author:安达
     * @date:2021年3月9日 9：51
     * @Description: 项目底稿设计列表页
     * @param bean
     * @return String
     * @throws
     */
    @ApiOperation(value="项目底稿设计列表页")
    @PostMapping(value="proRaftdesignPage")
    public Result proRaftdesignPage(
            @RequestParam(value = "current", defaultValue = "0") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            Result result=new Result();
            PageInfo<TProPerformanceInfo> pageInfo =relationProWorkingManuscriptDesignService.proRaftdesignPage(current,size,search,bean,getUser());
            result.setData(pageInfo);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 项目底稿类型下拉框
     * @Author: 安达
     * @Date: 2021/3/18 9:43
     * @Param:
     * @Return:
     */
    @ApiOperation(value="项目底稿类型下拉框")
    @GetMapping(value="findProSystemDseignComboJson" )
    public Result findProSystemDseignComboJson(){
        try {
            Result result=new Result();
            //到字典表查询底稿类型集合
            List<RcDict> list=rcDictService.findRcDictListByType("proManuscript");
            List<ComboboxJson> comboboxList= getComboboxJson(list);
            return getJsonResult(true,"查询成功",comboboxList);
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }

    /**
     * @Description:  问题建议类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @ApiOperation(value="问题建议类底稿")
    @GetMapping(value="getTProWorkingManuscriptDesignSuggestByIdA")
    public Result getTProWorkingManuscriptDesignSuggestByIdA(
            @RequestParam Integer idA,
            HttpServletRequest request){
        try {
            Result result=new Result();
            TProWorkingManuscriptDesignSuggest designSuggest =relationProWorkingManuscriptDesignService.getTProWorkingManuscriptDesignSuggestByIdA(idA);
            result.setData(designSuggest);
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
     * @Description: 保存问题建议类底稿
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="保存问题建议类底稿")
    @Transactional
    @PostMapping(value = "saveTProWorkingManuscriptDesignSuggest")
    public  Result saveTProWorkingManuscriptDesignSuggest(@RequestBody RelationProWorkingManuscriptDesignVo vo){
        try {
            Result result=new Result();
            relationProWorkingManuscriptDesignService.saveTProWorkingManuscriptDesignSuggest(vo.getIdA(),vo.getStauts(),
                    vo.getIndexWorkingPaperType(),vo.getProWorkingManuscriptDesignSuggest(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,getException(e),"");
        }
    }

    /**
     * @Description:  调研总结类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @ApiOperation(value="调研总结类底稿")
    @GetMapping(value="getTProWorkingManuscriptDesignResearchByIdA")
    public Result getTProWorkingManuscriptDesignResearchByIdA(
            @RequestParam Integer idA,
            HttpServletRequest request){
        try {
            Result result=new Result();
            TProWorkingManuscriptDesignResearch designResearch =relationProWorkingManuscriptDesignService.getTProWorkingManuscriptDesignResearchByIdA(idA);
            result.setData(designResearch);
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
     * @Description: 保存调研总结类底稿
     * @param vo
     * @return String
     * @throws
     */
    @ApiOperation(value="保存调研总结类底稿")
    @Transactional
    @PostMapping(value = "saveTProWorkingManuscriptDesignResearch")
    public  Result saveTProWorkingManuscriptDesignResearch(@RequestBody RelationProWorkingManuscriptDesignVo vo){
        try {
            Result result=new Result();
            relationProWorkingManuscriptDesignService.saveTProWorkingManuscriptDesignResearch(vo.getIdA(),vo.getStauts(),
                    vo.getIndexWorkingPaperType(),vo.getProWorkingManuscriptDesignResearch(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,getException(e),"");
        }
    }

    /**
     * @Description:  其他类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @ApiOperation(value="其他类底稿")
    @GetMapping(value="getTProWorkingManuscriptDesignOtherListByIdA")
    public Result getTProWorkingManuscriptDesignOtherListByIdA(
            @RequestParam Integer idA,
            HttpServletRequest request){
        try {
            Result result=new Result();
            List<TProWorkingManuscriptDesignOther> designOtherList =relationProWorkingManuscriptDesignService.getTProWorkingManuscriptDesignOtherListByIdA(idA);
            result.setData(designOtherList);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败","");
        }
    }
    /**
     * @Description: 保存其他类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idA  子项目主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: proWorkingManuscriptDesignOther  其他类底稿
     * @Param: user 当前登陆者
     * @Return:
     */
    @ApiOperation(value="保存其他类底稿")
    @Transactional
    @PostMapping(value = "saveTProWorkingManuscriptDesignOther")
    public  Result saveTProWorkingManuscriptDesignOther(@RequestBody RelationProWorkingManuscriptDesignVo vo){
        try {
            Result result=new Result();
            relationProWorkingManuscriptDesignService.saveTProWorkingManuscriptDesignOther(vo.getIdA(),vo.getStauts(),
                    vo.getIndexWorkingPaperType(),vo.getProWorkingManuscriptDesignOtherList(),getUser());
            result.setData("");
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return getJsonResult(false,getException(e),"");
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
                List<TProWorkingManuscriptDesignOther> designOtherList =new ArrayList<>();
                if(files.length > 0) {

                    //遍历最终要上传的文件，进行上传
                    for (MultipartFile listFile:files) {
                        //如果当前文件的大小大于0，说明这是个文件，那么才可以对该文件进行上传操作（getSize方法用来获取文件的大小，单位是字节。）
                        if (listFile.getSize()> 0) {

                            //向上转型 成接口类 EntityDao
                            EntityDao entity = new TProWorkingManuscriptDesignOther();
                            //调用文件保存方法，并且返回接口类  (接口类，文件类型，文件实体类，当前登录对象，上传的是哪个项目的(主子项目id值))
                            entity = FileUpLoadUtil.upload(entity, "3-方案制定", listFile, getUser(),idA);

                            //将返回的接口 进行向下转型
                            TProWorkingManuscriptDesignOther designOther = (TProWorkingManuscriptDesignOther) entity;
                            //调用服务层的方法进行 将相关数据插入到数据表中
                            tProWorkingManuscriptDesignOtherMapper.insert(designOther);
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
