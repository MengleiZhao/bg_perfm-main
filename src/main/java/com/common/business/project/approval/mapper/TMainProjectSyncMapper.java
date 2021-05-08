package com.common.business.project.approval.mapper;

import com.common.business.project.approval.entity.TMainProjectSync;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-09
 */
public interface TMainProjectSyncMapper extends BaseMapper<TMainProjectSync> {


    /**
     * 
     * @param pageNum
     * @param pagesize
     * @param tMainProjectSync
     * @return
     */
    List<TMainProjectSync> selectPageList(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tMainProjectSync") TMainProjectSync tMainProjectSync);

    /**
     * 项目立项选择主项目模糊查询
     * @param pageNum
     * @param pagesize
     * @param tMainProjectSync
     * @param search
     * @return
     */
    List<TMainProjectSync> selectLikePageList(@Param("pageNum") int pageNum, @Param("pageSize") int pagesize, @Param("tMainProjectSync") TMainProjectSync tMainProjectSync, @Param("search") String search);

    /**
     *  项目立项主项目模糊查询统计
     * @param tMainProjectSync
     * @param search
     * @return
     */
    Integer countLikePage(@Param("tMainProjectSync") TMainProjectSync tMainProjectSync, @Param("search") String search);

    
    Boolean updateProStatusById(@Param("idA") Integer idA,@Param("proStatus") String proStatus);

    /**
     * 根据条件去修改项目状态
     * @param cloumn
     * @param val
     * @param proStatus
     * @return
     */
    Boolean updateProStatusByCloumn(@Param("cloumn") String cloumn,@Param("val") String val,@Param("proStatus") String proStatus);

    /**
     * 自定义条件查询
     * @param cloumn 条件
     * @param val 值
     * @return
     */
    List<TMainProjectSync> findByCloumnList(@Param("cloumn") String cloumn,@Param("val") String val);

    TMainProjectSync findByCloumn(@Param("cloumn") String cloumn,@Param("val") String val);
    
}