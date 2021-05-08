package com.common.business.project.approval.service.impl;


import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.common.business.project.approval.mapper.TClassifcationOfNationalEconmyMapper;
import com.common.business.project.approval.mapper.TClassifcationOfNationalEconmyMapper;
import com.common.business.project.approval.service.TClassifcationOfNationalEconmyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.util.tree.TreeEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 存放绩效项目国民经济分类信息 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Service
@Transactional
public class TClassifcationOfNationalEconmyServiceImpl extends ServiceImpl<TClassifcationOfNationalEconmyMapper, TClassifcationOfNationalEconmy> implements TClassifcationOfNationalEconmyService {


    @Autowired
    private TClassifcationOfNationalEconmyMapper tClassifcationOfNationalEconmyMapper;

    /**
     * 查询list分页数据
     * @param pageNum
     * @param pageSize
     * @param tBudgetExpendFunctionClass
     * @return PageInfo<TClassifcationOfNationalEconmy>
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @Override
    public PageInfo<TClassifcationOfNationalEconmy> listforpage(Integer pageNum, Integer pageSize, TClassifcationOfNationalEconmy tBudgetExpendFunctionClass) {
        PageHelper.startPage(pageNum,pageSize);
        TClassifcationOfNationalEconmy bean = new TClassifcationOfNationalEconmy();
        //分页查询sql
        List<TClassifcationOfNationalEconmy> pagelist = tClassifcationOfNationalEconmyMapper.selectPageList(pageNum, pageSize, bean);
        PageInfo<TClassifcationOfNationalEconmy> pageinfo = new PageInfo<TClassifcationOfNationalEconmy>(pagelist);
        return pageinfo;


    }

    @Override
    public List<TClassifcationOfNationalEconmy> getbudgetExpendFunctionClass() {
        List<TClassifcationOfNationalEconmy> lsit = tClassifcationOfNationalEconmyMapper.selectbudgetClassList(null);
        return lsit;
    }

    @Override
    public List<TClassifcationOfNationalEconmy> getbudgetExpendFunctionClassLeftJion() {
        List<TClassifcationOfNationalEconmy> lsit = tClassifcationOfNationalEconmyMapper.selectbudgetClassListLeftJion(null);
        return lsit;
    }



    @Override
    public List<TreeEntity> getTreeEntity() {
        List<TClassifcationOfNationalEconmy> list = tClassifcationOfNationalEconmyMapper.selectList(null);
        List<TreeEntity> treeEntityList = new ArrayList<TreeEntity>();
        for (int i = 0; i < list.size(); i++) {
            TClassifcationOfNationalEconmy tClassifcationOfNationalEconmy =  list.get(i);
            TreeEntity treeEntity = new TreeEntity();
            treeEntity.setId(tClassifcationOfNationalEconmy.getId());
            treeEntity.setLabel(tClassifcationOfNationalEconmy.getClassName());
            treeEntity.setParentId(tClassifcationOfNationalEconmy.getParentId());
            treeEntity.setChildList(tClassifcationOfNationalEconmy.getChildList());
            treeEntityList.add(treeEntity);
        }
        return treeEntityList;
    }
    
}
