package com.common.business.planmgr.otherdraftdesign.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.common.business.planmgr.indexdraftdesign.entity.RelationIndexWorkingManuscriptDesign;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptDesignRoutine;
import com.common.business.planmgr.indexdraftdesign.entity.TIndexWorkingManuscriptPoints;
import com.common.business.planmgr.otherdraftdesign.entity.RelationProWorkingManuscriptDesign;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignOther;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignResearch;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignSuggest;
import com.common.business.planmgr.otherdraftdesign.mapper.RelationProWorkingManuscriptDesignMapper;
import com.common.business.planmgr.otherdraftdesign.mapper.TProWorkingManuscriptDesignOtherMapper;
import com.common.business.planmgr.otherdraftdesign.mapper.TProWorkingManuscriptDesignResearchMapper;
import com.common.business.planmgr.otherdraftdesign.mapper.TProWorkingManuscriptDesignSuggestMapper;
import com.common.business.planmgr.otherdraftdesign.service.BussinessFlowProWorkingManuscriptDesignService;
import com.common.business.planmgr.otherdraftdesign.service.RelationProWorkingManuscriptDesignService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目底稿关系表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-04-13
 */
@Service
public class RelationProWorkingManuscriptDesignServiceImpl extends ServiceImpl<RelationProWorkingManuscriptDesignMapper, RelationProWorkingManuscriptDesign> implements RelationProWorkingManuscriptDesignService {

    @Autowired
    private RelationProWorkingManuscriptDesignMapper relationProWorkingManuscriptDesignMapper;
    @Autowired
    private TProWorkingManuscriptDesignSuggestMapper tProWorkingManuscriptDesignSuggestMapper;
    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private TPerformanceWorkingGroupService tPerformanceWorkingGroupService;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private TProWorkingManuscriptDesignResearchMapper tProWorkingManuscriptDesignResearchMapper;
    @Autowired
    private TProWorkingManuscriptDesignOtherMapper tProWorkingManuscriptDesignOtherMapper;
    @Autowired
    private BussinessFlowProWorkingManuscriptDesignService bussinessFlowProWorkingManuscriptDesignService;


    /**
     * 项目底稿设计列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> proRaftdesignPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception{
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //查询已经立项的项目
        List<TProPerformanceInfo> list= tProPerformanceInfoMapper.proRaftdesignPage(bean,search,user.getId()+"");
        return new PageInfo<TProPerformanceInfo>(list);
    }

    /**
     * @Description: 根据idA查询最后一次项目底稿关系表
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    public RelationProWorkingManuscriptDesign getRelationProWorkingManuscriptDesignByIdA(Integer idA) throws Exception{
        EntityWrapper<RelationProWorkingManuscriptDesign> entity=new EntityWrapper<RelationProWorkingManuscriptDesign>();
        entity.eq("ID_A",idA);
        //根据idr查找 常规类底稿 集合
        List<RelationProWorkingManuscriptDesign> list=relationProWorkingManuscriptDesignMapper.selectList(entity);
        if(list !=null && list.size()>0){
            return list.get(list.size()-1);
        }
        return null;
    }
    /**
     * @Description: 获取最高的版本号
     * @Author: 安达
     * @Date: 2021/3/29 20:07
     * @Param:
     * @Return:
     */
    private String getVersionNo(Integer idA){
        //查询项目底稿关系表
        EntityWrapper<RelationProWorkingManuscriptDesign> entity=new EntityWrapper<RelationProWorkingManuscriptDesign>();
        entity.eq("ID_A",idA);
        //根据idr查找 常规类底稿 集合
        List<RelationProWorkingManuscriptDesign> list=relationProWorkingManuscriptDesignMapper.selectList(entity);
        //定义版本号
        StringBuilder versionBuilder=new StringBuilder("V");
        int size;
        if(list !=null || list.size()>0) {
            String versionNo=list.get(0).getVersionNo();
            //获取最高的版本号
            size = Integer.parseInt(versionNo.substring(1,versionNo.length()))+1;
        }else {
            size = 1;
        }
        versionBuilder.append(size);
        return versionBuilder.toString();
    }

