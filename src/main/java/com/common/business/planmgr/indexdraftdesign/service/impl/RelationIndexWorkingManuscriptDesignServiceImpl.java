package com.common.business.planmgr.indexdraftdesign.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.draftcheck.service.BussinessFlowIndexWorkingManuscriptDesignService;
import com.common.business.planmgr.indexcheck.mapper.RelationProIndexMapper;
import com.common.business.planmgr.indexdesign.entity.TIndexScoringPoints;
import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import com.common.business.planmgr.indexdesign.mapper.TIndexScoringPointsMapper;
import com.common.business.planmgr.indexdesign.mapper.TIndexSystemDseignMapper;
import com.common.business.planmgr.indexdesign.mapper.TScoringStandardsAndRuilesDetailMapper;
import com.common.business.planmgr.indexdraftdesign.entity.*;
import com.common.business.planmgr.indexdraftdesign.mapper.*;
import com.common.business.planmgr.indexdraftdesign.service.RelationIndexWorkingManuscriptDesignService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 三级指标与指标底稿关系表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-24
 */
@Service
@Transactional
public class RelationIndexWorkingManuscriptDesignServiceImpl extends ServiceImpl<RelationIndexWorkingManuscriptDesignMapper,
        RelationIndexWorkingManuscriptDesign> implements RelationIndexWorkingManuscriptDesignService {
    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private RelationIndexWorkingManuscriptDesignMapper relationIndexWorkingManuscriptDesignMapper;
    @Autowired
    private TIndexSystemDseignMapper tIndexSystemDseignMapper;
    @Autowired
    private TIndexScoringPointsMapper tIndexScoringPointsMapper;
    @Autowired
    private TIndexWorkingManuscriptDesignRoutineMapper tIndexWorkingManuscriptDesignRoutineMapper;
    @Autowired
    private TIndexWorkingManuscriptPointsMapper tIndexWorkingManuscriptPointsMapper;
    @Autowired
    private TIndexWorkingManuscriptDesignSatisfactionMapper tIndexWorkingManuscriptDesignSatisfactionMapper;
    @Autowired
    private TIndexWorkingManuscriptDesignOtherMapper tIndexWorkingManuscriptDesignOtherMapper;
    @Autowired
    private BussinessFlowIndexWorkingManuscriptDesignService bussinessFlowIndexWorkingManuscriptDesignService;
    @Autowired
    private TPerformanceWorkingGroupService tPerformanceWorkingGroupService;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private RelationProIndexMapper relationProIndexMapper;
    @Autowired
    private TScoringStandardsAndRuilesDetailMapper scoringStandardsAndRuilesDetailMapper;

    /**
     * 指标底稿设计列表页
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> indexdraftdesignPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean,ShiroUser user) throws Exception {

        PageInfo<TProPerformanceInfo> pageInfo=new PageInfo<TProPerformanceInfo>();
        EntityWrapper<TProPerformanceInfo> entity=new EntityWrapper<TProPerformanceInfo>();
        //项目名称
        if(StringUtils.isNotEmpty(bean.getParentProName())){
            entity.like("PARENT_PRO_NAME",bean.getParentProName());
        }
        //风险等级  A  B C
        if(StringUtils.isNotEmpty(bean.getRiskLevel())){
            entity.eq("RISK_LEVEL",bean.getRiskLevel());
        }
        //指标体系设计审核状态
        entity.eq("INDEX_DSEIGN_STATUS",2);

        //关键字模糊查询
        if(StringUtils.isNotEmpty(search)){
            entity.andNew().like("PARENT_PRO_NAME",search).or().like("PRO_NAME",search)
                    .or().like("PRO_PARTEN_NAME",search).or().like("PRO_MANAGER_NAME",search)
                    .or().like("RISK_LEVEL",search);
        }

        RowBounds rowBounds=new RowBounds(pageNum,pageSize);
        //SELECT * FROM t_pro_performance_info WHERE    (PRO_STATUS =1 AND IS_SETUP_WORKING_GROUP = 'Y') LIMIT 1, 10
        // AND (PARENT_PRO_NAME LIKE '%公共绩效评价%' OR PRO_NAME LIKE '%公共绩效评价%'  OR PRO_PARTEN_NAME LIKE '%公共绩效评价%'
        // OR PRO_MANAGER_NAME LIKE '%公共绩效评价%'  OR RISK_LEVEL LIKE '%公共绩效评价%' )
        //  LIMIT 0, 10
        List<TProPerformanceInfo> list=tProPerformanceInfoMapper.selectPage(rowBounds,entity);
        Integer total=list.size();
        if(list !=null && list.size()>0){
            //创建项目id集合
            List<Integer> idaList=new ArrayList<>();
            for(TProPerformanceInfo info:list){
                idaList.add(info.getIdA());
            }
            //获取底稿集合
            List<RelationIndexWorkingManuscriptDesign> indexdraftdesignList=relationIndexWorkingManuscriptDesignMapper.findByIdaList(idaList);
            if(indexdraftdesignList !=null && indexdraftdesignList.size()>0){
                Map<Integer, RelationIndexWorkingManuscriptDesign> map=getMapByList(indexdraftdesignList);
                for(TProPerformanceInfo info:list){
                    RelationIndexWorkingManuscriptDesign indexdraftdesign=map.get(info.getIdA());
                    //设计人
                    info.setSystemDseigntor(indexdraftdesign.getCreateUaseName());
                    //设计时间
                    info.setSystemDseignTime(indexdraftdesign.getCreateTime());
                    //设计状态   0-暂存   1-审批中   2-已审批
                    info.setSystemDseignStatus(indexdraftdesign.getCreateStauts());
                    // 项目指标体系关系 ID_R
                    info.setIdR(indexdraftdesign.getIdR());
                }
            }
        }
        pageInfo.setList(list);
        pageInfo.setTotal(total);
        return pageInfo;
    }
    /**
     * 根据集合获取 idR-创建时间 map
     * @param indexdraftdesignList
     * @return
     */
    private Map<Integer, RelationIndexWorkingManuscriptDesign> getMapByList(List<RelationIndexWorkingManuscriptDesign> indexdraftdesignList){
        Map<Integer, RelationIndexWorkingManuscriptDesign> map=new HashMap<>();
        for(RelationIndexWorkingManuscriptDesign indexdraftdesign:indexdraftdesignList){
            map.put(indexdraftdesign.getIdR(),indexdraftdesign);
        }
        return map;
    }

    /**
     * 根据idR查询底稿设计表头信息
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idR
     * @return
     * @throws Exception
     */
    @Override
    public List<TIndexSystemDseign> findIndexSystemDseignListByIdR(Integer idR) throws Exception {

        EntityWrapper<TIndexSystemDseign> entity=new EntityWrapper<TIndexSystemDseign>();
        entity.eq("ID_R",idR);
        //根据指标编码排序
        entity.orderBy("INDEX_CODE",true);
        List<TIndexSystemDseign> list=tIndexSystemDseignMapper.selectList(entity);
        for(TIndexSystemDseign dseign:list){
            //指标说明
            StringBuilder  scoringPointsBuilder=new StringBuilder("评分要点：");
            //要点分值
            Double  pointsScore=0d;
            //评价标准及评分规则
            StringBuilder  pointsRuleBuilder=new StringBuilder();

            //根据idb查找 评分要点 集合
            List<TIndexScoringPoints>  dseignList=findScoringPointsByIdb(dseign.getIdB());
            for(TIndexScoringPoints points:dseignList){
                scoringPointsBuilder.append(points.getPointsName()+";");
                pointsScore=pointsScore+points.getPointsScore();
                pointsRuleBuilder.append(points.getScoringMethod2()+";");
            }
            //指标说明
            dseign.setScoringPoints(scoringPointsBuilder.toString());
            //要点分值
            dseign.setPointsScore(pointsScore);
            //评价标准及评分规则
            dseign.setPointsRule(pointsRuleBuilder.toString());
            //根据idb查找 最后一次三级指标与指标底稿关系（主要是查询存在问题的值）
            RelationIndexWorkingManuscriptDesign  manuscriptDesign=findRelationIndexWorkingManuscriptDesignByIdb(dseign.getIdB());
            if(manuscriptDesign==null){
                dseign.setStatus("未设计");
            }else{
                //存在问题
                dseign.setExistingProblems(manuscriptDesign.getExistingProblems());
               // 版本状态 -1：退货，0：暂存，1：审批中，2：已完成
                if("2".equals(manuscriptDesign.getCreateStauts())){
                    dseign.setStatus("已设计");
                }else{
                    dseign.setStatus("设计中");
                }

            }

        }
        return list;
    }
    /**
     * @Description: 根据idb查找 评分要点 集合
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private List<TIndexScoringPoints>  findScoringPointsByIdb(Integer idB){
        EntityWrapper<TIndexScoringPoints> entity=new EntityWrapper<TIndexScoringPoints>();
        entity.eq("ID_B",idB);
        //根据idB查询指标说明（评分要点）集合
        List<TIndexScoringPoints> list= tIndexScoringPointsMapper.selectList(entity);
        return list;
    }

    /**
     * @Description: 根据idb查找 最后一次三级指标与指标底稿关系
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private RelationIndexWorkingManuscriptDesign  findRelationIndexWorkingManuscriptDesignByIdb(Integer idB){
        RelationIndexWorkingManuscriptDesign workingManuscriptDesign=null;
        EntityWrapper<RelationIndexWorkingManuscriptDesign> entity=new EntityWrapper<RelationIndexWorkingManuscriptDesign>();
        entity.eq("ID_B",idB);
        List<RelationIndexWorkingManuscriptDesign> list=relationIndexWorkingManuscriptDesignMapper.selectList(entity);
        if(list !=null && list.size()>0){
            workingManuscriptDesign=list.get(list.size()-1);
            //指标工作底稿类型 1-常规类工作底稿 2-满意度类工作底稿 3-其他类工作底稿
            if("1".equals(workingManuscriptDesign.getIndexWorkingPaperType())){
                //根据idr查找 常规类底稿对象
                TIndexWorkingManuscriptDesignRoutine  designRoutine=getDesignRoutineByIdr(workingManuscriptDesign.getIdR());
                //存在问题
                workingManuscriptDesign.setExistingProblems(designRoutine.getExistingProblems());
            }else if("2".equals(workingManuscriptDesign.getIndexWorkingPaperType())){
                // 根据idr查找 满意度底稿对象
                TIndexWorkingManuscriptDesignSatisfaction designSatisfaction=getDesignSatisfactionByIdr(workingManuscriptDesign.getIdR());
                //存在问题
                workingManuscriptDesign.setExistingProblems(designSatisfaction.getExistingProblems());
            }
        }
        return workingManuscriptDesign;
    }
    /**
     * @Description: 根据idr查找 常规类底稿对象
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private TIndexWorkingManuscriptDesignRoutine  getDesignRoutineByIdr(Integer idR){
        EntityWrapper<TIndexWorkingManuscriptDesignRoutine> entity=new EntityWrapper<TIndexWorkingManuscriptDesignRoutine>();
        entity.eq("ID_R",idR);
        List<TIndexWorkingManuscriptDesignRoutine> list=tIndexWorkingManuscriptDesignRoutineMapper.selectList(entity);
        return list.get(0);
    }
    /**
     * @Description: 根据idr查找 满意度底稿对象
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private TIndexWorkingManuscriptDesignSatisfaction  getDesignSatisfactionByIdr(Integer idR){
        EntityWrapper<TIndexWorkingManuscriptDesignSatisfaction> entity=new EntityWrapper<TIndexWorkingManuscriptDesignSatisfaction>();
        entity.eq("ID_R",idR);
        List<TIndexWorkingManuscriptDesignSatisfaction> list=tIndexWorkingManuscriptDesignSatisfactionMapper.selectList(entity);
        return list.get(0);
    }
    /**
     * @Description: 根据idb查找 常规类底稿评分要点
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private List<TIndexWorkingManuscriptPoints>  findManuscriptPointsByIdC(Integer idC){
        EntityWrapper<TIndexWorkingManuscriptPoints> entity=new EntityWrapper<TIndexWorkingManuscriptPoints>();
        entity.eq("ID_C",idC);
        //根据idB查询指标说明（评分要点）集合
        List<TIndexWorkingManuscriptPoints> list=tIndexWorkingManuscriptPointsMapper.selectList(entity);
        return list;
    }
    /**
     * @Description: 根据idr查找 常规类底稿 集合
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private List<TIndexWorkingManuscriptDesignRoutine> findDesignRoutineListByIdR(Integer idR){
        EntityWrapper<TIndexWorkingManuscriptDesignRoutine> entity=new EntityWrapper<TIndexWorkingManuscriptDesignRoutine>();
        entity.eq("ID_R",idR);
        //根据idr查找 常规类底稿 集合
        List<TIndexWorkingManuscriptDesignRoutine> list=tIndexWorkingManuscriptDesignRoutineMapper.selectList(entity);
        return list;
    }



    /** 
     * @Description: 工作底稿信息    常规类底稿
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param: 
     * @Return: 
     */
    @Override
    public TIndexWorkingManuscriptDesignRoutine getInformationDesignRoutineByIdB(Integer idB,Integer idR,Integer year1,Integer year2) throws Exception {
        TIndexWorkingManuscriptDesignRoutine getNewInformationDesignRoutine=new TIndexWorkingManuscriptDesignRoutine();
        //根据idR查找 根据idR查找 常规类底稿 集合
        List<TIndexWorkingManuscriptDesignRoutine>  designRoutineList=findDesignRoutineListByIdR(idR);
        if(designRoutineList !=null && designRoutineList.size()>0){
            //如果已经保存过
           TIndexWorkingManuscriptDesignRoutine designRoutine=designRoutineList.get(0);
           //根据idc查找 常规类底稿评分要点集合
            List<TIndexWorkingManuscriptPoints> manuscriptPointsList= findManuscriptPointsByIdC(designRoutine.getIdC());
            Map<String,List<TIndexWorkingManuscriptPoints>> yearIndexScoringPointsMap= new HashMap<>();
            for(TIndexWorkingManuscriptPoints manuscriptPoints:manuscriptPointsList){
                List<TIndexWorkingManuscriptPoints> pointsList=yearIndexScoringPointsMap.get(manuscriptPoints.getPointsYears());
                if(pointsList==null){
                   pointsList=new  ArrayList<TIndexWorkingManuscriptPoints>();
                   pointsList.add(manuscriptPoints);
                }else{
                    pointsList.add(manuscriptPoints);
                }
                yearIndexScoringPointsMap.put(manuscriptPoints.getPointsYears(),pointsList);
            }
            designRoutine.setYearIndexScoringPointsMap(yearIndexScoringPointsMap);
        }else{
            //如果第一次新增，就组装好
            getNewInformationDesignRoutine=getNewInformationDesignRoutine(idR,year1,year2);
        }
        return getNewInformationDesignRoutine;
    }
    /**
     * @Description: 工作底稿信息    常规类底稿 如果第一次新增，就组装好
     * @Author: 安达
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    public TIndexWorkingManuscriptDesignRoutine getNewInformationDesignRoutine(Integer idB,Integer year1,Integer year2) throws Exception {
        //根据idb查找 评分要点 集合
        List<TIndexScoringPoints>  dseignList=findScoringPointsByIdb(idB);
        //创建常规类底稿对象
        TIndexWorkingManuscriptDesignRoutine   designRoutine =new TIndexWorkingManuscriptDesignRoutine();
        Map<String,List<TIndexWorkingManuscriptPoints>> yearIndexScoringPointsMap= new HashMap<>();
        for(int i=year1;i<=year2;i++){
            List<TIndexWorkingManuscriptPoints> manuscriptPointsList=new ArrayList<>();
            for(TIndexScoringPoints points:dseignList){
                TIndexWorkingManuscriptPoints manuscriptPoints=new TIndexWorkingManuscriptPoints();
                //评分要点活动名称
                manuscriptPoints.setPointsName(points.getPointsName());
                //设计常规类工作底稿时使用，默认值0, 0-未开展 1-已开展
                manuscriptPoints.setIsDevelopActivity("0");
                //评价年度
                manuscriptPoints.setPointsYears(i+"");
                manuscriptPointsList.add(manuscriptPoints);
            }
            yearIndexScoringPointsMap.put(i+"",manuscriptPointsList);
        }
        designRoutine.setYearIndexScoringPointsMap(yearIndexScoringPointsMap);
        return designRoutine;
    }

    /**
     * @Description: 根据idB查找 三级指标与指标底稿关系表 集合
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private List<RelationIndexWorkingManuscriptDesign> findManuscriptDesignListByIdR(Integer idB){
        EntityWrapper<RelationIndexWorkingManuscriptDesign> entity=new EntityWrapper<RelationIndexWorkingManuscriptDesign>();
        entity.eq("ID_B",idB);
        entity.orderBy("CREATE_TIME",false);
        //根据idB查询指标说明（评分要点）集合
        List<RelationIndexWorkingManuscriptDesign> list=relationIndexWorkingManuscriptDesignMapper.selectList(entity);
        return list;
    }
    /** 
     * @Description: 获取最高的版本号
     * @Author: 安达
     * @Date: 2021/3/29 20:07
     * @Param: 
     * @Return: 
     */
    private String getVersionNo(Integer idB){
        //根据idB查找 三级指标与指标底稿关系表 集合
        List<RelationIndexWorkingManuscriptDesign> manuscriptDesignList=findManuscriptDesignListByIdR(idB);
        //定义版本号
        StringBuilder versionBuilder=new StringBuilder("V");
        int size;
        if(manuscriptDesignList !=null || manuscriptDesignList.size()>0) {
            String versionNo=manuscriptDesignList.get(0).getVersionNo();
            //获取最高的版本号
            size = Integer.parseInt(versionNo.substring(1,versionNo.length()))+1;
        }else {
            size = 1;
        }
        versionBuilder.append(size);
        return versionBuilder.toString();
    }

    /**
     * 新增指标底稿关系且返回主键
     * @param idB
     * @param stauts
     * @param indexWorkingPaperType
     * @param user
     * @return
     * @throws Exception
     */
    private RelationIndexWorkingManuscriptDesign insertRelationIndexWorkingManuscriptDesign(Integer idA, Integer idB,String stauts,
                                                                                        String indexWorkingPaperType, ShiroUser user)  throws Exception{

        //项目外勤主管
        String fieldSupervisorId=tPerformanceWorkingGroupService.getFieldSupervisorIdByIdA(idA);
        //获取外勤主管信息
        RcUser ieldSupervisorUser=rcUserMapper.selectById(fieldSupervisorId);
        //创建三级指标与指标底稿关系对象
        RelationIndexWorkingManuscriptDesign insertBean=new RelationIndexWorkingManuscriptDesign();
        insertBean.setIdB(idB);
        //版本号
        String versionNo=getVersionNo(idB);
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
        //新增三级指标与指标底稿关系且返回主键
        relationIndexWorkingManuscriptDesignMapper.insert(insertBean);
        return insertBean;
    }
    /**
     * @Description: 保存常规类底稿
     * @Author: 安达
     * @Date: 2021/3/26 17:23
     * @Param idB  指标体系设计主键
     * @Param stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param indexWorkingManuscriptDesignRoutine  常规类底稿对象
     * @Param user 当前登陆者
     * @Return:
     */
    @Override
    public void saveDesignRoutine(Integer idB,String stauts,String indexWorkingPaperType,
           TIndexWorkingManuscriptDesignRoutine indexWorkingManuscriptDesignRoutine, ShiroUser user) throws Exception {
        //如果为空，就是新增
        if(indexWorkingManuscriptDesignRoutine.getIdR()==null){
            //根据 指标体系设计表idB查询项目idA
            Integer idA=relationProIndexMapper.getIdaByIdb(idB);
            //新增指标底稿关系且返回主键
            RelationIndexWorkingManuscriptDesign manuscriptDesign=insertRelationIndexWorkingManuscriptDesign(idA,idB,stauts,indexWorkingPaperType,user);
            //保存工作流
            bussinessFlowIndexWorkingManuscriptDesignService.saveCheckFlowUser(idA,manuscriptDesign.getIdR(),user);
            //设置外键
            indexWorkingManuscriptDesignRoutine.setIdR(manuscriptDesign.getIdR());
            //新增常规类底稿且返回主键
            tIndexWorkingManuscriptDesignRoutineMapper.insert(indexWorkingManuscriptDesignRoutine);
            //年份对应的评分要点
            Map<String,List<TIndexWorkingManuscriptPoints>> yearIndexScoringPointsMap=indexWorkingManuscriptDesignRoutine.getYearIndexScoringPointsMap();
            for(String year:yearIndexScoringPointsMap.keySet()){
                List<TIndexWorkingManuscriptPoints> pointsList=yearIndexScoringPointsMap.get(year);
                for(TIndexWorkingManuscriptPoints points:pointsList){
                    //设置外键
                    points.setIdC(indexWorkingManuscriptDesignRoutine.getIdC());
                    //新增 评分要点
                    tIndexWorkingManuscriptPointsMapper.insert(points);
                }
            }
        }else{
            //修改
            //修改常规类底稿
            tIndexWorkingManuscriptDesignRoutineMapper.updateById(indexWorkingManuscriptDesignRoutine);
            //年份对应的评分要点
            Map<String,List<TIndexWorkingManuscriptPoints>> yearIndexScoringPointsMap=indexWorkingManuscriptDesignRoutine.getYearIndexScoringPointsMap();
            for(String year:yearIndexScoringPointsMap.keySet()){
                List<TIndexWorkingManuscriptPoints> pointsList=yearIndexScoringPointsMap.get(year);
                for(TIndexWorkingManuscriptPoints points:pointsList){
                    //新增 评分要点
                    tIndexWorkingManuscriptPointsMapper.updateById(points);
                }
            }
        }
    }



    /**
     * @Description: 根据idr查找 满意度类底稿集合
     * @Author: 安达
     * @Date: 2021/3/25 11:35
     * @Param:
     * @Return:
     */
    private List<TIndexWorkingManuscriptDesignSatisfaction> findDesignSatisfactionListByIdR(Integer idR){
        EntityWrapper<TIndexWorkingManuscriptDesignSatisfaction> entity=new EntityWrapper<TIndexWorkingManuscriptDesignSatisfaction>();
        entity.eq("ID_R",idR);
        //根据idB查询满意度类底稿）集合
        List<TIndexWorkingManuscriptDesignSatisfaction> list=tIndexWorkingManuscriptDesignSatisfactionMapper.selectList(entity);
        return list;
    }
    /**
     * @Description: 工作底稿信息    满意度类底稿
     * @Author: 安达
     * @Date: 2021/3/26 10:37
     * @Param:
     * @Return:
     */
    @Override
    public List<TIndexWorkingManuscriptDesignSatisfaction> findSatisfactionListByIdB(Integer idB,Integer idR, Integer year1, Integer year2) throws Exception {
        //根据idR查找 根据idR查找 满意度底稿 集合
        List<TIndexWorkingManuscriptDesignSatisfaction>  designSatisfactionList=findDesignSatisfactionListByIdR(idR);
        if(designSatisfactionList !=null && designSatisfactionList.size()>0){
            return designSatisfactionList;
        }else{
            //如果第一次新增，就组装好
            designSatisfactionList=getNewSatisfactionList(idB,year1,year2);
            return designSatisfactionList;
        }
    }
    /**
     * @Description: 工作底稿信息    满意度底稿 如果第一次新增，就组装好
     * @Author: 安达ist
     * @Date: 2021/3/25 11:23
     * @Param:
     * @Return:
     */
    public  List<TIndexWorkingManuscriptDesignSatisfaction>  getNewSatisfactionList(Integer idB,Integer year1,Integer year2) throws Exception {
        //根据idb查找 评分要点 集合
        List<TIndexScoringPoints>  dseignList=findScoringPointsByIdb(idB);
        List<TIndexWorkingManuscriptDesignSatisfaction> designSatisfactionList=new ArrayList<>();
        for(int i=year1;i<=year2;i++){
            for(TIndexScoringPoints points:dseignList){
                //创建满意度底稿对象
                TIndexWorkingManuscriptDesignSatisfaction   designSatisfaction =new TIndexWorkingManuscriptDesignSatisfaction();
                //评分要点活动名称
                designSatisfaction.setPointsName(points.getPointsName());
                //评价年度
                designSatisfaction.setPointsYears(i+"");
                designSatisfactionList.add(designSatisfaction);
            }
        }
        return designSatisfactionList;
    }

    /**
     * @Description: 保存满意度底稿
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idB  指标体系设计主键
     * @Param stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param designSatisfactionList  满意度底稿集合
     * @Param user 当前登陆者
     * @Return:
     */
    @Override
    public void saveDesignSatisfaction(Integer idB,String stauts,String indexWorkingPaperType,
                                       List<TIndexWorkingManuscriptDesignSatisfaction>  designSatisfactionList, ShiroUser user) throws Exception {

        for(TIndexWorkingManuscriptDesignSatisfaction resignSatisfaction:designSatisfactionList){
            //如果为空，就是新增
            if(resignSatisfaction.getIdR()==null){
                //根据 指标体系设计表idB查询项目idA
                Integer idA=relationProIndexMapper.getIdaByIdb(idB);
                //新增指标底稿关系且返回主键
                RelationIndexWorkingManuscriptDesign manuscriptDesign=insertRelationIndexWorkingManuscriptDesign(idA,idB,stauts,indexWorkingPaperType,user);
                //保存工作流
                bussinessFlowIndexWorkingManuscriptDesignService.saveCheckFlowUser(idA,manuscriptDesign.getIdR(),user);
                //设置外键
                resignSatisfaction.setIdR(manuscriptDesign.getIdR());
                //新增常满意度底稿
                tIndexWorkingManuscriptDesignSatisfactionMapper.insert(resignSatisfaction);
            }else{
                //修改
                //修改满意度底稿
                tIndexWorkingManuscriptDesignSatisfactionMapper.updateById(resignSatisfaction);

            }
        }

    }

    /**
     * @Description: 工作底稿信息    其他类底稿
     * @Author: 安达
     * @Date: 2021/3/26 10:37
     * @Param:
     * @Return:
     */
    @Override
    public List<TIndexWorkingManuscriptDesignOther> findOtherListByIdR(Integer idR) throws Exception {
        //根据idR查找 根据idR查找 其他类底稿 集合
        EntityWrapper<TIndexWorkingManuscriptDesignOther> entity=new EntityWrapper<TIndexWorkingManuscriptDesignOther>();
        entity.eq("ID_R",idR);
        //根据idB查询指标说明（评分要点）集合
        List<TIndexWorkingManuscriptDesignOther> list=tIndexWorkingManuscriptDesignOtherMapper.selectList(entity);
        return list;

    }
    /**
     * @Description: 保存其他类底稿
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idB  指标体系设计主键
     * @Param stauts 版本状态
     * @Param indexWorkingPaperType  指标工作底稿类型
     * @Param designOtherList  其他类底稿集合
     * @Param user 当前登陆者
     * @Return:
     */
    @Override
    public void saveDesignOther(Integer idB,String stauts,String indexWorkingPaperType,
                                       List<TIndexWorkingManuscriptDesignOther>  designOtherList, ShiroUser user) throws Exception {
        Integer idR=designOtherList.get(0).getIdR();
        //如果为空，就是新增
        if(idR==null){
            //根据 指标体系设计表idB查询项目idA
            Integer idA=relationProIndexMapper.getIdaByIdb(idB);
            //新增指标底稿关系且返回主键
            RelationIndexWorkingManuscriptDesign manuscriptDesign=insertRelationIndexWorkingManuscriptDesign(idA,idB,stauts,indexWorkingPaperType,user);
            idR=manuscriptDesign.getIdR();
            //保存工作流
            bussinessFlowIndexWorkingManuscriptDesignService.saveCheckFlowUser(idA,manuscriptDesign.getIdR(),user);
        }
        for(TIndexWorkingManuscriptDesignOther designOther:designOtherList){
            //设置外键
            designOther.setIdR(idR);
            //修改问题建议类底稿
            tIndexWorkingManuscriptDesignOtherMapper.updateById(designOther);
        }

    }
}
