package com.common.business.planmgr.indexdesign.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.collection.meanslist.mapper.TDevelopmentInformationListMapper;
import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.library.indexs.mapper.TLibraryIndexSystemMapper;
import com.common.business.planmgr.indexcheck.entity.RelationProIndex;
import com.common.business.planmgr.indexcheck.mapper.RelationProIndexMapper;
import com.common.business.planmgr.indexcheck.service.RelationProIndexService;
import com.common.business.planmgr.indexdesign.entity.*;
import com.common.business.planmgr.indexdesign.mapper.*;
import com.common.business.planmgr.indexdesign.service.TIndexSystemDseignService;
import com.common.business.planmgr.indexdraftdesign.service.RelationIndexWorkingManuscriptDesignService;
import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.mapper.TEvalUnitInfoMapper;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 指标体系设计表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-16
 */
@Service
@Transactional
public class TIndexSystemDseignServiceImpl extends ServiceImpl<TIndexSystemDseignMapper, TIndexSystemDseign> implements TIndexSystemDseignService {

    @Autowired
    private TIndexSystemDseignMapper tIndexSystemDseignMapper;
    @Autowired
    private TLibraryIndexSystemMapper tLibraryIndexSystemMapper;
    @Autowired
    private TDevelopmentInformationListMapper tDevelopmentInformationListMapper;
    @Autowired
    private TIndexScoringPointsMapper tIndexScoringPointsMapper;
    @Autowired
    private TScoringStandardsAndRuilesDetailMapper tScoringStandardsAndRuilesDetailMapper;
    @Autowired
    private TEvidencePoolsMapper tEvidencePoolsMapper;
    @Autowired
    private RelationProIndexMapper relationProIndexMapper;
    @Autowired
    private RelationProIndexService relationProIndexService;
    @Autowired
    private TPerformanceWorkingGroupService tPerformanceWorkingGroupService;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Autowired
    private RelationIndexWorkingManuscriptDesignService relationIndexWorkingManuscriptDesignService;
    @Autowired
    private TIndexSystemTempMapper tIndexSystemTempMapper;
    @Autowired
    private TAssessmentObjectByIndexMapper tAssessmentObjectByIndexMapper;
    @Autowired
    private TEvalUnitInfoMapper tEvalUnitInfoMapper;
    @Autowired
    private TAssessmentObjectByPointsMapper tAssessmentObjectByPointsMapper;

    /**
     * @Description: 根据idA查询指标体系列表
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @return
     * @throws Exception
     */
    @Override
    public List<TIndexSystemDseign> findIndexSystemDseignListByIdA(Integer idA) throws Exception {
        //根据idA查询项目指标体系关系
        EntityWrapper  relationEntity=new EntityWrapper();
        relationEntity.eq("ID_A",idA);
        relationEntity.orderBy("CREATE_TIME",true);
        List<RelationProIndex> relationList=relationProIndexMapper.selectList(relationEntity);
        if(relationList !=null && relationList.size()>0){
            Integer idR=relationList.get(0).getIdR();
            List<TIndexSystemDseign> indexSystemDseignList= relationIndexWorkingManuscriptDesignService.findIndexSystemDseignListByIdR(idR);
            return indexSystemDseignList;
        }else{
            return findTIndexSystemTemp();
        }
    }

    /**
     * @Description: 项目支出绩效评价指标库列表
     * @author:安达
     * @date:2021年3月9日 9：51
     * @return
     * @throws Exception
     */
    @Override
    public List<TIndexSystemDseign> findTLibraryIndexSystem( List<TIndexSystemDseign> oldList,String deleteCodeStr) throws Exception {
        //获取被排除的指标Map
        Map<String,TIndexSystemDseign> excludeCodeMap=getExcludeCodeMap(oldList,deleteCodeStr);
        //获取指标库集合
        EntityWrapper entity=new EntityWrapper();
        List<TLibraryIndexSystem> libraryList=tLibraryIndexSystemMapper.selectList(entity);
        //指标库数据转换为指标集合
        List<TIndexSystemDseign> indexSystemDseignList=TLibraryIndexSystemToTIndexSystemDseign(libraryList,excludeCodeMap);
        return indexSystemDseignList;
    }

