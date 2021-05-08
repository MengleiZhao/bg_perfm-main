package com.common.system.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.sys.entity.RcDept;
import com.common.system.sys.entity.RcDeptExample;

import java.util.List;

import com.common.system.sys.entity.RcDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RcDeptMapper  extends BaseMapper<RcDept> {
    int deleteByExample(RcDeptExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(RcDept record);

    List<RcDept> selectByExample(RcDeptExample example);

    RcDept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RcDept record, @Param("example") RcDeptExample example);

    int updateByExample(@Param("record") RcDept record, @Param("example") RcDeptExample example);

    int updateByPrimaryKeySelective(RcDept record);

    int updateByPrimaryKey(RcDept record);
}