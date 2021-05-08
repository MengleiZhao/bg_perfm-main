package com.common.business.planmgr.scheme.mkscheme.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.collection.meanslist.mapper.RelationProListMapper;
import com.common.business.collection.meanslist.mapper.TDevelopmentInformationListMapper;
import com.common.business.planmgr.indexcheck.entity.RelationProIndex;
import com.common.business.planmgr.indexcheck.mapper.RelationProIndexMapper;
import com.common.business.planmgr.indexdesign.entity.TIndexSystemDseign;
import com.common.business.planmgr.indexdesign.mapper.TIndexSystemDseignMapper;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mkoutline.entity.RelationProResearchOutline;
import com.common.business.planmgr.pre.mkoutline.entity.TResearchOutline;
import com.common.business.planmgr.pre.mkoutline.mapper.RelationProResearchOutlineMapper;
import com.common.business.planmgr.pre.mkoutline.mapper.TResearchOutlineMapper;
import com.common.business.planmgr.pre.mkoutline.service.RelationProResearchOutlineService;
import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.entity.TPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.mapper.RelationProPreparEvalWorkPlanMapper;
import com.common.business.planmgr.scheme.mkscheme.mapper.TPreparEvalWorkPlanMapper;
import com.common.business.planmgr.scheme.mkscheme.service.RelationProPreparEvalWorkPlanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.scheme.mkscheme.web.RelationProPreparEvalWorkPlanVo;
import com.common.business.planmgr.schemecheck.entity.BussinessFlowPreparEvalWorkPlan;
import com.common.business.planmgr.schemecheck.entity.TPreparEvalWorkPlanCheckRec;
import com.common.business.planmgr.schemecheck.mapper.TPreparEvalWorkPlanCheckRecMapper;
import com.common.business.planmgr.schemecheck.service.BussinessFlowPreparEvalWorkPlanService;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.system.entity.EntityDao;
import com.common.system.exception.ServiceException;
import com.common.system.page.MsgCode;
import com.common.system.page.Result;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.FileUpLoadUtil;
import com.common.system.util.NumberUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 *  项目编制评价工作方案关系表
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@Service
@Transactional
public class RelationProPreparEvalWorkPlanServiceImpl extends ServiceImpl<RelationProPreparEvalWorkPlanMapper, RelationProPreparEvalWorkPlan> implements RelationProPreparEvalWorkPlanService {

    @Autowired
    private TPreparEvalWorkPlanMapper preparEvalWorkPlanMapper;
    @Autowired
    private BussinessFlowPreparEvalWorkPlanService bussinessFlowPreparEvalWorkPlanService;
    @Autowired
    private TPreparEvalWorkPlanCheckRecMapper preparEvalWorkPlanCheckRecMapper;
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;
    @Autowired
    private RelationProIndexMapper relationProIndexMapper;
    @Autowired
    private TIndexSystemDseignMapper indexSystemDseignMapper;
    @Autowired
    private RelationProResearchOutlineMapper relationProResearchOutlineMapper;
    @Autowired
    private TResearchOutlineMapper researchOutlineMapper;
    @Autowired
    private TDevelopmentInformationListMapper developmentInformationListMapper;
    @Autowired
    private RelationProListMapper relationProListMapper;

    /**
     * @Title:
     * @Description: 项目编制评价工作方案列表分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/8 17:23
     * @updater: 陈睿超
     * @updateDate: 2021/4/8 17:23
     * @param pageNum
     * @param pageSize
     * @param search 模糊查询
     * @param bean 精确查询
     * @param user 当前用户
     * @return
     **/
    @Override
    public PageInfo<RelationProPreparEvalWorkPlan> pagelist(Integer pageNum, Integer pageSize, String search, RelationProPreparEvalWorkPlan bean, ShiroUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProPreparEvalWorkPlan> list = baseMapper.pageList(pageNum, pageSize, search, bean, user);
        PageInfo<RelationProPreparEvalWorkPlan> pageInfo = new PageInfo<RelationProPreparEvalWorkPlan>(list);
        return pageInfo;
    }

