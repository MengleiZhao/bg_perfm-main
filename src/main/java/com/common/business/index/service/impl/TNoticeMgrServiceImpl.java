package com.common.business.index.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.index.entity.TNoticeMgr;
import com.common.business.index.mapper.TNoticeMgrMapper;
import com.common.business.index.service.TNoticeMgrService;
import com.common.system.page.MsgCode;
import com.common.system.page.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 通知公告管理 服务实现类  fff
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-02
 */
@Service
public class TNoticeMgrServiceImpl extends ServiceImpl<TNoticeMgrMapper, TNoticeMgr> implements TNoticeMgrService {

	@Autowired
	private TNoticeMgrMapper tNoticeMgrMapper;//接口
	
	/* 
	 * 1.TNoticeMgrServiceImpl 通知公告管理 服务实现类，分页显示数据
	 * 
	 */
	@Override
	public PageInfo<TNoticeMgr> showTNoticeMgrs(Integer start,
			Integer pageSize, TNoticeMgr tNoticeMgr) throws Exception{
			
			//1.拿到数据
			PageHelper.startPage(start, pageSize);
	        List<TNoticeMgr> showDatas = tNoticeMgrMapper.showTNoticeMgrs(tNoticeMgr);//接收的是tNoticeMgr类型的列表
	        System.out.println("公告管理--显示数据服务实现层 从后端拿到的集合对象："+showDatas.size());
	        return new PageInfo<TNoticeMgr>(showDatas);//将list列表 转换成PageInfo的形式并返回

	}

	/*
	 * 2.TNoticeMgrServiceImpl 通知公告管理 服务实现类，根据id字段查询详细信息
	 *
	 */
	@Override
	public Result<TNoticeMgr> selectById(Integer idx) throws Exception{
		 Result<TNoticeMgr> result = new Result<>();
		 TNoticeMgr tNoticeMgr = tNoticeMgrMapper.selectByPrimaryKey(idx);
		 System.out.println("TNoticeMgrServiceImpl管理--服务实现层--查看当前id的详细信息："+tNoticeMgr);
		 //如果拿到的详细信息为空，则进行提示
	        if (tNoticeMgr == null){
	            result.setStatus(false);
	            result.setCode(MsgCode.FAILED);
	            result.setMsg("没有该公告新闻");
	            return result;
	        }
	        result.setData(tNoticeMgr);
	        result.setStatus(true);
	        result.setCode(MsgCode.SUCCESS);
	        return result ;
		
	}

	/* 
	 * 3.TNoticeMgrServiceImpl 通知公告管理 服务实现类  根据id字段删除数据
	 */
	@Override
	public int deleteById(Integer idx) throws Exception{
		
		return tNoticeMgrMapper.tNoticeDeleteById(idx);
	}

	/**
	 * 4.TNoticeMgrServiceImpl 通知公告管理 服务实现类  新增一条数据
	 * @param tNoticeMgr TNoticeMgr实体类[通知公告实体类]
	 * @return
	 */
	@Override
	public Result<Integer> save(TNoticeMgr tNoticeMgr) throws Exception {
		Result<Integer> result=new Result<>();
		result.setStatus(false);//设置状态，默认是失败
		result.setCode(MsgCode.FAILED);//设置状态码，默认是失败,失败的状态码为MsgCode.FAILED的值，即为：1001
		if (selectByTitle(tNoticeMgr.getTitle())!= null){
			result.setMsg("公告标题已存在");
			return result;
		}
		if (selectBySubtitle(tNoticeMgr.getSubtitle())!= null){
			result.setMsg("公告副标题已存在");
			return result;
		}
		//2.判断都不为存在时才可以新增
		try {
			tNoticeMgrMapper.tNoticeInsert(tNoticeMgr);
			result.setStatus(true);
			result.setCode(MsgCode.SUCCESS);
			result.setMsg("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setCode(MsgCode.FAILED);
			result.setMsg("操作失败："+e.getLocalizedMessage());
		}
		return result;


	}
	/**
	 * 5.TNoticeMgrServiceImpl 通知公告管理 服务实现类  通过“公告标题”查询
	 * @param title 公告标题
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:12
	 * @updateTime 2021/3/4 17:12
	 */
	@Override
	public TNoticeMgr selectByTitle(String title) throws Exception{
		return tNoticeMgrMapper.selectByTitle(title);
	}

	/**
	 * 6.TNoticeMgrServiceImpl 通知公告管理 服务实现类  通过“公告副标题”查询
	 * @param subtitle 公告副标题
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:12
	 * @updateTime 2021/3/4 17:12
	 */
	@Override
	public TNoticeMgr selectBySubtitle(String subtitle) throws Exception{
		return tNoticeMgrMapper.selectBySubtitle(subtitle);
	}

	/**
	 * 7.TNoticeMgrServiceImpl 通知公告管理 服务实现类,通过主键id修改数据
	 * @param
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:47
	 * @updateTime 2021/3/4 17:47
	 */
	@Override
	public Result<Integer> update(TNoticeMgr tNoticeMgr) {
		Result<Integer> result = new Result<>();
		result.setStatus(false);
		result.setCode(MsgCode.FAILED);
		//1.判断属性值是否为空
		if (!StringUtils.hasText(tNoticeMgr.getTitle())) {
			result.setMsg("公告标题不能为空");
			return result;
		}
		if (!StringUtils.hasText(tNoticeMgr.getSubtitle())) {
			result.setMsg("公告副标题不能为空");
			return result;
		}
		if (!StringUtils.hasText(tNoticeMgr.getNoticeContent())) {
			result.setMsg("公告内容不能为空");
			return result;
		}
		if (!StringUtils.hasText(tNoticeMgr.getCreateor())) {
			result.setMsg("公告发布人不能为空");
			return result;
		}
		//2.判断都不为空时，才可以修改
		try {
			tNoticeMgrMapper.updateByPrimaryKey(tNoticeMgr);
			result.setStatus(true);
			result.setCode(MsgCode.SUCCESS);
			result.setMsg("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setCode(MsgCode.FAILED);
			result.setMsg("操作失败："+e.getLocalizedMessage());
		}
		return result;
	}

}
