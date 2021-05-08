package com.common.system.userDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.system.sys.entity.RcRole;
import com.common.system.userDemo.entity.UserDemo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
public interface UserDemoMapper extends BaseMapper<UserDemo> {

	/**
	 * 1.
	 * @author: 202123522
	 * @date:2021年3月2日 上午9:50:15
	 * @Description: 得到查询后的结果
	 * @param userDemo
	 * @return List<UserDemo> 
	 * @throws
	 */
	List<UserDemo> showUserDemo(UserDemo userDemo);

	/**
	 * 2.通过userDemo的id来查询详细数据
	 * @author: 202123522
	 * @date:2021年3月2日 上午11:57:55
	 * @Description: TODO
	 * @param id
	 * @return RcRole 
	 * @throws
	 */
	UserDemo selectByPrimaryKey(@Param("id") Integer id);

}