package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.TLibraryPolocyRegulationCheckAtta;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 政策法规审批附件表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
public interface TLibraryPolocyRegulationCheckAttaMapper extends BaseMapper<TLibraryPolocyRegulationCheckAtta> {

    /**
     * 1.通过idX查询审批附件表数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 21:38
     * @updateTime 2021/4/6 21:38
     */
    List<TLibraryPolocyRegulationCheckAtta> queryByIdX(@Param("idBs") List<Integer> idBs);
}