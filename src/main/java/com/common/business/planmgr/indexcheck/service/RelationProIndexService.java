package com.common.business.planmgr.indexcheck.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.indexcheck.entity.BussinessFlowProIndex;
import com.common.business.planmgr.indexcheck.entity.RelationProIndex;
import com.common.business.planmgr.indexcheck.entity.TIndexSystemDseignCheckRec;
import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 项目指标体系关系表 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
public interface RelationProIndexService extends IService<RelationProIndex> {
    /**
     * 指标体系设计列表——所有版本
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> allRelationProIndexPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception;
    /**
     * 指标体系设计列表(待拟定的项目)
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> relationProIndexPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception;
    /**
     * 指标体系审核列表(设计了，轮到我审批的项目)
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> relationProIndexCheckPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception;


    /**
     * 根据项目idA项目信息
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @return
     * @throws Exception
     */
    TProPerformanceInfo getTProPerformanceInfoByIdA(Integer idA) throws Exception;

    /**
     * @Description: 设置审批流
     * @Author: 安达
     * @Date: 2021/3/29 20:57
     * @Param:
     * @Return:
     */
    public void saveCheckFlowUser(Integer idA,Integer idR, ShiroUser user) throws Exception;
    /**
     * @Description: 审批
     * @Author: 安达
     * @Date: 2021/3/31 10:58
     * @Param:
     * @Return:
     */
    public Boolean checkRelationProIndex(TIndexSystemDseignCheckRec indexSystemDseignCheckRec, ShiroUser user) throws Exception;

    /**
     * @Description: 转派（指标体系审批）
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    public boolean designeeCheckUser(Integer idR,String designeeId)throws Exception;
    /**
     * @Description: 审批记录信息
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    public List<TIndexSystemDseignCheckRec>  findTIndexSystemDseignCheckRecServiceListByIdR(Integer idR)throws Exception;

    /**
     * @Description: 审批流程节点信息
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    public List<BussinessFlowProIndex>  findTIndexSystemDseignCheckRecServiceNodesByIdR(Integer idR)throws Exception;

    /**
     * @Description: 保存指标体系关系
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idA  子项目id
     * @Param stauts 版本状态
     * @Param indexSystemDseignList  指标体系集合
     * @Param user 当前登陆者
     * @Return:
     */
    void saveRelationProIndex(Integer idA, String stauts, List<TIndexSystemDseign> indexSystemDseignList, ShiroUser user) throws Exception;
}
