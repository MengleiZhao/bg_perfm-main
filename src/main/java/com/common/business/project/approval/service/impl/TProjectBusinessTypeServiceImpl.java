package com.common.business.project.approval.service.impl;

import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.common.business.project.approval.entity.TProjectBusinessType;
import com.common.business.project.approval.mapper.TProjectBusinessTypeMapper;
import com.common.business.project.approval.service.TProjectBusinessTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.util.tree.TreeEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-18
 */
@Service
@Transactional
public class TProjectBusinessTypeServiceImpl extends ServiceImpl<TProjectBusinessTypeMapper, TProjectBusinessType> implements TProjectBusinessTypeService {

    
    
    @Override
    public List<TreeEntity> getTreeEntity() {
        List<TProjectBusinessType> list = super.selectList(null);
        List<TreeEntity> treeEntityList = new ArrayList<TreeEntity>();
        for (int i = 0; i < list.size(); i++) {
            TProjectBusinessType projectBusinessType =  list.get(i);
            TreeEntity treeEntity = new TreeEntity();
            treeEntity.setId(projectBusinessType.getId());
            treeEntity.setLabel(projectBusinessType.getBussName());
            treeEntity.setParentId(Integer.valueOf(projectBusinessType.getBussparentId()));
            treeEntity.setChildList(projectBusinessType.getChildList());
            treeEntityList.add(treeEntity);
        }
        return treeEntityList;
    }
    
    
    
    
}
