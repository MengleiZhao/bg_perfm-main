package com.common.business.project.approval.service.impl;


import com.common.business.project.approval.entity.TMainProjectSync;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TMainProjectSyncMapper;
import com.common.business.project.approval.service.TMainProjectSyncService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-09
 */
@Service
@Transactional
public class TMainProjectSyncServiceImpl extends ServiceImpl<TMainProjectSyncMapper, TMainProjectSync> implements TMainProjectSyncService {
	
    @Autowired
    private TMainProjectSyncMapper tMainProjectSyncMapper;


    @Override
    public PageInfo<TMainProjectSync> listForPage(Integer pageNum, Integer pageSize, TMainProjectSync bean, String search) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<TMainProjectSync> page = tMainProjectSyncMapper.selectLikePageList(pageNum, pageSize, bean, search);
        PageInfo<TMainProjectSync> pageInfo = new PageInfo<TMainProjectSync>(page);
        //pageInfo.setTotal(tMainProjectSyncMapper.countLikePage(bean, search));
        return pageInfo;
    }
    
    
    
}
