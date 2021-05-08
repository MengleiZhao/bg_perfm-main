package com.common.business.planmgr.pre.mkquestion.mapper;

import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.shiro.ShiroUser;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <p>
 * 项目拟定调查问卷关系 
 * Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
public interface RelationProQuestionnaireMapper extends BaseMapper<RelationProQuestionnaire> {



    /**
     * @Title: pagelist
     * @Description:  问卷拟定页面分页查询
     * @author: 陈睿超
     * @createDate: 2021/3/31 20:18
     * @updater: 陈睿超
     * @updateDate: 2021/3/31 20:18
     * @param pageNum
     * @param pagesize
     * @param search 模糊查询数据
     * @param bean 精确查询
     * @param user 当前登录人
     * @return
     **/
    List<RelationProQuestionnaire> pageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                            @Param("search") String search, 
                                            @Param("bean") RelationProQuestionnaire bean,
                                            @Param("user") ShiroUser user); 
    
    /**
     * @Title: 
     * @Description: 
     * @author: 陈睿超
     * @createDate: 2021/4/7 11:15
     * @updater: 陈睿超
     * @updateDate: 2021/4/7 11:15
     * @param pageNum
     * @param pagesize
     * @param search 模糊查询数据
     * @param bean 精确查询
     * @param user 当前登录人
     * @return 
     **/
    List<RelationProQuestionnaire> checkPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                            @Param("search") String search, 
                                            @Param("bean") RelationProQuestionnaire bean,
                                            @Param("user") ShiroUser user);
    
}