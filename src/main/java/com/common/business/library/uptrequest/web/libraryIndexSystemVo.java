package com.common.business.library.uptrequest.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class libraryIndexSystemVo {

    /**
     * 调整类型:
     * 1-出库申请
     * 2-入库申请
     * 3-修改申请
     */
    private String uptType;
    /**
     * 指标库 主键值
     */
    private Integer idX;
    /**
     * 数据状态
     * -1 退回
     * 0-暂存
     * 1-审批中
     * 2-已审批
     */
    private String dataStauts;

    /**
     * 申请原因
     */
    private String applyDesc;



}
