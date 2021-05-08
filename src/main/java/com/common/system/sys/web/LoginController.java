package com.common.system.sys.web;

import com.common.system.page.BaseController;
import com.common.system.shiro.ShiroKit;
import com.common.system.shiro.ShiroUser;
import com.common.system.page.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by Mr.Yangxiufeng on 2017/6/15.
 * Time:15:58
 * ProjectName:bg_perfm
 */
@Controller
public class LoginController extends BaseController {
    /**
     * 进入登录页面
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView getLogin(ModelAndView modelAndView){
        modelAndView.setViewName("/system/login");
        return modelAndView;
    }
    @RequestMapping(value = {"/postLogin"}, method = RequestMethod.POST)
    public String postLogin(@RequestParam(required = true) String username, @RequestParam(required = true) String password, ModelAndView modelAndView, HttpSession session){
        Subject subject = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password.toCharArray());
        try {
            subject.login(token);
            ShiroUser user = (ShiroUser) subject.getPrincipal();
            modelAndView.addObject("user",user);
            session.setAttribute("user",user);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return REDIRECT + "/";
    }
    @ApiOperation(value="前端页面登录")
    @RequestMapping(value = {"/frontLogin"})
    public @ResponseBody
    Result frontLogin(@RequestParam(required = true) String username, @RequestParam(required = true) String password, HttpSession session){
        Subject subject = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password.toCharArray());
        try {
            Result result=new Result();
            subject.login(token);
            ShiroUser user = (ShiroUser) subject.getPrincipal();
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("token",username+"-token");
            result.setData(map);
            session.setAttribute("user",user);
            return result;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return getJsonResult(false,"登陆失败","");
        }
    }
    @ApiOperation(value="前端登录成功后获取用户权限信息")
    @RequestMapping(value = {"/frontInfo"})
    public @ResponseBody
    Result frontInfo(@RequestParam(required = true) String username, @RequestParam(required = true) String password, HttpSession session){
        try {
            Subject subject = ShiroKit.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password.toCharArray());
            Result result=new Result();
            subject.login(token);
            ShiroUser user = (ShiroUser) subject.getPrincipal();
            HashMap<String,Object> map=new HashMap<String,Object>();
            //账号
            map.put("username",user.getUsername());
            //姓名
            map.put("name",user.getName());
            //用户id
            map.put("id",user.getId());
            //拥有的权限
            map.put("roles",user.getPermissionValues());
            result.setData(map);
            session.setAttribute("user",user);
            return result;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return getJsonResult(false,"登陆失败","");
        }
    }
    @ApiOperation(value="前端页面登出")
    @RequestMapping(value = {"/frontLoginOut"})
    public @ResponseBody
    Result frontLoginOut(){
        try {
            ShiroKit.getSubject().logout();
            return getJsonResult(true,"登出成功","");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return getJsonResult(false,"登出失败","");
        }
    }
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(){
        ShiroKit.getSubject().logout();
        return REDIRECT + "/";
    }
}
