package com.common.business.collection.meanslist.service;

import com.common.business.collection.meanslist.entity.TDevelopmentInformationListTemp;
import com.common.system.sys.entity.RcMenu;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 存放项目资料清单模板信息 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
public interface TDevelopmentInformationListTempService extends IService<TDevelopmentInformationListTemp> {
	
	
	/**
	 * <p>Title: listForPage</p>  
	 * <p>Description: 分页查询</p>  
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2021年3月2日
	 * @updator 陈睿超
	 * @updatetime 2021年3月2日
	 */
	PageInfo<TDevelopmentInformationListTemp> listForPage(int pageNum, int pageSize,TDevelopmentInformationListTemp developmentInformationListTemp);

	/**
	 * 获取模板
	 * @author 陈睿超
	 * @createtime 2021年3月17日
	 * @updator 陈睿超
	 * @updatetime 2021年3月17日
	 * @return
	 */
	List<TDevelopmentInformationListTemp> getTemplate();
	
	
}
