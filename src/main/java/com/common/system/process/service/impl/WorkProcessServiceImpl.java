package com.common.system.process.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.service.TProPerformanceInfoService;
import com.common.system.process.entity.WorkProcess;
import com.common.system.process.service.WorkProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: WorkProcessServiceImpl
 * Description： TODO
 * Author: 陈睿超
 * Date: 2021/3/18 15:25
 * Updater: 陈睿超
 * Date: 2021/3/18 15:25
 * Company: 天职国际
 * Version:
 **/
@Service
public class WorkProcessServiceImpl implements WorkProcessService {

    @Autowired
    private TProPerformanceInfoService proPerformanceInfoService;
    
    
    @Override
    public List<WorkProcess> getProcess(Integer id) {
        TProPerformanceInfo pro = proPerformanceInfoService.selectById(id);
        
        
        return null;
    }
}