    /**
     * @Description: 指标库数据转换为指标集合
      * @param libraryList
     * @param excludeCodeMap
     * @return
     */
    private List<TIndexSystemDseign> TLibraryIndexSystemToTIndexSystemDseign(List<TLibraryIndexSystem> libraryList,Map<String,TIndexSystemDseign> excludeCodeMap){
        List<TIndexSystemDseign> indexSystemDseignList=new ArrayList<>();
        //循环三级指标
        for(TLibraryIndexSystem library:libraryList){
            if(excludeCodeMap.get(library.getIndexCode3()) !=null){
                continue;
            }
            TIndexSystemDseign indexSystemDseign=new TIndexSystemDseign();
            //三级指标编码
            indexSystemDseign.setIndexCode3(library.getIndexCode3());
            //三级指标名称
            indexSystemDseign.setIndexName3(library.getIndexName3());
            //二级指标编码
            indexSystemDseign.setIndexCode2(library.getIndexCode2());
            //二级指标名称
            indexSystemDseign.setIndexName2(library.getIndexName2());
            //一级指标编码
            indexSystemDseign.setIndexCode1(library.getIndexCode1());
            //一级指标名称
            indexSystemDseign.setIndexName1(library.getIndexName1());
            //三级指标分值
            indexSystemDseign.setIndexScore3(library.getIndexScore3());
            //指标解释（三级）
            indexSystemDseign.setIndexExplanation(library.getIndexExplanation());
            //预算支出功能分类
            indexSystemDseign.setBudFunctClassName(library.getBudFunctClassName());
            //国民经济行业分类
            indexSystemDseign.setNationEcoIndustClassName(library.getNationEcoIndustClassName());
            //指标类别，0-请选择  1-共性  2-个性
            indexSystemDseign.setIndexType(library.getIndexType());
            indexSystemDseignList.add(indexSystemDseign);
        }
        return indexSystemDseignList;
    }
    /**
     * @Description: 获取被排除掉的codeMap
     * @param oldList
     * @param deleteCodeStr
     * @return
     */
    private Map<String,TIndexSystemDseign> getExcludeCodeMap(List<TIndexSystemDseign> oldList,String deleteCodeStr){
        Map<String,TIndexSystemDseign> excludeCodeMap=new HashMap<>();
        Map<String,String> deleteCodeMa=new HashMap<>();
        if(StringUtils.isNotEmpty(deleteCodeStr)){
            String[] deleteCodes=deleteCodeStr.split(",");
            for(String code:deleteCodes){
                deleteCodeMa.put(code,code);
            }
        }
        if(oldList !=null ){
            for(TIndexSystemDseign dseign:oldList){
                if(deleteCodeMa.get(dseign.getIndexCode3())!=null){
                    continue;
                }
                excludeCodeMap.put(dseign.getIndexCode3(),dseign);
            }
        }

        return excludeCodeMap;
    }

