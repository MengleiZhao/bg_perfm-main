package com.common.business.collection.meanslist.service;

import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  资料清单拟定 服务类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
public interface TDevelopmentInformationListService extends IService<TDevelopmentInformationList> {


    /**
     * @Title:
     * @Description: 获得资料清单和相关附件
     * @author: 陈睿超
     * @createDate: 2021/3/23 20:25
     * @updater: 陈睿超
     * @updateDate: 2021/3/23 20:25
     * @param relationProList
     * @return
     */
    List<TDevelopmentInformationList> getDatalist(RelationProList relationProList);
    
}
