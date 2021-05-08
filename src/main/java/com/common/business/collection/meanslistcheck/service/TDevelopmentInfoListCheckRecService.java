package com.common.business.collection.meanslistcheck.service;

import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.shiro.ShiroUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
public interface TDevelopmentInfoListCheckRecService extends IService<TDevelopmentInfoListCheckRec> {

    /**
     * 审批
     * @param developmentInfoListCheckRec
     * @return
     */
    Boolean check(TDevelopmentInfoListCheckRec developmentInfoListCheckRec, ShiroUser user);
    
}
