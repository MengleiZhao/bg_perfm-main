package com.common.business.library.regulations.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.means.mapper.TInformationsMapper;
import com.common.business.library.regulations.entity.*;
import com.common.business.library.regulations.mapper.*;
import com.common.business.library.regulations.service.TLibraryPolocyRegulationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.library.regulations.web.LibraryPolocyRegulationVo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.common.system.util.FileUpLoadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 * TLibraryPolocyRegulationServiceImpl 【政策法规库 服务实现层】
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
@Service
public class TLibraryPolocyRegulationServiceImpl extends ServiceImpl<TLibraryPolocyRegulationMapper, LibraryPolocyRegulation> implements TLibraryPolocyRegulationService {


    @Autowired
    private TLibraryPolocyRegulationMapper libraryPolocyRegulationMapper;//政策法规 mapper
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//主子信息 mapper
    @Autowired
    private TInformationsMapper informationsMapper;//资料 mapper
    @Autowired
    private BussinessFlowLibraryPolocyRegulationMapper bussinessFlowLibraryPolocyRegulationMapper;//政法业务流程 mapper
    @Autowired
    private RcUserMapper userMapper;//用户表 mapper
    @Autowired
    private TLibraryPolocyRegulationUptMapper libraryPolocyRegulationUptMapper;//政策法规库修改申请表 mapper
    @Autowired
    private TLibraryPolocyRegulationCheckRecMapper libraryPolocyRegulationCheckRecMapper;//政策法规审批记录表 mapper
    @Autowired
    private TLibraryPolocyRegulationCheckAttaMapper libraryPolocyRegulationCheckAttaMapper;//政策法规审批附件表 mapper
    @Autowired
    private TLibraryPolocyRegulationAttaMapper libraryPolocyRegulationAttaMapper;//政法附件表 mapper
    @Autowired
    private TLibraryPolocyRegulationUptAttaMapper libraryPolocyRegulationUptAttaMapper;//政法附件修改 mapper




    /**
     * 1.TLibraryPolocyRegulationServiceImpl 【政策法规库 服务实现层】
     * 政策法规 主页面显示
     * @param current                  开始查询的页码数 默认为第1页
     * @param size                     每页的大小  默认每页显示10条数据
     * @param libraryPolocyRegulation  精确查询对象
     * @param search                   综合查询
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 14:14
     * @updateTime 2021/3/26 14:14
     */
    @Override
    public PageInfo<LibraryPolocyRegulation> queryForPage(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search) throws Exception {
        PageHelper.startPage(current,size);
        List<LibraryPolocyRegulation> libraryPolocyRegulations=libraryPolocyRegulationMapper.queryLibPolocyRegs(libraryPolocyRegulation,search);
        //遍历 去掉00：00：00 时分秒
        for(LibraryPolocyRegulation polocy:libraryPolocyRegulations){
            //设置时间
            //去掉时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            polocy.setReleaseDate(sdf.format(polocy.getReleaseTime()).substring(0,sdf.format(polocy.getReleaseTime()).lastIndexOf("")));

            //admRegionProvince  admRegionCity  admRegionCounty
            //设置 行政地区
            String temp="";
            if(StringUtils.isNotEmpty(polocy.getAdmRegionProvince())){
                temp=temp+polocy.getAdmRegionProvince()+"-";
            }
            if(StringUtils.isNotEmpty(polocy.getAdmRegionCity())){
                temp=temp+polocy.getAdmRegionCity()+"-";
            }
            if(StringUtils.isNotEmpty(polocy.getAdmRegionCounty())){
                temp=temp+polocy.getAdmRegionCounty();
            }

            polocy.setAdministrativeRegion(temp);

        }


        return new PageInfo<>(libraryPolocyRegulations);
    }

    /**
     * 2.TLibraryPolocyRegulationServiceImpl 【政策法规库 服务实现层】
     * 根据主键id 查看该法规的详情（text文本）
     * @param idX 政策法规库主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/26 16:14
     * @updateTime 2021/3/26 16:14
     */
    @Override
    public LibraryPolocyRegulation policyDetail(Integer idX) {

        LibraryPolocyRegulation libraryPolocyRegulation=libraryPolocyRegulationMapper.policyDetail(idX);
        //设置 行政地区
        //admRegionProvince  admRegionCity  admRegionCounty
        String temp="";
        if(StringUtils.isNotEmpty(libraryPolocyRegulation.getAdmRegionProvince())){
            temp=temp+libraryPolocyRegulation.getAdmRegionProvince();
        }
        if(StringUtils.isNotEmpty(libraryPolocyRegulation.getAdmRegionCity())){
            temp=temp+libraryPolocyRegulation.getAdmRegionCity();
        }
        if(StringUtils.isNotEmpty(libraryPolocyRegulation.getAdmRegionCounty())){
            temp=temp+libraryPolocyRegulation.getAdmRegionCounty();
        }
        libraryPolocyRegulation.setAdministrativeRegion(temp);

        //设置时间
        //去掉时分秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        libraryPolocyRegulation.setReleaseDate(sdf.format(libraryPolocyRegulation.getReleaseTime()).substring(0,sdf.format(libraryPolocyRegulation.getReleaseTime()).lastIndexOf("")));


        //查询 附件
        EntityWrapper idXwrapper=new EntityWrapper();
        idXwrapper.eq("ID_X",idX);
        List<TLibraryPolocyRegulationAtta> libraryPolocyRegulationAtta=libraryPolocyRegulationAttaMapper.selectList(idXwrapper);

        //设置该法规的附件
        libraryPolocyRegulation.setLibraryPolocyRegulationAttas(libraryPolocyRegulationAtta);
        return libraryPolocyRegulation;
    }

    //数据库更新-->政法数库：入库 [开始]
    /**
     * 1.数据库更新-->政法数库:主页面显示
     * 约束条件：不显示调整状态为出库并且状态为已审批（UPT_TYPE='1' and  DATA_STAUTS='2'）
     * 整体思路：
     *      1）查询调整状态不为出库并且状态不为已审批（UPT_TYPE='1' and  DATA_STAUTS='2'）的政法数据
     *      2）判断查询的结果中，是否有“调整状态为3-修改,状态为0-暂存 或者 1-审批中”的政法数据
     *      3）有==》查询政法修改表中的数据，替换掉原本的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/29 19:51
     * @updateTime 2021/3/29 19:51
     */
    @Override
    public PageInfo<LibraryPolocyRegulation> queryRenewPage(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search) throws Exception{
        PageHelper.startPage(current,size);
        //1）查询调整状态不为出库并且状态不为已审批（UPT_TYPE='1' and  DATA_STAUTS='2'）的政法数据
        List<LibraryPolocyRegulation> libraryPolocyRegulations=libraryPolocyRegulationMapper.queryRenewPage(libraryPolocyRegulation,search);
        //2）判断查询的结果中，是否有“调整状态为3-修改,状态为0-暂存 或者 1-审批中”的政法数据
        for(LibraryPolocyRegulation policy:libraryPolocyRegulations){

            //去掉时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            policy.setReleaseDate(sdf.format(policy.getReleaseTime()).replaceAll(" 00:00:00",""));
            //设置行政地区：admRegionProvince  admRegionCity  admRegionCounty
            String temp="";
            if(StringUtils.isNotEmpty(policy.getAdmRegionProvince())){
                temp=temp+policy.getAdmRegionProvince()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCity())){
                temp=temp+policy.getAdmRegionCity()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCounty())){
                temp=temp+policy.getAdmRegionCounty();
            }

            policy.setAdministrativeRegion(temp);

