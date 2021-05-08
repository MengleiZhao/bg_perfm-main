package com.common.business.library.regulations.web;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.means.entity.TInformations;
import com.common.business.library.regulations.entity.LibraryPolocyRegulation;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationAtta;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationUptAtta;
import com.common.business.library.regulations.service.TLibraryPolocyRegulationAttaService;
import com.common.business.library.regulations.service.TLibraryPolocyRegulationService;
import com.common.business.library.regulations.service.TLibraryPolocyRegulationUptAttaService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.system.entity.EntityDao;
import com.common.system.page.BaseController;
import com.common.system.page.Result;
import com.common.system.util.FileUpLoadUtil;
import com.common.system.util.ZipUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * TLibraryPolocyRegulationController 【政策法规库 控制层】
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
@RestController
@Api(value = "/tLibraryPolocyRegulation", tags = {"政策法规库 接口"})
@RequestMapping("/tLibraryPolocyRegulation")
public class TLibraryPolocyRegulationController extends BaseController {

    @Autowired
    private TLibraryPolocyRegulationService libraryPolocyRegulationService;//政策法规库 服务层
    @Autowired
    private TLibraryPolocyRegulationAttaService libraryPolocyRegulationAttaService;//政法附件 service
    @Autowired
    private TLibraryPolocyRegulationUptAttaService libraryPolocyRegulationUptAttaService;//政法修改附件 service


