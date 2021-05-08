package com.common.business.project.approval.mapper;

import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-08
 */
public interface TEvalUnitInfoMapper extends BaseMapper<TEvalUnitInfo> {

    /**
     * 证据条件查询
     * @param tEvalUnitInfo
     * @return
     */
    List<TEvalUnitInfo> select(TEvalUnitInfo tEvalUnitInfo);

    /**
     * 分页查询
     * @param pageNum
     * @param pagesize
     * @param tEvalUnitInfo
     * @return
     */
    List<TEvalUnitInfo> page(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize,@Param("tEvalUnitInfo") TEvalUnitInfo tEvalUnitInfo);





/*======================================================================================================================*/
/*          组建专家组   开始         author:田鑫艳                                                                       */
/*======================================================================================================================*/

    /**
     * 1.TPerformanceExpertGroupInfoMapper [组建工作组-->组建专家组   Mapper接口]
     * 点击“详情” 按钮 查询-->项目信息 中的“被评单位”
     * @param idA TProPerformanceInfo实体类中idA属性，即：主键id值
     * @return List<TEvalUnitInfo> TEvalUnitInfo类型集合（被评单位集合）
     * @author 田鑫艳
     * @createTime 2021/3/9 14:25
     * @updateTime 2021/3/9 14:25
     */
    List<TEvalUnitInfo> selectExceptEvaluatedInfo(Integer idA);

/*======================================================================================================================*/
/*          组建专家组   结束         author:田鑫艳                                                                       */
/*======================================================================================================================*/

}