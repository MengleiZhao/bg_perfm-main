package com.common.business.planmgr.pre.mkoutline.service;

import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.pre.mkoutline.entity.TResearchOutline;
import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 项目调研提纲关系表  服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-21
 */
public interface RelationProResearchOutlineService extends IService<RelationProResearchOutline> {

    /**
     * 1.拟定调研提纲主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/21 21:06
     * @updateTime 2021/4/21 21:06
     */
    PageInfo<TProPerformanceInfo> queryForPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId, Integer preOrScheme)throws Exception;

    /**
     * 2.分页显示 选择要拟定调研提纲的项目
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 9:13
     * @updateTime 2021/4/22 9:13
     */
    List<TProPerformanceInfo> chooseOutlineProject(Integer current, Integer size, String userId, TProPerformanceInfo proPerformanceInfo, String search, Integer preOrScheme)throws Exception;

    /**
     * 3.显示该idR下的原来的拟定表数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 9:52
     * @updateTime 2021/4/22 9:52
     */
    List<TResearchOutline> showOutlines(Integer idR)throws Exception;

    /**
     * 4-显示初始调研提纲数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 10:16
     * @updateTime 2021/4/22 10:16
     */
    List<TResearchOutlineTemp> initialOutlines()throws Exception;

    /**
     * 5-暂存/提交
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/22 10:50
     * @updateTime 2021/4/22 10:50
     */
    void tempAndSubmit(RelationProResearchOutline proResearchOutline, ShiroUser user)throws Exception;


}
