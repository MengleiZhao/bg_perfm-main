package com.common.system.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.sys.entity.RcRole;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.entity.RcUserRole;
import com.common.system.sys.mapper.RcUserMapper;
import com.common.system.sys.service.RcUserRoleService;
import com.common.system.sys.service.RoleService;
import com.common.system.sys.service.UserService;
import com.common.system.page.MsgCode;
import com.common.system.page.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by Mr.Yangxiufeng on 2017/6/26.
 * Time:13:59
 * ProjectName:bg_perfm
 */
@Service
public class UserServiceImpl  extends ServiceImpl<RcUserMapper, RcUser>  implements UserService {

    @Autowired
    private RcUserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RcUserRoleService userRoleService;

    @Override
    public Result<Integer> deleteById(Integer userId) {
        Result<Integer> result = new Result<>();
        try {
            userMapper.deleteById(userId);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result<RcUser> getById(Integer userId) {
        Result<RcUser> result = new Result<>();
        RcUser user = userMapper.selectById(userId);
        if (user != null){
            result.setData(user);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        }
        return result;
    }

    @Override
    public Result<Integer> update(RcUser user) {
        Result<Integer> result = new Result<>();
        try {
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                result.setMsg("昵称已经存在");
            }
        }
        return result;
    }

    @Override
    public Result<Integer> save(RcUser user) {
        Result<Integer> result = new Result<>();
        try {
            userMapper.insert(user);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                result.setMsg("账号已经存在");
            }
        }
        return result;
    }

    @Override
    public Result<RcUser> getByUserName(String username) {
        Result<RcUser> result = new Result<>();
        RcUser user = userMapper.getUserByName(username);
        if (user != null){
            result.setData(user);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        }
        return result;
    }

    @Override
    public PageInfo<RcUser> listForPage(Integer pageNum, Integer pageSize) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<RcUser> userList = userMapper.selectList(null);
        for (RcUser u: userList ) {
            List<RcUserRole> userRoleList = userRoleService.getByUserId(u.getId());

            if (userRoleList != null && userRoleList.size() > 0){
                List<RcRole> list = new ArrayList<>();
                for (RcUserRole ur:userRoleList
                ) {
                    Result<RcRole> result = roleService.selectById(ur.getRoleId());
                    if (result.isStatus()){
                        list.add(result.getData());
                    }
                }
                u.setRoleList(list);
            }
        }
        return new PageInfo<>(userList);
    }   
    
    @Override
    public PageInfo<RcUser> listForPage(Integer pageNum, Integer pageSize, String search, RcUser user) {
        if (null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("status",1);
        List<RcUser> userList = userMapper.selectList(entityWrapper);
        
        return new PageInfo<>(userList);
    }

    @Override
    public int modifyPwd(RcUser user) {
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
