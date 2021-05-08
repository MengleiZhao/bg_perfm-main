package com.common.system.userDemo.service;

import com.common.system.userDemo.entity.UserDemo;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
public interface UserDemoService extends IService<UserDemo> {

	/**
	 * 1.UserDemoService服务层，分页显示UserDemo中的数据
	 * @param pageNum
	 * @param pageSize 
	 * @param userDemo
	 * @return
	 */
	PageInfo<UserDemo> showUserDemo(Integer start, int pageSize, UserDemo userDemo);

	/**
	 * 
	 * @author: 202123522
	 * @date:2021年3月2日 上午11:55:15
	 * @Description: TODO
	 * @param id
	 * @return Result<RcRole> 
	 * @throws
	 */
	Result<UserDemo> selectById(Integer id);
	
}
