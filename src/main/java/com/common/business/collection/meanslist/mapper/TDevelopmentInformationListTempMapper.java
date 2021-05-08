package com.common.business.collection.meanslist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import com.common.business.collection.meanslist.entity.TDevelopmentInformationListTemp;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 存放项目资料清单模板信息 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Mapper
public interface TDevelopmentInformationListTempMapper extends BaseMapper<TDevelopmentInformationListTemp> {

	
    /**
    * [查询] 根据主键 id 查询
    * @author 陈睿超
    * @date 2021/03/02
    **/
    TDevelopmentInformationListTemp load(int id);

    /**
    * [查询] 分页查询
    * @author 陈睿超
    * @date 2021/03/02
    **/
    List<TDevelopmentInformationListTemp> pageListdata(@Param("pageNum") int pageNum,@Param("pageSize")int pagesize,@Param("tDevelopmentInformationListTemp")TDevelopmentInformationListTemp tDevelopmentInformationListTemp);

    /**
    * [查询] 分页查询 count
    * @author 陈睿超
    * @date 2021/03/02
    **/
    int pageListCount(@Param("offset")int offset,@Param("pageSize")int pagesize);


    List<TDevelopmentInformationListTemp> getTemplate();
    
}