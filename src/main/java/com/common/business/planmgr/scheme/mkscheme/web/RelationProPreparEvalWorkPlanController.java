package com.common.business.planmgr.scheme.mkscheme.web;


import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.WordImageEntity;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.indexcheck.entity.RelationProIndex;
import com.common.business.planmgr.indexcheck.mapper.RelationProIndexMapper;
import com.common.business.planmgr.indexcheck.service.RelationProIndexService;
import com.common.business.planmgr.indexdesign.entity.IndexSystemOpinions;
import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import com.common.business.planmgr.indexdesign.mapper.TIndexSystemDseignMapper;
import com.common.business.planmgr.indexdesign.service.IndexSystemOpinionsService;
import com.common.business.planmgr.indexdesign.service.TIndexSystemDseignService;
import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.service.RelationProResearchScheduleService;
import com.common.business.planmgr.pre.mkinvarr.service.TResearchScheduleService;
import com.common.business.planmgr.pre.mkoutline.service.RelationProResearchOutlineService;
import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.entity.TPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.entity.TPreparEvalWorkPlanTemp;
import com.common.business.planmgr.scheme.mkscheme.service.RelationProPreparEvalWorkPlanService;
import com.common.business.planmgr.scheme.mkscheme.service.TPreparEvalWorkPlanService;
import com.common.business.planmgr.scheme.mkscheme.service.TPreparEvalWorkPlanTempService;
import com.common.business.planmgr.schemecheck.entity.BussinessFlowPreparEvalWorkPlan;
import com.common.business.planmgr.schemecheck.entity.TPreparEvalWorkPlanCheckRec;
import com.common.business.planmgr.schemecheck.service.BussinessFlowPreparEvalWorkPlanService;
import com.common.business.planmgr.schemecheck.service.TPreparEvalWorkPlanCheckRecService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.service.TProPerformanceInfoService;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.service.UserService;
import com.common.system.util.FileUpLoadUtil;
import com.common.system.util.WordUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  项目编制评价工作方案关系表
 *  前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@RestController
@Api(value = "relationProPreparEvalWorkPlan",tags = {"项目编制评价工作方案接口"})
@RequestMapping("/relationProPreparEvalWorkPlan")
public class RelationProPreparEvalWorkPlanController extends BaseController {

    @Autowired
    private RelationProPreparEvalWorkPlanService relationProPreparEvalWorkPlanService;
    @Autowired
    private TProPerformanceInfoService proPerformanceInfoService;
    @Autowired
    private TPreparEvalWorkPlanService preparEvalWorkPlanService;
    @Autowired
    private BussinessFlowPreparEvalWorkPlanService bussinessFlowPreparEvalWorkPlanService;
    @Autowired
    private TPreparEvalWorkPlanCheckRecService preparEvalWorkPlanCheckRecService;
    @Autowired
    private TPerformanceWorkingGroupService performanceWorkingGroupService;
    @Autowired
    private TPreparEvalWorkPlanTempService preparEvalWorkPlanTempService;
    @Autowired
    private UserService userService;
    @Autowired
    private RelationProIndexService relationProIndexService;
    @Autowired
    private TIndexSystemDseignService indexSystemDseignService;
    @Autowired
    private RelationProResearchScheduleService relationProResearchScheduleService;
    @Autowired
    private TResearchScheduleService researchScheduleService;
    @Autowired
    private IndexSystemOpinionsService indexSystemOpinionsService;


