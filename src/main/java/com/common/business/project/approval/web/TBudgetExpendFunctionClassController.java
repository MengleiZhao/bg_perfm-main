package com.common.business.project.approval.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.common.business.project.approval.entity.TBudgetExpendFunctionClass;
import com.common.business.project.approval.mapper.TBudgetExpendFunctionClassMapper;
import com.common.business.project.approval.service.TBudgetExpendFunctionClassService;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 存放绩效项目预算支出功能分类信息 前端控制器
 * </p>
 *  
 * @author 安达
 * @since 2021-03-01
 * @updator 陈睿超
 * @updatetime 2021年3月3日
 */
@Controller
@RequestMapping("/tBudgetExpendFunctionClass")
public class TBudgetExpendFunctionClassController extends BaseController {

    @Autowired
    private TBudgetExpendFunctionClassService tBudgetExpendFunctionClassService;
    @Autowired
    private TreeGridService treeGridService;
    @Autowired
    private ZTreeService zTreeService;
    @Autowired
    private TBudgetExpendFunctionClassMapper tBudgetExpendFunctionClassMapper;


    /**
     * <p>Title: list</p>
     * <p>Description: 跳转到资料清单模板管理list页面</p>
     * @param modelAndView
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @RequestMapping(value="list",method= RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/business/project/approval/budgetExpendFunctionList");
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
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @ResponseBody
    @GetMapping(value = "page")
    public PageBean<TBudgetExpendFunctionClass> queryByPage(
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "length", defaultValue = "10") int pageSize,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request,TBudgetExpendFunctionClass bean){
        PageInfo<TBudgetExpendFunctionClass> pageInfo = tBudgetExpendFunctionClassService.listforpage((start / pageSize) + 1, pageSize, bean);
        return new PageBean<TBudgetExpendFunctionClass>(pageInfo);

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
        List<TreeGridNode> list = treeGridService.getBudgetExpendTreeGridNodes();
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
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @RequestMapping(value="add",method=RequestMethod.GET)
    public ModelAndView add(ModelAndView modelAndView){
        ShiroUser user = (ShiroUser) ShiroKit.getSubject().getPrincipal();
        List<ZTreeNode> zTreeNodeList = zTreeService.getBudgetExpendTreeNodes("add");
        String treeStr = zTreeService.buildZTree(zTreeNodeList);
        TBudgetExpendFunctionClass bean = new TBudgetExpendFunctionClass();
        modelAndView.addObject("bean", bean);
        modelAndView.addObject("budgetExpendFunction", treeStr);
        modelAndView.setViewName("/business/project/approval/budgetExpendFunctionEdit");
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
    public Result save(TBudgetExpendFunctionClass tBudgetExpendFunctionClass){
        Result<Integer> result = new Result<>();
        if (tBudgetExpendFunctionClass.getParaentId().equals(tBudgetExpendFunctionClass.getBId())){
            result.setMsg("父级菜单不允许选择自己");
            return result;
        }
        if (StringUtils.isEmpty(tBudgetExpendFunctionClass.getFuncName())){
            result.setMsg("支出功能名称不能为空");
            return result;
        }
        if (StringUtils.isEmpty(tBudgetExpendFunctionClass.getFuncCode())){
            result.setMsg("支出功能编号不能为空");
            return result;
        }
        try {
            tBudgetExpendFunctionClassService.insertOrUpdate(tBudgetExpendFunctionClass);
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
     * @param id TBudgetExpendFunctionClass的主键id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @RequestMapping(value="edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable Integer id){
        TBudgetExpendFunctionClass bean = null;
        String treeStr = null;
        try {
            bean = tBudgetExpendFunctionClassMapper.selectListLeftJion(String.valueOf(id));
            List<ZTreeNode> zTreeNodeList = zTreeService.getBudgetExpendTreeNodes("edit");
            treeStr = zTreeService.buildZTree(zTreeNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("id", id);
        modelAndView.addObject("bean", bean);
        modelAndView.addObject("budgetExpendFunction", treeStr);
        modelAndView.setViewName("/business/project/approval/budgetExpendFunctionEdit");
        return modelAndView;
    }

    /**
     * <p>Title: view</p>
     * <p>Description: 跳转到查看页面</p>
     * @param modelAndView
     * @param id TBudgetExpendFunctionClass的主键id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @RequestMapping(value="view/{id}",method=RequestMethod.GET)
    public ModelAndView view(ModelAndView modelAndView,@PathVariable Integer id){
        TBudgetExpendFunctionClass td = null;
        try {
            td = tBudgetExpendFunctionClassService.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("td", td);
        modelAndView.setViewName("/business/project/approval/budgetExpendFunctionview");
        return modelAndView;
    }

    /**
     * <p>Title: delete</p>
     * <p>Description: 根据id删除</p>
     * @param modelAndView
     * @param id
     * @return
     * @author 陈睿超
     * @createtime 2021年3月3日
     * @updator 陈睿超
     * @updatetime 2021年3月3日
     */
    @ResponseBody
    @RequestMapping(value="delete/{id}",method=RequestMethod.GET)
    public Result delete(ModelAndView modelAndView,@PathVariable Integer id){
        Result result = new Result();
        try {
            TBudgetExpendFunctionClass tBudgetExpendFunctionClass = new TBudgetExpendFunctionClass();
            tBudgetExpendFunctionClass.setParaentId(id);
            Wrapper<TBudgetExpendFunctionClass> queryWrapper =  new EntityWrapper<TBudgetExpendFunctionClass>(tBudgetExpendFunctionClass);
            int count = tBudgetExpendFunctionClassService.selectCount(queryWrapper);
            if (count>=1){
                result.setCode(MsgCode.FAILED);
                result.setMsg("该支出功能分类下还有子节点，请先删除子节点");
                return result;
            }
            tBudgetExpendFunctionClassService.deleteById(id);
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
    public Result getTreeData(Integer idA){
        //查询数据，结果类型是List<TreeEntity>
        List<TreeEntity> list = tBudgetExpendFunctionClassService.getTreeEntity();
        //获取偷不
        // 调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
        return getJsonResult(true,null,TreeUtils.getTreeList("190",list));
    }

}
