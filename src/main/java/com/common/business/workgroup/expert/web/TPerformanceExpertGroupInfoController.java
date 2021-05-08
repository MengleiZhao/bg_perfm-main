package com.common.business.workgroup.expert.web;


import com.common.business.library.experts.entity.TLibraryPerformanceExpert;
import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.web.TPerformanceWorkingGroupVo;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import com.common.business.workgroup.expert.service.TPerformanceExpertGroupInfoService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/tPerformanceExpertGroupInfo")
@Api(value = "/tPerformanceExpertGroupInfo", description = "组建专家组跟工作组台账接口")
public class TPerformanceExpertGroupInfoController extends BaseController {

    @Autowired
    private TPerformanceExpertGroupInfoService tPerformanceExpertGroupInfoService;//服务层接口

    /**ok
     * 1.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 主页面分页显示数据
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param parentProName 根据“项目名称”搜索
     * @param riskLevel     根据“风险等级”搜索
     * @param search        综合查询的字段
     * @return Result 将返回的TProPerformanceInfo类型的集合封装成Result对象【如果Result对象为空，则有异常】
     * @author 田鑫艳
     * @createTime 2021/3/8 16:24
     * @updateTime 2021/3/11 10:57
     */
    @ApiOperation("主页面分页显示数据")
    @RequestMapping(value="/page",method = RequestMethod.POST)
    public Result queryForPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "parentProName", required = false) String parentProName,
            @RequestParam(value = "riskLevel", required = false) String riskLevel,
            @RequestParam(value = "search", required = false) String search

    ) {
        try {
            //1.将精确查询的字段 封装成对象
            TProPerformanceInfo tProPerformanceInfo = new TProPerformanceInfo();
            tProPerformanceInfo.setRiskLevel(riskLevel);//风险等级
            tProPerformanceInfo.setParentProName(parentProName);//主项目名字
            String proManagerId=String.valueOf(getUser().getId());

            //2.调用服务层的方法拿到值
            PageInfo<TProPerformanceInfo> pageInfo = tPerformanceExpertGroupInfoService.queryForPage(current, size, tProPerformanceInfo, search,proManagerId);

            if(pageInfo.getSize()>0){
                //3.封装成 Result对象
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            }else{
                return getJsonResult(true, "您还没有任何要分配专家组的项目", "");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**ok
     * 2.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 查询 项目信息 -->主项目信息
     * @param idA TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return Result 将返回的"项目信息"内容存放于Result中
     * @author 田鑫艳
     * @createTime 2021/3/8 21:46
     * @updateTime 2021/3/8 21:46
     */
    @ApiOperation("查询 项目信息 -->主项目信息")
    @RequestMapping(value = "selectProjectInfo", method = RequestMethod.GET)
    public Result selectProjectInfo(Integer idA) {
        try {
            //1.从服务层拿“项目信息”集合数据
            TProPerformanceInfo tProPerformanceInfo = tPerformanceExpertGroupInfoService.selectProjectInfo(idA);
            if(tProPerformanceInfo!=null){
                //2.封装成 Result对象
                Result result = new Result();
                result.setData(tProPerformanceInfo);
                return result;
            }else{
                return getJsonResult(true, "没有查到该项目的信息", "");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }

    /**ok
     * 3.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 查询 项目信息 -->主子项目的被评单位
     * @param idA 主键id值 任意一个主项目下面的id主键
     * @return Result
     * @author 田鑫艳
     * @createTime 2021/3/11 13:37
     * @updateTime 2021/3/11 13:37
     */
    @ApiOperation("查询 项目信息 -->每一个子项目的被评单位")
    @RequestMapping(value = "selectPEvaluatedInfo", method = RequestMethod.GET)
    public Result selectPEvaluatedInfo(Integer idA) {
        try {
            //1.从服务层拿“项目信息”集合数据
            List<TEvalUnitInfo> tEvalUnitInfos = tPerformanceExpertGroupInfoService.selectPEvaluatedInfo(idA);
            if(tEvalUnitInfos.size()>0){
                //2.封装成 Result对象
                Result result = new Result();
                result.setData(tEvalUnitInfos);
                return result;
            }else{
                return getJsonResult(true, "没有查到该项目的被评单位信息", "");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**ok
     * 4.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 查询-->工作组信息：每个主子项目的组员信息
     * @param parentProCode TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return Result 将返回的"工作组信息"内容存放于Result中
     * @author 田鑫艳
     * @createTime 2021/3/9 16:07
     * @updateTime 2021/3/11 14:03
     */
    @ApiOperation("查询-->工作组信息：每个主子项目的组员信息")
    @RequestMapping(value = "selectWorkGroupInfo", method = RequestMethod.GET)
    public Result selectWorkGroupInfo(String parentProCode) {
        try {
            //1.从服务层拿 “组员信息集合”集合数据和该 主/子 项目的信息
            List<TProPerformanceInfo> proPerformanceInfos = tPerformanceExpertGroupInfoService.selectWorkGroupInfo(parentProCode,getUser());
            if(proPerformanceInfos.size()>0){
                //2.封装成 Result对象
                Result result = new Result();
                result.setData(proPerformanceInfos);
                return result;
            }else{
                return getJsonResult(true, "没有查到该项目信息以及每个项目的组员信息", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }


    /**ok
     * 5.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 查询-->专家组信息 ：每个主子项目的专家组信息
     *
     * @param parentProCode TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return Result 将返回的"专家组信息"内容存放于Result中
     * @author 田鑫艳
     * @createTime 2021/3/9 9:41
     * @updateTime 2021/3/9 9:41
     */
    @ApiOperation("查询-->专家组信息 ：每个主子项目的专家组信息")
    @RequestMapping(value = "selectExpertGroup", method = RequestMethod.GET)
    public Result selectExpertGroup(String parentProCode) {
        try {
            //1.从服务层拿“专家组”集合数据
            List<TProPerformanceInfo> proPerformanceInfos = tPerformanceExpertGroupInfoService.selectExpertGroup(parentProCode,getUser());

            if(proPerformanceInfos.size()>0){
                //2.封装成 Result对象
                Result result = new Result();
                result.setData(proPerformanceInfos);
                return result;
            }else{
                return getJsonResult(true, "没有查到该项目信息以及每个项目的专家组信息", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }


    /**ok
     * 6.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 当前登录项目经理下的未组建专家组的信息  【点击页面的“组建专家组”按钮，分页查询该项目经理下的 未组建专家组 的信息】
     * @param current      开始查询的页码数 默认为第1页
     * @param size         每页的大小  默认每页显示10条数据
     * @return Result 将未组建专家组的所有数据集合封装在Result对象中
     * @author 田鑫艳
     * @createTime 2021/3/9 16:19
     * @updateTime 2021/3/9 14:06
     */
    @ApiOperation("当前登录项目经理下的未组建专家组的信息")
    @RequestMapping("/selectNoExcepertGroupInfo")
    public Result selectNoExcepertGroupInfo(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size
            ) {
        try {
            String proManagerId=String.valueOf(getUser().getId());
            //1.从服务层拿未组建专家组的集合数据
            PageInfo<TProPerformanceInfo> tPerformanceExpertGroupInfos = tPerformanceExpertGroupInfoService.selectNoExcepertGroupInfo(current, size, proManagerId);

            if(tPerformanceExpertGroupInfos.getSize()>0){
                //3.封装成 Result对象
                Result result = new Result();
                result.setData(tPerformanceExpertGroupInfos);
                return result;
            }else{
                return getJsonResult(true, "没有任何需要你组建专家组的项目", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }

    /**ok
     * 7.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 分页显示专家库中数据（去除该项目已经选择的专家）
     * @param current                开始页(即：第几页)
     * @param size                   每页的大小(即：每页的size)
     * @param expCode                专家编号
     * @param expName                专家姓名
     * @param mainResearchDirections 主要研究方向
     * @param search                 综合查询字段
     * @param expertGroupInfoVo      该项目中的idA和前端已经删除的专家信息
     * @return Result
     * @author 田鑫艳
     * @createTime 2021/3/10 14:23
     * @updateTime 2021/3/15 13:44
     */
    @ApiOperation("分页显示专家库中数据（去除该项目已经选择的专家）")
    @RequestMapping(value = "selectSetUpShowExpertGroups")
    public Result selectSetUpShowExpertGroups(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "expCode", required = false)String expCode,
            @RequestParam(value = "expName", required = false)String expName,
            @RequestParam(value = "mainResearchDirections", required = false)String mainResearchDirections,
            @RequestBody TPerformanceExpertGroupInfoVo expertGroupInfoVo
    ) {
        try {
            //1.将查询的数据封装成对象
            TLibraryPerformanceExpert tLibraryPerformanceExpert = new TLibraryPerformanceExpert();
            tLibraryPerformanceExpert.setExpCode(expCode);
            tLibraryPerformanceExpert.setExpName(expName);
            tLibraryPerformanceExpert.setMainResearchDirections(mainResearchDirections);

            //2.从服务层拿该“主项目编号”的所有项目工作组相关信息的集合
            PageInfo<TLibraryPerformanceExpert> tLibraryPerformanceExperts =
                    tPerformanceExpertGroupInfoService.selectSetUpShowExpertGroups(current, size, tLibraryPerformanceExpert, search,expertGroupInfoVo);

            if(tLibraryPerformanceExperts.getSize()>0){
                //3.封装成 Result对象
                Result result = new Result();
                result.setData(tLibraryPerformanceExperts);
                return result;
            }else{
                return getJsonResult(true, "该项目启用了所有专家，已经没有专家可供选择了", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }

    /**ok
     * 8.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 选中专家信息,跟老数据匹配且返回给前端
     * @param expertGroupInfoVo
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 21:44
     * @updateTime 2021/3/17 21:44
     */
    @ApiOperation("选中专家信息,跟老数据匹配且返回给前端")
    @RequestMapping(value = "goBackExpertGroups")
    public Result goBackExpertGroups(@RequestBody TPerformanceExpertGroupInfoVo expertGroupInfoVo) {
        try {


            //1.拿到要显示该项目的专家信息的数据
            List<TPerformanceExpertGroupInfo> expertGroupInfos =
                    tPerformanceExpertGroupInfoService.goBackExpertGroups(expertGroupInfoVo);

            //2.封装成 Result对象
            Result result = new Result();
            result.setData(expertGroupInfos);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }



    /**ok
     * 9.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 提交组建的专家成员集合信息（组建和修改都是这个操作）
     * @param
     * @return Result   (@Validated @RequestBody List<TProPerformanceInfo> tProPerformanceInfos,BindingResult br)
     * @author 田鑫艳
     * @createTime 2021/3/10 15:28
     * @updateTime 2021/3/10 15:28
     */
    @ApiOperation("提交组建的专家成员集合信息（组建和修改都是这个操作）")
    @RequestMapping(value = "setUpSaveExpertGroups",method = RequestMethod.POST)
    public Result setUpSaveExpertGroups( @RequestBody TPerformanceWorkingGroupVo proPerformanceInfos) {

        //验证合法后，将数据插入到数据库中
        try {
                System.out.println("前端传过来的 集合："+proPerformanceInfos.toString());
                //1.将选择的专家的信息插入到数据库中
                if(tPerformanceExpertGroupInfoService.setUpSaveExpertGroups(proPerformanceInfos,getUser())){
                    return getJsonResult(true, "组建成功", "");
                }else{
                    return getJsonResult(false, "您没有选择任何专家数据", "");
                }


        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "组建失败", "");
        }
    }



 /*   *//**不确定 之前用List拿不到值的原因：
     @TableField(exist = false)
     private List<TPerformanceExpertGroupInfo> tPerformanceExpertGroupInfos;//测试
     把tPerformanceExpertGroupInfos 换成 proPerformanceInfos 就可以拿到值了，所以，千万不要 一个小写字母后面紧跟大写字母
     * 8.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 提交组建的专家成员集合信息（组建和修改都是这个操作）
     * @param
     * @return Result
     * @author 田鑫艳
     * @createTime 2021/3/10 15:28
     * @updateTime 2021/3/10 15:28
     *//*
    @RequestMapping(value = "testSaveExpertGroups",method = RequestMethod.POST)

    public Result testSaveExpertGroups(@RequestBody List<TProPerformanceInfo> proPerformanceInfos) {
        System.out.println("前端拿到的对象："+proPerformanceInfos.toString());
        return null;
    }*/





    /*======================================================================================================================*/
    //项目台账

    /**ok
     * 1.TPerformanceExpertGroupInfoController [组建工作组-->组建专家组  控制层]
     * 主页面的显示
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param parentProName 根据“项目名称”搜索
     * @param riskLevel     根据“风险等级”搜索
     * @param search        综合查询的字段
     * @return Result 将返回的TProPerformanceInfo类型的集合封装成Result对象【如果Result对象为空，则有异常】
     * @author 田鑫艳
     * @createTime 2021/3/11 23:51
     * @updateTime 2021/3/11 23:51
     */
    @ApiOperation("项目台账:主页面的显示")
    @RequestMapping("/pagebookList")
    public Result pagebookList(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "parentProName", required = false) String parentProName,
            @RequestParam(value = "riskLevel", required = false) String riskLevel,
            @RequestParam(value = "search", required = false) String search
    ) {
        try {
            //1.将精确查询的字段 封装成对象
            TProPerformanceInfo tProPerformanceInfo = new TProPerformanceInfo();
            tProPerformanceInfo.setRiskLevel(riskLevel);//风险等级
            tProPerformanceInfo.setParentProName(parentProName);//主项目名字

            //2.调用服务层的方法拿到值
            PageInfo<TProPerformanceInfo> pageInfo = tPerformanceExpertGroupInfoService.pagebookList(current, size, tProPerformanceInfo, search,getUser());

            if(pageInfo.getSize()>0){
                //3.封装成 Result对象
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            }else{
                return getJsonResult(true, "没有已经组建工作组的项目", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }

    /**
     * 根据主项目编号查询查询该项目下的每个项目下的组员信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 14:01
     * @updateTime 2021/4/12 14:01
     */
    @ApiOperation("根据主项目编号查询查询该项目下已经组建工作组的每个项目下的组员信息")
    @RequestMapping(value = "infoByProCode",method = RequestMethod.POST)
    public Result infoByProCode( String  parentProCode) {

        //验证合法后，将数据插入到数据库中
        try {
            if(null==parentProCode && "".equals(parentProCode)){
                return getJsonResult(false, "请传送正确的主项目编号", "");
            }

            //1.拿到该项目下需要创建专家组的主子项目信息
            List<TProPerformanceInfo> infos =
                    tPerformanceExpertGroupInfoService.selectInfoByProCode(parentProCode);
            //2.封装成 Result对象
            Result result = new Result();
            result.setData(infos);
            return result;


        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }

    /**
     * 根据主项目编号查询需要组建专家组的子项目项目信息和专家信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 14:01
     * @updateTime 2021/4/12 14:01
     */
    @ApiOperation("根据主项目编号查询查询该项目下已经组建工作组的每个项目信息下的的专家组信息")
    @RequestMapping(value = "infoExpertByProCode",method = RequestMethod.POST)
    public Result infoExpertByProCode( String  parentProCode) {

        //验证合法后，将数据插入到数据库中
        try {
            if(null==parentProCode && "".equals(parentProCode)){
                return getJsonResult(false, "请传送正确的主项目编号", "");
            }

            //1.拿到该项目下需要创建专家组的主子项目信息
            List<TProPerformanceInfo> infoExperts =
                    tPerformanceExpertGroupInfoService.infoExpertByProCode(parentProCode);

            //2.封装成 Result对象
            Result result = new Result();
            result.setData(infoExperts);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }



    //报错的提示信息：
    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<String, String>();
        List<FieldError> list = result.getFieldErrors();
        for (FieldError error : list) {
            System.out.println("error.getField():" + error.getField());
            System.out.println("error.getDefaultMessage():" + error.getDefaultMessage());

            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }




}
