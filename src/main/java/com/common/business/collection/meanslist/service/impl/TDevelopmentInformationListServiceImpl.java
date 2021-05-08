package com.common.business.collection.meanslist.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.means.mapper.TInformationsMapper;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.collection.meanslist.mapper.RelationProListMapper;
import com.common.business.collection.meanslist.mapper.TDevelopmentInformationListMapper;
import com.common.business.collection.meanslist.service.TDevelopmentInformationListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  资料清单拟定 服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Service
@Transactional
public class TDevelopmentInformationListServiceImpl extends ServiceImpl<TDevelopmentInformationListMapper, TDevelopmentInformationList> implements TDevelopmentInformationListService {

    @Autowired
    private RelationProListMapper relationProListMapper;
    @Autowired
    private TInformationsMapper informationsMapper;

    
    @Override
    public List<TDevelopmentInformationList> getDatalist(RelationProList relationProList) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",relationProList.getIdR());
        
        List<TDevelopmentInformationList> developmentInformationList = selectList(entityWrapper);
        for (int i = 0; i < developmentInformationList.size(); i++) {
            TDevelopmentInformationList developmentInformation = developmentInformationList.get(i);
            EntityWrapper inforEntityWrapper = new EntityWrapper();
            inforEntityWrapper.eq("ID_B",developmentInformation.getIdB());
            List<TInformations> infolist = informationsMapper.selectList(inforEntityWrapper);
            developmentInformationList.get(i).setInformations(infolist);
        }
        return developmentInformationList;
    }
}
