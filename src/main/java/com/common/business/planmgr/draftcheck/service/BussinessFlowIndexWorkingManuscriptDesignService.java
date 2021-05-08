package com.common.business.planmgr.draftcheck.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.draftcheck.entity.BussinessFlowIndexWorkingManuscriptDesign;
import com.common.business.planmgr.draftcheck.entity.TIndexWorkingManuscriptDesignCheckRec;
import com.common.business.planmgr.indexcheck.entity.BussinessFlowProIndex;
import com.common.business.planmgr.indexcheck.entity.TIndexSystemDseignCheckRec;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.page.Result;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 业务流程表（三级指标与指标底稿审批） 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-30
 */
public interface BussinessFlowIndexWorkingManuscriptDesignService extends IService<BussinessFlowIndexWorkingManuscriptDesign> {

    /**
     * 底稿设计审核列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    public PageInfo<TProPerformanceInfo> manuscriptDesignCheckPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception;
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
    public List<TIndexWorkingManuscriptDesignCheckRec> findCheckRecServiceList(Integer idR) throws Exception;

    /**
     * @Description:  审批流程节点信息
     * @Author: 安达
     * @Date: 2021/4/1 18:14
     * @Param:
     * @Return:
     */
    public   List<BussinessFlowIndexWorkingManuscriptDesign> findCheckNodes( Integer idR) throws Exception;
    /**
     * @Description: 审批
     * @Author: 安达
     * @Date: 2021/3/31 10:58
     * @Param:
     * @Return:
     */
    public Boolean checkBussinessFlow(TIndexWorkingManuscriptDesignCheckRec checkRec, ShiroUser user) throws Exception;

    /**
     * @Description: 转派（指标体系审批）
     * @Author: 安达
     * @Date: 2021/3/31 10:56
     * @Param:
     * @Return:
     */
    public boolean designeeCheckUser(Integer idR,String designeeId)throws Exception;


}
