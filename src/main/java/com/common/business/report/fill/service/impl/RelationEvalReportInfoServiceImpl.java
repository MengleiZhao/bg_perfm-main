package com.common.business.report.fill.service.impl;

import com.common.business.report.fill.entity.RelationEvalReportInfo;
import com.common.business.report.fill.mapper.RelationEvalReportInfoMapper;
import com.common.business.report.fill.service.RelationEvalReportInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 项目编写评价报告关系表 服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-22
 */
@Service
@Transactional
public class RelationEvalReportInfoServiceImpl extends ServiceImpl<RelationEvalReportInfoMapper, RelationEvalReportInfo> implements RelationEvalReportInfoService {


    /**
     * 项目编写评价报告列表分页查询方法
     * @param pageNum
     * @param pageSize
     * @param search:模糊查询
     * @param relationEvalReportInfo:精确查询
     * @param user:当前登录人
     * @return
     */
    @Override
    public PageInfo<RelationEvalReportInfo> pageList(Integer pageNum, Integer pageSize, String search, RelationEvalReportInfo relationEvalReportInfo, ShiroUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationEvalReportInfo> list = baseMapper.pageList(pageNum,pageSize,search,user,relationEvalReportInfo);
        return new PageInfo<RelationEvalReportInfo>(list);
    }
    
    
    
}
