package com.common.business.workgroup.expert.mapper;

import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-09
 */
public interface TPerformanceExpertGroupInfoMapper extends BaseMapper<TPerformanceExpertGroupInfo> {


    /**
     * 1.TPerformanceExpertGroupInfoMapper [组建工作组-->组建专家组   Mapper接口]
     * 点击“详情” 按钮 查询-->专家组信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/9 14:50
     * @updateTime 2021/3/9 14:50
     */
    List<TPerformanceExpertGroupInfo> selectExpertGroup(Integer idA);







    /**
     * 4.查询该子项目的所有专家编号
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/11 18:09
     * @updateTime 2021/3/11 18:09
     */
    List<String> selectExceptCode(Integer idA);

    /**
     * 5.根据专家编号删除
     * @param expCode 专家编号
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/11 20:24
     * @updateTime 2021/3/11 20:24
     */
    void deleteByExpCode(String expCode);

    /**
     * 6.删除子项目中的专家表中的所有数据
     * @param idA 该子项目的主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/11 21:10
     * @updateTime 2021/3/11 21:10
     */
    void deleteAllDatas(Integer idA);

    /**
     * 查询专家组创建的最晚时间
     * @param idA 该子项目的主键id值
     * @return Date 最晚时间
     * @author 田鑫艳
     * @createTime 2021/3/12 0:52
     * @updateTime 2021/3/12 0:52
     */
    Date selectExpertTime(Integer idA);

    /**
     * 查询该项目下的专家成员
     * @param idA 该子项目的主键id值
     * @return Integer 人员数
     * @author 田鑫艳
     * @createTime 2021/3/12 9:20
     * @updateTime 2021/3/12 9:20
     */
    Integer selectExpertNum(Integer idA);

    /**
     * 查询原来分配的专家系统账号的账户
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/13 18:00
     * @updateTime 2021/3/13 18:00
     */
    List<String> selectExpertAccount(Integer idA);

    //得到原有的专家集合
    List<TPerformanceExpertGroupInfo> getOldExpertInfo(Integer idA);
    //修改原来的专家账号
    void updateAccount(@Param("performanceExpertGroupInfo") TPerformanceExpertGroupInfo performanceExpertGroupInfo);
    //根据专家编号删除
    void deleteExperts(@Param("oldExpCodes")List<String> oldExpCodes,@Param("idA")Integer idA);

    /**
     * 查询该专家账号是否在其他项目中使用，如果其他项目中也没有使用该专家账号，则删除用户表中该专家账号
     * @param  expAccount  专家账号
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 17:40
     * @updateTime 2021/4/12 17:40
     */
    Integer queryExprtAccountIsUsed(String expAccount);
}