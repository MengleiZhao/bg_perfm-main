package com.common.business.library.regulations.service;

import com.common.business.library.regulations.entity.LibraryPolocyRegulation;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationCheckAtta;
import com.common.business.library.regulations.entity.LibraryPolocyRegulationCheckRec;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 政策法规库审批表
入库、出库、查阅 审批记录表 服务类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
public interface TLibraryPolocyRegulationCheckRecService extends IService<LibraryPolocyRegulationCheckRec> {

    /**
     * 1.政策法规审核：主页面显示
     * 约束：入库、修改、出库 已经提交，并且政法表中的当前申请人是当前登录人，且上个人的活跃状态为活跃
     * @param current                  开始查询的页码数 默认为第1页
     * @param size                     每页的大小  默认每页显示10条数据
     * @param libraryPolocyRegulation  精确查询封装字段
     * @param search                    综合查询字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 16:01
     * @updateTime 2021/3/31 16:01
     */
    PageInfo<LibraryPolocyRegulation> policyCheckPage(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search, String userId) throws Exception;

    /**
     * 2.选择一个法规进行审核 查看该法规的审批流相关数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 11:11
     * @updateTime 2021/4/2 11:11
     */
    LibraryPolocyRegulation queryFlowCheck(Integer idX)throws Exception;

    /**
     * 3.插入数据至审批附件表
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 14:06
     * @updateTime 2021/4/2 14:06
     */
    @Transactional
    void insertCheckAtta(TLibraryPolocyRegulationCheckAtta policyCheckAtta, ShiroUser user)throws Exception;

    /**
     * 4.新增数据至审批表里（有没有附件上传都需要这个操作）、修改业务表数据
     * @param
     * @return Integer 新增审批表的主键id值
     * @author 田鑫艳
     * @createTime 2021/4/2 14:37
     * @updateTime 2021/4/2 14:37
     */
    @Transactional
    Integer checkPolicy(LibraryPolocyRegulationCheckRec libraryPolocyRegulationCheckRec, ShiroUser user)throws Exception;
}
