package com.common.business.collection.meanslistcheck.service;

import com.common.business.collection.meanslistcheck.entity.BussinessFlowProList;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;

import java.util.List;

/**
 * <p>
 *  业务流程表（资料清单审批）服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-22
 */
public interface BussinessFlowProListService extends IService<BussinessFlowProList> {
	
    
    
    /**
     * @Title: 
     * @Description: 获取当前发起人流程对象
     * @author: 陈睿超
     * @createDate: 2021/3/22 15:27
     * @updater: 陈睿超
     * @updateDate: 2021/3/22 15:27
     * @param 	user : 
     * @return 
     **/
    BussinessFlowProList getapplicantUser(ShiroUser user);


    /**
     * 组装审批记录和工作流
     * @param bussinessFlowProList 流程list
     * @param developmentInfoListCheckRecs 这次的审批记录list
     * @return
     */
    List<BussinessFlowProList> assemblyBussinessFlow(List<BussinessFlowProList> bussinessFlowProList,
                                                     List<TDevelopmentInfoListCheckRec> checkHistoryList);
   
}
