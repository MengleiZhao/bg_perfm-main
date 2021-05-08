package com.common.business.project.approval.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TMainProjectSync;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TMainProjectSyncMapper;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.project.approval.service.TEvalUnitInfoService;
import com.common.business.project.approval.service.TMainProjectSyncService;
import com.common.business.project.approval.service.TProPerformanceInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-08
 */
@Service
@Transactional
public class TProPerformanceInfoServiceImpl extends ServiceImpl<TProPerformanceInfoMapper, TProPerformanceInfo>
        implements TProPerformanceInfoService {

    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;
    @Autowired
    private TEvalUnitInfoService tEvalUnitInfoService;
    @Autowired
    private TMainProjectSyncMapper tMainProjectSyncMapper;

    @Override
    public PageInfo<TProPerformanceInfo> listForPage(Integer pageNum, Integer pageSize, TProPerformanceInfo bean,
                                                     String search) {
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<TProPerformanceInfo> page = proPerformanceInfoMapper.selectLikePageList(pageNum, pageSize, bean, search);
        PageInfo<TProPerformanceInfo> pageInfo = new PageInfo<TProPerformanceInfo>(page);
        //pageInfo.setTotal(baseMapper.countLikePage(bean, search));
        return pageInfo;
    }

    @Override
    public PageInfo<TProPerformanceInfo> listForLedgerPage(Integer pageNum, Integer pageSize, TProPerformanceInfo bean,
                                                           String search) {
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<TProPerformanceInfo> page = baseMapper.selectLikeLedgerPageList(pageNum, pageSize, bean,
                search);
        PageInfo<TProPerformanceInfo> pageInfo = new PageInfo<TProPerformanceInfo>(page);
        pageInfo.setTotal(baseMapper.countLikeLedgerPage(bean, search));
        return pageInfo;
    }

    @Override
    public PageInfo<TProPerformanceInfo> listApprovalForPage(Integer pageNum, Integer pageSize,
                                                             TProPerformanceInfo bean) {
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<TProPerformanceInfo> page = baseMapper.selectApprovalPageList(pageNum, pageSize, bean);
        PageInfo<TProPerformanceInfo> pageInfo = new PageInfo<TProPerformanceInfo>(page);
        return pageInfo;
    }


    /**
     * Title:
     * Description 保存
     *
     * @param idMainA             主项目ID
     * @param tProPerformanceInfo 保存基础类
     * @param user                当前登录人
     * @return
     * @author: 陈睿超
     * @createDate: 2021/3/10 17:04
     * @updater: 陈睿超
     * @updateDate: 2021/3/10 17:04
     **/
    @Transactional
    @Override
    public Boolean save(Integer idMainA, TProPerformanceInfo tProPerformanceInfo, ShiroUser user) {
        
        List<TEvalUnitInfo> tEvalUnitInfoList = (List<TEvalUnitInfo>) tProPerformanceInfo.getEvalUnitInfoList();
        List<TProPerformanceInfo> tProPerformanceInfoList = (List<TProPerformanceInfo>) tProPerformanceInfo.getProPerformanceInfolist();
        if (null == tProPerformanceInfo.getIdA()) {
            //新增
            //需要拆分
            if ("1".equals(tProPerformanceInfo.getProIsdismant())) {

                for (int i = 0; i < tProPerformanceInfoList.size(); i++) {
                    TProPerformanceInfo bean = tProPerformanceInfoList.get(i);
                    //组建工作组N-否（默认）
                    bean.setIsSetupWorkingGroup("N");
                    //组建专家组
                    bean.setExpertGroupIsformed("0");
                    //评价报告审核状态
                    bean.setEvalReportStatus("0");
                    //工作总结审核状态
                    bean.setWorkSummaryStatus("0");
                    //项目档案归档审核状态
                    bean.setArchivesClassStatus("0");

                    //获取主项目的code、name、业务类型、项目合伙人、项目经理
                    bean.setParentProCode(tProPerformanceInfo.getParentProCode());
                    bean.setParentProName(tProPerformanceInfo.getParentProName());
//                    bean.setProCode(tProPerformanceInfo.getParentProCode() + "-Z" + String.format("%02d", i + 1));
//                    bean.setProName(tProPerformanceInfo.getParentProName());
                    bean.setBussinessType(tProPerformanceInfo.getBussinessType());
                    bean.setBussinessTypeId(tProPerformanceInfo.getBussinessTypeId());
                    bean.setProPartenName(tProPerformanceInfo.getProPartenName());
                    bean.setProPartenId(tProPerformanceInfo.getProPartenId());
                    bean.setProManagerId(tProPerformanceInfo.getProManagerId());
                    bean.setProManagerName(tProPerformanceInfo.getProManagerName());
//                    bean.setProIndepReviewId(tProPerformanceInfo.getProIndepReviewId());
//                    bean.setProIndepReviewName(tProPerformanceInfo.getProIndepReviewName());

                    bean.setProStatus(tProPerformanceInfo.getProStatus());
                    bean.setProIsdismant(tProPerformanceInfo.getProIsdismant());
                    bean.setEvaluationObj(tProPerformanceInfo.getEvaluationObj());
                    bean.setProSecretaryId(tProPerformanceInfo.getProSecretaryId());
                    bean.setProSecretaryName(tProPerformanceInfo.getProSecretaryName());
                    bean.setProIsSecret(tProPerformanceInfo.getProIsSecret());
                    //获取主项目的项目四级分类
//                    bean.setProLevel4ClassName(tProPerformanceInfo.getProLevel4ClassName());
//                    bean.setProLevel4ClassId(tProPerformanceInfo.getProLevel4ClassId());
                    //修改人
                    bean.setUpdater(user.getName());
                    bean.setUpdateTime(new Date());

                    if ("1".equals(bean.getProStatus())) {
                        bean.setProApprovalTime(new Date());
                        bean.setProApprovaler(user.getName());
                    }
                    insertOrUpdate(bean);

                    //循环重新添加每一个子项目的被评价单位信息
                    for (TEvalUnitInfo ebean : tEvalUnitInfoList) {
                        //设置外键
                        ebean.setIdB(null);
                        ebean.setIdA(bean.getIdA());
                        tEvalUnitInfoService.insertOrUpdate(ebean);
                    }
                }
                //设置主项目状态变成“1“
                tMainProjectSyncMapper.updateProStatusById(idMainA, "1");
                return true;
            } else if ("0".equals(tProPerformanceInfo.getProIsdismant())) {
                //不需要拆分
                //组建工作组N-否（默认）
                tProPerformanceInfo.setIsSetupWorkingGroup("N");
                //组建专家组
                tProPerformanceInfo.setExpertGroupIsformed("0");
                //评价报告审核状态
                tProPerformanceInfo.setEvalReportStatus("0");
                //工作总结审核状态
                tProPerformanceInfo.setWorkSummaryStatus("0");
                //项目档案归档审核状态
                tProPerformanceInfo.setArchivesClassStatus("0");

                tProPerformanceInfo.setUpdater(user.getName());
                tProPerformanceInfo.setUpdateTime(new Date());
                if ("1".equals(tProPerformanceInfo.getProStatus())) {
                    tProPerformanceInfo.setProApprovalTime(new Date());
                    tProPerformanceInfo.setProApprovaler(user.getName());
                }
                //重新修改
                tProPerformanceInfo.setProCode(tProPerformanceInfo.getParentProCode());
//                tProPerformanceInfo.setProCode(tProPerformanceInfo.getParentProCode() + "-Z01");
                tProPerformanceInfo.setProName(tProPerformanceInfo.getParentProName());
                //保存
                insertOrUpdate(tProPerformanceInfo);
                //循环添加被评价单位信息
                for (TEvalUnitInfo ebean : tEvalUnitInfoList) {
                    //设置外键
                    ebean.setIdB(null);
                    ebean.setIdA(tProPerformanceInfo.getIdA());
                    tEvalUnitInfoService.insertOrUpdate(ebean);
                }

                //设置主项目状态变成“1“
                tMainProjectSyncMapper.updateProStatusById(idMainA, "1");
                return true;
            }

        } else {
            //修改
            TProPerformanceInfo oldbean = selectById(tProPerformanceInfo.getIdA());
            //根据code查询主项目
            TMainProjectSync mainPro = tMainProjectSyncMapper.findByCloumn("MAIN_PRO_CODE", oldbean.getParentProCode());
            idMainA=mainPro.getIdA();
            if ("1".equals(oldbean.getProIsdismant())) {
                //原来拆分
//                查询原来的数据，并删除
                EntityWrapper tProPerformanceInfoWrapper = new EntityWrapper();
                tProPerformanceInfoWrapper.eq("PARENT_PRO_CODE", oldbean.getParentProCode());
                tProPerformanceInfoWrapper.in("PRO_STATUS", "1,0");
                List<TProPerformanceInfo> oldProPerformanceInfoList = selectList(tProPerformanceInfoWrapper);

//                查询原来的子项目，删除
                EntityWrapper tEvalUnitInfoWrapper = new EntityWrapper();
                tEvalUnitInfoWrapper.eq("ID_A", tProPerformanceInfo.getIdA());
                List<TEvalUnitInfo> oldEvalUnitInfolist = tEvalUnitInfoService.selectList(tEvalUnitInfoWrapper);

                //删除以保存被评价单位信息
                for (TEvalUnitInfo oldebean : oldEvalUnitInfolist) {
                    tEvalUnitInfoService.deleteById(oldebean.getIdB());
                }
                //删除已保存子项目
                for (TProPerformanceInfo proPerformanceInfo : oldProPerformanceInfoList) {
                    proPerformanceInfo.deleteById(proPerformanceInfo.getIdA());
                }
//                for (TProPerformanceInfo obean : tProPerformanceInfoList) {
//                    deleteById(obean.getIdA());
//                }

                if ("0".equals(tProPerformanceInfo.getProIsdismant())) {
                    //原来拆分,现在不拆分

                    //组建工作组N-否（默认）
                    tProPerformanceInfo.setIsSetupWorkingGroup("N");
                    //组建专家组
                    tProPerformanceInfo.setExpertGroupIsformed("0");
                    //评价报告审核状态
                    tProPerformanceInfo.setEvalReportStatus("0");
                    //工作总结审核状态
                    tProPerformanceInfo.setWorkSummaryStatus("0");
                    //项目档案归档审核状态
                    tProPerformanceInfo.setArchivesClassStatus("0");

                    //修改人和时间
                    tProPerformanceInfo.setUpdater(user.getName());
                    tProPerformanceInfo.setUpdateTime(new Date());
                    if ("1".equals(tProPerformanceInfo.getProStatus())) {
                        tProPerformanceInfo.setProApprovalTime(new Date());
                        tProPerformanceInfo.setProApprovaler(user.getName());
                    }
                    //重新修改
                    tProPerformanceInfo.setParentProCode(oldbean.getParentProCode());
                    tProPerformanceInfo.setParentProName(oldbean.getParentProName());
                    tProPerformanceInfo.setProCode(tProPerformanceInfo.getParentProCode());
//                    tProPerformanceInfo.setProCode(tProPerformanceInfo.getParentProCode() + "-Z01");
                    tProPerformanceInfo.setProName(tProPerformanceInfo.getParentProName());
                    insertOrUpdate(tProPerformanceInfo);

                    //循环添加被评价单位信息
                    for (TEvalUnitInfo ebean : tEvalUnitInfoList) {
                        //设置外键
                        ebean.setIdB(null);
                        ebean.setIdA(tProPerformanceInfo.getIdA());
                        tEvalUnitInfoService.insertOrUpdate(ebean);
                    }

                } else if ("1".equals(tProPerformanceInfo.getProIsdismant())) {
                    //原来拆分,现在拆分
                    for (int i = 0; i < tProPerformanceInfoList.size(); i++) {
                        TProPerformanceInfo bean = tProPerformanceInfoList.get(i);

                        //组建工作组N-否（默认）
                        bean.setIsSetupWorkingGroup("N");
                        //组建专家组
                        bean.setExpertGroupIsformed("0");
                        //评价报告审核状态
                        bean.setEvalReportStatus("0");
                        //工作总结审核状态
                        bean.setWorkSummaryStatus("0");
                        //项目档案归档审核状态
                        bean.setArchivesClassStatus("0");

                        //获取主项目的code、name、业务类型、项目合伙人、项目经理
                        bean.setParentProCode(oldbean.getParentProCode());
                        bean.setParentProName(oldbean.getParentProName());
//                        bean.setProCode(oldbean.getParentProCode() + "-Z" + String.format("%02d", i + 1));
//                        bean.setProName(oldbean.getParentProName());
                        bean.setBussinessType(tProPerformanceInfo.getBussinessType());
                        bean.setBussinessTypeId(tProPerformanceInfo.getBussinessTypeId());
                        bean.setProPartenName(tProPerformanceInfo.getProPartenName());
                        bean.setProPartenId(tProPerformanceInfo.getProPartenId());
                        bean.setProManagerId(tProPerformanceInfo.getProManagerId());
                        bean.setProManagerName(oldbean.getProManagerName());
                        bean.setEvaluationObj(tProPerformanceInfo.getEvaluationObj());
                        bean.setProStatus(tProPerformanceInfo.getProStatus());
                        bean.setProIsdismant(tProPerformanceInfo.getProIsdismant());
                        bean.setProSecretaryId(tProPerformanceInfo.getProSecretaryId());
                        bean.setProSecretaryName(tProPerformanceInfo.getProSecretaryName());
                        bean.setProIsSecret(tProPerformanceInfo.getProIsSecret());
                        //获取主项目的项目四级分类
//                        bean.setProLevel4ClassName(tProPerformanceInfo.getProLevel4ClassName());
//                        bean.setProLevel4ClassId(tProPerformanceInfo.getProLevel4ClassId());
                        //修改人
                        bean.setUpdater(user.getName());
                        bean.setUpdateTime(new Date());
                        if ("1".equals(bean.getProStatus())) {
                            bean.setProApprovalTime(new Date());
                            bean.setProApprovaler(user.getName());
                        }
                        bean.setIdA(null);
                        insertOrUpdate(bean);

                        //循环重新添加每一个子项目的被评价单位信息
                        for (TEvalUnitInfo ebean : tEvalUnitInfoList) {
                            //设置外键
                            ebean.setIdB(null);
                            ebean.setIdA(bean.getIdA());
                            tEvalUnitInfoService.insertOrUpdate(ebean);
                        }

                    }

                }
            } else if ("0".equals(oldbean.getProIsdismant())) {
                //原来不拆分
               
//                查询原来的被评价单位，删除
                EntityWrapper tEvalUnitInfoWrapper = new EntityWrapper();
                tEvalUnitInfoWrapper.eq("ID_A", tProPerformanceInfo.getIdA());
                List<TEvalUnitInfo> oldEvalUnitInfolist = tEvalUnitInfoService.selectList(tEvalUnitInfoWrapper);
                //删除以保存被评价单位信息
                for (TEvalUnitInfo oldebean : oldEvalUnitInfolist) {
                    tEvalUnitInfoService.deleteById(oldebean.getIdB());
                }
                if ("0".equals(tProPerformanceInfo.getProIsdismant())) {
                    //原来不拆分,现在不拆分

                    //风险级别
                    oldbean.setRiskLevel(tProPerformanceInfo.getRiskLevel());
                    //获取主项目的项目四级分类
                    oldbean.setProLevel4ClassName(tProPerformanceInfo.getProLevel4ClassName());
                    oldbean.setProLevel4ClassId(tProPerformanceInfo.getProLevel4ClassId());
                    //国民经济行业分类
                    oldbean.setNationEcoIndustClassName(tProPerformanceInfo.getNationEcoIndustClassName());
                    oldbean.setNationEcoIndustClassId(tProPerformanceInfo.getNationEcoIndustClassId());
                    //预算支出功能分类
                    oldbean.setBudFunctClassName(tProPerformanceInfo.getBudFunctClassName());
                    oldbean.setBudFunctClassId(tProPerformanceInfo.getBudFunctClassId());
                    //项目独立复核人
                    oldbean.setProIndepReviewName(tProPerformanceInfo.getProIndepReviewName());
                    oldbean.setProIndepReviewId(tProPerformanceInfo.getProIndepReviewId());

                    oldbean.setProIsdismant(tProPerformanceInfo.getProIsdismant());
                    oldbean.setProStatus(tProPerformanceInfo.getProStatus());
                    oldbean.setEvaluationObj(tProPerformanceInfo.getEvaluationObj());

                    oldbean.setProSecretaryId(tProPerformanceInfo.getProSecretaryId());
                    oldbean.setProSecretaryName(tProPerformanceInfo.getProSecretaryName());
                    oldbean.setProIsSecret(tProPerformanceInfo.getProIsSecret());
                    
                    oldbean.setUpdater(user.getName());
                    oldbean.setUpdateTime(new Date());
                    oldbean.setProStatus(tProPerformanceInfo.getProStatus());
                    if ("1".equals(tProPerformanceInfo.getProStatus())) {
                        oldbean.setProApprovalTime(new Date());
                        oldbean.setProApprovaler(user.getName());
                    }
                    //循环添加被评价单位信息
                    for (TEvalUnitInfo ebean : tEvalUnitInfoList) {
                        //设置外键
                        ebean.setIdB(null);
                        ebean.setIdA(tProPerformanceInfo.getIdA());
                        tEvalUnitInfoService.insertOrUpdate(ebean);
                    }
                    insertOrUpdate(oldbean);
                } else if ("1".equals(tProPerformanceInfo.getProIsdismant())) {
                    //原来不拆分,现在拆分
                    //删除原来的一条子项目
                    deleteById(oldbean.getIdA());
                    for (int i = 0; i < tProPerformanceInfoList.size(); i++) {
                        TProPerformanceInfo bean = tProPerformanceInfoList.get(i);
                        bean.setIdA(null);
                        //新增
                        //组建工作组N-否（默认）
                        bean.setIsSetupWorkingGroup("N");
                        //组建专家组
                        bean.setExpertGroupIsformed("0");
                        //评价报告审核状态
                        bean.setEvalReportStatus("0");
                        //工作总结审核状态
                        bean.setWorkSummaryStatus("0");
                        //项目档案归档审核状态
                        bean.setArchivesClassStatus("0");

                        //获取主项目的code、name、业务类型、项目合伙人、项目经理
                        bean.setParentProCode(oldbean.getParentProCode());
                        bean.setParentProName(oldbean.getParentProName());
//                        bean.setProCode(oldbean.getParentProCode() + "-Z" + String.format("%02d", i + 1));
                        bean.setProName(oldbean.getParentProName());
                        
                        bean.setProPartenName(oldbean.getProPartenName());
                        bean.setProPartenId(oldbean.getProPartenId());
                        bean.setProManagerId(oldbean.getProManagerId());
                        bean.setProManagerName(oldbean.getProManagerName());

                        bean.setProStatus(tProPerformanceInfo.getProStatus());
                        bean.setProIsdismant(tProPerformanceInfo.getProIsdismant());
                        bean.setEvaluationObj(tProPerformanceInfo.getEvaluationObj());
                        bean.setProSecretaryId(tProPerformanceInfo.getProSecretaryId());
                        bean.setProSecretaryName(tProPerformanceInfo.getProSecretaryName());
                        bean.setProIsSecret(tProPerformanceInfo.getProIsSecret());
                        //修改人
                        bean.setUpdater(user.getName());
                        bean.setUpdateTime(new Date());
                        if ("1".equals(bean.getProStatus())) {
                            bean.setProApprovalTime(new Date());
                            bean.setProApprovaler(user.getName());
                        }
                        insertOrUpdate(bean);

                        //循环重新添加每一个子项目的被评价单位信息
                        for (TEvalUnitInfo ebean : tEvalUnitInfoList) {
                            //设置外键
                            ebean.setIdB(null);
                            ebean.setIdA(bean.getIdA());
                            tEvalUnitInfoService.insertOrUpdate(ebean);
                        }
                    }

                }
            }
            //设置主项目状态变成“1“
            tMainProjectSyncMapper.updateProStatusById(idMainA, "1");
            return true;

        }
        return false;
    }

    @Transactional
    @Override
    public Boolean deleteByProStatus(Integer id) throws Exception {
        TProPerformanceInfo bean = selectById(id);
        if ("1".equals(bean.getProIsdismant())) {
            //判断是否拆分子项目
            List<TProPerformanceInfo> list = baseMapper.findByCloumn("PARENT_PRO_CODE", bean.getParentProCode());
            for (int i = 0; i < list.size(); i++) {
                TProPerformanceInfo ebean = list.get(i);
                ebean.setProStatus("9");
                //逻辑删除信息
//                updateById(ebean);
                EntityWrapper entityWrapperebean = new EntityWrapper();
                entityWrapperebean.eq("ID_A", ebean.getIdA());
                update(ebean, entityWrapperebean);
                //物理删除被评价单位
                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("ID_A", ebean.getIdA());
                List<TEvalUnitInfo> evalUnitInfoList = tEvalUnitInfoService.selectList(entityWrapper);
                for (TEvalUnitInfo tEvalUnitInfo : evalUnitInfoList) {
                    tEvalUnitInfo.deleteById(tEvalUnitInfo.getIdB());
                }
            }
        } else if ("0".equals(bean.getProIsdismant())) {
            //不拆封
            bean.setProStatus("9");
            //逻辑删除信息
//            updateById(bean);
            EntityWrapper entityWrapperbean = new EntityWrapper();
            entityWrapperbean.eq("ID_A", bean.getIdA());
            update(bean, entityWrapperbean);
            //物理删除被评价单位
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("ID_A", bean.getIdA());
            List<TEvalUnitInfo> evalUnitInfoList = tEvalUnitInfoService.selectList(entityWrapper);
            for (TEvalUnitInfo tEvalUnitInfo : evalUnitInfoList) {
                tEvalUnitInfo.deleteById(tEvalUnitInfo.getIdB());
            }
        }
        //根据主项目编号修改主项目状态
        tMainProjectSyncMapper.updateProStatusByCloumn("MAIN_PRO_CODE", bean.getParentProCode(), "0");
        return true;
    }

    @Override
    public Workbook expertLedgerExcel(Integer pageNum, Integer pageSize, TProPerformanceInfo bean, String search) {
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<TProPerformanceInfo> list = baseMapper.selectLikeLedgerPageList(pageNum, pageSize,
                bean, search);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setNum(i + 1);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("项目台账", "项目台账"),
                TProPerformanceInfo.class, list);
        return workbook;
    }

    @Override
    public List<TProPerformanceInfo> findRelationProPage(int pageNum, int pagesize, TProPerformanceInfo tProPerformanceInfo, String search) {
        List<TProPerformanceInfo> pagebean = baseMapper.findRelationProPage(pageNum,pagesize,tProPerformanceInfo,search);
        return pagebean;
    }

    
    @Override
    public PageInfo<TProPerformanceInfo> chooseResearchPro(Integer pageNum, Integer pageSize, Integer preOrScheme, TProPerformanceInfo bean, String search) {
            if (null != pageNum && null != pageSize) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<TProPerformanceInfo> page = baseMapper.chooseResearchPro(pageNum, pageSize, preOrScheme, bean, search);
            PageInfo<TProPerformanceInfo> pageInfo = new PageInfo<TProPerformanceInfo>(page);
            return pageInfo;
    } 
    @Override
    public PageInfo<TProPerformanceInfo> choosePreparEvalWorkPro(Integer pageNum, Integer pageSize, Integer preOrScheme, TProPerformanceInfo bean, String search, ShiroUser user) {
            if (null != pageNum && null != pageSize) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<TProPerformanceInfo> page = baseMapper.choosePreparEvalWorkPro(pageNum, pageSize, preOrScheme, bean, search,user);
            PageInfo<TProPerformanceInfo> pageInfo = new PageInfo<TProPerformanceInfo>(page);
            return pageInfo;
    }

    @Override
    public PageInfo<TProPerformanceInfo> chooseEvalReportPro(Integer pageNum, Integer pageSize, Integer preOrScheme, TProPerformanceInfo bean, String search, ShiroUser user) {
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<TProPerformanceInfo> page = baseMapper.chooseEvalReportPro(pageNum, pageSize, preOrScheme, bean, search,user);
        PageInfo<TProPerformanceInfo> pageInfo = new PageInfo<TProPerformanceInfo>(page);
        return pageInfo;
    }

}
