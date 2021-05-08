package com.common.business.report.fill.service.impl;

import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.common.business.planmgr.pre.mkletter.mapper.TResearchLetterTempMapper;
import com.common.business.report.fill.entity.TProEvalReportTemp;
import com.common.business.report.fill.mapper.TProEvalReportTempMapper;
import com.common.business.report.fill.service.TProEvalReportTempService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目评价报告模板表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
@Service
public class TProEvalReportTempServiceImpl extends ServiceImpl<TProEvalReportTempMapper, TProEvalReportTemp> implements TProEvalReportTempService {
    @Autowired
    private TProEvalReportTempMapper tProEvalReportTempMapper;

    @Override
    public PageInfo<TProEvalReportTemp> listForPage(Integer pageNum, Integer pageSize, TProEvalReportTemp bean)  throws  Exception{
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        System.out.println(bean.toString());
        List<TProEvalReportTemp> list = tProEvalReportTempMapper.listForPage(bean);
        return new PageInfo<>(list);
    }
}
