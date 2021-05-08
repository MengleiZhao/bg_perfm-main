package com.common.business.planmgr.pre.mkquestion.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.planmgr.pre.mkquestion.entity.*;
import com.common.business.planmgr.pre.mkquestion.service.*;
import com.common.business.planmgr.pre.mkquestioncheck.entity.BussinessFlowProQuestionnaire;
import com.common.business.planmgr.pre.mkquestioncheck.entity.TQuestionnaireCheckRec;
import com.common.business.planmgr.pre.mkquestioncheck.service.BussinessFlowProQuestionnaireService;
import com.common.business.planmgr.pre.mkquestioncheck.service.TQuestionnaireCheckRecService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.DateUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  项目拟定调查问卷关系 
 *  前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
@RestController
@Api(value = "预调研调查问卷接口Controller",tags = {"预调研调查问卷接口"})
@RequestMapping("/relationProQuestionnaire")
public class RelationProQuestionnaireController extends BaseController {

    @Autowired
    private RelationProQuestionnaireService relationProQuestionnaireService;
    @Autowired
    private TQuestionnaireService questionnaireService;
    @Autowired
    private TQuestionnaireSubjectsService questionnaireSubjectsService;
    @Autowired
    private TQuestionnaireSubjectsOptionsService questionnaireSubjectsOptionsService;
    @Autowired
    private TQuestionnaireCheckRecService questionnaireCheckRecService;
    @Autowired
    private BussinessFlowProQuestionnaireService bussinessFlowProQuestionnaireService;
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;
    @Autowired
    private TPerformanceWorkingGroupService performanceWorkingGroupService;
    @Autowired
    private TQuestionnaireAnswerSheetService questionnaireAnswerSheetService;
    @Autowired
    private TAnswerSheetSubjectsOptionsService answerSheetSubjectsOptionsService;
    @Autowired
    private TSubjectsOptionsAnswerService subjectsOptionsAnswerService;