            //如果是修改
            if("3".equals(policy.getUptType())){
            //3）有==》查询政法修改表中的数据，替换掉原本的数据
                if("0".equals(policy.getDataStauts())||"1".equals(policy.getDataStauts())){
                    TLibraryPolocyRegulationUpt policyUpt=libraryPolocyRegulationUptMapper.queryByIdX(policy.getIdX());
                    //设置返回对象
                    if(policyUpt!=null){
                        policy.setPolocyName(policyUpt.getPolocyName());
                        policy.setDocNumber(policyUpt.getDocNumber());
                        policy.setKeyWords(policyUpt.getKeyWords());
                        policy.setUnitName(policyUpt.getUnitName());
                        policy.setAdministrativeRegion(policyUpt.getAdmRegionProvince()+policyUpt.getAdmRegionCity()+policyUpt.getAdmRegionCounty());
                        policy.setRemark(policyUpt.getRemark());
                        policy.setContent(policyUpt.getContent());
                    }

                }
            }
        }
        return new PageInfo<>(libraryPolocyRegulations);
    }

    /**
     * 2.数据库更新-->政法数库:入库申请：根据政策法规名进行查询 该数据是否存在:政法名唯一
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/29 13:30
     * @updateTime 2021/3/30 17:56
     */
    @Override
    public Integer queryByPolocyName(String policyName) {
        Integer isAlive=libraryPolocyRegulationMapper.queryByPolocyName(policyName);
        return isAlive;
    }

    /**
     * 3.数据库更新-->政法数库:入库/修改申请：向数据库中插入数据
     * 整体思路：
     *      1）设置政法对象
     *      2）根据政法表的主键id得到“原本的政法对象”
     *      3）判断是“2-入库申请”，还是“3-修改申请”
     *      4）“2-入库申请”
     *          4-1.判断 “原本的政法对象”是否为空
     *          4-2.不为空==》则代表有数据，说明是之前入库申请“被退回”或者“暂存”的再一次操作
     *              4-2.1.判断新传递进来的政法对象中file或者idC是否为空
     *                  4-2.1.1.不为空==》说明上传了新的附件
     *                      4-2.1.1.1.根据“原本的政法对象”得到文件路径，调用FileUpLoadUtil工具类 删除原来的文件
     *                  4-2.2.1.为空==》说明没有上传新的附件
     *                      4-2.2.1.1.设置当前新传递过来的政法对象的文件信息为“原本的政法对象”的文件信息
     *              4-2.2.判断是 暂存(0) 还是 提交(1 审批中，提交则代表进入审批)。如果是提交，则设置下级审批人
     *              4-2.3.修改数据至政法数据表中
     *              4-2.4.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
     *          4-3.为空==》则代表无数据，说明是新的入库申请数据
     *              4-3.1.判断是 暂存(0) 还是 提交(1 审批中，提交则代表进入审批)。如果是提交，则设置下级审批人
     *              4-3.2.修改数据至政法数据表中
     *              4-3.3.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
     *      5）“3-修改申请”
     *          5-1.设置政法表数据
     *          5-2.设置政法修改表数据
     *          5-3.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
     *          5-4.修改数据至政法数据表中（修改的字段是5-1设置的）
     *          5-5.修改/新增 数据至政策法规库修改申请表中（修改的字段是5-2设置的）
     *              5-5.1.根据政策法规主键id值，得到原来改政策法规的修改表中的数据 uptIsAlive
     *              5-5.2.判断uptIsAlive是否为空
     *                  5-5.2.1.“原本的政法对象”不为空==》进行修改 数据至政策法规库修改申请表中
     *                      5-5.2.1.1.判断新传递进来的政法对象中file或者idC是否为空
     *                          5-5.2.1.1.1.为空==》说明没有上传新的附件
     *                              5-5.2.1.1.1.1.设置政策法规库修改申请表中的文件信息为“原本的政法对象”的文件信息
     *                          5-5.2.1.1.2.不为空==》说明上传了新的附件
     *                              5-5.2.1.1.2.1.删除政策法规库修改申请表中之前上传的服务器中文件
     *                      5-5.2.1.2.设置政策法规修改表主键值，进行修改政策法规修改表中的数据
     *                  5-5.2.2.“原本的政法对象”为空==》进行新增 数据至政策法规库修改申请表中
     *              5-5.3.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
     *
     *
     * @param policyAttas libraryPolocy 文件对象转换的政法对象-->针对于 数据源是“非项目”
     * @param libraryPolocyRegulation   政法数据库对象-->最终要插入到政法表中的数据
     * @param user                      当前登录用户
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 10:55
     * @updateTime 2021/3/30 10:55
     */
    @Override
    @Transactional
    public void inUpdatePolicy(List<TLibraryPolocyRegulationAtta> policyAttas, LibraryPolocyRegulation libraryPolocyRegulation, ShiroUser user)  {

        if(policyAttas==null){
            policyAttas=new ArrayList<>();
        }

        //1）设置政法对象
        //1-1.设置数据源
        //判断数据来源 是“1-项目” 还是“2-非项目”,非项目设置文件信息
        String source=libraryPolocyRegulation.getDataSources();
        List<String>idCs=new ArrayList<>();
        if(libraryPolocyRegulation.getIdCs()!=null){
           idCs=Arrays.asList(libraryPolocyRegulation.getIdCs().split(Pattern.quote(",")));//资料主键值
        }

        //1-2.设置其他数据
        //申请时间
        if(null==libraryPolocyRegulation.getApplyTime()){
            libraryPolocyRegulation.setApplyTime(new Date());
        }
        //发布时间
        if(null==libraryPolocyRegulation.getReleaseTime()){
            libraryPolocyRegulation.setReleaseTime(new Date());
        }
        //申请人id
        if(null==libraryPolocyRegulation.getApplicantId()){
            libraryPolocyRegulation.setApplicantId(String.valueOf(user.getId()));
        }
        //申请人姓名
        if(null==libraryPolocyRegulation.getApplicantName()){
            libraryPolocyRegulation.setApplicantName(user.getUsername());
        }
        libraryPolocyRegulation.setDataRights("1");//公开

        //2）根据政法表的主键id得到“原本的政法对象”
        LibraryPolocyRegulation isAlive=libraryPolocyRegulationMapper.selectById(libraryPolocyRegulation.getIdX());

        //操作：2-入库  3-修改
        String operation=libraryPolocyRegulation.getUptType();
        //MultipartFile[] files=libraryPolocyRegulation.getFiles();

        //3）判断是“2-入库申请”，还是“3-修改申请”
        //4）“2-入库申请”
        if("2".equals(operation)){

            //4-1.判断 “原本的政法对象”是否为空
            //4-2.不为空==》则代表有数据，说明是之前入库申请“被退回”或者“暂存”的再一次操作
            if(isAlive!=null ){

                //根据idX查询 原来入库时的 政法附件
                EntityWrapper idXwrapper=new EntityWrapper();
                idXwrapper.eq("ID_X",isAlive.getIdX());
                List<TLibraryPolocyRegulationAtta> inAttas=libraryPolocyRegulationAttaMapper.selectList(idXwrapper);
                List<TLibraryPolocyRegulationAtta> lastDeleteAttas=new ArrayList<>();//最终要删除的本地服务的文件

                //前端保留的附件Map
                HashMap<Integer,TLibraryPolocyRegulationAtta> saveAttaMap=new HashMap<>();
                if(libraryPolocyRegulation.getLibraryPolocyRegulationAttas()!=null && libraryPolocyRegulation.getLibraryPolocyRegulationAttas().size()>0){
                    for(TLibraryPolocyRegulationAtta saveAtta:libraryPolocyRegulation.getLibraryPolocyRegulationAttas()){
                        saveAttaMap.put(saveAtta.getIdC(),saveAtta);
                    }
                    //遍历所有的附件，找到删除的附件信息
                    for(TLibraryPolocyRegulationAtta save:inAttas){
                        //该法规中能找到前端保留的文件，则说明，该文件保留，否则，则改文件删除
                        TLibraryPolocyRegulationAtta now=saveAttaMap.get(save.getIdC());
                        if(now==null){
                            lastDeleteAttas.add(now);
                        }
                    }
                }

                //4-2.1.1.1.遍历 要删除的附件集合，进行删除
                if(lastDeleteAttas!=null && lastDeleteAttas.size()>0){
                    for(TLibraryPolocyRegulationAtta deleteAtta:lastDeleteAttas){
                        FileUpLoadUtil.deleteFile(deleteAtta.getFilePath());
                    }
                }

                //先删除所有的政法附件记录，再将前端保留的数据，添加
                libraryPolocyRegulationAttaMapper.delete(idXwrapper);

                //得到前端保留的政法附件
                if(libraryPolocyRegulation.getLibraryPolocyRegulationAttas()!=null && libraryPolocyRegulation.getLibraryPolocyRegulationAttas().size()>0){
                    List<TLibraryPolocyRegulationAtta> saveAttas=libraryPolocyRegulation.getLibraryPolocyRegulationAttas();
                    for(TLibraryPolocyRegulationAtta saveAtta:saveAttas){
                        policyAttas.add(saveAtta);
                    }
                }
                //将上传的新的附件 添加到政法附件表中
                //Tool:3.(insertPolicyAttas) 将上传的新的附件 添加到政法附件表中
                insertPolicyAttas(policyAttas,user,isAlive.getIdX());

                //4-2.2.判断是 暂存(0) 还是 提交(1 审批中，提交则代表进入审批)。如果是提交，则设置下级审批人
                if("1".equals(libraryPolocyRegulation.getDataStauts())){
                    //提交时 要指定当前审批人是谁（自己创建的假数据：项目经理：达叔；对应分所政务咨询部主任(或副主任)：张强）
                    libraryPolocyRegulation.setCurrCheckId("72");
                    libraryPolocyRegulation.setCurrCheckName("李安达");
                }
                //4-2.3.修改数据至政法数据表中
                libraryPolocyRegulationMapper.updateAllColumnById(libraryPolocyRegulation);
                //4-2.4.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
                if("1".equals(libraryPolocyRegulation.getDataStauts())){
                    installChecker(user,libraryPolocyRegulation.getIdX(),72,65);
                }

            }
            //4-3.为空==》则代表无数据，说明是新的入库申请数据
            else{
                //4-3.1.判断是 暂存(0) 还是 提交(1 审批中，提交则代表进入审批)。如果是提交，则设置下级审批人
                if("1".equals(libraryPolocyRegulation.getDataStauts())){
                    //提交时 要指定当前审批人是谁（自己创建的假数据：项目经理：达叔；对应分所政务咨询部主任(或副主任)：张强）
                    libraryPolocyRegulation.setCurrCheckId("72");
                    libraryPolocyRegulation.setCurrCheckName("李安达");
                }
                //4-3.2.修改数据至政法数据表中
                libraryPolocyRegulationMapper.insertBackKey(libraryPolocyRegulation);//此方法会返回新增后的主键id值，以便后续的业务操作

                //将上传的新的附件 添加到政法附件表中
                //Tool:3.(insertPolicyAttas) 将上传的新的附件 添加到政法附件表中
                insertPolicyAttas(policyAttas,user,libraryPolocyRegulation.getIdX());

                //4-3.3.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
                if("1".equals(libraryPolocyRegulation.getDataStauts())){
                    installChecker(user,libraryPolocyRegulation.getIdX(),72,65);
                }
            }

        }
        //5）“3-修改申请”
        else if("3".equals(operation)){

            //5-1.设置政法表数据
            LibraryPolocyRegulation prolicyRe=new LibraryPolocyRegulation();
            prolicyRe.setIdX(libraryPolocyRegulation.getIdX());
            prolicyRe.setDataStauts(libraryPolocyRegulation.getDataStauts());
            prolicyRe.setApplicantId(libraryPolocyRegulation.getApplicantId());
            prolicyRe.setApplicantName(libraryPolocyRegulation.getApplicantName());
            prolicyRe.setApplyTime(libraryPolocyRegulation.getApplyTime());
            prolicyRe.setApplyDesc(libraryPolocyRegulation.getApplyDesc());
            prolicyRe.setUptType(libraryPolocyRegulation.getUptType());
            prolicyRe.setDataSources(libraryPolocyRegulation.getDataSources());
            prolicyRe.setDataStauts(libraryPolocyRegulation.getDataStauts());
            prolicyRe.setApplyTime(libraryPolocyRegulation.getApplyTime());
            prolicyRe.setReleaseTime(libraryPolocyRegulation.getReleaseTime());
            //5-2.设置政法修改表数据
            TLibraryPolocyRegulationUpt prolicyUpt=new TLibraryPolocyRegulationUpt();
            prolicyUpt.setIdX(libraryPolocyRegulation.getIdX());
            prolicyUpt.setPolocyName(libraryPolocyRegulation.getPolocyName());
            prolicyUpt.setDocNumber(libraryPolocyRegulation.getDocNumber());
            prolicyUpt.setUnitName(libraryPolocyRegulation.getUnitName());
            prolicyUpt.setKeyWords(libraryPolocyRegulation.getKeyWords());
            prolicyUpt.setAdministrativeRegion(libraryPolocyRegulation.getAdministrativeRegion());
            prolicyUpt.setRemark(libraryPolocyRegulation.getRemark());
            prolicyUpt.setContent(libraryPolocyRegulation.getContent());



            //5-3.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                //提交时 要指定当前审批人是谁（自己创建的假数据：项目经理：达叔；对应分所政务咨询部主任(或副主任)：马辉）
                prolicyRe.setCurrCheckId("72");
                prolicyRe.setCurrCheckName("李安达");
            }
            //5-4.修改数据至政法数据表中（修改的字段是5-1设置的）
            libraryPolocyRegulationMapper.updateSome(prolicyRe);
            //5-5.修改/新增 数据至政策法规库修改申请表中（修改的字段是5-2设置的）
            //5-5.1.根据政策法规主键id值，得到原来 政策法规的修改表中的数据 uptIsAlive
            TLibraryPolocyRegulationUpt uptIsAlive=libraryPolocyRegulationUptMapper.queryByIdX(libraryPolocyRegulation.getIdX());
            //5-5.2.判断uptIsAlive是否为空
            //5-5.2.1.“政策法规的修改表中的数据”不为空==》删除前端删除的服务器文件、删除修改附件表、新增修改附件表、修改政法申请表
            if(uptIsAlive!=null){

                //根据idX查询 原来修改政法附件中的数据
                EntityWrapper idUwrapper=new EntityWrapper();
                idUwrapper.eq("ID_U",uptIsAlive.getIdU());
                List<TLibraryPolocyRegulationUptAtta> uptAttas=libraryPolocyRegulationUptAttaMapper.selectList(idUwrapper);
                List<TLibraryPolocyRegulationUptAtta> lastDeleteUptAttas=new ArrayList<>();//最终要删除的本地服务的文件

                //前端保留的附件Map
                HashMap<Integer,TLibraryPolocyRegulationUptAtta> saveAttaMap=new HashMap<>();
                if(libraryPolocyRegulation.getLibraryPolocyRegulationUptAttas()!=null && libraryPolocyRegulation.getLibraryPolocyRegulationUptAttas().size()>0){
                    for(TLibraryPolocyRegulationUptAtta saveAtta:libraryPolocyRegulation.getLibraryPolocyRegulationUptAttas()){
                        saveAttaMap.put(saveAtta.getIdUA(),saveAtta);
                    }
                    //遍历所有的附件，找到删除的附件信息
                    for(TLibraryPolocyRegulationUptAtta save:uptAttas){
                        //该法规中能找到前端保留的文件，则说明，该文件保留，否则，则改文件删除
                        TLibraryPolocyRegulationUptAtta now=saveAttaMap.get(save.getIdUA());
                        if(now==null){
                            lastDeleteUptAttas.add(now);
                        }
                    }
                }

                //4-2.1.1.1.遍历 要删除的附件集合，进行删除
                if(lastDeleteUptAttas!=null && lastDeleteUptAttas.size()>0){
                    for(TLibraryPolocyRegulationUptAtta deleteAtta:lastDeleteUptAttas){
                        FileUpLoadUtil.deleteFile(deleteAtta.getFilePath());
                    }
                }

                //先删除所有的政法附件记录，再将前端保留的数据，添加
                libraryPolocyRegulationUptAttaMapper.delete(idUwrapper);

                //得到前端保留的政法附件
                if(libraryPolocyRegulation.getLibraryPolocyRegulationUptAttas()!=null && libraryPolocyRegulation.getLibraryPolocyRegulationUptAttas().size()>0){
                    for(TLibraryPolocyRegulationUptAtta upt:libraryPolocyRegulation.getLibraryPolocyRegulationUptAttas()){
                        TLibraryPolocyRegulationAtta atta=new TLibraryPolocyRegulationAtta();
                        atta.setFileSize(upt.getFileSize());
                        atta.setFileName(upt.getFileName());
                        atta.setFilePath(upt.getFilePath());
                        if(StringUtils.isNotEmpty(upt.getCreateorId())){
                            atta.setCreateorId(upt.getCreateorId());
                        }else{
                            atta.setCreateorId(String.valueOf(user.getId()));
                        }
                        if(StringUtils.isNotEmpty(upt.getCreateor())){
                            atta.setCreateor(upt.getCreateor());
                        }else{
                            atta.setCreateor(user.getName());
                        }
                        if(upt.getCreateTime()!=null){
                            atta.setCreateTime(upt.getCreateTime());
                        }else{
                            atta.setCreateTime(new Date());
                        }
                        policyAttas.add(atta);
                    }

                }
                insertPolicyUptAttas(policyAttas,user,libraryPolocyRegulation,uptIsAlive.getIdU(),libraryPolocyRegulation.getIdX());
                //5-5.2.1.2.设置政策法规修改表主键值，进行修改政策法规修改表中的数据
                 prolicyUpt.setIdU(uptIsAlive.getIdU());//设置主键，以便根据主键值来修改
                libraryPolocyRegulationUptMapper.updateAllColumnById(prolicyUpt);
            }
            //5-5.2.2.“原本的政法对象”为空==》进行新增 数据至政策法规库修改申请表中
            else if(uptIsAlive==null){
                libraryPolocyRegulationUptMapper.insertBackKey(prolicyUpt);
                //Tool:4.(insertPolicyUptAttas) 将上传的新的附件 添加到政法附件修改表中
                insertPolicyUptAttas(policyAttas,user,libraryPolocyRegulation,prolicyUpt.getIdU(),libraryPolocyRegulation.getIdX());
            }

            //5-5.3.如果是 1-提交，调用工具方法 Tool:8.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                //设置业务审批人都是谁：发起人：当前登录人；一级审批人：达叔 72；二级审批人：张强 65
                installChecker(user,libraryPolocyRegulation.getIdX(),72,65);
            }


        }

    }







    /**
     * 4.数据库更新-->政法数库:查询 修改和出库申请 选择要申请的政法数据列表
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 11:07
     * @updateTime 2021/3/30 11:07
     */
    @Override
    public PageInfo<LibraryPolocyRegulation> queryUpdateOutPolicy(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search) {
        PageHelper.startPage(current,size);
        List<LibraryPolocyRegulation> libraryPolocyRegulations=libraryPolocyRegulationMapper.queryUpdateOutPolicy(libraryPolocyRegulation,search);
        for(LibraryPolocyRegulation policy:libraryPolocyRegulations){

            //设置时间
            //去掉时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            policy.setReleaseDate(sdf.format(policy.getReleaseTime()).substring(0,sdf.format(policy.getReleaseTime()).lastIndexOf("")));

            //设置行政地区：admRegionProvince  admRegionCity  admRegionCounty
            String temp="";
            if(StringUtils.isNotEmpty(policy.getAdmRegionProvince())){
                temp=temp+policy.getAdmRegionProvince()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCity())){
                temp=temp+policy.getAdmRegionCity()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCounty())){
                temp=temp+policy.getAdmRegionCounty();
            }

            policy.setAdministrativeRegion(temp);
        }

        return new PageInfo<>(libraryPolocyRegulations);
    }

    /**
     * 5.数据库更新-->政法数库:出库申请 暂存/提交
     * 整体思路：
     *      1）设置要出库的政法对象
     *      2）判断是否是提交，提交时设置下级审批人是谁
     *      3）修改政法表中的数据
     *      4）如果是提交，则业务表中插入数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 19:36
     * @updateTime 2021/3/30 19:36
     */
    @Override
    @Transactional
    public void outPolicy(LibraryPolocyRegulationVo libraryPolocyRegulation, ShiroUser user) {
        //1.设置要 出库 的政法数据库对象
        if("1".equals(libraryPolocyRegulation.getUptType())){

            //申请时间
            if(null==libraryPolocyRegulation.getApplyTime()){
                libraryPolocyRegulation.setApplyTime(new Date());
            }
            //发布时间
            if(null==libraryPolocyRegulation.getReleaseTime()){
                libraryPolocyRegulation.setReleaseTime(new Date());
            }
            //申请人id
            if(null==libraryPolocyRegulation.getApplicantId()){
                libraryPolocyRegulation.setApplicantId(String.valueOf(user.getId()));
            }

            //申请人姓名
            if(null==libraryPolocyRegulation.getApplicantName()){
                libraryPolocyRegulation.setApplicantName(user.getUsername());
            }
            //数据权限 默认共有：1
            libraryPolocyRegulation.setDataRights("1");

            //判断是 暂存(0) 还是 提交(1 审批中，提交则代表进入审批)
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                //提交时 要指定当前审批人是谁（自己创建的假数据：项目经理：达叔；对应分所政务咨询部主任(或副主任)：马辉）
                libraryPolocyRegulation.setCurrCheckId("72");
                libraryPolocyRegulation.setCurrCheckName("李安达");
            }
            libraryPolocyRegulationMapper.updateOutPolicy(libraryPolocyRegulation);//修改操作
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                //设置业务审批人都是谁：发起人：当前登录人；一级审批人：达叔 72；二级审批人：张强 65
                installChecker(user,libraryPolocyRegulation.getIdX(),72,65);
            }

        }
    }

    /**
     * 6.数据库更新-->政法数库:
     * 选择项目:所有已经完结并且上传了资料的项目都可以选择（显示的是当前登录人的所在的项目，是该项目的成员）
     * 约束：去重，显示最新版本的
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/30 21:43
     * @updateTime 2021/3/30 21:43
     */
    @Override
    public PageInfo<TProPerformanceInfo> chooseProject(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search, String userId) {
        PageHelper.startPage(current,size);
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.chooseProject(proPerformanceInfo,search,userId);
        return new PageInfo<>(proPerformanceInfos);
    }

    /**
     * 7.数据库更新-->政法数库:针对于数据来源是项目中已经上传审核资料的数据，通过资料主键idC拿到该数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 9:37
     * @updateTime 2021/3/31 9:37
     */
    @Override
    public List<TInformations> queryInformationByIdC(List<String> idCs) {
        List<TInformations> informations= informationsMapper.selectInformations(idCs,null);
        return informations;
    }

    /**
     * 8.入库/修改/出库 暂存/被退回 的删除操作
     * 整体思路：
     *      1）判断状态(DATA_STAUS)是否是 “0-暂存” 或者 “-1 退回”==》是 才可以删除，否 返回提示信息
     *      2）判断调整类型 是 “2-入库申请” 还是 “3-修改申请” 还是 “1-出库申请”
     *      3）“3-修改申请”
     *              删除政策法规库修改申请表数据
     *      4）“2-入库申请”
     *              删除政策法规数据
     *      5）“3-修改申请” 或者 “1-出库申请”
     *              修改政策法规数据==》都修改成(2-入库 2-已完成)
     *      6）“2-入库申请” 或者 “3-修改申请”
     *              删除本地服务器中目录下的对应文件
     *       7）“2-入库申请” 或者 “3-修改申请” 或者 “1-出库申请”
     *              “-1 退回”
     *                  删除业务数据
     *                  删除审批记录数据
     *                  如果有审批附件数据：删除审批附件表数据
     *                  如果有审批附件：删除本地服务器目录下的审批附件
     *
     *
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 21:06
     * @updateTime 2021/4/6 21:06
     */
    @Override
    @Transactional
    public void deletePolicyApply(Integer idX, String dataStatus, String uptType)throws Exception {

        EntityWrapper policy=new EntityWrapper();//政策法规表住的主键id，构造器
        policy.eq("ID_X",idX);

        LibraryPolocyRegulation libraryPolocyRegulation;//该政策法规的数据

        //2）判断调整类型 是 “2-入库申请” 还是 “3-修改申请” 还是 “1-出库申请”
        //3）“3-修改申请”
        if("3".equals(uptType)){
            //删除政策法规库修改申请表数据
            libraryPolocyRegulationUptMapper.delete(policy);
            //删除修改附件表中的数据
            libraryPolocyRegulationUptAttaMapper.delete(policy);

        }
        //4）“2-入库申请”
        if("2".equals(uptType)){
            //删除政策法规数据
            libraryPolocyRegulationMapper.deleteById(idX);
            //删除 附件表中的数据
            libraryPolocyRegulationAttaMapper.delete(policy);
        }
        //5）“3-修改申请” 或者 “1-出库申请”
        if("3".equals(uptType)||"1".equals(uptType)){
            //修改政策法规数据==》都修改成(2-入库 2-已完成 当前审批人id和审批人姓名为null)
            libraryPolocyRegulationMapper.updateByIdX(idX);

        }
        //6）“2-入库申请” 或者 “3-修改申请”
        if("2".equals(uptType)||"3".equals(uptType)){
            //删除本地服务器中目录下的对应文件
            List<TLibraryPolocyRegulationAtta> attas=libraryPolocyRegulationAttaMapper.selectList(policy);
            if(attas!=null && attas.size()>0){
                for(TLibraryPolocyRegulationAtta atta:attas){
                    FileUpLoadUtil.deleteFile(atta.getFilePath());
                }
            }
        }
        //7）“2-入库申请” 或者 “3-修改申请” 或者 “1-出库申请”
        if("2".equals(uptType)||"3".equals(uptType)||"1".equals(uptType)){
            //“-1 退回”
            if("-1".equals(dataStatus)){
                //删除业务数据
                bussinessFlowLibraryPolocyRegulationMapper.delete(policy);
                //如果有审批附件数据：删除审批附件表数据
                //查询所有的审批记录主键id值
                List<Integer> policyCheckIdBs=libraryPolocyRegulationCheckRecMapper.queryIdBsByIdX(idX);
                //如果有审批附件：删除本地服务器目录下的审批附件
                List<TLibraryPolocyRegulationCheckAtta> checkAttas=libraryPolocyRegulationCheckAttaMapper.queryByIdX(policyCheckIdBs);
                if(checkAttas.size()>0){
                    for(TLibraryPolocyRegulationCheckAtta checkAtta:checkAttas){
                        FileUpLoadUtil.deleteFile(checkAtta.getFilePath());
                    }
                }

                //删除审批数据
                libraryPolocyRegulationCheckRecMapper.delete(policy);


            }
        }

    }
    /**
     * 9.显示原有的数据
     * 整体思路：
     *      1）判断该政策法规的调整类型是不是 3-修改
     * @param idX 要删除的政策法规主键id值
     * @param uptType 调整类型（2-入库  3-修改）
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 22:27
     * @updateTime 2021/4/6 22:27
     */
    @Override
    public LibraryPolocyRegulation queryUpdatePolicy(Integer idX, String uptType) {
        LibraryPolocyRegulation libraryPolocyRegulation=libraryPolocyRegulationMapper.selectById(idX);
        //设置时间
        //去掉时分秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        libraryPolocyRegulation.setReleaseDate(sdf.format(libraryPolocyRegulation.getReleaseTime()).substring(0,sdf.format(libraryPolocyRegulation.getReleaseTime()).lastIndexOf("")));

        TLibraryPolocyRegulationUpt policyUpt;
        if("3".equals(uptType)){
            //查询政策法规库修改申请表 的数据
            policyUpt=libraryPolocyRegulationUptMapper.queryByIdX(idX);
            //设置返回对象
            if(policyUpt!=null){
                libraryPolocyRegulation.setPolocyName(policyUpt.getPolocyName());
                libraryPolocyRegulation.setDocNumber(policyUpt.getDocNumber());
                libraryPolocyRegulation.setKeyWords(policyUpt.getKeyWords());
                libraryPolocyRegulation.setUnitName(policyUpt.getUnitName());
                libraryPolocyRegulation.setAdministrativeRegion(policyUpt.getAdministrativeRegion());
                libraryPolocyRegulation.setRemark(policyUpt.getRemark());
                libraryPolocyRegulation.setContent(policyUpt.getContent());
            }
            EntityWrapper idXwrapper=new EntityWrapper();
            idXwrapper.eq("ID_X",idX);
            List<TLibraryPolocyRegulationUptAtta> uptAttas=libraryPolocyRegulationUptAttaMapper.selectList(idXwrapper);
           libraryPolocyRegulation.setLibraryPolocyRegulationUptAttas(uptAttas);
        }
        return libraryPolocyRegulation;
    }

    /**
     * 10.根据政策法规主键值集合查询政策法规数据集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/19 20:11
     * @updateTime 2021/4/19 20:11
     */
    @Override
    public List<LibraryPolocyRegulation> queryByIdXs(List<String> chooseIdXs) {
        List<Integer> idXs=new ArrayList<>();
        if(chooseIdXs!=null && chooseIdXs.size()>0){
            for(String idX:chooseIdXs){
                idXs.add(Integer.parseInt(idX));
            }
        }
        List<LibraryPolocyRegulation> libraryPolocyRegulations=libraryPolocyRegulationMapper.queryByIdXs(idXs);
        for(LibraryPolocyRegulation policy:libraryPolocyRegulations){
            //设置行政地区：admRegionProvince  admRegionCity  admRegionCounty
            String temp="";
            if(StringUtils.isNotEmpty(policy.getAdmRegionProvince())){
                temp=temp+policy.getAdmRegionProvince()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCity())){
                temp=temp+policy.getAdmRegionCity()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCounty())){
                temp=temp+policy.getAdmRegionCounty();
            }

            policy.setAdministrativeRegion(temp);
        }
        return libraryPolocyRegulations;
    }
    /**
     * 插入 入库附件
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 15:28
     * @updateTime 2021/4/29 15:28
     */
    @Override
    public List<TLibraryPolocyRegulationAtta> inPolicyAtta(List<TLibraryPolocyRegulationAtta> attaList,ShiroUser user) throws Exception {
        if(attaList!=null && attaList.size()>0){
            for(TLibraryPolocyRegulationAtta atta:attaList){
                if(StringUtils.isEmpty(atta.getCreateorId())){
                    atta.setCreateorId(String.valueOf(user.getId()));
                }
                if(StringUtils.isEmpty(atta.getCreateor())){
                    atta.setCreateor(user.getName());
                }
                if(atta.getCreateTime()==null){
                    atta.setCreateTime(new Date());
                }
                libraryPolocyRegulationAttaMapper.insert(atta);
                //atta.setIdC(atta.getIdC());
            }
            //批量插入，并返回主键id值
            //libraryPolocyRegulationAttaMapper.insertBatchesBackKeys(attaList);
        }
        return attaList;
    }

    /**
     * 插入 修改库附件
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 15:29
     * @updateTime 2021/4/29 15:29
     */
    @Override
    public List<TLibraryPolocyRegulationUptAtta> inPolicyUptAtta(List<TLibraryPolocyRegulationUptAtta> uptAttaList,ShiroUser user) throws Exception {
        //批量插入，并返回主键id值
        if(uptAttaList!=null && uptAttaList.size()>0){
            for(TLibraryPolocyRegulationUptAtta uptAtta:uptAttaList){
                if(StringUtils.isEmpty(uptAtta.getCreateorId())){
                    uptAtta.setCreateorId(String.valueOf(user.getId()));
                }
                if(StringUtils.isEmpty(uptAtta.getCreateor())){
                    uptAtta.setCreateor(user.getName());
                }
                if(uptAtta.getCreateTime()==null){
                    uptAtta.setCreateTime(new Date());
                }
                libraryPolocyRegulationUptAttaMapper.insert(uptAtta);
            }
            //批量插入，并返回主键id值
            //uptAttaList=libraryPolocyRegulationUptAttaMapper.insertBatchesBackKeys(uptAttaList);
        }
        return uptAttaList;
    }

    /**
     * 入库申请
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 16:15
     * @updateTime 2021/4/29 16:15
     */
    @Override
    @Transactional
    public void inPolicy(List<TLibraryPolocyRegulationAtta> attas,List<TLibraryPolocyRegulationAtta> delAttas, LibraryPolocyRegulation libraryPolocyRegulation, ShiroUser user) throws Exception {
        Integer idX=libraryPolocyRegulation.getIdX();

        //修改政法表
        //设置政法对象
        if(StringUtils.isEmpty(libraryPolocyRegulation.getDataRights())){
            libraryPolocyRegulation.setDataRights("1");//数据权限 默认 1-公开
        }
        if(StringUtils.isEmpty(libraryPolocyRegulation.getApplicantId())){
            libraryPolocyRegulation.setApplicantId(String.valueOf(user.getId()));
        }
        if(StringUtils.isEmpty(libraryPolocyRegulation.getApplicantName())){
            libraryPolocyRegulation.setApplicantName(user.getName());
        }
        if(libraryPolocyRegulation.getApplyTime()==null){
            libraryPolocyRegulation.setApplyTime(new Date());
        }
        if(libraryPolocyRegulation.getReleaseDate()==null){
            libraryPolocyRegulation.setReleaseTime(new Date());
        }
        //如果是提交设置下级审批人
        if("1".equals(libraryPolocyRegulation.getDataStauts())){
            libraryPolocyRegulation.setCurrCheckId("72");
            libraryPolocyRegulation.setCurrCheckName("李安达");
        }

        //判断政法是否有主键值，有 则说明原来有数据，进行删除后再新增
        if(idX!=null){
            //删除 原来与政法表相关联的附件表、本地服务的文件；删除前端传递的附件表、本地服务的文件
            //得到原来相关联的附件表
            EntityWrapper idXwrapper=new EntityWrapper();
            idXwrapper.eq("ID_X",idX);
            List<TLibraryPolocyRegulationAtta> oldConAttas=libraryPolocyRegulationAttaMapper.selectList(idXwrapper);
            //遍历删除 本地与该政法相关连的文件
            if(oldConAttas!=null && oldConAttas.size()>0){
                for(TLibraryPolocyRegulationAtta deleteAtta:oldConAttas){
                    if(StringUtils.isNotEmpty(deleteAtta.getFilePath())){
                        FileUpLoadUtil.deleteFile(deleteAtta.getFilePath());
                    }
                }
            }
            //批量删除 与该政法相关连的文件
            libraryPolocyRegulationAttaMapper.delete(idXwrapper);

            //删除前端传递的附件表、本地服务的文件
            List<Integer> delIdCs=new ArrayList<>();
            if(delAttas!=null && delAttas.size()>0){
                for(TLibraryPolocyRegulationAtta deleteWebAtta:delAttas){
                    if(StringUtils.isNotEmpty(deleteWebAtta.getFilePath())){
                        FileUpLoadUtil.deleteFile(deleteWebAtta.getFilePath());
                        delIdCs.add(deleteWebAtta.getIdC());
                    }
                }
                //批量删除 前端传递的附件表数据
                libraryPolocyRegulationAttaMapper.deleteBatchIds(delIdCs);
            }
            //删除审批流
            bussinessFlowLibraryPolocyRegulationMapper.delete(idXwrapper);
            //修改政法表
            libraryPolocyRegulationMapper.updateAllColumnById(libraryPolocyRegulation);
            //新增 附件表
            //设置附件表相关连的政法表主键值
            for(TLibraryPolocyRegulationAtta atta:attas){
                atta.setIdX(libraryPolocyRegulation.getIdX());
                if(StringUtils.isEmpty(atta.getCreateorId())){
                    atta.setCreateorId(String.valueOf(user.getId()));
                }
                if(StringUtils.isEmpty(atta.getCreateor())){
                    atta.setCreateor(user.getName());
                }
                if(atta.getCreateTime()==null){
                    atta.setCreateTime(new Date());
                }
            }
            //批量新增
            libraryPolocyRegulationAttaMapper.insertBatches(attas);

            //新增 审批流
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                installChecker(user,idX,72,65);
            }
        }
        //没有 直接新增:新增附件、新增审批流、新增政法
        else if(idX==null){
            //新增政法
            libraryPolocyRegulationMapper.insertBackKey(libraryPolocyRegulation);
            //新增 附件表
            //设置附件表相关连的政法表主键值
            for(TLibraryPolocyRegulationAtta atta:attas){
                atta.setIdX(libraryPolocyRegulation.getIdX());
                if(StringUtils.isEmpty(atta.getCreateorId())){
                    atta.setCreateorId(String.valueOf(user.getId()));
                }
                if(StringUtils.isEmpty(atta.getCreateor())){
                    atta.setCreateor(user.getName());
                }
                if(atta.getCreateTime()==null){
                    atta.setCreateTime(new Date());
                }
            }
            //批量新增附件
            libraryPolocyRegulationAttaMapper.insertBatches(attas);

            //新增 审批流
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                installChecker(user,idX,72,65);
            }
        }
    }

    /**
     * 修改库申请
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 16:15
     * @updateTime 2021/4/29 16:15
     */
    @Override
    @Transactional
    public void uptPolicy(List<TLibraryPolocyRegulationUptAtta> uptAttas,List<TLibraryPolocyRegulationUptAtta> delUptAttas, LibraryPolocyRegulation libraryPolocyRegulation, ShiroUser user) throws Exception {
        Integer idX=libraryPolocyRegulation.getIdX();

        //修改政法表
        //设置政法对象
        //5-1.设置政法表数据
        LibraryPolocyRegulation prolicy=new LibraryPolocyRegulation();
        prolicy.setIdX(libraryPolocyRegulation.getIdX());
        prolicy.setDataStauts(libraryPolocyRegulation.getDataStauts());
        prolicy.setApplicantId(libraryPolocyRegulation.getApplicantId());
        prolicy.setApplicantName(libraryPolocyRegulation.getApplicantName());
        prolicy.setApplyTime(libraryPolocyRegulation.getApplyTime());
        prolicy.setApplyDesc(libraryPolocyRegulation.getApplyDesc());
        prolicy.setUptType(libraryPolocyRegulation.getUptType());
        prolicy.setDataSources(libraryPolocyRegulation.getDataSources());
        prolicy.setDataStauts(libraryPolocyRegulation.getDataStauts());
        prolicy.setApplyTime(libraryPolocyRegulation.getApplyTime());
        prolicy.setReleaseTime(libraryPolocyRegulation.getReleaseTime());
        //5-2.设置政法修改表数据
        TLibraryPolocyRegulationUpt prolicyUpt=new TLibraryPolocyRegulationUpt();
        prolicyUpt.setIdX(libraryPolocyRegulation.getIdX());
        prolicyUpt.setPolocyName(libraryPolocyRegulation.getPolocyName());
        prolicyUpt.setDocNumber(libraryPolocyRegulation.getDocNumber());
        prolicyUpt.setUnitName(libraryPolocyRegulation.getUnitName());
        prolicyUpt.setKeyWords(libraryPolocyRegulation.getKeyWords());
        prolicyUpt.setAdministrativeRegion(libraryPolocyRegulation.getAdministrativeRegion());
        prolicyUpt.setRemark(libraryPolocyRegulation.getRemark());
        prolicyUpt.setContent(libraryPolocyRegulation.getContent());

        //根据idX查找 修改表中是否有数据，如果有，则删除后再新增
        //删除 原来与政法修改表相关联的附件表、本地服务的文件；删除前端传递的附件表、本地服务的文件
        TLibraryPolocyRegulationUpt libraryUpt=libraryPolocyRegulationUptMapper.queryByIdX(idX);
        if(libraryUpt!=null){
            //删除 原来与政法修改表相关联的附件表、本地服务的文件
            //得到原来相关联的附件表
            EntityWrapper idUwrapper=new EntityWrapper();
            idUwrapper.eq("ID_U",libraryUpt.getIdU());
            List<TLibraryPolocyRegulationUptAtta> oldUptConAttas=libraryPolocyRegulationUptAttaMapper.selectList(idUwrapper);
            //遍历删除 本地与该政法相关连的文件
            if(oldUptConAttas!=null && oldUptConAttas.size()>0){
                for(TLibraryPolocyRegulationUptAtta deleteUptAtta:oldUptConAttas){
                    if(StringUtils.isNotEmpty(deleteUptAtta.getFilePath())){
                        FileUpLoadUtil.deleteFile(deleteUptAtta.getFilePath());
                    }
                }
            }
            //批量删除 与该政法修改表相关连的文件
            libraryPolocyRegulationUptAttaMapper.delete(idUwrapper);

            //删除前端传递的附件表、本地服务的文件
            List<Integer> delIdUAs=new ArrayList<>();
            if(delIdUAs!=null && delIdUAs.size()>0){
                for(TLibraryPolocyRegulationUptAtta deleteWebUptAtta:delUptAttas){
                    if(StringUtils.isNotEmpty(deleteWebUptAtta.getFilePath())){
                        FileUpLoadUtil.deleteFile(deleteWebUptAtta.getFilePath());
                        delIdUAs.add(deleteWebUptAtta.getIdUA());
                    }
                }
                //批量删除 前端传递的附件表数据
                libraryPolocyRegulationUptAttaMapper.deleteBatchIds(delIdUAs);
            }
            //删除审批流
            EntityWrapper idXwrapper=new EntityWrapper();
            idXwrapper.eq("ID_X",idX);
            bussinessFlowLibraryPolocyRegulationMapper.delete(idXwrapper);

            //修改政法表
            libraryPolocyRegulationMapper.updateSome(prolicy);
            //修改政法修改表
            prolicyUpt.setIdU(libraryUpt.getIdU());
            libraryPolocyRegulationUptMapper.updateAllColumnById(prolicyUpt);

            //批量新增前端保留的附件表
            for(TLibraryPolocyRegulationUptAtta uptAtta:uptAttas){
                uptAtta.setIdU(libraryUpt.getIdU());
                if(StringUtils.isEmpty(uptAtta.getCreateorId())){
                    uptAtta.setCreateorId(String.valueOf(user.getId()));
                }
                if(StringUtils.isEmpty(uptAtta.getCreateor())){
                    uptAtta.setCreateor(user.getName());
                }
                if(uptAtta.getCreateTime()==null){
                    uptAtta.setCreateTime(new Date());
                }
            }
            libraryPolocyRegulationUptAttaMapper.insertBatches(uptAttas);

            //新增 审批流
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                installChecker(user,idX,72,65);
            }


        }
        //没有直接新增
        else{
            //新增政法修改表
            libraryPolocyRegulationUptMapper.insertBackKey(prolicyUpt);
            //新增 附件表
            //批量新增前端保留的附件表
            for(TLibraryPolocyRegulationUptAtta uptAtta:uptAttas){
                uptAtta.setIdU(prolicyUpt.getIdU());
                if(StringUtils.isEmpty(uptAtta.getCreateorId())){
                    uptAtta.setCreateorId(String.valueOf(user.getId()));
                }
                if(StringUtils.isEmpty(uptAtta.getCreateor())){
                    uptAtta.setCreateor(user.getName());
                }
                if(uptAtta.getCreateTime()==null){
                    uptAtta.setCreateTime(new Date());
                }
            }
            //批量新增附件
            libraryPolocyRegulationUptAttaMapper.insertBatches(uptAttas);

            //新增 审批流
            if("1".equals(libraryPolocyRegulation.getDataStauts())){
                installChecker(user,idX,72,65);
            }
        }
    }

    /**
     * Tool:1.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
     * 整体思路：
     *  1) 将审批人的id顺序添加到一个集合中(目的是，根据大小，往政法业务流程审批表中进行添加几条数据[其实用到的仅仅是大小])
     *  2）查询原来的政法审批idX是否有审批流业务数据 ==》isAlive
     *  3）查询审批流业务数据的顺序是否是按照传递过来的顺序指定的 ==》List<Integer> isCherkerAlive
     *  4）根据2）和3）进行判断：
     *      4-1）isAlive>0 && isCherkerAlive.size()==3 说明原来有审批流数据，且审批顺序与现在的一致==》进行部分修改
     *      4-2）isAlive>0&&isCherkerAlive.size()!=3 说明原来有审批流数据，但审批顺序与现在的不一致==》删除数据，再添加数据
     *      4-3）isAlive==0 说明原来没有审批数据，直接添加数据
     *   注：4-1）与4-2）的情况针对于（申请被驳回），4-3）才是真正新的发起一条新的申请
     * @param user      政法申请人（当前登录人，也是发起人）
     * @param idX       要设置的政法申请表的id主键
     * @param firstId   一级审批人id值
     * @param secondId  二级审批人id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/1 10:41
     * @updateTime 2021/4/1 10:41
     */
    @Transactional
   public void installChecker(ShiroUser user,Integer idX,Integer firstId,Integer secondId){
       //1.审批人id顺序添加到集合中
       List<Integer> ids=new ArrayList<>();
       ids.add(user.getId());//发起人：当前登录对象id
       ids.add(firstId);//一级审批人：达叔
       ids.add(secondId);//二级审批人：张强
         Integer [] lastIds={idX,firstId,secondId};


       //2.判断原先是否有发起人、一级审批人、二级审批人，如果有，则进行修改，否则直接插入
       //查询原来的政法审批idX是否有审批流业务数据
       Integer isAlive=bussinessFlowLibraryPolocyRegulationMapper.queryAliveByIdX(idX);
       //查询审批流业务数据的顺序是否是按照传递过来的顺序指定的（返回的是按照审批节点顺序1，2，3来排序后的主键id值）
       List<Integer> isCherkerAlive=bussinessFlowLibraryPolocyRegulationMapper.queryCherkerAlive(idX,user.getId(),firstId,secondId);
       if(isAlive>0 && isCherkerAlive.size()==3){
           //说明原来的审批顺序跟现在的审批顺序相同：
           //修改：是否活跃(NODE_IS_ACTIVE)、修改人(UPDATEOR)、修改时间(UPDATE_TIME)、当前节点状态(CURRENT_NODE_STATUS)
           int i=1;
           BussinessFlowLibraryPolocyRegulation flowLibraryPolicy=new BussinessFlowLibraryPolocyRegulation();
           //修改人是第一个人（发起人）
           flowLibraryPolicy.setUpdateor(user.getName());
           for(Integer userId:ids){
               //第一个人是发起人是不活跃的，一级审批人是活跃的，后面的人都是不活跃的
               if(2==i){
                   flowLibraryPolicy.setNodeIsActive("1");//活跃
               }else{
                   flowLibraryPolicy.setNodeIsActive("0");//不活跃
               }
               flowLibraryPolicy.setUpdateTime(new Date());//修改时间
               //当前节点状态
               //发起人的节点状态是 已完成 1；其他人都是：未完成 0
               if(1==i){
                   flowLibraryPolicy.setCurrentNodeStatus("1");
               }else{
                   flowLibraryPolicy.setCurrentNodeStatus("0");
               }
               bussinessFlowLibraryPolocyRegulationMapper.updateSome(flowLibraryPolicy,isCherkerAlive.get(i-1));
               i++;
           }

       }else if(isAlive>0&&isCherkerAlive.size()!=3){
           //说明审批顺序变更：删除原来的数据，重新增加
           //删除
           Map deleteByIdX=new HashMap();
           deleteByIdX.put("ID_X",idX);
           bussinessFlowLibraryPolocyRegulationMapper.deleteByMap(deleteByIdX);
           //新增
           insertChecker(idX,user,firstId,secondId);
       }else if(isAlive==0){
           //说明原来没有该申请法规的审批顺序：直接新增
           insertChecker(idX,user,firstId,secondId);
       }


   }

   /**
    * Tool:2.(insertChecker)新增审批流业务表数据：发起人、一级审批人、二级审批人
    * 整体思路：
    *       1）将要审批的人添加到数组中，目的（为了防止审批人重复，用list不能会覆盖掉重复的）
    *       2）遍历数组（目的是添加三次）
    *       3）从用户表中查询用户数据
    *       4）将用户信息对象转换成业务信息对象，添加到业务表中
    * @param idX 对应的是哪一个
    * @return
    * @author 田鑫艳
    * @createTime 2021/4/1 16:40
    * @updateTime 2021/4/1 16:40
    */
   @Transactional
   public void insertChecker(Integer idX,ShiroUser nowUser,Integer firstId,Integer secondId) {

       Integer[] userIds = {nowUser.getId(), firstId, secondId};
       for (int i = 0; i < userIds.length; i++) {
           RcUser user = userMapper.selectById(userIds[i]);
           BussinessFlowLibraryPolocyRegulation flowLibraryPolicy = new BussinessFlowLibraryPolocyRegulation();
           flowLibraryPolicy.setIdX(idX);//对应的哪个政法申请
           flowLibraryPolicy.setCheckUserId(String.valueOf(user.getId()));//审批人id
           flowLibraryPolicy.setCheckUser(user.getName());//审批人姓名
           flowLibraryPolicy.setCheckUserDeptId(user.getDeptId());//审批人部门id
           flowLibraryPolicy.setCheckUserDeptName(user.getDeptName());//审批人部门名称
           flowLibraryPolicy.setOrderOfCurrentNode(i+1);//当前节点审批顺序
           //第一个人是发起人所以是不活跃的，一级审批人是活跃的，后面的人都是不活跃的
           if (1 == i) {
               flowLibraryPolicy.setNodeIsActive("1");//活跃
           } else {
               flowLibraryPolicy.setNodeIsActive("0");//不活跃
           }
           flowLibraryPolicy.setCreateor(nowUser.getName());//创建人
           flowLibraryPolicy.setCreateTime(new Date());//创建时间
           flowLibraryPolicy.setCheckUserJobNumber(user.getGroupMemberCode());//审批人工号==groupMemberCode员工编号
           //当前节点状态
           //发起人的节点状态是 已完成 1；其他人都是：未完成 0
           if (0 == i) {
               flowLibraryPolicy.setCurrentNodeStatus(String.valueOf(1));
           } else {
               flowLibraryPolicy.setCurrentNodeStatus(String.valueOf(0));
           }
           bussinessFlowLibraryPolocyRegulationMapper.insert(flowLibraryPolicy);
       }

   }


    /**
     * Tool:3.(insertPolicyAttas) 将上传的新的附件 添加到政法附件表中
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 11:00
     * @updateTime 2021/4/28 11:00
     */
    public void insertPolicyAttas(List<TLibraryPolocyRegulationAtta> policyAttas,ShiroUser user,Integer idX){
        if(policyAttas!=null && policyAttas.size()>0){
            //遍历 设置政法附件表对象
            for(TLibraryPolocyRegulationAtta atta:policyAttas){
                //对应的政法主键值
                atta.setIdX(idX);

                //上传人id
                if(null==atta.getCreateorId()||"".equals(atta.getCreateorId())){
                    atta.setCreateorId(String.valueOf(user.getId()));
                }
                //上传人姓名
                if(null==atta.getCreateor()||"".equals(atta.getCreateor())){
                    atta.setCreateor(user.getName());
                }
                //上传时间
                if(null==atta.getCreateTime()){
                    atta.setCreateTime(new Date());
                }
            }
            //批量插入
            libraryPolocyRegulationAttaMapper.insertBatches(policyAttas);
        }

    }

    /**
     *Tool:4.(insertPolicyUptAttas) 将上传的新的附件 添加到政法附件修改表中
     * @param  libraryPolocyRegulation
     * @return  deleteAttaIdcs
     * @author 田鑫艳
     * @createTime 2021/4/28 11:26
     * @updateTime 2021/4/28 11:26
     */
    private void insertPolicyUptAttas(List<TLibraryPolocyRegulationAtta> policyAttas, ShiroUser user, LibraryPolocyRegulation libraryPolocyRegulation,Integer idU,Integer idX) {
        List<TLibraryPolocyRegulationUptAtta> uptAttas=new ArrayList<>();
        //遍历 设置政法附件表对象
        for(TLibraryPolocyRegulationAtta atta:policyAttas){
            TLibraryPolocyRegulationUptAtta uptAtta=new TLibraryPolocyRegulationUptAtta();
            //修改表主键
            uptAtta.setIdU(idU);
            //上传人id
            if(null==atta.getCreateorId()||"".equals(atta.getCreateorId())){
                uptAtta.setCreateorId(String.valueOf(user.getId()));
            }else{
                uptAtta.setCreateorId(atta.getCreateorId());
            }
            //上传人姓名
            if(null==atta.getCreateor()||"".equals(atta.getCreateor())){
                uptAtta.setCreateor(user.getName());
            }else{
                uptAtta.setCreateor(atta.getCreateor());
            }
            //上传时间
            if(null==atta.getCreateTime()){
                uptAtta.setCreateTime(new Date());
            }else{
                uptAtta.setCreateTime(atta.getCreateTime());
            }
            uptAttas.add(uptAtta);
        }

        //查询还剩下的政法附件
        List<TLibraryPolocyRegulationAtta> liveAttas=libraryPolocyRegulation.getLibraryPolocyRegulationAttas();
        if(liveAttas!=null && liveAttas.size()>0){
            //遍历转换
            for(TLibraryPolocyRegulationAtta liveAtta:liveAttas){
                TLibraryPolocyRegulationUptAtta uptAtta=new TLibraryPolocyRegulationUptAtta();
                uptAtta.setIdU(idU);
                uptAtta.setFileName(liveAtta.getFileName());
                uptAtta.setFilePath(liveAtta.getFilePath());
                uptAtta.setFileSize(liveAtta.getFileSize());
                uptAtta.setCreateorId(liveAtta.getCreateorId());
                uptAtta.setCreateor(liveAtta.getCreateor());
                uptAtta.setCreateTime(liveAtta.getCreateTime());
                uptAttas.add(uptAtta);
            }
        }


        //查询还剩下的政法修改附件
        List<TLibraryPolocyRegulationUptAtta> liveUptAttas=libraryPolocyRegulation.getLibraryPolocyRegulationUptAttas();
        if(liveUptAttas!=null && liveUptAttas.size()>0){
            uptAttas.addAll(liveUptAttas);
        }

       /* //查询原来的政法文件（去除前端删除的文件）
        String deleteAttaIdcs=libraryPolocyRegulation.getDeleteAttaIdcs();
        List<String> deleteIdcList= Arrays.asList(deleteAttaIdcs.split(Pattern.quote(",")));
        List<TLibraryPolocyRegulationAtta> attas=libraryPolocyRegulationAttaMapper.queryAttas(idX,deleteIdcList);
        //将还保留的政法附件 插入到要修改的政法文件中
        if(attas!=null&&attas.size()>0){
            for(TLibraryPolocyRegulationAtta atta:attas){
                TLibraryPolocyRegulationUptAtta uptAtta=new TLibraryPolocyRegulationUptAtta();
                uptAtta.setIdU(idU);
                uptAtta.setFileName(atta.getFileName());
                uptAtta.setFilePath(atta.getFilePath());
                uptAtta.setFileSize(atta.getFileSize());
                uptAtta.setCreateorId(atta.getCreateorId());
                uptAtta.setCreateor(atta.getCreateor());
                uptAtta.setCreateTime(atta.getCreateTime());
                uptAttas.add(uptAtta);
            }
        }

        //将还保留的政法修改附件 插入到要修改的政法文件中
        String deleteUptAttaIduas=libraryPolocyRegulation.getDeleteUptAttaIduas();
        List<String> deleteUptAttaIduaList=Arrays.asList(deleteUptAttaIduas.split(Pattern.quote(",")));
        List<TLibraryPolocyRegulationUptAtta> aliveUptAttas=libraryPolocyRegulationUptAttaMapper.queryUptAttas(deleteUptAttaIduaList);
        uptAttas.addAll(aliveUptAttas);*/

        //批量插入
        libraryPolocyRegulationUptAttaMapper.insertBatches(uptAttas);
    }


}
