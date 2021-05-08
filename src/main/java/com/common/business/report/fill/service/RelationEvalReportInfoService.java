package com.common.business.report.fill.service;

import com.common.business.report.fill.entity.RelationEvalReportInfo;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 项目编写评价报告关系表 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-22
 */
public interface RelationEvalReportInfoService extends IService<RelationEvalReportInfo> {
	
    
    
    /**
     * Title: 
     * Description: 项目编写评价报告列表分页查询方法
     * @author: 陈睿超
     * @createDate: 2021/4/22 17:39
     * @updater: 陈睿超
     * @updateDate: 2021/4/22 17:39
     * @param pageNum
     * @param pageSize
     * @param search:模糊查询
     * @param relationEvalReportInfo:精确查询
     * @param user:当前登录人
     * @return 
     **/
    PageInfo<RelationEvalReportInfo> pageList(Integer pageNum, Integer pageSize, String search, RelationEvalReportInfo relationEvalReportInfo, ShiroUser user);
    
        
        
        
}
