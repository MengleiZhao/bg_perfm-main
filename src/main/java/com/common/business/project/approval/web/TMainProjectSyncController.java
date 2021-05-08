package com.common.business.project.approval.web;


import com.common.business.project.approval.entity.TMainProjectSync;
import com.common.business.project.approval.service.TMainProjectSyncService;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-09
 */
@Controller
@Api(value = "tMainProjectSync",tags = {"主项目Controller"})
@RequestMapping("/tMainProjectSync")
public class TMainProjectSyncController extends BaseController {

    @Autowired
    private TMainProjectSyncService tMainProjectSyncService;

    /**
     * 立项申请查询主项目list页面分页数据
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pagelList")
    public Result pagelList(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TMainProjectSync bean){
        //设置当前人查看自己的
        bean.setProManagerId(getUser().getId().toString());
        PageInfo<TMainProjectSync> pageInfo = tMainProjectSyncService.listForPage(start, pageSize, bean, search);
        Result result = getJsonResult(true,null,pageInfo);
        return result;
    }
    
    
    /**
     * Title: 
     * Description :根据主键id查询
     * @author: 陈睿超
     * @createDate: 2021/3/11 17:08
     * @updater: 陈睿超
     * @updateDate: 2021/3/11 17:08
     * @param 		id: TMainProjectSync表的主键id
     * @return 
     **/
    @ResponseBody
    @RequestMapping(value = "mainProEdit")
    public Result edit(Integer id){
        
        return getJsonResult(true,null,tMainProjectSyncService.selectById(id));
    }
    
    
    
}
