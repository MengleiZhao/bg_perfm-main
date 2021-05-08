package com.common.business.workgroup.expert.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.library.experts.entity.TLibraryPerformanceExpert;
import com.common.business.library.experts.mapper.TLibraryPerformanceExpertMapper;
import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TEvalUnitInfoMapper;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.business.workgroup.establish.web.TPerformanceWorkingGroupVo;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import com.common.business.workgroup.expert.mapper.TPerformanceExpertGroupInfoMapper;
import com.common.business.workgroup.expert.service.TPerformanceExpertGroupInfoService;
import com.common.business.workgroup.expert.web.TPerformanceExpertGroupInfoVo;
import com.common.system.shiro.ShiroKit;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcRole;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.entity.RcUserRole;
import com.common.system.sys.mapper.RcRoleMapper;
import com.common.system.sys.mapper.RcUserMapper;
import com.common.system.sys.mapper.RcUserRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-09
 */
@Service
public class TPerformanceExpertGroupInfoServiceImpl extends ServiceImpl<TPerformanceExpertGroupInfoMapper, TPerformanceExpertGroupInfo> implements TPerformanceExpertGroupInfoService {

    @Autowired
    private TPerformanceExpertGroupInfoMapper performanceExpertGroupInfoMapper;//组建专家 mapper接口
    @Autowired
    private TLibraryPerformanceExpertMapper libraryPerformanceExpertMapper;//专家库 mapper接口
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//项目主子表 mapper接口
    @Autowired
    private TEvalUnitInfoMapper evalUnitInfoMapper;//被评单位  mapper接口
    @Autowired
    private TPerformanceWorkingGroupMapper performanceWorkingGroupMapper;//绩效工作组 mapper接口
    @Autowired
    private RcUserMapper rcUserMapper;//用户表 mapper接口
    @Autowired
    private RcRoleMapper rcRoleMapper;//角色表 mapper接口
    @Autowired
    private RcUserRoleMapper rcUserRoleMapper;//用户角色表 mapper接口

    /**
     * 1.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 分页查询数据
     * @param pageNum 开始的页码数
     * @param pageSize 每页的大小
     * @param search  综合查询的字段
     * @param proPerformanceInfo  封装成对象的搜素字段
     * @return PageInfo<TProPerformanceInfo>
     * @author 田鑫艳
     * @createTime 2021/3/8 17:02
     * @updateTime 2021/3/16 14:09
     */
    @Override
    public PageInfo<TProPerformanceInfo> queryForPage(Integer pageNum, Integer pageSize,TProPerformanceInfo proPerformanceInfo,String search,String proManagerId) throws Exception{
        //1.拿到数据
        PageHelper.startPage(pageNum, pageSize);
        List<TProPerformanceInfo> showDatas = proPerformanceInfoMapper.queryExceptTPerformanceInfo(search,null,proPerformanceInfo,proManagerId);//接收的是TProPerformanceInfo类型的列表
        //2.拿到的数据集合不为空时，将最晚时间添加进去
        if(showDatas.size()!=0){
            for(TProPerformanceInfo tpr:showDatas){
                Date expertTime=performanceExpertGroupInfoMapper.selectExpertTime(tpr.getIdA());//组建专家组的最晚时间
                tpr.setWorkCreateTime(expertTime);//组建专家组的时间
            }
            System.out.println("组建工作组-->组建专家组--服务实现层 分页显示数据 从后端拿到的集合对象："+showDatas.size());
        }


        return new PageInfo<TProPerformanceInfo>(showDatas);//将list列表 转换成PageInfo的形式并返回

    }
    /**
     * 2.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 查询  项目信息-->主子项目信息集合
     * @param idA TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return List<TProPerformanceInfo> TProPerformanceInfo类型的集合
     * @author 田鑫艳
     * @createTime 2021/3/8 22:24
     * @updateTime 2021/3/8 22:24
     */
    @Override
    public TProPerformanceInfo selectProjectInfo(Integer idA) throws Exception{
        //1.通过“主项目编码”查询所有的项目信息 主子项目表
        TProPerformanceInfo proPerformanceInfo=proPerformanceInfoMapper.selectProjectMainInfo(idA);
        //被评单位
        List<TEvalUnitInfo> evalUnitInfos=evalUnitInfoMapper.selectExceptEvaluatedInfo(idA);
        proPerformanceInfo.setEvalUnitInfos(evalUnitInfos);
        return proPerformanceInfo;
    }

    /**
     * 3.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 查询  项目信息-->被评单位
     * @param idA 主键id值
     * @return List<TEvalUnitInfo>
     * @author 田鑫艳
     * @createTime 2021/3/11 13:45
     * @updateTime 2021/3/11 13:45
     */
    @Override
    public List<TEvalUnitInfo> selectPEvaluatedInfo(Integer idA) throws Exception{
        //遍历“idA”主键，查找 主/子 项目的被评单位信息 (主子项目的被评单位是一样的，只不过数据库里每一个子项目都会有几个被评单位)
        List<TEvalUnitInfo> evalUnitInfos=evalUnitInfoMapper.selectExceptEvaluatedInfo(idA);
        return evalUnitInfos;
    }

