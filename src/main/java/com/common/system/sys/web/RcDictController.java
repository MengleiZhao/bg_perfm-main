package com.common.system.sys.web;


import com.common.system.page.BaseController;
import com.common.system.page.ComboboxJson;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.common.system.sys.entity.RcDict;
import com.common.system.sys.service.RcDictService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 字典管理表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Controller
@Api(tags = {"数据字典接口"})
@RequestMapping("/rcDict")
public class RcDictController  extends BaseController {
	 @Autowired
	 private RcDictService rcDictService;
	 
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/rcDict/list");
        return modelAndView;
    }

    @ApiOperation(value="列表页")
    @ResponseBody
    @RequestMapping(value = "page")
    public PageBean<RcDict> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "length", defaultValue = "10") int pageSize, @RequestParam(value = "date", required = false) String date, @RequestParam(value = "search", required = false) String search) {
    	 PageInfo pageInfo = rcDictService.listForPage((start / pageSize) + 1, pageSize);
         return new PageBean<RcDict>(pageInfo);
    }

    @ApiOperation(value="根据id删除")
    @RequestMapping(value = "delete/{id}",method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable Integer id){
        rcDictService.deleteById(id);
        return REDIRECT+"/system/admin/rcDict/list";
    }
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelMap){
    	RcDict rcDict =new RcDict();
    	modelMap.addObject("bean",rcDict);
        modelMap.setViewName("/system/admin/rcDict/edit");
        return modelMap;
    }
    @RequestMapping(value = "edit/{id}",method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer id){
    	RcDict rcDict = rcDictService.selectById(id);
        modelAndView.addObject("bean",rcDict);
        modelAndView.setViewName("/system/admin/rcDict/edit");
        return modelAndView;
    }

    @ApiOperation(value="新增或者修改")
    @RequestMapping(value = "update")
    public @ResponseBody Result update(RcDict rcDict){
    	Result<Integer> result=new Result<>();
    	rcDict.setCreateTime(new Date());
    	 boolean status = rcDictService.insertOrUpdate(rcDict);
        result.setStatus(status);
        return result;
    }
    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer id,ModelAndView modelAndView){
    	RcDict rcDict = rcDictService.selectById(id);
        modelAndView.addObject("bean",rcDict);
        modelAndView.setViewName("/system/admin/rcDict/view");
        return modelAndView;
    }

    @ApiOperation(value="字典下拉框")
    @RequestMapping(value = "rcDictComboJson")
    public @ResponseBody Result rcDictComboJson(RcDict rcDict){
        try{
            Result result=new Result();
            List<RcDict> list = rcDictService.findRcDictList(rcDict);
            List<ComboboxJson> comboboxList= getComboboxJson(list);
            return getJsonResult(true,"查询成功",comboboxList);
        }catch (Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败",null);
        }

    }


}
