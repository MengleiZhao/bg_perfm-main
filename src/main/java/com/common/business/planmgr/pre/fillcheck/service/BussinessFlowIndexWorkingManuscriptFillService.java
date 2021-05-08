package com.common.business.planmgr.pre.fillcheck.service;

import com.common.business.planmgr.pre.fillcheck.entity.BussinessFlowIndexWorkingManuscriptFill;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.pre.fillcheck.entity.TIndexWorkingManuscriptFillCheckRec;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 填写_业务流程表（三级指标与指标底稿审批） 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-04-20
 */
public interface BussinessFlowIndexWorkingManuscriptFillService extends IService<BussinessFlowIndexWorkingManuscriptFill> {
    /**
     * 底稿填写列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    public PageInfo<TProPerformanceInfo> manuscriptFillPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception;
    /**
     * 底稿填写审核列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    public PageInfo<TProPerformanceInfo> manuscriptFillCheckPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception;
    /**
     * @Description: 设置审批流
     * @Author: 安达
     * @Date: 2021/3/29 20:57
     * @Param:
     * @Return:
     */
    public void saveCheckFlowUser(Integer idA,Integer idR, ShiroUser user) throws Exception;

    /**
     * @Description:  审批记录信息
     * @Author: 安达
     * @Date: 2021/4/1 18:14
     * @Param:
     * @Return:
     */
    public List<TIndexWorkingManuscriptFillCheckRec> findCheckRecServiceList(Integer idR) throws Exception;

    /**
     * @Description:  审批流程节点信息
     * @Author: 安达
     * @Date: 2021/4/1 18:14
     * @Param:
     * @Return:
     */
    public   List<BussinessFlowIndexWorkingManuscriptFill> findCheckNodes(Integer idR) throws Exception;
    /**
     * @Description: 审批
     * @Author: 安达
     * @Date: 2021/3/31 10:58
     * @Param:
     * @Return:
     */
    public Boolean checkBussinessFlow(TIndexWorkingManuscriptFillCheckRec checkRec, ShiroUser user) throws Exception;

    /**
     * @Description: 转派（指标体系审批）
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    public boolean designeeCheckUser(Integer idR,String designeeId)throws Exception;
}
