package com.common.business.planmgr.pre.mkletter.web;


import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.common.business.planmgr.pre.mkletter.service.TResearchLetterTempService;
import com.common.system.entity.EntityDao;
import com.common.system.page.BaseController;
import com.common.system.util.FileUpLoadUtil;
import com.common.system.page.PageBean;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 调研函模板表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
@Controller
@RequestMapping("/tResearchLetterTemp")
public class TResearchLetterTempController   extends BaseController {

        @Autowired
        private TResearchLetterTempService tResearchLetterTempService;

        /**
         *
         * 点击左侧的菜单时，进入初始界面，即：/business/planmgr/pre/mkletter/list.ftl页面
         * @author: 安达
         * @date:2021年3月2日 上午10:37:26
         * @Description: TODO
         * @param modelAndView
         * @return ModelAndView
         * @throws
         */
        @RequestMapping(value = "list",method = RequestMethod.GET)
        public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/business/planmgr/pre/mkletter/list");//跳转的界面
        return modelAndView;
    }

        /**
         * 分页查询列表
         * @author:李安达
         * @param start
         * @param pageSize
         * @param researchName
         * @param beginTime
         * @param endTime
         * @return
         */
        @RequestMapping(value = "page")
        @ResponseBody
        public PageBean<TResearchLetterTemp> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start,
        @RequestParam(value = "length", defaultValue = "10") int pageSize,
        @RequestParam(value = "researchName", required = false)String researchName,
        @RequestParam(value = "researchContent", required = false)String researchContent,
        @RequestParam(value = "beginTime", required = false)String beginTime,
        @RequestParam(value = "endTime", required = false)String endTime) {
        try {

            TResearchLetterTemp bean=new TResearchLetterTemp();
            //调研函文件名称
            bean.setResearchName(researchName);
            //调研函内容
            bean.setResearchContent(researchContent);
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
            PageInfo pageInfo = tResearchLetterTempService.listForPage((start / pageSize) + 1, pageSize,bean);
            return new PageBean<TResearchLetterTemp>(pageInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return new PageBean<TResearchLetterTemp>(new PageInfo());
        }

    }

        /**
         * 根据公告id来删除一条数据
         * @author:安达
         * @date:2021年3月3日 下午2:09:38
         * @Description: TODO
         * @param id
         * @return String
         * @throws
         */
        @RequestMapping(value = "delete/{id}",method = RequestMethod.GET)
        public @ResponseBody String delete(@PathVariable Integer id){
        try {
            TResearchLetterTemp tResearchLetterTemp =tResearchLetterTempService.selectById(id);
            if(tResearchLetterTemp !=null){
                FileUpLoadUtil.deleteFile(tResearchLetterTemp.getFilePath());
                tResearchLetterTempService.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return REDIRECT+"/business/planmgr/pre/mkletter/list";
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
            TResearchLetterTemp tResearchLetterTemp =new TResearchLetterTemp();
            modelMap.addObject("bean",tResearchLetterTemp);
            modelMap.setViewName("/business/planmgr/pre/mkletter/edit");
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
                            String serviceType,String researchContent, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<TResearchLetterTemp> list=new ArrayList<>();
        try {
            int t=files.length;
            //循环文件个数
            for (int i = 0; i < t; i++) {
                //如果文件大于0
                if (files[i].getSize() > 0) {
                    //向上转型成接口类
                    EntityDao entity=new TResearchLetterTemp();
                    //调用文件保存方法，并且返回接口类
                    entity=FileUpLoadUtil.upload( entity,serviceType,files[i],getUser());
                    //向下转型
                    TResearchLetterTemp bean=(TResearchLetterTemp)entity;
                    //调研函内容
                    bean.setResearchContent(researchContent);
                    //保存
                    tResearchLetterTempService.insert(bean);
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
