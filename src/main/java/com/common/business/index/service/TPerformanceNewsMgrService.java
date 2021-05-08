package com.common.business.index.service;

import com.common.business.index.entity.TPerformanceNewsMgr;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-04
 */
public interface TPerformanceNewsMgrService extends IService<TPerformanceNewsMgr> {




    /**
     * 1.TPerformanceNewsMgrService [绩效新闻管理 服务层] 分页显示数据
     * @param start 第几页
     * @param pageSize
     * @param tPerformanceNewsMgr
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 9:39
     * @updateTime 2021/3/5 9:39
     */
    PageInfo<TPerformanceNewsMgr> queryForPage(int start, Integer pageSize, TPerformanceNewsMgr tPerformanceNewsMgr) throws Exception;

    /**
     * 2.TPerformanceNewsMgrService [绩效新闻管理 服务层] 根据主键id显示详细数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 10:57
     * @updateTime 2021/3/5 10:57
     */
    Result<TPerformanceNewsMgr> selectById(Integer idX);

    /**
     * 3.TPerformanceNewsMgrService [绩效新闻管理 服务层]  根据id字段删除数据
     * @author:田鑫艳
     * @date:2021年3月3日 下午2:16:54
     * @Description: TODO
     * @param idX
     * @return int
     * @throws
     */
    int deleteById(Integer idX) throws Exception;

    /**
     * 4.TPerformanceNewsMgrService [绩效新闻管理 服务层]   新增一条数据
     * @param tPerformanceNewsMgr TPerformanceNewsMgr实体类[绩效新闻实体类]
     * @return Result<Integer>
     * @author 田鑫艳
     * @createTime 2021/3/4 17:02
     * @updateTime 2021/3/4 17:02
     */
    Result<Integer> save(TPerformanceNewsMgr tPerformanceNewsMgr) throws Exception;

    /**
     * 5.TPerformanceNewsMgrService [绩效新闻管理 服务层]  通过“绩效新闻标题”查询
     * @param title 绩效新闻标题
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:10
     * @updateTime 2021/3/4 17:10
     */
    TPerformanceNewsMgr selectByTitle(String title) throws Exception;

    /**
     * 6.TPerformanceNewsMgrService [绩效新闻管理 服务层]   通过“绩效新闻副标题”查询
     * @param subtitle 绩效新闻副标题
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:10
     * @updateTime 2021/3/4 17:10
     */
    TPerformanceNewsMgr selectBySubtitle(String subtitle) throws Exception;

    /**
     * 7.TPerformanceNewsMgrService [绩效新闻管理 服务层]  通过主键id修改数据
     * @param tPerformanceNewsMgr TPerformanceNewsMgr实体类[绩效新闻实体类]
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:46
     * @updateTime 2021/3/4 17:46
     */
    public Result<Integer> update(TPerformanceNewsMgr tPerformanceNewsMgr);


}
