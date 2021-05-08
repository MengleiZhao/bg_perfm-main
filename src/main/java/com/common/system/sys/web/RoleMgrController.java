package com.common.system.sys.web;

import java.util.Date;
import java.util.List;

import com.common.system.page.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.system.sys.entity.RcPrivilege;
import com.common.system.sys.entity.RcRole;
import com.common.system.sys.entity.ZTreeNode;
import com.common.system.sys.service.PrivilegeService;
import com.common.system.sys.service.RoleService;
import com.common.system.sys.service.ZTreeService;
import com.common.system.util.Convert;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.github.pagehelper.PageInfo;

/**
 * Created by Mr.Yangxiufeng on 2017/6/21.
 * Time:15:47
 * ProjectName:bg_perfm
 */
@Controller
@RequestMapping(value = "role")
public class RoleMgrController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ZTreeService treeService;
    @Autowired
    private PrivilegeService privilegeService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/role/list");
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping(value = "page")
    public PageBean<RcRole> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "length", defaultValue = "10") int pageSize, @RequestParam(value = "date", required = false) String date, @RequestParam(value = "search", required = false) String search) {
        PageInfo pageInfo = roleService.listForPage((start / pageSize) + 1, pageSize);
        return new PageBean<RcRole>(pageInfo);
    }
    @RequestMapping(value = "delete/{id}",method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable Integer id){
        roleService.deleteById(id);
        //删除角色与权限的关系
        privilegeService.deleteByRoleId(id);
        return REDIRECT+"/system/admin/role/list";
    }
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelMap){
        modelMap.setViewName("/system/admin/role/add");
        return modelMap;
    }
    @RequestMapping(value = "save")
    public @ResponseBody Result save(String roleName,String roleValue,String permission){
        RcRole rcRole = new RcRole();
        rcRole.setName(roleName);
        rcRole.setValue(roleValue);
        rcRole.setStatus(1);
        rcRole.setCreateTime(new Date());
        List<Integer> permissionList = Convert.toIntegerList(permission,",");
        Result<Integer> result = roleService.save(rcRole,permissionList);
        return result;
    }
    @RequestMapping(value = "edit/{id}",method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer id){
        Result<RcRole> result = roleService.selectById(id);
        modelAndView.addObject("role",result.getData());
        modelAndView.setViewName("/system/admin/role/edit");
        return modelAndView;
    }
    @RequestMapping(value = "update")
    public @ResponseBody Result update(RcRole role){
        role = roleService.selectById(role.getId()).getData();
        role.setUpdateTime(new Date());
        Result<Integer> result = roleService.update(role);
        return result;
    }
    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer id,ModelAndView modelAndView){
        Result<RcRole> result = roleService.selectById(id);
        modelAndView.addObject("role",result.getData());
        modelAndView.setViewName("/system/admin/role/view");
        return modelAndView;
    }
    @RequestMapping(value = "permission/{id}",method = RequestMethod.GET)
    public ModelAndView dispatchPermission(@PathVariable Integer id,ModelAndView modelAndView){
        List<ZTreeNode> treeNodes = treeService.getMenuZTreeNodes();
        ZTreeNode node=null;
        for (ZTreeNode n:treeNodes) {
            if (n.getpId().equals("0")){
                node = n;
                break;
            }
        }
        treeNodes.remove(node);
        List<RcPrivilege> privilegeList = privilegeService.getByRoleId(id);
        if (privilegeList != null){
            for (RcPrivilege p:privilegeList)
                for (ZTreeNode n:treeNodes){
                    if (p.getMenuId().equals(n.getId())){
                        n.setChecked(true);
                        break;
                    }
                }
        }
        String treeStr = treeService.buildZTree(treeNodes);
        modelAndView.addObject("zNodes",treeStr);
        modelAndView.addObject("roleId",id);
        modelAndView.setViewName("/system/admin/role/privilege");
        return modelAndView;
    }

}
