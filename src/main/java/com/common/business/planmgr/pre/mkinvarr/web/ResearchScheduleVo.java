package com.common.business.planmgr.pre.mkinvarr.web;

import com.common.business.planmgr.pre.mkinvarr.entity.TResearchSchedule;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 拟定调研安排 前端传递的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResearchScheduleVo {

    /**
     * 前端选择的组员信息
     */
    private List<TPerformanceWorkingGroup> chooseMembers;

    /**
     * 前端的拟定调研安排表对象
     */
    private TResearchSchedule researchSchedule;
    //主子项目id值
    private Integer idA;
    //关系表主键值
    private Integer idR;

    //判断是 组长还是组员 0-组长 1-组员
    private Integer who;



}
