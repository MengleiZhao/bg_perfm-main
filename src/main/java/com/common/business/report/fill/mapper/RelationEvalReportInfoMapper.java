package com.common.business.report.fill.mapper;

import com.common.business.report.fill.entity.RelationEvalReportInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 项目编写评价报告关系表 Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-22
 */
public interface RelationEvalReportInfoMapper extends BaseMapper<RelationEvalReportInfo> {

    /**
     * 项目编写评价报告列表分页查询方法
     * @param pageNum
     * @param pagesize
     * @param search:模糊查询
     * @param relationEvalReportInfo:精确查询
     * @param user:当前登录人
     * @return
     */
    List<RelationEvalReportInfo> pageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                          @Param("search") String search, @Param("search") ShiroUser user,
                                          @Param("search") RelationEvalReportInfo relationEvalReportInfo );
    
    
    
    
    
}