    /**
     * 4.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 查询-->每一个 主/子 项目的 组员信息
     * @param parentProCode TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return List<TPerformanceWorkingGroup> 组员信息集合
     * @author 田鑫艳
     * @createTime 2021/3/9 15:31
     * @updateTime 2021/3/9 15:31
     */
    @Override
    public List<TProPerformanceInfo> selectWorkGroupInfo(String parentProCode,ShiroUser user) throws Exception{
        //1.通过“主项目编码”查询所有的项目信息
        List<TProPerformanceInfo> tProPerformanceInfos=proPerformanceInfoMapper.selectExceptProjectInfo(parentProCode,user.getId(),null);

        if(tProPerformanceInfos.size()>0){
            //2.通过遍历“idA”主键，查找每一个 主/子 项目 的组员信息(集合)
            for(TProPerformanceInfo tpr:tProPerformanceInfos){
                //查询组员信息
                List<TPerformanceWorkingGroup> tPerformanceWorkingGroups=performanceWorkingGroupMapper.selectExceptMembers(tpr.getIdA());
                if(tPerformanceWorkingGroups.size()>0){
                    tpr.setPerformanceWorkingGroups(tPerformanceWorkingGroups);//设置 组员信息 (用绩效工作组封装)
                    System.out.println("组员信息："+tpr.getPerformanceWorkingGroups());
                }

            }
        }


        return tProPerformanceInfos;
    }

    /**
     * 5.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 点击“详情” 按钮 查询-->专家组信息
     * @param parentProCode TProPerformanceInfo实体类中parentProCode属性，即：主项目编号
     * @return List<TPerformanceExpertGroupInfo> TPerformanceExpertGroupInfo类型的集合
     * @author 田鑫艳
     * @createTime 2021/3/9 9:51
     * @updateTime 2021/3/9 9:51
     */
    @Override
    public List<TProPerformanceInfo> selectExpertGroup(String parentProCode,ShiroUser user) throws Exception {
        //1.通过“主项目编码”查询所有的项目信息 主要是得到所有的idA主键
        List<TProPerformanceInfo> tProPerformanceInfos=proPerformanceInfoMapper.selectExceptProjectInfo(parentProCode,user.getId(),1);

        if(tProPerformanceInfos.size()>0){
            //2.通过遍历“idA”主键，查找每一个 主/子 项目的专家组信息
            for(TProPerformanceInfo tpr:tProPerformanceInfos){
                List<TPerformanceExpertGroupInfo> performanceExpertGroupInfos=performanceExpertGroupInfoMapper.selectExpertGroup(tpr.getIdA());

                //将专家权限字符串转换为数组
                for(TPerformanceExpertGroupInfo expert:performanceExpertGroupInfos){
                    expert.setAutorityExpets(Arrays.asList(expert.getAuthorityOfExperts().split(Pattern.quote("|"))));
                    System.out.println(expert.getAutorityExpets().toString());
                }


                if(performanceExpertGroupInfos.size()>0){
                    tpr.setPerformanceExpertGroupInfos(performanceExpertGroupInfos);
                    System.out.println("专家信息；"+tpr.getPerformanceExpertGroupInfos());
                }

            }
        }

        return tProPerformanceInfos;
    }





    /**
     *6.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 分页查询该项目经理下的未组建专家组 的信息
     * @param pageNum 开始查询的页码数 默认为第1页
     * @param pageSize 每页的大小  默认每页显示10条数据
     * @param proManagerId ProPerformanceInfo实体类中proManagerId属性，即：项目经理的id
     * @return List<TProPerformanceInfo> 得到是所有未组建专家组的集合数据
     * @author 田鑫艳
     * @createTime 2021/3/10 9:26
     * @updateTime 2021/3/10 9:26
     */
    @Override
    public PageInfo<TProPerformanceInfo> selectNoExcepertGroupInfo(Integer pageNum, Integer pageSize, String proManagerId) throws Exception{
        PageHelper.startPage(pageNum, pageSize);
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.selectNoExcepertGroupInfo(proManagerId);
        System.out.println("组建工作组-->组建专家组  服务实现层,分页拿到 该项目经理下的未组建专家组 的信息："+proPerformanceInfos.size());
        return new PageInfo<>(proPerformanceInfos);
    }

