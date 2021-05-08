package com.common.business.library.experts.mapper;

import com.common.business.library.experts.entity.TLibraryPerformanceExpert;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.workgroup.expert.entity.TPerformanceExpertGroupInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 项目专家库 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-10
 */
public interface TLibraryPerformanceExpertMapper extends BaseMapper<TLibraryPerformanceExpert> {


/*======================================================================================================================*/
/*          组建专家组   开始         author:田鑫艳                                                                       */
/*======================================================================================================================*/

    /**
     * 1.分页查询数据，查询的约束：专家的状态为“正常”；专家的“数据状态”为在库
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/10 15:12
     * @updateTime 2021/3/10 15:12
     */
    List<TLibraryPerformanceExpert> queryLivePage(@Param("tLibraryPerformanceExpert")TLibraryPerformanceExpert tLibraryPerformanceExpert,
                                                  @Param("search")String search,
                                                  @Param("notIncludedExpCodes") List<String> notIncludedExpCodes);



/*======================================================================================================================*/
/*          组建专家组   结束         author:田鑫艳                                                                       */
/*======================================================================================================================*/

}