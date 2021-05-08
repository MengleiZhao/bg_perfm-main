package com.common.business.index.mapper;

import com.common.business.index.entity.TNoticeMgr;
import com.common.business.index.entity.TPerformanceNewsMgr;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  *  TPerformanceNewsMgrMapper   绩效新闻管理  Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-04
 */
public interface TPerformanceNewsMgrMapper extends BaseMapper<TPerformanceNewsMgr> {

    /**
     * 1.TPerformanceNewsMgrMapper  [绩效新闻管理  Mapper 接口] 分页显示数据
     * @param tPerformanceNewsMgr
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 10:16
     * @updateTime 2021/3/5 10:16
     */
    List<TPerformanceNewsMgr> queryForPage(TPerformanceNewsMgr tPerformanceNewsMgr);

    /**
     * 3.TPerformanceNewsMgrMapper  [绩效新闻管理  Mapper 接口] 根据主键id显示详细数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 10:57
     * @updateTime 2021/3/5 10:57
     */
    TPerformanceNewsMgr selectByPrimaryKey(Integer idX);

    /**
     * 3.TPerformanceNewsMgrMapper  [绩效新闻管理  Mapper 接口]  根据主键id删除数据
     * @author:田鑫艳
     * @date:2021年3月3日 下午2:29:52
     * @Description: TODO
     * @param idX
     * @return int
     * @throws
     */
    int tPerformanceNewsDeleteById(Integer idX);

    /**
     * 4.TPerformanceNewsMgrMapper  [绩效新闻管理  Mapper 接口],新增数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:20
     * @updateTime 2021/3/4 17:20
     */
    Integer tPerformanceNewsInsert(TPerformanceNewsMgr tPerformanceNewsMgr);

    /**
     * 5.TPerformanceNewsMgrMapper  [绩效新闻管理  Mapper 接口],通过“公告标题”查询
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:23
     * @updateTime 2021/3/4 17:23
     */
    TPerformanceNewsMgr selectByTitle(String title);

    /**
     * 6.TPerformanceNewsMgrMapper  [绩效新闻管理  Mapper 接口],通过“公告副标题”查询
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:23
     * @updateTime 2021/3/4 17:23
     */
    TPerformanceNewsMgr selectBySubtitle(String subtitle);

    /**
     * 7.TPerformanceNewsMgrMapper  [绩效新闻管理  Mapper 接口],通过“公告主键”修改数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:34
     * @updateTime 2021/3/4 17:34
     */
    Integer updateByPrimaryKey(TPerformanceNewsMgr tPerformanceNewsMgr);
}