    /**
     * 7.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 分页显示专家库中的专家数据
     * @param pageNum                     开始页(即：第几页)
     * @param pageSize                   每页的大小(即：每页的size)
     * @param tLibraryPerformanceExpert  精确的查询的字段
     * @param search                     综合查询字段
     * @param expertVo                   该项目中的idA和前端已经删除的专家信息
     * @return List<TLibraryPerformanceExpert> 返回的是所有在库且专家状态为正常的专家数据信息集合
     * @author 田鑫艳
     * @createTime 2021/3/10 15:03
     * @updateTime 2021/3/15 13:47
     */
    @Override
    public PageInfo<TLibraryPerformanceExpert> selectSetUpShowExpertGroups(Integer pageNum, Integer pageSize, TLibraryPerformanceExpert tLibraryPerformanceExpert, String search, TPerformanceExpertGroupInfoVo expertVo) throws Exception{

        //2.要排除的专家编号 集合
        List<String> notIncludedExpCodes=new ArrayList<>();

        //3.该idA子项目下的原来的专家数据-->专家编号
        List<String> oldExpertCodes=performanceExpertGroupInfoMapper.selectExceptCode(expertVo.getIdA());

        //前端还剩下的专家集合
        List<TPerformanceExpertGroupInfo> chooseList=expertVo.getChooseList();
        if(chooseList!=null && chooseList.size()>0){
            for(TPerformanceExpertGroupInfo choose:chooseList){
                notIncludedExpCodes.add(choose.getExpCode());
            }
        }else{
            //4.前端选择时 删除的专家编号字符串不为空，要排除的专家编号 集合 =原来该项目下存在的专家编号-前端删除的专家编号
            if(null!=expertVo.getExpertMemberCodes()&&!"".equals(expertVo.getExpertMemberCodes())){
                //4-1.前端选择时被删除的专家编号
                List<String> deleteExpertCodes=Arrays.asList(expertVo.getExpertMemberCodes().split(","));

                //4-2.如果前端选择删除的专家编号不为空,则将前端被删除的专家从要排除的专家中剔除
                if(deleteExpertCodes.size()>0){
                    //4-2.1.将 前端选择时被删除的专家编号集合放到 map中
                    Map<String,String> deleteExpertCodesMap=new HashMap<>();
                    for(String deleteCode:deleteExpertCodes){
                        deleteExpertCodesMap.put(deleteCode,deleteCode);
                    }

                    //4-2.2.原来的专家编号-前端选择时被删除的专家编号==》得到要排除的专家编号集合
                    //如果拿到的原来的专家编号不为空
                    if(oldExpertCodes!=null && oldExpertCodes.size()>0){
                        //遍历原来的专家编号
                        for(String oldExpertCode:oldExpertCodes){
                            //查看当前编号 是否存在于 删除的编号中，如果存在，则不保留；如果不存在，则该编号添加到要排除的专家编号集合中
                            String deleteExpertCode=deleteExpertCodesMap.get(oldExpertCode);
                            if(deleteExpertCode !=null){
                                continue;
                            }
                            notIncludedExpCodes.add(oldExpertCode);
                        }
                    }
                }
            }
            //5.前端选择删除时的专家编号字符串为空,要排除的专家编号 集合 =原来该项目下存在的专家编号
            else{
                notIncludedExpCodes.addAll(oldExpertCodes);
            }
        }


        //PageHelper分页拦截
        PageHelper.startPage(pageNum, pageSize);
        //6.调用 专家库 mapper接口中的方法分页查询所有符合条件的专家信息 得到的是集合
        List<TLibraryPerformanceExpert> tLibraryPerformanceExperts= libraryPerformanceExpertMapper.queryLivePage(tLibraryPerformanceExpert,search,notIncludedExpCodes);
        return new PageInfo<>(tLibraryPerformanceExperts);
    }



