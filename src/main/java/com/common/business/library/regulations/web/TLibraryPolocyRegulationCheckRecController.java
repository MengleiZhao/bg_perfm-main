package com.common.business.library.regulations.web;


import com.common.business.library.regulations.entity.LibraryPolocyRegulation;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationCheckAtta;
import com.common.business.library.regulations.entity.LibraryPolocyRegulationCheckRec;
import com.common.business.library.regulations.service.TLibraryPolocyRegulationCheckRecService;
import com.common.system.entity.EntityDao;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.util.FileUpLoadUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 政策法规库审批表
    入库、出库、修改 审批记录表 前端控制器
 * </p>
 * 包含接口：
 *      1-主页面现示
 *      2-审批流界面
 *      3-审批
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
@RestController
@Api(value = "/tLibraryPolocyRegulationCheckRec", tags = {"政策法规库审批 接口"})
@RequestMapping("/tLibraryPolocyRegulationCheckRec")
public class TLibraryPolocyRegulationCheckRecController extends BaseController {

    @Autowired
    private TLibraryPolocyRegulationCheckRecService libraryPolocyRegulationCheckRecService;//政策法规库森普 服务处



    /**
     * ok
     * 1.TLibraryPolocyRegulationCheckRecController 【政策法规审核 控制层】
     * 政法审核 主页面显示
     * 显示字段：文号docNumber、政策法规名称polocyName、发文单位unitName、发布时间createTime、行政地区administrativeRegion
     * 约束：入库、修改、出库 已经提交，并且政法表中的当前申请人是当前登录人，且上个人的活跃状态为活跃
     * @param current              开始查询的页码数 默认为第1页
     * @param size                 每页的大小  默认每页显示10条数据
     * @param search               综合查询
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 15:45
     * @updateTime 2021/3/31 15:45
     */
    @ApiOperation("政法审核 主页面显示")
    @RequestMapping(value = "/policyCheckPage", method = RequestMethod.POST)
    public Result policyCheckPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) LibraryPolocyRegulation libraryPolocyRegulation,
            @RequestParam(value = "search", required = false) String search,
            String userId) {
        try {

            //1.精确查询封装成对象
            if(libraryPolocyRegulation==null){
                libraryPolocyRegulation=new LibraryPolocyRegulation();
            }


            //为了测试
            if(null==userId || "".equals(userId)){
                userId=String.valueOf(getUser().getId());
            }

            //2.调用数据库中的方法
            PageInfo<LibraryPolocyRegulation> pageInfo = libraryPolocyRegulationCheckRecService.policyCheckPage(current, size, libraryPolocyRegulation, search,userId);
            if (pageInfo.getSize() > 0) {
                //3.返回
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            } else {
                return getJsonResult(true, "还没有需要您审核的“政策法规申请”数据", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**
     * 2.TLibraryPolocyRegulationCheckRecController 【政策法规审核 控制层】
     * 选择一个法规进行审批 查看该法规的审批流相关数据
     *      返回的数据：
     *          1）该法规详情（调用政策法规 控制层中的updatePolicyDetail接口：修改(一条法规)申请 显示原有的数据）
     *          2）审批流数据：
     *              1.申请人：姓名、编号、完成
     *              2.审批记录
     * @param idX 政策法规的主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 16:49
     * @updateTime 2021/3/31 16:49
     */
    @ApiOperation("选择一个政策法规进行审批 查看该政策法规的审批流相关数据")
    @RequestMapping(value = "/queryFlowCheck", method = RequestMethod.POST)
    public Result queryFlowCheck(Integer idX) {
        try {
            if(null==idX){
                return getJsonResult(false, "请传递正确的政策法规主键id值", "");
            }
            LibraryPolocyRegulation libraryPolocyRegulation=libraryPolocyRegulationCheckRecService.queryFlowCheck(idX);
            Result result=new Result();
            result.setData(libraryPolocyRegulation);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**
     * 3.审批
     * 整体思路：
     *      1）调用服务层的方法，新增数据至审批表里（有没有附件上传都需要这个操作）、修改业务表数据
     *      2）判断是否有审批附件
     *      3）有附件上传：
     *            3-1.上传到本地服务器中
     *            3-2.调用服务层的方法，新增数据至附件表里
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 11:27
     * @updateTime 2021/4/2 11:27
     */
    @ApiOperation("审批")
    @RequestMapping(value = "/checkPolicy", method = RequestMethod.POST)
    public Result checkPolicy(@RequestBody LibraryPolocyRegulationCheckRec libraryPolocyRegulationCheckRec) {
        try {

            if(null==libraryPolocyRegulationCheckRec.getIdX()){
                return getJsonResult(false, "请传递正确的审批数据", "");
            }
            //1.调用服务层的方法，新增数据至审批表里（有没有附件上传都需要这个操作）、修改业务表数据
            Integer idB=libraryPolocyRegulationCheckRecService.checkPolicy(libraryPolocyRegulationCheckRec,getUser());

            //2.判断是否有审批附件
            //拿到上传的附件
            MultipartFile file=libraryPolocyRegulationCheckRec.getFile();
            EntityDao policyCheckAttaEntity = new TLibraryPolocyRegulationCheckAtta();
            if(null!=file) {

                //3.有附件上传：
                //3-1.上传到本地服务器中
                //调用文件保存的方法，并且返回接口类（接口类型，文件类型，要上传的文件，当前登录对象）
                //向上转型 成接口类EntityDao
                policyCheckAttaEntity = FileUpLoadUtil.databaseUpload(policyCheckAttaEntity, "1-政策法规库", file, getUser());


                //2-2.调用服务层的方法，新增数据至附件表里
                //将返回的接口 进行向下转型
                TLibraryPolocyRegulationCheckAtta policyCheckAtta = (TLibraryPolocyRegulationCheckAtta) policyCheckAttaEntity;
                //调用服务层的方法进行 将相关数据插入到数据表中 （要插入的数据、当前登录人）
                //设置附件表中对应的审批表中的主键值
                policyCheckAtta.setIdB(idB);
                libraryPolocyRegulationCheckRecService.insertCheckAtta(policyCheckAtta, getUser());
            }


            return getJsonResult(true, "审批成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "审批失败", "");
        }

    }


























}
