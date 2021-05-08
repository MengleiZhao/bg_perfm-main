package com.common.business.collection.meanslistcheck.web;


import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.service.RelationProListService;
import com.common.business.collection.meanslist.service.TDevelopmentInformationListService;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;

import com.common.business.collection.meanslistcheck.service.TDevelopmentInfoListCheckRecService;

import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  资料清单审核 前端控制器
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Controller
@Api(value = "tDevelopmentInfoListCheckRec",tags = {"资料清单审核Controller"})
@RequestMapping("/tDevelopmentInfoListCheckRec")
public class TDevelopmentInfoListCheckRecController extends BaseController {


    @Autowired
    private RelationProListService relationProListService;
    @Autowired
    private TDevelopmentInformationListService developmentInformationListService;
    @Autowired
    private TDevelopmentInfoListCheckRecService developmentInfoListCheckRecService;



    /**
     * @Title:
     * @Description:资料清单审核数据
     * @author: 陈睿超
     * @createDate: 2021/3/19 20:11
     * @updater: 陈睿超
     * @updateDate: 2021/3/19 20:11
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean
     * @return
     */
    @ResponseBody
    @PostMapping(value = "checkDevelopmentPage")
    @ApiOperation(value = "资料清单审核数据")
    public Result checkDevelopmentPage(
            @RequestParam(value = "current", defaultValue = "1") int start,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, RelationProList bean){

        try {
            //当前登录人
            bean.setNextCheckId(String.valueOf(getUser().getId()));
            PageInfo<RelationProList> list = relationProListService.checkPagelist(start, pageSize,
                    search, bean);
            return getJsonResult(true,null,list);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }

    }

    /**
     * @Title:
     * @Description:
     * @author: 陈睿超
     * @createDate: 2021/3/22 17:04
     * @updater: 陈睿超
     * @updateDate: 2021/3/22 17:04
     * @param developmentInfoListCheckRec : 审批对象
     * @return
     **/
    @ResponseBody
    @ApiOperation(value = "审批")
    @PostMapping(value = "checkDevelopment")
    public Result checkDevelopment(@RequestBody TDevelopmentInfoListCheckRec developmentInfoListCheckRec){
        try {
            developmentInfoListCheckRecService.check(developmentInfoListCheckRec,getUser());
            return getJsonResult(true,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,null,null);
        }
        

    }






}
