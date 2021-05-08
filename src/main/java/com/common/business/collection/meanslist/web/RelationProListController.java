package com.common.business.collection.meanslist.web;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationListTemp;
import com.common.business.collection.meanslist.service.RelationProListService;
import com.common.business.collection.meanslist.service.TDevelopmentInformationListService;
import com.common.business.collection.meanslist.service.TDevelopmentInformationListTempService;
import com.common.business.collection.meanslistcheck.entity.BussinessFlowProList;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.common.business.collection.meanslistcheck.service.BussinessFlowProListService;
import com.common.business.collection.meanslistcheck.service.TDevelopmentInfoListCheckRecService;
import com.common.business.library.cases.entity.TLibraryProjectCase;
import com.common.business.library.cases.service.TLibraryProjectCaseService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.service.TProPerformanceInfoService;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.business.workgroup.taskmgr.entity.TPerformanceTaskAllocation;
import com.common.business.workgroup.taskmgr.service.TPerformanceTaskAllocationService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  项目资料清单关系表 前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@RestController
@Api(value = "relationProList",tags = {"资料清单拟定接口"})
@RequestMapping("/relationProList")
public class RelationProListController extends BaseController {


    @Autowired
    private RelationProListService relationProListService;
    @Autowired
    private TProPerformanceInfoService proPerformanceInfoService;
    @Autowired
    private TDevelopmentInformationListTempService developmentInformationListTempService;
    @Autowired
    private TLibraryProjectCaseService libraryProjectCaseService;
    @Autowired
    private TDevelopmentInformationListService developmentInformationListService;
    @Autowired
    private TPerformanceWorkingGroupService performanceWorkingGroupService;
    @Autowired
    private BussinessFlowProListService bussinessFlowProListService;
    @Autowired
    private TDevelopmentInfoListCheckRecService developmentInfoListCheckRecService;
    @Autowired
    private TPerformanceTaskAllocationService performanceTaskAllocationService;


