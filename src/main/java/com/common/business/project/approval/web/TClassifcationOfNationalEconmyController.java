package com.common.business.project.approval.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.common.business.project.approval.entity.TClassifcationOfNationalEconmy;
import com.common.business.project.approval.mapper.TClassifcationOfNationalEconmyMapper;
import com.common.business.project.approval.service.TClassifcationOfNationalEconmyService;
import com.common.system.shiro.ShiroKit;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.TreeGridNode;
import com.common.system.sys.entity.TreeGridWrapper;
import com.common.system.sys.entity.ZTreeNode;
import com.common.system.sys.service.TreeGridService;
import com.common.system.sys.service.ZTreeService;
import com.common.system.page.BaseController;
import com.common.system.page.MsgCode;
import com.common.system.page.PageBean;
import com.common.system.page.Result;
import com.common.system.util.tree.TreeEntity;
import com.common.system.util.tree.TreeUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 存放绩效项目国民经济分类信息 前端控制器
 * </p>
 *
 * @author 安达
 * @since 2021-03-01
 */
@Controller
@RequestMapping("/tClassifcationOfNationalEconmy")
public class TClassifcationOfNationalEconmyController extends BaseController {

    @Autowired
    private TreeGridService treeGridService;
    @Autowired
    private ZTreeService zTreeService;
    @Autowired
    private TClassifcationOfNationalEconmyMapper tClassifcationOfNationalEconmyMapper;
    @Autowired
    private TClassifcationOfNationalEconmyService tClassifcationOfNationalEconmyService;