    /**
     * Title: 
     * Description: 待审批分页查询
     * @author: 陈睿超
     * @createDate: 2021/4/21 20:57
     * @updater: 陈睿超
     * @updateDate: 2021/4/21 20:57
     * @param pageNum
     * @param pageSize
     * @param search 模糊查询
     * @param bean 精确查询
     * @param user 当前用户
     * @return
     **/
    @Override
    public PageInfo<RelationProPreparEvalWorkPlan> checkPagelist(Integer pageNum, Integer pageSize, String search, RelationProPreparEvalWorkPlan bean, ShiroUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProPreparEvalWorkPlan> list = baseMapper.pageList(pageNum, pageSize, search, bean, user);
        PageInfo<RelationProPreparEvalWorkPlan> pageInfo = new PageInfo<RelationProPreparEvalWorkPlan>(list);
        return pageInfo;
    }

    /**
     * 保存
     * @author: 陈睿超
     * @createDate: 2021/4/15 20:53
     * @updater: 陈睿超
     * @updateDate: 2021/4/15 20:53
     * @param relationProPreparEvalWorkPlanVo:编制评价工作方案接受数据类
     * @param user:当前登录人
     * @return 成功返回true,失败返回false
     **/
    @Override
    public Boolean save(RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo, ShiroUser user) {
        //项目编制评价工作方案关系
        RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan = relationProPreparEvalWorkPlanVo.getRelationProPreparEvalWorkPlan();
        //编制评价工作方案集合
        List<TPreparEvalWorkPlan> preparEvalWorkPlanList = relationProPreparEvalWorkPlanVo.getPreparEvalWorkPlanList();
        String addOrUpdate = null;
        if (StringUtils.isEmpty(relationProPreparEvalWorkPlan.getIdR())){
            //新增
            addOrUpdate = "add";
            //版本号
            //查询同个项目下有多少个版本
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_A",relationProPreparEvalWorkPlan.getIdA());
            entityWrapper.eq("RELATION_STATUS","1");
            entityWrapper.orderBy("CREATE_TIME");
            List<RelationProPreparEvalWorkPlan> versionNolist = selectList(entityWrapper);
            if (0 == versionNolist.size()) {
                relationProPreparEvalWorkPlan.setVersionNo("V1");
            }else {
                String versionNo = versionNolist.get(versionNolist.size()-1).getVersionNo();
                String num = versionNo.substring(1, versionNo.length());
                relationProPreparEvalWorkPlan.setVersionNo("V"+(Integer.parseInt(num)+1));
            }
            relationProPreparEvalWorkPlan.setCreateTime(new Date());
            relationProPreparEvalWorkPlan.setCreateUaseName(user.getName());
            relationProPreparEvalWorkPlan.setCreateUaseId(user.getId().toString());
            relationProPreparEvalWorkPlan.setRelationStatus("1");
            //保存编制方案关系
            insert(relationProPreparEvalWorkPlan);
            //保存编制评价工作方案
            for (int i = 0; i < preparEvalWorkPlanList.size(); i++) {
                TPreparEvalWorkPlan preparEvalWorkPlan = preparEvalWorkPlanMapper.selectById(preparEvalWorkPlanList.get(i).getIdB());
                preparEvalWorkPlan.setIdR(relationProPreparEvalWorkPlan.getIdR());
                preparEvalWorkPlanMapper.updateById(preparEvalWorkPlan);
            }
            //保存新工作流保存
            List<BussinessFlowPreparEvalWorkPlan> workFlowList = bussinessFlowPreparEvalWorkPlanService.getWorkFlow(relationProPreparEvalWorkPlan, user);
            for (int i = 0; i < workFlowList.size(); i++) {
                bussinessFlowPreparEvalWorkPlanService.insert(workFlowList.get(i));
            }
        }else{
            //修改
            addOrUpdate = "update";
            RelationProPreparEvalWorkPlan oldbean = selectById(relationProPreparEvalWorkPlan.getIdR());
            oldbean.setCreateTime(new Date());
            oldbean.setCreateStauts(relationProPreparEvalWorkPlan.getCreateStauts());
            relationProPreparEvalWorkPlan = oldbean;
            updateById(oldbean);
            //删除不需要的
            String ids = null;
            for (int i = 0; i < preparEvalWorkPlanList.size(); i++) {
                ids = ids + "," + preparEvalWorkPlanList.get(i).getIdB().toString();
            }
            ids = ids.substring(1,ids.length()-1);
            EntityWrapper idsEntityWrapper = new EntityWrapper();
            idsEntityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
            idsEntityWrapper.notIn("ID_B",ids);
            List<TPreparEvalWorkPlan> deleteList = preparEvalWorkPlanMapper.selectList(idsEntityWrapper);
            for (TPreparEvalWorkPlan tPreparEvalWorkPlan : deleteList) {
                preparEvalWorkPlanMapper.deleteById(tPreparEvalWorkPlan.getIdB());
            }
            //保存最新的
            for (int i = 0; i < preparEvalWorkPlanList.size(); i++) {
                TPreparEvalWorkPlan preparEvalWorkPlan = preparEvalWorkPlanMapper.selectById(preparEvalWorkPlanList.get(i).getIdB());
                preparEvalWorkPlan.setIdR(relationProPreparEvalWorkPlan.getIdR());
                preparEvalWorkPlanMapper.updateById(preparEvalWorkPlan);
            }
        }

        //判断是否送审
        if ("1".equals(relationProPreparEvalWorkPlan.getCreateStauts())){
            //因为新增的时候已经根据送审还是暂存调整了工作流的状态，所以这里只要判断修改的情况就可以了
            if ("update".equals(addOrUpdate)) {
                //查询第一个节点
                EntityWrapper currentEntityWrapper = new EntityWrapper();
                currentEntityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
                currentEntityWrapper.eq("ORDER_OF_CURRENT_NODE",1);
                BussinessFlowPreparEvalWorkPlan currentWorkFlow = bussinessFlowPreparEvalWorkPlanService.selectOne(currentEntityWrapper);
                //查询下一个节点
                EntityWrapper secondEntityWrapper = new EntityWrapper();
                secondEntityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
                secondEntityWrapper.eq("ORDER_OF_CURRENT_NODE",2);
                BussinessFlowPreparEvalWorkPlan secondWorkFlow = bussinessFlowPreparEvalWorkPlanService.selectOne(secondEntityWrapper);
                currentWorkFlow.setNodeIsActive("0");
                currentWorkFlow.setCurrentNodeStatus("1");
                secondWorkFlow.setNodeIsActive("1");
                //保存当前节点和下一个节点
                bussinessFlowPreparEvalWorkPlanService.updateById(currentWorkFlow);
                bussinessFlowPreparEvalWorkPlanService.updateById(secondWorkFlow);
            }
            //重置审批记录
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
            entityWrapper.eq("CHECK_DATA_STATUS","1");
            List<TPreparEvalWorkPlanCheckRec> checkList = preparEvalWorkPlanCheckRecMapper.selectList(entityWrapper);
            for (int i = 0; i < checkList.size(); i++) {
                TPreparEvalWorkPlanCheckRec check = checkList.get(i);
                check.setCheckDataStatus("0");
                preparEvalWorkPlanCheckRecMapper.updateById(check);
            }
        }
        return true;
    }

