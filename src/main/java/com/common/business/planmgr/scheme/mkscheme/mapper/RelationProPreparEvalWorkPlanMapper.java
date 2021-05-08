package com.common.business.planmgr.scheme.mkscheme.mapper;

import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.shiro.ShiroUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
public interface RelationProPreparEvalWorkPlanMapper extends BaseMapper<RelationProPreparEvalWorkPlan> {

    /**
     * @Title: 
     * @Description: 项目编制评价工作方案列表分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/8 17:22
     * @updater: 陈睿超
     * @updateDate: 2021/4/8 17:22
     * @param pageNum
     * @param pagesize
     * @param search 模糊查询
     * @param bean 精确查询
     * @param user 当前用户
     * @return 
     **/
    List<RelationProPreparEvalWorkPlan> pageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                             @Param("search") String search, @Param("bean") RelationProPreparEvalWorkPlan bean, @Param("user") ShiroUser user);
    
    /**
     * Title: 
     * Description: 
     * @author: 陈睿超
     * @createDate: 2021/4/21 17:48
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 17:48
     * @param pageNum
     * @param pagesize
     * @param search 模糊查询
     * @param bean 精确查询
     * @param user 当前用户
     * @return 
     **/
    List<RelationProPreparEvalWorkPlan> checkPagelist(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                             @Param("search") String search, @Param("bean") RelationProPreparEvalWorkPlan bean, @Param("user") ShiroUser user);
    
}