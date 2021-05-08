package com.common.business.collection.meanslist.web;


import com.common.business.collection.meanslist.entity.TDevelopmentInformationListTemp;
import com.common.business.collection.meanslist.service.TDevelopmentInformationListTempService;
import com.common.system.shiro.ShiroKit;
import com.common.system.shiro.ShiroUser;
import com.common.system.page.BaseController;
import com.common.system.page.MsgCode;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 存放项目资料清单模板信息 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Controller
@RequestMapping("/tDevelopmentInformationListTemp")
public class TDevelopmentInformationListTempController extends BaseController{

	@Autowired
	private TDevelopmentInformationListTempService developmentInfoService;


	/**
	 * <p>Title: list</p>  
	 * <p>Description: 跳转到资料清单模板管理list页面</p>  
	 * @param modelAndView
	 * @return
	 * @author 陈睿超
	 * @createtime 2021年3月2日
	 * @updator 陈睿超
	 * @updatetime 2021年3月2日
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView modelAndView){
		try {
			modelAndView.setViewName("/business/collection/meanslist/developmentinfo/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
	/**
	 * <p>Title: queryByPage</p>  
	 * <p>Description: list页面查询请求后台数据</p>  
	 * @param start
	 * @param pageSize
	 * @param date
	 * @param search
	 * @param request
	 * @param bean 查询条件
	 * @return
	 * @author 陈睿超
	 * @createtime 2021年3月2日
	 * @updator 陈睿超
	 * @updatetime 2021年3月2日
	 */
    @ResponseBody
    @GetMapping(value = "page")
	public PageBean<TDevelopmentInformationListTemp> queryByPage(
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int pageSize,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request,TDevelopmentInformationListTemp bean) {
		PageInfo<TDevelopmentInformationListTemp> pageInfo = null;
		try {
			pageInfo = developmentInfoService.listForPage(start, pageSize,bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PageBean<TDevelopmentInformationListTemp>(pageInfo);
		/*Map<String, String[]> parame = request.getParameterMap();
		Page<TDevelopmentInformationListTemp> developmentpage = new Page<>();
		developmentpage.setCurrent(start);
		developmentpage.setSize(pageSize);
		Page<TDevelopmentInformationListTemp> page = developmentInfoService.selectPage(developmentpage,new EntityWrapper<TDevelopmentInformationListTemp>().where("fInfoType1",parame.get("fInfoType1")).where("fInfoType2",parame.get("fInfoType2")));*/
	}
	
    
    /**
     * <p>Title: add</p>  
     * <p>Description: 跳转到新增</p>  
     * @param modelAndView
     * @return
     * @author 陈睿超
     * @createtime 2021年3月2日
     * @updator 陈睿超
     * @updatetime 2021年3月2日
     */
    @RequestMapping(value="add",method=RequestMethod.GET)
	public ModelAndView add(ModelAndView modelAndView){
		try {
			ShiroUser user = (ShiroUser)ShiroKit.getSubject().getPrincipal();
			TDevelopmentInformationListTemp td = new TDevelopmentInformationListTemp();
			modelAndView.addObject("td", td);
			modelAndView.setViewName("/business/collection/meanslist/developmentinfo/add");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
    
    /**
     * <p>Title: save</p>  
     * <p>Description: 保存数据</p>  
     * @param developmentInformationListTemp
     * @return
     * @author 陈睿超
     * @createtime 2021年3月2日
     * @updator 陈睿超
     * @updatetime 2021年3月2日
     */
    @ResponseBody
    @RequestMapping(value="save")
    public Result<TDevelopmentInformationListTemp> save(TDevelopmentInformationListTemp developmentInformationListTemp){
		Result result = new Result<TDevelopmentInformationListTemp>();
		try {
			ShiroUser user = (ShiroUser)ShiroKit.getSubject().getPrincipal();
			if("".equals(String.valueOf(developmentInformationListTemp.getInId()))||developmentInformationListTemp.getInId()==null){
				developmentInformationListTemp.setCreateor(user.getName());

				developmentInformationListTemp.setCreateTime(new Date());
			}else{
				developmentInformationListTemp.setUpdateor(user.getName());
				developmentInformationListTemp.setUpdateTime(new Date());
			}
			result.setMsg("操作成功");
			result.setStatus(true);
			result.setCode(MsgCode.SUCCESS);
			result.setData(developmentInformationListTemp);
			developmentInfoService.insertOrUpdate(developmentInformationListTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
     * <p>Title: edit</p>  
     * <p>Description: 跳转到修改页面</p>  
     * @param modelAndView
     * @param id TDevelopmentInformationListTemp的主键id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月2日
     * @updator 陈睿超
     * @updatetime 2021年3月2日
     */
    @RequestMapping(value="edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer id){
		TDevelopmentInformationListTemp td = null;
		try {
			td = developmentInfoService.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.addObject("td", td);
		modelAndView.setViewName("/business/collection/meanslist/developmentinfo/edit");
		return modelAndView;
	}
	
    /**
     * <p>Title: view</p>  
     * <p>Description: 跳转到查看页面</p>  
     * @param modelAndView
     * @param id TDevelopmentInformationListTemp的主键id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月2日
     * @updator 陈睿超
     * @updatetime 2021年3月2日
     */
    @RequestMapping(value="view/{id}",method=RequestMethod.GET)
    public ModelAndView view(ModelAndView modelAndView,@PathVariable Integer id){
		TDevelopmentInformationListTemp td = null;
		try {
			td = developmentInfoService.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.addObject("td", td);
    	modelAndView.setViewName("/business/collection/meanslist/developmentinfo/view");
    	return modelAndView;
    }
    
    /**
     * <p>Title: delete</p>  
     * <p>Description: 根据id删除</p>  
     * @param modelAndView
     * @param id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月2日
     * @updator 陈睿超
     * @updatetime 2021年3月2日
     */
    @RequestMapping(value="delete/{id}",method=RequestMethod.GET)
    public String delete(ModelAndView modelAndView,@PathVariable Integer id){
		try {
			developmentInfoService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return REDIRECT+"/tDevelopmentInformationListTemp/list";
    }
    
}