    /**
     * //新增项目底稿关系且返回主键
     * @param idA
     * @return
     */
    private RelationProWorkingManuscriptDesign insertRelationProWorkingManuscriptDesign(Integer idA, String stauts,
                                                                                        String indexWorkingPaperType, ShiroUser user)  throws Exception{
        //项目外勤主管
        String fieldSupervisorId=tPerformanceWorkingGroupService.getFieldSupervisorIdByIdA(idA);
        //获取外勤主管信息
        RcUser ieldSupervisorUser=rcUserMapper.selectById(fieldSupervisorId);
        //创建项目底稿关系对象
        RelationProWorkingManuscriptDesign insertBean=new RelationProWorkingManuscriptDesign();
        insertBean.setIdA(idA);
        //版本号
        String versionNo=getVersionNo(idA);
        insertBean.setVersionNo(versionNo);
        //指标底稿设计（申请）时间
        insertBean.setCreateTime(new Date());
        // 版本状态： -1：退货，0：暂存，1：审批中，2：已完成
        insertBean.setCreateStauts(stauts);
        //指标工作底稿类型
        insertBean.setIndexWorkingPaperType(indexWorkingPaperType);
        //设计人
        insertBean.setCreateUaseName(user.getName());
        //当前审批人ID
        insertBean.setCurrCheckId(ieldSupervisorUser.getId()+"");
        //当前审批人姓名
        insertBean.setCurrCheckName(ieldSupervisorUser.getName());
        //新增项目底稿关系且返回主键
        relationProWorkingManuscriptDesignMapper.insert(insertBean);
        return insertBean;
    }
    /**
     * @Description: 工作底稿信息    问题建议类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @Override
    public TProWorkingManuscriptDesignSuggest getTProWorkingManuscriptDesignSuggestByIdA(Integer idA) throws Exception{
        //根据idA查询最后一次项目底稿关系表
        RelationProWorkingManuscriptDesign manuscriptDesign=getRelationProWorkingManuscriptDesignByIdA(idA);
        if(manuscriptDesign==null){
            return new TProWorkingManuscriptDesignSuggest();
        }else{
            EntityWrapper<TProWorkingManuscriptDesignSuggest> entity=new EntityWrapper<TProWorkingManuscriptDesignSuggest>();
            entity.eq("ID_R",manuscriptDesign.getIdR());
            //根据idr查找 问题建议类底稿 集合
            List<TProWorkingManuscriptDesignSuggest> list=tProWorkingManuscriptDesignSuggestMapper.selectList(entity);
            if(list !=null && list.size()>0){
                return list.get(0);
            }else{
                return new TProWorkingManuscriptDesignSuggest();
            }
        }
    }
    /**
     * @Description: 保存问题建议类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idA  子项目主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: proWorkingManuscriptDesignSuggest  问题建议类底稿
     * @Param: user 当前登陆者
     * @Return:
     */
    @Override
    public void saveTProWorkingManuscriptDesignSuggest(Integer idA, String stauts, String indexWorkingPaperType,
                                                       TProWorkingManuscriptDesignSuggest proWorkingManuscriptDesignSuggest, ShiroUser user) throws Exception {
        //如果为空，就是新增
        if(proWorkingManuscriptDesignSuggest.getIdR()==null){
            //新增项目底稿关系且返回主键
            RelationProWorkingManuscriptDesign manuscriptDesign=insertRelationProWorkingManuscriptDesign(idA,stauts,indexWorkingPaperType,user);
            //保存工作流
            bussinessFlowProWorkingManuscriptDesignService.saveCheckFlowUser(idA,manuscriptDesign.getIdR(),user);
            //设置外键
            proWorkingManuscriptDesignSuggest.setIdR(manuscriptDesign.getIdR());
            //新增问题建议类底稿
            tProWorkingManuscriptDesignSuggestMapper.insert(proWorkingManuscriptDesignSuggest);
        }else{
            //修改
            //修改问题建议类底稿
            tProWorkingManuscriptDesignSuggestMapper.updateById(proWorkingManuscriptDesignSuggest);
        }
    }


