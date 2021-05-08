package com.common.business.index.web;


import com.common.business.index.entity.TPerformanceNewsMgr;
import com.common.business.index.service.TPerformanceNewsMgrService;
import com.common.system.page.BaseController;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  TPerformanceNewsMgrController 绩效新闻管理 前端控制器
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-04
 */
@Controller
@RequestMapping("/tPerformanceNewsMgr")
public class TPerformanceNewsMgrController extends BaseController {

    @Autowired
    private TPerformanceNewsMgrService tPerformanceNewsMgrService;

    /**
     * 1.TPerformanceNewsMgrController [绩效新闻管理 控制层]
     * 点击左侧的demo时，进入初始界面，即：/business/index/noticemgr/list.ftl页面
     *
     * @param modelAndView
     * @return ModelAndView
     * @throws
     * @author: 202123522
     * @date:2021年3月2日 上午10:37:26
     * @Description: TODO
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView) {
        try {

            modelAndView.setViewName("/business/index/performancenewsmgr/list");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return modelAndView;
        }
    }


    /**
     * 2.TPerformanceNewsMgrController [绩效新闻管理 控制层] 分页查询数据
     * @param start
     * @param pageSize
     * @param
     * @return PageBean<TPerformanceNewsMgr>
     * @author:田鑫艳
     * @date:2021年3月2日 下午4:44:35
     * @Description: TODO
     * @throwsParseException
     */
    @RequestMapping("/page")
    @ResponseBody  //转换为json格式
    public PageBean<TPerformanceNewsMgr> queryForPage(
            @RequestParam(value = "start", defaultValue = "1") Integer start,
            @RequestParam(value = "length", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "subtitle", required = false) String subtitle,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "createor", required = false) String createor) {
        //1.封装成对象
        try {
            TPerformanceNewsMgr tPerformanceNewsMgr = new TPerformanceNewsMgr();

            //1-2.把string转化为date
            if (createTime != "" && createTime != null) {
                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date date = fmt.parse(createTime);
                tPerformanceNewsMgr.setCreateTime(date);
                System.out.println("绩效管理控制层--时间:" + tPerformanceNewsMgr.getCreateTime());

            }
            tPerformanceNewsMgr.setTitle(title);
            tPerformanceNewsMgr.setSubtitle(subtitle);
            tPerformanceNewsMgr.setCreateor(createor);

            //2.调用服务层的方法拿到值
            PageInfo<TPerformanceNewsMgr> pageInfo = tPerformanceNewsMgrService.queryForPage((start / pageSize) + 1, pageSize, tPerformanceNewsMgr);
            return new PageBean<TPerformanceNewsMgr>(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageBean<TPerformanceNewsMgr>();
        }

    }

    /**
     * 3.TPerformanceNewsMgrController [绩效新闻管理 控制层] 查看某一个数据的详细信息
     * @author: 202123522
     * @date:2021年3月2日 上午11:51:28
     * @Description: TODO
     * @param  idX 传过来该行数据的id(主键，对应实体类的fNId字段)
     * @param modelAndView
     * @return ModelAndView
     * @throws
     */
    @RequestMapping(value = "view/{idX}",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer idX, ModelAndView modelAndView){
        try {
            Result<TPerformanceNewsMgr> result = tPerformanceNewsMgrService.selectById(idX);
            modelAndView.addObject("tPerformanceNewsMgrView",result.getData());
            modelAndView.setViewName("/business/index/performancenewsmgr/view");//跳转的界面
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return modelAndView;
        }
    }


    /**
     * 4.TPerformanceNewsMgrController [绩效新闻管理 控制层] 根据主键id来删除一条数据
     * @author:田鑫艳
     * @date:2021年3月3日 下午2:09:38
     * @Description: TODO
     * @param idX
     * @return String
     * @throws
     */
    @RequestMapping(value = "delete/{idX}",method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable Integer idX){
        try {
            tPerformanceNewsMgrService.deleteById(idX);
            return REDIRECT+"/business/index/performancenewsmgr/list";
        } catch (Exception e) {
            e.printStackTrace();
            return REDIRECT+"/templates/system/login";
        }
    }

    /**
     * 5.TPerformanceNewsMgrController [绩效新闻管理 控制层] 点击“新增”按钮时，跳转的界面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelMap){

        try {
            modelMap.setViewName("/business/index/performancenewsmgr/add");
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            return modelMap;
        }
    }

    /**
     * 6.TPerformanceNewsMgrController [绩效新闻管理 控制层] 点击“修改”按钮时，从后台拿到数据，跳转的界面
     * @param modelAndView
     * @param idX 传过来该行数据的id(主键，对应实体类的titleId字段)
     * @return
     */
    @RequestMapping(value = "edit/{idX}",method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer idX){
        try {
            //System.out.println("公告管理控制层--传入的id："+idX);
            Result<TPerformanceNewsMgr> result = tPerformanceNewsMgrService.selectById(Integer.valueOf(idX));
            modelAndView.addObject("tPerformanceNewsMgrEdit",result.getData());
            modelAndView.setViewName("/business/index/performancenewsmgr/edit");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return modelAndView;
        }
    }

    /**
     * 7.TPerformanceNewsMgrController [绩效新闻管理 控制层] “增加”界面下，点击“保存”按钮，进行保存数据至数据库
     * @param title 公告标题
     * @param subtitle 公告副标题
     * @param noticeContent 公告内容
     * @param createor 公告创建人
     * @return Result
     * @author 田鑫艳
     * @createTime 2021/3/4 16:49
     * @updateTime 2021/3/4 16:49
     */
    @RequestMapping(value = "save")
    public @ResponseBody Result save(String title,String subtitle,String noticeContent,String createor,String isTop){
        try {
            TPerformanceNewsMgr tPerformanceNewsMgr=new 	TPerformanceNewsMgr();
            tPerformanceNewsMgr.setTitle(title);
            tPerformanceNewsMgr.setSubtitle(subtitle);
            tPerformanceNewsMgr.setIsTop(isTop);
            tPerformanceNewsMgr.setNoticeContent(noticeContent);
            tPerformanceNewsMgr.setCreateor(createor);
            Result<Integer> result=tPerformanceNewsMgrService.save(tPerformanceNewsMgr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result();
        }
    }

    /**
     * 8.TPerformanceNewsMgrController [绩效新闻管理 控制层] “修改”界面下 点击“保存”按钮，进行保存数据至数据库
     * @param title 公告标题
     * @param subtitle 公告副标题
     * @param noticeContent 公告内容
     * @param createor 公告创建人
     * @param idX 公告主键
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:55
     * @updateTime 2021/3/4 17:55
     */
    @RequestMapping(value = "update")
    public @ResponseBody Result update(String title,String subtitle,String noticeContent,String createor,Integer idX,String isTop){
        try {
            TPerformanceNewsMgr tPerformanceNewsMgr=new TPerformanceNewsMgr();
            tPerformanceNewsMgr.setTitle(title);
            tPerformanceNewsMgr.setSubtitle(subtitle);
            tPerformanceNewsMgr.setNoticeContent(noticeContent);
            tPerformanceNewsMgr.setCreateor(createor);
            tPerformanceNewsMgr.setIdX(idX);
            tPerformanceNewsMgr.setIsTop(isTop);
            Result<Integer> result = tPerformanceNewsMgrService.update(tPerformanceNewsMgr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result();
        }
    }







}