    /**
     * 8.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 选中专家信息,跟老数据匹配且返回给前端
     * 整体思路：
     *      1）判断前端传递过来的新增的专家库数据是否为空
     *          1-1.不为空，将前端新增的专家库专家库数据 转换成 专家表数据
     *      2）判断前端传递过来的 最终保留的专家 是否有数据
     *      3）有数据，则 “最终要返回的专家数据”=“前端选择专家时新增的选择数据”+“前端保留的专家数据”
     *      4）没数据，则 “最终要返回的专家数据”=“前端选择专家时新增的选择数据” +（ “原来该项目存在的专家数据” - “前端删除的专家数据”）
     *          4-1.得到 该idA子项目下的原来的专家数据
     *          4-2.得到 前端选择时被删除的专家编号 map
     *          4-3.“原来该项目存在的专家数据” - “前端删除的专家数据”
     *          4-4.将原来没有被删除的专家的 专家权限字符串 转换成 集合
     *          4-5.将原来没有被删除的专家 加入到要返回的专家集合中
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 21:53
     * @updateTime 2021/3/17 21:53
     */
    @Override
    public List<TPerformanceExpertGroupInfo> goBackExpertGroups(TPerformanceExpertGroupInfoVo expertVo) {
        //最终要返回的专家数据
        List<TPerformanceExpertGroupInfo> performanceExpertGroupInfos=new ArrayList<>();
        //1）判断前端传递过来的新增的专家数据是否为空
        //1-1.不为空，将前端新增的专家库专家库数据 转换成 专家表数据
        if(expertVo.getLibrarySelectExperts()!=null && expertVo.getLibrarySelectExperts().size()>0){
            //前端选择专家时新增的选择数据
            List<TLibraryPerformanceExpert> addExperts=expertVo.getLibrarySelectExperts();

            //将“前端选择专家时新增的选择数据”转换成该项目下的专家数据表中 即：专家库数据 转换成 专家表数据
            for(TLibraryPerformanceExpert libraryExpert:addExperts){
                performanceExpertGroupInfos.add(LibraryToExpert(libraryExpert));
            }
        }

        //2）判断前端传递过来的 最终保留的专家 是否有数据
        List<TPerformanceExpertGroupInfo> chooseList=expertVo.getChooseList();

        //3）有数据，则 “最终要返回的专家数据”=“前端选择专家时新增的选择数据”+“前端保留的专家数据”
        if(chooseList!=null && chooseList.size()>0){
            //将“前端保留的专家数据”添加到“最终要返回的专家数据”集合中
            performanceExpertGroupInfos.addAll(chooseList);

        }

        //4）没数据，则 “最终要返回的专家数据”=“前端选择专家时新增的选择数据” +（ “原来该项目存在的专家数据” - “前端删除的专家数据”）
        else{
            //4-1.得到 该idA子项目下的原来的专家数据
            List<TPerformanceExpertGroupInfo> oldExpertInfos=performanceExpertGroupInfoMapper.getOldExpertInfo(expertVo.getIdA());

            //4-2.得到 前端选择时被删除的专家编号 map
            Map<String,String> deleteExpertCodesMap=new HashMap<>();
            if(expertVo.getExpertMemberCodes()!=null && !"".equals(expertVo.getExpertMemberCodes())){
                String[] deleteExpertCodes= expertVo.getExpertMemberCodes().split(",");
                for(String deleteCode:deleteExpertCodes){
                    deleteExpertCodesMap.put(deleteCode,deleteCode);
                }
            }

            //4-3.“原来该项目存在的专家数据” - “前端删除的专家数据”
            for(TPerformanceExpertGroupInfo oldExpert:oldExpertInfos){
                //查找当前旧的专家对象的编号是否在被删除的专家编号Map中，如果存在，则该条数据不加入到要返回的最终专家集合中
                String deleteCode=deleteExpertCodesMap.get(oldExpert.getExpCode());
                if(deleteCode !=null){
                    continue;
                }

            //4-4.将原来没有被删除的专家的 专家权限字符串 转换成 集合
            oldExpert.setAutorityExpets(Arrays.asList(oldExpert.getAuthorityOfExperts().split(Pattern.quote("|"))));

            //4-5.将原来没有被删除的专家 加入到要返回的专家集合中
            performanceExpertGroupInfos.add(oldExpert);
            }
        }

        return performanceExpertGroupInfos;
    }

    /**
     * 9.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 提交组建的专家成员集合信息
     * 整体思路：
     *      1）
     * @param tPerformanceWorkingGroupVo
     * @return Boolean
     * @author 田鑫艳
     * @createTime 2021/3/10 21:24
     * @updateTime 2021/3/10 21:24
     */
    @Transactional
    @Override
    public boolean setUpSaveExpertGroups(TPerformanceWorkingGroupVo tPerformanceWorkingGroupVo,ShiroUser user) throws Exception{

        Integer result=0;//用于判断传过来的集合中的专家数据是否都为空，如果都为空，则说明新增时，没有选择任何数据信息

        //拿到所有专家权限的角色主键id值
        Map<String,Integer> roleIdsMap=queryRoleIds();


        //得到传递过来的项目信息集合（每一个集合下都有一个专家组集合）
        List<TProPerformanceInfo> proPerformanceInfos=tPerformanceWorkingGroupVo.getProPerformanceInfos();
        System.out.println(proPerformanceInfos.toString());

        if(proPerformanceInfos.size()>0){
            //1.遍历传过来的集合
            for(TProPerformanceInfo tPr:proPerformanceInfos){

                //2.该子项目下原有的专家编号集合
                List<String> oldExpCodes=performanceExpertGroupInfoMapper.selectExceptCode(tPr.getIdA());

                //4.原来的专家编号为空
                if(oldExpCodes==null||oldExpCodes.size()==0){
                    //现在的专家数据为空，则说明该子项目新增时没有添加专家组
                    if(tPr.getPerformanceExpertGroupInfos()==null||tPr.getPerformanceExpertGroupInfos().size()==0) {
                        result++;
                    }
                    //现在的专家编号为不为空，则说明是新增，直接调用新增方法进行新增专家组信息到专家表中
                    else {
                        insertExpertGroups(tPr,roleIdsMap,user);
                    }
                }
                //5.原来的专家编号不为空，说明是修改
                else{
                    //删除该项目下的所有专家编号
                    performanceExpertGroupInfoMapper.deleteAllDatas(tPr.getIdA());
                    //根据项目id值，删除rc_user_role表中的数据(删除该项目下所有开通账号的数据)
                    rcUserRoleMapper.delectByIdA(tPr.getIdA());

                    //现在的专家数据为空，则说明，现在是修改，不需要专家了
                    if(null==tPr.getPerformanceExpertGroupInfos() || tPr.getPerformanceExpertGroupInfos().size()==0){
                        // 将 绩效项目信息（主子表）中 “是否已组建专家组” 字段 修改为 0 （0代表未组建）
                        proPerformanceInfoMapper.updateExpertGroupIsformed("0",tPr.getIdA());
                    }
                    //现在的专家数据不为空（原来的也不为空），则说明是中原来的基础上进行修改
                    else{
                        //调用新增方法
                        insertExpertGroups(tPr,roleIdsMap,user);
                    }

                }

                }
            }


        return result != proPerformanceInfos.size();



    }




