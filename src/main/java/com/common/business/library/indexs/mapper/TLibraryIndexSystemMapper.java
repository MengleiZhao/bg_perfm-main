package com.common.business.library.indexs.mapper;

import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 * <p>
  * 绩效指标库 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
public interface TLibraryIndexSystemMapper extends BaseMapper<TLibraryIndexSystem> {

    /**
     * 1-查询所有在库的绩效指标库数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/25 21:27
     * @updateTime 2021/4/25 21:27
     */
    List<TLibraryIndexSystem> indexSystemPage(@Param("libraryIndexSystem") TLibraryIndexSystem libraryIndexSystem,
                                              @Param("search") String search);

    /**
     * 2-申请主页面显示
     * 查询调整状态不为出库并且状态不为已审批（UPT_TYPE='1' and  DATA_STAUTS='2'）的绩效指标
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/26 13:41
     * @updateTime 2021/4/26 13:41
     */
    List<TLibraryIndexSystem> applicyPage(@Param("libraryIndexSystem") TLibraryIndexSystem libraryIndexSystem,
                                          @Param("search") String search);

    /**
     * 3-修改绩效指标库中的部分字段：
     * 调整类型、数据状态、申请人、申请时间、申请描述、审核人、审核时间
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 17:31
     * @updateTime 2021/4/27 17:31
     */
    void updateSomeColumns(@Param("indexSystem") TLibraryIndexSystem indexSystem);
}