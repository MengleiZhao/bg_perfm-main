package com.common.system.userDemo.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.system.page.BaseController;
import com.common.system.userDemo.entity.UserDemo;
import com.common.system.userDemo.service.UserDemoService;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Controller
@RequestMapping("/userDemo")
public class UserDemoController extends BaseController{
	
	@Autowired
	private UserDemoService userDemoService;//服务层
	
	
	/**
	 * 1.点击左侧的demo时，进入初始界面，即：/system/admin/demo/list.ftl页面
	 * @author: 202123522
	 * @date:2021年3月2日 上午10:37:26
	 * @Description: TODO
	 * @param modelAndView
	 * @return ModelAndView 
	 * @throws
	 */
	 @RequestMapping(value = "list",method = RequestMethod.GET)
	    public ModelAndView list(ModelAndView modelAndView){
	        modelAndView.setViewName("/system/admin/demo/list");
	        return modelAndView;
	    }
	 
	 
	/**
	 * 2.UserDemoController控制层，分页显示UserDemo中的数据
	 * @param pageNum 第几页
	 * @param userDemo 查询的数据(是UserDemo实体类中的一些属性)
	 * @return PageInfo<UserDemo> 分页显示的数据
	 */
	@RequestMapping("/showUserDemo")
	@ResponseBody  //转换为json格式
	public PageBean<UserDemo> showUserDemo(@RequestParam(value = "start",defaultValue = "1") Integer start, @RequestParam(value = "length", defaultValue = "10") int pageSize,  UserDemo userDemo){
		
		PageInfo<UserDemo> pageInfo=userDemoService.showUserDemo((start / pageSize) + 1, pageSize,userDemo);
		 return new PageBean<UserDemo>(pageInfo);
		
	}
	
	/**
	 * 3.UserDemoController控制层，查看某一个数据的详细信息
	 * @author: 202123522
	 * @date:2021年3月2日 上午11:51:28
	 * @Description: TODO
	 * @param id 要查询的userDemo的id值
	 * @param modelAndView
	 * @return ModelAndView 
	 * @throws
	 */
	 @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
	    public ModelAndView view(@PathVariable Integer id,ModelAndView modelAndView){
	        Result<UserDemo> result = userDemoService.selectById(id);
	        modelAndView.addObject("userDemo",result.getData());
	        modelAndView.setViewName("/system/admin/demo/view");//跳转的界面
	        return modelAndView;
	    }
	 
	 @RequestMapping(value = "add/{id}",method = RequestMethod.GET)
	    public ModelAndView add(@PathVariable Integer id,ModelAndView modelAndView){
	        Result<UserDemo> result = userDemoService.selectById(id);
	        modelAndView.addObject("userDemo",result.getData());
	        modelAndView.setViewName("/system/admin/demo/view");//跳转的界面
	        return modelAndView;
	    }
	 
	 @RequestMapping(value = "edit/{id}",method = RequestMethod.GET)
	    public ModelAndView edit(@PathVariable Integer id,ModelAndView modelAndView){
	        Result<UserDemo> result = userDemoService.selectById(id);
	        modelAndView.addObject("userDemo",result.getData());
	        modelAndView.setViewName("/system/admin/demo/view");//跳转的界面
	        return modelAndView;
	    }
	 
	 @RequestMapping(value = "delete/{id}",method = RequestMethod.GET)
	    public ModelAndView delete(@PathVariable Integer id,ModelAndView modelAndView){
	        Result<UserDemo> result = userDemoService.selectById(id);
	        modelAndView.addObject("userDemo",result.getData());
	        modelAndView.setViewName("/system/admin/demo/view");//跳转的界面
	        return modelAndView;
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
