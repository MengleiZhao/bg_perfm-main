package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.TLibraryPolocyRegulationUpt;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-06
 */
public interface TLibraryPolocyRegulationUptMapper extends BaseMapper<TLibraryPolocyRegulationUpt> {

    /**
     * 1.根据idX判断 数据至政策法规库修改申请表 中有没有之前该政法的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 19:46
     * @updateTime 2021/4/6 19:46
     */
    TLibraryPolocyRegulationUpt queryByIdX(Integer idX);

    /**
     * 2-新增修政法修改表，并返回主键值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 13:50
     * @updateTime 2021/4/28 13:50
     */
    void insertBackKey(@Param("prolicyUpt") TLibraryPolocyRegulationUpt prolicyUpt);
}