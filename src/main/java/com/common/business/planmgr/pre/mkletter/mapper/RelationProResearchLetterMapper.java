package com.common.business.planmgr.pre.mkletter.mapper;

import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.planmgr.pre.mkletter.entity.RelationProResearchLetter;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-24
 */
public interface RelationProResearchLetterMapper extends BaseMapper<RelationProResearchLetter> {

    /**
     * @Title:
     * @Description: 分页查询预调研函拟定列表数据
     * @param pageNum
     * @param pagesize
     * @param search
     * @param bean
     * @return
     * @author: 陈睿超
     * @createDate: 2021/3/24 17:33
     * @updater: 陈睿超
     * @updateDate: 2021/3/24 17:33
     */
    List<RelationProResearchLetter> pageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                             @Param("search") String search, @Param("bean") RelationProResearchLetter bean);

    /**
     * @Title:
     * @Description: 分页查询预调研函审批列表数据
     * @author: 陈睿超
     * @createDate: 2021/3/25 22:01
     * @updater: 陈睿超
     * @updateDate: 2021/3/25 22:01
     * @param pageNum
     * @param pagesize
     * @param search
     * @param bean
     * @return
     */
    List<RelationProResearchLetter> checkPageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                             @Param("search") String search, @Param("bean") RelationProResearchLetter bean);

    /**
     * @Title: 
     * @Description: 选择项目
     * @author: 陈睿超
     * @createDate: 2021/4/8 17:36
     * @updater: 陈睿超
     * @updateDate: 2021/4/8 17:36
     * @param pageNum
     * @param pagesize
     * @param search
     * @param bean
     * @return
     **/
    List<RelationProResearchLetter> chooseProPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                                  @Param("search") String search, @Param("bean") RelationProResearchLetter bean);


}