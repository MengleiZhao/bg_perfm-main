package com.common.business.planmgr.pre.mkletter.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.common.business.planmgr.pre.mkletter.entity.TResearchLetter;
import com.common.business.planmgr.pre.mkletter.mapper.RelationProResearchLetterMapper;
import com.common.business.planmgr.pre.mkletter.mapper.TResearchLetterMapper;
import com.common.business.planmgr.pre.mkletter.service.RelationProResearchLetterService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mklettercheck.entity.BussinessFlowProResearchLetter;
import com.common.business.planmgr.pre.mklettercheck.entity.TResearchLetterCheckRec;
import com.common.business.planmgr.pre.mklettercheck.mapper.TResearchLetterCheckRecMapper;
import com.common.business.planmgr.pre.mklettercheck.service.BussinessFlowProResearchLetterService;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
@Service
@Transactional
public class RelationProResearchLetterServiceImpl extends ServiceImpl<RelationProResearchLetterMapper, RelationProResearchLetter> implements RelationProResearchLetterService {

    @Autowired
    private TResearchLetterMapper researchLetterMapper;
    @Lazy
    @Autowired
    private BussinessFlowProResearchLetterService bussinessFlowProResearchLetterService;
    @Autowired
    private TResearchLetterCheckRecMapper researchLetterCheckRecMapper;

    /**
     * 分页查询预调研函拟定列表数据
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    @Override
    public PageInfo<RelationProResearchLetter> pagelist(Integer pageNum, Integer pageSize, String search, RelationProResearchLetter bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProResearchLetter> list = baseMapper.pageList(pageNum, pageSize, search, bean);
        PageInfo<RelationProResearchLetter> pageInfo = new PageInfo<RelationProResearchLetter>(list);
        return pageInfo;
    }

    /**
     * 分页查询预调研函审批列表数据
     * @param pageNum
     * @param pageSize
     * @param search
     * @param bean
     * @return
     */
    @Override
    public PageInfo<RelationProResearchLetter> checkPagelist(Integer pageNum, Integer pageSize, String search, RelationProResearchLetter bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProResearchLetter> list = baseMapper.checkPageList(pageNum, pageSize, search, bean);
        PageInfo<RelationProResearchLetter> pageInfo = new PageInfo<RelationProResearchLetter>(list);
        return pageInfo;
    }


    @Override
    public PageInfo<RelationProResearchLetter> chooseProPage(Integer pageNum, Integer pageSize, String search, RelationProResearchLetter bean) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RelationProResearchLetter> list = baseMapper.pageList(pageNum, pageSize, search, bean);
        PageInfo<RelationProResearchLetter> pageInfo = new PageInfo<RelationProResearchLetter>(list);
        return pageInfo;
    }

    /**
     * @Title:
     * @Description:  保存/送审
     * @author: 陈睿超
     * @createDate: 2021/3/25 10:36
     * @updater: 陈睿超
     * @updateDate: 2021/3/25 10:36
     * @param bean : 保存对象 
     * @return
     **/
    @Override
    public Boolean save(RelationProResearchLetter bean, ShiroUser user) {
        //获取前端传过来拟定调研函数据
        TResearchLetter researchLetter = bean.getResearchLetter();
        TResearchLetter oldresearchLetter = bean.getResearchLetter();
        //工作流
        List<BussinessFlowProResearchLetter> workList = new ArrayList<BussinessFlowProResearchLetter>();
        String addOrUpdate = null;
        if (null == bean.getIdR()){
            //新增
            addOrUpdate = "add";
            bean.setRelationStatus("1");
            //查询同项目已有几条数据
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("RELATION_STATUS","1");
            entityWrapper.eq("ID_A",bean.getIdA());
            entityWrapper.orderBy("CREATE_TIME");
            List<RelationProResearchLetter> versionNolist = baseMapper.selectList(entityWrapper);
            //版本号
            if (0 == versionNolist.size()) {
                //版本号
                bean.setVersionNo("V1");
            }else {
                String versionNo = versionNolist.get(versionNolist.size()-1).getVersionNo();
                String num = versionNo.substring(1, versionNo.length());
                bean.setVersionNo("V"+(Integer.valueOf(num)+1));
                bean.setVersionNo("V"+(versionNolist.size()+1));
            }

        }else {
            //修改
            addOrUpdate = "update";
            oldresearchLetter = researchLetterMapper.selectById(researchLetter.getIdB());
            oldresearchLetter.setResearchContent(researchLetter.getResearchContent());
            oldresearchLetter.setResearchName(researchLetter.getResearchName());
            //获取已有工作流
            EntityWrapper flowEntityWrapper = new EntityWrapper();
            flowEntityWrapper.eq("ID_R",bean.getIdR());
            workList = bussinessFlowProResearchLetterService.selectList(flowEntityWrapper);
            for (int i = 0; i < workList.size(); i++) {
                BussinessFlowProResearchLetter workFlow = workList.get(i);
                BussinessFlowProResearchLetter nextWorkFlow = workList.get(i+1);
                //送审
                if ("1".equals(bean.getCreateStauts()) && "1".equals(workFlow.getNodeIsActive())){
                    bean.setCurrCheckId(workList.get(i+1).getCheckUserId());
                    bean.setCurrCheckName(workList.get(i+1).getCheckUser());
                    workFlow.setCurrentNodeStatus("1");
                    workFlow.setNodeIsActive("0");
                    nextWorkFlow.setCurrentNodeStatus("0");
                    nextWorkFlow.setNodeIsActive("1");
                    bussinessFlowProResearchLetterService.updateById(workFlow);
                    bussinessFlowProResearchLetterService.updateById(nextWorkFlow);
                    continue;
                }
            }

        }

        bean.setCreateTime(new Date());
        bean.setCreateUaseName(user.getName());
        bean.setCreateUaseId(user.getId().toString());
        //保存关系表
        insertOrUpdate(bean);

        if("add".equals(addOrUpdate)){
            //获取标准工作流,并保存
            workList = bussinessFlowProResearchLetterService.getWorkFlow(bean, user);
            for (int i = 0; i < workList.size(); i++) {
                BussinessFlowProResearchLetter workFlow = workList.get(i);
                bussinessFlowProResearchLetterService.insertOrUpdate(workFlow);
            }
            researchLetter.setIdR(bean.getIdR());
            researchLetterMapper.insert(researchLetter);
        }else if("update".equals(addOrUpdate)){
            //修改
            researchLetterMapper.updateById(oldresearchLetter);
        }

        //修改旧的审批记录状态，设置下级审批人
        if ("1".equals(bean.getCreateStauts())){
            //送审  
            //查询历史审批记录
            EntityWrapper checkEntityWrapper = new EntityWrapper();
            checkEntityWrapper.eq("CHECK_DATA_STATUS","1");
            checkEntityWrapper.eq("ID_R",bean.getIdR());
            List<TResearchLetterCheckRec> oldCheckList = researchLetterCheckRecMapper.selectList(checkEntityWrapper);
            //审批记录全部改成历史状态
            for (int i = 0; i < oldCheckList.size(); i++) {
                TResearchLetterCheckRec checkHistory = oldCheckList.get(i);
                checkHistory.setCheckDataStatus("0");
                researchLetterCheckRecMapper.updateById(checkHistory);
            }
        }

        return true;
    }
    
    
    
    
    
}