    /**
     * @Description: 绩效指标库模板
     * @return
     */
    private List<TIndexSystemDseign>  findTIndexSystemTemp()  throws Exception{
        EntityWrapper entity=new EntityWrapper();
        //entity.eq("INDEX_TYPE","1");
        List<TIndexSystemTemp> tempList=tIndexSystemTempMapper.selectList(entity);
        //指标库数据转换为指标集合
        List<TIndexSystemDseign> indexSystemDseignList=TIndexSystemTempToTIndexSystemDseign(tempList);
        return indexSystemDseignList;
    }
    /**
     * @Description: 绩效指标库模板转换为指标集合
     * @param tempList
     * @return
     */
    private List<TIndexSystemDseign> TIndexSystemTempToTIndexSystemDseign(List<TIndexSystemTemp> tempList){
        List<TIndexSystemDseign> indexSystemDseignList=new ArrayList<>();
        //循环三级指标
        for(TIndexSystemTemp temp:tempList){
            TIndexSystemDseign indexSystemDseign=new TIndexSystemDseign();
            //三级指标编码
            indexSystemDseign.setIndexCode3(temp.getIndexCode3());
            //三级指标名称
            indexSystemDseign.setIndexName3(temp.getIndexName3());
            //二级指标编码
            indexSystemDseign.setIndexCode2(temp.getIndexCode2());
            //二级指标名称
            indexSystemDseign.setIndexName2(temp.getIndexName2());
            //一级指标编码
            indexSystemDseign.setIndexCode1(temp.getIndexCode1());
            //一级指标名称
            indexSystemDseign.setIndexName1(temp.getIndexName1());
            //三级指标分值
            indexSystemDseign.setIndexScore3(temp.getIndexScore());
            //指标解释（三级）
            indexSystemDseign.setIndexExplanation(temp.getIndexExplanation());
            //指标类别，0-请选择  1-共性  2-个性
            indexSystemDseign.setIndexType("1");
            indexSystemDseignList.add(indexSystemDseign);
        }
        return indexSystemDseignList;
    }
    /**
     * @Description:根据项目idB查询指标体系对象
     * @Author: 安达
     * @Date: 2021/3/18 16:19
     * @Param:
     * @Return:
     */
    @Override
    public TIndexSystemDseign getTLibraryIndexSystemByIdB(Integer idB) throws Exception {
        TIndexSystemDseign bean=tIndexSystemDseignMapper.selectById(idB);
        return bean;
    }

    /**
     * @Description:  获取一级指标集合
     * @Author: 安达
     * @Date: 2021/3/18 9:36
     * @Param:
     * @Return:
     */
    @Override
    public List<TLibraryIndexSystem> findLevelOneIndexList() throws Exception {
        EntityWrapper entity=new EntityWrapper();
        entity.groupBy("INDEX_NAME_1");
        List<TLibraryIndexSystem> libraryList=tLibraryIndexSystemMapper.selectList(entity);
        return libraryList;
    }

    /**
     * @Description:  获取二级指标集合
     * @Author: 安达
     * @Date: 2021/3/18 9:36
     * @Param:
     * @Return:
     */
    @Override
    public List<TLibraryIndexSystem> findLevelTwoIndexList() throws Exception {
        EntityWrapper entity=new EntityWrapper();
        entity.groupBy("INDEX_NAME_2");
        List<TLibraryIndexSystem> libraryList=tLibraryIndexSystemMapper.selectList(entity);
        return libraryList;
    }

    /**
     * 根据idB查询指标说明（评分要点）列表
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idB
     * @return
     * @throws Exception
     */
    @Override
    public List<TIndexScoringPoints> findFIndexDescScoringPointsListByIdB(Integer idB) throws Exception {

        EntityWrapper<TIndexScoringPoints> entity=new EntityWrapper<TIndexScoringPoints>();
        entity.eq("ID_B",idB);
        //根据idB查询指标说明（评分要点）集合
        List<TIndexScoringPoints> list= tIndexScoringPointsMapper.selectList(entity);
        for(TIndexScoringPoints point:list){
            EntityWrapper<TScoringStandardsAndRuilesDetail> detailEntity=new EntityWrapper<TScoringStandardsAndRuilesDetail>();
            detailEntity.eq("ID_C",point.getIdC());
            //根据idC查询评分标准及评分规则明细集合
            List<TScoringStandardsAndRuilesDetail> detailList=tScoringStandardsAndRuilesDetailMapper.selectList(detailEntity);
            point.setScoringStandardsAndRuilesDetailList(detailList);

            EntityWrapper tAssessmentObjectByPointsEntity=new EntityWrapper();
            tAssessmentObjectByPointsEntity.eq("ID_C",point.getIdC());
            //根据idC查询评分要点考核对象集合
            List<TAssessmentObjectByPoints> tAssessmentObjectByPointsList=tAssessmentObjectByPointsMapper.selectList(tAssessmentObjectByPointsEntity);
            point.setAssessmentObjectByPointsList(tAssessmentObjectByPointsList);
        }
        return list;
    }


