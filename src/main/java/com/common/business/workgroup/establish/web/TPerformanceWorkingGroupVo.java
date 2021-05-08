package com.common.business.workgroup.establish.web;


import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.system.sys.entity.RcUser;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 绩效工作组 前端接收参数对象
 * </p>
 *
 * @author 安达
 * @since 2021-03-08
 */
@Data
public class TPerformanceWorkingGroupVo {
    /**
     * 是否已组建工作组  Y-保存， N-暂存
     */
    private String isSetupWorkingGroup;
    /**
     * 子项目id
     */
    private Integer  idA;
    /**
     * 委派秘书id
     */
    private Integer  proSecretaryId;
    /**
     *委派秘书名字
     */
    private String  proSecretaryName;

    /**
     * 立项项目集合， 集合里包含工作组集合
     */
    private List<TProPerformanceInfo> proPerformanceInfoList;

    /**
     * 用户集合
     */
    private List<RcUser> userList;

    /**
     * 已经选择的用户集合
     */
    private List<TPerformanceWorkingGroup> chooseList;



    /**
     *用户
     */
    private RcUser rcUser;

    /**
     * 被删除的用户编号,用逗号分割
     */
    private String  groupMemberCodes;

    //提交时的每一个子项目下的专家组信息
    private List<TProPerformanceInfo> proPerformanceInfos;

}
