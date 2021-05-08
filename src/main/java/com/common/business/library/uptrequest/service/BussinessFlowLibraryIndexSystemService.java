package com.common.business.library.uptrequest.service;

import com.baomidou.mybatisplus.annotations.TableField;
import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.library.uptrequest.entity.BussinessFlowLibraryIndexSystem;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.library.uptrequest.web.libraryIndexSystemVo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
public interface BussinessFlowLibraryIndexSystemService extends IService<BussinessFlowLibraryIndexSystem> {
    /**
     * 1-申请主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/26 11:59
     * @updateTime 2021/4/26 11:59
     */
    PageInfo<TLibraryIndexSystem> applicyPage(Integer current, Integer size, TLibraryIndexSystem libraryIndexSystem, String search)throws Exception;

    /**
     * 3-绩效指标库 出库申请 提交/暂存
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 17:07
     * @updateTime 2021/4/27 17:07
     */
    @Transactional
    void outIndexSystem(TLibraryIndexSystem indexSystem, ShiroUser user)throws Exception;
}