    /**
     * @Description: 调研总结类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @Override
    public TProWorkingManuscriptDesignResearch getTProWorkingManuscriptDesignResearchByIdA(Integer idA) throws Exception{
        //根据idA查询最后一次项目底稿关系表
        RelationProWorkingManuscriptDesign manuscriptDesign=getRelationProWorkingManuscriptDesignByIdA(idA);
        if(manuscriptDesign==null){
            return new TProWorkingManuscriptDesignResearch();
        }else{
            EntityWrapper  entity=new EntityWrapper();
            entity.eq("ID_R",manuscriptDesign.getIdR());
            //根据idr查找 问题建议类底稿 集合
            List<TProWorkingManuscriptDesignResearch> list=tProWorkingManuscriptDesignResearchMapper.selectList(entity);
            if(list !=null && list.size()>0){
                return list.get(0);
            }else{
                return new TProWorkingManuscriptDesignResearch();
            }
        }
    }
    /**
     * @Description: 保存调研总结类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idA  子项目主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: proWorkingManuscriptDesignSuggest  调研总结类底稿
     * @Param: user 当前登陆者
     * @Return:
     */
    @Override
    public void saveTProWorkingManuscriptDesignResearch(Integer idA, String stauts, String indexWorkingPaperType,
                                                        TProWorkingManuscriptDesignResearch proWorkingManuscriptDesignResearch, ShiroUser user) throws Exception {
        //如果为空，就是新增
        if(proWorkingManuscriptDesignResearch.getIdR()==null){
            //新增项目底稿关系且返回主键
            RelationProWorkingManuscriptDesign manuscriptDesign=insertRelationProWorkingManuscriptDesign(idA,stauts,indexWorkingPaperType,user);
            //保存工作流
            bussinessFlowProWorkingManuscriptDesignService.saveCheckFlowUser(idA,manuscriptDesign.getIdR(),user);
            //设置外键
            proWorkingManuscriptDesignResearch.setIdR(manuscriptDesign.getIdR());
            //新增问题建议类底稿
            tProWorkingManuscriptDesignResearchMapper.insert(proWorkingManuscriptDesignResearch);
        }else{
            //修改
            //修改问题建议类底稿
            tProWorkingManuscriptDesignResearchMapper.updateById(proWorkingManuscriptDesignResearch);

        }
    }


    /**
     * @Description: 其他类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    @Override
    public  List<TProWorkingManuscriptDesignOther> getTProWorkingManuscriptDesignOtherListByIdA(Integer idA) throws Exception{
        //根据idA查询最后一次项目底稿关系表
        RelationProWorkingManuscriptDesign manuscriptDesign=getRelationProWorkingManuscriptDesignByIdA(idA);
        if(manuscriptDesign==null){
            return null;
        }else{
            EntityWrapper entity=new EntityWrapper();
            entity.eq("ID_R",manuscriptDesign.getIdR());
            //根据idr查找 问题建议类底稿 集合
            List<TProWorkingManuscriptDesignOther> list=tProWorkingManuscriptDesignOtherMapper.selectList(entity);
            return list;
        }
    }
    /**
     * @Description: 保存其他类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param: idA  子项目主键
     * @Param: stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param: proWorkingManuscriptDesignOther  其他类底稿
     * @Param: user 当前登陆者
     * @Return:
     */
    @Override
    public void saveTProWorkingManuscriptDesignOther(Integer idA, String stauts, String indexWorkingPaperType,
                                                     List<TProWorkingManuscriptDesignOther> proWorkingManuscriptDesignOtherList, ShiroUser user) throws Exception {
        Integer idR=proWorkingManuscriptDesignOtherList.get(0).getIdR();
        //如果为空，就是新增
        if(idR==null){
            //新增项目底稿关系且返回主键
            RelationProWorkingManuscriptDesign  manuscriptDesign=insertRelationProWorkingManuscriptDesign(idA,stauts,indexWorkingPaperType,user);
            idR=manuscriptDesign.getIdR();
            //保存工作流
            bussinessFlowProWorkingManuscriptDesignService.saveCheckFlowUser(idA,manuscriptDesign.getIdR(),user);
        }
        for(TProWorkingManuscriptDesignOther proWorkingManuscriptDesignOther:proWorkingManuscriptDesignOtherList){
            //设置外键
            proWorkingManuscriptDesignOther.setIdR(idR);
            //修改问题建议类底稿
            tProWorkingManuscriptDesignOtherMapper.updateById(proWorkingManuscriptDesignOther);
        }
    }



}
