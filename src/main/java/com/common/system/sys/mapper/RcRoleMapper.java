package com.common.system.sys.mapper;

import com.common.system.sys.entity.RcRole;
import com.common.system.sys.entity.RcRoleExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RcRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RcRole record);

    int insertSelective(RcRole record);

    List<RcRole> selectByExample(RcRoleExample example);

    RcRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RcRole record);

    int updateByPrimaryKey(RcRole record);

    List<RcRole> getRoleList();

    /**
     * 1.根据value值集合查询角色表中的角色主键id集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/13 11:46
     * @updateTime 2021/4/13 11:46
     */
    List<RcRole> queryByVlaue(@Param("value") List<String> value);
}