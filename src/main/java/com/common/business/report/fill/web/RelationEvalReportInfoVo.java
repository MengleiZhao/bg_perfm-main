package com.common.business.report.fill.web;

import com.common.business.report.fill.entity.RelationEvalReportInfo;
import com.common.business.report.fill.entity.TEvalReportInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Title: RelationEvalReportInfoVo
 * Description： 接收前台数据对象
 * Author: 陈睿超
 * Date: 2021/4/22 16:45
 * Updater: 陈睿超
 * Date: 2021/4/22 16:45
 * Company: 天职国际
 * Version:
 **/
@Data
@Accessors(chain = true)
public class RelationEvalReportInfoVo {

    /**
     * 项目编写评价报告关系
     */
    private RelationEvalReportInfo relationEvalReportInfo;
    /**
     * 编写评价报
     */
    private List<TEvalReportInfo> evalReportInfoList;
    
    
}
