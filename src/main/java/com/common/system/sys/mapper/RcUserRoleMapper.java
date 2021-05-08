package com.common.system.sys.mapper;

import com.common.system.sys.entity.RcUserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yangxiufeng
 * @since 2017-09-11
 */
public interface RcUserRoleMapper extends BaseMapper<RcUserRole> {
    /**
     * 根据用户id删除rc_user_role表中的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/13 16:28
     * @updateTime 2021/4/13 16:28
     */
    void deleteByUserId(Integer userId);

    /**
     * 根据项目idA删除rc_user_role表中的数据，idA对应的是create_by字段
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/13 20:25
     * @updateTime 2021/4/13 20:25
     */
    void delectByIdA(Integer idA);
}