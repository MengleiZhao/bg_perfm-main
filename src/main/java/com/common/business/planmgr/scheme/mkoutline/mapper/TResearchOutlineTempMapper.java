package com.common.business.planmgr.scheme.mkoutline.mapper;

import com.common.business.index.entity.TPerformanceNewsMgr;
import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-05
 */
public interface TResearchOutlineTempMapper extends BaseMapper<TResearchOutlineTemp> {


    /**
     * 1.TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口] 分页显示数据
     * @param tResearchOutlineTemp 分页显示数据时，要查询的字段封装成了TResearchOutlineTemp对象
     * @return List<TResearchOutlineTemp> 查询成功后，返回一条至多条数据，自动封装成TResearchOutlineTemp对象的集合
     * @author 田鑫艳
     * @createTime 2021/3/5 17:30
     * @updateTime 2021/3/5 17:30
     */
    List<TResearchOutlineTemp> queryForPage(TResearchOutlineTemp tResearchOutlineTemp);

    /**
     * 2.TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口] 根据主键id显示详细数据
     * @param idX 要查询的主键id值
     * @return TResearchOutlineTemp  查询后的数据自动封装成对象，并且返回
     * @author 田鑫艳
     * @createTime 2021/3/5 17:30
     * @updateTime 2021/3/5 17:30
     */
    TResearchOutlineTemp selectByPrimaryKey(Integer idX);

    /**
     * 3.TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口] 根据主键id删除数据
     * @author:田鑫艳
     * @createTime 2021/3/5 17:30
     * @updateTime 2021/3/5 17:30
     * @param idX 要删除数据的主键id值
     * @return int 删除成功时，会返回数据库中数据改变的行数 [删除一条数据成功时，返回值为1]
     * @throws
     */
    int deleteByIdx(Integer idX);

    /**
     * 4.TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口] 新增数据
     * @param tResearchOutlineTemp
     * @return Integer 插入成功时，会返回数据库中数据改变的行数 [插入一条数据成功时，返回值为1]
     * @author 田鑫艳
     * @createTime 2021/3/4 17:20
     * @updateTime 2021/3/4 17:20
     */
    Integer insertNewsMgr(TResearchOutlineTemp tResearchOutlineTemp);

    /**
     * 5.TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口] 通过“调研名称”查询
     * @param outlineName 要查询的“调研名称”值
     * @return TResearchOutlineTemp 查询后的数据自动封装成对象，并且返回
     * @author 田鑫艳
     * @createTime 2021/3/5 17:30
     * @updateTime 2021/3/5 17:30
     */
    TResearchOutlineTemp selectByOutlineName(String outlineName);

    /**
     * 6.TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口] 通过主键id修改数据
     * @param tPerformanceNewsMgr 要修改的数据(封装成了TPerformanceNewsMgr对象)
     * @return Integer 修改成功时，会返回数据库中数据改变的行数 [修改一条数据成功时，返回值为1]
     * @author 田鑫艳
     * @createTime 2021/3/5 17:30
     * @updateTime 2021/3/5 17:30
     */
    Integer updateByPrimaryKey(TResearchOutlineTemp tPerformanceNewsMgr);

    /**
     * 7.TResearchOutlineTempMapper [调研提纲模板表 Mapper 接口] 查询最大的序号
     * @return Integer 返回最大的序号值
     * @author 田鑫艳
     * @createTime 2021/3/5 21:07
     * @updateTime 2021/3/5 21:07
     */
    Integer selectOrderNoMax();
}