    /**
     * @Title:
     * @Description: 资料清单申请页面分页数据
     * @author: 陈睿超
     * @createDate: 2021/3/17 09:36
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 09:36
     * @param start
     * @param pageSize
     * @param date
     * @param search 模糊查询条件
     * @param request
     * @param bean 精确查询条件
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "资料清单申请页面分页数据")
    @PostMapping(value = "pagelist")
    public Result pagelist(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, RelationProList bean){
        try {
            bean.setCreateUaseId(String.valueOf(getUser().getId()));
            PageInfo<RelationProList> list = relationProListService.pagelist(start, pageSize,
                    search, bean);
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description: 资料清单申请页面新增页面选择项目
     * @author: 陈睿超
     * @createDate: 2021/3/17 14:36
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 14:36
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param pro
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "资料清单申请页面新增页面选择项目")
    @PostMapping(value = "findProInfoPage")
    public Result findProInfoPage(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo pro){
        try {
            pro.setProManagerId(String.valueOf(getUser().getId()));
            PageInfo<TProPerformanceInfo> pageinfo = relationProListService.selectProPage(start, pageSize, search, pro);
            return getJsonResult(true,null,pageinfo);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }

    /**
     * @Title:
     * @Description: 获取资料收集模板
     * @author: 陈睿超
     * @createDate: 2021/3/17 17:34
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 17:34
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "获取资料收集模板")
    @PostMapping(value = "getTemplate")
    public Result getTemplate(){
        try {
            List<TDevelopmentInformationListTemp> list = developmentInformationListTempService.getTemplate();
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * @Title:
     * @Description: 获得项目案例库所有数据
     * @author: 陈睿超
     * @createDate: 2021/3/17 18:18
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 18:18
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "获得项目案例库所有数据")
    @PostMapping(value = "getLibraryProjectCase")
    public Result getLibraryProjectCase(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TLibraryProjectCase bean){
        try {
            PageInfo<TLibraryProjectCase> pageinfo = libraryProjectCaseService.findPagelist(start, pageSize, search, bean);
            return getJsonResult(true,null,pageinfo);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }

    /**
     * @Title:
     * @Description: 导入资料模板数据
     * @author: 陈睿超
     * @createDate: 2021/3/17 20:57
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 20:57
     * @param 		multipartFile : 附件
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "导入资料模板数据")
    @PostMapping(value = "importDevelopmentExcel")
    public Result importDevelopmentExcel(@RequestParam("file") MultipartFile multipartFile){

        ImportParams params = new ImportParams();
        //设置表头几行
        params.setHeadRows(2);
        try {
            List<TDevelopmentInformationList> list = ExcelImportUtil.importExcel(multipartFile.getInputStream(), TDevelopmentInformationList.class, params);
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }

    /**
     * @Title:
     * @Description:导出页面已填写资料采集信息
     * @author: 陈睿超
     * @createDate: 2021/3/17 21:10
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 21:10
     * @param 	developmentlist:前台接收过来json参数 
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "导出页面已填写资料采集信息")
    @PostMapping(value = "expertDevelopmentExcel")
    public Result expertDevelopmentExcel(@RequestParam("developmentlist") List<TDevelopmentInformationList> developmentlist, HttpServletResponse response){
        ServletOutputStream output = null;

        try {
            if (null==developmentlist){
                return getJsonResult(false,"需要导出内容为空",null);
            }else{

                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("资料收集信息", "资料收集信息"),
                        TDevelopmentInformationList.class, developmentlist);

                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-Type", "application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode("资料收集信息.xlsx", "gbk"));
                output = response.getOutputStream();
                workbook.write(output);
                return getJsonResult(true,null,null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return getJsonResult(false,"io流错误",null);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
                return getJsonResult(false,"io流关闭错误",null);
            }
        }
    }

    /**
     * @Title:
     * @Description: 下载资料收集模板
     * @author: 陈睿超
     * @createDate: 2021/3/17 21:31
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 21:31
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "下载资料收集模板")
    @GetMapping("downloadTempExcel")
    public Result downloadTempExcel(HttpServletResponse response,HttpServletRequest request) {
        OutputStream out = null;
        InputStream in = null;
        try {
            File file = ResourceUtils.getFile("classpath:download/资料收集模板.xlsx");
            if (file.exists()) {
                in = new FileInputStream(file);
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setContentLength(in.available());
                response.setHeader("Content-Disposition",
                        "attachment; filename=\""
                                + new String("资料收集模板.xlsx".getBytes("gbk"),
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
    }

    /**
     * 获取该项目最新模板
     * @author: 陈睿超
     * @createDate: 2021/4/19 20:26
     * @updater: 陈睿超
     * @updateDate: 2021/4/19 20:26
     * @param id:项目id
     * @return 
     **/
    @ResponseBody
    @ApiOperation(value = "获取该项目最新模板")
    @PostMapping(value = "getLatestTemplate")
    public Result getLatestTemplate(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"项目id不能为空",null);
            }else {
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_A",id);
                entityWrapper.orderBy("VERSION_NO",true);
                List<RelationProList> list = relationProListService.selectList(entityWrapper);
                RelationProList relationPro = list.get(list.size()-1);
                //获取资料收集
                EntityWrapper developmentrEntityWrapper = new EntityWrapper();
                developmentrEntityWrapper.eq("ID_R",relationPro.getIdR());
                developmentrEntityWrapper.orderBy("ID_B");
                List<TDevelopmentInformationList> developmentrlist = developmentInformationListService.selectList(developmentrEntityWrapper);
                for (int i = 0; i < developmentrlist.size(); i++) {
                    developmentrlist.get(i).setIdB(null);
                }
                return getJsonResult(true,null,developmentrlist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }
    
    /**
     * 保存/送审
     * @author: 陈睿超
     * @createDate: 2021/3/18 11:22
     * @updater: 陈睿超
     * @updateDate: 2021/3/18 11:22
     * @param relationProList :接受对象 
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "保存/送审")
    @PostMapping(value = "saveRelationPro")
    public Result save(@RequestBody RelationProList relationProList){
        try {
            if (null == relationProList.getDevelopmentInformationLists()){
                return getJsonResult(false,"请添加清单拟定信息",null);
            } else if (null == relationProList.getIdA()){
                return getJsonResult(false,"请传相关项目主键id",null);
            }
            Boolean bool = relationProListService.save(relationProList,getUser());
            return getJsonResult(bool,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * @Title:
     * @Description: 修改/查看/审批 请求数据
     * @author: 陈睿超
     * @createDate: 2021/3/19 16:00
     * @updater: 陈睿超
     * @updateDate: 2021/3/19 16:00
     * @param id : RelationProList主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "修改/查看/审批 请求数据")
    @PostMapping(value = "editRelationPro")
    public Result edit(Integer id){
        try {
            if(null==id){
                return getJsonResult(false,"请传RelationProList主键id",null);
            }else{
//              获取关系表数据
                RelationProList bean = relationProListService.selectById(id);
                HashMap<String ,Object> hashMap = new HashMap<String ,Object>();
                hashMap.put("bean",bean);
//              获取资料收集
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_R",bean.getIdR());
                entityWrapper.orderBy("ID_B");
                List<TDevelopmentInformationList> developmentrlist = developmentInformationListService.selectList(entityWrapper);
                hashMap.put("developmentrlist",developmentrlist);
                //获取工作流数据集合
                EntityWrapper bussinessFlowEntityWrapper = new EntityWrapper();
                bussinessFlowEntityWrapper.eq("ID_R",bean.getIdR());
                List<BussinessFlowProList> bussinessFlowProList = bussinessFlowProListService.selectList(bussinessFlowEntityWrapper);
                //查询本次历史审批记录
                EntityWrapper nowEntityWrapper = new EntityWrapper();
                nowEntityWrapper.eq("CHECK_DATA_STATUS","1");
                nowEntityWrapper.eq("ID_R",bean.getIdR());
                List<TDevelopmentInfoListCheckRec> nowHistoryList = developmentInfoListCheckRecService.selectList(nowEntityWrapper);

                EntityWrapper checkEntityWrapper = new EntityWrapper();
                checkEntityWrapper.eq("ID_R",bean.getIdR());
                checkEntityWrapper.orderBy("CHECK_TIME",false);
                List<TDevelopmentInfoListCheckRec> checkHistoryList = developmentInfoListCheckRecService.selectList(checkEntityWrapper);
                hashMap.put("checkHistoryList",checkHistoryList);
                //组装审批记录和里程数据
                bussinessFlowProList = bussinessFlowProListService.assemblyBussinessFlow(bussinessFlowProList,nowHistoryList);
                hashMap.put("bussinessFlowProList",bussinessFlowProList);

                return getJsonResult(true,null,hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description: 获取项目组成员信息
     * @author: 陈睿超
     * @createDate: 2021/3/19 17:31
     * @updater: 陈睿超
     * @updateDate: 2021/3/19 17:31
     * @param  idA : 项目主键id
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
            if(null == bean.getIdA()){
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

    /**
     * @Title:
     * @Description: 根据id逻辑删除
     * @author: 陈睿超
     * @createDate: 2021/3/19 21:02
     * @updater: 陈睿超
     * @updateDate: 2021/3/19 21:02
     * @param 	id : RelationProList主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "根据id逻辑删除")
    @PostMapping(value = "deleteRelationPro")
    public Result delete(Integer id) {
        try {
            if(null==id){
                return getJsonResult(false,"请传RelationProList主键id",null);
            }else{
                //获取关系表数据
                RelationProList bean = relationProListService.selectById(id);
                relationProListService.deleteRelationPro(bean);
                return getJsonResult(true,null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }


    /**
     * @Title:
     * @Description: 台账页面查询
     * @author: 陈睿超
     * @createDate: 2021/3/23 15:52
     * @updater: 陈睿超
     * @updateDate: 2021/3/23 15:52
     * @param start
     * @param pageSize
     * @param date
     * @param search 模糊查询条件
     * @param request
     * @param bean 精确查询条件
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "查询台账页面")
    @PostMapping(value = "ledgerPage")
    public Result ledgerPage(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, RelationProList bean){
        try {
            bean.setCreateUaseId(String.valueOf(getUser().getId()));
            PageInfo<RelationProList> list = relationProListService.ledgerPagelist(start, pageSize,
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
     * @createDate: 2021/3/23 20:25
     * @updater: 陈睿超
     * @updateDate: 2021/3/23 20:25
     * @param 	id : RelationProList的主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "台账查看")
    @PostMapping(value = "editLedger")
    public Result editLedger(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"资料关系表主键id不能为空",null);
            }else{
                RelationProList relationProList = relationProListService.selectById(id);
                List<TDevelopmentInformationList> developmentInformationList = developmentInformationListService.getDatalist(relationProList);
                return getJsonResult(true,null,developmentInformationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }


    @ResponseBody
    @ApiOperation(value = "导出台账页面")
    @GetMapping(value = "expertledgerPage")
    public Result expertledgerPage(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, HttpServletResponse response, RelationProList bean){

        ServletOutputStream output = null;
        try {
            bean.setCreateUaseId(String.valueOf(getUser().getId()));

            Workbook workbook = relationProListService.expertledgerPagelist(Integer.valueOf(start), Integer.valueOf(pageSize), search, bean);

            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("资料台账.xlsx", "gbk"));
            output = response.getOutputStream();
            workbook.write(output);
        } catch (IOException e) {
            e.printStackTrace();
            return getJsonResult(false,"io流错误",null);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
                return getJsonResult(false,"io流关闭错误",null);
            }
        }
        return getJsonResult(true,null,null);
    }

    /**
     * @Title:
     * @Description: 校验版本
     * @author: 陈睿超
     * @createDate: 2021/4/9 10:34
     * @updater: 陈睿超
     * @updateDate: 2021/4/9 10:34
     * @param id : 项目id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "新增时校验版本")
    @PostMapping(value = "verifyVersion")
    public Result verifyVersion(Integer id){

        try {
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.in("CREATE_STAUTS","-1,0");
            entityWrapper.eq("ID_A",id);
            entityWrapper.eq("CREATE_UASE_ID",getUser().getId());
            List<RelationProList> list = relationProListService.selectList(entityWrapper);
            if (list.size() > 0){
                return getJsonResult(false,"本项目已有相同问卷存在，请修改",null);
            }else{
                return getJsonResult(true,null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title: 
     * @Description: 新增时校验前面工作任务的时间
     * @author: 陈睿超
     * @createDate: 2021/4/9 17:21
     * @updater: 陈睿超
     * @updateDate: 2021/4/9 17:21
     * @param id : 项目id
     * @return 
     **/
    @ResponseBody
    @ApiOperation(value = "新增时校验前面工作任务的时间")
    @PostMapping(value = "verifyTime")
    public Result verifyTime(Integer id){
        try {
            EntityWrapper entityWrapper = new EntityWrapper();
            //资料清单拟定代码
            entityWrapper.in("TASK_CODE","TS0201");
            entityWrapper.eq("ID_A",id);
            entityWrapper.eq("HAVE_BEEN_CHOOSE","1");
            TPerformanceTaskAllocation performanceTaskAllocation = performanceTaskAllocationService.selectOne(entityWrapper);
            return getJsonResult(true,null,performanceTaskAllocation);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


}
