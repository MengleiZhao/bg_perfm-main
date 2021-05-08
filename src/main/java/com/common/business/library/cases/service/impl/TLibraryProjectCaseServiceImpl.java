package com.common.business.library.cases.service.impl;

import com.github.pagehelper.PageHelper;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.library.cases.entity.TLibraryProjectCase;
import com.common.business.library.cases.mapper.TLibraryProjectCaseMapper;
import com.common.business.library.cases.service.TLibraryProjectCaseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-17
 */
@Service
public class TLibraryProjectCaseServiceImpl extends ServiceImpl<TLibraryProjectCaseMapper, TLibraryProjectCase> implements TLibraryProjectCaseService {


    @Override
    public PageInfo<TLibraryProjectCase> findPagelist(Integer pageNum, Integer pageSize, String search, TLibraryProjectCase bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<TLibraryProjectCase> list = baseMapper.findPageList(pageNum,pageSize,search,bean);
        PageInfo<TLibraryProjectCase> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
