package com.common.business.library.regulations.service.impl;

import com.common.business.library.regulations.entity.TLibraryPolocyRegulationAtta;
import com.common.business.library.regulations.mapper.TLibraryPolocyRegulationAttaMapper;
import com.common.business.library.regulations.service.TLibraryPolocyRegulationAttaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 政策法规附件表 服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-28
 */
@Service
public class TLibraryPolocyRegulationAttaServiceImpl extends ServiceImpl<TLibraryPolocyRegulationAttaMapper, TLibraryPolocyRegulationAtta> implements TLibraryPolocyRegulationAttaService {

    @Autowired
    private TLibraryPolocyRegulationAttaMapper libraryPolocyRegulationAttaMapper;//政法附件 mapper

    /**
     * 1-查询传进来的idX集合下的附件集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 14:12
     * @updateTime 2021/4/28 14:12
     */
    @Override
    public List<TLibraryPolocyRegulationAtta> queryByIdXs(List<String> chooseIdXs) throws Exception {
        List<TLibraryPolocyRegulationAtta> attas=libraryPolocyRegulationAttaMapper.queryByIdXs(chooseIdXs);
        return attas;
    }
}