    @Override
    public Map<String, Object> ExportWordData(TProPerformanceInfo pro ) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("proName",pro.getProName());
        String[] managerName = pro.getParentProName().split("-");
        //获取单位
        StringBuilder company = new StringBuilder(managerName[2]);
        map.put("company",company);
        //获取项目城院

        //查询指标信息
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_A",pro.getIdA());
        entityWrapper.eq("CREATE_STAUTS","2");
        entityWrapper.orderBy("VERSION_NO",false);
        List<RelationProIndex> relationProIndexList = relationProIndexMapper.selectList(entityWrapper);
        //获取最新的版本
        RelationProIndex relationProIndex = relationProIndexList.get(relationProIndexList.size()-1);
        EntityWrapper indexSystemDseignWrapper = new EntityWrapper();
        indexSystemDseignWrapper.eq("ID_R",relationProIndex.getIdR());
        //最新指标信息
        List<TIndexSystemDseign> indexSystemDseignList = indexSystemDseignMapper.selectList(indexSystemDseignWrapper);
        HashMap<String, Object> index1hashMap = new HashMap<String, Object>();
        HashMap<String, Object> index2hashMap = new HashMap<String, Object>();
        HashMap<String, Object> index3hashMap = new HashMap<String, Object>();
        for (int i = 0; i < indexSystemDseignList.size(); i++) {
            TIndexSystemDseign indexSystemDseign = indexSystemDseignList.get(i);
            index1hashMap.put(indexSystemDseign.getIndexName1(),indexSystemDseign.getIndexName1());
            index2hashMap.put(indexSystemDseign.getIndexName2(),indexSystemDseign.getIndexName2());
            index3hashMap.put(indexSystemDseign.getIndexName3(),indexSystemDseign.getIndexName3());
        }
        map.put("index1Bum",index1hashMap.size());
        map.put("index2Bum",index2hashMap.size());
        map.put("index3Bum",index3hashMap.size());
        //指标权重说明组合
        List<String[]> stringslist = indexSystemDseignMapper.selectGroupbyCode(relationProIndex.getIdR());
        StringBuilder index1Sb=new StringBuilder();
        for (String[] strings : stringslist) {
            index1Sb.append("类指标权重占"+strings[1]+"%,");
        }
        map.put("indexText",index1Sb);
        //绩效评价的主要内容
        String indexExplanation = null;
        int indexNumber = 1;
        for (String key:index1hashMap.keySet()){
            String keyValue = map.get(key).toString();
            //一级指标内容
            String explanation = indexNumber + "." + index1hashMap.get(keyValue) + "/n" + index1hashMap.get(keyValue) + 
                    "的评价内容包括";
            String index2Text = null;
            //组装决策的评价内容包括     、     、      方面
            for (int i = 0; i < indexSystemDseignList.size(); i++) {
                TIndexSystemDseign indexSystemDseign = indexSystemDseignList.get(i);
                //比较一级分类是否一致
                if (keyValue.equals(indexSystemDseign.getIndexCode1())){
                    index2Text = index2Text + indexSystemDseign.getIndexName2() + "、";
                }else {
                   continue; 
                }
            }
            index2Text = index2Text.substring(0,index2Text.length()-1);
            indexExplanation = explanation + index2Text + "方面。";
            
            Map<String, Object> index2TexthashMap = new HashMap<String, Object>();
            for (String key1:index2hashMap.keySet()){
                String index2keyValue = map.get(key1).toString();
                TIndexSystemDseign beanval = (TIndexSystemDseign) map.get("index2keyValue");
                String index2names = beanval.getIndexName2() + "的评价内容包括";
                EntityWrapper index2EntityWrapper = new EntityWrapper();
                index2EntityWrapper.eq("ID_R",beanval.getIdR());
                index2EntityWrapper.eq("INDEX_NAME_1",beanval.getIndexName1());
                index2EntityWrapper.eq("INDEX_NAME_2",beanval.getIndexName2());
                List<TIndexSystemDseign> list = indexSystemDseignMapper.selectList(index2EntityWrapper);
                for (TIndexSystemDseign tIndexSystemDseign : list) {
                    index2names = index2names + tIndexSystemDseign.getIndexName3() + "、";
                }
                index2names = index2names.substring(0,index2names.length()-1) + ";";
                indexExplanation = indexExplanation + index2names;
            }
            indexExplanation = indexExplanation.substring(0,indexExplanation.length()-1) + "./n";
        }
        //设置绩效评价的主要内容
        map.put("indexExplanation",indexExplanation);

        
        
        
        
        
        
        
        