    /**
     * ok
     * 1.TLibraryPolocyRegulationController 【政策法规库 控制层】
     * 政法数据库 主页面显示
     * 显示字段：文号docNumber、政策法规名称polocyName、发文单位unitName、发布时间createTime、行政地区administrativeRegion
     *
     * @param current              开始查询的页码数 默认为第1页
     * @param size                 每页的大小  默认每页显示10条数据
     * @param search               综合查询
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 13:49
     * @updateTime 2021/3/26 13:49
     */
    @ApiOperation("1-政法数据库 主页面显示")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result queryForPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) LibraryPolocyRegulation libraryPolocyRegulation,
            @RequestParam(value = "search", required = false) String search) {
        try {
            if(libraryPolocyRegulation==null){
                libraryPolocyRegulation=new LibraryPolocyRegulation();
            }
            //2.调用数据库中的方法
            PageInfo<LibraryPolocyRegulation> pageInfo = libraryPolocyRegulationService.queryForPage(current, size, libraryPolocyRegulation, search);
            if (pageInfo.getSize() > 0) {
                //3.返回
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            } else {
                return getJsonResult(true, "还没有在库或者正在审批中的政策法规", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**
     * ok
     * 2.TLibraryPolocyRegulationController 【政策法规库 控制层】
     * 根据主键id 查看该法规的详情（text文本）
     *
     * @param idX 政策法规库主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 15:53
     * @updateTime 2021/3/26 15:53
     */
    @ApiOperation("2-查看该法规的详情（text文本）")
    @RequestMapping(value = "/policyDetail", method = RequestMethod.POST)
    public Result policyDetail(Integer idX) {
        try {

            if (idX != null) {
                LibraryPolocyRegulation libraryPolocyRegulation = libraryPolocyRegulationService.policyDetail(idX);
                if (libraryPolocyRegulation != null) {
                    //3.返回
                    Result result = new Result();
                    result.setData(libraryPolocyRegulation);
                    return result;
                } else {
                    return getJsonResult(false, "没有该法规记录，请传递正确的法规id值", "");
                }

            } else {
                return getJsonResult(false, "请传送该法规的id值", "");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }

    }


    /**
     * ok
     * 3.TLibraryPolocyRegulationController 【政策法规库 控制层】
     * 点击一个政策法规 进行下载
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 16:02
     * @updateTime 2021/3/26 16:02
     */
    @ApiOperation("3-下载该法规文件")
    @RequestMapping(value = "/downloadPolicy", method = RequestMethod.GET)
    public Result downloadPolicy(Integer idX, String idXs, HttpServletResponse response) {

        try {

            //1）单个文件下载
            if (idX != null) {
                //1-1.根据前端传过来的数据，查看数据表中是否有数据，如果有则进行下载 （根据idX主键拿到值，存在则是1条，不存在则是null）
                EntityWrapper idXwrapper=new EntityWrapper();
                idXwrapper.eq("ID_X",idX);
                List<TLibraryPolocyRegulationAtta> libraryPolocyRegulationAttas=libraryPolocyRegulationAttaService.selectList(idXwrapper);
                if (libraryPolocyRegulationAttas != null && libraryPolocyRegulationAttas.size()>0) {
                    for(TLibraryPolocyRegulationAtta atta:libraryPolocyRegulationAttas){
                        //1-2.调用工具类的方法 下载本地服务器中的政法数据
                        FileUpLoadUtil.downLoadFile(atta.getFilePath(), atta.getFileName(), response);
                    }

                }

            //2）批量下载
            }else if(StringUtils.isNotEmpty(idXs)){
                List<String> chooseIdXs=Arrays.asList(idXs.split(Pattern.quote(",")));
                //2-1.根据前端传过来的数据，查看数据表中是否有数据，如果有则进行下载
                List<TLibraryPolocyRegulationAtta> libraryAttas = libraryPolocyRegulationAttaService.queryByIdXs(chooseIdXs);
                //2-2.存储批量下载的文件地址
                HashMap<String,String> urlMap=new HashMap<>();
                if(libraryAttas!=null && libraryAttas.size()>0){
                    //2-3.遍历 将文件地址加载到urlMap中
                    int i=1;
                    for(TLibraryPolocyRegulationAtta atta:libraryAttas){
                        //如果文件名重复，则进行加i
                        if(urlMap.get(atta.getFileName()) !=null){
                            String fileName=i+"、"+atta.getFileName();
                            urlMap.put(fileName,atta.getFilePath());
                            i++;
                        }else{
                            //针对第一次的时候（和文件不重复的时候），map中是没有数据的，所以，上面的判断是null，直接将名字放进去即可
                            urlMap.put(atta.getFileName(),atta.getFilePath());
                        }
                    }

                    //设置下载文件的压缩包的名字（zipName）为第一个文件的名字
                    String zipName=libraryAttas.get(0).getFileName()
                            .substring(0,libraryAttas.get(0).getFileName().lastIndexOf("."));
                    //2-4.调用批量下载的方法
                    ZipUtil.downZip(urlMap,zipName);
                }
            } else{
                return getJsonResult(true, "请选择政法文件进行下载", "");
            }

            return getJsonResult(true, "政法文件下载成功", "");

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "政法文件下载失败", "");
        }

    }






    //数据库更新

    /**
     * ok
     * 1.TLibraryPolocyRegulationController 【政策法规库 控制层】
     * 数据库更新 政策法规 界面 显示：入库申请、出库申请、修改申请的数据
     * 显示字段：文号docNumber、政策法规名称polocyName、发文单位unitName、发布时间createTime、行政地区administrativeRegion
     *
     * @param current              开始查询的页码数 默认为第1页
     * @param size                 每页的大小  默认每页显示10条数据
     * @param libraryPolocyRegulation   精确查询封装的对象
     * @param search               综合查询
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 17:02
     * @updateTime 2021/3/26 17:02
     */
    @ApiOperation("1-数据库更新 政策法规 主页面分页显示")
    @RequestMapping(value = "/renewPolicyPage", method = RequestMethod.POST)
    public Result renewPolicyPage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) LibraryPolocyRegulation libraryPolocyRegulation,
            @RequestParam(value = "search", required = false) String search
    ) {
        try {
            if(libraryPolocyRegulation==null){
                libraryPolocyRegulation=new LibraryPolocyRegulation();
            }

            //2.调用数据库中的方法
            PageInfo<LibraryPolocyRegulation> pageInfo = libraryPolocyRegulationService.queryRenewPage(current, size, libraryPolocyRegulation, search);
            if (pageInfo.getSize() > 0) {
                //3.返回
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            } else {
                return getJsonResult(false, "还没有任何法规提出申请", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "查询失败", "");
        }
    }


    /**
     * 2.TLibraryPolocyRegulationController 【政策法规库 控制层】
     * 政策法规 入库(修改)申请,提交/暂存
     * 整体思路：
     *      1）判断是否是入库/修改的申请==》是 可以操作，否 返回提示信息
     *      2）判断 操作是 “0-暂存”还是“1-提交”==》是 进行“暂存”/“提交”的操作，否 返回提示信息
     *      3）判断数据来源 是“1-项目”还是“2-非项目”
     *      4）“2-非项目”
     *          4-1.判断上传的文件是否为空==》不为空 可以操作（上传/修改），空 返回提示信息
     *          4-2.判断上传的政策法规的名字是否正确==》正确进行操作，不正确 返回提示信息
     *          4-3.将文件上传到本地服务器上
     *          4-4.将政策法规对象 添加/修改 到数据表中
     *      5）“1-项目”
     *          判断原来是否有数据，如果有数据，则说明原来是暂存，已经将资料信息从资料目录复制到政法目录种了，则直接将政法对象插入到数据表中
     *          有数据，则是提交
     *
     *          没数据，则是暂存/提交
     *          5-1.判断传递的idC是否正确==》正确 进行操作，不正确 返回提示信息
     *          5-2.从 资料表 中拿到资料数据
     *          5-3.将资料文件 从 资料目录 中复制到 政策法规库 目录中 作为政法文件附件
     *          5-4.设置政法对象
     *          5-5.将政策法规对象直接 添加/修改 到数据表中
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 17:31
     * @updateTime 2021/3/26 17:31
     */
    @ApiOperation("2-政策法规 入库(修改)申请,提交/暂存")
    @RequestMapping(value = "/inUpdatePolicy", method = RequestMethod.POST)
    @Transactional
    public Result inUpdatePolicy( LibraryPolocyRegulation libraryPolocyRegulation, BindingResult bindingResult) {
        try {

            String content=libraryPolocyRegulation.getContent();
            content=content.replaceAll("& ","&");
            content=HtmlUtils.htmlUnescape(content);
            libraryPolocyRegulation.setContent(content);

            if(null==libraryPolocyRegulation){
                return getJsonResult(false, "请传送正确的“政策法规”申请对象", "");
            }

            //拿到所有的上传文件 附件
            MultipartFile[] files = libraryPolocyRegulation.getFiles();

            //1.判断是否是入库/修改的申请==》是 可以操作，否 返回提示信息
            if ("2".equals(libraryPolocyRegulation.getUptType()) || "3".equals(libraryPolocyRegulation.getUptType())) {
                //2.判断 操作是 “0-暂存”还是“1-提交”==》是 进行“暂存”/“提交”的操作，否 返回提示信息
                String operation = libraryPolocyRegulation.getDataStauts();//存放 操作
                if ("0".equals(operation) || "1".equals(operation)) {

                    //3.判断数据来源 是“1-项目”还是“2-非项目”
                    String source=libraryPolocyRegulation.getDataSources();

                    //最终上传的文件集合
                    List<TLibraryPolocyRegulationAtta> policyAttas=new ArrayList<>();

                    //4.“2-非项目”
                    if("2".equals(source)) {
                            //判断 政策法规主键id是否存在，file是否没有再次传递,没有则说明在原来的基础上没有上传新的文件
                            if(null!=libraryPolocyRegulation.getIdX()&& files==null){
                                //调用服务层的方法进行 将相关数据插入到数据表中 （文件转换成政法类型、政法类数据、当前登录人）
                                libraryPolocyRegulationService.inUpdatePolicy(null,libraryPolocyRegulation,getUser());
                            }else{

                                //4-1.判断上传的文件是否为空==》不为空 可以操作（上传/修改），空 返回提示信息
                                if (files != null && files.length>0) {
                                    for(MultipartFile file:files){
                                        if(file.getSize()>0){
                                            //4-2.判断上传的政策法规的名字是否正确==》正确进行操作，不正确 返回提示信息
                                            if (libraryPolocyRegulation.getPolocyName() != null && !"".equals(libraryPolocyRegulation.getPolocyName())) {

                                                //4-3.将文件上传到本地服务器上
                                                //调用文件保存的方法，并且返回接口类（接口类型，文件类型，要上传的文件，当前登录对象）
                                                //向上转型 成接口类EntityDao
                                                EntityDao PolicyEntity = new TLibraryPolocyRegulationAtta();
                                                PolicyEntity = FileUpLoadUtil.databaseUpload(PolicyEntity, "1-政策法规库", file, getUser());

                                                //4-4.将政策法规对象 添加/修改 到数据表中
                                                //将返回的接口 进行向下转型
                                                TLibraryPolocyRegulationAtta libraryPolocy = (TLibraryPolocyRegulationAtta) PolicyEntity;

                                                //将向下转型的数据添加到policyAttas集合中
                                                policyAttas.add(libraryPolocy);

                                            }
                                            //调用服务层的方法进行 将相关数据插入到数据表中 （文件转换成政法类型、政法类数据、当前登录人）
                                            libraryPolocyRegulationService.inUpdatePolicy(policyAttas, libraryPolocyRegulation, getUser());

                                        }else {
                                            return getJsonResult(false, "请传递正确的文件，该文件的大小小于0kb", "");
                                        }
                                    }
                                } else {
                                    return getJsonResult(false, "您没有选择任何一个政法文件进行上传", "");
                                }
                            }
                    }

                    //5.“1-项目”
                    else if("1".equals(source)){
                        //5-1.判断传递的idC是否正确==》正确 进行操作，不正确 返回提示信息
                        List<String> idCs=new ArrayList<>();
                        if(libraryPolocyRegulation.getIdCs()!=null){
                            idCs=Arrays.asList(libraryPolocyRegulation.getIdCs().split(Pattern.quote(",")));
                        }
                        //判断原来是否有数据，如果有数据，则说明原来是暂存，已经将资料信息从资料目录复制到政法目录种了，则直接将政法对象插入到数据表中
                        //根据idX和idC进行判断，如果idX有值，且idC无值，则说明是 项目->选择资料-->暂存 之后的又一次 暂存/提交 且没有再次选择资料的操作
                        if(null!=libraryPolocyRegulation.getIdX()&&null==idCs){
                            //暂存
                            //调用服务层的方法进行 将相关数据插入到数据表中 （文件转换成政法类型、政法类数据、当前登录用户）
                            libraryPolocyRegulationService.inUpdatePolicy(null, libraryPolocyRegulation,getUser());
                        }else{

                            if(null==idCs){
                                return getJsonResult(false, "请传递正确的资料文件的主键id值", "");
                            }else{
                                //5-2.从 资料表 中拿到资料数据
                                List<TInformations> informations=libraryPolocyRegulationService.queryInformationByIdC(idCs);
                                //5-3.遍历 将资料文件 从 资料目录 中复制到 政策法规库 目录中 作为政法文件附件
                                for(TInformations information:informations){
                                    TLibraryPolocyRegulationAtta atta=new TLibraryPolocyRegulationAtta();
                                    //根据资料数据 得到旧的文件目录
                                    String oldPath=information.getFilePath();
                                    //设置要复制到哪个目录中（政策法规目录：1-政策法规库）
                                    String newPath=FileUpLoadUtil.newDatabasePath(information.getFileName(),"1-政策法规库");
                                    //调用文件复制方法，进行复制
                                    FileUpLoadUtil.copyFile(oldPath,newPath);

                                    //5-4.设置附件对象
                                    atta.setFileName(information.getFileName());//文件名字
                                    atta.setFileSize(information.getFileSize());//文件大小
                                    atta.setFilePath(newPath);//文件路径

                                    //添加到最终上传的附件集合对象中
                                    policyAttas.add(atta);

                                }

                                //5-5.将政策法规对象直接 添加/修改 到数据表中
                                //调用服务层的方法进行 将相关数据插入到数据表中 （上传的附件、政法类数据、当前登录用户）
                                libraryPolocyRegulationService.inUpdatePolicy(policyAttas, libraryPolocyRegulation,getUser());
                            }
                        }


                    }else{
                        return getJsonResult(false, "请传递正确的数据来源值：1-项目，2-非项目", "");
                    }
                    //非项目/项目 的修改和入库 都成功的情况下返回成功信息
                    return getJsonResult(true, "政法法规库入库(修改)申请 成功", "");

                } else {
                    return getJsonResult(false, "请传递正确的操作 0-暂存，1-提交", "");
                }

            }else {
                return getJsonResult(false, "请传递正确的申请：2-入库申请，3-修改申请", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "政法法规库入库(修改)申请 失败", "");
        }


    }

    /**
     * ---上传文件 至本地服务器
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 22:55
     * @updateTime 2021/4/28 22:55
     */
    @RequestMapping(value = "/inOrUptFiles",method = RequestMethod.POST)
    @ApiOperation("----上传文件 至本地服务器")
    public Result inOrUptFiles(TLibraryPolocyRegulationVo vo){
        try {
            //最终返回的集合
           Result result=new Result();

            //数据来源 1-项目 2-非项目
            String dataSources=vo.getDataSources();
            //调整类型 1-出库申请 2-入库申请 3-修改申请
            String uptType=vo.getUptType();
            //上传的附件
            MultipartFile[] files=vo.getFile();
            if(StringUtils.isEmpty(dataSources)){
                return getJsonResult(false,"请传递正确的数据来源：1-项目  2-非项目","");
            }
            if(StringUtils.isEmpty(uptType)){
                return getJsonResult(false,"请传递正确的调整类型：1-出库申请 2-入库申请","");
            }

            /* 1）“2-非项目”：
            *   1.遍历上传的文件，将文件上传到服务器中
            *   2.将上传的文件向下转型，得到政法附件记录
            *   3.将政法附件记录插入到数据表中，并返给前端
            */
            if("2".equals(dataSources)){
                if(files==null || files.length==0){
                    return  getJsonResult(false,"请传递附件","");
                }
                //入库
                List<TLibraryPolocyRegulationAtta> attaList=new ArrayList<>();
                //修改库
                List<TLibraryPolocyRegulationUptAtta> uptAttaList=new ArrayList<>();
                //1.遍历上传的文件，将文件上传到服务器中
                if (files != null && files.length>0) {
                    for (MultipartFile file : files) {
                        if (file.getSize() > 0) {
                            //4-3.将文件上传到本地服务器上
                            //调用文件保存的方法，并且返回接口类（接口类型，文件类型，要上传的文件，当前登录对象）
                            //向上转型 成接口类EntityDao
                            EntityDao PolicyEntity = new TLibraryPolocyRegulationAtta();
                            PolicyEntity = FileUpLoadUtil.databaseUpload(PolicyEntity, "1-政策法规库", file, getUser());

                            //4-4.将政策法规对象 添加/修改 到数据表中
                            //将返回的接口 进行向下转型
                            // 2-入库
                            if("2".equals(uptType)){
                                TLibraryPolocyRegulationAtta atta = (TLibraryPolocyRegulationAtta) PolicyEntity;
                                attaList.add(atta);
                            }
                            //3-修改库
                            if("3".equals(uptType)){
                                TLibraryPolocyRegulationUptAtta uptAtta=(TLibraryPolocyRegulationUptAtta)PolicyEntity;
                                uptAttaList.add(uptAtta);
                            }
                        }
                    }
                    // 2-入库
                    if("2".equals(uptType)){
                        //调入插入 入库的服务的的方法
                        List<TLibraryPolocyRegulationAtta> returnAttas=libraryPolocyRegulationService.inPolicyAtta(attaList,getUser());
                        result.setData(returnAttas);
                    }
                    //3-修改库
                    else if("3".equals(uptType)){
                        //调入插入修改库的服务方法
                        List<TLibraryPolocyRegulationUptAtta> returnuptAttas=libraryPolocyRegulationService.inPolicyUptAtta(uptAttaList,getUser());
                        result.setData(returnuptAttas);
                    }


                }

            }
            /* 2）“1-项目”：
             *  1.查找资料
             *  2.遍历资料，将资料移动到本地服务器中
             *  3.将数据插入到数据表中，并返给前端
             */
            else if("1".equals(dataSources)){
                //入库
                List<TLibraryPolocyRegulationAtta> attaList=new ArrayList<>();
                //修改库
                List<TLibraryPolocyRegulationUptAtta> uptAttaList=new ArrayList<>();

                //资料主键值
                String idCs=vo.getIdCs();
                if(StringUtils.isEmpty(idCs)){
                    return getJsonResult(false,"请传递正确的资料主键值，中间用逗号分割","");
                }
                //1.查找资料
                List<String> idCList=Arrays.asList(idCs.split(Pattern.quote(",")));
                if(idCList!=null && idCList.size()>0){
                    List<TInformations> informations=libraryPolocyRegulationService.queryInformationByIdC(idCList);
                    if(informations!=null && informations.size()>0){
                        //2.遍历资料，将资料移动到本地服务器中
                        for(TInformations information:informations){
                            //根据资料数据 得到旧的文件目录
                            String oldPath=information.getFilePath();
                            //设置要复制到哪个目录中（政策法规目录：1-政策法规库）
                            String newPath=FileUpLoadUtil.newDatabasePath(information.getFileName(),"1-政策法规库");
                            //调用文件复制方法，进行复制
                            FileUpLoadUtil.copyFile(oldPath,newPath);
                            //2-入库
                            if("2".equals(uptType)){
                                TLibraryPolocyRegulationAtta atta=new TLibraryPolocyRegulationAtta();
                                //设置附件对象
                                atta.setFileName(information.getFileName());//文件名字
                                atta.setFileSize(information.getFileSize());//文件大小
                                atta.setFilePath(newPath);//文件路径
                                attaList.add(atta);
                            }
                            //2-修改库
                            if("3".equals(uptType)){
                                TLibraryPolocyRegulationUptAtta uptAtta=new TLibraryPolocyRegulationUptAtta();
                                //设置附件对象
                                uptAtta.setFileName(information.getFileName());//文件名字
                                uptAtta.setFileSize(information.getFileSize());//文件大小
                                uptAtta.setFilePath(newPath);//文件路径
                                uptAttaList.add(uptAtta);
                            }

                    }

                    if("2".equals(uptType)){
                        //调入插入 入库的服务的的方法
                        List<TLibraryPolocyRegulationAtta> returnAttas=libraryPolocyRegulationService.inPolicyAtta(attaList,getUser());
                        result.setData(returnAttas);
                    }else if("3".equals(uptType)){
                        //调入插入修改库的服务方法
                        List<TLibraryPolocyRegulationUptAtta> returnuptAttas=libraryPolocyRegulationService.inPolicyUptAtta(uptAttaList,getUser());
                        result.setData(returnuptAttas);
                    }

                    }
                }

            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"操作失败","");
        }
    }

    /**
     * ----入库/修改库 暂存和提交
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 15:02
     * @updateTime 2021/4/29 15:02
     */
    @RequestMapping(value = "/inOrUptPolicy",method = RequestMethod.POST)
    @ApiOperation("----入库/修改库 暂存和提交")
    public Result inOrUptPolicy(@RequestBody TLibraryPolicyVo vo){
        try {
            //数据来源 1-项目 2-非项目
            String dataSources=vo.getDataSources();

            //保留的政法附件
            List<TLibraryPolocyRegulationAtta> attas=vo.getSavePolocyRegulationAttas();
            //保留的政法修改附件
            List<TLibraryPolocyRegulationUptAtta> uptAttas=vo.getSavePolocyRegulationUptAttas();
            //删除的政法附件
            List<TLibraryPolocyRegulationAtta> delAttas=vo.getDelPolocyRegulationAttas();
            //删除的政法修改附件
            List<TLibraryPolocyRegulationUptAtta> delUptAttas=vo.getDelPolocyRegulationUptAttas();
            //政法对象：
            LibraryPolocyRegulation libraryPolocyRegulation=vo.getLibraryPolocyRegulation();
            //调整类型 1-出库申请 2-入库申请 3-修改申请
            String uptType=vo.getLibraryPolocyRegulation().getUptType();

            if(StringUtils.isEmpty(uptType)){
                return getJsonResult(false,"请传递正确的调整类型：1-出库申请 2-入库申请","");
            }
            //2-入库申请
            if("2".equals(uptType)){
                libraryPolocyRegulationService.inPolicy(attas,delAttas,libraryPolocyRegulation,getUser());
            }
            //3-修改库申请
            else if("3".equals(uptType)){
                //针对第一次 修改时我返给前端的是附件表中的数据
                if(attas!=null && attas.size()>0){
                    //将 前端保留的的数据 转换为 修改附件表数据
                    for(TLibraryPolocyRegulationAtta atta:attas){
                        TLibraryPolocyRegulationUptAtta uptAtta=new TLibraryPolocyRegulationUptAtta();
                        uptAtta.setFileName(atta.getFileName());
                        uptAtta.setFileSize(atta.getFileSize());
                        uptAtta.setFilePath(atta.getFilePath());
                        uptAtta.setCreateorId(atta.getCreateorId());
                        uptAtta.setCreateor(atta.getCreateor());
                        uptAtta.setCreateTime(atta.getCreateTime());
                        uptAttas.add(uptAtta);
                    }
                }
                //将 前端删除的数据  转换为 修改附件表数据（删除的）（因为前删除了政法表的数据没必要保存到修改表中
                // [而且，删除的数据，修改表中是没有的，无法进行删除，因此这个操作就不需要了]）
                /*if(delAttas!=null && delAttas.size()>0){
                    for(TLibraryPolocyRegulationAtta delAtta:delAttas){
                        TLibraryPolocyRegulationUptAtta delUptAtta=new TLibraryPolocyRegulationUptAtta();
                        delUptAtta.setFileName(delAtta.getFileName());
                        delUptAtta.setFileSize(delAtta.getFileSize());
                        delUptAtta.setFilePath(delAtta.getFilePath());
                        delUptAtta.setCreateorId(delAtta.getCreateorId());
                        delUptAtta.setCreateor(delAtta.getCreateor());
                        delUptAtta.setCreateTime(delAtta.getCreateTime());
                        delUptAttas.add(delUptAtta);
                    }
                }*/
                libraryPolocyRegulationService.uptPolicy(uptAttas,delUptAttas,libraryPolocyRegulation,getUser());
            }


            return getJsonResult(true,"操作成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"操作失败","");
        }

    }



    /**
     * ----点击关闭
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 14:49
     * @updateTime 2021/4/29 14:49
     */
    @RequestMapping(value = "/colsePolicy",method = RequestMethod.POST)
    @ApiOperation("--------点击关闭")
    public Result colsePolicy(@RequestBody TLibraryPolicyVo vo){
        try {
            //关闭时，前端已经上传的政法附件集合
            List<TLibraryPolocyRegulationAtta> uploadPolocyRegulationAttas=vo.getUploadPolocyRegulationAttas();
            //关闭时，前端已经上传的政法修改附件集合
            List<TLibraryPolocyRegulationUptAtta> uploadPolocyRegulationUptAttas=vo.getUploadPolocyRegulationUptAttas();

            //删除政法附件表和本地服务上的文件
            if(uploadPolocyRegulationAttas!=null && uploadPolocyRegulationAttas.size()>0){
                List<Integer> delAttas=new ArrayList<>();
                for(TLibraryPolocyRegulationAtta atta:uploadPolocyRegulationAttas){
                    if(StringUtils.isNotEmpty(atta.getFilePath())){
                        FileUpLoadUtil.deleteFile(atta.getFilePath());
                        delAttas.add(atta.getIdC());
                    }
                }
                libraryPolocyRegulationAttaService.deleteBatchIds(delAttas);
            }
            //删除政法修改表和本地服务上的文件
            if(uploadPolocyRegulationUptAttas!=null && uploadPolocyRegulationUptAttas.size()>0){
                List<Integer> delUptAttas=new ArrayList<>();
                for(TLibraryPolocyRegulationUptAtta uptAtta:uploadPolocyRegulationUptAttas){
                    if(StringUtils.isNotEmpty(uptAtta.getFilePath())){
                        FileUpLoadUtil.deleteFile(uptAtta.getFilePath());
                        delUptAttas.add(uptAtta.getIdUA());
                    }
                }
                libraryPolocyRegulationUptAttaService.deleteBatchIds(delUptAttas);
            }
            return getJsonResult(true,"关闭成功","");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"关闭失败","");
        }

    }


    /**
     * 3.政策法规 出库申请 提交/暂存
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 18:10
     * @updateTime 2021/3/30 18:10
     */
    @ApiOperation("3-政策法规 出库申请 提交/暂存")
    @RequestMapping(value = "/outPolicy", method = RequestMethod.POST)
    @Transactional
    public Result outPolicy(@RequestBody LibraryPolocyRegulationVo libraryPolocyRegulation) {
        try {
            if (null == libraryPolocyRegulation && !"1".equals(libraryPolocyRegulation.getUptType())) {
                return getJsonResult(true, "请传送正确的调整类型：1-出库申请", "");
            } else if(null == libraryPolocyRegulation || null ==libraryPolocyRegulation.getIdX()){
                return getJsonResult(true, "请传送正确的政策法规主键值", "");
            }else {

                libraryPolocyRegulationService.outPolicy(libraryPolocyRegulation,getUser());
                return getJsonResult(true, "政策法规 出库申请 成功", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(true, "政策法规 出库申请 失败", "");
        }


    }


    /**
     * 4.入库申请和修改申请，政策法规名唯一
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 17:59
     * @updateTime 2021/3/30 17:59
     */
    @ApiOperation("4-入库申请和修改申请，政策法规名唯一")
    @RequestMapping(value = "/policyNameOnly", method = RequestMethod.POST)
    public Result policyNameOnly(String policyName) {
        try {
            if (null == policyName || "".equals(policyName)) {
                return getJsonResult(true, "请传送正确的政策法规名称", "");
            } else {
                //政策法规名称唯一
                Integer isAlive = libraryPolocyRegulationService.queryByPolocyName(policyName);
                if (isAlive != null) {
                    return getJsonResult(true, "该政策法规名称已经存在，请重新输入！", "");
                } else {
                    return getJsonResult(true, "可以使用该 政策法规名称", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(true, "查询失败", "");
        }


    }




    /**
     * 5.修改和出库申请 选择要申请的政法数据列表界面
     *
     * @param current              开始查询的页码数 默认为第1页
     * @param size                 每页的大小  默认每页显示10条数据
     * @param libraryPolocyRegulation 精确查询封装的对象
     * @param search               综合查询
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 11:01
     * @updateTime 2021/3/30 11:01
     */
    @ApiOperation("5-修改和出库申请 选择要申请的政法数据列表界面")
    @RequestMapping(value = "/chooseUpdateOutPolicies", method = RequestMethod.POST)
    public Result chooseUpdateOutPolicies(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody(required = false) LibraryPolocyRegulation libraryPolocyRegulation,
            @RequestParam(value = "search", required = false) String search
    ) {
        try {

            //2.调用数据库中的方法
            PageInfo<LibraryPolocyRegulation> pageInfo = libraryPolocyRegulationService.queryUpdateOutPolicy(current, size, libraryPolocyRegulation, search);
            if (pageInfo.getSize() > 0) {
                //3.返回
                Result result = new Result();
                result.setData(pageInfo);
                return result;
            } else {
                return getJsonResult(true, "无在库法规以供申请", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(true, "查询失败", "");
        }


    }

    /**
     * 6. 显示原有的数据
     * @param idX 政策法规主键id值
     * @param uptType 调整类型（2-入库  3-修改）
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 22:26
     * @updateTime 2021/4/6 22:26
     */

    @ApiOperation("6-显示原有的数据")
    @RequestMapping(value = "/updatePolicyDetail", method = RequestMethod.POST)
    public Result updatePolicyDetail(Integer idX,String uptType) {
        try {
            if (idX == null) {
                return getJsonResult(true, "请传送正确的政策法规主键id值", "");
            } else {
                //1）判断该政策法规的调整类型是不是 3-修改 ==》是，调用服务层拿值；否，直接查询
                LibraryPolocyRegulation libraryPolocyRegulation;
                if("3".equals(uptType)){
                    libraryPolocyRegulation = libraryPolocyRegulationService.queryUpdatePolicy(idX,uptType);

                }else{
                    libraryPolocyRegulation = libraryPolocyRegulationService.selectById(idX);
                    //设置时间
                    //去掉时分秒
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    libraryPolocyRegulation.setReleaseDate(sdf.format(libraryPolocyRegulation.getReleaseTime()).substring(0,sdf.format(libraryPolocyRegulation.getReleaseTime()).lastIndexOf("")));

                    EntityWrapper idXwrapper=new EntityWrapper();
                    idXwrapper.eq("ID_X",idX);
                    List<TLibraryPolocyRegulationAtta> attas=libraryPolocyRegulationAttaService.selectList(idXwrapper);

                    libraryPolocyRegulation.setLibraryPolocyRegulationAttas(attas);
                }
                Result result = new Result();
                result.setData(libraryPolocyRegulation);
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(true, "查询失败", "");
        }


    }


    /**此方法暂时不需要
     * 7.TLibraryPolocyRegulationController 【政策法规库 控制层】
     * 非项目：删除政法文件 （针对于修改/入库被退回可能删除原有的文件)
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 17:34
     * @updateTime 2021/3/26 17:34
     */
    @ApiOperation("7-非项目：删除政法文件 （针对于修改/入库被退回可能删除原有的文件)")
    @RequestMapping(value = "/deletePolicy", method = RequestMethod.GET)
    public Result deletePolicy(Integer idX) {
        try {
            if(idX!=null) {
                //1.查询 前端传过来的数据，看数据表中是否有数据，如果有则进行删除

                EntityWrapper idXwrapper=new EntityWrapper();
                idXwrapper.eq("ID_X",idX);
                List<TLibraryPolocyRegulationAtta> attas=libraryPolocyRegulationAttaService.selectList(idXwrapper);
                if(attas!=null){
                    for(TLibraryPolocyRegulationAtta atta:attas){
                        FileUpLoadUtil.deleteFile(atta.getFilePath());
                    }
                }

                return getJsonResult(true, "政策法规文件删除成功", "");
            }else{
                return getJsonResult(true, "请选择一个政策法规文件进行删除", "");
            }



        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "删除失败", "");
        }
    }


    /**ok
     * 8.选择项目:所有已经完结并且上传了资料的项目都可以选择（显示的是当前登录人的所在的项目，是该项目的成员）
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param proName      根据“子项目名称”搜索
     * @param riskLevel     根据“风险等级”搜索
     * @param search        综合查询的字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 20:52
     * @updateTime 2021/3/30 20:52
     */
    @ApiOperation("8-选择项目")
    @RequestMapping(value = "/chooseProject", method = RequestMethod.GET)
    public Result chooseProject(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "proName", required = false) String proName,
            @RequestParam(value = "riskLevel", required = false) String riskLevel,
            @RequestParam(value = "search", required = false) String search
    ) {
        try {


            //1.将精确查询的字段 封装成对象
            TProPerformanceInfo proPerformanceInfo = new TProPerformanceInfo();
            proPerformanceInfo.setRiskLevel(riskLevel);//风险等级
            proPerformanceInfo.setProName(proName);//子项目名字
            String userId=String.valueOf(getUser().getId());//当前登录人id

            //2.调用服务层的方法拿到值
            PageInfo<TProPerformanceInfo> pageInfo = libraryPolocyRegulationService.chooseProject(current, size, proPerformanceInfo, search, userId);
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
     * 9.入库/修改/出库 暂存/被退回 的删除操作
     *
     * @param idX 要删除的政策法规主键id值
     * @param dataStatus 状态（-1 退回、0 暂存、1 审批中、2 已审批）
     * @param uptType 调整类型（2-入库  3-修改）
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 17:51
     * @updateTime 2021/4/6 17:51
     */
    @ApiOperation("9-入库/修改/出库 暂存/被退回 的删除操作")
    @RequestMapping(value = "/deletePolicyApply", method = RequestMethod.POST)
    public Result deletePolicyApply(Integer idX,String dataStatus,String uptType) {
        try {
            //1）判断状态(DATA_STAUS)是否是 “0-暂存” 或者 “-1 退回”==》是 才可以删除，否 返回提示信息
            if("0".equals(dataStatus)||"-1".equals(dataStatus)){
                libraryPolocyRegulationService.deletePolicyApply(idX,dataStatus,uptType);


            }else{
                return getJsonResult(false, "只有 暂存 和 已退回 的政策法规才可以删除", "");
            }

            return getJsonResult(true, "删除成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false, "删除失败", "");
        }
    }







/*
    @ApiOperation("测试 用户")
    @RequestMapping(value = "/insertChecker", method = RequestMethod.GET)
    public Result insertChecker(){
        List<Integer> users=new ArrayList<>();
        users.add(63);
        users.add(72);
        users.add(65);
        List<RcUser> rcUsers=libraryPolocyRegulationService.insertChecker(null,users);
        Result result=new Result();
        result.setData(rcUsers);
        return result;
    }*/



    /**
     * 批量插入并返回主键
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 20:50
     * @updateTime 2021/4/29 20:50
     */
    @RequestMapping("/test")
    @ApiOperation("-----批量插入")
     public Result test(){
        try {
            Result result=new Result();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return getJsonResult(false,"错误","");
        }
    }















}










































