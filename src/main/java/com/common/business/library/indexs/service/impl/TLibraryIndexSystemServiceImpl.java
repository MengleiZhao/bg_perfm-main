package com.common.business.library.indexs.service.impl;

import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.library.indexs.mapper.TLibraryIndexSystemMapper;
import com.common.business.library.indexs.service.TLibraryIndexSystemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.indexdesign.entity.TEvidencePools;
import com.common.business.workgroup.taskmgr.entity.TPerformanceTaskAllocationTemp;
import com.common.business.workgroup.taskmgr.mapper.TPerformanceTaskAllocationTempMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.applet2.AppletParameters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 绩效指标库 服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@Service
public class TLibraryIndexSystemServiceImpl extends ServiceImpl<TLibraryIndexSystemMapper, TLibraryIndexSystem> implements TLibraryIndexSystemService {

    @Autowired
    private TLibraryIndexSystemMapper indexSystemMapper;//绩效指标库 mapper




    /**
     * 1-绩效指标库 主页面 树状显示
     * 约束条件：默认不显示已经出库的，但是，出库审批中的是要显示的、入库已审核、修改已审核、绩效指标库不是私有
     * 整体思路：
     *      1）查询所有在库的绩效指标库数据
     *      2）得到 一级指标库Map
     *      3）得到 二级指标库Map
     *      4）遍历，将属于二级指标库的数据放入到二级指标库Map中
     *      5）遍历，将属于一级指标库的数据放入到一级指标库Map中
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 21:09
     * @updateTime 2021/4/25 21:09
     */
    @Override
    public PageInfo<TLibraryIndexSystem> indexSystemPage(Integer current, Integer size, TLibraryIndexSystem libraryIndexSystem, String search) throws Exception {
        PageHelper.startPage(current,size);
        //最终要返回的对象
        List<TLibraryIndexSystem> lastLibraryIndexSystems=new ArrayList<>();

        //1）查询所有在库的绩效指标库数据
        List<TLibraryIndexSystem> libraryIndexSystems=indexSystemMapper.indexSystemPage(libraryIndexSystem,search);

        //存放一级指标编码Map  <一级编号，bean> 此时的map，有几个一级编号，它的size就是几个（存放的bean是不正确的）
        HashMap<String,TLibraryIndexSystem> codeOneMap=new HashMap<>();
        //存放二级指标编码Map  <一级编号+二级编号，bean>  此时的map，有几个 一级编号+二级编号，它的size就是几个（存放的bean是不正确的）
        HashMap<String,TLibraryIndexSystem> codeTwoMap=new HashMap<>();

        //得到一级指标编码Map和二级指标编码Map
        for(TLibraryIndexSystem indexSystem:libraryIndexSystems){
            //2）得到 一级指标编码Map
            if(codeOneMap.get(indexSystem.getIndexCode1())==null){
                TLibraryIndexSystem codeOne=new TLibraryIndexSystem();
                codeOne.setIndexCode1(indexSystem.getIndexCode1());
                codeOne.setIndexName1(indexSystem.getIndexName1());
                codeOne.setIndexCode2(indexSystem.getIndexCode2());
                codeOne.setIndexName2(indexSystem.getIndexName2());
                codeOneMap.put(indexSystem.getIndexCode1(),codeOne);
            }
            //3）得到 二级指标编码Map
            if(codeTwoMap.get(indexSystem.getIndexCode1()+indexSystem.getIndexCode2())==null){
                TLibraryIndexSystem codeTwo=new TLibraryIndexSystem();
                codeTwo.setIndexCode1(indexSystem.getIndexCode1());
                codeTwo.setIndexName1(indexSystem.getIndexName1());
                codeTwo.setIndexCode2(indexSystem.getIndexCode2());
                codeTwo.setIndexName2(indexSystem.getIndexName2());
                codeTwoMap.put(indexSystem.getIndexCode1()+indexSystem.getIndexCode2(),codeTwo);
            }
        }

        //4）遍历，将 三级指标装进二级指标编码Map中
        for(TLibraryIndexSystem indexSystem:libraryIndexSystems){
            //得到当前指标的二级指标编码
            TLibraryIndexSystem nowCodeTwo=codeTwoMap.get(indexSystem.getIndexCode1()+indexSystem.getIndexCode2());
            //如果当前的指标的二级指标编码在 二级指标Map中可以找到，则将当前对象添加到二级指标Map中
            if(nowCodeTwo!=null){
                //将当前对象添加到 刚刚得到的二级指标对象中的子节点中
                nowCodeTwo.getChildrenList().add(indexSystem);
                //再将当前对象(此时有子节点了)添加到二级指标Map中
                codeTwoMap.put(nowCodeTwo.getIndexCode1()+nowCodeTwo.getIndexCode2(),nowCodeTwo);
            }

        }

        //5）遍历，将 二级指标装进一级指标编码的Map中
        for(TLibraryIndexSystem indexSystem:libraryIndexSystems){
            TLibraryIndexSystem nowCodeOne=codeOneMap.get(indexSystem.getIndexCode1());
            if(nowCodeOne!=null){
                nowCodeOne.getChildrenList().add(indexSystem);
                codeOneMap.put(nowCodeOne.getIndexCode1(),nowCodeOne);
            }
        }

       //6）遍历 一级指标编码Map，将数据装入集合中
        for(String codeOne:codeOneMap.keySet()){
            TLibraryIndexSystem codeOneIndexSystem=codeOneMap.get(codeOne);
            lastLibraryIndexSystems.add(codeOneIndexSystem);
        }

        return new PageInfo<>(lastLibraryIndexSystems);
    }




