package com.common.business.planmgr.pre.mkquestion.service;

import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mkquestion.entity.RelationProQuestionnaire;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.pre.mkquestion.web.RelationProQuestionnaireVo;
import com.common.business.planmgr.pre.mkquestioncheck.entity.TQuestionnaireCheckRec;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  项目拟定调查问卷关系 
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-31
 */
public interface RelationProQuestionnaireService extends IService<RelationProQuestionnaire> {



    /**
     * @Title: pagelist
     * @Description:  问卷拟定页面分页查询
     * @author: 陈睿超
     * @createDate: 2021/3/31 20:18
     * @updater: 陈睿超
     * @updateDate: 2021/3/31 20:18
     * @param pageNum 
     * @param pageSize
     * @param search 模糊查询数据
     * @param bean 精确查询
     * @param user 当前登录人
     * @return 
     **/
    PageInfo<RelationProQuestionnaire> pagelist(Integer pageNum, Integer pageSize, String search, RelationProQuestionnaire bean, ShiroUser user);

    /**
     * @Title: 
     * @Description: 审批分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/7 11:05
     * @updater: 陈睿超
     * @updateDate: 2021/4/7 11:05
     * @param pageNum
     * @param pageSize
     * @param search 模糊查询数据
     * @param bean 精确查询
     * @param user 当前登录人
     * @return 
     **/
    PageInfo<RelationProQuestionnaire> checkPage(Integer pageNum, Integer pageSize, String search, RelationProQuestionnaire bean, ShiroUser user);
    
    /**
     * 调查问卷新增查询项目
     * @author: 陈睿超
     * @createDate: 2021/3/31 20:18
     * @updater: 陈睿超
     * @updateDate: 2021/3/31 20:18
     * @param pageNum
     * @param pagesize
     * @param pro
     * @param workingGroup
     * @param user
     * @return
     */
    PageInfo<TProPerformanceInfo> choosePro(Integer pageNum, Integer pagesize, Integer preOrScheme, TProPerformanceInfo pro, TPerformanceWorkingGroup workingGroup, ShiroUser user);
    
    /**
     * @Title: save
     * @Description:  保存
     * @author: 陈睿超
     * @createDate: 2021/4/1 15:43
     * @updater: 陈睿超
     * @updateDate: 2021/4/1 15:43
     * @param 	beanVo : 前台接收对象
     * @param 	user : 当前登录人
     * @return 
     **/
    Boolean saveQuestionnaire(RelationProQuestionnaireVo beanVo,ShiroUser user) throws Exception;

    /**
     * @Title: 
     * @Description:  逻辑删除
     * @author: 陈睿超
     * @createDate: 2021/4/6 11:25
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 11:25
     * @param idR : RelationProQuestionnaire的主键
     * @param user : 当前登录人
     * @return 
     **/
    Boolean deleteByIdA(Integer idR,ShiroUser user);

    /**
     * @Title: 
     * @Description: 
     * @author: 陈睿超
     * @createDate: 2021/4/6 16:38
     * @updater: 陈睿超
     * @updateDate: 2021/4/6 16:38
     * @param questionnaireCheckRec : 审批信息
     * @param user : 当前登录人
     * @return 
     **/
    Boolean check(TQuestionnaireCheckRec questionnaireCheckRec, ShiroUser user);


    
    
}
