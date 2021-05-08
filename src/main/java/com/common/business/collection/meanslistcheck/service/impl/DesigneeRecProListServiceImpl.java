package com.common.business.collection.meanslistcheck.service.impl;

import com.common.business.collection.meanslistcheck.entity.DesigneeRecProList;
import com.common.business.collection.meanslistcheck.mapper.DesigneeRecProListMapper;
import com.common.business.collection.meanslistcheck.service.DesigneeRecProListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-22
 */
@Service
@Transactional
public class DesigneeRecProListServiceImpl extends ServiceImpl<DesigneeRecProListMapper, DesigneeRecProList> implements DesigneeRecProListService {
	
}
