package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.LibraryPolocyRegulationCheckRec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 政策法规库审批表
入库、出库、查阅 审批记录表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
public interface TLibraryPolocyRegulationCheckRecMapper extends BaseMapper<LibraryPolocyRegulationCheckRec> {



    /**
     * 1.查询原来上一批该人的审批记录(目的是将上一批的状态改为 历史状态)
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 15:08
     * @updateTime 2021/4/2 15:08
     */
    List<Integer> selectCheckDataStatus(@Param("idX") Integer idX,
                                        @Param("userId") Integer userId);


    /**
     * 2.将该审批人的上一批的 审批数据状态 改为 0-历史记录
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 15:29
     * @updateTime 2021/4/2 15:29
     */
    void updateLastBatch(@Param("lastBatch") List<Integer> lastBatch);


    /**
     * 3.新增审批数据，并返回新增后的主键id值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 14:49
     * @updateTime 2021/4/2 14:49
     */
    void insertCheckBackKey(@Param("libraryPolocyRegulationCheckRec") LibraryPolocyRegulationCheckRec libraryPolocyRegulationCheckRec);


    /**
     * 4.根据政法主键id值查询政法记录id集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/6 21:46
     * @updateTime 2021/4/6 21:46
     */
    List<Integer> queryIdBsByIdX(Integer idX);
}