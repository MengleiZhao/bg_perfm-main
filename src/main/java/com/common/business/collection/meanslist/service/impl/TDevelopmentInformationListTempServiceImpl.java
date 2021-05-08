package com.common.business.collection.meanslist.service.impl;

import java.util.List;

import com.common.business.collection.meanslist.entity.TDevelopmentInformationListTemp;
import com.common.business.collection.meanslist.mapper.TDevelopmentInformationListTempMapper;
import com.common.business.collection.meanslist.service.TDevelopmentInformationListTempService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 存放项目资料清单模板信息 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Service
@Transactional
public class TDevelopmentInformationListTempServiceImpl extends ServiceImpl<TDevelopmentInformationListTempMapper, TDevelopmentInformationListTemp> implements TDevelopmentInformationListTempService {

	
	@Autowired
	private TDevelopmentInformationListTempMapper tDevelopmentInformationListTempMapper;
	
	/* 
	 * <p>Title: listForPage</p>
	 * <p>Description: </p>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @see com.common.business.collection.meanslist.service.TDevelopmentInformationListTempService#listForPage(java.lang.Integer, java.lang.Integer) 
	 * @author 陈睿超
	 * @createtime 2021年3月2日
	 * @updator 陈睿超
	 * @updatetime 2021年3月2日
	 */
	@Override
	public PageInfo<TDevelopmentInformationListTemp> listForPage(int pageNum, int pageSize,TDevelopmentInformationListTemp developmentInformationListTemp) {
		PageHelper.startPage(pageNum, pageSize);
        List<TDevelopmentInformationListTemp> list = tDevelopmentInformationListTempMapper.pageListdata(pageNum, pageSize,developmentInformationListTemp);
        PageInfo<TDevelopmentInformationListTemp> pageInfo = new PageInfo<>(list);
        return pageInfo;
		
	}

	/**
	 * 获取资料收集模板
	 * @author 陈睿超
	 * @createtime 2021年3月17日
	 * @updator 陈睿超
	 * @updatetime 2021年3月17日
	 * @return
	 */
	@Override
	public List<TDevelopmentInformationListTemp> getTemplate() {
		List<TDevelopmentInformationListTemp> list = tDevelopmentInformationListTempMapper.getTemplate();
		return list;
	}

}
