package com.common.business.collection.meanslist.mapper;

import com.common.business.collection.meanslist.entity.RelationProList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Mapper
public interface RelationProListMapper extends BaseMapper<RelationProList> {



    /**
     * @Title:
     * @Description: 查询数据
     * @author: 陈睿超
     * @createDate: 2021/3/17 11:18
     * @updater: 陈睿超
     * @updateDate: 2021/3/17 11:18
     * @param search : 模糊查询条件
     * @param bean : 精确查询条件
     * @return
     **/
    List<RelationProList> findPageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                       @Param("search") String search,@Param("bean") RelationProList bean);

    /**
     * 分页查询待审批的数据
     * @param pageNum
     * @param pagesize
     * @param search
     * @param bean
     * @return
     */
    List<RelationProList> findCheckPageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                       @Param("search") String search,@Param("bean") RelationProList bean);

    /**
     * 分页查询台账的数据
     * @param pageNum
     * @param pagesize
     * @param search
     * @param bean
     * @return
     */
    List<RelationProList> findLedgerPageList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pagesize,
                                       @Param("search") String search,@Param("bean") RelationProList bean);

/*================================================================================================================*/
/*===========资料收集上传    author:田鑫艳    [开始]================================================================ */
/*================================================================================================================*/

    /**
     * 1.查询最新版本号
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 10:43
     * @updateTime 2021/3/17 10:43
     */
    List<RelationProList> getlastVersionNo(List<Integer> proInfoIdAs);



/*================================================================================================================*/
/*===========资料收集上传    author:田鑫艳    [结束]================================================================ */
/*================================================================================================================*/

}