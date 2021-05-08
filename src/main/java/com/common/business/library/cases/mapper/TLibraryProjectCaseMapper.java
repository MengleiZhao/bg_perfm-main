package com.common.business.library.cases.mapper;

import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.library.cases.entity.TLibraryProjectCase;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-17
 */
public interface TLibraryProjectCaseMapper extends BaseMapper<TLibraryProjectCase> {


    List<TLibraryProjectCase> findPageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                            @Param("search") String search, @Param("bean") TLibraryProjectCase bean);
    
}