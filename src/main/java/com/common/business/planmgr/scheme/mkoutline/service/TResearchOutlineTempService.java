package com.common.business.planmgr.scheme.mkoutline.service;

import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * TResearchOutlineTempService [调研提纲模管理 服务类]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-05
 */
public interface TResearchOutlineTempService extends IService<TResearchOutlineTemp> {
    /**
     * 1.TResearchOutlineTempService [调研提纲模管理 服务层] 分页显示数据
     * @param start 第几页
     * @param pageSize 每页要显示的数据值
     * @param tResearchOutlineTemp 根据某些字段(封装成了TResearchOutlineTemp对象)查询时分页显示数据
     * @return PageInfo<TResearchOutlineTemp> 查询后返回的数据
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    PageInfo<TResearchOutlineTemp> queryForPage(int start, Integer pageSize, TResearchOutlineTemp tResearchOutlineTemp) throws Exception;

    /**
     * 2.TResearchOutlineTempService [调研提纲模管理 服务层] 根据主键id显示详细数据
     * @param idX 要查询的主键id值
     * @return Result<TResearchOutlineTemp> 返回查询到的数据
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    Result<TResearchOutlineTemp> selectById(Integer idX);

    /**
     * 3.TResearchOutlineTempService [调研提纲模管理 服务层]  根据id字段删除数据
     * @author:田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     * @param idX 要删除的主键id值
     * @return int  删除成功时，会返回数据库中数据改变的行数 [删除一条数据成功时，返回值为1]
     * @throws Exception
     */
    int deleteById(Integer idX) throws Exception;

    /**
     * 4.TResearchOutlineTempService [调研提纲模管理 服务层]   新增一条数据
     * @param tResearchOutlineTemp TResearchOutlineTemp实体类[绩效新闻实体类]
     * @return Result<Integer>
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    Result<Integer> save(TResearchOutlineTemp tResearchOutlineTemp) throws Exception;

    /**
     * 5.TResearchOutlineTempService [调研提纲模管理 服务层]  通过“绩效新闻标题”查询
     * @param title 绩效新闻标题
     * @return TResearchOutlineTemp 查询后得到的数据
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    TResearchOutlineTemp selectByOutlineName(String title) throws Exception;



    /**
     * 6.TResearchOutlineTempService [调研提纲模管理 服务层] 通过主键id修改数据
     * @param tResearchOutlineTemp TResearchOutlineTemp[绩效新闻实体类]
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    public Result<Integer> update(TResearchOutlineTemp tResearchOutlineTemp) throws Exception;

    /**
     * 7.TResearchOutlineTempService [调研提纲模管理 服务层] 查询最大的序号
     * @return Integer 返回最大的序号值
     * @author 田鑫艳
     * @createTime 2021/3/5 21:07
     * @updateTime 2021/3/5 21:07
     */
    Integer selectOrderNoMax();
}
