package com.common.system.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.sys.entity.RcDict;
import com.common.system.sys.mapper.RcDictMapper;
import com.common.system.sys.service.RcDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典管理表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Service
public class RcDictServiceImpl extends ServiceImpl<RcDictMapper, RcDict> implements RcDictService {

    @Autowired
    private RcDictMapper rcDictMapper;
    
	@Override
	public PageInfo<RcDict> listForPage(Integer pageNum, Integer pageSize) {
		if (null != pageNum && null != pageSize){
			PageHelper.startPage(pageNum,pageSize);
		}
		List<RcDict> rcDictList = rcDictMapper.getRcDictList();
		return new PageInfo<>(rcDictList);
	}

	/** 
	 * @Description: 根据条件查询字典列表
	 * @Author: 安达
	 * @Date: 2021/3/19 21:04
	 * @Param: 
	 * @Return: 
	 */
	@Override
	public List<RcDict>  findRcDictList(RcDict rcDict) throws  Exception{
		 EntityWrapper<RcDict> entity=new EntityWrapper<RcDict>();
		 if(StringUtils.isNotEmpty(rcDict.getType())){
			 //类型
			 entity.eq("type",rcDict.getType());
		 }
		if(StringUtils.isNotEmpty(rcDict.getParentCode())){
			//级联依赖
			entity.eq("parent_code",rcDict.getParentCode());
		}
		if(StringUtils.isNotEmpty(rcDict.getDescript())){
			//描述
			entity.eq("descript",rcDict.getDescript());
		}
		//升序
		entity.orderBy("sort",true);
		 List<RcDict> list=rcDictMapper.selectList(entity);
		 return list;
	}
	/**
	 * @Description: 根据类型查询字典列表
	 * @Author: 安达
	 * @Date: 2021/3/19 21:04
	 * @Param:
	 * @Return:
	 */
	@Override
	public List<RcDict>  findRcDictListByType(String type) throws  Exception{
		EntityWrapper<RcDict> entity=new EntityWrapper<RcDict>();
		//类型
		entity.eq("type",type);
		//升序
		entity.orderBy("sort",true);
		List<RcDict> list=rcDictMapper.selectList(entity);
		return list;
	}
}
