package com.common.business.planmgr.scheme.mkoutline.service.impl;

import com.common.business.planmgr.scheme.mkoutline.entity.TResearchOutlineTemp;
import com.common.business.planmgr.scheme.mkoutline.mapper.TResearchOutlineTempMapper;
import com.common.business.planmgr.scheme.mkoutline.service.TResearchOutlineTempService;
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
 *  TResearchOutlineTempServiceImpl [调研提纲模板表 服务实现层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-05
 */
@Service
public class TResearchOutlineTempServiceImpl extends ServiceImpl<TResearchOutlineTempMapper, TResearchOutlineTemp> implements TResearchOutlineTempService {

    @Autowired
    private TResearchOutlineTempMapper tResearchOutlineTempMapper;


    /**
     * 1.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层] 分页显示数据
     * @param start 第几页
     * @param pageSize 每页要显示的数据值
     * @param tResearchOutlineTemp 据某些字段(封装成了TResearchOutlineTemp对象)查询时分页显示数据
     * @return PageInfo<TResearchOutlineTemp  返回查到的数据集合
            * @author 田鑫艳
            * @createTime 2021/3/5 9:39
            * @updateTime 2021/3/5 9:39
     */
    @Override
    public PageInfo<TResearchOutlineTemp> queryForPage(int start, Integer pageSize, TResearchOutlineTemp tResearchOutlineTemp)throws Exception {
        //1.拿到数据
        PageHelper.startPage(start, pageSize);
        List<TResearchOutlineTemp> showDatas = tResearchOutlineTempMapper.queryForPage(tResearchOutlineTemp);//接收的是TResearchOutlineTemp类型的列表
        System.out.println("调研提纲模板管理--显示数据服务实现层 从后端拿到的集合对象："+showDatas.size());
        return new PageInfo<TResearchOutlineTemp>(showDatas);//将list列表 转换成PageInfo的形式并返回

    }

    /**
     * 2.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层] 根据主键id显示详细数据
     * @param idX 要查询的主键id值
     * @return Result<TResearchOutlineTemp> 返回查询到的数据
     * @author 田鑫艳
     * @createTime 2021/3/5 10:57
     * @updateTime 2021/3/5 10:57
     */
    @Override
    public Result<TResearchOutlineTemp> selectById(Integer idX) {
        Result<TResearchOutlineTemp> result = new Result<>();
        TResearchOutlineTemp tResearchOutlineTemp = tResearchOutlineTempMapper.selectByPrimaryKey(idX);
        System.out.println("调研提纲模板管理--服务实现层--查看当前id的详细信息："+tResearchOutlineTemp);
        //如果拿到的详细信息为空，则进行提示
        if (tResearchOutlineTemp == null){
            result.setStatus(false);
            result.setCode(MsgCode.FAILED);
            result.setMsg("没有该提纲");
            return result;
        }
        result.setData(tResearchOutlineTemp);
        result.setStatus(true);
        result.setCode(MsgCode.SUCCESS);
        return result ;
    }

    /**
     * 3.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层]  根据id字段删除数据
     * @author:田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     * @param idX 要删除的主键id值
     * @return int  删除成功时，会返回数据库中数据改变的行数 [删除一条数据成功时，返回值为1]
     * @throws Exception
     */
    @Transactional
    @Override
    public int deleteById(Integer idX) throws Exception{
        return tResearchOutlineTempMapper.deleteByIdx(idX);
    }

    /**
     * 4.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层]   新增一条数据
     * @param tResearchOutlineTemp TResearchOutlineTemp实体类[绩效新闻实体类]
     * @return Result<Integer>
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    @Transactional
    @Override
    public Result<Integer> save(TResearchOutlineTemp tResearchOutlineTemp) throws Exception {
        Result<Integer> result=new Result<>();
        result.setStatus(false);//设置状态，默认是失败
        result.setCode(MsgCode.FAILED);//设置状态码，默认是失败,失败的状态码为MsgCode.FAILED的值，即为：1001

        //1.查询该 “调研提纲名称"是否已经存在
        if (selectByOutlineName(tResearchOutlineTemp.getOutlineName())!= null){
            result.setMsg("调研提纲已存在");
            return result;
        }

        //2.判断不存在时才可以新增
        try {
            //2-1.得到目前数据库里最大的提纲序号，如果为空，则设置默认值为100000
            Integer max=selectOrderNoMax();
            if(max==null){
                tResearchOutlineTemp.setOrderNo(100000);
            }else{
                tResearchOutlineTemp.setOrderNo(max+1);
            }
            tResearchOutlineTempMapper.insertNewsMgr(tResearchOutlineTemp);//新增t_performance_news_mgr表
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
     * 5.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层]  通过“绩效新闻标题”查询
     * @param title 绩效新闻标题
     * @return TResearchOutlineTemp 查询后得到的数据
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    @Override
    public TResearchOutlineTemp selectByOutlineName(String title) throws Exception{
        return tResearchOutlineTempMapper.selectByOutlineName(title);
    }



    /**
     * 6.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层] 通过主键id修改数据
     * @param tResearchOutlineTemp TResearchOutlineTemp[绩效新闻实体类]
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/5 17:57
     * @updateTime 2021/3/5 17:57
     */
    @Transactional
    @Override
    public Result<Integer> update(TResearchOutlineTemp tResearchOutlineTemp) {
        Result<Integer> result = new Result<>();
        result.setStatus(false);
        result.setCode(MsgCode.FAILED);
        //1.判断属性值是否为空
        if (!StringUtils.hasText(tResearchOutlineTemp.getOutlineName())) {
            result.setMsg("不能为空");
            return result;
        }

        //2.判断都不为空时，才可以修改
        try {
            tResearchOutlineTempMapper.updateByPrimaryKey(tResearchOutlineTemp);
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
     * 7.TResearchOutlineTempServiceImpl [调研提纲模板管理 服务实现层] 查询最大的序号
     * @return Integer 返回最大的序号值
     * @author 田鑫艳
     * @createTime 2021/3/5 21:07
     * @updateTime 2021/3/5 21:07
     */
    @Override
    public Integer selectOrderNoMax() {
        return tResearchOutlineTempMapper.selectOrderNoMax();
    }


}