    /**
     * 10.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 向专家表(用户表) 中插入数据
     * 整体思路：
     *      1.遍历TProPerformanceInfo对象中的专家对象集合
     *      2.判断专家对象是否要开通系统账号
     *         2-1.开通：
     *                    1).自动生成随机的账号，添加到rc_user表中
     *      3.向专家表中插入数据
     *      4.修改 该主/子项目的 是否已组建专家组 改为1
     *
     * @param proPerformanceInfo 一条主/子项目对象集合（里面可包含多个专家数据集合）
     * @return void
     * @author 田鑫艳
     * @createTime 2021/3/11 21:03
     * @updateTime 2021/3/11 21:03
     */
    @Transactional
    public void insertExpertGroups(TProPerformanceInfo proPerformanceInfo,Map<String,Integer> roleIdsMap,ShiroUser user) {

        //1.遍历tProPerformanceInfo中的工作组信息列表，将拿到每一个专家对象的信息插入到数据库中
        Integer idA=proPerformanceInfo.getIdA();//该子项目的主键
        String name=null;//创建人姓名
        if(null==proPerformanceInfo.getProManagerName()||"".equals(proPerformanceInfo.getProManagerName())){
            name=user.getName();
        }else{
            name=proPerformanceInfo.getProManagerName();//创建人姓名
        }

        for(TPerformanceExpertGroupInfo expert:proPerformanceInfo.getPerformanceExpertGroupInfos()){

            //如果前端传过来的专家权限数组不为空，则转换成字符串进行存储
            if(expert.getAutorityExpets()!=null&&expert.getAutorityExpets().size()>0){
                //设置专家权限(前端传递过来的是一个数组)
                String power= "";
                for(String str:expert.getAutorityExpets()){
                    power+=str+"|";
                }
                expert.setAuthorityOfExperts(power);
            }else{
                expert.setAuthorityOfExperts("");
            }


            expert.setIdA(idA);//设置关联的主表id值
            //2.判断是否给该专家开通账号，如果开通，则user表中增加数据，并且激活
            System.out.println("专家是否开通账号"+expert.getIsCreateAccount());
            if(("1").equals(expert.getIsCreateAccount())){

                //查看用户表中是否有这个专家账号，根据专家编号查询,用户表的员工编号就是专家编号，有直接拿过来用就好了
                String expertAccount=rcUserMapper.selectExpertAccount(expert.getExpCode());//根据专家编号查询账号
                if(expertAccount!=null){
                    expert.setExpAccount(expertAccount);

                }
                //用户表中没有该专家的系统账号，则新生成一个专家系统账号
                else{
                    //3.得到一个专家的账号，来进行判断是否数据库中有这个专家的账号，直到拿到随机生成的专家账号在数据库里查不到，
                    //则可以插入数据值用户表中
                    //3-1.随机生成一个专家的账号：
                    String exCode=getExpertCode();//初始专家账号
                    //3-2.判断随机生成的专家账号在用户表中是否存在,不存在则再次生成随机数
                    Integer judgeStay=rcUserMapper.selectExpertCount(exCode);//初始判断
                    //3-3.不为空，则说明，账号重复
                    while(judgeStay>=1){
                        exCode=getExpertCode();//再次拿到 新的专家账号
                        judgeStay=rcUserMapper.selectExpertCount(exCode);//再次调用mapper中的方法，去判断是否存在
                    }
                    //创建该专家的系统账号,即往用户表中插入数据
                    openUser(expert,exCode);
                    //设置专家表中的该专家的系统账号
                    expert.setExpAccount(exCode);

                }

                //4.给开通系统账号的专家进行权限菜单的分配(给开通系统账号的专家分配登录时能查看哪些菜单)
                //4-1.将前端选择的该专家的专家权限进行分割，并将分割好的数据封装成集合
                String authorityStr=expert.getAuthorityOfExperts();//得到该专家的权限字符串
                if(null!=authorityStr && !"".equals(authorityStr)){
                    authorityStr=authorityStr.replaceAll("M10,","");
                    String[] authorities= authorityStr.split(Pattern.quote("|"));

                    //4-2.根据该值和权限表中的value中的值相同，查询权限表rc_role得到该权限表的角色主键id值集合
                    //将该专家的权限跟所有的专家可拥有的权限做对比，得到该专加的角色主键值
                    List<Integer> expertRoleIds=new ArrayList<>();

                    for(String authority:authorities){
                        expertRoleIds.add(roleIdsMap.get(authority));
                    }

                    //4-3.根据专家账号得到用户表rc_user中的用户主键值
                    Integer userId=rcUserMapper.queryByUserName(expert.getExpAccount());

                    //4-4.遍历向rc_user_role中插入该专家的相关数据(用户id、用户权限)
                    for(Integer expertRoleId:expertRoleIds){
                        RcUserRole userRole=new RcUserRole();
                        userRole.setUserId(userId);//该专家的用户主键id值
                        userRole.setRoleId(expertRoleId);//对应的角色id值
                        userRole.setCreateTime(new Date());//创建时间
                        userRole.setCreateBy(String.valueOf(idA));//创建人对应项目id值
                        //rc_user_role中插入该专家的相关数据(用户id、用户权限)
                        rcUserRoleMapper.insert(userRole);
                    }
                }


            }
            //没有开通系统账号
            else if(("0").equals(expert.getIsCreateAccount())){
                //判断该用户在其他项目中又没有被使用，如果没有，则在用户表中删除该专家账号信息、删除该专家层设置的rc_user_role信息
                //根据专家编号拿到专家账号、用户主键id值、专家姓名
                RcUser exprtAccountInfo=rcUserMapper.queryByExpCode(expert.getExpCode());
                //如果根据该专家编号查询的专家账号不为空
                if(exprtAccountInfo!=null ){
                    //查询该专家账号是否在其他项目中使用(查询的结果为空)，如果其他项目中也没有使用该专家账号，则删除用户表中该专家账号
                    Integer exprtAccountIsUsed=performanceExpertGroupInfoMapper.queryExprtAccountIsUsed(exprtAccountInfo.getUsername());
                    if(exprtAccountIsUsed==null || exprtAccountIsUsed==0){
                        rcUserMapper.deleteExpertAccount(exprtAccountInfo.getUsername());
                        //删除该专家层设置的rc_user_role信息
                        rcUserRoleMapper.deleteByUserId(exprtAccountInfo.getId());

                    }
                }


            }
            expert.setCreateor(name);//设置绩效专家表中的 创建人
            //创建时间
            if(expert.getCreateTime()==null){
                expert.setCreateTime(new Date());
            }

            //修改人
            expert.setUpdateor(name);
            //修改时间
            if(expert.getUpdateTime()==null){
                expert.setUpdateTime(new Date());
            }
            //插入到专家表中
            performanceExpertGroupInfoMapper.insert(expert);
        }
        // 将 绩效项目信息（主子表）中 “是否已组建专家组” 字段 修改为 1 （0代表未组建）
        proPerformanceInfoMapper.updateExpertGroupIsformed("1",idA);
    }

