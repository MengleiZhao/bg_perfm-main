package com.common.business.library.uptrequest.service.impl;

import com.baomidou.mybatisplus.MybatisSqlSessionTemplate;
import com.common.business.library.indexs.entity.TLibraryIndexSystem;
import com.common.business.library.indexs.mapper.TLibraryIndexSystemMapper;
import com.common.business.library.uptrequest.entity.BussinessFlowLibraryIndexSystem;
import com.common.business.library.uptrequest.entity.TLibraryIndexSystemUpt;
import com.common.business.library.uptrequest.mapper.BussinessFlowLibraryIndexSystemMapper;
import com.common.business.library.uptrequest.mapper.TLibraryIndexSystemUptMapper;
import com.common.business.library.uptrequest.service.BussinessFlowLibraryIndexSystemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.library.uptrequest.web.libraryIndexSystemVo;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.txw2.output.IndentingXMLFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-04-25
 */
@Service
public class BussinessFlowLibraryIndexSystemServiceImpl extends ServiceImpl<BussinessFlowLibraryIndexSystemMapper, BussinessFlowLibraryIndexSystem> implements BussinessFlowLibraryIndexSystemService {

    @Autowired
    private BussinessFlowLibraryIndexSystemMapper flowLibraryIndexSystemMapper;//业务 mapper
    @Autowired
    private TLibraryIndexSystemMapper indexSystemMapper;//绩效指标库 mapper
    @Autowired
    private TLibraryIndexSystemUptMapper indexSystemUptMapper;// 绩效指标修改表 mapper

    /**
     * 1-申请主页面显示
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/26 12:00
     * @updateTime 2021/4/26 12:00
     */
    @Override
    public PageInfo<TLibraryIndexSystem> applicyPage(Integer current, Integer size, TLibraryIndexSystem libraryIndexSystem, String search) throws Exception {
        PageHelper.startPage(current,size);
        //查询调整状态不为出库并且状态不为已审批（UPT_TYPE='1' and  DATA_STAUTS='2'）的绩效指标
        List<TLibraryIndexSystem> libraryIndexSystems=indexSystemMapper.applicyPage(libraryIndexSystem,search);
        //遍历
        for(TLibraryIndexSystem index:libraryIndexSystems){

            //1.设置 发布时间 截取时间
            SimpleDateFormat sdf=new SimpleDateFormat();
            index.setReleaseDate(sdf.format(index.getReleaseTime()).substring(0,sdf.format(index.getReleaseTime()).lastIndexOf("")));

            //2.修改 替换
            //判断绩效指标的状态如果是 3-修改 且 数据状态是1-审批中或者0-暂存 的数据,如果是 则将修改的数据进行替换
            if("3".equals(index.getUptType())){
                if("0".equals(index.getDataStauts())||"1".equals(index.getDataStauts())){
                    index=replaceLibraryIndex(index);
                }
            }
        }
        return new PageInfo<>(libraryIndexSystems);
    }

    /**
     * 3-绩效指标库 出库申请 提交/暂存
     * 整体思路：
     *      1）设置修改指标库的某些字段（提交 设置下级审批人）
     *      2）修改绩效指标库
     *      3）提交 增加业务流数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 17:08
     * @updateTime 2021/4/27 17:08
     */
    @Override
    public void outIndexSystem(TLibraryIndexSystem indexSystem, ShiroUser user) throws Exception {
        //如果是提交 设置下级审批人
        if("1".equals(indexSystem.getDataStauts())){
            //暂时设置 审批人是 李安达--张强
            indexSystem.setCurrCheckId("72");
            indexSystem.setCurrCheckName("李安达");
        }
        //申请人id、申请人姓名
        indexSystem.setApplicantId(String.valueOf(user.getId()));
        indexSystem.setApplicantName(String.valueOf(user.getName()));
        //修改绩效指标表部分数据
        indexSystemMapper.updateSomeColumns(indexSystem);

        //如果是提交，则插入审批数据
        if("1".equals(indexSystem.getDataStauts())){
            //Tool:1.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
            //设置审批人：发起人（当前登录人）、一级审批（达叔）、二级审批（张强）
            Integer[] checkerIds={user.getId(),72,65};
            installChecker(checkerIds,indexSystem.getIdX(),user);
        }

    }


    /**
     * Tool:1.（replaceLibraryIndex）替换指标库中的数据为指标修改表中的数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 9:49
     * @updateTime 2021/4/27 9:49
     */
    public TLibraryIndexSystem replaceLibraryIndex(TLibraryIndexSystem index){
        //查询修改表中的数据
        TLibraryIndexSystemUpt queryUpt=new TLibraryIndexSystemUpt();
        queryUpt.setIdX(index.getIdX());
        TLibraryIndexSystemUpt indexSystemUpt=indexSystemUptMapper.selectOne(queryUpt);
        //将修改表中的数据替换掉指标库中的数据
        //指标编码、名称
        index.setIndexCode1(indexSystemUpt.getIndexCode1());
        index.setIndexName1(indexSystemUpt.getIndexName1());
        index.setIndexCode2(indexSystemUpt.getIndexCode2());
        index.setIndexName2(indexSystemUpt.getIndexName2());
        index.setIndexCode3(indexSystemUpt.getIndexCode3());
        index.setIndexName3(indexSystemUpt.getIndexName3());

        //指标评价开始(结束)年度
        index.setIndexYears1(indexSystemUpt.getIndexYears1());
        index.setIndexYears2(indexSystemUpt.getIndexYears2());

        //三级指标分值
        index.setIndexScore3(indexSystemUpt.getIndexScore3());
        //指标解释（三级）
        index.setIndexExplanation(indexSystemUpt.getIndexExplanation());
        //数据来源
        index.setDataSources(indexSystemUpt.getDataSources());
        //备注
        index.setIndexRemark(indexSystemUpt.getIndexRemark());
        //预算支出功能分类ID
        index.setBudFunctClassId(indexSystemUpt.getBudFunctClassId());
        //预算支出功能分类
        index.setBudFunctClassName(indexSystemUpt.getBudFunctClassName());
        //国民经济行业分类ID
        index.setNationEcoIndustClassId(indexSystemUpt.getNationEcoIndustClassId());
        //国民经济行业分类
        index.setNationEcoIndustClassName(indexSystemUpt.getNationEcoIndustClassName());
        return index;
    }

    /**
     * Tool:2.(installChecker)设置该申请的审批人都有哪些（发起人、一级审批、二级审批 共三个人）
     * @param checkerIds 审批人id数组（不用集合的原因：集合不能存放重复的数据）
     * @param idX        绩效指标主键值
     * @param user       当前登录者，用于设置业务流表中的创建人
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/27 17:52
     * @updateTime 2021/4/27 17:52
     */
    public void installChecker(Integer[] checkerIds,Integer idX,ShiroUser user)throws Exception{
        //
    }


}
