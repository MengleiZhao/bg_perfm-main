package com.common.business.project.approval.service.impl;

import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.mapper.TEvalUnitInfoMapper;
import com.common.business.project.approval.service.TEvalUnitInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
 * @since 2021-03-08
 */
@Service
@Transactional
public class TEvalUnitInfoServiceImpl extends ServiceImpl<TEvalUnitInfoMapper, TEvalUnitInfo> implements TEvalUnitInfoService {

    @Autowired
    private TEvalUnitInfoMapper tEvalUnitInfoMapper;
    
    
}
