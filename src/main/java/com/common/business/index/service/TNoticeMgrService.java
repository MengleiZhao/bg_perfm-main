package com.common.business.index.service;


import com.baomidou.mybatisplus.service.IService;
import com.common.business.index.entity.TNoticeMgr;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 通知公告管理 服务类  fff
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-02
 */
public interface TNoticeMgrService extends IService<TNoticeMgr> {

	/**
	 * 1.TNoticeMgrService 通知公告管理 服务层，分页显示数据
	 * @author: 田鑫艳
	 * @date:2021年3月2日 下午4:41:03
	 * @Description: TODO
	 * @param
	 * @param pageSize
	 * @param tNoticeMgr
	 * @return PageInfo<TNoticeMgr> 
	 * @throws
	 */
	PageInfo<TNoticeMgr> showTNoticeMgrs(Integer start, Integer pageSize,
										 TNoticeMgr tNoticeMgr) throws Exception;
	
	/**
	 * 2.TNoticeMgrService 通知公告管理 服务层  根据id字段查询详细信息
	 * @author:田鑫艳
	 * @date:2021年3月2日 下午9:24:12
	 * @Description: TODO
	 * @param idx
	 * @return Result<TNoticeMgr> 
	 * @throws
	 */
	Result<TNoticeMgr> selectById(Integer idx) throws Exception;
	
	
	/**
	 * 3.TNoticeMgrService 通知公告管理 服务层  根据id字段删除数据
	 * @author:田鑫艳
	 * @date:2021年3月3日 下午2:16:54
	 * @Description: TODO
	 * @param idx
	 * @return int 
	 * @throws
	 */
	int deleteById(Integer idx) throws Exception;

	/**
	 * 4.TNoticeMgrService 通知公告管理 服务层  新增一条数据
	 * @param tNoticeMgr TNoticeMgr实体类[通知公告实体类]
	 * @return Result<Integer>
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:02
	 * @updateTime 2021/3/4 17:02
	 */
	Result<Integer> save(TNoticeMgr tNoticeMgr) throws Exception;

	/**
	 * 5.TNoticeMgrService 通知公告管理 服务层  通过“公告标题”查询
	 * @param title 公告标题
	 * @return TNoticeMgr TNoticeMgr类型的实体类
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:10
	 * @updateTime 2021/3/4 17:10
	 */
	TNoticeMgr selectByTitle(String title) throws Exception;

	/**
	 * 6.TNoticeMgrService 通知公告管理 服务层  通过“公告副标题”查询
	 * @param subtitle 公告副标题
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:10
	 * @updateTime 2021/3/4 17:10
	 */
	TNoticeMgr selectBySubtitle(String subtitle) throws Exception;

	/**
	 * 7.TNoticeMgrService 通知公告管理 服务层  通过主键id修改数据
	 * @param tNoticeMgr TNoticeMgr实体类[通知公告实体类]
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:46
	 * @updateTime 2021/3/4 17:46
	 */
	public Result<Integer> update(TNoticeMgr tNoticeMgr);
}
