package com.common.system.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.system.sys.entity.RcUser;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;

/**
 * Created by Mr.Yangxiufeng on 2017/6/26.
 * Time:13:57
 * ProjectName:bg_perfm
 */
public interface UserService  extends IService<RcUser>{
    Result<Integer> deleteById(Integer userId);
    Result<RcUser> getById(Integer userId);
    Result<Integer> update(RcUser user);
    Result<Integer> save(RcUser user);
    Result<RcUser> getByUserName(String username);
    PageInfo<RcUser> listForPage(Integer pageNum, Integer pageSize);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @param user
     * @return
     */
    PageInfo<RcUser> listForPage(Integer pageNum, Integer pageSize, String search, RcUser user);

    int modifyPwd(RcUser user);

}