    /**
     * @Title:
     * @Description:  问卷拟定页面分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/1 10:14
     * @updater: 陈睿超
     * @updateDate: 2021/4/1 10:14
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "问卷拟定页面分页查询")
    @PostMapping(value = "pageList")
    public Result pageList(@RequestParam(value = "current", defaultValue = "1") int start,
                           @RequestParam(value = "size", defaultValue = "10") int pageSize,
                           @RequestParam(value = "date", required = false) String date,
                           @RequestParam(value = "search", required = false) String search,
                           HttpServletRequest request, RelationProQuestionnaire bean){
        try {
            PageInfo<RelationProQuestionnaire> pagelist = relationProQuestionnaireService.pagelist(start, pageSize, search, bean, getUser());
            return getJsonResult(true,null,pagelist);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }


    /**
     * @Title:
     * @Description: 新增选择项目接口
     * @author: 陈睿超
     * @createDate: 2021/4/1 14:41
     * @updater: 陈睿超
     * @updateDate: 2021/4/1 14:41
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param pro 项目
     * @param preOrScheme 预调研还是编制评价方案，0-预调研，1-编制评价方案
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "新增选择项目接口")
    @PostMapping(value = "choosePro")
    public Result choosePro(@RequestParam(value = "current", defaultValue = "1") int start,
                            @RequestParam(value = "size", defaultValue = "10") int pageSize,
                            @RequestParam(value = "date", required = false) String date,
                            @RequestParam(value = "search", required = false) String search,
                            HttpServletRequest request, TProPerformanceInfo pro, Integer preOrScheme){
        try {
            PageInfo<TProPerformanceInfo> page = relationProQuestionnaireService.choosePro(start, pageSize, preOrScheme,null, null, getUser());
            return getJsonResult(true,null,page);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description: 校验选择项目是否已有在编辑中的问卷调查
     * @author: 陈睿超
     * @createDate: 2021/4/7 14:37
     * @updater: 陈睿超
     * @updateDate: 2021/4/7 14:37
     * @param 	id : 项目id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "校验选择项目是否已有在编辑中的问卷调查")
    @PostMapping(value = "checkVersion")
    public Result checkVersion(Integer id,Integer preOrScheme){
        try {
            ShiroUser user = getUser();
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_A",id);
            entityWrapper.eq("CREATE_UASE_ID",user.getId());
            entityWrapper.eq("RELATION_STATUS","1");
            entityWrapper.eq("PRE_OR_SCHEME",preOrScheme);
            entityWrapper.in("CREATE_STAUTS","-1,0,1");
            List<RelationProQuestionnaire> list = relationProQuestionnaireService.selectList(entityWrapper);
            if (list.size() > 0){
                return getJsonResult(false,"本项目已有相同问卷存在，请修改",null);
            }else {
                return getJsonResult(true,null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description: 新增的时候启用上一个版本
     * @author: 陈睿超
     * @createDate: 2021/4/6 15:07
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 15:07
     * @param id :  项目id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "新增的时候启用上一个版本")
    @PostMapping(value = "getLastVersion")
    public Result getLastVersion(Integer id,Integer preOrScheme){
        try {
            if (null == id){
                return getJsonResult(false,"请传需要删除信息的id",null);
            }else{
                HashMap<String,Object> hashMap = new HashMap<String,Object>();
                //查询该项目最新的版本数据 
                ShiroUser user = getUser();
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_A",id);
                entityWrapper.eq("CREATE_UASE_ID",user.getId());
                entityWrapper.eq("RELATION_STATUS","1");
                entityWrapper.eq("CREATE_STAUTS","2");
                entityWrapper.eq("PRE_OR_SCHEME",preOrScheme);
                entityWrapper.orderBy("VERSION_NO",true);
                List<RelationProQuestionnaire> list = relationProQuestionnaireService.selectList(entityWrapper);
                //查询项目拟定调查问卷关系
                RelationProQuestionnaire relationProQuestionnaire = relationProQuestionnaireService.selectById(list.get(list.size()-1).getIdR());
                hashMap.put("relationProQuestionnaire",relationProQuestionnaire);
                //查询拟定调查问卷
                EntityWrapper questionnaireEntityWrapper = new EntityWrapper();
                questionnaireEntityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
                TQuestionnaire questionnaire = questionnaireService.selectOne(questionnaireEntityWrapper);
                hashMap.put("questionnaire",questionnaire);
                //查询问卷题目表
                EntityWrapper questionnaireSubjectsEntityWrapper = new EntityWrapper();
                questionnaireSubjectsEntityWrapper.eq("ID_B",questionnaire.getIdB());
                List<TQuestionnaireSubjects> questionnaireSubjectsList = questionnaireSubjectsService.selectList(questionnaireSubjectsEntityWrapper);
                //查询问卷答项表
                for (int i = 0; i < questionnaireSubjectsList.size(); i++) {
                    EntityWrapper questionnaireSubjectsOptionsEntityWrapper = new EntityWrapper();
                    questionnaireSubjectsOptionsEntityWrapper.eq("ID_C",questionnaireSubjectsList.get(i).getIdC());
                    List<TQuestionnaireSubjectsOptions> questionnaireSubjectsOptionsList = questionnaireSubjectsOptionsService.selectList(questionnaireSubjectsOptionsEntityWrapper);
                    questionnaireSubjectsList.get(i).setQuestionnaireSubjectsOptions(questionnaireSubjectsOptionsList);
                }
                hashMap.put("questionnaireSubjectsList",questionnaireSubjectsList);
                return getJsonResult(true,null,hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title:
     * @Description: 保存
     * @author: 陈睿超
     * @createDate: 2021/4/1 15:12
     * @updater: 陈睿超
     * @updateDate: 2021/4/1 15:12
     * @param bean : 保存对象
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "保存")
    @PostMapping(value = "saveQuestionnaire")
    public Result saveQuestionnaire(@RequestBody  RelationProQuestionnaireVo beanVo){
        Boolean b = null;
        try {
            b = relationProQuestionnaireService.saveQuestionnaire(beanVo,getUser());
            return getJsonResult(b,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title: delete
     * @Description: 根据RelationProQuestionnaire的主键id逻辑删除
     * @author: 陈睿超
     * @createDate: 2021/4/6 11:29
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 11:29
     * @param id : RelationProQuestionnaire的主键
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "根据id逻辑删除")
    @PostMapping(value = "deleteProQuestionnaire")
    public Result deleteProQuestionnaire(Integer id){
        Boolean b = null;
        try {
            if (null == id){
                return getJsonResult(false,"请传需要删除信息的id",null);
            }else{
                b = relationProQuestionnaireService.deleteByIdA(id,getUser());
                return getJsonResult(b,null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(b,null,null);
        }

    }

    /**
     * @Title:
     * @Description: 查看/修改/审批数据查询
     * @author: 陈睿超
     * @createDate: 2021/4/6 15:07
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 15:07
     * @param id : RelationProQuestionnaire的主键
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "查看/修改/审批数据查询")
    @PostMapping(value = "editProQuestionnaire")
    public Result editProQuestionnaire(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"请传需要删除信息的id",null);
            }else{
                HashMap<String,Object> hashMap = new HashMap<String,Object>();
                //查询项目拟定调查问卷关系
                RelationProQuestionnaire relationProQuestionnaire = relationProQuestionnaireService.selectById(id);
                hashMap.put("relationProQuestionnaire",relationProQuestionnaire);
                //查询拟定调查问卷
                EntityWrapper questionnaireEntityWrapper = new EntityWrapper();
                questionnaireEntityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
                TQuestionnaire questionnaire = questionnaireService.selectOne(questionnaireEntityWrapper);
                hashMap.put("questionnaire",questionnaire);
                //查询问卷题目表
                EntityWrapper questionnaireSubjectsEntityWrapper = new EntityWrapper();
                questionnaireSubjectsEntityWrapper.eq("ID_B",questionnaire.getIdB());
                List<TQuestionnaireSubjects> questionnaireSubjectsList = questionnaireSubjectsService.selectList(questionnaireSubjectsEntityWrapper);
                //查询问卷答项表
                for (int i = 0; i < questionnaireSubjectsList.size(); i++) {
                    EntityWrapper questionnaireSubjectsOptionsEntityWrapper = new EntityWrapper();
                    questionnaireSubjectsOptionsEntityWrapper.eq("ID_C",questionnaireSubjectsList.get(i).getIdC());
                    List<TQuestionnaireSubjectsOptions> questionnaireSubjectsOptionsList = questionnaireSubjectsOptionsService.selectList(questionnaireSubjectsOptionsEntityWrapper);
                    questionnaireSubjectsList.get(i).setQuestionnaireSubjectsOptions(questionnaireSubjectsOptionsList);
                }
                hashMap.put("questionnaireSubjectsList",questionnaireSubjectsList);
                //查询审批记录
                EntityWrapper checkentityWrapper = new EntityWrapper();
                checkentityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
                checkentityWrapper.orderBy("CHECK_TIME",false);
                List<TQuestionnaireCheckRec> questionnaireCheckRecList = questionnaireCheckRecService.selectList(checkentityWrapper);
                hashMap.put("questionnaireCheckRecList",questionnaireCheckRecList);
                //查询组装数据审批记录
                EntityWrapper checkentityWrapper1 = new EntityWrapper();
                checkentityWrapper1.eq("ID_R",relationProQuestionnaire.getIdR());
                checkentityWrapper1.eq("CHECK_DATA_STATUS","1");
                List<TQuestionnaireCheckRec> questionnaireCheckRecList1 = questionnaireCheckRecService.selectList(checkentityWrapper1);
                //查询工作流
                EntityWrapper workFlowEntityWrapper = new EntityWrapper();
                workFlowEntityWrapper.eq("ID_R",relationProQuestionnaire.getIdR());
                workFlowEntityWrapper.orderBy("ORDER_OF_CURRENT_NODE",true);
                List<BussinessFlowProQuestionnaire> bussinessFlowProQuestionnaireList = bussinessFlowProQuestionnaireService.selectList(workFlowEntityWrapper);
                //组装工作流数据和审批记录
                bussinessFlowProQuestionnaireList = bussinessFlowProQuestionnaireService.assemblyBussinessFlow(bussinessFlowProQuestionnaireList,questionnaireCheckRecList1);
                hashMap.put("bussinessFlowProQuestionnaireList",bussinessFlowProQuestionnaireList);

                return getJsonResult(true,null,hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
    }

    /**
     * @Title: checkPage
     * @Description: 审批页分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/7 11:03
     * @updater: 陈睿超
     * @updateDate: 2021/4/7 11:03
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "审批页分页查询")
    @PostMapping(value = "checkPage")
    public Result checkPage(@RequestParam(value = "current", defaultValue = "1") int start,
                            @RequestParam(value = "size", defaultValue = "10") int pageSize,
                            @RequestParam(value = "date", required = false) String date,
                            @RequestParam(value = "search", required = false) String search,
                            HttpServletRequest request, RelationProQuestionnaire bean){
        try {
            PageInfo<RelationProQuestionnaire> pagelist = relationProQuestionnaireService.pagelist(start, pageSize, search, bean, getUser());
            return getJsonResult(true,null,pagelist);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }

    /**
     * @Title:
     * @Description:  审批
     * @author: 陈睿超
     * @createDate: 2021/4/6 16:35
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 16:35
     * @param questionnaireCheckRec : 审批信息
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "审批/转派")
    @PostMapping(value = "checkProQuestionnaire")
    public Result checkProQuestionnaire(TQuestionnaireCheckRec questionnaireCheckRec){
        Boolean b = null;
        try {
            b = relationProQuestionnaireService.check(questionnaireCheckRec,getUser());
            return getJsonResult(b,null,null);
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
     * 保存答卷信息
     * @author: 陈睿超
     * @createDate: 2021/4/20 16:23
     * @updater: 陈睿超
     * @updateDate: 2021/4/20 16:23
     * @param relationProQuestionnaireVo:答卷信息
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "保存答卷信息")
    @PostMapping(value = "saveAnswerSheet")
    public Result saveAnswerSheet(@RequestBody RelationProQuestionnaireVo relationProQuestionnaireVo){
        //2021.04.20 和许丹一丰讨论后，校验信息由前端来做
        //调查问卷
       /* TQuestionnaire questionnaire = relationProQuestionnaireVo.getQuestionnaire();
        if ("1".equals(questionnaire.getIsTimeControl())){
            //是否时间控制
            if (DateUtil.getDaySpanNoAbs(questionnaire.getQuesStartTime(),new Date()) < 0 || 
                    DateUtil.getDaySpanNoAbs(questionnaire.getQuesEndTime(),new Date()) > 0){
                String quesStartTime = DateUtil.formatDate(questionnaire.getQuesStartTime());
                String quesEndTime = DateUtil.formatDate(questionnaire.getQuesEndTime());
                return getJsonResult(false,"问卷作答时间为"+quesStartTime+"~"+quesEndTime+"，请在规定时间内作答",null);
            }
        }*/
        try {
            questionnaireAnswerSheetService.saveAnswerSheet(relationProQuestionnaireVo,getUser());
            return getJsonResult(true,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,e.getMessage(),null);
        }
    }

    /**
     * 发布调查问卷
     * @author: 陈睿超
     * @createDate: 2021/4/20 16:33
     * @updater: 陈睿超
     * @updateDate: 2021/4/20 16:33
     * @param id:TQuestionnaire的主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "发布调查问卷")
    @PostMapping(value = "releaseQuestionnaire")
    public Result releaseQuestionnaire(Integer id){

        try {
            questionnaireService.releaseQuestionnaire(id);
            return getJsonResult(true,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,e.getMessage(),null);
        }
    }

    /**
     * 关闭调查问卷
     * @author: 陈睿超
     * @createDate: 2021/4/20 16:33
     * @updater: 陈睿超
     * @updateDate: 2021/4/20 16:33
     * @param id:TQuestionnaire的主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "关闭调查问卷")
    @PostMapping(value = "closeQuestionnaire")
    public Result closeQuestionnaire(Integer id){
        try {
            questionnaireService.closeQuestionnaire(id);
            return getJsonResult(true,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,e.getMessage(),null);
        }
    }

    /**
     * Title: selectAnswerSheet
     * Description: 查看已答所有答卷列表
     * @author: 陈睿超
     * @createDate: 2021/4/21 09:41
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 09:41
     * @param questionnaireAnswerSheet：精确查询(外键idB不能为空)
     * @param user：当前登录人
     * @param search：模糊查询
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "查看已答的答卷列表")
    @PostMapping(value = "selectAnswerSheet")
    public Result selectAnswerSheet(@RequestParam(value = "current", defaultValue = "1") int start,
                                    @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "date", required = false) String date,
                                    @RequestParam(value = "search", required = false) String search,
                                    HttpServletRequest request, TQuestionnaireAnswerSheet questionnaireAnswerSheet){
        try {
            PageInfo<TQuestionnaireAnswerSheet> page = questionnaireAnswerSheetService.closeQuestionnaire(start, pageSize, search, questionnaireAnswerSheet, getUser());
            return getJsonResult(true,null,page);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,e.getMessage(),null);
        }
    }

    /**
     * Title: editAnswerSheet
     * Description: 查看答卷信息
     * @author: 陈睿超
     * @createDate: 2021/4/21 11:02
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 11:02
     * @param id：TQuestionnaireAnswerSheet的主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "查看答卷信息")
    @PostMapping(value = "editAnswerSheet")
    public Result editAnswerSheet(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"请选择需要查看的答卷",null);
            }else {
                HashMap<String,Object> hashMap = new HashMap<String,Object>();
                //查询答卷信息
                TQuestionnaireAnswerSheet questionnaireAnswerSheet = questionnaireAnswerSheetService.selectById(id);
                hashMap.put("questionnaireAnswerSheet",questionnaireAnswerSheet);
                //查询答项信息
                EntityWrapper optionsEntityWrapper = new EntityWrapper();
                optionsEntityWrapper.eq("ID_C",questionnaireAnswerSheet.getIdC());
                optionsEntityWrapper.orderBy("SUB_NO",true);
                List<TAnswerSheetSubjectsOptions> subjectsOptionsList = answerSheetSubjectsOptionsService.selectList(optionsEntityWrapper);
                for (int i = 0; i < subjectsOptionsList.size(); i++) {
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("ID_D",subjectsOptionsList.get(i).getIdD());
                    entityWrapper.orderBy("OPTION_ORDER_NO",true);
                    List<TSubjectsOptionsAnswer> list = subjectsOptionsAnswerService.selectList(entityWrapper);
                    subjectsOptionsList.get(i).setSubjectsOptionsAnswer(list);
                }
                hashMap.put("subjectsOptionsList",subjectsOptionsList);
                return getJsonResult(true,null,hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,e.getMessage(),null);
        }

    }

    /**
     * Title: 
     * Description: 
     * @author: 陈睿超
     * @createDate: 2021/4/21 14:42
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 14:42
     * @param id：TQuestionnaireAnswerSheet的主键id
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "第一次答卷时查看问卷信息")
    @PostMapping(value = "selectQuestionnaire")
    public Result selectQuestionnaire(Integer id){
        try {
            if (null == id){
                return getJsonResult(false,"请选择需要答题的答卷",null);
            }else {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                //查询问卷信息
                TQuestionnaire questionnaire = questionnaireService.selectById(id);
                hashMap.put("questionnaire",questionnaire);
                //查询问卷题目和题目选项
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_B",questionnaire.getIdB());
                entityWrapper.orderBy("SUB_NO",true);
                List<TQuestionnaireSubjects> questionnaireSubjectsList= questionnaireSubjectsService.selectList(entityWrapper);
                for (int i = 0; i < questionnaireSubjectsList.size(); i++) {
                    EntityWrapper optionsEntityWrapper = new EntityWrapper();
                    optionsEntityWrapper.eq("ID_C",questionnaireSubjectsList.get(i).getIdC());
                    optionsEntityWrapper.orderBy("OPTION_ORDER_NO",true);
                    List<TQuestionnaireSubjectsOptions> optionsList = questionnaireSubjectsOptionsService.selectList(optionsEntityWrapper);
                    questionnaireSubjectsList.get(i).setQuestionnaireSubjectsOptions(optionsList);
                }
                hashMap.put("questionnaireSubjectsList",questionnaireSubjectsList);
                return getJsonResult(true,null,hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,e.getMessage(),null);
        }
    }

    
    
    
    


}
