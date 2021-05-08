package com.common.business.project.approval.service;

import com.common.business.project.approval.entity.TProjectBusinessType;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.util.tree.TreeEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-18
 */
public interface TProjectBusinessTypeService extends IService<TProjectBusinessType> {


    /**
     * 获取数据
     * @return
     */
    List<TreeEntity> getTreeEntity();
}
