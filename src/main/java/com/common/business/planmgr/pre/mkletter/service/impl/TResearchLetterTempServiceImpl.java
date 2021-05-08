package com.common.business.planmgr.pre.mkletter.service.impl;

import com.common.business.index.entity.TOperationManualRegulation;
import com.common.business.index.mapper.TOperationManualRegulationMapper;
import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.common.business.planmgr.pre.mkletter.mapper.TResearchLetterTempMapper;
import com.common.business.planmgr.pre.mkletter.service.TResearchLetterTempService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 调研函模板表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-06
 */
@Service
public class TResearchLetterTempServiceImpl extends ServiceImpl<TResearchLetterTempMapper, TResearchLetterTemp> implements TResearchLetterTempService {
    @Autowired
    private TResearchLetterTempMapper tResearchLetterTempMapper;

    @Override
    public PageInfo<TResearchLetterTemp> listForPage(Integer pageNum, Integer pageSize, TResearchLetterTemp bean)  throws  Exception{
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        System.out.println(bean.toString());
        List<TResearchLetterTemp> list = tResearchLetterTempMapper.listForPage(bean);
        return new PageInfo<>(list);
    }
}