    /**
     * Tool1.递归
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/26 10:47
     * @updateTime 2021/4/26 10:47
     */
    public List<TPerformanceTaskAllocationTemp> recursion(List<TPerformanceTaskAllocationTemp> taskAllocationTemps){
        List<TPerformanceTaskAllocationTemp> lastTaskTemps=new ArrayList<>();
        //得到父级Map，并将子级数据添加到Map中
        for(TPerformanceTaskAllocationTemp task:taskAllocationTemps){

        }
        return lastTaskTemps;
    }







    //    /**
//     * 项目支出绩效评价指标库列表
//     * @author:安达
//     * @date:2021年3月9日 9：51
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public List<TIndexSystemDseign> findTLibraryIndexSystem() throws Exception {
//        //获取三级指标集合
//        List<TLibraryIndexSystem> levelThirdIndexList=findLevelThirdIndexList();
//        List<TIndexSystemDseign> indexSystemDseignList=new ArrayList<>();
//        //循环三级指标
//        for(TLibraryIndexSystem library:levelThirdIndexList){
//            TIndexSystemDseign indexSystemDseign=new TIndexSystemDseign();
//            //三级指标编码
//            indexSystemDseign.setIndexCode3(library.getIndexCode());
//            //三级指标名称
//            indexSystemDseign.setIndexName3(library.getIndexName());
//            //二级指标
//            TLibraryIndexSystem levelTwoIndex=levelTwoIndexMap.get(library.getParentId());
//            //二级指标编码
//            indexSystemDseign.setIndexCode2(levelTwoIndex.getIndexCode());
//            //二级指标名称
//            indexSystemDseign.setIndexName2(levelTwoIndex.getIndexName());
//            //一级指标
//            TLibraryIndexSystem levelOneIndex=levelOneIndexMap.get(levelTwoIndex.getParentId());
//            //一级指标编码
//            indexSystemDseign.setIndexCode1(levelOneIndex.getIndexCode());
//            //一级指标名称
//            indexSystemDseign.setIndexName1(levelOneIndex.getIndexName());
//            //三级指标分值
//            indexSystemDseign.setIndexScore3(library.getIndexScore());
//            //指标解释（三级）
//            indexSystemDseign.setIndexExplanation(library.getIndexExplanation());
//            //预算支出功能分类
//            indexSystemDseign.setBudFunctClassName(library.getBudFunctClassName());
//            //国民经济行业分类
//            indexSystemDseign.setNationEcoIndustClassName(library.getNationEcoIndustClassName());
//            //指标类别，0-请选择  1-共性  2-个性
//            indexSystemDseign.setIndexType(library.getIndexType());
//            indexSystemDseignList.add(indexSystemDseign);
//        }
//        return indexSystemDseignList;
//    }
//    /**
//     * @Description:  获取一级指标集合
//     * @Author: 安达
//     * @Date: 2021/3/18 9:36
//     * @Param:
//     * @Return:
//     */
//    @Override
//    public List<TLibraryIndexSystem> findLevelOneIndexList() throws Exception {
//        EntityWrapper<TLibraryIndexSystem> entity=new EntityWrapper<TLibraryIndexSystem>();
//        //一级指标
//        entity.eq("INDEX_LEVEL",1);
//        //聚合去重复
//        entity.groupBy("INDEX_CODE");
//        List<TLibraryIndexSystem> list=tLibraryIndexSystemMapper.selectList(entity);
//        return list;
//    }
//
//
//    /**
//     * @Description:  获取二级指标集合
//     * @Author: 安达
//     * @Date: 2021/3/18 9:36
//     * @Param:
//     * @Return:
//     */
//    @Override
//    public List<TLibraryIndexSystem> findLevelTwoIndexList() throws Exception {
//        EntityWrapper<TLibraryIndexSystem> entity=new EntityWrapper<TLibraryIndexSystem>();
//        //二级指标
//        entity.eq("INDEX_LEVEL",2);
//        //聚合去重复
//        entity.groupBy("INDEX_CODE");
//        List<TLibraryIndexSystem> list=tLibraryIndexSystemMapper.selectList(entity);
//        return list;
//    }
//
//    /**
//     * @Description: list集合转换为map
//     * @param list
//     * @return
//     * @throws Exception
//     */
//    public Map<Integer,TLibraryIndexSystem> getLevelIndexMap(List<TLibraryIndexSystem> list) throws Exception {
//        HashMap<Integer,TLibraryIndexSystem> levelIndexMap=new HashMap();
//        for(TLibraryIndexSystem indexSystem:list){
//            levelIndexMap.put(indexSystem.getIdX(),indexSystem);
//        }
//        return levelIndexMap;
//    }
//    /**
//     * @Description:  获取三级指标集合
//     * @Author: 安达
//     * @Date: 2021/3/18 9:36
//     * @Param:
//     * @Return:
//     */
//
//    public List<TLibraryIndexSystem> findLevelThirdIndexList() throws Exception {
//        EntityWrapper<TLibraryIndexSystem> entity=new EntityWrapper<TLibraryIndexSystem>();
//        //二级指标
//        entity.eq("INDEX_LEVEL",3);
//        //聚合去重复
//        entity.groupBy("INDEX_CODE");
//        List<TLibraryIndexSystem> list=tLibraryIndexSystemMapper.selectList(entity);
//        return list;
//    }








}
