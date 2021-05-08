package com.common.business.collection.meanslistcheck.service.impl;

import com.common.business.collection.meanslistcheck.entity.BussinessFlowProList;
import com.common.business.collection.meanslistcheck.entity.TDevelopmentInfoListCheckRec;
import com.common.business.collection.meanslistcheck.mapper.BussinessFlowProListMapper;
import com.common.business.collection.meanslistcheck.service.BussinessFlowProListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-22
 */
@Service
@Transactional
public class BussinessFlowProListServiceImpl extends ServiceImpl<BussinessFlowProListMapper, BussinessFlowProList> implements BussinessFlowProListService {

    @Autowired
    private RcUserMapper userMapper;

    @Override
    public BussinessFlowProList getapplicantUser(ShiroUser user) {
        RcUser rcuser = userMapper.selectById(user.getId());
        BussinessFlowProList bussinessFlowProList = new BussinessFlowProList();
        bussinessFlowProList.setCheckUserId(rcuser.getId().toString());
        bussinessFlowProList.setCheckUser(rcuser.getName());
        bussinessFlowProList.setCheckUserDeptId(rcuser.getDeptId().toString());
        bussinessFlowProList.setCheckUserDeptName(rcuser.getDeptName());
        bussinessFlowProList.setOrderOfCurrentNode(1);
        bussinessFlowProList.setNodeIsActive("1");
        bussinessFlowProList.setCreateor(rcuser.getName());
        bussinessFlowProList.setCreateTime(new Date());
        bussinessFlowProList.setUpdateor(rcuser.getName());
        bussinessFlowProList.setUpdateTime(new Date());
        bussinessFlowProList.setCheckUserJobNumber(rcuser.getGroupMemberCode());
        return bussinessFlowProList;
    }

    @Override
    public List<BussinessFlowProList> assemblyBussinessFlow(List<BussinessFlowProList> bussinessFlowProList, List<TDevelopmentInfoListCheckRec> checkHistoryList) {
        
//        checkHistoryList.sort((o1, o2) -> o1.getCheckTime().compareTo(o2.getCheckTime()));
//        bussinessFlowProList.sort((o1, o2) -> o1.getOrderOfCurrentNode().compareTo(o2.getOrderOfCurrentNode()));
        //根据节点编号排列
        
        bussinessFlowProList.sort(Comparator.comparing(BussinessFlowProList::getOrderOfCurrentNode));
        checkHistoryList.sort(Comparator.comparing(TDevelopmentInfoListCheckRec::getOrderOfCurrentNode));
        for (int j = 0; j < checkHistoryList.size(); j++) {
            TDevelopmentInfoListCheckRec checkHistory = checkHistoryList.get(j);

            for (int i = 0; i < bussinessFlowProList.size(); i++) {
                BussinessFlowProList bussinessFlowPro = bussinessFlowProList.get(i);
                //需要添加审批记录字段
                //节点序号相等
                if ((bussinessFlowPro.getOrderOfCurrentNode()).equals(checkHistory.getOrderOfCurrentNode())){
                    bussinessFlowProList.get(i).setCheckTime(checkHistory.getCheckTime());
                    bussinessFlowProList.get(i).setCheckResult(checkHistory.getCheckResult());
                    bussinessFlowProList.get(i).setCheckOpinion(checkHistory.getCheckOpinion());
                    bussinessFlowProList.get(i).setRemark(checkHistory.getRemark());
//                    bussinessFlowProList.get(i).setCheckResult(checkHistory.getCheckResult());
//                    bussinessFlowProList.get(i).setCheckTime(checkHistory.getCheckTime());
//                    bussinessFlowProList.get(i).setCheckOpinion(checkHistory.getCheckOpinion());
//                    bussinessFlowProList.get(i).setRemark(checkHistory.getRemark());
                }
            }
        }
        return bussinessFlowProList;
    }


}