    /**
     * @Description: 考核对象集合
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    @Override
    public List<TAssessmentObjectByIndex> findEvalUnitList(Integer idA,List<TAssessmentObjectByIndex> oldObjectList,String deleteIdStr)throws Exception{
        //获取被排除的指标Map
        Map<String,TAssessmentObjectByIndex> excludeIdMap=getExcludeIdMap(oldObjectList,deleteIdStr);
        //获取指标库集合
        EntityWrapper entity=new EntityWrapper();
        entity.eq("ID_A",idA);
        List<TEvalUnitInfo> evalUnitInfoList=tEvalUnitInfoMapper.selectList(entity);
        //指标库数据转换为指标集合
        List<TAssessmentObjectByIndex> assessmentObjectByIndexList=TEvalUnitInfoToAssessmentObject(evalUnitInfoList,excludeIdMap);
        return assessmentObjectByIndexList;
    }
    /**
     * @Description: 专家集合转换成评价对象
     * @param evalUnitInfoList
     * @return
     */
    private List<TAssessmentObjectByIndex> TEvalUnitInfoToAssessmentObject(List<TEvalUnitInfo> evalUnitInfoList, Map<String,TAssessmentObjectByIndex> excludeIdMap){
        List<TAssessmentObjectByIndex> assessmentObjectByIndexList=new ArrayList<>();
        for(TEvalUnitInfo info:evalUnitInfoList){
            if(excludeIdMap.get(info.getId()+"") !=null){
                continue;
            }
            TAssessmentObjectByIndex assessmentObjectByIndex=new TAssessmentObjectByIndex();
            assessmentObjectByIndex.setAssessmentObjectId(info.getId()+"");
            assessmentObjectByIndex.setAssessmentObjectName(info.getUnitName());
        }

        return assessmentObjectByIndexList;
    }


