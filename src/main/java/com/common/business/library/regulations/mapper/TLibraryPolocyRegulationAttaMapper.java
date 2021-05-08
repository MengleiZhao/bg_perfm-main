package com.common.business.library.regulations.mapper;

import com.common.business.library.regulations.entity.TLibraryPolocyRegulationAtta;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 政策法规附件表 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-28
 */
public interface TLibraryPolocyRegulationAttaMapper extends BaseMapper<TLibraryPolocyRegulationAtta> {

    /**
     * 1-批量插入政法 附件表
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 11:07
     * @updateTime 2021/4/28 11:07
     */
    void insertBatches(@Param("policyAttas") List<TLibraryPolocyRegulationAtta> policyAttas);
    /**
     * 2-查询原来的政法文件（去除前端删除的文件）
     * @param idX 要查询的政法主键值
     * @param deleteIdcList 前端删除的政法附件 主键值集合
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 13:30
     * @updateTime 2021/4/28 13:30
     */
    List<TLibraryPolocyRegulationAtta> queryAttas(@Param("idX") Integer idX,
                                                  @Param("deleteIdcList")List<String> deleteIdcList);

    /**
     * 3-查询传进来的idX集合下的附件集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/28 14:13
     * @updateTime 2021/4/28 14:13
     */
    List<TLibraryPolocyRegulationAtta> queryByIdXs(@Param("chooseIdXs") List<String> chooseIdXs);
    /**
     * 批量插入，并返回主键id值 (目前有问题)
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/29 15:31
     * @updateTime 2021/4/29 15:31
     */
    void insertBatchesBackKeys(List<TLibraryPolocyRegulationAtta> attaList);
}