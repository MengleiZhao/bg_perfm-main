package com.common.business.index.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.index.entity.TNoticeMgr;

import java.util.List;

/**
 * <p>
  * 通知公告管理 Mapper 接口 fff
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-02
 */
public interface TNoticeMgrMapper extends BaseMapper<TNoticeMgr> {

	/**
	 * 1.TNoticeMgrMapper [通知公告管理 Mapper 接口],分页显示数据
	 * @author:田鑫艳
	 * @date:2021年3月2日 下午6:02:15
	 * @Description: TODO
	 * @param tNoticeMgr
	 * @return List<TNoticeMgr> 
	 * @throws 
	 */
	List<TNoticeMgr> showTNoticeMgrs(TNoticeMgr tNoticeMgr);

	/**
	 * 2.TNoticeMgrMapper [通知公告管理 Mapper 接口],根据id查看详细信息
	 * @author:田鑫艳
	 * @date:2021年3月2日 下午9:28:51
	 * @Description: TODO
	 * @param idx
	 * @return TNoticeMgr 
	 * @throws
	 */
	TNoticeMgr selectByPrimaryKey(Integer idx);
	
	/**
	 * 3.TNoticeMgrMapper [通知公告管理 Mapper 接口],根据id删除数据
	 * @author:田鑫艳
	 * @date:2021年3月3日 下午2:29:52
	 * @Description: TODO
	 * @param idx
	 * @return int 
	 * @throws
	 */
	int tNoticeDeleteById(Integer idx);

	/**
	 * 4.TNoticeMgrMapper [通知公告管理 Mapper 接口],新增数据
	 * @param
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:20
	 * @updateTime 2021/3/4 17:20
	 */
	Integer tNoticeInsert(TNoticeMgr tNoticeMgr);

	/**
	 * 5.TNoticeMgrMapper [通知公告管理 Mapper 接口],通过“公告标题”查询
	 * @param
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:23
	 * @updateTime 2021/3/4 17:23
	 */
	TNoticeMgr selectByTitle(String title);

	/**
	 * 6.TNoticeMgrMapper [通知公告管理 Mapper 接口],通过“公告副标题”查询
	 * @param
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:23
	 * @updateTime 2021/3/4 17:23
	 */
	TNoticeMgr selectBySubtitle(String subtitle);

	/**
	 * 7.TNoticeMgrMapper [通知公告管理 Mapper 接口],通过“公告主键”修改数据
	 * @param
	 * @return
	 * @author 田鑫艳
	 * @createTime 2021/3/4 17:34
	 * @updateTime 2021/3/4 17:34
	 */
	Integer updateByPrimaryKey(TNoticeMgr tNoticeMgr);
}