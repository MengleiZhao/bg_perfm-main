package com.common.business.index.service.impl;

import com.common.business.index.entity.TPerformanceNewsMgr;
import com.common.business.index.mapper.TPerformanceNewsMgrMapper;
import com.common.business.index.service.TPerformanceNewsMgrService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.page.MsgCode;
import com.common.system.page.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  绩效新闻管理 服务实现层
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-04
 */
@Service
public class TPerformanceNewsMgrServiceImpl extends ServiceImpl<TPerformanceNewsMgrMapper, TPerformanceNewsMgr> implements TPerformanceNewsMgrService {

    @Autowired
    private TPerformanceNewsMgrMapper tperformanceNewsMgrMapper;


    /**
     * 1.TPerformanceNewsMgrServiceImpl [绩效新闻管理 服务实现层] 分页显示数据
     * @param start
     * @param pageSize
     * @param tPerformanceNewsMgr
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 9:39
     * @updateTime 2021/3/5 9:39
     */
    @Override
    public PageInfo<TPerformanceNewsMgr> queryForPage(int start, Integer pageSize, TPerformanceNewsMgr tPerformanceNewsMgr)throws Exception {
        //1.拿到数据
        PageHelper.startPage(start, pageSize);
        List<TPerformanceNewsMgr> showDatas = tperformanceNewsMgrMapper.queryForPage(tPerformanceNewsMgr);//接收的是tPerformanceNewsMgr类型的列表
        System.out.println("公告管理--显示数据服务实现层 从后端拿到的集合对象："+showDatas.size());
        return new PageInfo<TPerformanceNewsMgr>(showDatas);//将list列表 转换成PageInfo的形式并返回

    }

    /**
     * 2.TPerformanceNewsMgrServiceImpl [绩效新闻管理 服务实现层] 根据主键id显示详细数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 10:57
     * @updateTime 2021/3/5 10:57
     */
    @Override
    public Result<TPerformanceNewsMgr> selectById(Integer idX) {
        Result<TPerformanceNewsMgr> result = new Result<>();
        TPerformanceNewsMgr tPerformanceNewsMgr = tperformanceNewsMgrMapper.selectByPrimaryKey(idX);
        System.out.println("绩效新闻管理--服务实现层--查看当前id的详细信息："+tPerformanceNewsMgr);
        //如果拿到的详细信息为空，则进行提示
        if (tPerformanceNewsMgr == null){
            result.setStatus(false);
            result.setCode(MsgCode.FAILED);
            result.setMsg("没有该绩效新闻");
            return result;
        }
        result.setData(tPerformanceNewsMgr);
        result.setStatus(true);
        result.setCode(MsgCode.SUCCESS);
        return result ;
    }

    /**
     * 3.TPerformanceNewsMgrServiceImpl [绩效新闻管理 服务实现层]  根据id字段删除数据
     * @param idX 主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 10:57
     * @updateTime 2021/3/5 10:57
     */
    @Transactional
    @Override
    public int deleteById(Integer idX) throws Exception{
        return tperformanceNewsMgrMapper.tPerformanceNewsDeleteById(idX);
    }

    /**
     * 4.TPerformanceNewsMgrServiceImpl [绩效新闻管理 服务实现层]  新增一条数据
     * @param tPerformanceNewsMgr TPerformanceNewsMgr实体类[通知公告实体类]
     * @return
     */
    @Transactional
    @Override
    public Result<Integer> save(TPerformanceNewsMgr tPerformanceNewsMgr) throws Exception {
        Result<Integer> result=new Result<>();
        result.setStatus(false);//设置状态，默认是失败
        result.setCode(MsgCode.FAILED);//设置状态码，默认是失败,失败的状态码为MsgCode.FAILED的值，即为：1001
        if (selectByTitle(tPerformanceNewsMgr.getTitle())!= null){
            result.setMsg("公告标题已存在");
            return result;
        }
        if (selectBySubtitle(tPerformanceNewsMgr.getSubtitle())!= null){
            result.setMsg("公告副标题已存在");
            return result;
        }
        //2.判断都不为存在时才可以新增
        try {
            tperformanceNewsMgrMapper.tPerformanceNewsInsert(tPerformanceNewsMgr);//新增t_performance_news_mgr表
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setCode(MsgCode.FAILED);
            result.setMsg("操作失败："+e.getLocalizedMessage());
        }
        return result;


    }
    /**
     * 5.TPerformanceNewsMgrServiceImpl [绩效新闻管理 服务实现层]  通过“公告标题”查询
     * @param title 公告标题
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:12
     * @updateTime 2021/3/4 17:12
     */
    @Override
    public TPerformanceNewsMgr selectByTitle(String title) throws Exception{
        return tperformanceNewsMgrMapper.selectByTitle(title);
    }

    /**
     * 6.TPerformanceNewsMgrServiceImpl [绩效新闻管理 服务实现层]  通过“公告副标题”查询
     * @param subtitle 公告副标题
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:12
     * @updateTime 2021/3/4 17:12
     */
    @Override
    public TPerformanceNewsMgr selectBySubtitle(String subtitle) throws Exception{
        return tperformanceNewsMgrMapper.selectBySubtitle(subtitle);
    }

    /**
     * 7.TPerformanceNewsMgrServiceImpl [绩效新闻管理 服务实现层] 通过主键id修改数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/4 17:47
     * @updateTime 2021/3/4 17:47
     */
    @Transactional
    @Override
    public Result<Integer> update(TPerformanceNewsMgr tPerformanceNewsMgr) {
        Result<Integer> result = new Result<>();
        result.setStatus(false);
        result.setCode(MsgCode.FAILED);
        //1.判断属性值是否为空
        if (!StringUtils.hasText(tPerformanceNewsMgr.getTitle())) {
            result.setMsg("绩效新闻标题不能为空");
            return result;
        }
        if (!StringUtils.hasText(tPerformanceNewsMgr.getSubtitle())) {
            result.setMsg("绩效新闻副标题不能为空");
            return result;
        }
        if (!StringUtils.hasText(tPerformanceNewsMgr.getNoticeContent())) {
            result.setMsg("绩效新闻内容不能为空");
            return result;
        }
        if (!StringUtils.hasText(tPerformanceNewsMgr.getCreateor())) {
            result.setMsg("绩效新闻发布人不能为空");
            return result;
        }
        //2.判断都不为空时，才可以修改
        try {
            tperformanceNewsMgrMapper.updateByPrimaryKey(tPerformanceNewsMgr);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setCode(MsgCode.FAILED);
            result.setMsg("操作失败："+e.getLocalizedMessage());
        }
        return result;
    }
}
