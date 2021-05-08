package com.common.business.planmgr.pre.mkletter.web;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mkletter.entity.TResearchLetter;
import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.common.business.planmgr.pre.mkletter.service.RelationProResearchLetterService;
import com.common.business.planmgr.pre.mkletter.service.TResearchLetterService;
import com.common.business.planmgr.pre.mkletter.service.TResearchLetterTempService;
import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.business.planmgr.pre.mklettercheck.service.BussinessFlowProResearchLetterService;
import com.common.business.planmgr.pre.mklettercheck.service.TResearchLetterCheckRecService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.service.TProPerformanceInfoService;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.FileUpLoadUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  项目拟定调研函关系表 前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
@RestController
@Api(value = "relationProResearchLetter",tags = {"项目拟定调研函关系表Controller"})
@RequestMapping("/relationProResearchLetter")
public class RelationProResearchLetterController extends BaseController {


    @Autowired
    private RelationProResearchLetterService relationProResearchLetterService;
    @Autowired
    private TProPerformanceInfoService proPerformanceInfoService;
    @Autowired
    private TResearchLetterService researchLetterService;
    @Autowired
    private TResearchLetterCheckRecService researchLetterCheckRecService;
    @Autowired
    private BussinessFlowProResearchLetterService bussinessFlowProResearchLetterService;
    @Autowired
    private TPerformanceWorkingGroupService performanceWorkingGroupService;
    @Autowired
    private TResearchLetterTempService researchLetterTempService;



