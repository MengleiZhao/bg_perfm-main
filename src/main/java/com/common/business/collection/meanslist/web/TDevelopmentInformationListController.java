package com.common.business.collection.meanslist.web;


import com.common.business.collection.meanslist.service.TDevelopmentInformationListService;
import com.common.system.page.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  资料清单拟定 前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/tDevelopmentInformationList")
public class TDevelopmentInformationListController extends BaseController {

    
    @Autowired
    private TDevelopmentInformationListService developmentInformationListService;
    
    

    
    
    
}
