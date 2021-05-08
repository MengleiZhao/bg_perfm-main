package com.common.business.report.fill.web;


import com.common.business.report.fill.entity.TProEvalReportTemp;
import com.common.business.report.fill.service.TProEvalReportTempService;
import com.common.system.entity.EntityDao;
import com.common.system.page.BaseController;
import com.common.system.util.FileUpLoadUtil;
import com.common.system.page.PageBean;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 项目评价报告模板表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
@Controller
@RequestMapping("/tProEvalReportTemp")
public class TProEvalReportTempController  extends BaseController {

    @Autowired
    private TProEvalReportTempService tProEvalReportTempService;

    /**
     * 1.TProEvalReportTempController[操作手册及业规管理控制层]
     * 点击左侧的菜单时，进入初始界面，即：/business/report/fill/list.ftl页面
     * @author: 安达
     * @date:2021年3月2日 上午10:37:26
     * @Description: TODO
     * @param modelAndView
     * @return ModelAndView
     * @throws
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/business/report/fill/list");//跳转的界面
        return modelAndView;
    }

    /**
     * 分页查询列表
     * @author:李安达
     * @param start
     * @param pageSize
     * @param reportName
     * @param remark
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "page")
    @ResponseBody
    public PageBean<TProEvalReportTemp> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start,
                                                     @RequestParam(value = "length", defaultValue = "10") int pageSize,
                                                     @RequestParam(value = "reportName", required = false)String reportName,
                                                     @RequestParam(value = "remark", required = false)String remark,
                                                     @RequestParam(value = "beginTime", required = false)String beginTime,
                                                     @RequestParam(value = "endTime", required = false)String endTime) {
        try {

            TProEvalReportTemp bean=new TProEvalReportTemp();
            //评价报告文件名称
            bean.setReportName(reportName);
            //评价报告备注
            bean.setRemark(remark);
            //1-2.把string转化为date
            DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtils.isNotEmpty(beginTime)){
                Date date = fmt.parse(beginTime);

            }
            bean.setBeginTime(beginTime);
            if(StringUtils.isNotEmpty(endTime)){
                Date date = fmt.parse(endTime);

            }
            bean.setEndTime(endTime);
            PageInfo pageInfo = tProEvalReportTempService.listForPage((start / pageSize) + 1, pageSize,bean);
            return new PageBean<TProEvalReportTemp>(pageInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return new PageBean<TProEvalReportTemp>(new PageInfo());
        }

    }

    /**
     *
     * @author:安达
     * @date:2021年3月3日 下午2:09:38
     * @Description: 根据id来删除一条数据
     * @param id
     * @return String
     * @throws
     */
    @RequestMapping(value = "delete/{id}",method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable Integer id){
        try {
            TProEvalReportTemp tProEvalReportTemp =tProEvalReportTempService.selectById(id);
            if(tProEvalReportTemp !=null){
                FileUpLoadUtil.deleteFile(tProEvalReportTemp.getFilePath());
                tProEvalReportTempService.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return REDIRECT+"/business/report/fill/list";
    }

    /**
     * 点击上传按钮，跳转到上传页面
     * @author:安达
     * @date:2021年3月3日 下午2:09:38
     * @Description: TODO
     * @return String
     * @throws
     */
    @RequestMapping(value = "upload")
    public ModelAndView upload(ModelAndView modelMap){
        try {
            TProEvalReportTemp tProEvalReportTemp =new TProEvalReportTemp();
            modelMap.addObject("bean",tProEvalReportTemp);
            modelMap.setViewName("/business/report/fill/edit");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }

    /**
     * 保存数据到数据库
     * @author:安达
     * @date:2021年3月3日 下午2:09:38
     * @Description: TODO
     * @param files
     * @param serviceType
     * @return Object
     * @throws
     */
    @RequestMapping("/save")
    @ResponseBody
    public Object save( @RequestParam(value = "files", required = false) MultipartFile[] files,
                        String serviceType,String remark, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<TProEvalReportTemp> list=new ArrayList<>();
        try {
            int t=files.length;
            //循环文件个数
            for (int i = 0; i < t; i++) {
                //如果文件大于0
                if (files[i].getSize() > 0) {
                    //向上转型成接口类
                    EntityDao entity=new TProEvalReportTemp();
                    //调用文件保存方法，并且返回接口类
                    entity=FileUpLoadUtil.upload( entity,serviceType,files[i],getUser());
                    //向下转型
                    TProEvalReportTemp bean=(TProEvalReportTemp)entity;
                    //评价报告内容
                    bean.setRemark(remark);
                    //保存
                    tProEvalReportTempService.insert(bean);
                    list.add(bean);
                }
            }
            result.put("staus", true);
            result.put("msg", "上传成功");
            result.put("names", StringUtils.join(list, "</br>"));
//            }
        } catch (Exception e) {
            result.put("staus", false);
            result.put("msg", "上传失败");
            result.put("names", null);
            e.printStackTrace();
        }
        return result;
    }



}
