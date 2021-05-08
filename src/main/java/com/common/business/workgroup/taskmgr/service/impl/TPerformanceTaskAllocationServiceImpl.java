package com.common.business.workgroup.taskmgr.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.pre.mkletter.entity.TResearchLetterTemp;
import com.common.business.planmgr.pre.mkletter.mapper.TResearchLetterTempMapper;
import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.common.business.planmgr.scheme.mkoutline.mapper.TResearchOutlineTempMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.business.workgroup.taskmgr.entity.TPerformanceTaskAllocation;
import com.common.business.workgroup.taskmgr.entity.TPerformanceTaskAllocationTemp;
import com.common.business.workgroup.taskmgr.entity.TResearchLetterGzz;
import com.common.business.workgroup.taskmgr.entity.TResearchOutlineGzz;
import com.common.business.workgroup.taskmgr.mapper.TPerformanceTaskAllocationMapper;
import com.common.business.workgroup.taskmgr.mapper.TPerformanceTaskAllocationTempMapper;
import com.common.business.workgroup.taskmgr.mapper.TResearchLetterGzzMapper;
import com.common.business.workgroup.taskmgr.mapper.TResearchOutlineGzzMapper;
import com.common.business.workgroup.taskmgr.service.TPerformanceTaskAllocationService;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 绩效任务分配表 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-09
 */
@Service
@Transactional
public class TPerformanceTaskAllocationServiceImpl extends ServiceImpl<TPerformanceTaskAllocationMapper, TPerformanceTaskAllocation> implements TPerformanceTaskAllocationService {
    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private TPerformanceWorkingGroupMapper tPerformanceWorkingGroupMapper;
    @Autowired
    private TPerformanceTaskAllocationMapper tPerformanceTaskAllocationMapper;
    @Autowired
    private TResearchLetterGzzMapper tResearchLetterGzzMapper;
    @Autowired
    private TResearchOutlineTempMapper tResearchOutlineTempMapper;
    @Autowired
    private TResearchOutlineGzzMapper tResearchOutlineGzzMapper;
    @Autowired
    private TResearchLetterTempMapper tResearchLetterTempMapper;
    @Autowired
    TPerformanceTaskAllocationTempMapper tPerformanceTaskAllocationTempMapper;

    /**
     * 查询已组建工作组的项目(所有子项目)
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> findToTaskAllocationPage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean,ShiroUser user) throws Exception {
        //设置分页   pageNum 当前页      pageSize 每页多少条
        PageHelper.startPage(pageNum, pageSize);
        //项目经理id
        bean.setProManagerId(user.getId().toString());
        //查询已组建工作组的项目
        List<TProPerformanceInfo> list= tProPerformanceInfoMapper.findHadEstablishedList(bean,search);
        if(list !=null && list.size()>0){
            //创建项目id集合
            List<Integer> idaList =new ArrayList<>();
            for(TProPerformanceInfo info:list){
                idaList .add(info.getIdA());
            }
            EntityWrapper<TPerformanceTaskAllocation> taskAllocationEntity=new EntityWrapper<TPerformanceTaskAllocation>();
            taskAllocationEntity.in("ID_A",idaList );
            //根据最后更新时间顺序排序
            taskAllocationEntity.orderBy("UPDATE_TIME",true);
            //获取绩效任务分配表集合
            List<TPerformanceTaskAllocation> taskAllocationList=tPerformanceTaskAllocationMapper.selectList(taskAllocationEntity);
            if(taskAllocationList !=null && taskAllocationList.size()>0){
                Map<Integer, TPerformanceTaskAllocation> map=getMapByList(taskAllocationList);
                for(TProPerformanceInfo info:list){
                    TPerformanceTaskAllocation taskAllocation=map.get(info.getIdA());
                    //如果不为空，说明已经分配过工作任务
                    if(taskAllocation!=null){
                        //设置最后调整时间
                        info.setAssignTasksTime(taskAllocation.getUpdateTime());
                        //任务分配人
                        info.setAssignTaskstor(taskAllocation.getUpdateor());
                    }

                }
            }
        }
        return new PageInfo<TProPerformanceInfo>(list);
    }

    /**
     * 查询已归档未涉密的项目
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> findHadFilePage(Integer pageNum, Integer pageSize, String search, TProPerformanceInfo bean, ShiroUser user) throws Exception{
        //设置分页   pageNum 当前页      pageSize 每页多少条
        PageHelper.startPage(pageNum, pageSize);
        EntityWrapper  entity=new EntityWrapper();
        //0-未归档（默认） 1-暂存 2-审核中 3-退回/撤回 9-已完成
        entity.eq("ARCHIVES_CLASS_STATUS","9" );
        //是否涉密项目  0-不涉密（默认）  1-涉密
        entity.eq("PRO_IS_SECRET","0");
        //查询已归档未涉密的项目
        List<TProPerformanceInfo> list=tProPerformanceInfoMapper.selectList(entity);
        return new PageInfo<TProPerformanceInfo>(list);
    }
    /**
     * 根据集合获取 ida- bean
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param taskAllocationList
     * @return
     */
    private Map<Integer, TPerformanceTaskAllocation> getMapByList(List<TPerformanceTaskAllocation> taskAllocationList){
        Map<Integer, TPerformanceTaskAllocation> map=new HashMap<>();
        for(TPerformanceTaskAllocation taskAllocation:taskAllocationList){
            map.put(taskAllocation.getIdA(),taskAllocation);
        }
        return map;
    }