    /**
     * @Description: 获取被排除掉的IdMap
     * @param oldObjectList
     * @param deleteIdStr
     * @return
     */
    private Map<String,TAssessmentObjectByIndex> getExcludeIdMap(List<TAssessmentObjectByIndex> oldObjectList,String deleteIdStr){
        Map<String,TAssessmentObjectByIndex> excludeIdMap=new HashMap<>();
        Map<String,String> deleteIdMap=new HashMap<>();
        if(StringUtils.isNotEmpty(deleteIdStr)){
            String[] deleteIds=deleteIdStr.split(",");
            for(String id:deleteIds){
                deleteIdMap.put(id,id);
            }
        }
        if(oldObjectList !=null ){
            for(TAssessmentObjectByIndex index:oldObjectList){
                if(deleteIdMap.get(index.getAssessmentObjectId()+"")!=null){
                    continue;
                }
                excludeIdMap.put(index.getAssessmentObjectId(),index);
            }
        }

        return excludeIdMap;
    }
    /**
     * @Description: 根据idB查询选中的考核对象集合
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    @Override
    public List<TAssessmentObjectByIndex> findTAssessmentObjectByIndexList(Integer idB)throws Exception{
        EntityWrapper entity=new EntityWrapper();
        entity.eq("ID_B",idB);
        List<TAssessmentObjectByIndex> list=tAssessmentObjectByIndexMapper.selectList(entity);
        return list;
    }
    /**
     * @Description: 根据idC查询评分要点考核对象集合
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    @Override
    public List<TAssessmentObjectByPoints> findTAssessmentObjectByPointsList(Integer idC)throws Exception{
        EntityWrapper entity=new EntityWrapper();
        entity.eq("ID_C",idC);
        List<TAssessmentObjectByPoints> list=tAssessmentObjectByIndexMapper.selectList(entity);
        return list;
    }
    /**
     * @Description: 根据idA查询资料清单集合，且把资料清单转换为佐证材料池对象
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    @Override
    public List<TEvidencePools> findTEvidencePoolsListByIdA(Integer idA)throws Exception{
        List<TEvidencePools> evidencePoolsList=tEvidencePoolsMapper.findTEvidencePoolsListByIdA(idA);
        return evidencePoolsList;
    }
    /**
     * 根据idA查询佐证材料池
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    @Override
    public List<TEvidencePools> findEvidencePool(Integer idA,List<TEvidencePools> oldList,String deleteIdStr) throws Exception {
        //根据idA查询资料清单集合，且把资料清单转换为佐证材料池对象
        List<TEvidencePools> evidencePoolsList=tEvidencePoolsMapper.findTEvidencePoolsListByIdA(idA);
        //type1和bean的map
        Map<String,TEvidencePools> type1BeanMap=new HashMap<>();
        //type2和bean的map
        Map<String,TEvidencePools> type2BeanMap=new HashMap<>();
        //第一级的集合
        List<TEvidencePools> plist =new ArrayList<>();
        for(TEvidencePools bean:evidencePoolsList){
            //存入 资料大分类和bean的map
            if(type1BeanMap.get(bean.getInfoType1())==null){
                TEvidencePools type1Bean=new TEvidencePools();
                type1Bean.setInfoType1(bean.getInfoType1());
                type1BeanMap.put(bean.getInfoType1(),type1Bean);
            }
            if(type2BeanMap.get(bean.getInfoType1()+bean.getInfoType2()) ==null){
                //存入 资料大分类二级分类和bean的map
                TEvidencePools type2Bean=new TEvidencePools();
                type2Bean.setInfoType1(bean.getInfoType1());
                type2Bean.setInfoType2(bean.getInfoType2());
                type2BeanMap.put(bean.getInfoType1()+bean.getInfoType2(),type2Bean);
            }
        }
        //循环资料名称集合，把对象存入map里的父对象children里c
        type2BeanMap=gettype2BeanMap( evidencePoolsList, type2BeanMap, oldList,deleteIdStr);
        //循环二级分类map，把值装入大分类children
        for(String key:type2BeanMap.keySet()){
            TEvidencePools type2Bean=type2BeanMap.get(key);
            TEvidencePools type1Bean=type1BeanMap.get(type2Bean.getInfoType1());
            if(type1Bean !=null){
                //如果这个节点存在父节点，父节点就把它装入子节点
                type1Bean.getChildren().add(type2Bean);
                //再把父节点装回去
                type1BeanMap.put(type2Bean.getInfoType1(),type1Bean);
            }
        }
        //循环支出大分类map，装入list集合返回
        for(String key:type1BeanMap.keySet()){
            TEvidencePools type1Bean=type1BeanMap.get(key);
            plist.add(type1Bean);
        }
        return plist;
    }

    //type2和bean的map
    private  Map<String,TEvidencePools> gettype2BeanMap(List<TEvidencePools> list,Map<String,TEvidencePools> type2BeanMap,
                                                                     List<TEvidencePools> oldList,String deleteIdStr) throws Exception{
        Map<String,String> haveBeenChooseMap=new HashMap<>();
        Map<String,String> deleteIdMap=new HashMap<>();
        if(StringUtils.isNotEmpty(deleteIdStr)){
            String[] deleteIds=deleteIdStr.split(",");
            for(String id:deleteIds){
                deleteIdMap.put(id,id);
            }
        }
        if(oldList !=null ){
            for(TEvidencePools tEvidencePools:oldList){
                if(deleteIdMap.get(tEvidencePools.getIdC()+"")!=null){
                    continue;
                }
                //资料一级分类+资料二级分类+资料名称 :资料名称   键值对
                haveBeenChooseMap.put(tEvidencePools.getInfoType1()+tEvidencePools.getInfoType2()+tEvidencePools.getInfoName(),tEvidencePools.getInfoName());
            }
        }

        //循环资料名称集合，把对象存入map里的父对象children里c
        for(TEvidencePools child:list){
            TEvidencePools type2Bean=type2BeanMap.get(child.getInfoType1()+child.getInfoType2());
            if(type2Bean !=null){
                //默认不选择
                child.setHaveBeenChoose("0");
                if(haveBeenChooseMap.get(child.getInfoType1()+child.getInfoType2()+child.getInfoName()) !=null){
                    //如果已经被选择过
                    child.setHaveBeenChoose("1");
                }
                //如果这个节点存在父节点，父节点就把它装入子节点
                type2Bean.getChildren().add(child);
                //再把父节点装回去
                type2BeanMap.put(child.getInfoType1()+child.getInfoType2(),type2Bean);
            }
        }

        return type2BeanMap;
    }

    /**
     * @Description: 根据idB查询已经选择的佐证材料
     * @Author: 安达
     * @Date: 2021/3/18 11:52
     * @Param:
     * @Return:
     */
    @Override
    public List<TEvidencePools> findTEvidencePoolsListByIdB(Integer idB)throws Exception{
        //查询这个指标体系已经选择过的佐证材料
        EntityWrapper<TEvidencePools> entity=new EntityWrapper<TEvidencePools>();
        //指标清单id
        entity.eq("ID_B",idB);
        List<TEvidencePools>  tEvidencePoolsList=tEvidencePoolsMapper.selectList(entity);
        return tEvidencePoolsList;
    }