    /**
     * 项目编制评价工作方案列表分页查询方法
     * @author: 陈睿超
     * @createDate: 2021/4/8 16:05
     * @updater: 陈睿超
     * @updateDate: 2021/4/8 16:05
     * @param start
     * @param pageSize
     * @param date
     * @param search 模糊查询
     * @param request
     * @param relationProPreparEvalWorkPlan 精确查询
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "项目编制评价工作方案列表分页查询方法")
    @PostMapping(value = "page")
    public Result page(@RequestParam(value = "current", defaultValue = "1") int start,
                       @RequestParam(value = "size", defaultValue = "10") int pageSize,
                       @RequestParam(value = "date", required = false) String date,
                       @RequestParam(value = "search", required = false) String search,
                       HttpServletRequest request, RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan){
        try {
            PageInfo<RelationProPreparEvalWorkPlan> page = relationProPreparEvalWorkPlanService.pagelist(start, pageSize, search, relationProPreparEvalWorkPlan, getUser());
            return getJsonResult(true,null,page);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * 新增项目编制评价工作方案时查询项目
     * @author: 陈睿超
     * @createDate: 2021/4/14 19:31
     * @updater: 陈睿超
     * @updateDate: 2021/4/14 19:31
     * @param start
     * @param pageSize
     * @param date
     * @param search 模糊查询
     * @param bean 精确查询
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "新增项目编制评价工作方案时查询项目",notes = "查询条件跟预调研一样")
    @PostMapping(value = "chooseProPage")
    public Result chooseProPage( @RequestParam(value = "current", defaultValue = "1") int start,
                                 @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                 @RequestParam(value = "date", required = false) String date,
                                 @RequestParam(value = "search", required = false) String search,
                                 HttpServletRequest request, TProPerformanceInfo bean, Integer preOrScheme){
        try {
            // bean.setProManagerId(String.valueOf(getUser().getId()));
            PageInfo<TProPerformanceInfo> list = proPerformanceInfoService.choosePreparEvalWorkPro(start, pageSize, preOrScheme,
                    bean, search,getUser());
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * 保存方法
     * @author: 陈睿超
     * @createDate: 2021/4/14 20:03
     * @updater: 陈睿超
     * @updateDate: 2021/4/14 20:03
     * @param relationProPreparEvalWorkPlanVo:编制评价工作方案接受数据类
     * @return 成功返回true,失败返回false
     **/
    @ResponseBody
    @ApiOperation(value = "保存")
    @PostMapping(value = "save")
    public Result save(@RequestBody RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo){
        try {
            boolean b = relationProPreparEvalWorkPlanService.save(relationProPreparEvalWorkPlanVo,getUser());
            return getJsonResult(b,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * 上传文件
     * @author: 陈睿超
     * @createDate: 2021/4/15 14:19
     * @updater: 陈睿超
     * @updateDate: 2021/4/15 14:19
     * @param relationProPreparEvalWorkPlanVo : 编制评价工作方案接受数据类
     * @return 返回
     **/
    @ResponseBody
    @ApiOperation("上传文件")
    @PostMapping(value = "upLoadFiles")
    public Result upLoadFiles(@RequestBody RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo) {
        try {
            List<TPreparEvalWorkPlan> preparEvalWorkPlanList = preparEvalWorkPlanService.upLoadFiles(relationProPreparEvalWorkPlanVo, getUser());
            return getJsonResult(true,null,preparEvalWorkPlanList);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * 下载工作方案
     * @author: 陈睿超
     * @createDate: 2021/4/15 16:26
     * @updater: 陈睿超
     * @updateDate: 2021/4/15 16:26
     * @param idB:TPreparEvalWorkPlan的主键
     * @return 附件
     **/
    @ApiOperation("下载工作方案")
    @GetMapping(value = "downloadWorkPlan")
    public Result downloadWorkPlan(Integer idB, HttpServletResponse response) {
        try {
            if(idB != null){
                TPreparEvalWorkPlan preparEvalWorkPlan = preparEvalWorkPlanService.selectById(idB);
                if (preparEvalWorkPlan != null) {
                    FileUpLoadUtil.downLoadFile(preparEvalWorkPlan.getFilePath(),preparEvalWorkPlan.getFileName(),response);
                }else {
                    return getJsonResult(false, "未查询到相关文件", null);
                }
                return getJsonResult(true, "文件下载成功", null);
            }else{
                return getJsonResult(false, "请选择一个文件进行下载", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "文件下载失败", null);
        }
    }

    /**
     * 下载模板
     * @author: 陈睿超
     * @createDate: 2021/4/15 16:26
     * @updater: 陈睿超
     * @updateDate: 2021/4/15 16:26
     * @param type:1==绩效评价工作方案（以委托方名义出具，2==绩效评价工作方案（以我所名义出具
     * @return 附件
     **/
    @ApiOperation("下载模板(以委托方名义出具)")
    @GetMapping(value = "downloadWorkPlanTemplate")
    public Result downloadWorkPlanTemplate(Integer type, HttpServletResponse response) {
        try {
            if(type != null){
                //获取后台数据，因为只有两个所有默认获取
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.like("F_PLAN_NAME","以委托方名义出具");
                /*if (1 == type) {
                    entityWrapper.like("F_PLAN_NAME","以委托方名义出具");
//                    FileUpLoadUtil.downLoadFile("classpath:download/绩效评价工作方案（以委托方名义出具）.docx","绩效评价工作方案（以委托方名义出具）.docx",response);
                }else if (2 == type) {
                    entityWrapper.like("F_PLAN_NAME","以我所名义出具");
//                    FileUpLoadUtil.downLoadFile("classpath:download/绩效评价工作方案（以我所名义出具）.docx","绩效评价工作方案（以我所名义出具）.docx",response);
                }else {
                    return getJsonResult(false, "未查询到相关文件", null);
                }*/
                TPreparEvalWorkPlanTemp preparEvalWorkPlanTemp = preparEvalWorkPlanTempService.selectOne(entityWrapper);
                FileUpLoadUtil.downLoadFile(preparEvalWorkPlanTemp.getFFilePath(),preparEvalWorkPlanTemp.getFPlanName(),response);
                return getJsonResult(true, "文件下载成功", null);
            }else{
                return getJsonResult(false, "请选择一个文件进行下载", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "文件下载失败", null);
        }
    }   


    
    @ApiOperation("下载word模板(以我所名义出具)")
    @GetMapping(value = "complexWordExport")
    public void complexWordExport(Integer id,HttpServletResponse response) {
        try {
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode("绩效评价工作方案（以我所名义出具）.docx", "UTF-8"));
            OutputStream os = response.getOutputStream();
            //获取项目
            TProPerformanceInfo pro = proPerformanceInfoService.selectById(id);

            //组装需要替换文字,现在方法还没有放开，因为没有数据会有问题
            Map<String, Object> map = relationProPreparEvalWorkPlanService.ExportWordData(pro);
            //组装需要替换的段落
            Map<String, Object> textMap = relationProPreparEvalWorkPlanService.ExportWordTextMap(pro);

            XWPFDocument doc = WordExportUtil.exportWord07(
                    "D:\\IDeaSpace\\bg_perfm\\bg_perfm\\src\\main\\resources\\download\\绩效评价工作方案（以我所名义出具）.docx", map);
            
            WordUtils.changeText(doc,textMap);
            
            //获得表格
            List<XWPFTable> tables = doc.getTables();
            //第一个表和第四个表共用一个查询
            //查询指标信息
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_A",pro.getIdA());
            entityWrapper.eq("CREATE_STAUTS","2");
            entityWrapper.orderBy("VERSION_NO",false);
            List<RelationProIndex> relationProIndexList = relationProIndexService.selectList(entityWrapper);
            //获取最新的版本
            RelationProIndex relationProIndex = relationProIndexList.get(relationProIndexList.size()-1);
            EntityWrapper indexSystemDseignWrapper = new EntityWrapper();
            indexSystemDseignWrapper.eq("ID_R",relationProIndex.getIdR());
            //最新指标信息
            List<TIndexSystemDseign> indexSystemDseignList = indexSystemDseignService.selectList(indexSystemDseignWrapper);
            //组装一级指标和二级指标的分值
            List<String[]> indexSystemDseignatt = indexSystemDseignService.getTableInfo(indexSystemDseignList);
            //添加最后一列
            indexSystemDseignatt.add(new String[]{"总分","100",null,"100",null,"100",null,null,null});
            //第4个表添加数据
            WordUtils.insertTable(tables.get(3),indexSystemDseignatt);
            //第1个绩效指标表数据
            List<String[]> list01 = new ArrayList<String[]>();
            for (int i = 0; i < indexSystemDseignatt.size(); i++) {
                String[] arr = indexSystemDseignatt.get(i);
                list01.add(new String[]{arr[0],arr[1],arr[2],arr[3]});
            }
            //添加第一个表绩效评价指标框架表数据
            WordUtils.insertTable(tables.get(0),list01);

            //获取段落
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            EntityWrapper indexSystemOpinionsWrapper1 = new EntityWrapper();
            indexSystemOpinionsWrapper1.eq("ID_R",relationProIndex.getIdR());
            List<IndexSystemOpinions> indexSystemOpinionsList = indexSystemOpinionsService.selectList(indexSystemOpinionsWrapper1);
            String indexSystemOpinionshead = null;
            String indexSystemOpinionsbody = null;
            if (indexSystemOpinionsList.size() > 0){
                indexSystemOpinionshead = "组织召开专家评价会。";
                indexSystemOpinionsbody = "会议结合项目情况和项目资料，就项目绩效情况进行交流和质询，评价工作组充分听取评价专家意见，并根据其意见修改完善绩效评价指标体系和初步评价结论。 \r\n";
            }else {
                //删除评价工作时间安排表中第9行
                tables.get(2).removeRow(8);
            }
            //匹配需要替换的字段
            for (XWPFParagraph paragraph : paragraphs) {
                String text = paragraph.getText();
                XWPFRun titleRun = paragraph.createRun();
                if ("indexSystemOpinionshead".equals(text)){
                    titleRun.setText(indexSystemOpinionshead);
                    titleRun.setBold(true);
                }else if ("indexSystemOpinionsbody".equals(text)){
                    titleRun.setText(indexSystemOpinionsbody);
                    titleRun.addBreak();
                }
                //字体是否加粗
            }
            //第2个表
            //项目经理，项目秘书、外勤主管、组员
            EntityWrapper workingGroupWrapper = new EntityWrapper();
            workingGroupWrapper.eq("ID_A",pro.getIdA());
            List<TPerformanceWorkingGroup> workingGroupList = performanceWorkingGroupService.selectList(workingGroupWrapper);
            //第一个动态生成的数据列表
            List<String[]> newWorkingGroupList = new ArrayList<String[]>();
            //添加项目经理
            RcUser proManager = userService.selectById(pro.getProManagerId());
            newWorkingGroupList.add(new String[]{"1",proManager.getName(),proManager.getUserLeavel()});
            //添加项目秘书
            RcUser proSecretary = userService.selectById(pro.getProSecretaryId());
            newWorkingGroupList.add(new String[]{"2",proSecretary.getName(),proSecretary.getUserLeavel()});
            //添加组员和外勤主管
            for (int i = 0; i < workingGroupList.size(); i++) {
                newWorkingGroupList.add(new String[]{String.valueOf(i+3),workingGroupList.get(i).getGroupMemberName(),workingGroupList.get(i).getGroupMemberName(),workingGroupList.get(i).getUserLeavel()});
            }
            //第2个表添加数据
            WordUtils.insertTable(tables.get(1),newWorkingGroupList);

            //第4个表(绩效评价指标体系框架及评分标准)
            //查询指标信息
           /* EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_A",pro.getIdA());
            entityWrapper.eq("CREATE_STAUTS","2");
            entityWrapper.orderBy("VERSION_NO",false);
            List<RelationProIndex> relationProIndexList = relationProIndexService.selectList(entityWrapper);
            //获取最新的版本
            RelationProIndex relationProIndex = relationProIndexList.get(relationProIndexList.size()-1);
            EntityWrapper indexSystemDseignWrapper = new EntityWrapper();
            indexSystemDseignWrapper.eq("ID_R",relationProIndex.getIdR());
            //最新指标信息
            List<TIndexSystemDseign> indexSystemDseignList = indexSystemDseignService.selectList(indexSystemDseignWrapper);
            //组装一级指标和二级指标的分值
            List<String []> indexSystemDseignatt = indexSystemDseignService.getTableInfo(indexSystemDseignList);
            //添加最后一列
            indexSystemDseignatt.add(new String[]{"总分","100",null,"100",null,"100",null,null,null});
            //第4个表添加数据
            WordUtils.insertTable(tables.get(3),indexSystemDseignatt);*/

            //第5个表 调研安排
            EntityWrapper relationProResearchScheduleWrapper = new EntityWrapper();
            relationProResearchScheduleWrapper.eq("ID_A",pro.getIdA());
            relationProResearchScheduleWrapper.eq("CREATE_STAUTS","2");
            relationProResearchScheduleWrapper.orderBy("VERSION_NO",false);
            List<RelationProResearchSchedule> relationProResearchScheduleList = relationProResearchScheduleService.selectList(relationProResearchScheduleWrapper);
            //获取最新版本数据
            RelationProResearchSchedule relationProResearchSchedule = relationProResearchScheduleList.get(relationProResearchScheduleList.size() - 1);
            EntityWrapper researchScheduleWrapper = new EntityWrapper();
            researchScheduleWrapper.eq("ID_R",relationProResearchSchedule.getIdR());
            //调研安排表数据
            List<TResearchSchedule> researchScheduleList = researchScheduleService.selectList(researchScheduleWrapper);
            List<String[]> researchScheduleArr = new ArrayList<String[]>();
            for (int i = 0; i < researchScheduleList.size(); i++) {
                TResearchSchedule researchSchedule = researchScheduleList.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                researchScheduleArr.add(new String[]{String.valueOf(i+1),researchSchedule.getResearchPlaceProvince(),
                        researchSchedule.getResearchPlaceCity(),researchSchedule.getResearchPlaceCounty(),
                        researchSchedule.getDetailedAddress(),sdf.format(researchSchedule.getResearchDate()),
                        String.valueOf(researchSchedule.getResearchDays()),researchSchedule.getSpecificShape(),
                        researchSchedule.getGroupLeader(),researchSchedule.getGroupMembers(),
                        researchSchedule.getSelectionBasis(),researchSchedule.getDataSources()});
            }
            WordUtils.insertTable(tables.get(4),researchScheduleArr);
            
//            WordExportUtil.exportWord07(doc,map);
           /* doc.write(os);
            os.flush();
            os.close();*/
            doc.write(os);
            os.flush();
            os.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改/查看/审批页面信息查询接口
     * @author: 陈睿超
     * @createDate: 2021/4/16 10:05
     * @updater: 陈睿超
     * @updateDate: 2021/4/16 10:05
     * @param id:查询主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation("修改/查看/审批页面信息查询接口")
    @PostMapping(value = "edit")
    public Result edit(Integer id){
        try {
            if (id == null){
                return getJsonResult(false, "请选择需要查看信息", null);
            }else {
                HashMap<String,Object> hashMap = new HashMap<String,Object>();
                RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan = relationProPreparEvalWorkPlanService.selectById(id);
                hashMap.put("relationProPreparEvalWorkPlan",relationProPreparEvalWorkPlan);
                //查询编制评价工作方案
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_R",id);
                entityWrapper.orderBy("ID_R",true);
                List<TPreparEvalWorkPlan> preparEvalWorkPlanList = preparEvalWorkPlanService.selectList(entityWrapper);
                hashMap.put("preparEvalWorkPlanList",preparEvalWorkPlanList);
                //查询审批记录
                EntityWrapper checkEntityWrapper = new EntityWrapper();
                checkEntityWrapper.eq("ID_R",id);
                checkEntityWrapper.orderBy("CHECK_TIME",false);
                List<TPreparEvalWorkPlanCheckRec> checkList = preparEvalWorkPlanCheckRecService.selectList(checkEntityWrapper);
                hashMap.put("checkList",checkList);
                //用于组装工作流审批记录
                EntityWrapper checkEntityWrapper1 = new EntityWrapper();
                checkEntityWrapper1.eq("ID_R",id);
                checkEntityWrapper1.eq("CHECK_DATA_STATUS","1");
                List<TPreparEvalWorkPlanCheckRec> checkList1 = preparEvalWorkPlanCheckRecService.selectList(checkEntityWrapper1);
                //查询流程
                EntityWrapper workFlowEntityWrapper = new EntityWrapper();
                workFlowEntityWrapper.eq("ID_R",id);
                List<BussinessFlowPreparEvalWorkPlan> workFlowList = bussinessFlowPreparEvalWorkPlanService.selectList(workFlowEntityWrapper);
                workFlowList = bussinessFlowPreparEvalWorkPlanService.assemblyBussinessFlow(workFlowList,checkList1);
                hashMap.put("workFlowList",workFlowList);
                return getJsonResult(true, null, hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, null, null);
        }
    }

    /**
     * Title: checkPage
     * Description: 待审批分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/21 17:38
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 17:38
     * @param start
     * @param pageSize
     * @param date
     * @param search:精确查询
     * @param request
     * @param relationProPreparEvalWorkPlan:精确查询
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "待审批分页查询")
    @PostMapping(value = "checkPage")
    public Result checkPage(@RequestParam(value = "current", defaultValue = "1") int start,
                            @RequestParam(value = "size", defaultValue = "10") int pageSize,
                            @RequestParam(value = "date", required = false) String date,
                            @RequestParam(value = "search", required = false) String search,
                            HttpServletRequest request,RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan){
        try {
            PageInfo<RelationProPreparEvalWorkPlan> page = relationProPreparEvalWorkPlanService.checkPagelist(start, pageSize, search, relationProPreparEvalWorkPlan, getUser());
            return getJsonResult(true,null,page);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * 审批
     * @author: 陈睿超
     * @createDate: 2021/4/16 16:13
     * @updater: 陈睿超
     * @updateDate: 2021/4/16 16:13
     * @param relationProPreparEvalWorkPlanVo : 接收前天传过来的参数
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "审批")
    @PostMapping(value = "check")
    public Result check(@RequestBody RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo){
        try {
            Boolean b = bussinessFlowPreparEvalWorkPlanService.check(relationProPreparEvalWorkPlanVo,getUser());
            return getJsonResult(b, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * Title: getWorkuser
     * Description: 获取项目组成员信息
     * @author: 陈睿超
     * @createDate: 2021/4/16 16:13
     * @updater: 陈睿超
     * @updateDate: 2021/4/16 16:13
     * @param  bean.idA : 项目主键id
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
                return getJsonResult(false,"请选择需要查询的项目",null);
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
     * Title: 
     * Description: 逻辑删除
     * @author: 陈睿超
     * @createDate: 2021/4/21 21:05
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 21:05
     * @param id : 关系表id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "逻辑删除")
    @PostMapping(value = "delete")
    public Result delete(Integer id){
        try {
            if (id == null){
                return getJsonResult(false, "请选择需要删除信息", null);
            }else {
                RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan = relationProPreparEvalWorkPlanService.selectById(id);
                relationProPreparEvalWorkPlan.setRelationStatus("9");
                relationProPreparEvalWorkPlanService.updateById(relationProPreparEvalWorkPlan);
                return getJsonResult(true, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }

    /**
     * Title: deleteFiles
     * Description: 删除未保存的附件
     * @author: 陈睿超
     * @createDate: 2021/4/22 10:20
     * @updater: 陈睿超
     * @updateDate: 2021/4/22 10:20
     * @param filesId ： 需要删除附件id数组
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "删除未保存的附件")
    @PostMapping(value = "deleteFiles")
    public Result deleteFiles(Integer[] filesId){
        try {
            if (filesId.length <= 0){
                return getJsonResult(false, "请上传无用附件信息", null);
            }else {
                //页面关闭时，删除已上传但并不保存的附件
                for (int i = 0; i < filesId.length; i++) {
                    preparEvalWorkPlanService.deleteById(filesId[i]);
                }
                return getJsonResult(true, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }




}



