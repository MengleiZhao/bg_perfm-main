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
 *  ?????????????????????????????? ???????????????
 * </p>
 *
 * @author ?????????
 * @since 2021-03-24
 */
@RestController
@Api(value = "relationProResearchLetter",tags = {"??????????????????????????????Controller"})
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
     * @Description: ???????????????????????????????????????
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     * @author: ?????????
     * @createDate: 2021/3/24 19:32
     * @updater: ?????????
     * @updateDate: 2021/3/24 19:32
     */
    @ResponseBody
    @ApiOperation(value = "???????????????????????????????????????")
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
     * @Description:  ????????????????????????????????????????????????
     * @param start
     * @param pageSize
     * @param date
     * @param search ????????????
     * @param request
     * @param bean ????????????
     * @author: ?????????
     * @createDate: 2021/3/24 21:00
     * @updater: ?????????
     * @updateDate: 2021/3/24 21:00
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "????????????????????????????????????????????????")
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
     * Description: ?????????????????????
     * @author: ?????????
     * @createDate: 2021/4/22 14:38
     * @updater: ?????????
     * @updateDate: 2021/4/22 14:38
     * @param 
     * @return 
     **/
    @ApiOperation("?????????????????????")
    @GetMapping(value = "downloadResearchModel")
    public Result download( HttpServletResponse response) {
        try {
            //??????????????????????????????????????????????????????????????????????????????
            List<TResearchLetterTemp> tempList = researchLetterTempService.selectList(null);
            TResearchLetterTemp researchLetterTemp = tempList.get(0);
            FileUpLoadUtil.downLoadFile(researchLetterTemp.getFilePath(),researchLetterTemp.getResearchName(),response);
            return getJsonResult(true, "??????????????????", null);

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * ???????????????????????????
     * @param response
     * @param request
     * @return
     */
    /*@ResponseBody
    @ApiOperation(value = "???????????????????????????")
    @GetMapping("downloadResearchModel")
    public Result download(HttpServletResponse response, HttpServletRequest request) {
        OutputStream out = null;
        InputStream in = null;
        try {

            File file = ResourceUtils.getFile("classpath:download/???????????????????????????.docx");
            if (file.exists()) {
                in = new FileInputStream(file);
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setContentLength(in.available());
                response.setHeader("Content-Disposition",
                        "attachment; filename=\""
                                + new String("???????????????????????????.docx".getBytes("gbk"),
                                "iso8859-1") + "\"");
                out = response.getOutputStream();
                byte[] bt = new byte[1000];
                int a;
                while ((a = in.read(bt, 0, 1000)) > 0) {
                    out.write(bt, 0, a);
                    out.flush();
                }
            } else {
                return getJsonResult(false, "??????????????????",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "????????????,?????????????????????",null);
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
        return getJsonResult(true, "???????????????",null);
    }*/

    /**
     * @Title:
     * @Description: ??????/?????????????????????
     * @author: ?????????
     * @createDate: 2021/3/25 16:32
     * @updater: ?????????
     * @updateDate: 2021/3/25 16:32
     * @param relationProResearchLetter : ??????????????????
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "??????/?????????????????????")
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
     * @author: ?????????
     * @createDate: 2021/3/25 17:32
     * @updater: ?????????
     * @updateDate: 2021/3/25 17:32
     * @param id : RelationProResearchLetter?????????id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "??????/??????/????????????")
    @PostMapping(value = "editResearch")
    public Result edit(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"?????????????????????????????????id",null);
            }else {
                HashMap<String ,Object> map = new HashMap<String ,Object>();
                //????????????????????????
                RelationProResearchLetter bean = relationProResearchLetterService.selectById(id);
                map.put("bean",bean);
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_R",bean.getIdR());
                //?????????????????????
                TResearchLetter researchLetter = researchLetterService.selectOne(entityWrapper);
                map.put("researchLetter",researchLetter);
                //??????????????????
                EntityWrapper checkEntityWrapper = new EntityWrapper();
                checkEntityWrapper.eq("ID_R",bean.getIdR());
                checkEntityWrapper.orderBy("CHECK_TIME",false);
                List<TResearchLetterCheckRec> checkHistoryList = researchLetterCheckRecService.selectList(checkEntityWrapper);
                map.put("checkHistoryList",checkHistoryList);
                //?????????????????????????????????
                EntityWrapper checkEntityWrapper1 = new EntityWrapper();
                checkEntityWrapper1.eq("ID_R",bean.getIdR());
                checkEntityWrapper1.eq("CHECK_DATA_STATUS","1");
                checkEntityWrapper1.orderBy("CHECK_TIME");
                List<TResearchLetterCheckRec> checkHistoryList1 = researchLetterCheckRecService.selectList(checkEntityWrapper1);
                //???????????????
                EntityWrapper flowEntityWrapper = new EntityWrapper();
                flowEntityWrapper.eq("ID_R",bean.getIdR());
                List<BussinessFlowProResearchLetter> flowProResearchLetterList = bussinessFlowProResearchLetterService.selectList(flowEntityWrapper);
                //???????????????????????????????????????
                flowProResearchLetterList = bussinessFlowProResearchLetterService.assemblyBussinessFlow(flowProResearchLetterList,checkHistoryList1);
                map.put("flowProResearchLetterList",flowProResearchLetterList);
                //??????????????????????????????
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
     * ????????????
     * @param id RelationProResearchLetter?????????id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "????????????")
    @PostMapping(value = "deleteResearch")
    public Result delete(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"??????????????????id??????",null);
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
     * @Description: ???????????????????????????????????????
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     * @author: ?????????
     * @createDate: 2021/3/25 22:01
     * @updater: ?????????
     * @updateDate: 2021/3/25 22:01
     */
    @ResponseBody
    @ApiOperation(value = "?????????????????????????????????")
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
     * @author: ?????????
     * @createDate: 2021/3/26 16:53
     * @updater: ?????????
     * @updateDate: 2021/3/26 16:53
     * @param researchLetterCheckRec : ?????????????????????????????????
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "??????/??????")
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
     * @Description: ???????????????????????????
     * @author: ?????????
     * @createDate: 2021/3/26 16:53
     * @updater: ?????????
     * @updateDate: 2021/3/26 16:53
     * @param  id : ????????????id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "???????????????????????????")
    @PostMapping(value = "getWorkuser")
    public Result getWorkuser(@RequestParam(value = "current", defaultValue = "1") int start,
                              @RequestParam(value = "size", defaultValue = "10") int pageSize,
                              @RequestParam(value = "date", required = false) String date,
                              @RequestParam(value = "search", required = false) String search,
                              HttpServletRequest request, TPerformanceWorkingGroup bean){
        try {
            if(null==bean.getIdA()){
                return getJsonResult(false,"??????TProPerformanceInfo??????id",null);
            }else {
                //?????????????????????
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
