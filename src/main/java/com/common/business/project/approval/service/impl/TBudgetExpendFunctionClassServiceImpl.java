package com.common.business.project.approval.service.impl;

import com.github.pagehelper.PageHelper;
import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.common.business.project.approval.mapper.TBudgetExpendFunctionClassMapper;
import com.common.business.project.approval.service.TBudgetExpendFunctionClassService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.util.tree.TreeEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 存放绩效项目预算支出功能分类信息 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Service
@Transactional
public class TBudgetExpendFunctionClassServiceImpl extends ServiceImpl<TBudgetExpendFunctionClassMapper, TBudgetExpendFunctionClass> implements TBudgetExpendFunctionClassService {

    @Autowired
    private TBudgetExpendFunctionClassMapper tBudgetExpendFunctionClassMapper;

    /**
     * 查询list分页数据
     * @param pageNum
     * @param pageSize
     * @param tBudgetExpendFunctionClass
     * @return PageInfo<TBudgetExpendFunctionClass>
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @Override
    public PageInfo<TBudgetExpendFunctionClass> listforpage(Integer pageNum, Integer pageSize,TBudgetExpendFunctionClass tBudgetExpendFunctionClass) {
        PageHelper.startPage(pageNum,pageSize);
        TBudgetExpendFunctionClass bean = new TBudgetExpendFunctionClass();
        //分页查询sql
        List<TBudgetExpendFunctionClass> pagelist = tBudgetExpendFunctionClassMapper.selectPageList(pageNum, pageSize, bean);
        PageInfo<TBudgetExpendFunctionClass> pageinfo = new PageInfo<TBudgetExpendFunctionClass>(pagelist);
        return pageinfo;


    }

    @Override
    public List<TBudgetExpendFunctionClass> getBudgetExpend() {
        List<TBudgetExpendFunctionClass> lsit = tBudgetExpendFunctionClassMapper.selectBudgetExpendList(null);
        return lsit;
    }

    @Override
    public List<TBudgetExpendFunctionClass> getBudgetExpendLeftJion() {
        List<TBudgetExpendFunctionClass> lsit = tBudgetExpendFunctionClassMapper.selectBudgetExpendListLeftJion(null);
        return lsit;
    }

    @Override
    public List<TreeEntity> getTreeEntity() {
        List<TBudgetExpendFunctionClass> list = tBudgetExpendFunctionClassMapper.selectList(null);
        List<TreeEntity> treeEntityList = new ArrayList<TreeEntity>();
        for (int i = 0; i < list.size(); i++) {
            TBudgetExpendFunctionClass tBudgetExpendFunctionClass =  list.get(i);
            TreeEntity treeEntity = new TreeEntity();
            treeEntity.setId(tBudgetExpendFunctionClass.getId());
            treeEntity.setLabel(tBudgetExpendFunctionClass.getFuncName());
            treeEntity.setParentId(tBudgetExpendFunctionClass.getParentId());
            treeEntity.setChildList(tBudgetExpendFunctionClass.getChildList());
            treeEntityList.add(treeEntity);
        }
        return treeEntityList;
    }
}
