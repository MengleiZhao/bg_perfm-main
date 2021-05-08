package com.common.business.planmgr.pre.mkletter.service;

import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
public interface RelationProResearchLetterService extends IService<RelationProResearchLetter> {

    /**
     * 分页查询预调研函拟定列表数据
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    PageInfo<RelationProResearchLetter> pagelist(Integer pageNum, Integer pageSize, String search, RelationProResearchLetter bean);

    /**
     * 分页查询预调研函审批列表数据
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    PageInfo<RelationProResearchLetter> checkPagelist(Integer pageNum, Integer pageSize, String search, RelationProResearchLetter bean);

    /**
     * 新增调研函时查询需要预调研的项目
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    PageInfo<RelationProResearchLetter> chooseProPage(Integer pageNum, Integer pageSize, String search, RelationProResearchLetter bean);
    
    /**
     * @Title: 
     * @Description:  保存/送审
     * @author: 陈睿超
     * @createDate: 2021/3/25 10:36
     * @updater: 陈睿超
     * @updateDate: 2021/3/25 10:36
     * @param bean : 保存对象 
     * @return 
     **/
    Boolean save(RelationProResearchLetter bean, ShiroUser user);

    


}
