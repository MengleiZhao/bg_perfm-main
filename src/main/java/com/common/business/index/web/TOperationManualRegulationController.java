package com.common.business.index.web;


import com.common.business.index.entity.TOperationManualRegulation;
import com.common.business.index.service.TOperationManualRegulationService;
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
 * 操作手册及业规管理 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Controller
@RequestMapping("/tOperationManualRegulation")
public class TOperationManualRegulationController   extends BaseController {
    @Autowired
    private TOperationManualRegulationService tOperationManualRegulationService;

    /**
     * 1.TOperationManualRegulationController[操作手册及业规管理控制层]
     * 点击左侧的菜单时，进入初始界面，即：/business/index/toperationmanualregulation/list.ftl页面
     * @author: 安达
     * @date:2021年3月2日 上午10:37:26
     * @Description: TODO
     * @param modelAndView
     * @return ModelAndView
     * @throws
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/business/index/toperationmanualregulation/list");//跳转的界面
        return modelAndView;
    }

    /**
     * 分页查询列表
     * @author:李安达
     * @param start
     * @param pageSize
     * @param fileName
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "page")
    @ResponseBody
    public PageBean<TOperationManualRegulation> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start,
                                                             @RequestParam(value = "length", defaultValue = "10") int pageSize,
                                                             @RequestParam(value = "fileName", required = false)String fileName,
                                                             @RequestParam(value = "beginTime", required = false)String beginTime,
                                                             @RequestParam(value = "endTime", required = false)String endTime) {
        try {

            TOperationManualRegulation bean=new TOperationManualRegulation();
            bean.setFileName(fileName);
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
            PageInfo pageInfo = tOperationManualRegulationService.listForPage((start / pageSize) + 1, pageSize,bean);
           return new PageBean<TOperationManualRegulation>(pageInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return new PageBean<TOperationManualRegulation>(new PageInfo());
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
            TOperationManualRegulation tOperationManualRegulation =tOperationManualRegulationService.selectById(id);
            if(tOperationManualRegulation !=null){
                FileUpLoadUtil.deleteFile(tOperationManualRegulation.getFilePath());
                tOperationManualRegulationService.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return REDIRECT+"/business/index/toperationmanualregulation/list";
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
            TOperationManualRegulation tOperationManualRegulation =new TOperationManualRegulation();
            modelMap.addObject("bean",tOperationManualRegulation);
            modelMap.setViewName("/business/index/toperationmanualregulation/edit");
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
    public Object save( @RequestParam(value = "files", required = false) MultipartFile[] files, String serviceType, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<TOperationManualRegulation> list=new ArrayList<>();
        try {
            int t=files.length;
            //循环文件个数
            for (int i = 0; i < t; i++) {
                //如果文件大于0
                if (files[i].getSize() > 0) {
                    //向上转型成接口类
                    EntityDao entity=new TOperationManualRegulation();
                    //调用文件保存方法，并且返回接口类
                    entity=FileUpLoadUtil.upload( entity,serviceType,files[i],getUser());
                    //向下转型
                    TOperationManualRegulation bean=(TOperationManualRegulation)entity;
                    //保存
                    tOperationManualRegulationService.insert(bean);
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
