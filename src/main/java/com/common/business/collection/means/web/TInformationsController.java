package com.common.business.collection.means.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.means.service.TInformationsService;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.index.entity.TOperationManualRegulation;
import com.common.business.index.service.TOperationManualRegulationService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.entity.EntityDao;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.util.FileUpLoadUtil;
import com.common.system.util.ZipUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.dialect.rowbounds.InformixRowBoundsDialect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 * TInformationsController [资料收集上传 Contorller控制层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/tInformations")
@Api(value = "/tInformations",tags = {"资料收集上传接口"})
public class TInformationsController extends BaseController {

    @Autowired
    private TInformationsService informationsService;//资料收集上传 服务层接口


    /**
     * ok+1
     * 1.TInformationsController [资料收集上传 Contorller控制层]
     * 资料收集上传接口的主页面分页显示数据：当前登录人需要上传的数据资料（包含已经上传完毕跟未上传完毕的资料）
     *
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param proName      根据“子项目名称”搜索
     * @param riskLevel     根据“风险等级”搜索
     * @param search        综合查询的字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/16 16:03
     * @updateTime 2021/3/16 16:03
     */
    @ApiOperation("资料收集上传接口的主页面分页显示数据：当前登录人需要上传的数据资料（包含已经上传完毕跟未上传完毕的资料）")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result queryForPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "proName", required = false) String proName,
            @RequestParam(value = "riskLevel", required = false) String riskLevel,
            @RequestParam(value = "search", required = false) String search) {
        try {
            //1.将精确查询的字段 封装成对象
            TProPerformanceInfo proPerformanceInfo = new TProPerformanceInfo();
            proPerformanceInfo.setRiskLevel(riskLevel);//风险等级
            proPerformanceInfo.setProName(proName);//子项目名字
            String userId=String.valueOf(getUser().getId());//当前登录人id

            //2.调用服务层的方法拿到值
            PageInfo<TProPerformanceInfo> pageInfo = informationsService.queryForPage(current, size, proPerformanceInfo, search, userId);
            if (pageInfo.getSize() > 0) {
                //3.封装成 Result对象
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            } else {
                return getJsonResult(true, "您还没有任何要上传的项目资料", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**
     * ok+1
     * 2.TInformationsController [资料收集上传 Contorller控制层]
     * 选择一个项目，进行该项目的资料上传(进入需要上传模块的界面)
     *
     * @param idR       该项目下的资料清单关系表主键id
     * @param versionNO 该项目需要上传的最新版本号
     * @return Result
     * @author 田鑫艳
     * @createTime 2021/3/17 19:54
     * @updateTime 2021/3/17 19:54
     */
    @ApiOperation("选择一个项目进行上传(进入需要上传模块的界面)")
    @RequestMapping(value = "/chooseProjectUpload", method = RequestMethod.POST)
    public Result chooseProjectUpload(
            @RequestParam(value = "idR", required = false) Integer idR,
            @RequestParam(value = "informationVersionNO", required = false) String versionNO) {
        try {

            //2.调用服务层的方法拿到值
            List<TDevelopmentInformationList> developmentInformations = informationsService.chooseProjectUpload(idR, versionNO,getUser());

            //3.封装成 Result对象
            Result result = new Result();
            result.setData(developmentInformations);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**
     * ok
     * 3.TInformationsController [资料收集上传 Contorller控制层]
     * 选择一个大类，进行该大类下的的资料上传
     *
     * @param informationsVo       前端传过来的值，包含 文件、idB、idA
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/18 15:04
     * @updateTime 2021/3/18 15:04
     */
    @ApiOperation("选择一个模块，进行该模块下的的资料上传")
    @RequestMapping(value = "/chooseClassUpload", method = RequestMethod.POST)
    @Transactional
    public Result chooseClassUpload( InformationsVo informationsVo) {
        try {
            Integer idA=informationsVo.getIdA();//主子项目id主键
            Integer idB=informationsVo.getIdB();//拟定单的id主键
            MultipartFile[] files=informationsVo.getFiles();//前端传过来的集合
            //1.将上传的文件保存到本地服务器中
            //遍历 前台上传的文件个数，将每一个文件上传到本地服务器中， 并将每一个文件的相关数据插入到数据表中
            if (files!=null) {
                if(files.length > 0) {

                    //遍历最终要上传的文件，进行上传
                    for (MultipartFile listFile:files) {
                        //如果当前文件的大小大于0，说明这是个文件，那么才可以对该文件进行上传操作（getSize方法用来获取文件的大小，单位是字节。）
                        if (listFile.getSize()> 0) {

                            //向上转型 成接口类 EntityDao
                            EntityDao entity = new TInformations();
                            //调用文件保存方法，并且返回接口类  (接口类，文件类型，文件实体类，当前登录对象，上传的是哪个项目的(主子项目id值))
                            entity = FileUpLoadUtil.upload(entity, FileUpLoadUtil.COLLECTION_MEANS, listFile, getUser(),idA);

                            //将返回的接口 进行向下转型
                            TInformations information = (TInformations) entity;
                            //调用服务层的方法进行 将相关数据插入到数据表中
                            //2.将上传的文件的数据插入到 资料表中
                            information.setIdB(idB);
                            informationsService.saveClassUpload(information,getUser());//将该上传资料信息插入到数据表中
                        }

                    }

                }
                return getJsonResult(true, "上传成功", "");

            } else {
                return getJsonResult(true, "您没有选择任何一个文件进行上传", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "上传失败", "");
        }

    }


    /**
     * 4.暂存和提交 (保存)
     * @param operation   操作：暂存 0 ，提交 1
     * @param idA         主子项目主键
     * @param idR         清单关系表主键
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/27 17:23
     * @updateTime 2021/3/27 17:23
     */
    @ApiOperation("暂存和提交 (保存)")
    @RequestMapping(value = "/operation", method = RequestMethod.POST)
    @Transactional
    public Result operation (Integer idA,Integer operation,Integer idR){
        try {
            if(null==idA){
                return getJsonResult(false, "请传递正确的主子项目idA值", "");
            }
            //修该本次操作的拟定表的状态
            informationsService.updateCoumnById(idA,operation,idR,getUser());

            return getJsonResult(true, "操作成功", "");
        }catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "操作失败", "");
        }
    }



    /**
     * ok
     * 5.TInformationsController [资料收集上传 Contorller控制层]
     * 删除资料文件
     *
     * @param idC 被删除的资料主键id,用逗号分割
     * @param idB 被删除的拟定清单的主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 11:31
     * @updateTime 2021/3/19 11:31
     */
    @ApiOperation("(单个/批量)删除资料文件")
    @RequestMapping(value = "/chooseClassDelete", method = RequestMethod.POST)
    @Transactional
    public Result chooseClassDelete(String idC, Integer idB) {
        try {
            if(idC!=null&&idB!=null) {
                //1.将要删除的idC字符串转换为集合
                List<String> deleteInformanceIdCs = Arrays.asList(idC.split(","));

                //2.查询 前端传过来的数据，看数据表中是否有数据，如果有则进行删除
                List<TInformations> selectInfos = informationsService.selectInformations(deleteInformanceIdCs, idB);
                if (selectInfos.size() > 0) {
                    //3.遍历删除本地服务器中的数据
                    for (TInformations deleteInfo : selectInfos) {
                        FileUpLoadUtil.deleteFile(deleteInfo.getFilePath());
                    }
                    //4.调用服务层  删除数据表中的数据
                    informationsService.chooseClassDelete(deleteInformanceIdCs, idB);
                }


                return getJsonResult(true, "删除成功", "");
            }else{
                return getJsonResult(true, "请选择一个文件进行删除", "");
            }



        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "删除失败", "");
        }


    }


    /**ok
     * 6.TInformationsController [资料收集上传 Contorller控制层]
     * 下载资料文件
     * @param idCs 要下载的资料表的idc主键集合（中间用逗号分割）
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/22 15:49
     * @updateTime 2021/3/22 15:49
     */
    @ApiOperation("选择一个资料名，进行下载该资料")
    @RequestMapping(value = "/chooseClassDownload", method = RequestMethod.GET)
    public Result chooseClassDownload(Integer idC, String idCs, HttpServletResponse response) {
        try {
            //1)单个文件下载
            if(idC!=null){
                //1-1.查询 前端传过来的数据，看数据表中是否有数据，如果有则进行下载
                List<String> oneIdC=new ArrayList<>();
                oneIdC.add(String.valueOf(idC));
                List<TInformations> oneInfo = informationsService.selectInformations(oneIdC, null);
                //1-2.调用单个下载文件的方法
                if (oneInfo.size() > 0) {
                    FileUpLoadUtil.downLoadFile(oneInfo.get(0).getFilePath(),oneInfo.get(0).getFileName(),response);
                }
            }
            //2)批量下载
            else if(StringUtils.isNotEmpty(idCs)){
                //2-1.查询 前端传过来的数据，看数据表中是否有数据，如果有则进行下载
                List<String> bathIdCs=Arrays.asList(idCs.split(Pattern.quote(",")));
                List<TInformations> bathInfos = informationsService.selectInformations(bathIdCs, null);
                if(bathInfos!=null && bathInfos.size()>0){
                    HashMap<String,String> urlMap=new HashMap<>();
                    int i=1;
                    //2-2.设置 要下载的文件路径集合：urlMap
                    for(TInformations info:bathInfos){
                        if(urlMap.get(info.getFileName())!=null){
                            String fileName=i+"、"+info.getFileName();
                            urlMap.put(fileName,info.getFilePath());
                            i++;
                        }else{
                            urlMap.put(info.getFileName(),info.getFilePath());
                        }
                    }
                    //2-3.设置 下载的压缩包的名字为第一个文件的名字 xxxxxxx.doc 去掉.doc后缀
                    String zipName=bathInfos.get(0).getFileName().substring(0,bathInfos.get(0).getFileName().lastIndexOf("."));
                    //2-4.调用 批量下载成压缩包的方法
                    ZipUtil.downZip(urlMap,zipName);

                }

            }else{
                return getJsonResult(false, "请选择文件进行下载", "");
            }

            return getJsonResult(true, "文件下载成功", "");

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "文件下载失败", "");
        }


    }


    /**ok
     * 7.局部刷新拟定清单下的资料文件
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/27 14:10
     * @updateTime 2021/3/27 14:10
     */
    @ApiOperation("局部刷新拟定清单下的资料文件")
    @RequestMapping(value = "/refreshFiles", method = RequestMethod.POST)
    public Result refreshFiles(Integer idB) {
        try {

            if(null==idB){
                return getJsonResult(false, "请传入正确的拟定清单id主键值", "");
            }
            //2.根据拟定清单主键idB拿到该拟定清单下的资料数据
            EntityWrapper entityWrapper=new EntityWrapper();
            entityWrapper.eq("ID_B",idB);
            List<TInformations> informations = informationsService.selectList(entityWrapper);

            //3.封装成 Result对象
            Result result = new Result();
            result.setData(informations);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }



    //资料收集审核
    /**ok
     * 1.资料收集审核
     * 审核项目中上传的资料文件 审核人是外勤主管
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param proName       根据“子项目名称”搜索
     * @param riskLevel     根据风险等级搜索
     * @param search        综合查询的字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 10:03
     * @updateTime 2021/3/24 10:03
     */
    @ApiOperation("资料收集审核主页面分页显示数据（包含已审核和待审核的数据）")
    @RequestMapping(value = "/agreePage", method = RequestMethod.POST)
    public Result agreePage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "proName", required = false) String proName,
            @RequestParam(value = "riskLevel", required = false) String riskLevel,
            @RequestParam(value = "search", required = false) String search

    ){
        try {
            //1.将精确查询封装成对象
            TProPerformanceInfo proPerformanceInfo=new TProPerformanceInfo();
            proPerformanceInfo.setProName(proName);
            proPerformanceInfo.setRiskLevel(riskLevel);
            String userId=String.valueOf(getUser().getId());

            //2.调用服务层的方法进行查询
            PageInfo<TProPerformanceInfo> pageInfo=informationsService.agreePage(current,size,proPerformanceInfo,userId,search);
            if(pageInfo.getSize()>0){
                //3.将查询到的结果封装成Result对象，并且返回
                Result result=new Result();
                result.setData(pageInfo);
                return result;
            }else{
                return getJsonResult(true, "没有任何需要审核的项目资料", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }


    }


    /**ok
     * 2.资料收集审核
     * 该项目下需要审核认证的资料信息界面
     * @param idR 拟定关系表中的主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 11:24
     * @updateTime 2021/3/24 11:24
     */
    @ApiOperation("该项目下需要审核认证的资料信息界面")
    @RequestMapping(value = "/fileDetails", method = RequestMethod.POST)
    public Result fileDetails(Integer idR, Integer detail){
        try {

            //1.调用服务层的方法进行查询
            List<TDevelopmentInformationList> developmentInformations=informationsService.fileDetails(idR,detail);
            if(developmentInformations.size()>0){
                //2.将查询到的结果封装成Result对象，并且返回
                Result result=new Result();
                result.setData(developmentInformations);
                return result;
            }else{
                return getJsonResult(true, "没有任何需要认证的项目资料", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /** ok
     * 3.资料收集审核
     * 单个/批量 认证
     * @param developmentInfos 前端传过来的 资料集合
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 11:54
     * @updateTime 2021/3/24 11:54
     */
    @ApiOperation("单个/批量 认证")
    @RequestMapping(value = "/agreeFiles", method = RequestMethod.POST)
    public Result agreeFiles(@RequestBody InformationsVo developmentInfos){
        try {
            if(developmentInfos!=null && developmentInfos.getDevelopmentInformationList()!=null&&developmentInfos.getDevelopmentInformationList().size()>0){
                informationsService.agreeFiles(developmentInfos,getUser());
                return getJsonResult(true, "认证成功", "");
            }else{
                return getJsonResult(true, "请选择要认证的资料", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "认证失败", "");
        }

    }



















}
