package com.common.business.project.approval.web;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.service.TEvalUnitInfoService;
import com.common.business.project.approval.service.TMainProjectSyncService;
import com.common.business.project.approval.service.TProPerformanceInfoService;
import com.common.business.project.approval.service.TProjectBusinessTypeService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.service.UserService;
import com.common.system.util.tree.TreeEntity;
import com.common.system.util.tree.TreeUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  项目立项前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-08
 */
@Controller
@Api(value = "tProPerformanceInfo",tags = {"项目立项"})
@RequestMapping("/tProPerformanceInfo")
public class TProPerformanceInfoController extends BaseController {

    @Autowired
    private TProPerformanceInfoService tProPerformanceInfoService;
    @Autowired
    private TEvalUnitInfoService tEvalUnitInfoService;
    @Autowired
    private TMainProjectSyncService tMainProjectSyncService;
    @Autowired
    private UserService userService;
    @Autowired
    private TProjectBusinessTypeService projectBusinessTypeService;


    /**
     * 项目立项分页查询
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean 查询条件类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pageList")
    public Result pageList(
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            bean.setProManagerId(getUser().getId().toString());
            PageInfo<TProPerformanceInfo> pageInfo = tProPerformanceInfoService.listForPage(current, pageSize, bean, search);
            Result result = getJsonResult(true,null,pageInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * 保存项目立项
     * @param tProPerformanceInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public Result save(@Valid @RequestBody TProPerformanceInfo tProPerformanceInfo, HttpServletRequest request){
        try {
            if ("1".equals(tProPerformanceInfo.getProIsdismant())){
                //拆分 
               /* if (StringUtils.isEmpty(tProPerformanceInfo.getProLevel4ClassName())){
                    return getJsonResult(false,"请填写项目四级分类",null);
                }*/
                if (null==tProPerformanceInfo.getProPerformanceInfolist()){
                    return getJsonResult(false,"请填写子项目信息",null);
                }
            } else if ("0".equals(tProPerformanceInfo.getProIsdismant())){
                //不拆分
               /* if (StringUtils.isEmpty(tProPerformanceInfo.getProLevel4ClassName())){
                    return getJsonResult(false,"请填写项目四级分类",null);
                }*/
                if (StringUtils.isEmpty(tProPerformanceInfo.getRiskLevel())){
                    return getJsonResult(false,"请填写风险级别",null);
                }
                if (StringUtils.isEmpty(tProPerformanceInfo.getNationEcoIndustClassId())){
                    return getJsonResult(false,"请填写国民经济行业分类",null);
                }
                if (StringUtils.isEmpty(tProPerformanceInfo.getBudFunctClassId())){
                    return getJsonResult(false,"请填写预算支出功能分类",null);
                }
            } else {
                return getJsonResult(false,"请选择是否拆分子项目",null);
            }
            if (null==tProPerformanceInfo.getEvalUnitInfoList()){
                return getJsonResult(false,"请填写被评价单位信息",null);
            }

            Result result = getJsonResult(tProPerformanceInfoService.save(tProPerformanceInfo.getIdMainA(),
                    tProPerformanceInfo,getUser()),null,null);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * 台账list页面数据
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pageLedgerList")
    public Result pageLedgerList(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TProPerformanceInfo bean){
        try {
            bean.setProManagerId(getUser().getId().toString());
            PageInfo<TProPerformanceInfo> pageInfo = tProPerformanceInfoService.listForLedgerPage(start, pageSize, bean, search);
            return getJsonResult(true,null,pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * @Title: 修改页面
     * @Description
     * @author: 陈睿超
     * @createDate: 2021/3/9 16:24
     * @updater: 陈睿超
     * @updateDate: 2021/3/9 16:24
     * @param 	id 主键id 
     * @return
     **/
    @ResponseBody
    @GetMapping(value = "edit")
    public Result edit( Integer id){
        try {
            if (null==id){
                return getJsonResult(false,"请传主键id",null);
            }else {
                TProPerformanceInfo tProPerformanceInfo = tProPerformanceInfoService.selectById(id);

                EntityWrapper tEvalUnitInfoWrapper = new EntityWrapper();
                tEvalUnitInfoWrapper.eq("ID_A", id);
                List<TEvalUnitInfo> tEvalUnitInfolist = tEvalUnitInfoService.selectList(tEvalUnitInfoWrapper);

                EntityWrapper tProPerformanceInfoWrapper = new EntityWrapper();
                tProPerformanceInfoWrapper.eq("PARENT_PRO_CODE", tProPerformanceInfo.getParentProCode());
                tProPerformanceInfoWrapper.in("PRO_STATUS", "1,0");
                List<TProPerformanceInfo> tProPerformanceInfoList = tProPerformanceInfoService.selectList(tProPerformanceInfoWrapper);
                //让不拆封的时候点击拆分按钮的时候不显示自己
                if ("0".equals(tProPerformanceInfo.getProIsdismant())) {
                    //            for (int i = tProPerformanceInfoList.size()-1; i > 0; i--) {
                    for (int i = 0; i < tProPerformanceInfoList.size(); i++) {
                        TProPerformanceInfo proPerformanceInfo = tProPerformanceInfoList.get(i);
                        if (proPerformanceInfo.getProIsdismant().equals(tProPerformanceInfo.getProIsdismant())) {
                            tProPerformanceInfoList.remove(i);
                        }
                    }
                }
                

                Result result = new Result();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("bean", tProPerformanceInfo);
                map.put("evalUnitInfolist", tEvalUnitInfolist);
                map.put("proPerformanceInfoList", tProPerformanceInfoList);
                result.setData(map);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * @Title: delete
     * @Description 删除主表
     * @author: 陈睿超
     * @createDate: 2021/3/10 10:03
     * @updater: 陈睿超
     * @updateDate: 2021/3/10 10:03
     * @param 		id 子项目主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "删除")
    @PostMapping(value = "delete")
    public Result delete(Integer id){
        try {
            if (null==id){
                return getJsonResult(false,"请传主键id",null);
            }else{
                return getJsonResult(tProPerformanceInfoService.deleteByProStatus(id),null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * 下载“被评价单位模板”
     * @param response
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("outcomeDownload")
    public Result download(HttpServletResponse response,HttpServletRequest request) {
        OutputStream out = null;
        InputStream in = null;
        try {
//            String path = request.getSession().getServletContext()
//                    .getRealPath("/resource");
//            String filePath = path + "\\download\\流程确认表.xlsx";

            File file = ResourceUtils.getFile("classpath:download/被评价单位模板.xlsx");
            //File file = new File(filePath);
            /* File file = new File("D:/项目申报支出明细模板.xlsx"); */
            if (file.exists()) {
                in = new FileInputStream(file);
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setContentLength(in.available());
                response.setHeader("Content-Disposition",
                        "attachment; filename=\""
                                + new String("被评价单位模板.xlsx".getBytes("gbk"),
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
     * @Title:
     * @Description 导入数据
     * @author: 陈睿超
     * @createDate: 2021/3/10 16:50
     * @updater: 陈睿超
     * @updateDate: 2021/3/10 16:50
     * @param 	multipartFile : 传过来的文件
     * @return
     **/
    @ResponseBody
    @RequestMapping("/importExcel")
    public Result importExcel(@RequestParam("file") MultipartFile multipartFile){
        List<TEvalUnitInfo> list = null;
        try {
            ImportParams params = new ImportParams();
            //设置表头是几行
            params.setHeadRows(2);
//         params.setTitleRows(0);
            //获取数据
            list = ExcelImportUtil.importExcel(multipartFile.getInputStream(), TEvalUnitInfo.class, params);
//        System.out.println(JSONUtil.toJsonStr(list));
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }



    /**
     * @Title:
     * @Description: 导出台账
     * @author: 陈睿超
     * @createDate: 2021/3/11 13:54
     * @updater: 陈睿超
     * @updateDate: 2021/3/11 13:54
     * @return
     **/
    @ResponseBody
    @GetMapping(value = "expertExcel")
    public Result expertExcel(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, HttpServletResponse response, TProPerformanceInfo bean){

        ServletOutputStream output = null;
        try {
            bean.setProManagerId(getUser().getId().toString());
            Workbook workbook = tProPerformanceInfoService.expertLedgerExcel(start, pageSize, bean, search);

            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("项目台账.xlsx", "gbk"));
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
     * 获取项目独立复核人
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取独立复核人",notes = "因为现在没办法判断需要什么条件，所以目前可以选择所有人")
    @PostMapping(value = "getIndepReviewUser")
    public Result getIndepReviewUser(@RequestParam(value = "current", defaultValue = "1") int start,
                                     @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "date", required = false) String date,
                                     @RequestParam(value = "search", required = false) String search){

        try {
            PageInfo<RcUser> userlsit = userService.listForPage(start, pageSize,search,null);
            return getJsonResult(true,null,userlsit);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }


    /***
     * 获取项目业务类型
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取项目业务类型")
    @PostMapping(value = "getProjectBusinessType")
    public Result getProjectBusinessType(){
        
        //查询数据，结果类型是List<TreeEntity>
        List<TreeEntity> list = projectBusinessTypeService.getTreeEntity();
        //获取偷不
        // 调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
        return getJsonResult(true,null, TreeUtils.getTreeList("1",list));
    }



}
