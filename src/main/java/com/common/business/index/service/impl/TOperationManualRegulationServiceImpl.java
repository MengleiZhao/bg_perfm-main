package com.common.business.index.service.impl;

import com.common.business.index.entity.TOperationManualRegulation;
import com.common.business.index.mapper.TOperationManualRegulationMapper;
import com.common.business.index.service.TOperationManualRegulationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.sys.entity.RcDict;
import com.common.system.sys.mapper.RcDictMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 操作手册及业规管理 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Service
public class TOperationManualRegulationServiceImpl extends ServiceImpl<TOperationManualRegulationMapper, TOperationManualRegulation> implements TOperationManualRegulationService {
    @Autowired
    private TOperationManualRegulationMapper tOperationManualRegulationMapper;

    @Override
    public PageInfo<TOperationManualRegulation> listForPage(Integer pageNum, Integer pageSize,TOperationManualRegulation bean)  throws  Exception{
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
            System.out.println(bean.toString());
        }
        List<TOperationManualRegulation> list = tOperationManualRegulationMapper.listForPage(bean);
        return new PageInfo<>(list);
    }
}
