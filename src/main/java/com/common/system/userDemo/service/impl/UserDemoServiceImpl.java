package com.common.system.userDemo.service.impl;

import java.util.List;

import com.common.system.userDemo.entity.UserDemo;
import com.common.system.userDemo.mapper.UserDemoMapper;
import com.common.system.userDemo.service.UserDemoService;
import com.common.system.page.MsgCode;
import com.common.system.page.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Service
public class UserDemoServiceImpl extends ServiceImpl<UserDemoMapper, UserDemo> implements UserDemoService {

	@Autowired
	private UserDemoMapper userDemoMapper;//Dao层接口
	
	/**
	 * 1.UserDemoServiceImpl 服务层实现层，分页显示UserDemo中的数据
	 */
	@Override
	public PageInfo<UserDemo> showUserDemo(Integer start,int pageSize, UserDemo userDemo) {
		PageHelper.startPage(start, pageSize);
        List<UserDemo> user = userDemoMapper.showUserDemo(userDemo);
        
        System.out.println("demo管理--显示数据服务层 从后端拿到的集合对象："+user.size());//调用接口中的方法
        
        return new PageInfo<UserDemo>(user);//将list列表 转换成PageInfo的形式并返回
	}

	/* (non-Javadoc)
	 * @see com.common.system.userDemo.service.UserDemoService#selectById(java.lang.Integer)
	 * 2.通过userDemo的id字段查询该id的详细数据
	 */
	@Override
	public Result<UserDemo> selectById(Integer id) {
		
		 Result<UserDemo> result = new Result<>();
		 UserDemo userDemo = userDemoMapper.selectByPrimaryKey(id);
		 System.out.println("demo管理--服务实现层--查看当前id的详细信息："+userDemo);
		 //如果拿到的用户详细信息为空，则进行提示
	        if (userDemo == null){
	            result.setStatus(false);
	            result.setCode(MsgCode.FAILED);
	            result.setMsg("没有该demoUser");
	            return result;
	        }
	        result.setData(userDemo);
	        result.setStatus(true);
	        result.setCode(MsgCode.SUCCESS);
	        return result ;
		
	}
	
	
}
