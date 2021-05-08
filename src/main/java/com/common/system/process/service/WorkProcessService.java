package com.common.system.process.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.system.process.entity.WorkProcess;

import java.util.List;

/**
 * Title: ProcessService
 * Description： TODO
 * Author: 陈睿超
 * Date: 2021/3/18 15:22
 * Updater: 陈睿超
 * Date: 2021/3/18 15:22
 * Company: 天职国际
 * Version:
 **/
public interface WorkProcessService {

    /**
     * 获取标准流程
     * @param id 项目主键id
     * @return
     */
    List<WorkProcess> getProcess(Integer id);
    
    
}