    /**
     * <p>Title: list</p>
     * <p>Description: 跳转到国民经济分类管理list页面</p>
     * @param modelAndView
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @RequestMapping(value="list",method= RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/business/project/approval/nationalEconmyList");
        return modelAndView;
    }



    /**
     * <p>Title: queryByPage</p>
     * <p>Description: list页面查询请求后台数据</p>
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @param bean 查询条件接受类
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @ResponseBody
    @GetMapping(value = "page")
    public PageBean<TClassifcationOfNationalEconmy> queryByPage(
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "length", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request, TClassifcationOfNationalEconmy bean){

        PageInfo<TClassifcationOfNationalEconmy> pageInfo = tClassifcationOfNationalEconmyService.listforpage((start / pageSize) + 1, pageSize, bean);

        return new PageBean<TClassifcationOfNationalEconmy>(pageInfo);

    }

    /**
     * 获取树形数据
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @ResponseBody
    @RequestMapping(value = "getTreeGridMenu")
    public TreeGridWrapper getTreeGridMenu(){
        List<TreeGridNode> list = treeGridService.getBudgetClassTreeGridNodes();
        TreeGridWrapper wrapper = new TreeGridWrapper();
        wrapper.setRows(list);
        wrapper.setTotal(list.size());
        return wrapper;

    }



    /**
     * <p>Title: add</p>
     * <p>Description: 跳转到新增</p>
     * @param modelAndView
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @RequestMapping(value="add",method=RequestMethod.GET)
    public ModelAndView add(ModelAndView modelAndView){
        ShiroUser user = (ShiroUser) ShiroKit.getSubject().getPrincipal();
        List<ZTreeNode> zTreeNodeList = zTreeService.getBudgetClassTreeNodes("add");
        String treeStr = zTreeService.buildZTree(zTreeNodeList);
        TClassifcationOfNationalEconmy bean = new TClassifcationOfNationalEconmy();
        modelAndView.addObject("bean", bean);
        modelAndView.addObject("nationalEconmy", treeStr);
        modelAndView.setViewName("/business/project/approval/nationalEconmyEdit");
        return modelAndView;
    }


    /**
     * 保存
     * @param tBudgetExpendFunctionClass 接受对象
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseBody
    public Result save(TClassifcationOfNationalEconmy tBudgetExpendFunctionClass){
        Result<Integer> result = new Result<>();
        if(!StringUtils.isEmpty(tBudgetExpendFunctionClass.getCId())){
            if (tBudgetExpendFunctionClass.getParaentId().equals(tBudgetExpendFunctionClass.getCId())){
                result.setMsg("父级菜单不允许选择自己");
                return result;
            }
        }
        if (StringUtils.isEmpty(tBudgetExpendFunctionClass.getClassName())){
            result.setMsg("类别名称不能为空");
            return result;
        }
        if (StringUtils.isEmpty(tBudgetExpendFunctionClass.getClassCode())){
            result.setMsg("分类代码不能为空");
            return result;
        }
        try {
            tClassifcationOfNationalEconmyService.insertOrUpdate(tBudgetExpendFunctionClass);
            result.setStatus(true);
            result.setMsg("OK");
            result.setCode(MsgCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("系统错误");
        }
        return result;
    }


    /**
     * <p>Title: edit</p>
     * <p>Description: 跳转到修改页面</p>
     * @param modelAndView
     * @param id TClassifcationOfNationalEconmy的主键id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @RequestMapping(value="edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer id){
        TClassifcationOfNationalEconmy bean = null;
        String treeStr = null;
        try {
            bean = tClassifcationOfNationalEconmyMapper.selectListLeftJion(String.valueOf(id));
            List<ZTreeNode> zTreeNodeList = zTreeService.getBudgetClassTreeNodes("edit");
            for (ZTreeNode node:zTreeNodeList
            ) {
                if (node.getId().equals(String.valueOf(bean.getCId()))){
//                    modelAndView.addObject("pName",node.getName());
                    node.setChecked(true);
                }
            }
            treeStr = zTreeService.buildZTree(zTreeNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("id", id);
        modelAndView.addObject("bean", bean);
        modelAndView.addObject("nationalEconmy", treeStr);
        modelAndView.setViewName("/business/project/approval/nationalEconmyEdit");
        return modelAndView;
    }

    /**
     * <p>Title: view</p>
     * <p>Description: 跳转到查看页面</p>
     * @param modelAndView
     * @param id TClassifcationOfNationalEconmy的主键id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @RequestMapping(value="view/{id}",method=RequestMethod.GET)
    public ModelAndView view(ModelAndView modelAndView,@PathVariable Integer id){
        TClassifcationOfNationalEconmy bean = null;
        try {
            bean = tClassifcationOfNationalEconmyService.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("bean", bean);
        modelAndView.setViewName("/business/project/approval/nationalEconmyView");
        return modelAndView;
    }

    /**
     * <p>Title: delete</p>
     * <p>Description: 根据id删除</p>
     * @param modelAndView
     * @param id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月5日
     * @updator 陈睿超
     * @updatetime 2021年3月5日
     */
    @ResponseBody
    @RequestMapping(value="delete/{id}",method=RequestMethod.GET)
    public Result delete(ModelAndView modelAndView,@PathVariable Integer id){
        Result result = new Result();
        try {
            TClassifcationOfNationalEconmy tBudgetExpendFunctionClass = new TClassifcationOfNationalEconmy();
            tBudgetExpendFunctionClass.setParaentId(id);
            Wrapper<TClassifcationOfNationalEconmy> queryWrapper =  new EntityWrapper<TClassifcationOfNationalEconmy>(tBudgetExpendFunctionClass);
            int count = tClassifcationOfNationalEconmyService.selectCount(queryWrapper);
            if (count>=1){
                result.setCode(MsgCode.FAILED);
                result.setMsg("该支出功能分类下还有子节点，请先删除子节点");
                return result;
            }
            tClassifcationOfNationalEconmyService.deleteById(id);
            result.setMsg("操作成功点");
            result.setCode(MsgCode.SUCCESS);
            result.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(MsgCode.FAILED);
            result.setStatus(false);
        }
        return result;
    }


    /**
     * Title: 
     * Description 获取tree类型数据
     * @author: 陈睿超
     * @createDate: 2021/3/10 15:49
     * @updater: 陈睿超
     * @updateDate: 2021/3/10 15:49
     * @param : 
     * @return
     **/
    @ResponseBody
    @RequestMapping(value = "getTreeData")
    public Result getTreeData(){
        //查询数据，结果类型是List<TreeEntity>
        List<TreeEntity> list = tClassifcationOfNationalEconmyService.getTreeEntity();
        // 调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
        return getJsonResult(true,null, TreeUtils.getTreeList("5",list));
    }



}
