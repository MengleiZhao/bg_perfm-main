package com.common.system.sys.mapper;

import com.common.system.sys.entity.RcPrivilege;
import com.common.system.sys.entity.RcPrivilegeExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RcPrivilegeMapper {
    int deleteByExample(RcPrivilegeExample example);

    int insert(RcPrivilege record);

    int insertSelective(RcPrivilege record);

    List<RcPrivilege> selectByExample(RcPrivilegeExample example);

    int updateByExampleSelective(@Param("record") RcPrivilege record, @Param("example") RcPrivilegeExample example);

    int updateByExample(@Param("record") RcPrivilege record, @Param("example") RcPrivilegeExample example);
}