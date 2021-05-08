package com.common.business.collection.meanslistcheck.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.service.RelationProListService;
import com.common.business.collection.meanslistcheck.entity.BussinessFlowProList;
import com.common.business.collection.meanslistcheck.entity.DesigneeRecProList;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.common.business.collection.meanslistcheck.mapper.TDevelopmentInfoListCheckRecMapper;
import com.common.business.collection.meanslistcheck.service.BussinessFlowProListService;
import com.common.business.collection.meanslistcheck.service.DesigneeRecProListService;
import com.common.business.collection.meanslistcheck.service.TDevelopmentInfoListCheckRecService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Service
@Transactional
public class TDevelopmentInfoListCheckRecServiceImpl extends ServiceImpl<TDevelopmentInfoListCheckRecMapper, TDevelopmentInfoListCheckRec> implements TDevelopmentInfoListCheckRecService {

    @Autowired
    private RelationProListService relationProListService;
    @Autowired
    private BussinessFlowProListService bussinessFlowProListService;
    @Autowired
    private UserService userService;
    @Autowired
    private DesigneeRecProListService designeeRecProListService;
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;
    

    /**
     * @Title: 
     * @Description: 审批
     * @author: 陈睿超
     * @createDate: 2021/3/22 20:10
     * @updater: 陈睿超
     * @updateDate: 2021/3/22 20:10
     * @param developmentInfoListCheckRec : 审批数据
     * @param user : 当前登录人
     * @return 
     **/
    @Override
    public Boolean check(TDevelopmentInfoListCheckRec developmentInfoListCheckRec, ShiroUser user) {
        //根据外键获取资料收集关系表
        RelationProList relationProList = relationProListService.selectById(developmentInfoListCheckRec.getIdR());
        //获取业务流程
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("ID_R",developmentInfoListCheckRec.getIdR());
        List<BussinessFlowProList> bussinessFlowProList = bussinessFlowProListService.selectList(entityWrapper);
        
        //判断是否转派任务信息
        if (null == developmentInfoListCheckRec.getBussinessFlowPro()){
            //发起人节点
            BussinessFlowProList bussinessFlowPro = bussinessFlowProList.get(0);
            //审批人节点
            BussinessFlowProList bussinessFlowPro1 = bussinessFlowProList.get(1);
            //不转派
            if ("1".equals(developmentInfoListCheckRec.getCheckResult())){
                //通过，因为只有一级审批所以直接结束,如果有多级的话需要判断是否是最后一个节点，是否是被转派人审批
                //版本状态 2-已完成
                relationProList.setCreateStauts("2");
                //更新节点流程信息
                bussinessFlowPro1.setNodeIsActive("0");
                bussinessFlowPro1.setCheckUserJobNumber(user.getGroupMemberCode());
                bussinessFlowPro1.setCurrentNodeStatus("1");
                
                bussinessFlowPro.setNodeIsActive("0");
                bussinessFlowProListService.updateById(bussinessFlowPro);
                bussinessFlowProListService.updateById(bussinessFlowPro1);
                //审批通过
                if ("2".equals(relationProList.getCreateStauts())){
                    //改变项目研函审核状态
                    TProPerformanceInfo pro = proPerformanceInfoMapper.selectById(relationProList.getIdA());
                    pro.setDevelopmentStatus("1");
                    proPerformanceInfoMapper.updateById(pro);
                }
            }else if ("0".equals(developmentInfoListCheckRec.getCheckResult())) {
                //不通过
                //版本状态 -1-退回
                relationProList.setCreateStauts("-1");
                
                bussinessFlowPro.setNodeIsActive("1");
                bussinessFlowPro.setCurrentNodeStatus("0");

                bussinessFlowPro1.setNodeIsActive("0");
                bussinessFlowPro1.setCheckUserJobNumber(user.getGroupMemberCode());
                bussinessFlowPro1.setCurrentNodeStatus("0");
                bussinessFlowPro1.setDesigneeJobNumber(null);
                bussinessFlowPro1.setDesigneeDeptName(null);
                bussinessFlowPro1.setDesigneeDeptId(null);
                bussinessFlowPro1.setDesigneeName(null);
                bussinessFlowPro1.setDesigneeId(null);
                bussinessFlowProListService.updateById(bussinessFlowPro);
                bussinessFlowProListService.updateAllColumnById(bussinessFlowPro1);
            }

            //下级审批人
            relationProList.setNextCheckId(null);
            relationProList.setNextCheckName(null);

            //判断是不是被转派人审批
            if (!StringUtils.isEmpty(bussinessFlowPro1.getDesigneeId()) && user.getId().equals(bussinessFlowPro1.getDesigneeId())){
                //被转派人进来审批需要添加备注
                //设置审批记录的备注
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str = sdf.format(new Date());
                developmentInfoListCheckRec.setRemark(str+"时间 由 "+bussinessFlowPro1.getCheckUser()+" 转 "+
                        user.getName()+" 承办”");
                developmentInfoListCheckRec.setOrderOfCurrentNode(bussinessFlowPro1.getOrderOfCurrentNode());
            }
            
            developmentInfoListCheckRec.setCheckUserId(user.getId().toString());
            developmentInfoListCheckRec.setCheckUser(user.getName());
            developmentInfoListCheckRec.setCheckTime(new Date());
            developmentInfoListCheckRec.setCheckDataStatus("1");
            developmentInfoListCheckRec.setOrderOfCurrentNode(bussinessFlowPro1.getOrderOfCurrentNode());
            //保存审批记录
            insert(developmentInfoListCheckRec);
            relationProListService.updateById(relationProList);
        }else {
            //转派
            //被转派人
            RcUser rcuser = userService.selectById(developmentInfoListCheckRec.getBussinessFlowPro().getDesigneeId());
            //下级审批人
            relationProList.setNextCheckName(rcuser.getName());
            relationProList.setNextCheckId(rcuser.getId().toString());
            //原审批人id
            String checkUserId = null;
            String checkUser = null;
            //节点
            Integer orderOfCurrentNode = null;
            //更新节点流程信息
            for (int i = 0; i < bussinessFlowProList.size(); i++) {
                BussinessFlowProList bussinessFlowPro = bussinessFlowProList.get(i);
                if("1".equals(bussinessFlowPro.getNodeIsActive())){
                    //活跃节点
                    checkUserId = bussinessFlowPro.getCheckUserId();
                    checkUser = bussinessFlowPro.getCheckUser();
                    orderOfCurrentNode = bussinessFlowPro.getOrderOfCurrentNode();
                    bussinessFlowPro.setDesigneeId(rcuser.getId().toString());
                    bussinessFlowPro.setDesigneeName(rcuser.getName());
                    bussinessFlowPro.setDesigneeDeptId(rcuser.getDeptId());
                    bussinessFlowPro.setDesigneeDeptName(rcuser.getDeptName());
                    bussinessFlowPro.setUpdateor(user.getName());
                    bussinessFlowPro.setUpdateTime(new Date());
                    bussinessFlowPro.setDesigneeJobNumber(rcuser.getGroupMemberCode());
//                    bussinessFlowPro.setNodeIsActive("0");
                    bussinessFlowProListService.updateById(bussinessFlowPro);
                }
            }
            //增加一条转派记录
            DesigneeRecProList designeeRecProList = new DesigneeRecProList();
            designeeRecProList.setIdR(developmentInfoListCheckRec.getIdR());
            designeeRecProList.setDesignee(new Date());
            designeeRecProList.setCheckUserId(checkUserId);
            designeeRecProList.setCheckUser(checkUser);
            designeeRecProList.setDesigneeId(rcuser.getId().toString());
            designeeRecProList.setDesigneeName(rcuser.getName());
            designeeRecProList.setOrderOfCurrentNode(orderOfCurrentNode);
            designeeRecProListService.insert(designeeRecProList);
            relationProListService.updateById(relationProList);
        }


        return true;
    }
}
