package com.common.system.sys.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.system.page.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.common.system.shiro.ShiroKit;
import com.common.system.sys.entity.RcRoleWrapper;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.entity.RcUserRole;
import com.common.system.sys.service.RcUserRoleService;
import com.common.system.sys.service.RoleService;
import com.common.system.sys.service.UserService;
import com.common.system.page.MsgCode;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;
import com.xiaoleilu.hutool.date.DateUtil;

/**
 * Created by Mr.Yangxiufeng on 2017/6/21.
 * Time:15:46
 * ProjectName:bg_perfm
 */
@Controller
@RequestMapping(value = "user")
public class UserMgrController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMgrController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RcUserRoleService userRoleService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/admin/user/list");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "page")
    public PageBean<RcUser> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start,
                                         @RequestParam(value = "length", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "date", required = false) String date,
                                         @RequestParam(value = "search", required = false) String search,
                                         HttpServletRequest request) {
        PageInfo<RcUser> pageInfo = userService.listForPage((start / pageSize) + 1, pageSize);
        return new PageBean<RcUser>(pageInfo);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Result delete(@PathVariable Integer id) {
        Result<Integer> result = userService.deleteById(id);
        return result;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/admin/user/add");
        return modelAndView;
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer id, ModelAndView modelAndView) {
        Result<RcUser> result = userService.getById(id);
        modelAndView.addObject("bean", result.getData());
        modelAndView.setViewName("/system/admin/user/edit");
        return modelAndView;
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer id, ModelAndView modelAndView) {
        Result<RcUser> result = userService.getById(id);
        modelAndView.addObject("bean", result.getData());
        modelAndView.setViewName("/system/admin/user/view");
        return modelAndView;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Result update(RcUser user) {
        try{
            userService.updateById(user);
            return getJsonResult(true,"????????????","");
        }catch (Exception e){
            e.printStackTrace();
            return getJsonResult(false,"????????????","");
        }
    }

    @RequestMapping(value = "save")
    public
    @ResponseBody
    Result save(RcUser rcUser) {
        rcUser.setCreateTime(new Date());
        rcUser.setStatus(1);
        String salt = ShiroKit.getRandomSalt(5);
        rcUser.setSalt(salt);
        //????????????????????????
        rcUser.setUsername(rcUser.getGroupMemberCode());
        //????????????????????????
        rcUser.setName(rcUser.getGroupMemberName());
        String saltPwd = ShiroKit.md5(rcUser.getPassword(), salt);
        rcUser.setPassword(saltPwd);
        Result<Integer> result = userService.save(rcUser);
        return result;
    }

    @RequestMapping(value = "goResetPwd/{id}", method = RequestMethod.GET)
    public ModelAndView goResetPwd(ModelAndView modelAndView, @PathVariable Integer id) {
        modelAndView.setViewName("system/admin/user/reset_pwd");
        RcUser user = userService.getById(id).getData();
        modelAndView.addObject("bean", user);
        return modelAndView;
    }

    @RequestMapping(value = "doResetPwd", method = RequestMethod.POST)
    public
    @ResponseBody
    Result doResetPwd(Integer id, String password) {
        Result result = new Result();
        Result<RcUser> rcUserResult = userService.getById(id);
        RcUser user = rcUserResult.getData();
        String salt = ShiroKit.getRandomSalt(5);
        String saltPwd = ShiroKit.md5(password, salt);
        user.setPassword(saltPwd);
        user.setSalt(salt);
        userService.modifyPwd(user);
        result.setStatus(true);
        result.setCode(MsgCode.SUCCESS);
        result.setMsg("????????????");
        return result;
    }

    @RequestMapping(value = "goModifyPwd/{id}", method = RequestMethod.GET)
    public ModelAndView goModifyPwd(ModelAndView modelAndView, @PathVariable Integer id) {
        modelAndView.setViewName("system/admin/user/modify_pwd");
        RcUser user = userService.getById(id).getData();
        modelAndView.addObject("bean", user);
        return modelAndView;
    }

    /**
     * <p>????????????</p>
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "modifyPwd", method = RequestMethod.POST)
    public
    @ResponseBody
    Result modifyPwd(Integer id, String oldPassword, String password) {
        Result result = new Result();
        if (StringUtils.isEmpty(password)) {
            result.setMsg("?????????????????????");
            return result;
        }
        Result<RcUser> rcUserResult = userService.getById(id);
        RcUser user = rcUserResult.getData();
        String md5pwd = ShiroKit.md5(oldPassword, user.getSalt());
        if (!user.getPassword().equals(md5pwd)) {
            result.setCode(MsgCode.FAILED);
            result.setStatus(false);
            result.setMsg("???????????????");
            return result;
        }
        String salt = ShiroKit.getRandomSalt(5);
        String saltPwd = ShiroKit.md5(password, salt);
        user.setPassword(saltPwd);
        user.setSalt(salt);
        try {
            userService.modifyPwd(user);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("????????????");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "goDispatcherRole/{id}", method = RequestMethod.GET)
    public ModelAndView goDispatcherRole(ModelAndView modelAndView, @PathVariable final Integer id) {
        modelAndView.setViewName("/system/admin/user/dispatcher_role");
        List<RcRoleWrapper> roleWrappers = roleService.getRoleWrapperList();
        modelAndView.addObject("roles", roleWrappers);
        modelAndView.addObject("userId", id);
        RcUser user = userService.getById(id).getData();
        modelAndView.addObject("bean", user);
        List<RcUserRole> list = userRoleService.selectList(new Wrapper<RcUserRole>() {
            @Override
            public String getSqlSegment() {
                return "where user_id=" + id;
            }
        });
        for (RcRoleWrapper w : roleWrappers) {
            if (list != null) {
                for (RcUserRole r : list) {
                    if (w.getId().equals(r.getRoleId())) {
                        w.setChecked(true);
                    }
                }
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "doDispatcherRole", method = RequestMethod.POST)
    public
    @ResponseBody
    Result doDispatcherRole(Integer id, String roleId) {
        LOGGER.info("roleId=" + roleId);
        Result result = new Result();
        if (StringUtils.isEmpty(roleId)) {
            if (userRoleService.deleteByUserId(id)) {
                result.setStatus(true);
                result.setCode(MsgCode.SUCCESS);
            }
        } else {
            String[] roleIds = roleId.split(",");
            //???????????????
            userRoleService.deleteByUserId(id);
            List<RcUserRole> list = new ArrayList<>();
            for (int i = 0; i < roleIds.length; i++) {
                RcUserRole userRole = new RcUserRole();
                userRole.setUserId(id);
                userRole.setRoleId(Integer.valueOf(roleIds[i]));
                userRole.setCreateTime(new Date());
                userRole.setCreateBy(getUser().getName());
                list.add(userRole);
            }
            if (userRoleService.insertBatch(list)) {
                result.setStatus(true);
                result.setCode(MsgCode.SUCCESS);
            }
        }
        return result;
    }

    @RequestMapping(value = "exportExcel", method = RequestMethod.GET)
    public void exportExcel(ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) {
        PageInfo<RcUser> result = userService.listForPage(null, null);
        ExportParams params = new ExportParams("????????????", null, ExcelType.XSSF);
        modelMap.put(NormalExcelConstants.DATA_LIST, result.getList());
        modelMap.put(NormalExcelConstants.CLASS, RcUser.class);
        modelMap.put(NormalExcelConstants.PARAMS, params);
        String fileName = DateUtil.format(new Date(), DateUtil.NORM_DATETIME_PATTERN);
        modelMap.put(NormalExcelConstants.FILE_NAME, "???????????????:" + fileName);
        PoiBaseView.render(modelMap, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }
}