    /**
     * 11.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 开通系统账号
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 21:52
     * @updateTime 2021/3/17 21:52
     */
    public void openUser(TPerformanceExpertGroupInfo tpr,String exCode){
        //4.跳出循环后，说明，该账号可以作为专家的账号来使用，封装成一个用户对象，插入到数据库中
        RcUser rcUser=new RcUser();
        rcUser.setUsername(exCode);//专家账号就是用户名
        rcUser.setStatus(2);//2 表示专家组成员
        String salt = ShiroKit.getRandomSalt(5);
        rcUser.setSalt(salt);//盐值
        rcUser.setName(tpr.getExpName());//用户姓名=专家姓名
        rcUser.setPassword("123456");
        String saltPwd = ShiroKit.md5(rcUser.getPassword(), salt);
        rcUser.setPassword(saltPwd);//专家密码 默认：123456
        rcUser.setGroupMemberName(tpr.getExpName());//组员姓名
        rcUser.setUserLeavel(tpr.getExpLeavel());//userLeavel级别
        rcUser.setGraduatedFrom(tpr.getGraduatedFrom());//毕业院校
        rcUser.setEducation(tpr.getEducation());//education
        rcUser.setGroupMemberCode(tpr.getExpCode());//员工编号为专家编号
        rcUser.setCreateTime(new Date());
        rcUserMapper.insert(rcUser);//插入数据

    }