    /**
     * 根据项目idA查询工作组集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @throws Exception
     */
    @Override
    public List<TPerformanceWorkingGroup> findTPerformanceWorkingGroupListByIdA(Integer idA) throws Exception {

        EntityWrapper<TPerformanceWorkingGroup> entity=new EntityWrapper<TPerformanceWorkingGroup>();
        //根据idA查询
        entity.eq("ID_A",idA);
        List<TPerformanceWorkingGroup> list=tPerformanceWorkingGroupMapper.selectList(entity);
        if(list !=null && list.size()>0){
            return list;
        }else{
            return new ArrayList<TPerformanceWorkingGroup>() ;
        }
    }
    /**
     * 根据项目parentProCode查询分配工作的列表信息
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param parentProCode
     * @throws Exception
     */
    @Override
    public List<TProPerformanceInfo> findToTaskAllocationListByParentProCode(String parentProCode) throws Exception {
        EntityWrapper<TProPerformanceInfo> entity=new EntityWrapper<TProPerformanceInfo>();
        //根据parentProCode查询
        entity.eq("PARENT_PRO_CODE",parentProCode);
        //已经立项，未删除
        entity.eq("PRO_STATUS","1");
        List<TProPerformanceInfo> list=tProPerformanceInfoMapper.selectList(entity);
        if(list !=null && list.size()>0){
            for(TProPerformanceInfo info:list){
                //根据项目idA查询工作任务树列表
                List<TPerformanceTaskAllocation> taskAllocationTrees=getTaskAllocationTrees(info.getIdA());
                info.setTaskAllocationTrees(taskAllocationTrees);
                //根据项目idA查询调研提纲拟定列表
                List<TResearchOutlineGzz>  researchOutlineGzz=getResearchOutlineGzzListByIdA(info.getIdA());
                info.setResearchOutlineGzzList(researchOutlineGzz);
                //根据项目idA查询绩效评价调研函（工作组）列表
                TResearchLetterGzz  researchLetterGzz=getResearchLetterGzzByIdA(info.getIdA());
                info.setResearchLetterGzz(researchLetterGzz);
            }
        }
        return list;
    }
    /**
     * 根据项目idA查询调研提纲拟定
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    @Override
    public List<TResearchOutlineGzz> getResearchOutlineGzzListByIdA(Integer idA) throws Exception {
        EntityWrapper<TResearchOutlineGzz> entity=new EntityWrapper<TResearchOutlineGzz>();
        //根据idA查询
        entity.eq("ID_A",idA);
        List<TResearchOutlineGzz> list=tResearchOutlineGzzMapper.selectList(entity);
        if(list !=null && list.size()>0){
            return list;
        }else{
            //定义返回的集合
            List<TResearchOutlineGzz>  returnList=new ArrayList<>();
            //如果为空，查询模板数据
            EntityWrapper<TResearchOutlineTemp> tempEntity=new EntityWrapper<TResearchOutlineTemp>();
            List<TResearchOutlineTemp>  tempList=tResearchOutlineTempMapper.selectList(tempEntity);
            if(tempList !=null && tempList.size()>0){
               for(TResearchOutlineTemp temp:tempList){
                   //把提纲模板数据设置到调研提纲里
                   TResearchOutlineGzz tResearchOutlineGzz=new TResearchOutlineGzz();
                   //提纲序号
                   tResearchOutlineGzz.setOrderNo(temp.getOrderNo());
                   //提纲名称
                   tResearchOutlineGzz.setOutlineName(temp.getOutlineName());
                   returnList.add(tResearchOutlineGzz);
               }
            }
            return returnList;
        }
    }


    /**
     * 根据项目idA查询绩效评价调研函（工作组）
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    @Override
    public  TResearchLetterGzz  getResearchLetterGzzByIdA(Integer idA) throws Exception {
        EntityWrapper<TResearchLetterGzz> entity=new EntityWrapper<TResearchLetterGzz>();
        //根据idA查询
        entity.eq("ID_A",idA);
        List<TResearchLetterGzz> list=tResearchLetterGzzMapper.selectList(entity);
        if(list !=null && list.size()>0){
            return list.get(0);
        }else{
            //把调研函模板数据设置到调研函里
            TResearchLetterGzz tResearchLetterGzz=new TResearchLetterGzz();
            //如果为空，查询模板数据
            EntityWrapper<TResearchLetterTemp> tempEntity=new EntityWrapper<TResearchLetterTemp>();
            List<TResearchLetterTemp>  tempList=tResearchLetterTempMapper.selectList(tempEntity);
            if(tempList !=null && tempList.size()>0){
                for(TResearchLetterTemp temp:tempList){

                    //调研函文件名称
                    tResearchLetterGzz.setResearchName(temp.getResearchName());
                    //调研函内容
                    tResearchLetterGzz.setResearchContent(temp.getResearchContent());
                    //存储路径
                    tResearchLetterGzz.setFilePath(temp.getFilePath());
                    //文件大小
                    tResearchLetterGzz.setFileSize(temp.getFileSize());
                }
            }
            return tResearchLetterGzz;
        }
    }
    /**
     * 根据项目idA查询工作任务树列表
     * @author:安达
     * @date:2021年3月11日 9：51
     * @param idA
     * @throws Exception
     */
    @Override
    public List<TPerformanceTaskAllocation> getTaskAllocationTrees(Integer idA) throws Exception {
        EntityWrapper<TPerformanceTaskAllocation> entity=new EntityWrapper<TPerformanceTaskAllocation>();
        //根据idA查询
        entity.eq("ID_A",idA);
        entity.orderBy("TASK_CODE",true);
        List<TPerformanceTaskAllocation> list = tPerformanceTaskAllocationMapper.selectList(entity);
        if(list ==null || list.size()==0){
            //如果第一次分配，需要从模板获取工作任务
            list = getTaskAllocationFromTemp(idA);
        }
        //id和bean的map
        LinkedHashMap<Integer,TPerformanceTaskAllocation> idBeanMap=new LinkedHashMap<>();
        //第一级的集合
        List<TPerformanceTaskAllocation> plist =new ArrayList<>();

        for(TPerformanceTaskAllocation bean:list){
            //存入 d和bean的map
            idBeanMap.put(bean.getIdB(),bean);
        }
        //循环，把对象存入map里的父对象ChildList里
        for(TPerformanceTaskAllocation child:list){
            TPerformanceTaskAllocation parent=idBeanMap.get(child.getParentId());
            if(parent !=null){
                //如果这个节点存在父节点，父节点就把它装入子节点
                parent.getChildren().add(child);
                //再把父节点装回去
                idBeanMap.put(parent.getIdB(),parent);
            }
        }
        //循环map，获得第一级对象
        for(Integer key:idBeanMap.keySet()){
            TPerformanceTaskAllocation parent=idBeanMap.get(key);
            if(parent.getTaskLeavel() !=null && "1".equals(parent.getTaskLeavel().toString())){
                //如果是第一级，就存入plist集合
                plist.add(parent);
            }
        }

        return plist;
    }
    /**
     * 从模板获取工作任务
     * @author:安达
     * @date:2021年3月17日 9：51
     * @throws Exception
     */
    public List<TPerformanceTaskAllocation> getTaskAllocationFromTemp(Integer idA) throws Exception {
        //定义返回的分配工作集合
        List<TPerformanceTaskAllocation> returnList=new ArrayList<>();
        //从模板获取工作任务集合
        EntityWrapper  entity=new EntityWrapper ();
        entity.orderBy("TASK_CODE",true);
        List<TPerformanceTaskAllocationTemp> tempList=tPerformanceTaskAllocationTempMapper.selectList(entity);
        //用IDA*100  ，IDA不会重复，子工作任务目前是62个
        long baseId=idA*100;
        for(TPerformanceTaskAllocationTemp temp:tempList){
            TPerformanceTaskAllocation bean=new TPerformanceTaskAllocation();
            //主键
            bean.setIdB((int) (baseId+temp.getIdX()));
            //任务编号
            bean.setTaskCode(temp.getTaskCode());
            //任务描述
            bean.setTaskDesc(temp.getTaskDesc());
            //任务层级
            bean.setTaskLeavel(temp.getTaskLeavel());
            //上级任务编号
            if(temp.getParentId() !=null){
                bean.setParentId((int)(baseId+temp.getParentId()));
            }

            //0-未选择  1-已选择（默认）
            bean.setHaveBeenChoose("1");
            returnList.add(bean);
        }
        return returnList;
    }
    /**
     * 组建工分配任务
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param isTaskAssigned  是否已分配绩效任务  Y-保存， Z-暂存
     * @param proPerformanceInfoList
     * @throws Exception
     */
    @Override
    public void saveTPerformanceTaskAllocation ( String isTaskAssigned,List<TProPerformanceInfo> proPerformanceInfoList, ShiroUser user) throws Exception {
        //循环集合
        for(TProPerformanceInfo info:proPerformanceInfoList){
            info.setIsTaskAssigned(isTaskAssigned);
            //绩效评价调研函（工作组）
            TResearchLetterGzz researchLetterGzz=info.getResearchLetterGzz();
            researchLetterGzz.setIdA(info.getIdA());
            //创建人
            researchLetterGzz.setCreateor(user.getName());
            //创建时间
            researchLetterGzz.setCreateTime(new Date());
            if(researchLetterGzz.getIdB() !=null){
                //如果id不为空，就修改
                tResearchLetterGzzMapper.updateById(researchLetterGzz);
            }else{
                //否则就新增
                tResearchLetterGzzMapper.insert(researchLetterGzz);
            }

            //绩效评价提纲表（组建工作组）
            List<TResearchOutlineGzz>  researchOutlineGzzList=info.getResearchOutlineGzzList();
            //先删除老的提纲数据
            EntityWrapper<TResearchOutlineGzz> deleteOutlineEntity=new EntityWrapper<TResearchOutlineGzz>();
            //根据idA删除
            deleteOutlineEntity.eq("ID_A",info.getIdA());
            tResearchOutlineGzzMapper.delete(deleteOutlineEntity);
            for(TResearchOutlineGzz researchOutlineGzz:researchOutlineGzzList){
                researchOutlineGzz.setIdA(info.getIdA());
                //否则就新增
                tResearchOutlineGzzMapper.insert(researchOutlineGzz);
            }

            List<TPerformanceTaskAllocation> trees=info.getTaskAllocationTrees();
            //先删除原来的数据
            EntityWrapper<TPerformanceTaskAllocation> deleteEntity=new EntityWrapper<TPerformanceTaskAllocation>();
            //根据idA删除
            deleteEntity.eq("ID_A",info.getIdA());
            tPerformanceTaskAllocationMapper.delete(deleteEntity);
            //保存树集合
            saveTaskAllocationTrees(trees,info.getIdA(),user);
            tProPerformanceInfoMapper.updateById(info);
        }
    }

    /**
     * 保存树集合
     * @throws Exception
     */
    private void saveTaskAllocationTrees(List<TPerformanceTaskAllocation> trees,Integer idA, ShiroUser user) throws Exception{
        for(TPerformanceTaskAllocation tree:trees){
            //创建人
            tree.setCreateor(user.getName());
            //创建时间
            tree.setCreateTime(new Date());
            //修改人
            tree.setUpdateor(user.getName());
            //修改时间
            tree.setUpdateTime(new Date());
            tree.setIdA(idA);
            //每次都插入，因为之前已经删掉了
            super.insert(tree);
            if(tree.getChildren() !=null && tree.getChildren().size()>0){
                //递归保存树集合
                saveTaskAllocationTrees(tree.getChildren(), idA,user);
            }
        }
    }

}
