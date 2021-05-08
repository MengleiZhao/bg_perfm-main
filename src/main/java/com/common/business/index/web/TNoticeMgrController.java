package com.common.business.index.web;


import com.common.business.index.entity.TNoticeMgr;
import com.common.business.index.service.TNoticeMgrService;
import com.common.system.page.BaseController;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 通知公告管理 前端控制器  fff
 * </p>
 *	主要功能：通知公告的显示、查询、增加、修改、删除操作
 * @author 田鑫艳
 * @since 2021-03-02
 */
@Controller
@RequestMapping("/tNoticeMgr")
public class TNoticeMgrController  extends BaseController{
	

	
	@Autowired
	private TNoticeMgrService tNoticeMgrService;//服务层
	
	
	/**
	 * 1.TNoticeMgrController控制层[通知公告控制层]
	 * 点击左侧的demo时，进入初始界面，即：/business/index/noticemgr/list.ftl页面
	 * @author: 202123522
	 * @date:2021年3月2日 上午10:37:26
	 * @Description: TODO
	 * @param modelAndView
	 * @return ModelAndView 
	 * @throws
	 */
	 @RequestMapping(value = "list",method = RequestMethod.GET)
	    public ModelAndView list(ModelAndView modelAndView){
		 try {
			 modelAndView.setViewName("/business/index/noticemgr/list");
			 return modelAndView;
		 } catch (Exception e) {
			 e.printStackTrace();
			 return modelAndView;
		 }
	 }
	 
	 
	/**
	 * 2.TNoticeMgrController控制层[通知公告控制层]，分页显示数据
	 * @author:田鑫艳
	 * @date:2021年3月2日 下午4:44:35
	 * @Description: TODO
	 * @param start
	 * @param pageSize
	 * @param
	 * @return PageBean<TNoticeMgr> 
	 * @throwsParseException 
	 */
	@RequestMapping("/page")
	@ResponseBody  //转换为json格式
	public PageBean<TNoticeMgr> queryForPage(@RequestParam(value = "start",defaultValue = "1") Integer start,
			 @RequestParam(value = "length", defaultValue = "10") Integer pageSize,
			 @RequestParam(value = "title", required = false)String title,
			 @RequestParam(value = "subtitle", required = false)String subtitle,
			 @RequestParam(value = "createTime", required = false)String createTime,
			 @RequestParam(value = "createor", required = false)String createor){
		//1.封装成对象
		try {
			TNoticeMgr tNoticeMgr=new TNoticeMgr();
//			if(createTime!=null&&createTime!=""){
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				Date date=sdf.parse(createTime);
//				tNoticeMgr.setCreateTime(date);
//			}
			tNoticeMgr.setCreateTime(createTime);
			tNoticeMgr.setTitle(title);
			tNoticeMgr.setSubtitle(subtitle);
			tNoticeMgr.setCreateor(createor);

			//2.调用服务层的方法拿到值
			PageInfo<TNoticeMgr> pageInfo=tNoticeMgrService.showTNoticeMgrs((start / pageSize) + 1, pageSize,tNoticeMgr);
			return new PageBean<TNoticeMgr>(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new PageBean<TNoticeMgr>();
		}

	}
	
	/**
	 * 3.TNoticeMgrController控制层，查看某一个数据的详细信息
	 * @author: 202123522
	 * @date:2021年3月2日 上午11:51:28
	 * @Description:
	 * @param  idx 传过来该行数据的id(主键，对应实体类的fNId字段)
	 * @param modelAndView
	 * @return ModelAndView 
	 * @throws
	 */
	 @RequestMapping(value = "view/{idx}",method = RequestMethod.GET)
	    public ModelAndView view(@PathVariable Integer idx,ModelAndView modelAndView){
		 try {
			 Result<TNoticeMgr> result = tNoticeMgrService.selectById(idx);
			 modelAndView.addObject("tNoticeMgrView",result.getData());
			 modelAndView.setViewName("/business/index/noticemgr/view");//跳转的界面
			 return modelAndView;
		 } catch (Exception e) {
			 e.printStackTrace();
			 return modelAndView;
		 }
	 }
	 
	 /**
	  * 4.TNoticeMgrController控制层[通知公告控制层]，根据公告id来删除一条数据
	  * @author:田鑫艳
	  * @date:2021年3月3日 下午2:09:38
	  * @Description: TODO
	  * @param idx
	  * @return String 
	  * @throws
	  */
	@RequestMapping(value = "delete/{idx}",method = RequestMethod.GET)
	    public @ResponseBody String delete(@PathVariable Integer idx){
		try {
			tNoticeMgrService.deleteById(idx);
			return REDIRECT+"/business/index/noticemgr/list";
		} catch (Exception e) {
			e.printStackTrace();
			return REDIRECT+"/templates/system/login";
		}
	}

	/**
	 * 5.点击“新增”按钮时，跳转的界面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "add",method = RequestMethod.GET)
	public ModelAndView add(ModelAndView modelMap){

		try {
			modelMap.setViewName("/business/index/noticemgr/add");
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			return modelMap;
		}
	}

	/**
	 * 6.点击“修改”按钮时，从后台拿到数据，跳转的界面
	 * @param modelAndView
	 * @param idx 传过来该行数据的id(主键，对应实体类的titleId字段)
	 * @return
	 */
	@RequestMapping(value = "edit/{idx}",method = RequestMethod.GET)
	public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer idx){
		try {
			//System.out.println("公告管理控制层--传入的id："+idx);
			Result<TNoticeMgr> result = tNoticeMgrService.selectById(Integer.valueOf(idx));
			modelAndView.addObject("tNoticeMgrEdit",result.getData());
			modelAndView.setViewName("/business/index/noticemgr/edit");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return modelAndView;
		}
	}

	/**
	 * 7.TNoticeMgrController控制层[通知公告控制层]，“增加”界面下，点击“保存”按钮，进行保存数据至数据库
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
			TNoticeMgr tNoticeMgr=new 	TNoticeMgr();
			tNoticeMgr.setTitle(title);
			tNoticeMgr.setSubtitle(subtitle);
			tNoticeMgr.setIsTop(isTop);
			tNoticeMgr.setNoticeContent(noticeContent);
			tNoticeMgr.setCreateor(createor);
			System.out.println("通知公告管理层："+tNoticeMgr);
			Result<Integer> result=tNoticeMgrService.save(tNoticeMgr);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result();
		}
	}

	/**
	 * 8.TNoticeMgrController控制层[通知公告控制层]，“修改”界面下，点击“保存”按钮，进行保存数据至数据库
	 * @param title 公告标题
	 * @param subtitle 公告副标题
	 * @param noticeContent 公告内容
	 * @param createor 公告创建人
	 * @param idx 公告主键
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:55
	 * @updateTime 2021/3/4 17:55
	 */
	@RequestMapping(value = "update")
	public @ResponseBody Result update(String title,String subtitle,String noticeContent,String createor,Integer idx,String isTop){
		try {
			TNoticeMgr tNoticeMgr=new TNoticeMgr();
			tNoticeMgr.setTitle(title);
			tNoticeMgr.setSubtitle(subtitle);
			tNoticeMgr.setNoticeContent(noticeContent);
			tNoticeMgr.setCreateor(createor);
			tNoticeMgr.setIdx(idx);
			tNoticeMgr.setIsTop(isTop);
			Result<Integer> result = tNoticeMgrService.update(tNoticeMgr);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result();
		}
	}
}