    /**
     * @Title:
     * @Description: 调研函拟定列表分页查询方法
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     * @author: 陈睿超
     * @createDate: 2021/3/24 19:32
     * @updater: 陈睿超
     * @updateDate: 2021/3/24 19:32
     */
    @ResponseBody
    @ApiOperation(value = "调研函拟定列表分页查询方法")
    @PostMapping(value = "pagelist")
    public Result pagelist(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, RelationProResearchLetter bean){
        try {
            bean.setCreateUaseId(String.valueOf(getUser().getId()));
            PageInfo<RelationProResearchLetter> list = relationProResearchLetterService.pagelist(start, pageSize,
                    search, bean);
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description:  新增调研函时查询需要预调研的项目
     * @param start
     * @param pageSize
     * @param date
     * @param search 模糊查询
     * @param request
     * @param bean 精确查询
     * @author: 陈睿超
     * @createDate: 2021/3/24 21:00
     * @updater: 陈睿超
     * @updateDate: 2021/3/24 21:00
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "新增调研函时查询需要预调研的项目")
    @PostMapping(value = "chooseProPage")
    public Result chooseProPage( @RequestParam(value = "current", defaultValue = "1") int start,
                                 @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                 @RequestParam(value = "date", required = false) String date,
                                 @RequestParam(value = "search", required = false) String search,
                                 HttpServletRequest request, TProPerformanceInfo bean, Integer preOrScheme){
        try {
            bean.setProManagerId(String.valueOf(getUser().getId()));
            PageInfo<TProPerformanceInfo> list = proPerformanceInfoService.chooseResearchPro(start, pageSize, preOrScheme,
                    bean, search);
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * Title: downloadResearchModel
     * Description: 下载调研函模板
     * @author: 陈睿超
     * @createDate: 2021/4/22 14:38
     * @updater: 陈睿超
     * @updateDate: 2021/4/22 14:38
     * @param 
     * @return 
     **/
    @ApiOperation("下载调研函模板")
    @GetMapping(value = "downloadResearchModel")
    public Result download( HttpServletResponse response) {
        try {
            //因为只有一个模板所有强行获取后台数据，写死获取第一个
            List<TResearchLetterTemp> tempList = researchLetterTempService.selectList(null);
            TResearchLetterTemp researchLetterTemp = tempList.get(0);
            FileUpLoadUtil.downLoadFile(researchLetterTemp.getFilePath(),researchLetterTemp.getResearchName(),response);
            return getJsonResult(true, "文件下载成功", null);

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * 下载统一调研函模板
     * @param response
     * @param request
     * @return
     */
    /*@ResponseBody
    @ApiOperation(value = "下载统一调研函模板")
    @GetMapping("downloadResearchModel")
    public Result download(HttpServletResponse response, HttpServletRequest request) {
        OutputStream out = null;
        InputStream in = null;
        try {

            File file = ResourceUtils.getFile("classpath:download/绩效评价调研函模板.docx");
            if (file.exists()) {
                in = new FileInputStream(file);
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setContentLength(in.available());
                response.setHeader("Content-Disposition",
                        "attachment; filename=\""
                                + new String("绩效评价调研函模板.docx".getBytes("gbk"),
                                "iso8859-1") + "\"");
                out = response.getOutputStream();
                byte[] bt = new byte[1000];
                int a;
                while ((a = in.read(bt, 0, 1000)) > 0) {
                    out.write(bt, 0, a);
                    out.flush();
                }
            } else {
                return getJsonResult(false, "文件不存在！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "出现错误,请联系管理员！",null);
        } finally {
            try {
                if (out != null){
                    out.close();
                }
                if (in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getJsonResult(true, "操作成功！",null);
    }*/

    /**
     * @Title:
     * @Description: 保存/送审调研函信息
     * @author: 陈睿超
     * @createDate: 2021/3/25 16:32
     * @updater: 陈睿超
     * @updateDate: 2021/3/25 16:32
     * @param relationProResearchLetter : 需要保存对象
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "保存/送审调研函信息")
    @PostMapping(value = "saveResearch")
    public Result save(RelationProResearchLetter relationProResearchLetter){
        try {
            Boolean b = relationProResearchLetterService.save(relationProResearchLetter,getUser());
            return getJsonResult(true,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            getException(e);
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description:
     * @author: 陈睿超
     * @createDate: 2021/3/25 17:32
     * @updater: 陈睿超
     * @updateDate: 2021/3/25 17:32
     * @param id : RelationProResearchLetter的主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "查看/修改/审批数据")
    @PostMapping(value = "editResearch")
    public Result edit(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"请上传调研函关系表主键id",null);
            }else {
                HashMap<String ,Object> map = new HashMap<String ,Object>();
                //获取调研函关系表
                RelationProResearchLetter bean = relationProResearchLetterService.selectById(id);
                map.put("bean",bean);
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_R",bean.getIdR());
                //获取调研函信息
                TResearchLetter researchLetter = researchLetterService.selectOne(entityWrapper);
                map.put("researchLetter",researchLetter);
                //获取审批记录
                EntityWrapper checkEntityWrapper = new EntityWrapper();
                checkEntityWrapper.eq("ID_R",bean.getIdR());
                checkEntityWrapper.orderBy("CHECK_TIME",false);
                List<TResearchLetterCheckRec> checkHistoryList = researchLetterCheckRecService.selectList(checkEntityWrapper);
                map.put("checkHistoryList",checkHistoryList);
                //获取组装数据的审批记录
                EntityWrapper checkEntityWrapper1 = new EntityWrapper();
                checkEntityWrapper1.eq("ID_R",bean.getIdR());
                checkEntityWrapper1.eq("CHECK_DATA_STATUS","1");
                checkEntityWrapper1.orderBy("CHECK_TIME");
                List<TResearchLetterCheckRec> checkHistoryList1 = researchLetterCheckRecService.selectList(checkEntityWrapper1);
                //获取工作流
                EntityWrapper flowEntityWrapper = new EntityWrapper();
                flowEntityWrapper.eq("ID_R",bean.getIdR());
                List<BussinessFlowProResearchLetter> flowProResearchLetterList = bussinessFlowProResearchLetterService.selectList(flowEntityWrapper);
                //组装工作流加上审批记录数据
                flowProResearchLetterList = bussinessFlowProResearchLetterService.assemblyBussinessFlow(flowProResearchLetterList,checkHistoryList1);
                map.put("flowProResearchLetterList",flowProResearchLetterList);
                //获取当前审批活跃节点
                Integer orderOfCurrentNode = null;
                for (BussinessFlowProResearchLetter bussinessFlowProResearchLetter : flowProResearchLetterList) {
                    if ("1".equals(bussinessFlowProResearchLetter.getNodeIsActive())){
                        orderOfCurrentNode = bussinessFlowProResearchLetter.getOrderOfCurrentNode();
                    }
                }
                map.put("orderOfCurrentNode",orderOfCurrentNode);

                return getJsonResult(true,null,map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * 逻辑删除
     * @param id RelationProResearchLetter的主键id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "逻辑删除")
    @PostMapping(value = "deleteResearch")
    public Result delete(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"需要删除主键id为空",null);
            }else {
                RelationProResearchLetter bean = relationProResearchLetterService.selectById(id);
                bean.setRelationStatus("9");
                relationProResearchLetterService.updateById(bean);
                return getJsonResult(true,null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * @Title:
     * @Description: 调研函审批列表分页查询方法
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     * @author: 陈睿超
     * @createDate: 2021/3/25 22:01
     * @updater: 陈睿超
     * @updateDate: 2021/3/25 22:01
     */
    @ResponseBody
    @ApiOperation(value = "调研函审批列表分页查询")
    @PostMapping(value = "pageChecklist")
    public Result pageChecklist(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, RelationProResearchLetter bean){
        try {
            bean.setCurrCheckId(String.valueOf(getUser().getId()));
            PageInfo<RelationProResearchLetter> list = relationProResearchLetterService.checkPagelist(start, pageSize,
                    search, bean);
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description:
     * @author: 陈睿超
     * @createDate: 2021/3/26 16:53
     * @updater: 陈睿超
     * @updateDate: 2021/3/26 16:53
     * @param researchLetterCheckRec : 审批对象包含了指派信息
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "审批/指派")
    @PostMapping(value = "checkResearch")
    public Result check(@Validated TResearchLetterCheckRec researchLetterCheckRec){
        try {
            researchLetterCheckRecService.check(researchLetterCheckRec,getUser());
            return getJsonResult(true,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * @Title:
     * @Description: 获取项目组成员信息
     * @author: 陈睿超
     * @createDate: 2021/3/26 16:53
     * @updater: 陈睿超
     * @updateDate: 2021/3/26 16:53
     * @param  id : 项目主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "获取项目组成员信息")
    @PostMapping(value = "getWorkuser")
    public Result getWorkuser(@RequestParam(value = "current", defaultValue = "1") int start,
                              @RequestParam(value = "size", defaultValue = "10") int pageSize,
                              @RequestParam(value = "date", required = false) String date,
                              @RequestParam(value = "search", required = false) String search,
                              HttpServletRequest request, TPerformanceWorkingGroup bean){
        try {
            if(null==bean.getIdA()){
                return getJsonResult(false,"请传TProPerformanceInfo主键id",null);
            }else {
                //获取关系表数据
                PageInfo<TPerformanceWorkingGroup> workUserList = performanceWorkingGroupService.findWorkGroupByStatus(start,pageSize,search,bean,"1");

                return getJsonResult(true, null, workUserList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }



    public Result addGetTemp(){


        return getJsonResult(false,null,null);
    }


}
