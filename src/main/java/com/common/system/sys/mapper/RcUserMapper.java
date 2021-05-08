package com.common.system.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.sys.entity.RcUser;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface RcUserMapper extends BaseMapper<RcUser> {

    RcUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RcUser record);

    RcUser getUserByName(String username);

/*======================================================================================================================*/
        /*          组建专家组   开始          author:田鑫艳                                                                      */
/*======================================================================================================================*/
    /**
     * 1.查询该专家账号是否存在
     * @param exCode 专家账号
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/12 15:04
     * @updateTime 2021/3/12 15:04
     */
    Integer selectExpertCount(String exCode);

    /**
     * 2.根据专家的账号删除用户表中的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/13 17:35
     * @updateTime 2021/3/13 17:35
     */
    void deleteExpertAccount(String expAccount);
    //判断该专家是否已经开通账号，根据专家的编号查询
    Integer selectGroupMemberId(String expCode);
    //根据专家编号查询专家账号
    String selectExpertAccount(String expCode);


    /*======================================================================================================================*/
        /*          组建专家组   结束          author:田鑫艳                                                                      */
/*======================================================================================================================*/



    /**
     * 1.根据用户主键id集合查询用户Map集合
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/1 15:00
     * @updateTime 2021/4/1 15:00
     */
    @MapKey("id")
    HashMap<Integer, RcUser> queryByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据专家账号( username)得到该专家账号的用户主键id值
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/13 14:00
     * @updateTime 2021/4/13 14:00
     */
    Integer queryByUserName(String exCode);

    /**
     * 根据专家账号查询该专家的信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/13 16:17
     * @updateTime 2021/4/13 16:17
     */
    RcUser queryByExpCode(String expCode);

    /**
     * 根据idA查询该项目的项目经理相关信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/23 18:25
     * @updateTime 2021/4/23 18:25
     */
    RcUser managerUser(@Param("idA") Integer idA,@Param("userId") Integer userId);
}