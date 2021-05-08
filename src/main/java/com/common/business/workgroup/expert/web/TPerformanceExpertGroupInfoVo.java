package com.common.business.workgroup.expert.web;

import com.common.business.library.experts.entity.TLibraryPerformanceExpert;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.util.List;

/**
 * <p>
 * 组建专家组 前端接收参数对象
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TPerformanceExpertGroupInfoVo {

    /**
     * 被删除的专家编号,用逗号分割
     */
    private String  expertMemberCodes;

    /**
     * 子项目的idA主键值
     */
    private Integer idA;

    /**
     * 现在选择的专家组中的数据
     */
    private List<TLibraryPerformanceExpert> librarySelectExperts;

    //前端传递过来的 最终保留的专家（针对第一次新的组建时的操作）
    private List<TPerformanceExpertGroupInfo> chooseList;

}
