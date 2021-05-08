package com.common.system.sys.web;


import com.common.system.page.BaseController;
import com.common.system.page.ComboboxJson;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.common.system.sys.entity.RcDept;
import com.common.system.sys.service.RcDeptService;
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
 * 部门管理表 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Controller
@Api(tags = {"部门接口"})
@RequestMapping("/rcDept")
public class RcDeptController extends BaseController {
	 @Autowired
	 private RcDeptService rcDeptService;
	 
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/rcDept/list");
        return modelAndView;
    }

    @ApiOperation(value="列表页")
    @ResponseBody
    @RequestMapping(value = "page")
    public PageBean<RcDept> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "length", defaultValue = "10") int pageSize, @RequestParam(value = "date", required = false) String date, @RequestParam(value = "search", required = false) String search) {
        PageInfo pageInfo = null;
        try {
            pageInfo = rcDeptService.listForPage((start / pageSize) + 1, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageBean<RcDept>(pageInfo);
    }

    @ApiOperation(value="根据id删除")
    @RequestMapping(value = "delete/{id}",method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable Integer id){
        rcDeptService.deleteById(id);
        return REDIRECT+"/system/admin/rcDept/list";
    }
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelMap){
    	RcDept rcDept =new RcDept();
    	modelMap.addObject("bean",rcDept);
        modelMap.setViewName("/system/admin/rcDept/edit");
        return modelMap;
    }
    @RequestMapping(value = "edit/{id}",method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer id){
    	RcDept rcDept = rcDeptService.selectById(id);
        modelAndView.addObject("bean",rcDept);
        modelAndView.setViewName("/system/admin/rcDept/edit");
        return modelAndView;
    }

    @ApiOperation(value="新增或者修改")
    @RequestMapping(value = "update")
    public @ResponseBody Result update(RcDept rcDept){
    	Result<Integer> result=new Result<>();
    	 boolean status = rcDeptService.insertOrUpdate(rcDept);
        result.setStatus(status);
        return result;
    }
    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer id,ModelAndView modelAndView){
    	RcDept rcDept = rcDeptService.selectById(id);
        modelAndView.addObject("bean",rcDept);
        modelAndView.setViewName("/system/admin/rcDept/view");
        return modelAndView;
    }

    @ApiOperation(value="部门下拉框")
    @GetMapping(value = "rcDeptComboJson")
    public @ResponseBody Result rcDeptComboJson(){
        try{
            RcDept rcDept=new RcDept();
            rcDept.setPid(34);
            List<RcDept> list = rcDeptService.findRcDeptList(rcDept);
            List<ComboboxJson> comboboxList= getComboboxJson(list);
            return getJsonResult(true,"查询成功",comboboxList);
        }catch (Exception e){
            e.printStackTrace();
            return getJsonResult(false,"查询失败",null);
        }

    }


}
