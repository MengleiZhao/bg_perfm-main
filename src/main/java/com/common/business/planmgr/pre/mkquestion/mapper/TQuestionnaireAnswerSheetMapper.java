package com.common.business.planmgr.pre.mkquestion.mapper;

import com.common.business.planmgr.pre.mkquestion.entity.TQuestionnaireAnswerSheet;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.shiro.ShiroUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 答卷信息表 Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
public interface TQuestionnaireAnswerSheetMapper extends BaseMapper<TQuestionnaireAnswerSheet> {


    /**
     * 查看已答所有答卷列表
     * @author: 陈睿超
     * @createDate: 2021/4/21 10:21
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 10:21
     * @param pageNum
     * @param pagesize
     * @param search：模糊查询
     * @param questionnaireAnswerSheet：精确查询(外键idB不能为空)
     * @param user：当前登录人
     * @return
     */

    List<TQuestionnaireAnswerSheet> closeQuestionnaire(@Param("pageNum") Integer pageNum, 
                                                       @Param("pageSize") Integer pagesize, 
                                                       @Param("search") String search,
                                                       @Param("questionnaireAnswerSheet") TQuestionnaireAnswerSheet questionnaireAnswerSheet,
                                                       @Param("user") ShiroUser user);
    
    
}