    /**
     * @Description: 保存指标体系设计
     * @Author: 安达
     * @Date: 2021/3/29 14:01
     * @Param idA  子项目id
     * @Param stauts 版本状态
     * @param  assessmentObjectList 考核对象集合
     * @Param evidencePoolsList  佐证材料池集合
     * @Param scoringPointsList  评分要点集合
     * @Param systemDseign  绩效指标对象
     * @Param user 当前登陆者
     * @Return:
     */
    @Override
    public TIndexSystemDseign saveSystemDseign( List<TAssessmentObjectByIndex> assessmentObjectList, List<TEvidencePools> evidencePoolsList,
                                 List<TIndexScoringPoints> scoringPointsList, TIndexSystemDseign  systemDseign) throws Exception {
        //设置考核对象集合
        systemDseign.setAssessmentObjectList(assessmentObjectList);
        //设置佐证材料
        systemDseign.setEvidencePoolsList(evidencePoolsList);
        //设置评分要点集合
        systemDseign.setScoringPointsList(scoringPointsList);
        return systemDseign;
    }
    /**
     * 指标信息组装
     * @param indexSystemDseignList
     * @return
     */
    @Override
    public List<String[]> getTableInfo(List<TIndexSystemDseign> indexSystemDseignList) {
        List<String[]> arrlist = new ArrayList<>();
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        for (int i = 0; i < indexSystemDseignList.size(); i++) {
            TIndexSystemDseign indexSystemDseign = indexSystemDseignList.get(i);
            Double pointsScore = indexSystemDseign.getPointsScore();
            arrlist.add(new String[]{indexSystemDseign.getIndexName1(),null,indexSystemDseign.getIndexName2(),null,indexSystemDseign.getIndexName3(),String.valueOf(indexSystemDseign.getPointsScore()),indexSystemDseign.getIndexExplanation(),indexSystemDseign.getScoringPoints()});
//            hashMap.put(indexSystemDseign.getIndexName1(),indexSystemDseign);
        }
        //获取该组绩效指标里面不同一级分类分值合计
        List<String[]> sumindex1List = baseMapper.sumIndexScore3ByIndex1(indexSystemDseignList.get(0).getIdR());
        for (int i = 0; i < sumindex1List.size(); i++) {
            //一级分类名称
            String indexName = sumindex1List.get(i)[0];
            //分数合计
            Double pointsScore = Double.valueOf(sumindex1List.get(i)[1]);
            for (int j = 0; j < arrlist.size(); j++) {
                if (indexName.equals(arrlist.get(j)[0])){
                    arrlist.get(j)[1] = String.valueOf(pointsScore);
                }else {
                    continue;
                }
            }
        }
        //获取该组绩效指标里面不同二级分类分值合计
        for (int i = 0; i < arrlist.size(); i++) {
            //一级分类名称
            String index1Name = arrlist.get(i)[0];
            List<String[]> sumindex2List = baseMapper.sumIndexScore3ByIndex2(indexSystemDseignList.get(0).getIdR(),arrlist.get(i)[0]);
            for (int j = 0; j < sumindex2List.size(); j++) {
                //一级二级指标名称都相同
                if (index1Name.equals(sumindex2List.get(j)[0]) && arrlist.get(i)[2].equals(sumindex2List.get(j)[1])){
                    arrlist.get(i)[3] = sumindex2List.get(j)[2];
                }
            }
        }

        return arrlist;
    }
}
