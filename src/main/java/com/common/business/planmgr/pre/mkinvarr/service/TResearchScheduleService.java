package com.common.business.planmgr.pre.mkinvarr.service;

import com.common.business.planmgr.pre.mkinvarr.entity.RelationProResearchSchedule;
import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.baomidou.mybatisplus.service.IService;
import com.common.business.planmgr.pre.mkinvarr.web.ResearchScheduleVo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * TResearchScheduleService  [拟定调研安排表 服务层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-25
 */
public interface TResearchScheduleService extends IService<TResearchSchedule> {

    /**
     * 1.TResearchScheduleService  [拟定调研安排表 服务层]
     * 主页面显示
     * 约束条件：当前登录人是项目主管/或者组员、明确工作任务时有“拟定调研安排” 且已经勾选、已经立项、已经创建工作组、跟拟定关系表相关联
     * @param current                  开始查询的页码数 默认为第1页
     * @param size                     每页的大小  默认每页显示10条数据
     * @param proPerformanceInfo       精确查询封装的对象
     * @param search                   综合查询的字段
     * @param userId                   当前登录用户的id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 10:28
     * @updateTime 2021/4/19 10:03
     */
    PageInfo<TProPerformanceInfo> queryForPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId,Integer preOrScheme);

    /**
     * 2.分页显示 选择要拟定调研安排的项目
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/15 14:44
     * @updateTime 2021/4/19 10:03
     */
    List<TProPerformanceInfo> chooseRecarchProject(Integer current, Integer size, String userId,TProPerformanceInfo proPerformanceInfo,String search,Integer preOrScheme);

    /**
     * 3.TResearchScheduleService  [拟定调研安排表 服务层]
     * 拟定调研安排的 组长、组员的选择
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 14:42
     * @updateTime 2021/4/19 10:09
     */
    PageInfo<TPerformanceWorkingGroup> chooseResearchPeople(Integer current, Integer size, TPerformanceWorkingGroup workingGroup, String search, ResearchScheduleVo researchVo);

    /**
     * 4.TResearchScheduleService  [拟定调研安排表 服务层]
     * 选择组员信息，跟老数据匹配返还给前端
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/16 9:55
     * @updateTime 2021/4/16 9:55
     */
    TResearchSchedule goBackMembersGroups(ResearchScheduleVo researchVo);

    /**
     * 5.TResearchScheduleService  [拟定调研安排表 服务层]
     * 拟定调研安排 提交/暂存  “暂存”为0  “提交”就是“审批中”为1
     * @param proResearchSchedule 调研安排关系表对象，每一个关系表对应多个调研安排表（集合）
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/25 16:28
     * @updateTime 2021/4/16 11:06
     */
    void tempAndSubmit(RelationProResearchSchedule proResearchSchedule, ShiroUser user);

    /**
     * 6.显示该idR中的调研安排数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 14:09
     * @updateTime 2021/4/19 14:09
     */
    List<TResearchSchedule> showResearchs(Integer idR);
}
