package com.common.business.planmgr.scheme.mkoutline.web;


import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.common.business.planmgr.scheme.mkoutline.service.TResearchOutlineTempService;
import com.common.system.page.BaseController;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * TResearchOutlineTempController [调研提纲管理 控制层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-05
 */
@Controller
@RequestMapping("/tResearchOutlineTemp")
public class TResearchOutlineTempController extends BaseController {

    @Autowired
    private TResearchOutlineTempService tResearchOutlineTempService;


    /**
     * 1.TResearchOutlineTempController [调研提纲管理 控制层]
     * 点击左侧的目录栏时，进入初始界面，即：/business/planmgr/scheme/mkoutline/list.ftl页面
     * @param modelAndView
     * @return ModelAndView
     * @author: 田鑫艳
     * @createTime 2021/3/5 19:06
     * @updateTime 2021/3/5 19:06
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView) {
        try {
            modelAndView.setViewName("/business/planmgr/scheme/mkoutline/list");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return modelAndView;
        }
    }


    /**
     * 2.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层] 分页显示数据
     * @param start 第几页
     * @param pageSize 每页要显示的数据值
     * @param outlineName 要查询的调研提纲名称
     * @return PageInfo<TResearchOutlineTemp  返回查到的数据集合
     * @author 田鑫艳
     * @createTime 2021/3/5 19:06
     * @updateTime 2021/3/5 19:06
     */
    @RequestMapping("/page")
    @ResponseBody  //转换为json格式
    public PageBean<TResearchOutlineTemp> queryForPage(
            @RequestParam(value = "start", defaultValue = "1") Integer start,
            @RequestParam(value = "length", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "outlineName", required = false) String outlineName) {
        //1.封装成对象
        try {
            TResearchOutlineTemp tPerformanceNewsMgr = new TResearchOutlineTemp();
            tPerformanceNewsMgr.setOutlineName(outlineName);

            //2.调用服务层的方法拿到值
            PageInfo<TResearchOutlineTemp> pageInfo = tResearchOutlineTempService.queryForPage((start / pageSize) + 1, pageSize, tPerformanceNewsMgr);
            return new PageBean<TResearchOutlineTemp>(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageBean<TResearchOutlineTemp>();
        }

    }

    /**
     * 3.TResearchOutlineTempController [调研提纲管理 控制层] 查看某一个数据的详细信息，并跳转到显示页面
     * @author: 田鑫艳
     * @createTime 2021/3/5 19:06
     * @updateTime 2021/3/5 19:06
     * @param  idX 传过来该行数据的id(主键，对应实体类的fNId字段)
     * @param modelAndView
     * @return ModelAndView
     */
    @RequestMapping(value = "view/{idX}",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer idX, ModelAndView modelAndView){
        try {
            Result<TResearchOutlineTemp> result = tResearchOutlineTempService.selectById(idX);
            modelAndView.addObject("tResearchOutlineTempView",result.getData());
            modelAndView.setViewName("/business/planmgr/scheme/mkoutline/view");//跳转的界面
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return modelAndView;
        }
    }


    /**
     * 4.TResearchOutlineTempController [调研提纲管理 控制层] 根据主键id来删除一条数据
     * @author:田鑫艳
     * @createTime 2021/3/5 19:06
     * @updateTime 2021/3/5 19:06
     * @param idX
     * @return String
     */
    @RequestMapping(value = "delete/{idX}",method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable Integer idX){
        try {
            tResearchOutlineTempService.deleteById(idX);
            return REDIRECT+"/business/planmgr/scheme/mkoutline/list";
        } catch (Exception e) {
            e.printStackTrace();
            return REDIRECT+"/templates/system/login";
        }
    }

    /**
     * 5.TResearchOutlineTempController [调研提纲管理 控制层] 点击“新增”按钮时，跳转的界面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelMap){

        try {
            modelMap.setViewName("/business/planmgr/scheme/mkoutline/add");
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            return modelMap;
        }
    }

    /**
     * 6.TResearchOutlineTempController [调研提纲管理 控制层] 点击“修改”按钮时，从后台拿到数据，跳转的界面
     * @param modelAndView
     * @param idX 传过来该行数据的id(主键，对应实体类的titleId字段)
     * @return
     */
    @RequestMapping(value = "edit/{idX}",method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer idX){
        try {
            //System.out.println("调研提纲管理--传入的id："+idX);
            Result<TResearchOutlineTemp> result = tResearchOutlineTempService.selectById(Integer.valueOf(idX));
            System.out.println("调研提纲管理--控制层："+result.getData());
            modelAndView.addObject("tResearchOutlineTempEdit",result.getData());
            modelAndView.setViewName("/business/planmgr/scheme/mkoutline/edit");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return modelAndView;
        }
    }

    /**
     * 7.TResearchOutlineTempController [调研提纲管理 控制层]  “增加”界面下，点击“保存”按钮，进行保存数据至数据库
     * @param outlineName 调研提纲名称
     * @param createor 调研提纲创建人
     * @return Result
     * @author 田鑫艳
     * @createTime 2021年3月5日 下午5:18
     * @updateTime 2021年3月5日 下午5:18
     */
    @RequestMapping(value = "save")
    public @ResponseBody
    Result save(String outlineName, String createor){
        try {
            TResearchOutlineTemp tPerformanceNewsMgr=new TResearchOutlineTemp();
            tPerformanceNewsMgr.setOutlineName(outlineName);
            tPerformanceNewsMgr.setCreateor(createor);
            Result<Integer> result=tResearchOutlineTempService.save(tPerformanceNewsMgr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result();
        }
    }

    /**
     * 8.TResearchOutlineTempController [调研提纲管理 控制层] “修改”界面下 点击“保存”按钮，进行保存数据至数据库
     * @param outlineName 调研提纲名称
     * @param createor 调研提纲创建人
     * @param idX 公告调研提纲主键
     * @return Result
     * @author 田鑫艳
     * @createTime 2021年3月5日 下午5:20
     * @updateTime 2021年3月5日 下午5:20
     */
    @RequestMapping(value = "update")
    public @ResponseBody
    Result update(String outlineName, String createor, Integer idX){
        try {
            TResearchOutlineTemp tPerformanceNewsMgr=new TResearchOutlineTemp();
            tPerformanceNewsMgr.setOutlineName(outlineName);
            tPerformanceNewsMgr.setCreateor(createor);
            tPerformanceNewsMgr.setIdX(idX);
            Result<Integer> result = tResearchOutlineTempService.update(tPerformanceNewsMgr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result();
        }
    }



}
