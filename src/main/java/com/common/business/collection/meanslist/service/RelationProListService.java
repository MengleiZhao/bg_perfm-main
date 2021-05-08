package com.common.business.collection.meanslist.service;

import com.common.business.collection.meanslist.entity.RelationProList;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
public interface RelationProListService extends IService<RelationProList> {

    /**
     * @Title:
     * @Description:项目资料清单关系表,list页面查询项目
     * @author: 陈睿超
     * @createDate: 2021/3/17 14:40
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 14:40
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     **/
    PageInfo<RelationProList> pagelist(Integer pageNum, Integer pageSize,String search,
                                       RelationProList bean);

    /**
     * 查询审批数据
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    PageInfo<RelationProList> checkPagelist(Integer pageNum, Integer pageSize,String search,
                                       RelationProList bean);

    /**
     * 查询台账页面
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    PageInfo<RelationProList> ledgerPagelist(Integer pageNum, Integer pageSize,String search,
                                       RelationProList bean);

    /**
     * 导出资料台账
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    Workbook expertledgerPagelist(Integer pageNum, Integer pageSize, String search,
                                  RelationProList bean);
    /**
     * @Title: 
     * @Description:项目资料清单关系表,点击新增查询项目
     * @author: 陈睿超
     * @createDate: 2021/3/17 14:40
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 14:40
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return 
     **/
    PageInfo<TProPerformanceInfo> selectProPage(Integer pageNum, Integer pageSize,String search,
                                       TProPerformanceInfo bean);
    
    /**
     * @Title: 
     * @Description: 
     * @author: 陈睿超
     * @createDate: 2021/3/18 11:40
     * @updater: 陈睿超
     * @updateDate: 2021/3/18 11:40
     * @param bean : 接收对象
     * @param user : 当前登录用户
     * @return 
     **/
    Boolean save(RelationProList bean, ShiroUser user);
    
    /**
     * @Title: 
     * @Description: 逻辑删除单据和审批记录
     * @author: 陈睿超
     * @createDate: 2021/3/23 11:15
     * @updater: 陈睿超
     * @updateDate: 2021/3/23 11:15
     * @param bean : 需要被删除的对象
     * @return 
     **/
    Boolean deleteRelationPro(RelationProList bean );
    
}
