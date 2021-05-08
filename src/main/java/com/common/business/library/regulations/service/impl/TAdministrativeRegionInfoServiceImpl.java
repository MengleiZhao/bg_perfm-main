package com.common.business.library.regulations.service.impl;

import com.common.business.library.regulations.entity.TAdministrativeRegionInfo;
import com.common.business.library.regulations.mapper.TAdministrativeRegionInfoMapper;
import com.common.business.library.regulations.service.TAdministrativeRegionInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-27
 */
@Service
public class TAdministrativeRegionInfoServiceImpl extends ServiceImpl<TAdministrativeRegionInfoMapper, TAdministrativeRegionInfo> implements TAdministrativeRegionInfoService {

    @Autowired
    private TAdministrativeRegionInfoMapper administrativeRegionInfoMapper;//省市 mapper
    /**
     * 1-查询省市 (树状）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 13:47
     * @updateTime 2021/4/27 13:47
     */
    @Override
    public List<TAdministrativeRegionInfo> queryProvinceCity() throws Exception {
        //最终返回的集合 （存放的是一级节点的集合）
        List<TAdministrativeRegionInfo> administrativeRegionInfos=new ArrayList<>();

        //查询所有的数据
        HashMap<Integer,TAdministrativeRegionInfo> allDatasMap=administrativeRegionInfoMapper.queryProvinceCity();

        //遍历 给需要的对象中插入子节点
        for(TAdministrativeRegionInfo child:allDatasMap.values()){
            //根据 当前对象的parentId字段得到，当前节点的父节点对象，如果能在map中找到，则往map中的父节点里添加当前对象（作为子节点）
            //判断 当前对象的父节点值是否能在Map中找到，如果能找到，则说明当前节点是父节点，需要添加到Map的子节点中
            TAdministrativeRegionInfo parentAdmin=allDatasMap.get(child.getParaentId());
            if(parentAdmin!=null){
                parentAdmin.getChildList().add(child);
                //再将含有子节点对象重新放入到map中
                allDatasMap.put(parentAdmin.getIdX(),parentAdmin);
            }
        }

        //遍历 返回数据
        for(Integer key:allDatasMap.keySet()){
            TAdministrativeRegionInfo nowAdmin=allDatasMap.get(key);
            //如果当前对象不为空，且当前对象的 “描述”是 省级 ，则说明 当前对象是 省级 级别，添加到最终返回的列表里
            if(nowAdmin!=null && nowAdmin.getLevelDesc()!=null){
                boolean test=("省级".equals(nowAdmin.getLevelDesc())||"中央级".equals(nowAdmin.getLevelDesc()));
                if("省级".equals(nowAdmin.getLevelDesc())||"中央级".equals(nowAdmin.getLevelDesc())){
                    administrativeRegionInfos.add(nowAdmin);
                }
            }
        }

        return administrativeRegionInfos;
    }
}
