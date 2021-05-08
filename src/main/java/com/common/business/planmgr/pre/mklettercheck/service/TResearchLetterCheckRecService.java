package com.common.business.planmgr.pre.mklettercheck.service;

import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.shiro.ShiroUser;

/**
 * <p>
 * 调研函审批记录表 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
public interface TResearchLetterCheckRecService extends IService<TResearchLetterCheckRec> {


    /**
     * @Title:
     * @Description:
     * @author: 陈睿超
     * @createDate: 2021/3/26 14:06
     * @updater: 陈睿超
     * @updateDate: 2021/3/26 14:06
     * @param researchLetterCheckRec : 审批信息
     * @param user : 当前登录人
     * @return
     **/
    Boolean check(TResearchLetterCheckRec researchLetterCheckRec, ShiroUser user);


}