    /**
     * 12.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 生成专家账号
     * @return String 返回专家的账号
     * @author 田鑫艳
     * @createTime 2021/3/12 15:15
     * @updateTime 2021/3/12 15:15
     */
    public String getExpertCode(){
        //自动生成专家的账号：EX+4位年份+5位随机数
        String exCode="EX";
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));//四位年份
        int num=(int)((Math.random()*9+1)*10000);//随机5位数
        exCode+=year+num;//专家的账号
        return exCode;
    }

    /**
     * 13.TProPerformanceInfoServiceImpl [组建工作组-->组建专家组  服务实现层]
     * 专家库数据 转换成 专家表数据
     * @param libraryExpert 要转换成专家组表的专家库中的专家对象
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 22:02
     * @updateTime 2021/3/17 22:02
     */
    public TPerformanceExpertGroupInfo LibraryToExpert(TLibraryPerformanceExpert libraryExpert){

            TPerformanceExpertGroupInfo expertGroupInfo=new TPerformanceExpertGroupInfo();
            //expCode 专家编号
            expertGroupInfo.setExpCode(libraryExpert.getExpCode());

            /**
             * 姓名 expName
             */
            expertGroupInfo.setExpName(libraryExpert.getExpName());
            /**
             * 身份证号 idNumber
             */
            expertGroupInfo.setIdNumber(libraryExpert.getIdNumber());
            /**
             * 专家级别 expLeavel
             */
            expertGroupInfo.setExpLeavel(libraryExpert.getExpLeavel());
            /**
             * 职称 expTitle
             */
             expertGroupInfo.setExpTitle(libraryExpert.getExpTitle());
            /**
             * 学历 education
             */
            expertGroupInfo.setEducation(libraryExpert.getEducation());
            /**
             * 院校 graduatedFrom
             */
            expertGroupInfo.setGraduatedFrom(libraryExpert.getGraduatedFrom());
            /**
             * 电话 telNumber
             */
            expertGroupInfo.setTelNumber(libraryExpert.getTelNumber());
            /**
             * 邮箱 emailAddress
             */
            expertGroupInfo.setEmailAddress(libraryExpert.getEmailAddress());
            /**
             * 常驻地_省 permanentResidenceProvince
             */
            expertGroupInfo.setPermanentResidenceProvince(libraryExpert.getPermanentResidenceProvince());
            /**
             * 常驻地_市 permanentResidenceCity
             */
            expertGroupInfo.setPermanentResidenceCity(libraryExpert.getPermanentResidenceCity());
            /**
             * 常驻地_县 permanentResidenceCounty
             */
            expertGroupInfo.setPermanentResidenceCounty(libraryExpert.getPermanentResidenceCounty());
            /**
             * 主要研究方向 mainResearchDirections
             */
             expertGroupInfo.setMainResearchDirections(libraryExpert.getMainResearchDirections());
            /**
             * 主要著作 mainWorks
             */
            expertGroupInfo.setMainWorks(libraryExpert.getMainWorks());
            /**
             * 课题成果 researchAchievements
             */
             expertGroupInfo.setResearchAchievements(libraryExpert.getResearchAchievements());
            /**
             * 参与所内项目数 particProjectNumber
             */
            expertGroupInfo.setParticProjectNumber(libraryExpert.getParticProjectNumber());
            /**
             * 主要服务分所 mainServiceBranch
             */
             expertGroupInfo.setMainServiceBranch(libraryExpert.getMainServiceBranch());
            /**
             * 所在行业（政府预算支出功能分类）一级分类 industryZfysLevel1
             */
            expertGroupInfo.setIndustryZfysLevel1(libraryExpert.getIndustryZfysLevel1());
            /**
             * 所在行业（政府预算支出功能分类）二级分类 industryZfysLevel2
             */
             expertGroupInfo.setIndustryZfysLevel2(libraryExpert.getIndustryZfysLevel2());
            /**
             * 所在行业（国民经济分类）门类 industryGmjjLevel1
             */
            expertGroupInfo.setIndustryGmjjLevel1(libraryExpert.getIndustryGmjjLevel1());
            /**
             * 所在行业（国民经济分类）大类 industryGmjjLevel2
             */
             expertGroupInfo.setIndustryGmjjLevel2(libraryExpert.getIndustryGmjjLevel2());
            /**
             * 服务登记评定 servRegAsse
             */
            expertGroupInfo.setServRegAsse(libraryExpert.getServRegAsse());
            /**
             * 专家权限  存放菜单id
             用竖线分隔 “|” authorityOfExperts
             */
             expertGroupInfo.setAuthorityOfExperts(libraryExpert.getAuthorityOfExperts());
            /**
             * 是否创建专家账号   0-否（默认）  1-是
             * 	如选择是，则触发逻辑：后台在rc_user表中自动生成专家账号数据，并激活
             *	账号规则：（注意判断重复）
             *	EX+4位年份+5位随机数 isCreateAccount
             */
            expertGroupInfo.setIsCreateAccount("0");
//            /**
//             * 专家账号 expAccount
//             */
//            expertGroupInfo.setExpAccount();

        return expertGroupInfo;
    }

    /**
     * 根据该值和权限表中的value中的值相同，查询权限表rc_role得到该权限表的角色主键id值集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/13 11:55
     * @updateTime 2021/4/13 11:55
     */
    public Map<String,Integer> queryRoleIds(){
        List<String> authority=new ArrayList<>();
        authority.add("M101");//指标体系审核
        authority.add("M102");//评价方案(项目组审核后)
        authority.add("M103");//评价报告(项目组审核后)
        //根据顺序查询所有的专家权限的角色主键id值和vaule集合（查询的结果是按照M101\M102\M103的顺序进行排列好的角色主键id值）
        List<RcRole>roleIds=rcRoleMapper.queryByVlaue(authority);
        //将值加入到map中
        Map<String,Integer> roleIdsMap=new HashMap<>();
        for(RcRole role:roleIds){
            roleIdsMap.put(role.getValue(),role.getId());
        }
        return roleIdsMap;
    }