        /*for (String key:index1hashMap.keySet()) {
            index1Sb.append(key+"类指标权重占");
        }*/



        return map;
    }

    @Override
    public Map<String, Object> ExportWordTextMap(TProPerformanceInfo pro) {
        Map<String, Object> map = new HashMap<String, Object>();

        //设置调研提纲表格数据数据
        EntityWrapper relationProResearchWrapper = new EntityWrapper();
        relationProResearchWrapper.eq("ID_A",pro.getIdA());
        relationProResearchWrapper.eq("CREATE_STAUTS","2");
        //预调研之后正式调研数据
        relationProResearchWrapper.eq("PRE_OR_SCHEME","1");
        relationProResearchWrapper.orderBy("VERSION_NO",true);
        List<RelationProResearchOutline> relationProResearchOutlineList = relationProResearchOutlineMapper.selectList(relationProResearchWrapper);
        if (relationProResearchOutlineList.size() > 0){
            //取最新版本的数据
            RelationProResearchOutline relationProResearchOutline = relationProResearchOutlineList.get(relationProResearchOutlineList.size()-1);
            //获取拟定调研提
            EntityWrapper researchOutlineWrapper = new EntityWrapper();
            researchOutlineWrapper.eq("ID_R",relationProResearchOutline.getIdR());
            List<TResearchOutline> researchOutlineList = researchOutlineMapper.selectList(researchOutlineWrapper);
            //调研提纲text
            String researchOutlineText = null;
            for (int i = 0; i < researchOutlineList.size(); i++) {
                TResearchOutline researchOutline = researchOutlineList.get(i);
                if (i == researchOutlineList.size()-1){
                    researchOutlineText = researchOutlineText + (i+1) + "." + researchOutline.getOutlineName() + "。\r\n";
                }else {
                    researchOutlineText = researchOutlineText + (i+1) + "." + researchOutline.getOutlineName() + ";\r\n";
                }
            }
            map.put("researchOutlineText",researchOutlineText);
        }

        //查询最新版本资料清单
        EntityWrapper relationProListWrapper = new EntityWrapper();
        relationProListWrapper.eq("ID_A",pro.getIdA());
        relationProListWrapper.eq("CREATE_STAUTS","2");
        relationProListWrapper.eq("RELATION_STATUS","1");
        relationProListWrapper.orderBy("VERSION_NO",true);
        List<RelationProList> relationProLists = relationProListMapper.selectList(relationProListWrapper);
        //如果有
        if (relationProLists.size() > 0){
            
            RelationProList relationPro = relationProLists.get(relationProLists.size() - 1);
            //资料信息
            EntityWrapper developmentInformationWrapper = new EntityWrapper();
            developmentInformationWrapper.eq("ID_R",relationPro.getIdR());
            developmentInformationWrapper.orderBy("ID_B");
            List<TDevelopmentInformationList> developmentInformationLists = developmentInformationListMapper.selectList(developmentInformationWrapper);
            //几种不同资料一级分类
            HashMap<String,Object> developmentMap = new HashMap<String,Object>();
            for (int i = 0; i < developmentInformationLists.size(); i++) {
                TDevelopmentInformationList developmentInfo = developmentInformationLists.get(i);
                developmentMap.put(developmentInfo.getInfoType1(),null);
            }
            //判断这几个大类中有多少二级,并分类获取
            for (String key:developmentMap.keySet()){
                List<TDevelopmentInformationList> newList = new ArrayList<TDevelopmentInformationList>();
                for (int i = 0; i < developmentInformationLists.size(); i++) {
                    TDevelopmentInformationList developmentInfo = developmentInformationLists.get(i);
                    if (key.equals(developmentInfo.getInfoType1())){
                        newList.add(developmentInfo);
                    }
                }
                developmentMap.put(key,newList);
            }         
            //资料信息text
            String developmentText = null;
            int developmentNum = 0;
            int infoType1Num = 0;
            //组装显示数据
            for (String key:developmentMap.keySet()){
                infoType1Num = infoType1Num + 1;
                String str = NumberUtil.int2chineseNum(infoType1Num) + "、" + key + "\r\n";
                List<TDevelopmentInformationList> list = (List<TDevelopmentInformationList>) developmentMap.get(key);
                for (int i = 0; i < list.size(); i++) {
                    TDevelopmentInformationList developmentInfo = list.get(i);
                    developmentNum = developmentNum + 1;
                    str = str + developmentNum + "." +  developmentInfo.getInfoType2() + ";\r\n";
                }
                developmentText = developmentText + str;
            }
            developmentText = developmentText + "注：评价工作组可根据评价对象自行修改调整资料清单；如为政策或部门整体评价，还应增加国外相关对比资料等。\r\n";
           
            map.put("developmentText",developmentText);
        }
        
        return map;
    }


}
