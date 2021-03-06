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
 *  ???????????????????????????????????????
 *  ???????????????
 * </p>
 *
 * @author ?????????
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
     * @Description: ????????????????????????????????????????????????
     * @author: ?????????
     * @createDate: 2021/4/8 17:23
     * @updater: ?????????
     * @updateDate: 2021/4/8 17:23
     * @param pageNum
     * @param pageSize
     * @param search ????????????
     * @param bean ????????????
     * @param user ????????????
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
     * Description: ?????????????????????
     * @author: ?????????
     * @createDate: 2021/4/21 20:57
     * @updater: ?????????
     * @updateDate: 2021/4/21 20:57
     * @param pageNum
     * @param pageSize
     * @param search ????????????
     * @param bean ????????????
     * @param user ????????????
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
     * ??????
     * @author: ?????????
     * @createDate: 2021/4/15 20:53
     * @updater: ?????????
     * @updateDate: 2021/4/15 20:53
     * @param relationProPreparEvalWorkPlanVo:???????????????????????????????????????
     * @param user:???????????????
     * @return ????????????true,????????????false
     **/
    @Override
    public Boolean save(RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo, ShiroUser user) {
        //????????????????????????????????????
        RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan = relationProPreparEvalWorkPlanVo.getRelationProPreparEvalWorkPlan();
        //??????????????????????????????
        List<TPreparEvalWorkPlan> preparEvalWorkPlanList = relationProPreparEvalWorkPlanVo.getPreparEvalWorkPlanList();
        String addOrUpdate = null;
        if (StringUtils.isEmpty(relationProPreparEvalWorkPlan.getIdR())){
            //??????
            addOrUpdate = "add";
            //?????????
            //???????????????????????????????????????
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
            //????????????????????????
            insert(relationProPreparEvalWorkPlan);
            //??????????????????????????????
            for (int i = 0; i < preparEvalWorkPlanList.size(); i++) {
                TPreparEvalWorkPlan preparEvalWorkPlan = preparEvalWorkPlanMapper.selectById(preparEvalWorkPlanList.get(i).getIdB());
                preparEvalWorkPlan.setIdR(relationProPreparEvalWorkPlan.getIdR());
                preparEvalWorkPlanMapper.updateById(preparEvalWorkPlan);
            }
            //????????????????????????
            List<BussinessFlowPreparEvalWorkPlan> workFlowList = bussinessFlowPreparEvalWorkPlanService.getWorkFlow(relationProPreparEvalWorkPlan, user);
            for (int i = 0; i < workFlowList.size(); i++) {
                bussinessFlowPreparEvalWorkPlanService.insert(workFlowList.get(i));
            }
        }else{
            //??????
            addOrUpdate = "update";
            RelationProPreparEvalWorkPlan oldbean = selectById(relationProPreparEvalWorkPlan.getIdR());
            oldbean.setCreateTime(new Date());
            oldbean.setCreateStauts(relationProPreparEvalWorkPlan.getCreateStauts());
            relationProPreparEvalWorkPlan = oldbean;
            updateById(oldbean);
            //??????????????????
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
            //???????????????
            for (int i = 0; i < preparEvalWorkPlanList.size(); i++) {
                TPreparEvalWorkPlan preparEvalWorkPlan = preparEvalWorkPlanMapper.selectById(preparEvalWorkPlanList.get(i).getIdB());
                preparEvalWorkPlan.setIdR(relationProPreparEvalWorkPlan.getIdR());
                preparEvalWorkPlanMapper.updateById(preparEvalWorkPlan);
            }
        }

        //??????????????????
        if ("1".equals(relationProPreparEvalWorkPlan.getCreateStauts())){
            //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if ("update".equals(addOrUpdate)) {
                //?????????????????????
                EntityWrapper currentEntityWrapper = new EntityWrapper();
                currentEntityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
                currentEntityWrapper.eq("ORDER_OF_CURRENT_NODE",1);
                BussinessFlowPreparEvalWorkPlan currentWorkFlow = bussinessFlowPreparEvalWorkPlanService.selectOne(currentEntityWrapper);
                //?????????????????????
                EntityWrapper secondEntityWrapper = new EntityWrapper();
                secondEntityWrapper.eq("ID_R",relationProPreparEvalWorkPlan.getIdR());
                secondEntityWrapper.eq("ORDER_OF_CURRENT_NODE",2);
                BussinessFlowPreparEvalWorkPlan secondWorkFlow = bussinessFlowPreparEvalWorkPlanService.selectOne(secondEntityWrapper);
                currentWorkFlow.setNodeIsActive("0");
                currentWorkFlow.setCurrentNodeStatus("1");
                secondWorkFlow.setNodeIsActive("1");
                //????????????????????????????????????
                bussinessFlowPreparEvalWorkPlanService.updateById(currentWorkFlow);
                bussinessFlowPreparEvalWorkPlanService.updateById(secondWorkFlow);
            }
            //??????????????????
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
        //????????????
        StringBuilder company = new StringBuilder(managerName[2]);
        map.put("company",company);
        //??????????????????

        //??????????????????
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_A",pro.getIdA());
        entityWrapper.eq("CREATE_STAUTS","2");
        entityWrapper.orderBy("VERSION_NO",false);
        List<RelationProIndex> relationProIndexList = relationProIndexMapper.selectList(entityWrapper);
        //?????????????????????
        RelationProIndex relationProIndex = relationProIndexList.get(relationProIndexList.size()-1);
        EntityWrapper indexSystemDseignWrapper = new EntityWrapper();
        indexSystemDseignWrapper.eq("ID_R",relationProIndex.getIdR());
        //??????????????????
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
        //????????????????????????
        List<String[]> stringslist = indexSystemDseignMapper.selectGroupbyCode(relationProIndex.getIdR());
        StringBuilder index1Sb=new StringBuilder();
        for (String[] strings : stringslist) {
            index1Sb.append("??????????????????"+strings[1]+"%,");
        }
        map.put("indexText",index1Sb);
        //???????????????????????????
        String indexExplanation = null;
        int indexNumber = 1;
        for (String key:index1hashMap.keySet()){
            String keyValue = map.get(key).toString();
            //??????????????????
            String explanation = indexNumber + "." + index1hashMap.get(keyValue) + "/n" + index1hashMap.get(keyValue) + 
                    "?????????????????????";
            String index2Text = null;
            //?????????????????????????????????     ???     ???      ??????
            for (int i = 0; i < indexSystemDseignList.size(); i++) {
                TIndexSystemDseign indexSystemDseign = indexSystemDseignList.get(i);
                //??????????????????????????????
                if (keyValue.equals(indexSystemDseign.getIndexCode1())){
                    index2Text = index2Text + indexSystemDseign.getIndexName2() + "???";
                }else {
                   continue; 
                }
            }
            index2Text = index2Text.substring(0,index2Text.length()-1);
            indexExplanation = explanation + index2Text + "?????????";
            
            Map<String, Object> index2TexthashMap = new HashMap<String, Object>();
            for (String key1:index2hashMap.keySet()){
                String index2keyValue = map.get(key1).toString();
                TIndexSystemDseign beanval = (TIndexSystemDseign) map.get("index2keyValue");
                String index2names = beanval.getIndexName2() + "?????????????????????";
                EntityWrapper index2EntityWrapper = new EntityWrapper();
                index2EntityWrapper.eq("ID_R",beanval.getIdR());
                index2EntityWrapper.eq("INDEX_NAME_1",beanval.getIndexName1());
                index2EntityWrapper.eq("INDEX_NAME_2",beanval.getIndexName2());
                List<TIndexSystemDseign> list = indexSystemDseignMapper.selectList(index2EntityWrapper);
                for (TIndexSystemDseign tIndexSystemDseign : list) {
                    index2names = index2names + tIndexSystemDseign.getIndexName3() + "???";
                }
                index2names = index2names.substring(0,index2names.length()-1) + ";";
                indexExplanation = indexExplanation + index2names;
            }
            indexExplanation = indexExplanation.substring(0,indexExplanation.length()-1) + "./n";
        }
        //?????????????????????????????????
        map.put("indexExplanation",indexExplanation);

        
        
        
        
        
        
        
        
        /*for (String key:index1hashMap.keySet()) {
            index1Sb.append(key+"??????????????????");
        }*/



        return map;
    }

    @Override
    public Map<String, Object> ExportWordTextMap(TProPerformanceInfo pro) {
        Map<String, Object> map = new HashMap<String, Object>();

        //????????????????????????????????????
        EntityWrapper relationProResearchWrapper = new EntityWrapper();
        relationProResearchWrapper.eq("ID_A",pro.getIdA());
        relationProResearchWrapper.eq("CREATE_STAUTS","2");
        //?????????????????????????????????
        relationProResearchWrapper.eq("PRE_OR_SCHEME","1");
        relationProResearchWrapper.orderBy("VERSION_NO",true);
        List<RelationProResearchOutline> relationProResearchOutlineList = relationProResearchOutlineMapper.selectList(relationProResearchWrapper);
        if (relationProResearchOutlineList.size() > 0){
            //????????????????????????
            RelationProResearchOutline relationProResearchOutline = relationProResearchOutlineList.get(relationProResearchOutlineList.size()-1);
            //?????????????????????
            EntityWrapper researchOutlineWrapper = new EntityWrapper();
            researchOutlineWrapper.eq("ID_R",relationProResearchOutline.getIdR());
            List<TResearchOutline> researchOutlineList = researchOutlineMapper.selectList(researchOutlineWrapper);
            //????????????text
            String researchOutlineText = null;
            for (int i = 0; i < researchOutlineList.size(); i++) {
                TResearchOutline researchOutline = researchOutlineList.get(i);
                if (i == researchOutlineList.size()-1){
                    researchOutlineText = researchOutlineText + (i+1) + "." + researchOutline.getOutlineName() + "???\r\n";
                }else {
                    researchOutlineText = researchOutlineText + (i+1) + "." + researchOutline.getOutlineName() + ";\r\n";
                }
            }
            map.put("researchOutlineText",researchOutlineText);
        }

        //??????????????????????????????
        EntityWrapper relationProListWrapper = new EntityWrapper();
        relationProListWrapper.eq("ID_A",pro.getIdA());
        relationProListWrapper.eq("CREATE_STAUTS","2");
        relationProListWrapper.eq("RELATION_STATUS","1");
        relationProListWrapper.orderBy("VERSION_NO",true);
        List<RelationProList> relationProLists = relationProListMapper.selectList(relationProListWrapper);
        //?????????
        if (relationProLists.size() > 0){
            
            RelationProList relationPro = relationProLists.get(relationProLists.size() - 1);
            //????????????
            EntityWrapper developmentInformationWrapper = new EntityWrapper();
            developmentInformationWrapper.eq("ID_R",relationPro.getIdR());
            developmentInformationWrapper.orderBy("ID_B");
            List<TDevelopmentInformationList> developmentInformationLists = developmentInformationListMapper.selectList(developmentInformationWrapper);
            //??????????????????????????????
            HashMap<String,Object> developmentMap = new HashMap<String,Object>();
            for (int i = 0; i < developmentInformationLists.size(); i++) {
                TDevelopmentInformationList developmentInfo = developmentInformationLists.get(i);
                developmentMap.put(developmentInfo.getInfoType1(),null);
            }
            //???????????????????????????????????????,???????????????
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
            //????????????text
            String developmentText = null;
            int developmentNum = 0;
            int infoType1Num = 0;
            //??????????????????
            for (String key:developmentMap.keySet()){
                infoType1Num = infoType1Num + 1;
                String str = NumberUtil.int2chineseNum(infoType1Num) + "???" + key + "\r\n";
                List<TDevelopmentInformationList> list = (List<TDevelopmentInformationList>) developmentMap.get(key);
                for (int i = 0; i < list.size(); i++) {
                    TDevelopmentInformationList developmentInfo = list.get(i);
                    developmentNum = developmentNum + 1;
                    str = str + developmentNum + "." +  developmentInfo.getInfoType2() + ";\r\n";
                }
                developmentText = developmentText + str;
            }
            developmentText = developmentText + "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\r\n";
           
            map.put("developmentText",developmentText);
        }
        
        return map;
    }


}