/*==============工作组台账=================================================================================================*/
    /**
     * 1.主页面的显示
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param proPerformanceInfo 精准查询封装的对象
     * @param search        综合查询的字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/12 0:06
     * @updateTime 2021/3/12 0:06
     */
    @Override
    public PageInfo<TProPerformanceInfo> pagebookList(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search,ShiroUser user) {
        PageHelper.startPage(current,size);
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.selectWorkInfo(proPerformanceInfo,search,String.valueOf(user.getId()));

        //如果得到符合 “约束条件” 的数据不为空，则才可以进入下一步操作 （约束条件：已经立项、已经组建工作组）
        if(proPerformanceInfos.size()>0){
            //遍历
            for(TProPerformanceInfo tpr:proPerformanceInfos){
                //取该项目的外勤主管
                TPerformanceWorkingGroup performanceWorkingGroup=performanceWorkingGroupMapper.queryWorkCharge(tpr.getIdA());
                tpr.setPerformanceWorkingGroup(performanceWorkingGroup);


                //1.取工作组创建的最晚时间
                Date setupTime=null;//工作组的组建时间，取 “员工工作组”跟“专家工作组”中的最晚时间
                Date memberTime=performanceWorkingGroupMapper.selectMemberTime(tpr.getIdA());//组建工作组的（修改）最晚时间
                Date expertTime=performanceExpertGroupInfoMapper.selectExpertTime(tpr.getIdA());//组建专家组的（修改）最晚时间

                //拿到时间，判断是否为空
                if(memberTime!=null && expertTime==null){
                    setupTime=memberTime;
                }else if(memberTime==null && expertTime!=null){
                    setupTime=expertTime;
                }else if(memberTime!=null && expertTime!=null){
                    Integer i=memberTime.compareTo(expertTime);
                    if(i>=0){
                        setupTime=memberTime;
                    }else{
                        setupTime=expertTime;
                    }
                }

                tpr.setWorkCreateTime(setupTime);//设置该项目的创建时间

                //2.取工作组的人员总数
                Integer memberNum=performanceWorkingGroupMapper.selectMemberNum(tpr.getIdA());
                Integer expertNum=performanceExpertGroupInfoMapper.selectExpertNum(tpr.getIdA());
                Integer lastNum=memberNum+expertNum;//最后总共的人员数
                tpr.setMemberNum(lastNum);
            }

        }

        return new PageInfo<TProPerformanceInfo>(proPerformanceInfos);//将list列表 转换成PageInfo的形式并返回

    }
    /**
     * 根据主项目编号查询查询该项目下的每个项目下的组员信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 15:43
     * @updateTime 2021/4/12 15:43
     */
    @Override
    public List<TProPerformanceInfo> selectInfoByProCode(String parentProCode) {
        List<TProPerformanceInfo> infos=proPerformanceInfoMapper.selectInfoByProCode(parentProCode);
        //遍历 将项目已经立项、已经组建专家组的项目信息 将每个项目信息的工作组成员添加进去
        if(infos!=null && infos.size()>0){
            //2.通过遍历“idA”主键，查找每一个 主/子 项目 的组员信息(集合)
            for(TProPerformanceInfo tpr:infos){
                //查询组员信息
                List<TPerformanceWorkingGroup> tPerformanceWorkingGroups=performanceWorkingGroupMapper.selectExceptMembers(tpr.getIdA());
                if(tPerformanceWorkingGroups.size()>0){
                    tpr.setPerformanceWorkingGroups(tPerformanceWorkingGroups);//设置 组员信息 (用绩效工作组封装)
                    System.out.println("组员信息："+tpr.getPerformanceWorkingGroups());
                }

            }
        }
        return infos;
    }

    /**
     * 根据主项目编号查询需要组建专家组的子项目项目信息和专家信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 16:00
     * @updateTime 2021/4/12 16:00
     */
    @Override
    public List<TProPerformanceInfo> infoExpertByProCode(String parentProCode) {
        //查询当前主项目编号下的所有主子项目信息（该项目经理是当前登录人）
        List<TProPerformanceInfo> infos=proPerformanceInfoMapper.selectInfoByProCode(parentProCode);
        if(infos!=null && infos.size()>0){
            //2.通过遍历“idA”主键，查找每一个 主/子 项目的专家组信息
            for(TProPerformanceInfo tpr:infos){
                if("1".equals(tpr.getExpertGroupIsformed())){
                    List<TPerformanceExpertGroupInfo> performanceExpertGroupInfos=performanceExpertGroupInfoMapper.selectExpertGroup(tpr.getIdA());

                    //将专家权限字符串转换为数组
                    for(TPerformanceExpertGroupInfo expert:performanceExpertGroupInfos){
                        expert.setAutorityExpets(Arrays.asList(expert.getAuthorityOfExperts().split(Pattern.quote("|"))));
                        System.out.println(expert.getAutorityExpets().toString());
                    }


                    if(performanceExpertGroupInfos.size()>0){
                        tpr.setPerformanceExpertGroupInfos(performanceExpertGroupInfos);
                        System.out.println("专家信息；"+tpr.getPerformanceExpertGroupInfos());
                    }
                }


            }
        }
        return infos;
    }



}
