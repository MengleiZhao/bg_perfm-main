package com.common.system.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.sys.entity.RcDept;
import com.common.system.sys.entity.RcDeptExample;
import com.common.system.sys.mapper.RcDeptMapper;
import com.common.system.sys.service.RcDeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Service
public class RcDeptServiceImpl extends ServiceImpl<RcDeptMapper, RcDept> implements RcDeptService {

    @Autowired
    private RcDeptMapper rcDeptMapper;
    
	@Override
	public PageInfo<RcDept> listForPage(Integer pageNum, Integer pageSize)  throws  Exception{
		if (null != pageNum && null != pageSize){
			PageHelper.startPage(pageNum,pageSize);
		}
		List<RcDept> rcDeptList =findRcDeptList(null);
		return new PageInfo<>(rcDeptList);
	}

	/** 
	 * @Description: 根据条件查询部门列表
	 * @Author: 安达
	 * @Date: 2021/3/19 21:04
	 * @Param: 
	 * @Return: 
	 */
	@Override
	public List<RcDept>  findRcDeptList(RcDept rcDept) throws  Exception{
		RcDeptExample rcDeptExample = new RcDeptExample();
		RcDeptExample.Criteria criteria = rcDeptExample.createCriteria();
		 if(StringUtils.isNotEmpty(rcDept.getFullName())){
			 criteria.andFullNameLike(rcDept.getFullName());
		 }
		if(StringUtils.isNotEmpty(rcDept.getSimpleName())){
			criteria.andSimpleNameLike(rcDept.getSimpleName());
		}
		if(rcDept.getPid() !=null){
			criteria.andPidEqualTo(rcDept.getPid());
		}
		List<RcDept> list = rcDeptMapper.selectByExample(rcDeptExample);
		return list;
	}

}
