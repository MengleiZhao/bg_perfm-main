package com.common.system.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.system.sys.entity.RcDept;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
public interface RcDeptService extends IService<RcDept> {
	/**
	 * 
	* @author:安达
	* @Title: listForPage 
	* @Description: 分页查询
	* @param pageNum
	* @param pageSize
	* @return
	* @return PageInfo<RcDept>    返回类型 
	* @date： 2021年3月2日下午5:59:55 
	* @throws
	 */
	PageInfo<RcDept> listForPage(Integer pageNum, Integer pageSize) throws  Exception;

	/** 
	 * @Description: 根据条件查询部门列表
	 * @Author: 安达
	 * @Date: 2021/3/19 20:49
	 * @Param: 
	 * @Return: 
	 */
	List<RcDept>  findRcDeptList(RcDept rcDict) throws  Exception;

}
