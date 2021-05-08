package com.common.system.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.system.sys.entity.RcDict;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 字典管理表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
public interface RcDictService extends IService<RcDict> {
	/**
	 * 
	* @author:安达
	* @Title: listForPage 
	* @Description: 分页查询
	* @param pageNum
	* @param pageSize
	* @return
	* @return PageInfo<RcDict>    返回类型 
	* @date： 2021年3月2日下午5:59:55 
	* @throws
	 */
	PageInfo<RcDict> listForPage(Integer pageNum, Integer pageSize);

	/** 
	 * @Description: 根据条件查询字典列表
	 * @Author: 安达
	 * @Date: 2021/3/19 20:49
	 * @Param: 
	 * @Return: 
	 */
	List<RcDict>  findRcDictList(RcDict rcDict) throws  Exception;

	/**
	 * @Description: 根据类型查询字典列表
	 * @Author: 安达
	 * @Date: 2021/3/19 21:04
	 * @Param:
	 * @Return:
	 */
	public List<RcDict>  findRcDictListByType(String type) throws  Exception;
}
